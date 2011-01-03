package com.pe.operation.PE分析;

import java.util.List;

import com.pe.dao.DaoManager;
import com.pe.dao.virus.PETrainSetDao;
import com.pe.entity.virus.VirusResult;
import com.pe.entity.virus.JudgedResult;
import com.pe.entity.virus.PETrainSet;
import com.pe.operation.Operation;
import com.pe.operation.文件.AbstractFileOperation;
import com.pe.virus.JudgePEVirus;
import com.pe.util.Serialize;

public class 判断文件是否是PE恶意软件 extends AbstractFileOperation implements Operation
{
	private String name;
	private long trainSetId;
	private List<JudgedResult> data;
	private int total;
	public void execute() throws Exception
	{
		/** 得到训练集 */
		PETrainSetDao dao = DaoManager.getInstance().getDao(PETrainSetDao.class);
		PETrainSet trainSet = dao.getTrainSetById(trainSetId);
		VirusResult rst = (VirusResult)Serialize.deSerializeData(trainSet.getResult());
		
		/** 开始判断 */
		JudgePEVirus judgeELFVirus = new JudgePEVirus(rst, name);
		data = judgeELFVirus.judge();
		total = data.size();
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

	public List<JudgedResult> getData()
	{
		return data;
	}

	public void setData(List<JudgedResult> data)
	{
		this.data = data;
	}
	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}
}
