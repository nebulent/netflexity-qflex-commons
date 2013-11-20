package org.netflexity.api.orm;

import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.commons.lang.StringUtils;
import org.netflexity.api.orm.DatasourceMetadata;
import org.netflexity.api.orm.FieldMetadata;
import org.netflexity.api.orm.RecordMetadata;
import org.netflexity.api.util.PropertiesUtil;
import org.netflexity.api.util.StringConstants;
import org.xml.sax.InputSource;

/**
 * @author Max Fedorov
 * 
 * MetadataTest Metadata loading.
 */
public class MetadataTest {

	public static void main(String[] args) {
        String ormPackagePath = StringUtils.replace(RecordMetadata.class.getPackage().getName(), StringConstants.DOT, StringConstants.FORWARD_SLASH);
        String packagePath = StringUtils.replace(MetadataTest.class.getPackage().getName(), StringConstants.DOT, StringConstants.FORWARD_SLASH);
		try {
			InputSource rules = new InputSource(PropertiesUtil.getInputStream(ormPackagePath + "/orm-rules.xml", MetadataTest.class.getClassLoader()));
			InputStream input = PropertiesUtil.getInputStream(packagePath + "/dorm-sample.xml", MetadataTest.class.getClassLoader());
			Digester digester = DigesterLoader.createDigester(rules);
			/*File rules = new File("dorm-rules.xml");
			File input = new File("dorm-sample.xml");
			Digester digester = DigesterLoader.createDigester(rules.toURL());*/
			List dsMeta = (List) digester.parse(input);
			for (Iterator iter = dsMeta.iterator(); iter.hasNext();) {
				DatasourceMetadata dmd = (DatasourceMetadata) iter.next();
				printDatasourceMetadataContents(dmd);
			}
		} 
		catch (Exception exc) {
			System.out.println(exc.getMessage());
			exc.printStackTrace();
		}
	}
	
	public static void printDatasourceMetadataContents(DatasourceMetadata dmd){
		System.out.println("->NAME=" + dmd.getName());
		System.out.println("->CLASS=" + dmd.getClassName());
		System.out.println("->PROPERTY=" + dmd.getProperties());
		System.out.println("->QUERY=" + dmd.getQueries());
		System.out.println();

		Collection tableMeta = dmd.geRecordMetadata();
		for (Iterator iterator = tableMeta.iterator(); iterator.hasNext();) {
			RecordMetadata tbl = (RecordMetadata) iterator.next();
			System.out.println("-->NAME=" + tbl.getName());
			System.out.println("-->CLASS=" + tbl.getClassName());
			System.out.println("-->PROPERTY=" + tbl.getProperties());
			System.out.println("-->DS=" + tbl.getDatasourceMetadata().getName());
			System.out.println();

            Collection columnMeta = tbl.getFieldMetadata();
			for (Iterator iter2 = columnMeta.iterator(); iter2.hasNext();) {
				FieldMetadata col = (FieldMetadata) iter2.next();
				System.out.println("--->NAME=" + col.getName());
				System.out.println("--->JDBC_TYPE=" + col.getJdbcType());
				System.out.println("--->REQUIRED=" + col.isRequired());
				System.out.println("--->PK=" + col.isPrimaryKey());
				System.out.println("--->FK=" + col.isForeignKey());
				System.out.println("--->PROPERTY=" + col.getProperties());
				System.out.println("--->NORMALIZE=" + col.getNormalization());
				System.out.println("--->REC=" + col.getRecordMetadata().getName());
				System.out.println();
			}
		}
	}
}