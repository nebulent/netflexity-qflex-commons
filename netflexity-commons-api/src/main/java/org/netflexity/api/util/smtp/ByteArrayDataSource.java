/*
 *  2004 Netflexity, Ltd. All Rights Reserved.
 *
 * CONFIDENTIAL BUSINESS INFORMATION
 *
 * THIS PROGRAM IS PROPRIETARY INFORMATION OF NETFLEXITY, LTD. AND
 * IS NOT TO BE COPIED, REPRODUCED, LENT, OR DISPOSED OF, NOR USED FOR ANY
 * OTHER PURPOSE, UNLESS THE WRITTEN PERMISSION FROM THE STATED ABOVE CORPORATION
 * IS GIVEN.
*/
package org.netflexity.api.util.smtp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JavaMail DataSource that takes files contents 
 * without physical location.
 */
public class ByteArrayDataSource implements DataSource {
    
    private static Logger logger = LoggerFactory.getLogger(ByteArrayDataSource.class.getPackage().getName());
    
	public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";

	private byte[] data;
    private String type = APPLICATION_OCTET_STREAM;
    private String fileName;
    
    /**
     * @param is
     * @param type
     */
    public ByteArrayDataSource(InputStream is, String fileName) {
        this.fileName = fileName;
        try { 
            ByteArrayOutputStream os = new ByteArrayOutputStream(10240);
		    byte buff[] = new byte[1024];
            int n = -1;
		    while((n = is.read(buff)) != -1){
		        os.write(buff, 0, n);
	        }
		    this.data = os.toByteArray();
        }
        catch (IOException io) {
            logger.error(io.getMessage(), io);
        }
    }

    /**
     * @param data
     * @param type
     */
    public ByteArrayDataSource(byte[] data, String type) {
        this.data = data;
        this.type = type;
    }

    /**
     * @param data
     * @param type
     */
    public ByteArrayDataSource(String data, String type) {
    	this(data.getBytes(), type);
	}

    /* (non-Javadoc)
     * @see javax.activation.DataSource#getInputStream()
     */
    public InputStream getInputStream() throws IOException {
    	if (data == null) throw new IOException("No data found in the stream.");
    	return new ByteArrayInputStream(data);
    }

    /* (non-Javadoc)
     * @see javax.activation.DataSource#getOutputStream()
     */
    public OutputStream getOutputStream() throws IOException {
    	throw new IOException("Unsupported operation.");
    }

    /* (non-Javadoc)
     * @see javax.activation.DataSource#getName()
     */
    public String getName() {
        return fileName;
    }
    
	/* (non-Javadoc)
	 * @see javax.activation.DataSource#getContentType()
	 */
	public String getContentType() {
		return type;
	}
	
	/**
	 * @param fileName The fileName to set.
	 */
	public void setName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * @param type The type to set.
	 */
	public void setContentType(String type) {
		this.type = type;
	}
}
