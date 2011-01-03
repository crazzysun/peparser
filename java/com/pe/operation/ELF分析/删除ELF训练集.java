package com.pe.operation.ELF����;

import com.pe.dao.DaoManager;
import com.pe.dao.virus.ELFTrainSetDao;
import com.pe.operation.Operation;

public class ɾ��ELFѵ���� implements Operation
{
	private long trainSetId;
	
	public void execute() throws Exception
	{
		ELFTrainSetDao dao = DaoManager.getInstance().getDao(ELFTrainSetDao.class);
		dao.deleteTrainSet(trainSetId);
	}

	public long getTrainSetId()
	{
		return trainSetId;
	}

	public void setTrainSetId(long trainSetId)
	{
		this.trainSetId = trainSetId;
	}
}
