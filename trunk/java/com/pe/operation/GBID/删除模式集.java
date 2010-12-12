package com.pe.operation.GBID;

import java.io.File;

import com.pe.dao.DaoManager;
import com.pe.dao.GBID.GBIDDao;
import com.pe.entity.GBID.RulesLib;
import com.pe.operation.Operation;

public class ɾ��ģʽ�� implements Operation
{
	private long trainSetId;
	
	public void execute() throws Exception
	{
		GBIDDao dao = DaoManager.getInstance().getDao(GBIDDao.class);
		
		RulesLib lib = dao.getLibById(trainSetId);
		dao.deleteRulesLib(trainSetId);
		
		/** ɾ��ģʽ���ļ� */
		File libFile = new File(lib.getAllLibFilePath());
		if (libFile.exists()) libFile.delete();
		else throw new Exception(libFile.getName() + "�����ڣ�ɾ��ʧ�ܣ�");
		libFile = new File(lib.getNtvLibFilePath());
		if (libFile.exists()) libFile.delete();
		else throw new Exception(libFile.getName() + "�����ڣ�ɾ��ʧ�ܣ�");
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
