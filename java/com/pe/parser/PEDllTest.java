package com.pe.parser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.regex.PatternSyntaxException;

import com.pe.dll.petest.PEAnalyzerDll;
import com.sun.jna.WString;

public class PEDllTest
{
	// static boolean flag = false;
	//
	// private static void getData() throws InterruptedException
	// {
	// flag = true;
	// Thread.sleep(3000);
	// System.out.println("连接成功");
	// }

	public static void main(String[] args) throws UnsupportedEncodingException
	{
		// int i = 0;
		// while (true)
		// {
		// Thread.sleep(3000);
		// while (i == 5)
		// {
		// System.out.println("正在初始化连接！");
		// getData();
		// while (flag)
		// {
		// return;
		// }
		// }
		// i++;
		// System.out.println("连接失败，正在发起第" + i + "连接");
		// }�汾
		PEAnalyzerDll PE = PEAnalyzerDll.INSTANCE;
		PE.LoadPEHeader("c:/test/createlang.exe");
//		String str = PE.say(new String("Hello World!"));
//		System.out.println(getWebContext(str));
		// String str = PE.GetRSCType(0);
		// System.out.println("2:" +new
		// String(str.getBytes("gb18030"),"utf-8"));
		// System.out.println("2:" +new
		// String(str.getBytes("gb18030"),"gb18030"));
		// System.out.println("2:" +new String(str.getBytes("utf-8"),"utf-8"));
		// System.out.println("2:" +new
		System.out.println(PE.GetRSCType(0));
	}

	private static String getWebContext(String aa)
	{
		StringBuffer input123 = new StringBuffer();
		try
		{
			String urlString;

			urlString = "http://www..sina.com";

			System.out.println("urlString length" + urlString.length());
			InputStreamReader in = new InputStreamReader(new URL(urlString).openStream(), "UTF-8");

			int ch;
			while ((ch = in.read()) != -1)
				input123.append((char) ch);

		}
		catch (IOException exception)
		{
			exception.printStackTrace();
		}
		catch (PatternSyntaxException exception)
		{
			exception.printStackTrace();
		}
		return input123.toString();
	}
}
