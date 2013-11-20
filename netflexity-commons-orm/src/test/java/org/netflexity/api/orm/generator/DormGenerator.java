package org.netflexity.api.orm.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.StringUtils;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.util.StringConstants;

import freemarker.template.Configuration;
import freemarker.template.SimpleScalar;
import freemarker.template.Template;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * @author Max Fedorov
 *
 * Running this class with appropriate options will produce all the necessary
 * metadata information for any targeted database.
 */
public class DormGenerator {

    public static final String BASE = "Base";
    public static final String Y_ENDING = "y";
    public static final String S_ENDING = "s";
    public static final String ES_ENDING = "es";
    public static final String SS_ENDING = "ss";
    public static final String IES_ENDING = "ies";
    
    public static final String BASE_CLASS = "baseClass";
	public static final String BASE_PACKAGE = "basePackage";
    public static final String PACKAGE_STR = "package";
    public static final String DOMAIN_CLASSES_FTL = "domainClasses.ftl";
    public static final String BASE_CLASSES_FTL = "baseClasses.ftl";
	public static final String META_FTL = "meta.ftl";
	public static final String TABLE = "table";
	public static final String CLASS = "class";
	public static final String JAVA_EXT = ".java";
	public static final String TABLES = "tables";
	public static final String DORM_XML = "dorm.xml";
	
	public static final String PASSWORD_OPT = "p";
	public static final String USERNAME_OPT = "u";
	public static final String URL_OPT = "r";
	public static final String CATALOG_OPT = "c";
	public static final String SCHEMA_OPT = S_ENDING;
	public static final String DRIVER_OPT = "v";
	public static final String PACKAGE_OPT = "g";
	public static final String DIR_OPT = "d";
	
