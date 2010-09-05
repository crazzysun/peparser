package com.pe.util.bean.convert;

import java.lang.reflect.Array;
import java.util.List;

class ArrayConverter extends Converter
{
	private Class<?> type;
	private Converter converter;

	public ArrayConverter(Class<?> type)
	{
		this.type = type;
		this.converter = Converter.getConverter(type);
	}

	public Object convert(Object o)
	{
		if (o == null) return null;

		if (o.getClass().isArray()) return convertArray(o);

		if (o instanceof List<?>) return converList(o);

		throw new RuntimeException("不能转换源类型 " + o.getClass().getName() + " 为数组");
	}

	@SuppressWarnings("unchecked")
	private Object converList(Object o)
	{
		List<Object> ll = (List<Object>) o;

		int length = ll.size();
		Object array = Array.newInstance(type, length);
		for (int i = 0; i < length; i++)
		{
			Object t = ll.get(i);
			t = converter.convert(t);
			Array.set(array, i, t);
		}

		return array;
	}

	private Object convertArray(Object o)
	{
		int length = Array.getLength(o);
		Object array = Array.newInstance(type, length);
		for (int i = 0; i < length; i++)
		{
			Object t = Array.get(o, i);
			t = converter.convert(t);
			Array.set(array, i, t);
		}

		return array;
	}
}
