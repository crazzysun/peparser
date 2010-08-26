package com.pe.util.bean.convert;

public class StringConverter extends Converter
{
	public String convert(Object o)
	{
		if (o == null) return null;

		o = prepare(o);

		return o.toString();
	}
}
