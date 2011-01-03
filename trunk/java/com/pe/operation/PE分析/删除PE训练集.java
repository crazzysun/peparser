package com.pe.operation.PE·ÖÎö;

import com.pe.dao.DaoManager;
import com.pe.dao.virus.PETrainSetDao;
import com.pe.operation.Operation;

public class É¾³ýPEÑµÁ·¼¯ implements Operation
{
	private long trainSetId;
	
	public void execute() throws Exception
	{
		PETrainSetDao dao = DaoManager.getInstance().getDao(PETrainSetDao.class);
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
