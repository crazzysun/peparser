package com.pe.entity.parser;

import java.util.List;

import com.pe.entity.Bean;

/**
 * 重定义块
 *
 * @author FangZhiyang
 */
public class Relocation implements Bean
{
	private static final long serialVersionUID = 1L;

	private int itemIndex;			//编号
	private String sectionName;		//所属块名
	private int recCount;				//块数量
	private List<RelocationChunk> relocChunk;
	
	public int getItemIndex()
	{
		return itemIndex;
	}
	public void setItemIndex(int itemIndex)
	{
		this.itemIndex = itemIndex;
	}
	public String getSectionName()
	{
		return sectionName;
	}
	public void setSectionName(String sectionName)
	{
		this.sectionName = sectionName;
	}
	public List<RelocationChunk> getRelocChunk()
	{
		return relocChunk;
	}
	public void setRelocChunk(List<RelocationChunk> relocChunk)
	{
		this.relocChunk = relocChunk;
	}
	public int getRecCount()
	{
		return recCount;
	}
	public void setRecCount(int recCount)
	{
		this.recCount = recCount;
	}
}
