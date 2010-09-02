package com.pe.operation.文件;

import java.io.File;

import com.pe.UserException;
import com.pe.util.SystemConfigure;

/**
 * 抽象文件相关操作
 */
public abstract class AbstractFileOperation
{
	protected File getHomeDirectory() throws Exception
	{
		File home = new File(SystemConfigure.get("PEHome"));
		if (!home.exists()) home.mkdirs();

		return home;
	}

	protected File getWorkFile(String path) throws Exception
	{
		File home = getHomeDirectory();
		File work = new File(home, path);

		// 判断work的合法性
		if (!work.getCanonicalPath().startsWith(home.getCanonicalPath())) throw new UserException("访问非法: " + path);
		
		// 判断是否存在
		if (!work.exists()) 
			throw new UserException("文件或目录不存在: " + path);

		return work;
	}
}
