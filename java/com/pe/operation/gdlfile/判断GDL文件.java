package com.pe.operation.gdlfile;

import java.util.List;

import com.pe.dao.DaoManager;
import com.pe.dao.gdl.GdlTrainSetDao;
import com.pe.entity.gdl.JudgedResult;
import com.pe.entity.gdl.GdlResult;
import com.pe.entity.gdl.GdlTrainSet;
import com.pe.operation.Operation;
import com.pe.operation.文件.AbstractFileOperation;
import com.pe.gdl.JudgeGdl;
import com.pe.util.Serialize;

public class 判断GDL文件 extends AbstractFileOperation implements Operation
{
	private String name; // 要判断的文件
	private long trainSetId; // 已有的训练集
	private List<JudgedResult> data;

	public void execute() throws Exception
	{
		/** 得到训练集 */
		GdlTrainSetDao dao = DaoManager.getInstance().getDao(GdlTrainSetDao.class);
		GdlTrainSet trainSet = dao.getTrainSetById(trainSetId);
		GdlResult rst = (GdlResult) Serialize.deSerializeData(trainSet.getResult());

		/** 开始判断 */
		JudgeGdl judgeGdl = new JudgeGdl(rst, name);
		data = judgeGdl.judge();

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