	/**
	 * -d /TEMP -g com.netflexity.qflex.data.base -c qflex -v com.mysql.jdbc.Driver -r jdbc:mysql://127.0.0.1/qflex -u root -p root
	 * @param args
	 */
	public static void main(String args[]){
		
		// Define command line rules.
		Options options = new Options();
		Option opt = new Option(DIR_OPT, true, "Place holder of generated files.");
		opt.setRequired(true);
		options.addOption(opt);
		opt = new Option(PACKAGE_OPT, true, "Package name, used by generated Java classes.");
		opt.setRequired(true);
		options.addOption(opt);
		opt = new Option(CATALOG_OPT, true, "Database catalog name.");
		opt.setRequired(true);
		options.addOption(opt);
		opt = new Option(SCHEMA_OPT, true, "Database schema name.");
		options.addOption(opt);
		opt = new Option(DRIVER_OPT, true, "JDBC driver class name.");
		opt.setRequired(true);
		options.addOption(opt);
		opt = new Option(URL_OPT, true, "Database connection string.");
		opt.setRequired(true);
		options.addOption(opt);
		opt = new Option(USERNAME_OPT, true, "Database username.");
		opt.setRequired(true);
		options.addOption(opt);
		opt = new Option(PASSWORD_OPT, true, "Database password.");
		opt.setRequired(true);
		options.addOption(opt);
		
		// Check command line, based on defined rules.
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = null;
		try{
			cmd = parser.parse(options, args);
		}
		catch (ParseException pe){ 
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(DormGenerator.class.getName(), options);
			return; 
		}

		// Connect to database and process tables.
		Connection conn = null;
		try {
			Class.forName(cmd.getOptionValue(DRIVER_OPT));
			conn = DriverManager.getConnection(cmd.getOptionValue(URL_OPT), cmd.getOptionValue(USERNAME_OPT), cmd.getOptionValue(PASSWORD_OPT));
		
			String dir = cmd.getOptionValue(DIR_OPT);
			String basePackageName = cmd.getOptionValue(PACKAGE_OPT);
			String domainPackageName = basePackageName.substring(0, basePackageName.lastIndexOf('.'));
			
			// Get all metadata.
			MetadataHelper meta = new MetadataHelper(conn, cmd.getOptionValue(CATALOG_OPT), cmd.getOptionValue(SCHEMA_OPT));
			List tables = meta.getMetadata();
			
			// Create folder structure.
			File basePackageFolder = new File(dir + File.separator + basePackageName.replace('.', File.separatorChar));
			if(!basePackageFolder.exists()){
				basePackageFolder.mkdirs();
			}
			File domainPackageFolder = basePackageFolder.getParentFile();
			
			// Generate metadata.
			File metaXml = new File(dir, DORM_XML);
			Map modelRoot = new HashMap();
			modelRoot.put(TABLES, tables);
			modelRoot.put(PACKAGE_STR, domainPackageName);
			modelRoot.put("createJavaClassName", new JavaClassNameMethod());
			
			String templateDir = StringUtils.EMPTY; // DormGenerator.class.getName().replace('.', File.separatorChar);
			String xmlContent = transform(modelRoot, templateDir, META_FTL);
			writeToFile(metaXml, xmlContent);
			System.out.println("Created " + metaXml.getPath());
			
			// Generate files.
			for (Iterator iter = tables.iterator(); iter.hasNext();) {
				RecordMetadata tbl = (RecordMetadata) iter.next();
				String tableName = tbl.getName();
				String domainClassName = createJavaClassName(tableName);
				String baseClassName = createJavaClassName(tableName) + BASE;
				File domainJava = new File(domainPackageFolder, domainClassName + JAVA_EXT);
				File baseJava = new File(basePackageFolder, baseClassName + JAVA_EXT);
				
				// Domain classes.
				modelRoot = new HashMap();
				modelRoot.put(PACKAGE_STR, domainPackageName);
				modelRoot.put(BASE_PACKAGE, basePackageName);
				modelRoot.put(BASE_CLASS, baseClassName);
				modelRoot.put(CLASS, domainClassName);
				modelRoot.put(TABLE, tbl);
				
				String javaContent = transform(modelRoot, templateDir, DOMAIN_CLASSES_FTL);
				writeToFile(domainJava, javaContent);
				System.out.println("Created " + domainJava.getPath());
				
				// Base classes.
				modelRoot.put(PACKAGE_STR, basePackageName);
				modelRoot.put(CLASS, baseClassName);
				
				javaContent = transform(modelRoot, templateDir, BASE_CLASSES_FTL);;
				writeToFile(baseJava, javaContent);
				System.out.println("Created " + baseJava.getPath());
			}
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if(conn != null){
				try {
					conn.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static class JavaClassNameMethod implements TemplateMethodModel {

		/* (non-Javadoc)
		 * @see freemarker.template.TemplateMethodModel#exec(java.util.List)
		 */
		public Object exec(List args) throws TemplateModelException {
			if (args.size() != 1) {
	            throw new TemplateModelException("Wrong arguments createJavaClassName(String)");
	        }

	        String arg = (String)args.get(0);
	        if(StringUtils.isNotEmpty(arg)){
	        	return new SimpleScalar(createJavaClassName(arg));
	        }
	        return new SimpleScalar(StringUtils.EMPTY);
		}
	}
	
	/**
	 * @param tableName
	 * @return
	 */
	private static String createJavaClassName(String tableName){
	    StringBuffer buff = new StringBuffer(16);
		String[] pieces = StringUtils.split(tableName, StringConstants.UNDERSCORE);
		for (int i = 0; i < pieces.length; i++) {
			String piece = pieces[i];
			if(StringUtils.isNotBlank(piece)){
				buff.append(piece.substring(0, 1).toUpperCase()).append(piece.substring(1).toLowerCase());
			}
		}
		String className = buff.toString();
		
		// Handle plural ending.
		if(className.length() > 2){
			String lastOne = className.substring(className.length()-1);
			String lastTwo = className.substring(className.length()-2);
			String lastThree = className.substring(className.length()-3);
			if(lastThree.equals(IES_ENDING)){
			    className = className.substring(0, className.length()-3) + Y_ENDING;
			}
			else if(lastTwo.equals(ES_ENDING) || (!lastTwo.equals(SS_ENDING) && lastOne.equals(S_ENDING))){
			    className = className.substring(0, className.length()-1);
			}
		}
		return className;
	}
	
	/**
	 * @param file
	 * @param content
	 */
	public static void writeToFile(File file, String content){
        writeFile(file, content, false);
    }
    
    /**
     * @param file
     * @param content
     * @param append
     */
    private static void writeFile(File file, String content, boolean append){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, append));
            writer.write(content);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {}
            }
        }
    }
    
    /**
     * Method transform for Freemarker template transformation.
     * 
     * @param dataModelRoot
     * @param packagePath
     * @param templateName
     * @return
     */
    public static String transform(Map dataModelRoot, String packagePath, String templateName) {
        // Obtain the configuration.
        Configuration cfg = new Configuration();
        try {
            // Set loader for templates.
            cfg.setClassForTemplateLoading(DormGenerator.class, packagePath);
            
            // Create a template.
            Template temp = cfg.getTemplate(templateName);

            // Merge data model with template.
            StringWriter out = new StringWriter();
            temp.process(dataModelRoot, out);
            out.flush();

            return out.getBuffer().toString();
        }
        catch (Throwable e) {
            e.printStackTrace();
            System.err.println("transform() failed to transform Freemarker template '" + templateName + "'" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
