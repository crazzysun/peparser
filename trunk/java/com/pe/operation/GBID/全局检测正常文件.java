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
import com.pe.entity.GBID.ChatResult;
import com.pe.entity.GBID.MatchingResult;
import com.pe.entity.GBID.RulesLib;
import com.pe.operation.Operation;
import com.pe.util.FileManager;
import com.pe.util.Serialize;

public class 全局检测正常文件 implements Operation
{
	private static Log log = LogFactory.getLog(全局检测正常文件.class);
	
	private String name;
	private String filePath;
	private long trainSetId;
	private List<MatchingResult> data;		//local
	private int total;
	
	public void execute() throws Exception
	{
		/** 得到模式集 */
		GBIDDao dao = DaoManager.getInstance().getDao(GBIDDao.class);
		RulesLib lib = dao.getLibById(trainSetId);
		
		/** 开始检测 */
		GBIDDll GBID = GBIDDll.INSTANCE;
		data = new ArrayList<MatchingResult>();
		
		/** 检测plus.int */
		validateName();
		plusMatching(lib, GBID);
		
		/** 局部结果保存如数据库，为直方图显示所用 */
		ChatResult result = new ChatResult();
		result.setName(name);
		result.setResult(Serialize.serializeData(data));
		dao.addChatResult1(result);
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
		double[] results2 = new double[10];
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
			MatchingResult result = getResult(results1, results2, i);
			data.add(result);
		}
		
		total = data.size();
	}

	private MatchingResult getResult(double[] results1, double[] results2, int i)
	{
		BigDecimal a = new BigDecimal(results1[i] * 100);
		BigDecimal b = new BigDecimal(results2[i] * 100);
		BigDecimal sub = a.subtract(b);
		
		MatchingResult result = new MatchingResult();
		result.setFileName("plus-" + ( i + 1 ));
		result.setAllRulesRate(a.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
		result.setNtvRulesRate(b.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
		result.setIncreaseRate(sub.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
		return result;
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
}
