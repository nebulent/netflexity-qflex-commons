package com.netflexity.jms.util;

import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import com.netflexity.jms.MessageType;
import com.netflexity.jms.SnapshotAppender;
import com.netflexity.util.BASE64Decoder;
import com.netflexity.util.BASE64Encoder;

public abstract class XmlMessageConvertor<M extends Message> implements MessageConvertor<M, SnapshotAppender, XMLStreamReader> {

    protected static int ENCODE_CHUNK_SIZE = 1000000;
    private BASE64Encoder encoder = new BASE64Encoder();
    private BASE64Decoder decoder = new BASE64Decoder();

    public void write(SnapshotAppender result, M message) throws JMSException {
        //Converting predefined message attributes to string
        result.append("<message ").append(" type=\"").append(getMessageType().name()).append("\" ").append(" JMSCorrelationID=\"").append(message.getJMSCorrelationID()).append("\" ").append(" JMSDeliveryMode=\"").append(message.getJMSDeliveryMode()).append("\" ").append(" JMSExpiration=\"").append(message.getJMSExpiration()).append("\" ").append(" JMSMessageID=\"").append(message.getJMSMessageID()).append("\" ").append(" JMSPriority=\"").append(message.getJMSPriority()).append("\" ").append(" JMSRedelivered=\"").append(message.getJMSRedelivered()).append("\" ") //TODO: realy dont know how to handl this attribute: message.getJMSReplyTo();
                //.append(" JMSReplyTo=\"").append(message.getJMSReplyTo()).append("\" ")
                .append(" JMSTimestamp=\"").append(message.getJMSTimestamp()).append("\" ").append(" JMSType=\"").append(message.getJMSType()).append("\" ").append(" >\n") //Strarting properties section
                .append("<properties>\n");
        Enumeration<String> propertyNames = message.getPropertyNames();
        while (propertyNames.hasMoreElements()) {
            String name = propertyNames.nextElement();
            Object propertyValue = message.getObjectProperty(name);
            String stringValue;
            if (propertyValue == null) {
                stringValue = "";
            } else if (propertyValue instanceof String) {
                //encoding string in order not to cause xml malfunction :)
                stringValue = encoder.encode(((String) propertyValue).getBytes());
            } else {
                //since all other possible value types are numbers
                //this should work perfectly
                stringValue = propertyValue.toString();
            }
            result.append("<property name=\"").append(name).append("\" value=\"").append(stringValue).append("\" type=\""
                    + DataType.getDataTypeForJavaType(propertyValue.getClass()).name() + "\"/>\n");
        }
        result.append("</properties>");
        appendContent(message, result);
        result.append("</message>");
    }

    public void read(XMLStreamReader xmlReader, M message) throws JMSException {

        if (xmlReader.getEventType() != XMLEvent.START_ELEMENT
                || !xmlReader.getName().getLocalPart().equals("message")) {
            throw new IllegalArgumentException("Xml reader is not pointing to message start tag");
        }

        parseMessageAttributes(xmlReader, message);

        try {
            while (xmlReader.hasNext()) {
                xmlReader.next();
                switch (xmlReader.getEventType()) {
                    case XMLEvent.START_ELEMENT:
                        if (xmlReader.getName().getLocalPart().equals("property")) {
                            parseMessageProperty(xmlReader, message);
                        } else if (xmlReader.getName().getLocalPart().equals("content")) {
                            parseMessageContent(xmlReader, message);
                        }
                        break;

                    case XMLEvent.END_ELEMENT:
                        if (xmlReader.getName().getLocalPart().equals("message")) {
                            return;
                        }

                    default:
                        break;
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }

    }

    public abstract MessageType getMessageType();

    protected abstract void appendContent(M message, SnapshotAppender result) throws JMSException;

    protected abstract void parseMessageContent(XMLStreamReader xmlReader, M message) throws JMSException;

    protected void encodeString(String text, SnapshotAppender result) {
        int length = text.length();
        int chunkCount = length / ENCODE_CHUNK_SIZE;
        BASE64Encoder encoder = getEncoder();
        for (int i = 0; i < chunkCount; i++) {
            result.append(encoder.encode(text.substring(i * ENCODE_CHUNK_SIZE, (i + 1) * ENCODE_CHUNK_SIZE).getBytes()));
        }
        result.append(encoder.encode(text.substring(chunkCount * ENCODE_CHUNK_SIZE).getBytes()));
    }

    protected String decodeString(String encodedString) {
        StringBuilder builder = new StringBuilder();
        String[] data = encodedString.split("==");
        for (String string : data) {
            builder.append(new String(decoder.decodeBuffer(string + "==")));
        }
        return builder.toString();
    }

    protected BASE64Encoder getEncoder() {
        return encoder;
    }

    protected BASE64Decoder getDecoder() {
        return decoder;
    }

    private void parseMessageAttributes(XMLStreamReader xmlReader, Message message) throws JMSException {
        message.setJMSCorrelationID(xmlReader.getAttributeValue(null, "JMSCorrelationID"));
        message.setJMSDeliveryMode(Integer.parseInt(xmlReader.getAttributeValue(null, "JMSDeliveryMode")));
        message.setJMSExpiration(Long.parseLong(xmlReader.getAttributeValue(null, "JMSExpiration")));
        message.setJMSMessageID(xmlReader.getAttributeValue(null, "JMSMessageID"));
        message.setJMSPriority(Integer.parseInt(xmlReader.getAttributeValue(null, "JMSPriority")));
        message.setJMSRedelivered(Boolean.parseBoolean(xmlReader.getAttributeValue(null, "JMSRedelivered")));
        //TODO: realy dont know how to handl this attribute: message.getJMSReplyTo();
        //message.setJMSReplyTo(xmlReader.getAttributeValue(null, "JMSReplyTo"));
        message.setJMSTimestamp(Long.parseLong(xmlReader.getAttributeValue(null, "JMSTimestamp")));
        message.setJMSType(xmlReader.getAttributeValue(null, "JMSType"));
    }

    private void parseMessageProperty(XMLStreamReader xmlReader, Message message) /*throws JMSException*/ {
        DataType type = DataType.valueOf(xmlReader.getAttributeValue(null, "type"));
        String propertyName = xmlReader.getAttributeValue(null, "name");
        String propertyValue = xmlReader.getAttributeValue(null, "value");
        try {
            switch (type) {
                case BOOLEAN:
                    message.setBooleanProperty(propertyName, Boolean.parseBoolean(propertyValue));
                    return;

                case BYTE:
                    message.setByteProperty(propertyName, Byte.parseByte(propertyValue));
                    return;

                case DOUBLE:
                    message.setDoubleProperty(propertyName, Double.parseDouble(propertyValue));
                    return;

                case FLOAT:
                    message.setFloatProperty(propertyName, Float.parseFloat(propertyValue));
                    return;

                case INT:
                    message.setIntProperty(propertyName, Integer.parseInt(propertyValue));
                    return;

                case LONG:
                    message.setLongProperty(propertyName, Long.parseLong(propertyValue));
                    return;

                case SHORT:
                    message.setShortProperty(propertyName, Short.parseShort(propertyValue));
                    return;

                case STRING:
                    message.setStringProperty(propertyName, decodeString(propertyValue));
                    return;

                default:
                    throw new RuntimeException("message does not have property of type " + type);
            }
        } catch (JMSException je) {
        }
    }
}
