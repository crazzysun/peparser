package com.pe.operation.ELF·ÖÎö;

import com.pe.dao.DaoManager;
import com.pe.dao.virus.ELFTrainSetDao;
import com.pe.operation.Operation;

public class ÅÐ¶ÏELFÑµÁ·¼¯ÊÇ·ñ´æÔÚ implements Operation
{
	private String trainSetName;
	private boolean exist;
	
	public void execute() throws Exception
	{
		ELFTrainSetDao trainSetDao = DaoManager.getInstance().getDao(ELFTrainSetDao.class);
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
