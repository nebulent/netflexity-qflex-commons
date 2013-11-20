package com.netflexity.jms.util;

import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import com.netflexity.jms.MessageType;
import com.netflexity.jms.SnapshotAppender;

public class MapMessageConvertor extends XmlMessageConvertor<MapMessage> {

	@Override
	public void appendContent(MapMessage message, SnapshotAppender result) throws JMSException {

		result.append("<content>");
		result.append("<properties>\n");
		Enumeration<String> propertyNames = message.getMapNames();
		while (propertyNames.hasMoreElements()) {

			//starting property tag
			String name = propertyNames.nextElement();
			result.append("<property name=\"").append(name).append("\" value=\"");

			//appending value
			Object propertyValue = message.getObject(name);
			String stringValue;
			if (propertyValue == null) {
				//do nothing
				//stringValue = "";
			} else if (propertyValue instanceof String) {
				//encoding string in order not to cause xml malfunction :)
				encodeString((String)propertyValue, result);
			} else if (propertyValue instanceof byte[]) {
				byte[] array = (byte[])propertyValue;
				result.append(getEncoder().encode(array));
			} else {
				stringValue = propertyValue.toString();
				result.append(stringValue);
			}

			//closing property tag
			result.append("\" type=\"" 
					+ DataType.getDataTypeForJavaType(propertyValue.getClass()) + "\"/>\n");
		}
		result.append("</properties>");
		result.append("</content>");

	}

	@Override
	public MessageType getMessageType() {
		return MessageType.MAP;
	}

	@Override
	protected void parseMessageContent(XMLStreamReader xmlReader, MapMessage message) throws JMSException {
		try {
			while (xmlReader.hasNext()) {
				xmlReader.next();
				switch (xmlReader.getEventType()) {
				case XMLEvent.START_ELEMENT:
					if( xmlReader.getName().getLocalPart().equals("property")) {
						parseMapMessageProperty(xmlReader, message);
					} 
					break;

				case XMLEvent.END_ELEMENT:
					if( xmlReader.getName().getLocalPart().equals("content")) {
						return;
					} 
					break;

				default:
					break;
				}

			}
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}
	}

	private void parseMapMessageProperty(XMLStreamReader xmlReader, MapMessage message) throws JMSException {
		DataType type = DataType.valueOf(xmlReader.getAttributeValue(null, "type"));
		String propertyName = xmlReader.getAttributeValue(null, "name");
		String propertyValue = xmlReader.getAttributeValue(null, "value");

		switch (type) {
		case BOOLEAN:
			message.setBoolean(propertyName, Boolean.parseBoolean(propertyValue));
			return;

		case BYTE:
			message.setByte(propertyName, Byte.parseByte(propertyValue));
			return;

		case DOUBLE:
			message.setDouble(propertyName, Double.parseDouble(propertyValue));
			return;

		case FLOAT:
			message.setFloat(propertyName, Float.parseFloat(propertyValue));
			return;

		case INT:
			message.setInt(propertyName, Integer.parseInt(propertyValue));
			return;

		case LONG:
			message.setLong(propertyName, Long.parseLong(propertyValue));
			return;

		case SHORT:
			message.setShort(propertyName, Short.parseShort(propertyValue));
			return;

		case STRING:
			message.setString(propertyName, decodeString(propertyValue));
			return;

		case BYTES_ARRAY:
			message.setBytes(propertyName, getDecoder().decodeBuffer(propertyValue));
			return;
			
		default:
			throw new RuntimeException("message does not have property of type " + type);
		}
	}

}
