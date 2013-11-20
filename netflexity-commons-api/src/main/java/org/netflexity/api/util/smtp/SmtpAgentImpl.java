/*
 *  2004 Netflexity, Ltd. All Rights Reserved.
 * 
 * CONFIDENTIAL BUSINESS INFORMATION
 * 
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND IS NOT TO BE
 * COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY OTHER PURPOSE,
 * UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION IS GIVEN.
 */
package org.netflexity.api.util.smtp;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.netflexity.api.util.StringConstants;

/**
 * SmtpAgentImpl.java
 *  
 */
public class SmtpAgentImpl implements SmtpAgent {

    public static final String MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    
    private Session session;
    private List fileAttachments = new LinkedList();
    private MimeMultipartPopulator populator;
    
    /**
     * @param host
     */
    public SmtpAgentImpl(String host) {
        this(host, null, null);
    }

    /**
     * @param host
     * @param username
     * @param password
     */
    public SmtpAgentImpl(String host, final String username, final String password) {
        Properties properties = System.getProperties();
        properties.put(MAIL_SMTP_HOST, host);

        Authenticator authenticator = null;
        if (StringUtils.isNotEmpty(username)) {
            properties.put(MAIL_SMTP_AUTH, "true");
            
            authenticator = new Authenticator() {
                /* (non-Javadoc)
                 * @see javax.mail.Authenticator#getPasswordAuthentication()
                 */
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            };
        }

        // Create mail session.
        session = Session.getDefaultInstance(properties, authenticator);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.smtp.impl.ISmtpAgent#sendMessage(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void sendMessage(String fromAddrList, String toAddressList, String subject, String content) throws MessagingException {
        sendMessage(fromAddrList, null, toAddressList, subject, content);
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.smtp.impl.ISmtpAgent#sendMessage(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void sendMessage(String fromAddr, String ccAddressList, String toAddressList, String subject, String content) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(fromAddr));
        mimeMessage.setSubject(subject);
        mimeMessage.setSentDate(new Date());

        // Add TO recepients.
        if(StringUtils.isNotBlank(toAddressList)){
	        String toAddresses[] = StringUtils.split(toAddressList, StringConstants.SEMICOLON);
	        InternetAddress toEmails[] = new InternetAddress[toAddresses.length];
	        for (int i = 0; i < toAddresses.length; i++) {
	            toEmails[i] = new InternetAddress(toAddresses[i]);
	        }
	        mimeMessage.setRecipients(javax.mail.Message.RecipientType.TO, toEmails);
        }

        // Add CC recepients.
        if(StringUtils.isNotEmpty(ccAddressList)){
            String ccAddresses[] = StringUtils.split(ccAddressList, StringConstants.SEMICOLON);
            InternetAddress toEmails[] = new InternetAddress[ccAddresses.length];
	        for (int i = 0; i < ccAddresses.length; i++) {
	            toEmails[i] = new InternetAddress(ccAddresses[i]);
	        }
            mimeMessage.setRecipients(javax.mail.Message.RecipientType.CC, toEmails);
        }
        
        // Collection of parts.
        MimeMultipart mimeParts = new MimeMultipart();

        // Add mail content as a body part.
        MimeBodyPart mimePart = new MimeBodyPart();
        mimePart.setText(content);
        mimeParts.addBodyPart(mimePart);

        // Add file attachments as body parts.
        for (Iterator iter = fileAttachments.iterator(); iter.hasNext();) {
            File f = (File) iter.next();
            FileDataSource fileDataSource = new FileDataSource(f);
            mimePart = new MimeBodyPart();
            mimePart.setDataHandler(new DataHandler(fileDataSource));
            mimePart.setFileName(f.getName());
            mimeParts.addBodyPart(mimePart);
        }

        // Callback for populator.
        if(populator != null){
            populator.populate(mimeParts);
        }
        
        // Add all the parts to message.
        mimeMessage.setContent(mimeParts);
        mimeMessage.saveChanges();

        // Send mail.
        Transport.send(mimeMessage);
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.smtp.impl.ISmtpAgent#getFileAttachments()
     */
    public List getFileAttachments() {
        return fileAttachments;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.smtp.impl.ISmtpAgent#setFileAttachment(java.io.File)
     */
    public void setFileAttachment(File fileAttachment) {
        if (fileAttachment != null && fileAttachment.exists()) {
            fileAttachments.add(fileAttachment);
        }
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.smtp.impl.ISmtpAgent#getPopulator()
     */
    public MimeMultipartPopulator getPopulator() {
        return populator;
    }
    
    /* (non-Javadoc)
     * @see org.netflexity.api.util.smtp.impl.ISmtpAgent#setPopulator(org.netflexity.api.util.smtp.MimeMultipartPopulator)
     */
    public void setPopulator(MimeMultipartPopulator populator) {
        this.populator = populator;
    }
}

