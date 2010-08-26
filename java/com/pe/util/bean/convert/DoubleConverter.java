package com.pe.util.bean.convert;

class DoubleConverter extends Converter
{
	public Double convert(Object o)
	{
		if (o == null) return null;

		o = prepare(o);

		if (o instanceof Number) return ((Number) o).doubleValue();

		return new Double(o.toString());
	}
}
