package com.pe.dao;

import java.util.List;

import com.pe.entity.Bean;

public class PagedList<T> implements Bean
{
	private static final long serialVersionUID = -4229521084456910164L;

	private List<T> data;
	private int total;
	private int offset;
	private int limit;
	private String order;

	public List<T> getData()
	{
		return data;
	}

	public void setData(List<T> list)
	{
		this.data = list;
	}

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public int getOffset()
	{
		return offset;
	}

	public void setOffset(int offset)
	{
		this.offset = offset;
	}

	public int getLimit()
	{
		return limit;
	}

	public void setLimit(int limit)
	{
		this.limit = limit;
	}

	public String getOrder()
	{
		return order;
	}

	public void setOrder(String order)
	{
		this.order = order;
	}
}
