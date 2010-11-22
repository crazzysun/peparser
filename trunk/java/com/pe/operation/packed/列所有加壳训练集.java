package com.pe.operation.packed;

import java.util.List;

import com.pe.dao.DaoManager;
import com.pe.dao.PagedList;
import com.pe.dao.packed.PackedTrainSetDao;
import com.pe.entity.packed.PackedTrainSet;
import com.pe.operation.Operation;

public class 列所有加壳训练集 implements Operation
{
	private List<PackedTrainSet> data;
	private int limit;
	private int offset;
	private int total;
	
	public void execute() throws Exception
	{
		PackedTrainSetDao dao = DaoManager.getInstance().getDao(PackedTrainSetDao.class);
		PagedList<PackedTrainSet> list = dao.listTrainSet(offset, limit);
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

	public List<PackedTrainSet> getData()
	{
		return data;
	}

	public void setData(List<PackedTrainSet> data)
	{
		this.data = data;
	}
}
