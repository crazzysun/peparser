package com.pe.operation.gdlfile;

import java.util.List;

import com.pe.dao.DaoManager;
import com.pe.dao.PagedList;
import com.pe.dao.gdl.GdlTrainSetDao;
import com.pe.entity.gdl.GdlTrainSet;
import com.pe.operation.Operation;

public class 列GDL所有训练集 implements Operation
{
	private List<GdlTrainSet> data;
	private int limit;
	private int offset;
	private int total;
	
	public void execute() throws Exception
	{
		GdlTrainSetDao dao = DaoManager.getInstance().getDao(GdlTrainSetDao.class);
		PagedList<GdlTrainSet> list = dao.listTrainSet(offset, limit);
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

	public List<GdlTrainSet> getData()
	{
		return data;
	}

	public void setData(List<GdlTrainSet> data)
	{
		this.data = data;
	}
}
