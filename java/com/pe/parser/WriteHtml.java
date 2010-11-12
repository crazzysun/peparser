package com.pe.parser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.pe.UserException;

/**
 * 生成结果html
 *
 * @author FangZhiyang
 */
public class WriteHtml
{
	public static void save(String s, String s1, String s2) throws UserException
	{
		try
		{
			getFile(s1);
			FileOutputStream fileOutputStream = new FileOutputStream(s1 + s2);
			OutputStreamWriter out = new OutputStreamWriter(fileOutputStream, "UTF-8");
			BufferedWriter bw = new BufferedWriter(out);
			bw.write(s);
			bw.close();
		}
		catch (IOException ioexception)
		{
			throw new UserException("write file error !");
		}
	}

	private static void getFile(String s) throws UserException
	{
		try
		{
			File file = new File(s);
			if (!file.exists()) file.mkdirs();
		}
		catch (Exception exception)
		{
			throw new UserException("wirte File error !");
		}
	}
}
