package com.pe.util;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

public class SystemConfigure
{
	private static Map<String, String> configures = new HashMap<String, String>();
	
	public static void load(String file) throws Exception
	{
		LineNumberReader reader = null;
		try
		{
			reader = new LineNumberReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			
			load(reader);
		}
		catch (Exception e)
		{
			throw new Exception("载入系统配置错误", e);
		}
		finally
		{
			if (reader != null) try { reader.close(); } catch (Exception e) {}
		}
	}

	private static void load(LineNumberReader reader) throws Exception
	{
		for (; ; )
		{
			String s = reader.readLine();
			if (s == null) break;
			
			s = s.trim();

			if ("".equals(s)) continue;
			if (s.startsWith("#")) continue;
			
			int k = s.indexOf("=");
			if (k < 0) throw new Exception("LINE " + reader.getLineNumber() + ": 格式错误");
			
			String key = s.substring(0, k).trim();
			String value = s.substring(k + 1).trim();
			
			configures.put(key, value);
		}
	}
	
	public static String get(String key) throws Exception
	{
		String value = configures.get(key);
		if (value == null) throw new Exception("系统配置 " + key + " 不存在");
		
		return value;
	}
}
