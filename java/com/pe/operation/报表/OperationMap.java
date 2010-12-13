package com.pe.operation.����;

import java.util.HashMap;

public class OperationMap extends HashMap<String,String>
{
	private static final long serialVersionUID = 1L;
	private static OperationMap operationmap;
	
    static
	{
		operationmap = 	new OperationMap();
		operationmap.put("graph_report.rptdesign", "����.�鿴ָ�����ͼ");
		operationmap.put("dynamic_graph_report.rptdesign", "����.�ֲ����ֱ��ͼ");
	}
    
	public static String getOperationName(String design)
	{
		return operationmap.get(design);
	} 
}
