package com.pe.operation.packed;

import com.pe.dao.DaoManager;
import com.pe.dao.packed.PackedTrainSetDao;
import com.pe.operation.Operation;

public class �жϼӿ�ѵ�����Ƿ���� implements Operation
{
	private String trainSetName;
	private boolean exist;
	
	public void execute() throws Exception
	{
		PackedTrainSetDao trainSetDao = DaoManager.getInstance().getDao(PackedTrainSetDao.class);
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
