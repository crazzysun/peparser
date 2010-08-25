package com.util.bean.convert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class DateConverter extends Converter
{
	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public Date convert(Object o)
	{
		if (o == null) return null;

		o = prepare(o);

		if (o instanceof Date) return (Date) o;

		String s = o.toString();
		try
		{
			return format.parse(s);
		}
		catch (ParseException e)
		{
			throw new RuntimeException("不能转换 " + s + " 为日期类型");
		}
	}
}
