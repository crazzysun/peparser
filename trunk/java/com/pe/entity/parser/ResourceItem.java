package com.pe.entity.parser;

import com.pe.entity.Bean;

public class ResourceItem implements Bean
{
	private static final long serialVersionUID = 1L;

	private String name;			//名称
	private String RAV;				
	private int size;
	
	public int getSize()
	{
		return size;
	}
	public void setSize(int size)
	{
		this.size = size;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getRAV()
	{
		return RAV;
	}
	public void setRAV(String rAV)
	{
		RAV = rAV;
	}

}
