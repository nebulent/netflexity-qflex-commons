/*
 *  2004 Netflexity, Ltd. All Rights Reserved.
 * 
 * CONFIDENTIAL BUSINESS INFORMATION
 * 
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND IS NOT TO BE
 * COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY OTHER PURPOSE,
 * UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION IS GIVEN.
 */
package org.netflexity.api.mq.ibm;

import org.netflexity.api.mq.MqMessageHeader;
import org.netflexity.api.mq.ibm.enums.MqMessageHeaderCOAReportOptionTypeEnum;
import org.netflexity.api.mq.ibm.enums.MqMessageHeaderCODReportOptionTypeEnum;
import org.netflexity.api.mq.ibm.enums.MqMessageHeaderExceptionReportOptionTypeEnum;
import org.netflexity.api.mq.ibm.enums.MqMessageHeaderExpirationReportOptionTypeEnum;
import org.netflexity.api.mq.ibm.enums.MqMessageHeaderMessageTypeEnum;
import org.netflexity.api.mq.ibm.enums.MqMessageHeaderPersistenceTypeEnum;
import org.netflexity.api.mq.ibm.enums.MqMessageHeaderSegmentTypeEnum;

import com.ibm.mq.MQMessage;
import com.ibm.mq.constants.CMQC;

/**
 * @author Max Fedorov
 * 
 * Class that defines all properties for MQ message header.
 */
public class IBMMqMessageHeader implements MqMessageHeader{

    private String messageFormat;
    private String userId;
    private Integer codePage;
    private Long putTime;
    private Integer expiry;
    private String correlationId;
    private String groupId;
    private String applicationId;
    private String applicationOrigin;
    private String putApplicationName;
    private String replyToQmanagerName;
    private String replyToQueueName;
    private String accountingToken;
    private Integer messageFlags;
    private Integer originalLength;
    private Integer backoutCount;
    private Integer offset;
    private Integer priority;
    private Integer messageSequenceNumber;
    private Integer feedback;
    private Integer characterSet;
    private Integer encoding;
    private MqMessageHeaderMessageTypeEnum messageType;
    private MqMessageHeaderSegmentTypeEnum segment;
    private MqMessageHeaderPersistenceTypeEnum persistence;
    
    // Report options.
    private MqMessageHeaderExceptionReportOptionTypeEnum exception;
    private MqMessageHeaderExpirationReportOptionTypeEnum expiration;
    private MqMessageHeaderCOAReportOptionTypeEnum coa;
    private MqMessageHeaderCODReportOptionTypeEnum cod;
    private boolean pan;
    private boolean nan;
    private boolean passCorrelationId;
    
    /**
     * Default constructor.
     */
    public IBMMqMessageHeader() {
    }

    /**
     * Constructor that will initialize the object based on MQ message.
     * @param msg
     */
    public IBMMqMessageHeader(MQMessage msg) {
        messageFormat = msg.format;
        userId = msg.userId;
        codePage = new Integer(msg.characterSet);
        putTime = (msg.putDateTime != null) ? new Long(msg.putDateTime.getTime().getTime()) : null;
        expiry = new Integer(msg.expiry);
        correlationId = IBMPCFUtil.toHex(msg.correlationId);
        groupId = IBMPCFUtil.toHex(msg.groupId);
        applicationId = msg.applicationIdData;
        applicationOrigin = msg.applicationOriginData;
        putApplicationName = msg.putApplicationName;
        replyToQmanagerName = msg.replyToQueueManagerName;
        replyToQueueName = msg.replyToQueueName;
        accountingToken = IBMPCFUtil.toHex(msg.accountingToken);
        originalLength = new Integer(msg.originalLength);
        messageFlags = new Integer(msg.messageFlags);
        backoutCount = new Integer(msg.backoutCount);
        feedback = new Integer(msg.feedback);
        characterSet = new Integer(msg.characterSet);
        encoding = new Integer(msg.encoding);
        offset = new Integer(msg.offset);
        priority = new Integer(msg.priority);
        messageSequenceNumber = new Integer(msg.messageSequenceNumber);
        persistence = MqMessageHeaderPersistenceTypeEnum.getEnum(msg.persistence);
        messageType = MqMessageHeaderMessageTypeEnum.getEnum(msg.messageType);
        setReportOptions(msg);
	
    }

