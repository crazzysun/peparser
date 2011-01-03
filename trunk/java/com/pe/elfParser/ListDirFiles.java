package com.pe.elfParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.pe.UserException;


/**
 * java ��ȡĿ¼����Ŀ¼��ָ���ļ�����·�� ���ŵ�һ��List��������
 *
 * @author FangZhiyang
 */
public class ListDirFiles
{
	public static List<String> fileList;		//�洢������������ļ���

	public static void initalize()
	{
		fileList = new ArrayList<String>();
	}
	/**
	 * 
	 * @param path
	 *            �ļ�·��
	 * @param suffix
	 *            ��׺��
	 * @param isDepth
	 *            �Ƿ������Ŀ¼
	 * @return
	 * @throws UserException 
	 */
	public static List<String> listFile(File f,boolean isDepth) throws Exception
	{
		// ��Ŀ¼��ͬʱ��Ҫ������Ŀ¼
		if (f.isDirectory())
		{
			if (isDepth)
			{
				File[] t = f.listFiles();
				for (int i = 0; i < t.length; i++)
				{
					listFile(t[i], isDepth);
				}
			}
		}
		else
		{
			String filePath = f.getAbsolutePath();
            fileList.add(filePath);
		}
		return fileList;
	}
}