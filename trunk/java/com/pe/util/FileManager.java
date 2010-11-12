package com.pe.util;

import java.io.File;

public class FileManager
{
	private static FileManager instance = new FileManager();

	public static FileManager getInstance()
	{
		return instance;
	}

	public static void initialize() throws Exception
	{
		instance.startup();
	}

	// ========================================================
	private File PEHome; 			// PE�ļ�Ŀ¼
	private File TemplateHome; 		// ģ��Ŀ¼
	private File PEResultHome; 		// PE�ļ��������
	private File DefaultTrainSetPath;		// �ӿ�ѵ����Ĭ�ϴ��Ŀ¼
	private File PackedResultHome;	// �ӿǷ���ѵ����������Ŀ¼
	

	public void startup() throws Exception
	{
		PEHome = new File(SystemConfigure.get("PEHome"));
		TemplateHome = new File(SystemConfigure.get("TemplateHome"));
		PEResultHome = new File(SystemConfigure.get("PEResultHome"));
		DefaultTrainSetPath = new File(SystemConfigure.get("DefaultTrainSetPath"));
		PackedResultHome = new File(SystemConfigure.get("PackedResultHome"));
		
		if (!PEHome.exists()) PEHome.mkdirs();
		if (!TemplateHome.exists()) TemplateHome.mkdirs();
		if (!PEResultHome.exists()) PEResultHome.mkdirs();
		if (!PackedResultHome.exists()) PackedResultHome.mkdirs();
	}

	public File getPEHome()
	{
		return PEHome;
	}

	public void setPEHome(File pEHome)
	{
		PEHome = pEHome;
	}

	public File getTemplateHome()
	{
		return TemplateHome;
	}

	public void setTemplateHome(File templateHome)
	{
		TemplateHome = templateHome;
	}

	public File getPEResultHome()
	{
		return PEResultHome;
	}

	public void setPEResultHome(File pEResultHome)
	{
		PEResultHome = pEResultHome;
	}

	public File getDefaultTrainSetPath()
	{
		return DefaultTrainSetPath;
	}

	public void setDefaultTrainSetPath(File defaultTrainSetPath)
	{
		DefaultTrainSetPath = defaultTrainSetPath;
	}

	public File getPackedResultHome()
	{
		return PackedResultHome;
	}

	public void setPackedResultHome(File packedResultHome)
	{
		PackedResultHome = packedResultHome;
	}
}
