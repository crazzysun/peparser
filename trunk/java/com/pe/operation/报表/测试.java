package com.pe.operation.����;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.birt.report.engine.api.script.IUpdatableDataSetRow;
import org.eclipse.birt.report.engine.api.script.instance.IDataSetInstance;

import com.pe.operation.AbstractReportOperation;
import com.pe.operation.Operation;
import com.pe.report.ReportEventHandle;

public class ���� extends AbstractReportOperation implements Operation, ReportEventHandle
{
	private List<String> portions = new ArrayList<String>();
	private Map<String, String> pMap = new HashMap<String, String>();
	
	private int index = 0;
	
	@Override
	public void execute() throws Exception
	{
		
		portions.add("1");
		portions.add("2");
		portions.add("3");
		portions.add("4");
		portions.add("5");
		
		pMap.put("1", "15");
		pMap.put("2", "10");
		pMap.put("3", "8");
		pMap.put("4", "20");
		pMap.put("5", "16");
		
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
			row.setColumnValue("�ȷ���", portions.get(index));
			row.setColumnValue("����", pMap.get(portions.get(index)));
			
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
