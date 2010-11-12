package com.pe.entity.parser;

import java.util.List;

import com.pe.entity.Bean;

/**
 * ��Դ
 *
 * @author FangZhiyang
 */
public class Resource implements Bean
{
	private static final long serialVersionUID = 1L;

	private String recType;				//��Դ����
	private int recCount;			//��Դ����
	private List<ResourceItem> item;
	
	public String getRecType()
	{
		return recType;
	}
	public void setRecType(String recType)
	{
		this.recType = recType;
	}
	public int getRecCount()
	{
		return recCount;
	}
	public void setRecCount(int recCount)
	{
		this.recCount = recCount;
	}
	public List<ResourceItem> getItem()
	{
		return item;
	}
	public void setItem(List<ResourceItem> item)
	{
		this.item = item;
	}
}
