package com.pe.operation.GBID;

import com.pe.dao.DaoManager;
import com.pe.dao.packed.PackedTrainSetDao;
import com.pe.operation.Operation;

public class É¾³ý¼Ó¿ÇÑµÁ·¼¯ implements Operation
{
	private long trainSetId;
	
	public void execute() throws Exception
	{
		PackedTrainSetDao dao = DaoManager.getInstance().getDao(PackedTrainSetDao.class);
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
