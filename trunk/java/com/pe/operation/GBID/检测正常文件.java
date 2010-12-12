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

public class 检测正常文件 implements Operation
{
	private static Log log = LogFactory.getLog(检测正常文件.class);
	
	private String name;
	private String filePath;
	private long trainSetId;
	private List<MatchingResult> data;		//local
	private List<MatchingResult> data1;		//global
	private int total;
	
	public void execute() throws Exception
	{
		/** 得到训练集 */
		GBIDDao dao = DaoManager.getInstance().getDao(GBIDDao.class);
		RulesLib lib = dao.getLibById(trainSetId);
		
		/** 开始检测 */
		GBIDDll GBID = GBIDDll.INSTANCE;
		data = new ArrayList<MatchingResult>();
		data1 = new ArrayList<MatchingResult>();
		
		/** 检测plus.int */
		validateName();
		plusMatching(lib, GBID);
	}

	private void validateName()
	{
		/** 只取文件名，解决ie低版本的安全问题 */
		name = name.replace('\\', '/');
		int k = name.lastIndexOf("/");
		if (k > 0) name = name.substring(k + 1);
		
		File file = new File(FileManager.getInstance().getPEHome(), name);
		filePath = file.getAbsolutePath();
	}

	/** 对plus文件进行检测 */
	private void plusMatching(RulesLib lib, GBIDDll GBID)
	{
		//local
		double[] results1 = new double[10];
		if (log.isTraceEnabled())
			log.trace("正在用带衍生规则的模式集对plus文件进行局部检测...");
		GBID.PlusLocal(filePath, lib.getAllLibFilePath(), results1);
		
		double[] results2 = new double[10];
		if (log.isTraceEnabled())
			log.trace("正在用不带衍生规则的模式集对plus文件进行局部检测...");
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
			log.trace("正在用带衍生规则的模式集对plus文件进行全局检测...");
		GBID.PlusGlobal(filePath, lib.getAllLibFilePath(), results1);
		
		results2 = new double[10];
		if (log.isTraceEnabled())
			log.trace("正在用不带衍生规则的模式集对plus文件进行全局检测...");
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
	
	/** 计算比率增幅 */
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
