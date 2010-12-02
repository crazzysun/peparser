package com.pe.operation.报表;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.birt.report.engine.api.script.IUpdatableDataSetRow;
import org.eclipse.birt.report.engine.api.script.instance.IDataSetInstance;

import com.pe.UserException;
import com.pe.dao.DaoManager;
import com.pe.dao.IndicatorDao;
import com.pe.entity.ChatEntity;
import com.pe.operation.AbstractReportOperation;
import com.pe.operation.Operation;
import com.pe.report.ChatAnalysis;
import com.pe.report.ReportEventHandle;
import com.pe.util.FileManager;

public class 查看指标分析图 extends AbstractReportOperation implements Operation, ReportEventHandle
{
	private List<String> portions;
	private Map<String, Integer> pMap1;
	private Map<String, Integer> pMap2;
	
	private String indicatorName;
	private String trainSet;
	
	private int index = 0;
	
	@Override
	public void execute() throws Exception
	{
		validateTrainFile();
		
		ChatEntity result = ChatAnalysis.analysize(indicatorName, trainSet);
		portions = result.getList();
		pMap1 = result.getMap1();
		pMap2 = result.getMap2();
		
		IndicatorDao dao = DaoManager.getInstance().getDao(IndicatorDao.class);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String title = dao.getIndicatorByName(indicatorName).getComment();
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
			row.setColumnValue("正常文件", pMap1.get(portions.get(index)));
			row.setColumnValue("非正常文件", pMap2.get(portions.get(index)));
			
			index++;
			
			return true;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	private void validateTrainFile() throws Exception
	{
		/** 只取文件名，解决ie低版本的安全问题 */
		trainSet = trainSet.replace('\\', '/');
		int k = trainSet.lastIndexOf("/");
		if (k > 0) trainSet = trainSet.substring(k + 1);

		File file = new File(FileManager.getInstance().getPEHome(), trainSet);
		if (!file.isFile()) throw new UserException("训练集文件选取错误，请重新选择！");
		trainSet = file.getAbsolutePath();
	}

	public String getIndicatorName()
	{
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName)
	{
		this.indicatorName = indicatorName;
	}

	public String getTrainSet()
	{
		return trainSet;
	}

	public void setTrainSet(String trainSet)
	{
		this.trainSet = trainSet;
	}
}
