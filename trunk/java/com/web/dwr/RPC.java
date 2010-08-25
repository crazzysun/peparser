package com.web.dwr;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.UserException;
import com.operation.Operation;

/** 用于和权限系统搭配的通用DWR类 */
public class RPC
{
	private static Log log = LogFactory.getLog(RPC.class);
	
	public static Object call(String name, Operation operation) throws Exception
	{
		if (log.isTraceEnabled()) log.trace("通过DWR执行操作: " + operation);

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
}
