package com.pe.operation.gdlfile;

import com.pe.dao.DaoManager;
import com.pe.dao.gdl.GdlTrainSetDao;
import com.pe.operation.Operation;

public class É¾³ýGDLÑµÁ·¼¯ implements Operation
{
	private long trainSetId;
	
	public void execute() throws Exception
	{
		GdlTrainSetDao dao = DaoManager.getInstance().getDao(GdlTrainSetDao.class);
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
