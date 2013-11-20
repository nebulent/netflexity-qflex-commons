/*
 *  2006 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.mq;

import java.io.Serializable;

import org.netflexity.api.mq.ibm.enums.MqMessageHeaderCODReportOptionTypeEnum;
import org.netflexity.api.mq.ibm.enums.MqMessageHeaderExceptionReportOptionTypeEnum;
import org.netflexity.api.mq.ibm.enums.MqMessageHeaderExpirationReportOptionTypeEnum;
import org.netflexity.api.mq.ibm.enums.MqMessageHeaderMessageTypeEnum;
import org.netflexity.api.mq.ibm.enums.MqMessageHeaderPersistenceTypeEnum;
import org.netflexity.api.mq.ibm.enums.MqMessageHeaderCOAReportOptionTypeEnum;
import org.netflexity.api.mq.ibm.enums.MqMessageHeaderSegmentTypeEnum;

public interface MqMessageHeader extends Serializable{

    /**
     * @return Returns the accountingToken.
     */
    public String getAccountingToken();

    /**
     * @param accountingToken
     *            The accountingToken to set.
     */
    public void setAccountingToken(String accounting_token);

    /**
     * @return Returns the applicationId.
     */
    public String getApplicationId();

    /**
     * @param applicationId
     *            The applicationId to set.
     */
    public void setApplicationId(String application_id);

    /**
     * @return Returns the applicationOrigin.
     */
    public String getApplicationOrigin();

    /**
     * @param applicationOrigin
     *            The applicationOrigin to set.
     */
    public void setApplicationOrigin(String applicationOrigin);

    /**
     * @return Returns the backoutCount.
     */
    public Integer getBackoutCount();

    /**
     * @param backoutCount
     *            The backoutCount to set.
     */
    public void setBackoutCount(Integer backout_count);

    /**
     * @return Returns the coa.
     */
    public MqMessageHeaderCOAReportOptionTypeEnum getCoa();

    /**
     * @param coa
     *            The coa to set.
     */
    public void setCoa(MqMessageHeaderCOAReportOptionTypeEnum coa);

    /**
     * @return Returns the cod.
     */
    public MqMessageHeaderCODReportOptionTypeEnum getCod();

    /**
     * @param cod
     *            The cod to set.
     */
    public void setCod(MqMessageHeaderCODReportOptionTypeEnum cod);

    /**
     * @return Returns the codePage.
     */
    public Integer getCodePage();

    /**
     * @param codePage
     *            The codePage to set.
     */
    public void setCodePage(Integer code_page);

    /**
     * @return Returns the correlationId.
     */
    public String getCorrelationId();

    /**
     * @param correlationId
     *            The correlationId to set.
     */
    public void setCorrelationId(String correl_id);

    /**
     * @return Returns the except.
     */
    public MqMessageHeaderExceptionReportOptionTypeEnum getException();

    /**
     * @param except
     *            The except to set.
     */
    public void setException(MqMessageHeaderExceptionReportOptionTypeEnum exception);

    /**
     * @return Returns the expire.
     */
    public MqMessageHeaderExpirationReportOptionTypeEnum getExpiration();

    /**
     * @param expire
     *            The expire to set.
     */
    public void setExpiration(MqMessageHeaderExpirationReportOptionTypeEnum expiration);

    /**
     * @return Returns the expiry.
     */
    public Integer getExpiry();

    /**
     * @param expiry
     *            The expiry to set.
     */
    public void setExpiry(Integer expiry);
    
    /**
     * @return Returns the groupId.
     */
    public String getGroupId();

    /**
     * @param groupId
     *            The groupId to set.
     */
    public void setGroupId(String group_id);

    /**
     * @return Returns the messageFormat.
     */
    public String getMessageFormat();

    /**
     * @param messageFormat
     *            The messageFormat to set.
     */
    public void setMessageFormat(String mq_message_format);

    /**
     * @return Returns the messageType.
     */
    public MqMessageHeaderMessageTypeEnum getMessageType();

    /**
     * @param messageType
     *            The messageType to set.
     */
    public void setMessageType(MqMessageHeaderMessageTypeEnum msg_type);

    /**
     * @return Returns the nan.
     */
    public boolean isNan();

    /**
     * @param nan
     *            The nan to set.
     */
    public void setNan(boolean nan);

    /**
     * @return Returns the offset.
     */
    public Integer getOffset();

    /**
     * @param offset
     *            The offset to set.
     */
    public void setOffset(Integer offset);

    /**
     * @return Returns the pan.
     */
    public boolean isPan();

    /**
     * @param pan
     *            The pan to set.
     */
    public void setPan(boolean pan);

    /**
     * @return Returns the passCorrelationId.
     */
    public boolean isPassCorrelationId();

    /**
     * @param passCorrelationId
     *            The passCorrelationId to set.
     */
    public void setPassCorrelationId(boolean passCorrelationId);

    /**
     * @return Returns the persistence.
     */
    public MqMessageHeaderPersistenceTypeEnum getPersistence();

    /**
     * @param persistence
     *            The persistence to set.
     */
    public void setPersistence(MqMessageHeaderPersistenceTypeEnum persistence);

    /**
     * @return
     */
    public Integer getPriority();
    
    /**
     * @param priority The priority to set.
     */
    public void setPriority(Integer priority);
    
    /**
     * @return Returns the putApplicationName.
     */
    public String getPutApplicationName();

    /**
     * @param putApplicationName
     *            The putApplicationName to set.
     */
    public void setPutApplicationName(String put_application_nm);

    /**
     * @return Returns the putTime.
     */
    public Long getPutTime();

    /**
     * @param putTime
     *            The putTime to set.
     */
    public void setPutTime(Long putTime);

    /**
     * @return Returns the replyToQueueName.
     */
    public String getReplyToQueueName();

    /**
     * @param replyToQueueName
     *            The replyToQueueName to set.
     */
    public void setReplyToQueueName(String reply_to_q);

    /**
     * @return Returns the replyToQmanagerName.
     */
    public String getReplyToQmanagerName();

    /**
     * @param replyToQmanagerName
     *            The replyToQmanagerName to set.
     */
    public void setReplyToQmanagerName(String reply_to_qm);

    /**
     * @return Returns the segment.
     */
    public MqMessageHeaderSegmentTypeEnum getSegment();

    /**
     * @param segment
     *            The segment to set.
     */
    public void setSegment(MqMessageHeaderSegmentTypeEnum segment);

    /**
     * @return Returns the messageSequenceNumber.
     */
    public Integer getMessageSequenceNumber();

    /**
     * @param messageSequenceNumber
     *            The messageSequenceNumber to set.
     */
    public void setMessageSequenceNumber(Integer seqno);

    /**
     * @return Returns the userId.
     */
    public String getUserId();

    /**
     * @param userId
     *            The userId to set.
     */
    public void setUserId(String user_id);

    /**
     * @return
     */
    public Integer getMessageFlags();

    /**
     * @param messageFlags The messageFlags to set.
     */
    public void setMessageFlags(Integer messageFlags);

    /**
     * @return Returns the originalLength.
     */
    public Integer getOriginalLength();

    /**
     * @param originalLength The originalLength to set.
     */
    public void setOriginalLength(Integer originalLength);
    
    /**
     * @return Returns the characterSet.
     */
    public Integer getCharacterSet();

    /**
     * @param characterSet The characterSet to set.
     */
    public void setCharacterSet(Integer characterSet);

    /**
     * @return Returns the encoding.
     */
    public Integer getEncoding();

    /**
     * @param encoding The encoding to set.
     */
    public void setEncoding(Integer encoding);

    /**
     * @return Returns the feedback.
     */
    public Integer getFeedback();

    /**
     * @param feedback The feedback to set.
     */
    public void setFeedback(Integer feedback);
}