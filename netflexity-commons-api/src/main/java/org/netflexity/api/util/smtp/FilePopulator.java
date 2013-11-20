/*
 *  2005 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
 */
package org.netflexity.api.util.smtp;

import java.io.File;
import java.io.InputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 * @author MAX
 *
 */
public class FilePopulator implements MimeMultipartPopulator {

    private File file;
    private InputStream is;
    
    /**
     * @param file
     * @param is
     */
    public FilePopulator(File file, InputStream is) {
        this.file = file;
        this.is = is;
    }

    /* (non-Javadoc)
     * @see org.netflexity.api.util.smtp.MimeMultipartPopulator#populate(javax.mail.internet.MimeMultipart)
     */
    public void populate(MimeMultipart mimeParts) throws MessagingException {
        File f = new File(file.getPath());
        DataSource byteDataSource = new ByteArrayDataSource(is, file.getName());
        MimeBodyPart mimePart = new MimeBodyPart();
        mimePart.setDataHandler(new DataHandler(byteDataSource));
        mimePart.setFileName(f.getName());
        mimeParts.addBodyPart(mimePart);
    }
}
