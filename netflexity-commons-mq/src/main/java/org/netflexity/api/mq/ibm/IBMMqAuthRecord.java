/*
 *  2008 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.mq.ibm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MAX
 *
 */
public class IBMMqAuthRecord extends AbstractIBMMqObject implements Serializable{

    private int objectType;
    private String profileName;
    private String qmanagerName;
    private String entityName;
    private int entityType;
    private List operations = new ArrayList();
    
    /**
     * @return the objectType
     */
    public int getObjectType() {
        return objectType;
    }
    /**
     * @param objectType the objectType to set
     */
    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }
    /**
     * @return the profileName
     */
    public String getProfileName() {
        return profileName;
    }
    /**
     * @param profileName the profileName to set
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
    /**
     * @return the qmanagerName
     */
    public String getQmanagerName() {
        return qmanagerName;
    }
    /**
     * @param qmanagerName the qmanagerName to set
     */
    public void setQmanagerName(String qmanagerName) {
        this.qmanagerName = qmanagerName;
    }
    /**
     * @return the entityName
     */
    public String getEntityName() {
        return entityName;
    }
    /**
     * @param entityName the entityName to set
     */
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
    /**
     * @return the entityType
     */
    public int getEntityType() {
        return entityType;
    }
    /**
     * @param entityType the entityType to set
     */
    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }
    /**
     * @return the operations
     */
    public List getOperations() {
        return operations;
    }
    /**
     * @param operations the operations to set
     */
    public void setOperations(List operations) {
        this.operations = operations;
    }
}
