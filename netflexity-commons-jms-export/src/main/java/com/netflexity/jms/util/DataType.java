package com.netflexity.jms.util;

import java.util.HashMap;
import java.util.Map;

public enum DataType {

	BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BYTES_ARRAY, STRING, BOOLEAN;
	
	private static Map<Object, DataType> conversionTable = new HashMap<Object, DataType>();
	
	static {
		conversionTable.put(Byte.class, BYTE);
		conversionTable.put(Short.class, SHORT);
		conversionTable.put(Integer.class, INT);
		conversionTable.put(Long.class, LONG);
		conversionTable.put(Float.class, FLOAT);
		conversionTable.put(Double.class, DOUBLE);
		conversionTable.put(byte[].class, BYTES_ARRAY);
		conversionTable.put(String.class, STRING);
		conversionTable.put(Boolean.class, BOOLEAN);
	}
	
	public static DataType getDataTypeForJavaType(Class javaType) {
		DataType result = conversionTable.get(javaType);
		if (result == null) {
			throw new RuntimeException("Unknown type: " + javaType);
		}
		return result;
	}
	
	public static Object parse(String data, String type) {
		return parse(data, DataType.valueOf(type));
	}
	
	public static Object parse(String data, DataType type) {
		switch (type) {
		case BYTE:
			return new Byte(data);
		case SHORT:
			return new Short(data);
		case INT:
			return new Integer(data);
		case LONG:
			return new Long(data);
		case FLOAT:
			return new Float(data);
		case DOUBLE:
			return new Double(data);
		case BYTES_ARRAY:
			String[] bytes = data.split(",");
			byte[] result = new byte[bytes.length];
			for (int i = 0; i < bytes.length; i++) {
				result[i] = Byte.parseByte(bytes[i].trim());
			}
			return result;
		case STRING:
			return data;
			
		default:
			throw new RuntimeException("this should not happen");
		}
	}
	
	public static String bytesArrayToString(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		if (bytes.length > 0) {
			builder.append(bytes[0]);
		}
		for (int i = 1; i < bytes.length; i++) {
			builder.append(", ").append(bytes[i]);
		}
		return builder.toString();
	}
}
