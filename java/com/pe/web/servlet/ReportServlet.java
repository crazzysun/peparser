package com.pe.web.servlet;

import java.io.IOException;
import java.net.SocketException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pe.UserException;
import com.pe.dao.DaoManager;
import com.pe.operation.OperationContext;
import com.pe.operation.OperationManager;
import com.pe.operation.报表.OperationMap;

/**
 * 报表Servlet 用于产生报表
 */
public class ReportServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private Log log = LogFactory.getLog(ReportServlet.class);

	@SuppressWarnings("unchecked")
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		DaoManager dm = DaoManager.getInstance();
		try
		{
			dm.begin();

			request.setCharacterEncoding("UTF-8");
			
			OperationContext context = new OperationContext();
			HttpSession session = request.getSession(false);
			
			context.setSession(session);
			context.setRequest(request);
			context.setResponse(response);
			
			String designname = request.getParameter("design").toString();
			String operation = OperationMap.getOperationName(designname);
			OperationManager.getInstance().execute(operation, request.getParameterMap(), context);
			dm.commit();
		}
		catch (UserException e)           
		{
			response.sendError(404, e.getMessage());
		}
		catch (SocketException e)
		{
		}
		catch (Exception e)
		{
			log.error("生成报表错误", e);
			response.sendError(503, e.getMessage());
		}
		finally
		{
			dm.end();
		}
	}
}
