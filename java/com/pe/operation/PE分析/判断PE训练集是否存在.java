package com.pe.operation.PE����;

import com.pe.dao.DaoManager;
import com.pe.dao.virus.PETrainSetDao;
import com.pe.operation.Operation;

public class �ж�PEѵ�����Ƿ���� implements Operation
{
	private String trainSetName;
	private boolean exist;
	
	public void execute() throws Exception
	{
		PETrainSetDao trainSetDao = DaoManager.getInstance().getDao(PETrainSetDao.class);
		exist = trainSetDao.isExist(trainSetName);
	}

	public boolean getExist()
	{
		return exist;
	}

	public void setExist(boolean exist)
	{
		this.exist = exist;
	}

	public String getTrainSetName()
	{
		return trainSetName;
	}

	public void setTrainSetName(String trainSetName)
	{
		this.trainSetName = trainSetName;
	}
}
