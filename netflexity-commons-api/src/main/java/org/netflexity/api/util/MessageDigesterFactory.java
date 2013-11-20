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
package org.netflexity.api.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

/**
 * @author MFedorov
 *
 * This is a singleton that cyphers text in a thread-safe manner.
 */
public final class MessageDigesterFactory {
	
	private static MessageDigesterFactory instance = new MessageDigesterFactory();

	/**
	 * @see java.lang.Object#Object()
	 */
	private MessageDigesterFactory() {
	}
	
	/**
	 * Method encrypt.
	 * @param plaintext
	 * @return String
	 * @throws SystemUnavailableException
	 */
	public synchronized String createDigest(String plaintext) throws MessageDigesterException{
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} 
        catch (NoSuchAlgorithmException e) {
			throw new MessageDigesterException(e.getMessage(), e);
		}
        
		try {
			md.update(plaintext.getBytes("UTF-8"));
		} 
        catch (UnsupportedEncodingException e) {
			throw new MessageDigesterException(e.getMessage(), e);
		}
        
        // Digest a message.
		byte raw[] = md.digest();
		return (new BASE64Encoder()).encode(raw);
	}
	
	/**
	 * Method getInstance.
	 * @return MessageDigesterFactory
	 */
	public static MessageDigesterFactory getInstance(){
		return instance;
	}
}