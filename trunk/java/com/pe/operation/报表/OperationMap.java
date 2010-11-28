package com.pe.operation.报表;

import java.util.HashMap;

public class OperationMap extends HashMap<String,String>
{
	private static final long serialVersionUID = 1L;
	private static OperationMap operationmap;
	
    static
	{
		operationmap = 	new OperationMap();
		operationmap.put("new_report.rptdesign", "报表.测试");
	}
    
	public static String getOperationName(String design)
	{
		return operationmap.get(design);
	} 
}
