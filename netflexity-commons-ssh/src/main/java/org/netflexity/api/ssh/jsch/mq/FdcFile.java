/*
 *  2007 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.ssh.jsch.mq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.netflexity.api.util.StringConstants;

/**
 * @author MAX
 *
 */
public class FdcFile {

    private Date accessDate;
    private Date creationDate;
    private String access;
    private String linksCount;
    private String user;
    private String group;
    private String size;
    private String fileName;
    
    public static final String DATE_FORMAT_PATTERN_FOR_CURRENT_YEAR = "yyyy MMM d HH:mm";
    public static final String DATE_FORMAT_PATTERN_FOR_OTHER_YEARS = "MMM d yyyy";
    
    /**
     * @return the accessDate
     */
    public Date getAccessDate() {
        return accessDate;
    }
    /**
     * @param accessDate the accessDate to set
     */
    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }
    /**
     * @param accessDate
     * @param locale
     * @param formatForCurrentYear
     * @param formatForOtherYears
     * @throws ParseException
     */
    public void setAccessDate(String accessDate, Locale locale, SimpleDateFormat formatForCurrentYear, SimpleDateFormat formatForOtherYears) throws ParseException{
        if (accessDate.indexOf(StringConstants.COLON) != -1) {
            accessDate = Calendar.getInstance(locale).get(Calendar.YEAR) + StringConstants.SPACE + accessDate;
            setAccessDate(formatForCurrentYear.parse(accessDate));
        } 
        else {
            setAccessDate(formatForOtherYears.parse(accessDate));
        }
    }
    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }
    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    /**
     * @return the access
     */
    public String getAccess() {
        return access;
    }
    /**
     * @param access the access to set
     */
    public void setAccess(String access) {
        this.access = access;
    }
    /**
     * @return the group
     */
    public String getGroup() {
        return group;
    }
    /**
     * @param group the group to set
     */
    public void setGroup(String group) {
        this.group = group;
    }
    /**
     * @return the linksCount
     */
    public String getLinksCount() {
        return linksCount;
    }
    /**
     * @param linksCount the linksCount to set
     */
    public void setLinksCount(String linksCount) {
        this.linksCount = linksCount;
    }
    /**
     * @return the size
     */
    public String getSize() {
        return size;
    }
    /**
     * @param size the size to set
     */
    public void setSize(String size) {
        this.size = size;
    }
    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }
    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }
    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return fileName + ": " + accessDate.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        FdcFile object = (FdcFile) obj;
        return fileName.compareTo(object.getFileName()) == 0 && accessDate.equals(object.getAccessDate());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return accessDate.hashCode() + fileName.hashCode();
    }
}
