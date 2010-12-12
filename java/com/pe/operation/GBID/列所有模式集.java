package com.pe.operation.GBID;

import java.util.List;

import com.pe.dao.DaoManager;
import com.pe.dao.PagedList;
import com.pe.dao.GBID.GBIDDao;
import com.pe.entity.GBID.RulesLib;
import com.pe.operation.Operation;

public class 列所有模式集 implements Operation
{
	private List<RulesLib> data;
	private int limit;
	private int offset;
	private int total;
	
	public void execute() throws Exception
	{
		GBIDDao dao = DaoManager.getInstance().getDao(GBIDDao.class);
		PagedList<RulesLib> list = dao.listRulesLib(offset, limit);
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

	public List<RulesLib> getData()
	{
		return data;
	}

	public void setData(List<RulesLib> data)
	{
		this.data = data;
	}
}
