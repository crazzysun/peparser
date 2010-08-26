package com.pe.operation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OperationContext
{
	private static ThreadLocal<OperationContext> context = new ThreadLocal<OperationContext>();

	private HttpSession session;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	private boolean rollback = false;

	public boolean isRollback()
	{
		return rollback;
	}

	public void setRollback(boolean rollback)
	{
		this.rollback = rollback;
	}

	public static void setContext(OperationContext c)
	{
		context.set(c);
	}

	public static OperationContext getContext()
	{
		return context.get();
	}

	// --------------------------------------------

	public HttpSession getSession()
	{
		return session;
	}

	public void setSession(HttpSession session)
	{
		this.session = session;
	}

	public HttpServletRequest getRequest()
	{
		return request;
	}

	public void setRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public HttpServletResponse getResponse()
	{
		return response;
	}

	public void setResponse(HttpServletResponse response)
	{
		this.response = response;
	}
}
