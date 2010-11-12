package com.pe.operation.PE分析;

import java.util.List;

import com.pe.dao.DaoManager;
import com.pe.dao.packed.PackedTrainSetDao;
import com.pe.entity.packed.JudgedResult;
import com.pe.entity.packed.PackedResult;
import com.pe.entity.packed.PackedTrainSet;
import com.pe.operation.Operation;
import com.pe.operation.文件.AbstractFileOperation;
import com.pe.packed.JudgePacked;
import com.pe.util.Serialize;

public class 判断文件是否加壳 extends AbstractFileOperation implements Operation
{
	private String name;
	private long trainSetId;
	private List<JudgedResult> data;
	
	public void execute() throws Exception
	{
		/** 得到训练集 */
		PackedTrainSetDao dao = DaoManager.getInstance().getDao(PackedTrainSetDao.class);
		PackedTrainSet trainSet = dao.getTrainSetById(trainSetId);
		PackedResult rst = (PackedResult)Serialize.deSerializeData(trainSet.getResult());
		
		/** 开始判断 */
		JudgePacked judgePacked = new JudgePacked(rst, name);
		data = judgePacked.judge();
		
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
}
