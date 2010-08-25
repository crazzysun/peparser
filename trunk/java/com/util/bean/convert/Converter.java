package com.util.bean.convert;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Converter
{
	private static final Map<Class<?>, Converter> map = new HashMap<Class<?>, Converter>();
	static
	{
		map.put(Integer.TYPE, new IntegerConverter());
		map.put(Integer.class, new IntegerConverter());
		map.put(Long.TYPE, new LongConverter());
		map.put(Long.class, new LongConverter());
		map.put(Double.TYPE, new DoubleConverter());
		map.put(Double.class, new DoubleConverter());
		map.put(Boolean.TYPE, new BooleanConverter());
		map.put(Boolean.class, new BooleanConverter());
		map.put(String.class, new StringConverter());
		map.put(Date.class, new DateConverter());

		map.put(int[].class, new ArrayConverter(Integer.TYPE));
		map.put(Integer[].class, new ArrayConverter(Integer.class));
		map.put(long[].class, new ArrayConverter(Long.TYPE));
		map.put(Long[].class, new ArrayConverter(Long.class));
		map.put(double[].class, new ArrayConverter(Double.TYPE));
		map.put(Double[].class, new ArrayConverter(Double.class));
		map.put(boolean[].class, new ArrayConverter(Boolean.TYPE));
		map.put(Boolean[].class, new ArrayConverter(Boolean.class));
		map.put(String[].class, new ArrayConverter(String.class));
		map.put(Date[].class, new ArrayConverter(Date.class));
	}

	public static Converter getConverter(Class<?> type)
	{
		Converter converter = map.get(type);
		if (converter == null) throw new RuntimeException("没有指定的转换器: " + type);

		return converter;
	}

	public abstract Object convert(Object o);

	@SuppressWarnings("unchecked")
	protected Object prepare(Object o)
	{
		if (o instanceof List)
		{
			o = ((List<Object>) o).get(0);
		}
		else if (o.getClass().isArray())
		{
			o = Array.get(o, 0);
		}

		return o;
	}
}
