package org.netflexity.api.orm;


/**
 * @author 
 * 
 * AlertStatusTypes maps database table &quot;ALERT_STATUS_TYPES&quot; to java bean.
 */
public class AlertStatusTypes{
	
	/*
	* Constants.
	*/
    public static final String ALERT_STATUS_TYPE_ID_COL = "alert_status_type_id";
    public static final String DISPLAY_SEQ_NO_COL = "display_seq_no";
    public static final String DESCRIPTION_COL = "description";
    
    /*
     * Properties.
     */
    private Long alert_status_type_id;
    private Integer display_seq_no;
    private String description;
    
    /**
     * @return Returns the alert_status_type_id.
     */
    public Long getAlert_status_type_id() {
        return alert_status_type_id;
    }
    /**
     * @param alert_status_type_id The alert_status_type_id to set.
     */
    public void setAlert_status_type_id(Long alert_status_type_id) {
        this.alert_status_type_id = alert_status_type_id;
    }
    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return Returns the display_seq_no.
     */
    public Integer getDisplay_seq_no() {
        return display_seq_no;
    }
    /**
     * @param display_seq_no The display_seq_no to set.
     */
    public void setDisplay_seq_no(Integer display_seq_no) {
        this.display_seq_no = display_seq_no;
    }
}