package com.pe.parser;

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
	public static List<String> fileList = new ArrayList<String>();		//�洢������������ļ���

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
	public static List<String> listFile(File f, List<String> suffix, boolean isDepth) throws Exception
	{
		// ��Ŀ¼��ͬʱ��Ҫ������Ŀ¼
		if (f.isDirectory())
		{
			if (isDepth)
			{
				File[] t = f.listFiles();
				for (int i = 0; i < t.length; i++)
				{
					listFile(t[i], suffix, isDepth);
				}
			}
		}
		else
		{
			String filePath = f.getAbsolutePath();

			if (suffix != null && suffix.size() != 0)
			{
				int begIndex = filePath.lastIndexOf(".");	// ���һ��.(����׺��ǰ���.)������
				String tempsuffix = "";

				if (begIndex != -1)							// ��ֹ���ļ���ȴû�к�׺���������ļ�
				{
					tempsuffix = filePath.substring(begIndex + 1, filePath.length());
				}

				if (suffix.contains(tempsuffix))
				{
					fileList.add(filePath);
				}
			}
//			else throw new UserException("��ָ��Ҫ�������ļ����ͣ�");
			/** ��ָ�����ͣ���Ϊ����ȫ���ļ� */
			else
			{
				fileList.add(filePath);
			}
		}
		return fileList;
	}
}
