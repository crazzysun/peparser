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
	private File PEHome; 					// PE�ļ�Ŀ¼
	private File TemplateHome; 				// ģ��Ŀ¼
	private File PEResultHome; 				// PE�ļ��������
	private File DefaultPackedTrainSample;	// �ӿ�ѵ������Ĭ�ϴ��Ŀ¼
	private File PackedResultHome;			// �ӿǷ���ѵ����������Ŀ¼
	private File DefaultGdlTrainSample;		// GDLѵ������Ĭ�ϴ��Ŀ¼
	private File GdlResultHome;				// �ӿǷ���ѵ����������Ŀ¼
	private File DefaultGBIDSample;			// GBIDģʽ������Ĭ�ϴ��Ŀ¼
	private File GBIDResultHome;			// GBIDģʽ��������Ŀ¼
	

	public void startup() throws Exception
	{
		PEHome = new File(SystemConfigure.get("PEHome"));
		TemplateHome = new File(SystemConfigure.get("TemplateHome"));
		PEResultHome = new File(SystemConfigure.get("PEResultHome"));
		DefaultPackedTrainSample = new File(SystemConfigure.get("DefaultPackedTrainSample"));
		DefaultGdlTrainSample = new File(SystemConfigure.get("DefaultGdlTrainSample"));
		PackedResultHome = new File(SystemConfigure.get("PackedResultHome"));
		GdlResultHome = new File(SystemConfigure.get("GdlResultHome"));
		DefaultGBIDSample = new File(SystemConfigure.get("DefaultGBIDSample"));
		GBIDResultHome = new File(SystemConfigure.get("GBIDResultHome"));
		
		if (!PEHome.exists()) PEHome.mkdirs();
		if (!TemplateHome.exists()) TemplateHome.mkdirs();
		if (!PEResultHome.exists()) PEResultHome.mkdirs();
		if (!PackedResultHome.exists()) PackedResultHome.mkdirs();
		if (!GdlResultHome.exists()) GdlResultHome.mkdirs();
		if (!GBIDResultHome.exists()) GBIDResultHome.mkdirs();
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

	public File getPackedResultHome()
	{
		return PackedResultHome;
	}

	public void setPackedResultHome(File packedResultHome)
	{
		PackedResultHome = packedResultHome;
	}

	public File getDefaultPackedTrainSample()
	{
		return DefaultPackedTrainSample;
	}

	public void setDefaultPackedTrainSample(File defaultPackedTrainSample)
	{
		DefaultPackedTrainSample = defaultPackedTrainSample;
	}

	public File getDefaultGdlTrainSample()
	{
		return DefaultGdlTrainSample;
	}

	public void setDefaultGdlTrainSample(File defaultGdlTrainSample)
	{
		DefaultGdlTrainSample = defaultGdlTrainSample;
	}

	public File getGdlResultHome()
	{
		return GdlResultHome;
	}

	public void setGdlResultHome(File gdlResultHome)
	{
		GdlResultHome = gdlResultHome;
	}

	public File getDefaultGBIDSample()
	{
		return DefaultGBIDSample;
	}

	public void setDefaultGBIDSample(File defaultGBIDSample)
	{
		DefaultGBIDSample = defaultGBIDSample;
	}

	public File getGBIDResultHome()
	{
		return GBIDResultHome;
	}

	public void setGBIDResultHome(File gBIDResultHome)
	{
		GBIDResultHome = gBIDResultHome;
	}
}
