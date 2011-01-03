package com.pe.operation.ELF分析;

import java.util.List;

import com.pe.dao.DaoManager;
import com.pe.dao.PagedList;
import com.pe.dao.virus.ELFTrainSetDao;
import com.pe.entity.virus.ELFTrainSet;
import com.pe.operation.Operation;

public class 列所有训练集 implements Operation
{
	private List<ELFTrainSet> data;
	private int limit;
	private int offset;
	private int total;
	
	public void execute() throws Exception
	{
		ELFTrainSetDao dao = DaoManager.getInstance().getDao(ELFTrainSetDao.class);
		PagedList<ELFTrainSet> list = dao.listTrainSet(offset, limit);
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

	public List<ELFTrainSet> getData()
	{
		return data;
	}

	public void setData(List<ELFTrainSet> data)
	{
		this.data = data;
	}
}
