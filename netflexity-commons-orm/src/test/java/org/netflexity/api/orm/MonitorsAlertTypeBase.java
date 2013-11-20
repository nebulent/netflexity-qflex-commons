package org.netflexity.api.orm;

/**
 * @author 
 * 
 * MonitorsAlertTypeBase.java maps database table &quot;monitors_alert_types&quot; to java bean.
 */
public class MonitorsAlertTypeBase{
	
	/*
	* Constants.
	*/
    public static final String MONITORS_ALERT_TYPES_ID_COL = "monitors_alert_types_id";
    public static final String MONITOR_ID_COL = "monitor_id";
    public static final String MONITOR_TEMPLATE_ID_COL = "monitor_template_id";
    public static final String ALERT_TYPE_ID_COL = "alert_type_id";
    public static final String MODIFIED_TM_COL = "modified_tm";
    public static final String MODIFIED_BY_COL = "modified_by";
    public static final String TABLE_NAME = "monitors_alert_types";
    
    /*
    * Properties.
    */
    private java.lang.Long monitors_alert_types_id;
    private java.lang.Long monitor_id;
    private java.lang.Long monitor_template_id;
    private java.lang.Long alert_type_id;
    private java.lang.Long modified_tm;
    private java.lang.String modified_by;
    
    /**
     * @param descriptor
     */
    public MonitorsAlertTypeBase() {
    }
    
    /**
     * Method getMonitors_alert_types_id.
     * @return
     */
    public java.lang.Long getMonitors_alert_types_id() {
        return monitors_alert_types_id;
    }
	
	/**
	 * Method setMonitors_alert_types_id.
	 * @param monitors_alert_types_id
	 */
    public void setMonitors_alert_types_id(java.lang.Long monitors_alert_types_id) {
        this.monitors_alert_types_id = monitors_alert_types_id;
    }
    
    /**
     * Method getMonitor_id.
     * @return
     */
    public java.lang.Long getMonitor_id() {
        return monitor_id;
    }
	
	/**
	 * Method setMonitor_id.
	 * @param monitor_id
	 */
    public void setMonitor_id(java.lang.Long monitor_id) {
        this.monitor_id = monitor_id;
    }
    
    /**
     * Method getMonitor_template_id.
     * @return
     */
    public java.lang.Long getMonitor_template_id() {
        return monitor_template_id;
    }
	
	/**
	 * Method setMonitor_template_id.
	 * @param monitor_template_id
	 */
    public void setMonitor_template_id(java.lang.Long monitor_template_id) {
        this.monitor_template_id = monitor_template_id;
    }
    
    /**
     * Method getAlert_type_id.
     * @return
     */
    public java.lang.Long getAlert_type_id() {
        return alert_type_id;
    }
	
	/**
	 * Method setAlert_type_id.
	 * @param alert_type_id
	 */
    public void setAlert_type_id(java.lang.Long alert_type_id) {
        this.alert_type_id = alert_type_id;
    }
    
    /**
     * Method getModified_tm.
     * @return
     */
    public java.lang.Long getModified_tm() {
        return modified_tm;
    }
	
	/**
	 * Method setModified_tm.
	 * @param modified_tm
	 */
    public void setModified_tm(java.lang.Long modified_tm) {
        this.modified_tm = modified_tm;
    }
    
    /**
     * Method getModified_by.
     * @return
     */
    public java.lang.String getModified_by() {
        return modified_by;
    }
	
	/**
	 * Method setModified_by.
	 * @param modified_by
	 */
    public void setModified_by(java.lang.String modified_by) {
        this.modified_by = modified_by;
    }
    
}