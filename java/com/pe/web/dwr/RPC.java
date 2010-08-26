package com.pe.web.dwr;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.pe.operation.Operation;
import com.pe.operation.OperationContext;
import com.pe.UserException;

/** 用于和权限系统搭配的通用DWR类 */
public class RPC
{
	private static Log log = LogFactory.getLog(RPC.class);
	
	public static Object call(String name, Operation operation) throws Exception
	{
		if (log.isTraceEnabled()) log.trace("通过DWR执行操作: " + operation);

		OperationContext context = createOperationContext();
		OperationContext.setContext(context);
		
		Map<String, Object> map = new HashMap<String, Object>();
//		DaoManager dm = DaoManager.getInstance();
		try
		{
//			dm.begin();
			
			operation.execute();
			
			map.put("data", operation);

//			dm.commit();
		}
		catch (UserException e)
		{
			map.put("message", e.getMessage());
		}
//		finally
//		{
//			dm.end();
//		}
		
		return map;
	}
	
	private static OperationContext createOperationContext()
	{
		WebContext wc = WebContextFactory.get();
		
		HttpServletRequest request = wc.getHttpServletRequest();
		HttpServletResponse response = wc.getHttpServletResponse();
		HttpSession session = request.getSession();
		
		OperationContext context = new OperationContext();
		OperationContext.setContext(context);

		context.setSession(session);
		context.setRequest(request);
		context.setResponse(response);
		
		return context;
	}
}
