package org.netflexity.api.orm;

/**
 * @author 
 * 
 * ConfigAttributeTypeBase.java maps database table &quot;config_attribute_types&quot; to java bean.
 */
public class ConfigAttributeTypeBase{
	
	/*
	* Constants.
	*/
    public static final String CONFIG_ATTRIBUTE_TYPE_ID_COL = "config_attribute_type_id";
    public static final String CONFIG_OBJECT_TYPE_ID_COL = "config_object_type_id";
    public static final String DISPLAY_SEQ_NO_COL = "display_seq_no";
    public static final String DESCRIPTION_COL = "description";
    public static final String MQ_ATTRIBUTE_ID_COL = "mq_attribute_id";
    public static final String DATA_TYPE_COL = "data_type";
    public static final String IMMUTABLE_COL = "immutable";
    public static final String REQUIRED_FOR_CHANGE_COL = "required_for_change";
    public static final String TABLE_NAME = "config_attribute_types";
    
    /*
    * Properties.
    */
    private java.lang.Long config_attribute_type_id;
    private java.lang.Long config_object_type_id;
    private java.lang.Integer display_seq_no;
    private java.lang.String description;
    private java.lang.Integer mq_attribute_id;
    private java.lang.Integer data_type;
    private java.lang.String immutable;
    private java.lang.String required_for_change;
    
    /**
     * @param descriptor
     */
    public ConfigAttributeTypeBase() {
    }
    
    /**
     * Method getConfig_attribute_type_id.
     * @return
     */
    public java.lang.Long getConfig_attribute_type_id() {
        return config_attribute_type_id;
    }
	
	/**
	 * Method setConfig_attribute_type_id.
	 * @param config_attribute_type_id
	 */
    public void setConfig_attribute_type_id(java.lang.Long config_attribute_type_id) {
        this.config_attribute_type_id = config_attribute_type_id;
    }
    
    /**
     * Method getConfig_object_type_id.
     * @return
     */
    public java.lang.Long getConfig_object_type_id() {
        return config_object_type_id;
    }
	
	/**
	 * Method setConfig_object_type_id.
	 * @param config_object_type_id
	 */
    public void setConfig_object_type_id(java.lang.Long config_object_type_id) {
        this.config_object_type_id = config_object_type_id;
    }
    
    /**
     * Method getDisplay_seq_no.
     * @return
     */
    public java.lang.Integer getDisplay_seq_no() {
        return display_seq_no;
    }
	
	/**
	 * Method setDisplay_seq_no.
	 * @param display_seq_no
	 */
    public void setDisplay_seq_no(java.lang.Integer display_seq_no) {
        this.display_seq_no = display_seq_no;
    }
    
    /**
     * Method getDescription.
     * @return
     */
    public java.lang.String getDescription() {
        return description;
    }
	
	/**
	 * Method setDescription.
	 * @param description
	 */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    
    /**
     * Method getMq_attribute_id.
     * @return
     */
    public java.lang.Integer getMq_attribute_id() {
        return mq_attribute_id;
    }
	
	/**
	 * Method setMq_attribute_id.
	 * @param mq_attribute_id
	 */
    public void setMq_attribute_id(java.lang.Integer mq_attribute_id) {
        this.mq_attribute_id = mq_attribute_id;
    }
    
    /**
     * Method getData_type.
     * @return
     */
    public java.lang.Integer getData_type() {
        return data_type;
    }
	
	/**
	 * Method setData_type.
	 * @param data_type
	 */
    public void setData_type(java.lang.Integer data_type) {
        this.data_type = data_type;
    }
    
    /**
     * Method getImmutable.
     * @return
     */
    public java.lang.String getImmutable() {
        return immutable;
    }
	
	/**
	 * Method setImmutable.
	 * @param immutable
	 */
    public void setImmutable(java.lang.String immutable) {
        this.immutable = immutable;
    }
    
    /**
     * Method getRequired_for_change.
     * @return
     */
    public java.lang.String getRequired_for_change() {
        return required_for_change;
    }
	
	/**
	 * Method setRequired_for_change.
	 * @param required_for_change
	 */
    public void setRequired_for_change(java.lang.String required_for_change) {
        this.required_for_change = required_for_change;
    }
    
}