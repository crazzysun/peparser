package com.pe.elfParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.pe.UserException;


/**
 * java 读取目录及子目录下指定文件名的路径 并放到一个List数组里面
 *
 * @author FangZhiyang
 */
public class ListDirFiles
{
	public static List<String> fileList;		//存储遍历后的所有文件名

	public static void initalize()
	{
		fileList = new ArrayList<String>();
	}
	/**
	 * 
	 * @param path
	 *            文件路径
	 * @param suffix
	 *            后缀名
	 * @param isDepth
	 *            是否遍历子目录
	 * @return
	 * @throws UserException 
	 */
	public static List<String> listFile(File f,boolean isDepth) throws Exception
	{
		// 是目录，同时需要遍历子目录
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