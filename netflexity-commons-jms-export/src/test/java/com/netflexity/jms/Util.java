package com.netflexity.jms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.netflexity.jms.util.DataType;
import com.netflexity.jms.util.XmlMessageConvertor;

public class Util {

	final static XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
	
	public static Document toDocument(String xml) 
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return builder.parse(new ByteArrayInputStream(xml.getBytes()));
	}
	
	public static String getPropertyValue(String propertyName, Element xml) {
		NodeList nodes = xml.getElementsByTagName("property");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element)node;
				if (element.getAttribute("name").equals(propertyName)) {
					return element.getAttribute("value");
				}
			}
		}
		return null;
	}
	
	public static DataType getPropertyType(String propertyName, Element xml) {
		NodeList nodes = xml.getElementsByTagName("property");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element)node;
				if (element.getAttribute("name").equals(propertyName)) {
					return DataType.valueOf(element.getAttribute("type"));
				}
			}
		}
		return null;
	}
	
	public static String getTextContent(String tagName, Element xml) {
		NodeList list = xml.getElementsByTagName(tagName);
		return list.item(0).getTextContent();
	}
	
	public static void readmessage(XmlMessageConvertor convertor, Message message, String xml) throws XMLStreamException, JMSException {
		XMLStreamReader xmlReader = xmlFactory.createXMLStreamReader(new StringReader(xml));
		//System.out.println(xml);
		while (xmlReader.hasNext()) {
			xmlReader.next();
			if (xmlReader.getEventType() == XMLEvent.START_ELEMENT 
					&& xmlReader.getName().getLocalPart().equals("message")) {
				break;
			}
			System.out.println(xmlReader.getEventType());
		}
		convertor.read(xmlReader, message);
		
	}
}