    /**
     * Helper method to populate all report-related properties.
     * @param msg
     */
    protected void setReportOptions(MQMessage msg) {
        int report = msg.report;
        
        // Set report options.
        cod = MqMessageHeaderCODReportOptionTypeEnum.getEnum((report & CMQC.MQRO_COD_WITH_FULL_DATA));
        coa = MqMessageHeaderCOAReportOptionTypeEnum.getEnum((report & CMQC.MQRO_COA_WITH_FULL_DATA));
        exception = MqMessageHeaderExceptionReportOptionTypeEnum.getEnum((report & CMQC.MQRO_EXCEPTION_WITH_FULL_DATA));
        expiration = MqMessageHeaderExpirationReportOptionTypeEnum.getEnum((report & CMQC.MQRO_EXPIRATION_WITH_FULL_DATA));
        
        // Set PAN.
        int panTest = report & CMQC.MQRO_PAN;
        pan = (panTest == CMQC.MQRO_PAN);

        // Set NAN.
        int nanTest = report & CMQC.MQRO_NAN;
        nan = (nanTest == CMQC.MQRO_NAN);

        // Set pass correl.
        int passCorrelTest = report & CMQC.MQRO_PASS_CORREL_ID;
        passCorrelationId = (passCorrelTest == CMQC.MQRO_PASS_CORREL_ID);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getAccountingToken()
     */
    @Override
    public String getAccountingToken() {
        return accountingToken;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setAccountingToken(java.lang.String)
     */
    @Override
    public void setAccountingToken(String accounting_token) {
        this.accountingToken = IBMPCFUtil.toHex(accounting_token.getBytes());
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getApplicationId()
     */
    @Override
    public String getApplicationId() {
        return applicationId;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setApplicationId(java.lang.String)
     */
    @Override
    public void setApplicationId(String application_id) {
        this.applicationId = application_id;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getApplicationOrigin()
     */
    @Override
    public String getApplicationOrigin() {
        return applicationOrigin;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setApplicationOrigin(java.lang.String)
     */
    @Override
    public void setApplicationOrigin(String applicationOrigin) {
        this.applicationOrigin = applicationOrigin;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getBackoutCount()
     */
    @Override
    public Integer getBackoutCount() {
        return backoutCount;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setBackoutCount(java.lang.Integer)
     */
    @Override
    public void setBackoutCount(Integer backout_count) {
        this.backoutCount = backout_count;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getCoa()
     */
    @Override
    public MqMessageHeaderCOAReportOptionTypeEnum getCoa() {
        return coa;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setCoa(org.netflexity.api.mq.enums.MqMessageHeaderReportOptionEnum)
     */
    @Override
    public void setCoa(MqMessageHeaderCOAReportOptionTypeEnum coa) {
        this.coa = coa;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getCod()
     */
    @Override
    public MqMessageHeaderCODReportOptionTypeEnum getCod() {
        return cod;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setCod(org.netflexity.api.mq.enums.MqMessageHeaderReportOptionEnum)
     */
    @Override
    public void setCod(MqMessageHeaderCODReportOptionTypeEnum cod) {
        this.cod = cod;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getCodePage()
     */
    @Override
    public Integer getCodePage() {
        return codePage;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setCodePage(java.lang.Integer)
     */
    @Override
    public void setCodePage(Integer code_page) {
        this.codePage = code_page;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getCorrelationId()
     */
    @Override
    public String getCorrelationId() {
        return correlationId;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setCorrelationId(java.lang.String)
     */
    @Override
    public void setCorrelationId(String correl_id) {
        this.correlationId = IBMPCFUtil.toHex(correl_id.getBytes());
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getException()
     */
    @Override
    public MqMessageHeaderExceptionReportOptionTypeEnum getException() {
        return exception;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setException(org.netflexity.api.mq.enums.MqMessageHeaderReportOptionEnum)
     */
    @Override
    public void setException(MqMessageHeaderExceptionReportOptionTypeEnum exception) {
        this.exception = exception;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getExpiration()
     */
    @Override
    public MqMessageHeaderExpirationReportOptionTypeEnum getExpiration() {
        return expiration;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setExpiration(org.netflexity.api.mq.enums.MqMessageHeaderReportOptionEnum)
     */
    @Override
    public void setExpiration(MqMessageHeaderExpirationReportOptionTypeEnum expiration) {
        this.expiration = expiration;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getExpiry()
     */
    @Override
    public Integer getExpiry() {
        return expiry;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setExpiry(java.lang.Integer)
     */
    @Override
    public void setExpiry(Integer expiry) {
        this.expiry = expiry;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getGroupId()
     */
    @Override
    public String getGroupId() {
        return groupId;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setGroupId(java.lang.String)
     */
    @Override
    public void setGroupId(String group_id) {
        this.groupId = IBMPCFUtil.toHex(group_id.getBytes());
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getMessageFormat()
     */
    @Override
    public String getMessageFormat() {
        return messageFormat;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setMessageFormat(java.lang.String)
     */
    @Override
    public void setMessageFormat(String mq_message_format) {
        this.messageFormat = mq_message_format;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getMessageType()
     */
    @Override
    public MqMessageHeaderMessageTypeEnum getMessageType() {
        return messageType;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setMessageType(java.lang.String)
     */
    @Override
    public void setMessageType(MqMessageHeaderMessageTypeEnum msg_type) {
        this.messageType = msg_type;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#isNan()
     */
    @Override
    public boolean isNan() {
        return nan;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setNan(boolean)
     */
    @Override
    public void setNan(boolean nan) {
        this.nan = nan;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getOffset()
     */
    @Override
    public Integer getOffset() {
        return offset;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setOffset(java.lang.Integer)
     */
    @Override
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#isPan()
     */
    @Override
    public boolean isPan() {
        return pan;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setPan(boolean)
     */
    @Override
    public void setPan(boolean pan) {
        this.pan = pan;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#isPassCorrelationId()
     */
    @Override
    public boolean isPassCorrelationId() {
        return passCorrelationId;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setPassCorrelationId(boolean)
     */
    @Override
    public void setPassCorrelationId(boolean passCorrelationId) {
        this.passCorrelationId = passCorrelationId;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getPersistence()
     */
    @Override
    public MqMessageHeaderPersistenceTypeEnum getPersistence() {
        return persistence;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setPersistence(org.netflexity.api.mq.enums.MqMessageHeaderPersistenceTypeEnum)
     */
    @Override
    public void setPersistence(MqMessageHeaderPersistenceTypeEnum persistence) {
        this.persistence = persistence;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getPutApplicationName()
     */
    @Override
    public String getPutApplicationName() {
        return putApplicationName;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setPutApplicationName(java.lang.String)
     */
    @Override
    public void setPutApplicationName(String put_application_nm) {
        this.putApplicationName = put_application_nm;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getPutTime()
     */
    @Override
    public Long getPutTime() {
        return putTime;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setPutTime(java.lang.Long)
     */
    @Override
    public void setPutTime(Long putTime) {
        this.putTime = putTime;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getReplyToQueueName()
     */
    @Override
    public String getReplyToQueueName() {
        return replyToQueueName;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setReplyToQueueName(java.lang.String)
     */
    @Override
    public void setReplyToQueueName(String reply_to_q) {
        this.replyToQueueName = reply_to_q;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getReplyToQmanagerName()
     */
    @Override
    public String getReplyToQmanagerName() {
        return replyToQmanagerName;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setReplyToQmanagerName(java.lang.String)
     */
    @Override
    public void setReplyToQmanagerName(String reply_to_qm) {
        this.replyToQmanagerName = reply_to_qm;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getSegment()
     */
    @Override
    public MqMessageHeaderSegmentTypeEnum getSegment() {
        return segment;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setSegment(org.netflexity.api.mq.enums.MqMessageHeaderSegmentTypeEnum)
     */
    @Override
    public void setSegment(MqMessageHeaderSegmentTypeEnum segment) {
        this.segment = segment;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getMessageSequenceNumber()
     */
    @Override
    public Integer getMessageSequenceNumber() {
        return messageSequenceNumber;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setMessageSequenceNumber(java.lang.Integer)
     */
    @Override
    public void setMessageSequenceNumber(Integer seqno) {
        this.messageSequenceNumber = seqno;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#getUserId()
     */
    @Override
    public String getUserId() {
        return userId;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.browser.MqMessageHeader#setUserId(java.lang.String)
     */
    @Override
    public void setUserId(String user_id) {
        this.userId = user_id;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqMessageHeader#getPriority()
     */
    @Override
    public Integer getPriority() {
        return priority;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqMessageHeader#setPriority(java.lang.Integer)
     */
    @Override
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqMessageHeader#getMessageFlags()
     */
    @Override
    public Integer getMessageFlags() {
        return messageFlags;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqMessageHeader#setMessageFlags(java.lang.Integer)
     */
    @Override
    public void setMessageFlags(Integer messageFlags) {
        this.messageFlags = messageFlags;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqMessageHeader#getOriginalLength()
     */
    @Override
    public Integer getOriginalLength() {
        return originalLength;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqMessageHeader#setOriginalLength(java.lang.Integer)
     */
    @Override
    public void setOriginalLength(Integer originalLength) {
        this.originalLength = originalLength;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqMessageHeader#getCharacterSet()
     */
    @Override
    public Integer getCharacterSet() {
        return characterSet;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqMessageHeader#setCharacterSet(java.lang.Integer)
     */
    @Override
    public void setCharacterSet(Integer characterSet) {
        this.characterSet = characterSet;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqMessageHeader#getEncoding()
     */
    @Override
    public Integer getEncoding() {
        return encoding;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqMessageHeader#setEncoding(java.lang.Integer)
     */
    @Override
    public void setEncoding(Integer encoding) {
        this.encoding = encoding;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqMessageHeader#getFeedback()
     */
    @Override
    public Integer getFeedback() {
        return feedback;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.mq.MqMessageHeader#setFeedback(java.lang.Integer)
     */
    @Override
    public void setFeedback(Integer feedback) {
        this.feedback = feedback;
    }
}