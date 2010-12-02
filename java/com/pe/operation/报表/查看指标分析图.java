package com.pe.operation.����;

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

public class �鿴ָ�����ͼ extends AbstractReportOperation implements Operation, ReportEventHandle
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
			row.setColumnValue("�ȷ���", portions.get(index));
			row.setColumnValue("�����ļ�", pMap1.get(portions.get(index)));
			row.setColumnValue("�������ļ�", pMap2.get(portions.get(index)));
			
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
		/** ֻȡ�ļ��������ie�Ͱ汾�İ�ȫ���� */
		trainSet = trainSet.replace('\\', '/');
		int k = trainSet.lastIndexOf("/");
		if (k > 0) trainSet = trainSet.substring(k + 1);

		File file = new File(FileManager.getInstance().getPEHome(), trainSet);
		if (!file.isFile()) throw new UserException("ѵ�����ļ�ѡȡ����������ѡ��");
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
