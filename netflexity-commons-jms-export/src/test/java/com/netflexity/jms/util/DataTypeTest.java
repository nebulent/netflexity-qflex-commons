package com.netflexity.jms.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DataTypeTest {

	@Test
	public void testGetDataTypeForJavaType() {
		assertEquals(DataType.BYTE, DataType.getDataTypeForJavaType(Byte.class));
		assertEquals(DataType.SHORT, DataType.getDataTypeForJavaType(Short.class));
		assertEquals(DataType.INT, DataType.getDataTypeForJavaType(Integer.class));
		assertEquals(DataType.LONG, DataType.getDataTypeForJavaType(Long.class));
		assertEquals(DataType.FLOAT, DataType.getDataTypeForJavaType(Float.class));
		assertEquals(DataType.DOUBLE, DataType.getDataTypeForJavaType(Double.class));
		assertEquals(DataType.BYTES_ARRAY, DataType.getDataTypeForJavaType(byte[].class));
		assertEquals(DataType.STRING, DataType.getDataTypeForJavaType(String.class));
	}

	@Test
	public void testParseStringString() {
		//fail("Not yet implemented");
	}

	@Test
	public void testParseStringDataType() {
//		switch (type) {
//		case BYTE:
//			return new Byte(data);
		assertEquals((byte)100, ((Byte)DataType.parse("100", DataType.BYTE)).byteValue());
//		case SHORT:
//			return new Short(data);
		assertEquals((short)100, ((Short)DataType.parse("100", DataType.SHORT)).shortValue());
//		case INT:
//			return new Integer(data);
		assertEquals(100, ((Integer)DataType.parse("100", DataType.INT)).intValue());
//		case LONG:
//			return new Long(data);
		assertEquals(100, ((Long)DataType.parse("100", DataType.LONG)).longValue());
//		case FLOAT:
//			return new Float(data);
		assertEquals((float)100, ((Float)DataType.parse("100", DataType.FLOAT)).floatValue(), 0.5);
//		case DOUBLE:
//			return new Double(data);
		assertEquals((double)100, ((Double)DataType.parse("100", DataType.DOUBLE)).doubleValue(), 0.5);
//		case BYTES_ARRAY:
//			String[] bytes = data.split(",");
//			byte[] result = new byte[bytes.length];
//			for (int i = 0; i < bytes.length; i++) {
//				result[i] = Byte.parseByte(bytes[i]);
//			}
//			return result;
		assertArrayEquals(new byte[]{100, 125}, (byte[])DataType.parse("100, 125", DataType.BYTES_ARRAY));		
//		case STRING:
//			return data;
		assertEquals("100", ((String)DataType.parse("100", DataType.STRING)));
	}

	@Test
	public void testBytesArrayToString() {
		assertEquals("123, 45", DataType.bytesArrayToString(new byte[]{123, 45}));
	}

}
