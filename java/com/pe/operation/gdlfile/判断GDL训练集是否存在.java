package com.pe.operation.gdlfile;

import com.pe.dao.DaoManager;
import com.pe.dao.gdl.GdlTrainSetDao;
import com.pe.operation.Operation;

public class ÅÐ¶ÏGDLÑµÁ·¼¯ÊÇ·ñ´æÔÚ implements Operation
{
	private String trainSetName;
	private boolean exist;
	
	public void execute() throws Exception
	{
		GdlTrainSetDao trainSetDao = DaoManager.getInstance().getDao(GdlTrainSetDao.class);
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
