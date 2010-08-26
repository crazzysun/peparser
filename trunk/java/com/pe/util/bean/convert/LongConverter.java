package com.pe.util.bean.convert;

class LongConverter extends Converter
{
	public Long convert(Object o)
	{
		if (o == null) return null;

		o = prepare(o);

		if (o instanceof Number) return ((Number) o).longValue();

		return new Long(o.toString());
	}
}
