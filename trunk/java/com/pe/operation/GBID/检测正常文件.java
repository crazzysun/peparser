package com.pe.operation.GBID;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pe.dao.DaoManager;
import com.pe.dao.GBID.GBIDDao;
import com.pe.dll.petest.GBIDDll;
import com.pe.entity.GBID.MatchingResult;
import com.pe.entity.GBID.RulesLib;
import com.pe.operation.Operation;
import com.pe.util.FileManager;

public class ��������ļ� implements Operation
{
	private static Log log = LogFactory.getLog(��������ļ�.class);
	
	private String name;
	private String filePath;
	private long trainSetId;
	private List<MatchingResult> data;		//local
	private List<MatchingResult> data1;		//global
	private int total;
	
	public void execute() throws Exception
	{
		/** �õ�ѵ���� */
		GBIDDao dao = DaoManager.getInstance().getDao(GBIDDao.class);
		RulesLib lib = dao.getLibById(trainSetId);
		
		/** ��ʼ��� */
		GBIDDll GBID = GBIDDll.INSTANCE;
		data = new ArrayList<MatchingResult>();
		data1 = new ArrayList<MatchingResult>();
		
		/** ���plus.int */
		validateName();
		plusMatching(lib, GBID);
	}

	private void validateName()
	{
		/** ֻȡ�ļ��������ie�Ͱ汾�İ�ȫ���� */
		name = name.replace('\\', '/');
		int k = name.lastIndexOf("/");
		if (k > 0) name = name.substring(k + 1);
		
		File file = new File(FileManager.getInstance().getPEHome(), name);
		filePath = file.getAbsolutePath();
	}

	/** ��plus�ļ����м�� */
	private void plusMatching(RulesLib lib, GBIDDll GBID)
	{
		//local
		double[] results1 = new double[10];
		if (log.isTraceEnabled())
			log.trace("�����ô����������ģʽ����plus�ļ����оֲ����...");
		GBID.PlusLocal(filePath, lib.getAllLibFilePath(), results1);
		
		double[] results2 = new double[10];
		if (log.isTraceEnabled())
			log.trace("�����ò������������ģʽ����plus�ļ����оֲ����...");
		GBID.PlusLocal(filePath, lib.getNtvLibFilePath(), results2);
		
		for (int i = 0; i < 10; i++)
		{
			MatchingResult result = new MatchingResult();
			result.setFileName("plus-" + ( i + 1 ));
			result.setAllRulesRate(validateRate(results1[i]));
			result.setNtvRulesRate(validateRate(results2[i]));
			result.setIncreaseRate(getIncreaseRate(result.getAllRulesRate(), result.getNtvRulesRate()));
			
			data.add(result);
		}
		
		//global
		results1 = new double[10];
		if (log.isTraceEnabled())
			log.trace("�����ô����������ģʽ����plus�ļ�����ȫ�ּ��...");
		GBID.PlusGlobal(filePath, lib.getAllLibFilePath(), results1);
		
		results2 = new double[10];
		if (log.isTraceEnabled())
			log.trace("�����ò������������ģʽ����plus�ļ�����ȫ�ּ��...");
		GBID.PlusGlobal(filePath, lib.getNtvLibFilePath(), results2);
		
		for (int i = 0; i < 10; i++)
		{
			MatchingResult result = new MatchingResult();
			result.setFileName("plus-" + ( i + 1 ));
			result.setAllRulesRate(validateRate(results1[i]));
			result.setNtvRulesRate(validateRate(results2[i]));
			result.setIncreaseRate(getIncreaseRate(result.getAllRulesRate(), result.getNtvRulesRate()));
			
			data1.add(result);
		}
		
		total = 10;
	}
	
	private String validateRate(double d)
	{
		BigDecimal a = new BigDecimal(d*100);   
		return a.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%";
	}
	
	/** ����������� */
	private String getIncreaseRate(String all, String ntv)
	{
		BigDecimal a = new BigDecimal(all);
		BigDecimal b = new BigDecimal(ntv);
		
		return a.subtract(b).toString();
	}
	
	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public long getTrainSetId()
	{
		return trainSetId;
	}

	public void setTrainSetId(long trainSetId)
	{
		this.trainSetId = trainSetId;
	}

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public List<MatchingResult> getData()
	{
		return data;
	}

	public void setData(List<MatchingResult> data)
	{
		this.data = data;
	}

	public List<MatchingResult> getData1()
	{
		return data1;
	}

	public void setData1(List<MatchingResult> data1)
	{
		this.data1 = data1;
	}
}
