package com.pe.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialize
{
	//序列化对象
	public static final byte[] serializeData(Object object) throws Exception
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(object);
		byte[] bytes = byteOut.toByteArray();

		return bytes;
	}

	//反序列化对象
	public static final Object deSerializeData(byte[] bytes) throws Exception
	{
		ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
		ObjectInputStream in = new ObjectInputStream(byteIn);
		Object object = in.readObject();
		
		return object;
	}
}
