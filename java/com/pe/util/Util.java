package com.pe.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util
{
	/** 显示当前日期 */
	public static String showNowTime()
	{
		Date date = new Date();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = simpledateformat.format(date);
		return s;
	}
	
	public static String replace2HTML(String s)
	{
		s = replace(s, "\n", "<br/>");
		s = replace(s, " ", "&nbsp;&nbsp;");
		return s;
	}
	
	/** 替换s中的字段s1为s2 */
	public static String replace(String s, String s1, String s2)
	{
		if (s == null) return null;
		StringBuffer stringbuffer = new StringBuffer();
		int i = s.length();
		int j = s1.length();
		int k;
		int l;
		for (k = 0; (l = s.indexOf(s1, k)) >= 0; k = l + j)
		{
			stringbuffer.append(s.substring(k, l));
			stringbuffer.append(s2);
		}

		if (k < i) stringbuffer.append(s.substring(k));
		return stringbuffer.toString();
	}
}
