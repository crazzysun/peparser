package com.pe.operation.报表;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.birt.report.engine.api.script.IUpdatableDataSetRow;
import org.eclipse.birt.report.engine.api.script.instance.IDataSetInstance;

import com.pe.operation.AbstractReportOperation;
import com.pe.operation.Operation;
import com.pe.report.ReportEventHandle;

public class 测试 extends AbstractReportOperation implements Operation, ReportEventHandle
{
	private List<String> portions = new ArrayList<String>();
	private Map<String, String> pMap = new HashMap<String, String>();
	
	private int index = 0;
	
	@Override
	public void execute() throws Exception
	{
		
		portions.add("a");
		portions.add("b");
		portions.add("c");
		portions.add("d");
		portions.add("e");
		
		pMap.put("a", "15");
		pMap.put("b", "10");
		pMap.put("c", "8");
		pMap.put("d", "20");
		pMap.put("e", "16");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String title = "124";
		paramMap.put("title", title);
		super.output(paramMap);
		
	}
	
	public boolean fetch(IDataSetInstance dataSet, IUpdatableDataSetRow row)
	{
		if(index >= portions.size())
		{
			index = 0;
			return false;
		}
		try
		{
			row.setColumnValue("等份数", portions.get(index));
			row.setColumnValue("比重", pMap.get(portions.get(index)));
			
			index++;
			
			return true;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
}
