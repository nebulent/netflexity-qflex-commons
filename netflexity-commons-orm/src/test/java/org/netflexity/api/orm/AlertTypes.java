package org.netflexity.api.orm;


/**
 * @author 
 * 
 * AlertTypes maps database table &quot;ALERT_TYPES&quot; to java bean.
 */
public class AlertTypes{
	
	/*
	* Constants.
	*/
    public static final String ALERT_TYPE_ID_COL = "alert_type_id";
    public static final String DISPLAY_SEQ_NO_COL = "display_seq_no";
    public static final String DESCRIPTION_COL = "description";
    
    /*
     * Proeprties.
     */
    private Long alert_type_id;
    private Integer display_seq_no;
    private String description;
    
    /**
     * @return Returns the alert_type_id.
     */
    public Long getAlert_type_id() {
        return alert_type_id;
    }
    /**
     * @param alert_type_id The alert_type_id to set.
     */
    public void setAlert_type_id(Long alert_type_id) {
        this.alert_type_id = alert_type_id;
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