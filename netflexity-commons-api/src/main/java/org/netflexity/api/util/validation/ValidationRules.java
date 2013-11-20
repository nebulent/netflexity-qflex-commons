package org.netflexity.api.util.validation;

import org.apache.commons.validator.DateValidator;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.util.ValidatorUtils;
import org.netflexity.api.util.StringConstants;

public class ValidationRules {
	
	public static final String DATE_PATTERN = "datePattern";
    public static final String FIELDINDEXED_LB = "fieldIndexed[";
    public static final String FIELDVALUE_LB = "fieldValue[";
    public static final String FIELDTEST_LB = "fieldTest[";
    public static final String FIELD_LB = "field[";
    public static final String RB = "]";
    public static final String LB = "[";
    public static final String FALSE = "false";
    public static final String TRUE = "true";
    public static final String FIELDJOIN = "fieldJoin";
    public static final String AND = "AND";
    public final static String FIELD_TEST_NULL = "NULL";
	public final static String FIELD_TEST_NOTNULL = "NOTNULL";
	public final static String FIELD_TEST_EQUAL = "EQUAL";
	public static final String VALIDATOR_EXCEPTION = "VALIDATOR-EXCEPTION";
    public static final String RUNTIME = "RUNTIME";
    public static final String CHECKED = "CHECKED";
    public static final String CHECKED_EXCEPTION = "CHECKED-EXCEPTION";
    public static final String RUNTIME_EXCEPTION = "RUNTIME-EXCEPTION";
    public static final String MINLENGTH = "minlength";
    public static final String MAXLENGTH = "maxlength";
	public static final String MASK = "mask";
    
    /**
     *  Checks if the field's length is less than or equal to the maximum value.
     *  A <code>Null</code> will be considered an error.
     *
     * @param  bean     The bean validation is being performed on.
     * @param  field    The <code>Field</code> object associated with the current
     * @return True if stated conditions met.
     */
    public static boolean validateMaxLength(Object bean, Field field) {
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }

        if (value != null) {
            try {
                int max = Integer.parseInt(field.getVarValue(MAXLENGTH));

                if (!GenericValidator.maxLength(value, max)) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }

        return true;
    }


    /**
     * Checks if the field's length is greater than or equal to the minimum value.
     * A <code>Null</code> will be considered an error.
     *
     * @param  bean     The bean validation is being performed on.
     * @param  field    The <code>Field</code> object associated with the current
     * @return True if stated conditions met.
     */
    public static boolean validateMinLength(Object bean, Field field) {

        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }

        if (!GenericValidator.isBlankOrNull(value)) {
            try {
                int min = Integer.parseInt(field.getVarValue(MINLENGTH));

                if (!GenericValidator.minLength(value, min)) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }

        return true;
    }
    
	/**
	 * Throws a runtime exception if the value of the argument is "RUNTIME", an
	 * exception if the value of the argument is "CHECKED", and a
	 * ValidatorException otherwise.
	 * 
	 * @param value
	 *            string which selects type of exception to generate
	 * @throws RuntimeException
	 *             with "RUNTIME-EXCEPTION as message" if value is "RUNTIME"
	 * @throws Exception
	 *             with "CHECKED-EXCEPTION" as message if value is "CHECKED"
	 * @throws ValidatorException
	 *             with "VALIDATOR-EXCEPTION" as message otherwise
	 */
	public static boolean validateRaiseException(final Object bean,
			final Field field) throws Exception {

		final String value = ValidatorUtils.getValueAsString(bean, field
				.getProperty());

		if (RUNTIME.equals(value)) {
			throw new RuntimeException(RUNTIME_EXCEPTION);

		} else if (CHECKED.equals(value)) {
			throw new Exception(CHECKED_EXCEPTION);

		} else {
			throw new ValidatorException(VALIDATOR_EXCEPTION);
		}
	}

	/**
	 * Validate if the string is composed of digits only.
	 * 
     * @param bean
     * @param field
     * @return
     */
    public static boolean validateDigits(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		return isDigit(value);
	}
	
	/**
	 * Validates a string against Regular Expression. Jakarta ORO library is necessary.
	 * 
     * @param bean
     * @param field
     * @return
     */
    public static boolean validateMask(Object bean, Field field) {
		String mask = field.getVarValue(MASK);
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		return (!GenericValidator.isBlankOrNull(value) && GenericValidator.matchRegexp(value, mask));
	}

	/**
	 * Validate date.
	 * 
     * @param bean
     * @param field
     * @return
     */
    public static boolean validateDate(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		String pattern = field.getVarValue(DATE_PATTERN);
		return (!GenericValidator.isBlankOrNull(value) && DateValidator.getInstance().isValid(value, pattern, false));
	}

