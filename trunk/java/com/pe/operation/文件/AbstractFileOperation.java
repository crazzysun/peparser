package com.pe.operation.�ļ�;

import java.io.File;

import com.pe.UserException;
import com.pe.util.SystemConfigure;

/**
 * �����ļ���ز���
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

		// �ж�work�ĺϷ���
		if (!work.getCanonicalPath().startsWith(home.getCanonicalPath())) throw new UserException("���ʷǷ�: " + path);
		
		// �ж��Ƿ����
		if (!work.exists()) 
			throw new UserException("�ļ���Ŀ¼������: " + path);

		return work;
	}
}
