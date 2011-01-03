package com.pe.operation.ELF·ÖÎö;

import com.pe.dao.DaoManager;
import com.pe.dao.virus.ELFTrainSetDao;
import com.pe.operation.Operation;

public class É¾³ýELFÑµÁ·¼¯ implements Operation
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
