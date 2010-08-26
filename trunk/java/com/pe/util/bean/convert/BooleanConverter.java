package com.pe.util.bean.convert;

class BooleanConverter extends Converter
{
	private static Number zero = new Integer(0);

	public Boolean convert(Object o)
	{
		if (o == null) return null;

		o = prepare(o);

		if (o instanceof Boolean) { return (Boolean) o; }

		if (o instanceof Number) { return !zero.equals(o); }

		return new Boolean(o.toString());
	}
}
