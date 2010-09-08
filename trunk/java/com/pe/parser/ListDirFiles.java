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
	public static List<String> getListFiles(String path, String suffix, boolean isDepth) throws UserException
	{
		File file = new File(path);
		return listFile(file, suffix, isDepth);
	}

	public static List<String> listFile(File f, String suffix, boolean isdepth) throws UserException
	{
		// ��Ŀ¼��ͬʱ��Ҫ������Ŀ¼
		if (f.isDirectory() && isdepth == true)
		{
			File[] t = f.listFiles();
			for (int i = 0; i < t.length; i++)
			{
				listFile(t[i], suffix, isdepth);
			}
		}
		else
		{
			String filePath = f.getAbsolutePath();

			if (suffix != null)
			{
				int begIndex = filePath.lastIndexOf(".");	// ���һ��.(����׺��ǰ���.)������
				String tempsuffix = "";

				if (begIndex != -1)							// ��ֹ���ļ���ȴû�к�׺���������ļ�
				{
					tempsuffix = filePath.substring(begIndex + 1, filePath.length());
				}

				if (tempsuffix.equals(suffix))
				{
					fileList.add(filePath);
				}
			}
			else
			{
				throw new UserException("��ָ��Ҫ�������ļ����ͣ�");
//				// ��׺��Ϊnull��Ϊ�����ļ�
//				fileList.add(filePath);
			}
		}
		return fileList;
	}
}
