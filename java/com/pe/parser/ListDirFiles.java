package com.pe.parser;

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
	public static List<String> fileList = new ArrayList<String>();		//存储遍历后的所有文件名

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
	public static List<String> getListFiles(String path, String suffix, boolean isDepth) throws UserException
	{
		File file = new File(path);
		return listFile(file, suffix, isDepth);
	}

	public static List<String> listFile(File f, String suffix, boolean isdepth) throws UserException
	{
		// 是目录，同时需要遍历子目录
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
				int begIndex = filePath.lastIndexOf(".");	// 最后一个.(即后缀名前面的.)的索引
				String tempsuffix = "";

				if (begIndex != -1)							// 防止是文件但却没有后缀名结束的文件
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
				throw new UserException("请指定要分析的文件类型！");
//				// 后缀名为null则为所有文件
//				fileList.add(filePath);
			}
		}
		return fileList;
	}
}
