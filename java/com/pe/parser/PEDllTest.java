package com.pe.parser;


public class PEDllTest
{
	static boolean flag = false;

	private static void getData() throws InterruptedException
	{
		flag = true;
		Thread.sleep(3000);
		System.out.println("连接成功");
	}

	public static void main(String[] args) throws InterruptedException
	{
		int i = 0;
		while (true)
		{
			Thread.sleep(3000);
			while (i == 5)
			{
				System.out.println("正在初始化连接！");
				getData();
				while (flag)
				{
					return;
				}
			}
			i++;
			System.out.println("连接失败，正在发起第" + i + "连接");
		}
	}
}
