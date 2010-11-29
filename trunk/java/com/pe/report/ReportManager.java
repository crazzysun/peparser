package com.pe.report;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IParameterDefnBase;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;

import com.pe.util.SystemConfigure;


public class ReportManager
{
	private static final Log log = LogFactory.getLog(ReportManager.class);

	protected static ReportManager instance = new ReportManager();
	
	public static ReportManager getInstance()
	{
		return instance;
	}
	
	public static void initialize() throws Exception
	{
		instance.startup();
	}

	public static void destroy()
	{
		instance.shutdown();
	}

	//====================================
	private IReportEngine engine;

	public ReportManager()
	{
		instance = this;
	}
	
	public void startup() throws Exception
	{
		log.info("启动报表引擎");

		// System.setProperty("RUN_UNDER_ECLIPSE", "false");

		String birtHome = SystemConfigure.get("BirtRuntime");
		if (birtHome.equals(""))
		{
			log.warn("未设置 BirtRuntime，未启动报表引擎");
			return;
		}

		EngineConfig config = new EngineConfig();
		config.setEngineHome(birtHome);
		// config.setLogConfig("c:/temp", Level.FINE);
		Platform.startup(config);

		IReportEngineFactory factory = (IReportEngineFactory) Platform
				.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		engine = factory.createReportEngine(config);
		// engine.changeLogLevel(Level.WARNING);

		// by liusong 2008.8.15
		// setup XLS emitter configuration
		Map<String, Integer> xlsConfig = new HashMap<String, Integer>();
		// Check out constants in XlsEmitterConfig.java for more configuration
		// detail.
		xlsConfig.put("fixed_column_width", new Integer(50));
		// Associate the configuration with the XLS output format.
		config.setEmitterConfiguration("xls", xlsConfig);
	}

	public void shutdown()
	{
		log.info("关闭报表引擎");

		if (engine != null)
		{
			engine.destroy();
			Platform.shutdown();
		}
	}

	@SuppressWarnings("unchecked")
	public String report(InputStream design, OutputStream os, String format, ReportEventHandle handle,
			Map<String, Object> paramValues) throws Exception
	{

		if (log.isDebugEnabled()) log.debug(String.format("根据模板 %s 输出报表", design));

		IReportRunnable report = null;
		IRunAndRenderTask task = null;
		IGetParameterDefinitionTask paramTask = null;

		try
		{
			report = engine.openReportDesign(design);
			task = engine.createRunAndRenderTask(report);

			paramTask = engine.createGetParameterDefinitionTask(report);
			Collection<Object> params = paramTask.getParameterDefns(false);

			task.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, ReportManager.class.getClassLoader());

			// 设置render options
			IRenderOption options = null;
			if (format.equalsIgnoreCase("html"))
			{
				HTMLRenderOption o = new HTMLRenderOption();

				o.setOutputFormat("html");
				o.setImageDirectory(SystemConfigure.get("BirtImageDirectory"));
				o.setBaseImageURL(SystemConfigure.get("BirtImageURL"));
				o.setImageHandler(new HTMLServerImageHandler());

				options = o;

			}
			else if (format.equalsIgnoreCase("pdf"))
			{
				options = new PDFRenderOption();
				options.setOutputFormat("pdf");
			}
			else if (format.equalsIgnoreCase("xls"))
			{
				options = new RenderOption();
				// options.setOutputFormat("xls");
			}
			else if (format.equalsIgnoreCase("doc"))
			{
				options = new RenderOption();
				options.setOutputFormat("doc");
			}
			else
			{
				throw new Exception("非法的报表格式：" + format);
			}
			options.setOutputStream(os);
			task.setRenderOption(options);

			// 设置task的handle
			task.setParameterValue("handle", handle);
			// 设置页面参数
			if (paramValues != null)
			{
				Map<String, Object> paramMap = new HashMap<String, Object>();
				Iterator<Object> paramIterator = params.iterator();
				while (paramIterator.hasNext())
				{
					IParameterDefnBase pBase = (IParameterDefnBase) paramIterator.next();
					if (pBase instanceof IScalarParameterDefn)
					{
						IScalarParameterDefn paramDefn = (IScalarParameterDefn) pBase;
						String paramName = paramDefn.getName();
						Object inputValue = paramValues.get(paramName);
						if (inputValue != null)
						{
							paramMap.put(paramName, inputValue);
						}
					}
				}
				task.setParameterValues(paramMap);
			}

			task.validateParameters();

			// run
			task.run();

			return "ok";
		}
		finally
		{
			if (task != null) task.close();
		}
	}
}
