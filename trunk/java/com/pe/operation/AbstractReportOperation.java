package com.pe.operation;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.birt.report.engine.api.script.IUpdatableDataSetRow;
import org.eclipse.birt.report.engine.api.script.instance.IDataSetInstance;

import com.pe.report.ReportEventHandle;
import com.pe.report.ReportManager;

public abstract class AbstractReportOperation implements Operation, ReportEventHandle
{
	private String design;
	private String format;
	
	protected void output(Map<String, Object> paramValues) throws Exception
	{
		OperationContext context = OperationContext.getContext();
		
		HttpServletResponse response = context.getResponse();
		HttpServletRequest request = context.getRequest();

		OutputStream client = null;
		try
		{
			String fomat = request.getParameter("format");

			if (fomat.equalsIgnoreCase("pdf"))
				response.setContentType("application/pdf");
			else if (fomat.equalsIgnoreCase("xls"))
			{
				response.setContentType("application/vnd.ms-excel");
				String name = design+".xls";
				response.setHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes("GBK"), "ISO8859-1"));
			}
			else if (format.equalsIgnoreCase("doc"))
			{
				response.setContentType("application/vnd.ms-word");
				String name = design+".doc";
				response.setHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes("GBK"), "ISO8859-1"));
			}
			else
				response.setContentType("text/html");

			response.setCharacterEncoding("UTF-8");
			client = response.getOutputStream();

			String designfile = "com/pe/rptdesigns/" + design;
			InputStream is = ReportManager.class.getClassLoader().getResourceAsStream(designfile);

			ReportManager.getInstance().report(is, client, format, this, paramValues);
		}
		catch (Exception e)
		{
			throw new Exception("生成报表失败", e);
		}
		finally
		{
			if (client != null) client.close();
		}
	}

	public void open(IDataSetInstance dataSet)
	{
	}

	public boolean fetch(IDataSetInstance dataSet, IUpdatableDataSetRow row)
	{
		return false;
	}

	public void setFormat(String format)
	{
		this.format = format;
	}

	public void setDesign(String design)
	{
		this.design = design;
	}
}
