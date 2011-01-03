package com.pe.operation.PE·ÖÎö;

import com.pe.dao.DaoManager;
import com.pe.dao.virus.PETrainSetDao;
import com.pe.operation.Operation;

public class ÅÐ¶ÏPEÑµÁ·¼¯ÊÇ·ñ´æÔÚ implements Operation
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
