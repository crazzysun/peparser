package com.pe.parser;


public class PEDllTest
{
	static boolean flag = false;

	private static void getData() throws InterruptedException
	{
		flag = true;
		Thread.sleep(3000);
		System.out.println("���ӳɹ�");
	}

	public static void main(String[] args) throws InterruptedException
	{
		int i = 0;
		while (true)
		{
			Thread.sleep(3000);
			while (i == 5)
			{
				System.out.println("���ڳ�ʼ�����ӣ�");
				getData();
				while (flag)
				{
					return;
				}
			}
			i++;
			System.out.println("����ʧ�ܣ����ڷ����" + i + "����");
		}
	}
}