	/**
	 * Checks if the field is required.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field isn't <code>null</code> and has a length
	 *         greater than zero, <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static boolean validateRequired(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		return !GenericValidator.isBlankOrNull(value);
	}
	
	/**
	 * Checks if the field can be successfully converted to a <code>byte</code>.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>byte</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static boolean validateByte(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		return GenericValidator.isByte(value);
	}

	/**
	 * Checks if the field can be successfully converted to a <code>short</code>.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>short</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static boolean validateShort(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field
				.getProperty());

		return GenericValidator.isShort(value);
	}

	/**
	 * Checks if the field can be successfully converted to a <code>int</code>.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>int</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static boolean validateInt(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field
				.getProperty());

		return GenericValidator.isInt(value);
	}

	/**
	 * Checks if field is positive assuming it is an integer
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @param field
	 *            Description of the field to be evaluated
	 * @return boolean If the integer field is greater than zero, returns true,
	 *         otherwise returns false.
	 */
	public static boolean validatePositive(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field
				.getProperty());

		return GenericTypeValidator.formatInt(value).intValue() > 0;
	}

	/**
	 * Checks if the field can be successfully converted to a <code>long</code>.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>long</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static boolean validateLong(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field
				.getProperty());

		return GenericValidator.isLong(value);
	}

	/**
	 * Checks if the field can be successfully converted to a <code>float</code>.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>float</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static boolean validateFloat(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field
				.getProperty());

		return GenericValidator.isFloat(value);
	}

	/**
	 * Checks if the field can be successfully converted to a
	 * <code>double</code>.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field can be successfully converted to a
	 *         <code>double</code> <code>true</code> is returned. Otherwise
	 *         <code>false</code>.
	 */
	public static boolean validateDouble(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field
				.getProperty());

		return GenericValidator.isDouble(value);
	}

	/**
	 * Checks if the field is an e-mail address.
	 * 
	 * @param value
	 *            The value validation is being performed on.
	 * @return boolean If the field is an e-mail address <code>true</code> is
	 *         returned. Otherwise <code>false</code>.
	 */
	public static boolean validateEmail(Object bean, Field field) {
		String value = ValidatorUtils.getValueAsString(bean, field
				.getProperty());

		return GenericValidator.isEmail(value);
	}

	public static boolean validateRequiredIf(Object bean, Field field,
			Validator validator) {

		Object form = validator.getParameterValue(Validator.BEAN_PARAM);
		String value = null;
		boolean required = false;
		if (isString(bean)) {
			value = (String) bean;
		} else {
			value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		}
		int i = 0;
		String fieldJoin = AND;
		if (!GenericValidator.isBlankOrNull(field.getVarValue(FIELDJOIN))) {
			fieldJoin = field.getVarValue(FIELDJOIN);
		}
		if (fieldJoin.equalsIgnoreCase(AND)) {
			required = true;
		}
		while (!GenericValidator.isBlankOrNull(field.getVarValue(FIELD_LB + i
				+ RB))) {
			String dependProp = field.getVarValue(FIELD_LB + i + RB);
			String dependTest = field.getVarValue(FIELDTEST_LB + i + RB);
			String dependTestValue = field.getVarValue(FIELDVALUE_LB + i + RB);
			String dependIndexed = field.getVarValue(FIELDINDEXED_LB + i + RB);
			if (dependIndexed == null)
				dependIndexed = FALSE;
			String dependVal = null;
			boolean this_required = false;
			if (field.isIndexed() && dependIndexed.equalsIgnoreCase(TRUE)) {
				String key = field.getKey();
				if ((key.indexOf(LB) > -1) && (key.indexOf(RB) > -1)) {
					String ind = key.substring(0, key.indexOf(StringConstants.DOT) + 1);
					dependProp = ind + dependProp;
				}
			}
			dependVal = ValidatorUtils.getValueAsString(form, dependProp);
			if (dependTest.equals(FIELD_TEST_NULL)) {
				if ((dependVal != null) && (dependVal.length() > 0)) {
					this_required = false;
				} else {
					this_required = true;
				}
			}
			if (dependTest.equals(FIELD_TEST_NOTNULL)) {
				if ((dependVal != null) && (dependVal.length() > 0)) {
					this_required = true;
				} else {
					this_required = false;
				}
			}
			if (dependTest.equals(FIELD_TEST_EQUAL)) {
				this_required = dependTestValue.equalsIgnoreCase(dependVal);
			}
			if (fieldJoin.equalsIgnoreCase(AND)) {
				required = required && this_required;
			} else {
				required = required || this_required;
			}
			i++;
		}
		if (required) {
			if ((value != null) && (value.length() > 0)) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	private static Class stringClass = new String().getClass();

	private static boolean isString(Object o) {
		if (o == null)
			return true;
		return (stringClass.isInstance(o));
	}
	
	protected static boolean isDigit(String value){
		for (int i = 0; i < value.length(); i++) {
			char ch = value.charAt(i);
			if(!Character.isDigit(ch)){
				return false;
			}
		}
		return true;
	}
}