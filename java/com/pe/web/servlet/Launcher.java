package com.pe.web.servlet;

import javax.naming.Context;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pe.dao.DaoManager;
import com.pe.operation.OperationManager;
import com.pe.report.ReportManager;
import com.pe.util.FileManager;
import com.pe.util.SystemConfigure;

public class Launcher extends HttpServlet implements ServletContextListener
{
	private static final long serialVersionUID = -3436651019725988874L;
	private static Log log = LogFactory.getLog(Launcher.class);

	public void init() throws ServletException
	{
		super.init();

		try
		{
			log.info("启动PE分析系统...");

			Context context = new javax.naming.InitialContext();
			context = (Context) context.lookup("java:comp/env");

			// 载入系统配置
			try
			{
				SystemConfigure.load((String) context.lookup("configure")); 
			}
			catch (Exception e)
			{
				throw new Exception("请检查tomcat目录下的peparser.xml文件", e);
			}
			
			// 初始化DAO管理器
			DaoManager.initialize((DataSource) context.lookup("jdbc/tcc"));
			
			// 文件管理器
			FileManager.initialize();

			// 操作管理器
			OperationManager.initialize();

			// 报表管理系统
			ReportManager.initialize();
						
			log.info("启动PE分析系统完毕！");
		}
		catch (Exception e)
		{
			log.fatal("启动PE分析系统失败！", e);

			throw new ServletException("启动提交系统失败: " + e.getMessage(), e);
		}
	}

	@Override
	public void destroy()
	{
		log.info("关闭PE分析系统...");
		
		log.info("关闭PE分析系统完毕！");

		super.destroy();
	}

	public void contextInitialized(ServletContextEvent arg0)
	{
		
	}

	public void contextDestroyed(ServletContextEvent arg0)
	{
		
	}
}

