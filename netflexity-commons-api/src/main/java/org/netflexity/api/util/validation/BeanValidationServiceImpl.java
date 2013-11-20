package org.netflexity.api.util.validation;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.WeakHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.Arg;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Form;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.ValidatorResources;
import org.apache.commons.validator.ValidatorResult;
import org.apache.commons.validator.ValidatorResults;
import org.netflexity.api.util.PropertiesUtil;
import org.netflexity.api.util.StringConstants;
import org.xml.sax.SAXException;
	
/**
 * @author Max Fedorov
 * 
 * Singleton that allows to validate record fields against simple rules,
 * defined in the same package as the record class and named the same way.
 * Files necessary: MyBean.xml (rules), MyBean.properties (error messages), MyBean (view bean)
 *
 */
public class BeanValidationServiceImpl implements BeanValidationService {

	public static final String XML_EXT = ".xml";

	private Map resources = Collections.synchronizedMap(new WeakHashMap());
	private Map bundles = Collections.synchronizedMap(new WeakHashMap());

	protected BeanValidationServiceImpl() {
	}

	/* (non-Javadoc)
     * @see org.netflexity.dorm.validator.IRecordValidationService#validate(java.lang.Object, java.lang.ClassLoader)
     */
	public List validate(Object bean, ClassLoader loader) {
		if(bean == null){
			throw new BeanValidationRuntimeException("Cannot validate a NULL bean.");
		}
		
		List errors = new ArrayList();
		String fullClassName = bean.getClass().getName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(StringConstants.DOT)+ 1);
		
		// Obtain bundle from cache.
		ResourceBundle bundle = null;
		ResourceBundle sysBundle = null;
		synchronized(bundles){
			// Load bundle if it does not exist.
			bundle = (ResourceBundle)bundles.get(fullClassName);
			if(bundle == null){
				if (loader != null){
					bundle = ResourceBundle.getBundle(fullClassName, Locale.getDefault(), loader);
				}
				else{
					bundle = ResourceBundle.getBundle(fullClassName);
				}
				
				if(bundle != null){
					bundles.put(fullClassName, bundle);
				}
				else{
					throw new BeanValidationRuntimeException("Cannot find file " + fullClassName + ".properties");
				}
			}
			
			// Check is system bundle is loaded.
			sysBundle = (ResourceBundle)bundles.get(BeanValidationServiceImpl.class.getName());
			if(sysBundle == null){
				if (loader != null){
					sysBundle = ResourceBundle.getBundle(BeanValidationServiceImpl.class.getName(), Locale.getDefault(), loader);
				}
				else{
					sysBundle = ResourceBundle.getBundle(BeanValidationServiceImpl.class.getName());
				}

				if(sysBundle != null){
					bundles.put(BeanValidationServiceImpl.class.getName(), sysBundle);
				}
			}
		}
		
		// Obtain bean validation rules from cache.
		ValidatorResources resource = null;
		synchronized(resources){
			resource = (ValidatorResources)resources.get(fullClassName);
			
			// Load resource if it does not exist.
			if(resource == null){
				// Load the validator xml file and general rules file.
				String xmlPath = StringUtils.replace(fullClassName, StringConstants.DOT, StringConstants.FORWARD_SLASH) + XML_EXT;
				String rulesPath = StringUtils.replace(BeanValidationServiceImpl.class.getName(), StringConstants.DOT, StringConstants.FORWARD_SLASH) + XML_EXT;
				
				InputStream[] streamArray = new InputStream[2];
				try {
					// Load all resources.
					streamArray[0] = PropertiesUtil.getInputStream(rulesPath, loader);
					streamArray[1] = PropertiesUtil.getInputStream(xmlPath, loader);
					
					// Create validator resource, based on 2 streams.
					resource = new ValidatorResources(streamArray);
				}
				catch(IOException io){
					throw new BeanValidationRuntimeException("Cannot find or read file " + xmlPath);
				}
				catch(SAXException sax){
					throw new BeanValidationRuntimeException("Cannot parse file " + xmlPath + sax.getMessage());
				}
				finally {
					// Make sure we close input streams.
					if (streamArray != null && streamArray.length > 0) {
						for (int i = 0; i < streamArray.length; i++) {
							InputStream in = streamArray[i];
							try {
								if(in != null)in.close();
							} 
							catch (IOException e) {}
                        }
						
					}
		            
					if(resource != null){
						resources.put(fullClassName, resource);
					}
				}
			}
		}
		
		// Create a validator with the SampleBean actions for the bean
		Validator validator = new Validator(resource, className);
        
		// Tell the validator which bean to validate against.
		validator.setParameter(Validator.BEAN_PARAM, bean);

		// Report only failed fields.
		validator.setOnlyReturnErrors(true);
        
		// Run the validation actions against the bean.
		ValidatorResults results = null;
		try {
			results = validator.validate();
		} 
		catch (ValidatorException e) {
			throw new BeanValidationRuntimeException(e);
		}
        
		// Start by getting the form for the current locale and Bean.
		Form form = resource.getForm(Locale.getDefault(), className);

		// Iterate over each of the properties of the Bean which had messages.
		Iterator propertyNames = results.getPropertyNames().iterator();
		while (propertyNames.hasNext()) {
			String propertyName = (String) propertyNames.next();

			// Get the Field associated with that property in the Form
			Field field = form.getField(propertyName);
            
			// Get the result of validating the property.
			ValidatorResult result = results.getValidatorResult(propertyName);

			// Get all the actions run against the property, and iterate over their names.
			Map actionMap = result.getActionMap();
			Iterator keys = actionMap.keySet().iterator();
			while (keys.hasNext()) {
				String actName = (String) keys.next();

				// Get the Action for that name.
				ValidatorAction action = resource.getValidatorAction(actName);

				// If the result failed, format the Action's message against the formatted field name
				if (!result.isValid(actName)) {
					String message = null;
					try {
                        message = bundle.getString(action.getMsg());
                    }
                    catch (MissingResourceException e) {
						if(StringUtils.isEmpty(message)){
							if(sysBundle != null){
								try {
                                    message = sysBundle.getString(action.getMsg());
                                }
                                catch (MissingResourceException e1) {
									throw new BeanValidationRuntimeException(e);
                                }
							}
						}
                    }
                    
					// Set all arguments.
					Object[] argVals = {};
					Arg args[] = field.getArgs(propertyName);
					if(args != null && args.length > 0){
						argVals = new Object[args.length];
						for (int i = 0; i < args.length; i++) {
							Arg arg = args[i];
							if(arg.isResource()){
								argVals[i] = bundle.getString(field.getArg(i).getKey());
							}
							else{
								argVals[i] = field.getArg(i).getKey();
							}
						}
					}
					// System.out.println(MessageFormat.format(message, argVals));
					errors.add(MessageFormat.format(message, argVals));
				}
			}
		}
		return errors;
	}
}