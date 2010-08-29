package com.pe.parser;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.pe.UserException;

/**
 * 读取模板文件
 *
 * @author FangZhiyang
 */
public class ReadTemplates
{
	private static String tlpContent = null;

	public static String getTlpContent(String s) throws UserException
	{
		if (tlpContent == null) try
		{
			tlpContent = readTemplateContent(s);
			if (tlpContent == null) throw new UserException("tlpContent is null !");
		}
		catch (Exception e)
		{
			throw new UserException("read template error !");
		}
		return tlpContent;
	}

	private static synchronized String readTemplateContent(String s) throws UserException
	{
		String s1 = "";
		try
		{
			FileInputStream fileinputstream = new FileInputStream(s);
			InputStreamReader reader = new InputStreamReader(fileinputstream, "GB2312");
			BufferedReader br = new BufferedReader(reader);
			
			String str = "";
			while ((str = br.readLine()) != null)
			{
				s1 += str + "\n";
			}
			br.close();
		}
		catch (IOException ioexception)
		{
			throw new UserException("io error !");
		}
		return s1;
	}
}
