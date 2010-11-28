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
			log.info("����PE����ϵͳ...");

			Context context = new javax.naming.InitialContext();
			context = (Context) context.lookup("java:comp/env");

			// ����ϵͳ����
			try
			{
				SystemConfigure.load((String) context.lookup("configure")); 
			}
			catch (Exception e)
			{
				throw new Exception("����tomcatĿ¼�µ�peparser.xml�ļ�", e);
			}
			
			// ��ʼ��DAO������
			DaoManager.initialize((DataSource) context.lookup("jdbc/tcc"));
			
			// �ļ�������
			FileManager.initialize();

			// ����������
			OperationManager.initialize();

			// �������ϵͳ
			ReportManager.initialize();
						
			log.info("����PE����ϵͳ��ϣ�");
		}
		catch (Exception e)
		{
			log.fatal("����PE����ϵͳʧ�ܣ�", e);

			throw new ServletException("�����ύϵͳʧ��: " + e.getMessage(), e);
		}
	}

	@Override
	public void destroy()
	{
		log.info("�ر�PE����ϵͳ...");
		
		log.info("�ر�PE����ϵͳ��ϣ�");

		super.destroy();
	}

	public void contextInitialized(ServletContextEvent arg0)
	{
		
	}

	public void contextDestroyed(ServletContextEvent arg0)
	{
		
	}
}

