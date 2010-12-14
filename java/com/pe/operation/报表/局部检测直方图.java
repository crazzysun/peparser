package com.pe.operation.报表;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.birt.report.engine.api.script.IUpdatableDataSetRow;
import org.eclipse.birt.report.engine.api.script.instance.IDataSetInstance;

import com.pe.dao.DaoManager;
import com.pe.dao.GBID.GBIDDao;
import com.pe.entity.GBID.ChatResult;
import com.pe.entity.GBID.MatchingResult;
import com.pe.operation.AbstractReportOperation;
import com.pe.operation.Operation;
import com.pe.report.ReportEventHandle;
import com.pe.util.Serialize;

public class 局部检测直方图 extends AbstractReportOperation implements Operation, ReportEventHandle
{
	private List<String> xZhou = new ArrayList<String>();
	private Map<String, Double> pMap1 = new HashMap<String, Double>();
	private Map<String, Double> pMap2 = new HashMap<String, Double>();
	List<MatchingResult> data;
	
	private Map<String, Double> pMap1All = new HashMap<String, Double>();
	private Map<String, Double> pMap2All = new HashMap<String, Double>();
	List<MatchingResult> dataAll;
	
	private String fileName;
	
	private int index = 0;
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws Exception
	{
		GBIDDao dao = DaoManager.getInstance().getDao(GBIDDao.class);
		ChatResult result = dao.getChatResult(fileName);
		ChatResult result1 = dao.getChatResult1(fileName);
		data = (List<MatchingResult>)Serialize.deSerializeData(result.getResult());
		dataAll = (List<MatchingResult>)Serialize.deSerializeData(result1.getResult());
		
		/** 横坐标*/
		for(int i = 1; i <= 10; i++)
		{
			xZhou.add("plus-" + i);
		}
		
		/** 局部检测纵坐标取值*/
		for (int i = 0; i < data.size(); i++)
		{
			MatchingResult mr = data.get(i);
			String p1 = mr.getAllRulesRate();
			String p2 = mr.getNtvRulesRate();
			pMap1.put(xZhou.get(i), Double.valueOf(p1.substring(0, p1.length()-1)));
			pMap2.put(xZhou.get(i), Double.valueOf(p2.substring(0, p2.length()-1)));
		}
		
		/** 全局检测纵坐标取值*/
		for (int i = 0; i < dataAll.size(); i++)
		{
			MatchingResult mr = dataAll.get(i);
			String p1 = mr.getAllRulesRate();
			String p2 = mr.getNtvRulesRate();
			pMap1All.put(xZhou.get(i), Double.valueOf(p1.substring(0, p1.length()-1)));
			pMap2All.put(xZhou.get(i), Double.valueOf(p2.substring(0, p2.length()-1)));
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("title", fileName);
		super.output(paramMap);
	}
	
	public boolean fetch(IDataSetInstance dataSet, IUpdatableDataSetRow row)
	{
		if(dataSet.getName().equals("数据集")) 	return fetch1(row);
		if(dataSet.getName().equals("数据集1")) return fetch2(row);
		else return true;
	}
	
	public boolean fetch1(IUpdatableDataSetRow row)
	{
		if(index >= xZhou.size())
		{
			index = 0;
			return false;
		}
		try
		{
			row.setColumnValue("等份数", xZhou.get(index));
			row.setColumnValue("正常文件", pMap1.get(xZhou.get(index)));
			row.setColumnValue("非正常文件", pMap2.get(xZhou.get(index)));
			
			index++;
			
			return true;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean fetch2(IUpdatableDataSetRow row)
	{
		if(index >= xZhou.size())
		{
			index = 0;
			return false;
		}
		try
		{
			row.setColumnValue("等份数", xZhou.get(index));
			row.setColumnValue("正常文件", pMap1All.get(xZhou.get(index)));
			row.setColumnValue("非正常文件", pMap2All.get(xZhou.get(index)));
			
			index++;
			
			return true;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getFileName()
	{
		return fileName;
	}

}
