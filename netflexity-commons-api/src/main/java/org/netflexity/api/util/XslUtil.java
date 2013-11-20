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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * @author MFedorov
 *
 */
public final class XslUtil {
    /**
         * Method transform
         *
         * @param    sXML                a  String
         * @param    xslFilePath         a  String
         * @param    loader              a  Class
         * @param    os                  an OutputStream
         *
         */
    public static void transform(String xml, String xslFilePath, OutputStream os) {
        try {
            // Create a transform factory instance.
            TransformerFactory tfactory = TransformerFactory.newInstance();

            // Create XSL source
            StreamSource xslSource = new StreamSource(new File(xslFilePath));

            // Create XML source
            StreamSource xmlSource = new StreamSource(new StringReader(xml));

            // Create result
            StreamResult result = new StreamResult(os);

            // Create a transformer for the stylesheet.
            Transformer transformer = tfactory.newTransformer(xslSource);

            // Transform the source XML to System.out.
            transformer.transform(xmlSource, result);
        }
        catch (Throwable t) {
            t.printStackTrace();
            //              IntellecticaBusinessException e = new IntellecticaBusinessException(
            //                  IntellecticaBusinessException.FAILED_TO_TRANSFORM_XML);
            //              e.addParameter(xslFilePath);
            //throw e;
        }
    }

    /**
     * Method transform
     *
     * @param    xml                 a  String
     * @param    xslFilePath         a  String
     * @param    loader              a  Class
     *
     * @return   a String
     *
     */
    public static String transform(String xml, String xslFilePath) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024); // 1KB
        transform(xml, xslFilePath, baos);
        try {
            return baos.toString("UTF8");
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return StringConstants.EMPTY;
    }
}
