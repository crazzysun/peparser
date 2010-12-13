package com.pe.operation.GBID;

import java.io.File;
import java.math.BigDecimal;

import com.pe.dao.DaoManager;
import com.pe.dao.GBID.GBIDDao;
import com.pe.dll.petest.GBIDDll;
import com.pe.entity.GBID.MatchingResult;
import com.pe.entity.GBID.RulesLib;
import com.pe.operation.Operation;
import com.pe.util.FileManager;

public class 检测异常文件 implements Operation
{
	private String name;
	private String filePath;
	private long trainSetId;
	private MatchingResult result;
	
	public void execute() throws Exception
	{
		/** 得到训练集 */
		GBIDDao dao = DaoManager.getInstance().getDao(GBIDDao.class);
		RulesLib lib = dao.getLibById(trainSetId);
		
		/** 开始检测 */
		GBIDDll GBID = GBIDDll.INSTANCE;
		
		validateName();
		/** 检测bounce.int 和异常文件 */
		bounceMatching(lib, GBID);
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

	/** 对bounce文件进行检测 */
	private void bounceMatching(RulesLib lib, GBIDDll GBID)
	{
		double allRate = GBID.FileMatching(filePath, lib.getAllLibFilePath());
		double ntvRate = GBID.FileMatching(filePath, lib.getNtvLibFilePath());
		BigDecimal a = new BigDecimal(allRate * 100);
		BigDecimal b = new BigDecimal(ntvRate * 100);
		BigDecimal sub = a.subtract(b);
		
		result = new MatchingResult();
		result.setFileName(name);
		result.setAllRulesRate(a.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
		result.setNtvRulesRate(b.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
		result.setIncreaseRate(sub.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
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

	public MatchingResult getResult()
	{
		return result;
	}

	public void setResult(MatchingResult result)
	{
		this.result = result;
	}
}
