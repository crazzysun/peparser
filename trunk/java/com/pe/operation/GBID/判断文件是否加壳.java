package com.pe.operation.GBID;

import java.util.List;

import com.pe.dao.DaoManager;
import com.pe.dao.packed.PackedTrainSetDao;
import com.pe.entity.packed.JudgedResult;
import com.pe.entity.packed.PackedResult;
import com.pe.entity.packed.PackedTrainSet;
import com.pe.operation.Operation;
import com.pe.packed.JudgePacked;
import com.pe.util.Serialize;

public class �ж��ļ��Ƿ�ӿ� implements Operation
{
	private String name;
	private long trainSetId;
	private List<JudgedResult> data;
	private int total;
	
	public void execute() throws Exception
	{
		/** �õ�ѵ���� */
		PackedTrainSetDao dao = DaoManager.getInstance().getDao(PackedTrainSetDao.class);
		PackedTrainSet trainSet = dao.getTrainSetById(trainSetId);
		PackedResult rst = (PackedResult)Serialize.deSerializeData(trainSet.getResult());
		
		/** ��ʼ�ж� */
		JudgePacked judgePacked = new JudgePacked(rst, name);
		data = judgePacked.judge();
		total = data.size();
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public long getTrainSetId()
	{
		return trainSetId;
	}

	public void setTrainSetId(long trainSetId)
	{
		this.trainSetId = trainSetId;
	}

	public List<JudgedResult> getData()
	{
		return data;
	}

	public void setData(List<JudgedResult> data)
	{
		this.data = data;
	}

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}
}
