package com.pe.operation.PE分析;

import java.util.List;

import com.pe.dao.DaoManager;
import com.pe.dao.PagedList;
import com.pe.dao.virus.PETrainSetDao;
import com.pe.entity.virus.PETrainSet;
import com.pe.operation.Operation;

public class 列PE恶意软件所有训练集 implements Operation
{
	private List<PETrainSet> data;
	private int limit;
	private int offset;
	private int total;
	
	public void execute() throws Exception
	{
		PETrainSetDao dao = DaoManager.getInstance().getDao(PETrainSetDao.class);
		PagedList<PETrainSet> list = dao.listTrainSet(offset, limit);
		data = list.getData();
		total = list.getTotal();
	}

	public int getLimit()
	{
		return limit;
	}

	public void setLimit(int limit)
	{
		this.limit = limit;
	}

	public int getOffset()
	{
		return offset;
	}

	public void setOffset(int offset)
	{
		this.offset = offset;
	}

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public List<PETrainSet> getData()
	{
		return data;
	}

	public void setData(List<PETrainSet> data)
	{
		this.data = data;
	}
}
