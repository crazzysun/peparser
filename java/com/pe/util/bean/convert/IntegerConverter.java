package com.pe.util.bean.convert;

class IntegerConverter extends Converter
{
	public Integer convert(Object o)
	{
		if (o == null) return null;

		o = prepare(o);

		if (o instanceof Number) return ((Number) o).intValue();

		return new Integer(o.toString());
	}
}
