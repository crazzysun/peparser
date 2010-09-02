package com.pe.entity.parser;

import com.pe.entity.Bean;

public class RelocationChunk implements Bean
{
	private static final long serialVersionUID = 1L;
	
	private int index;			//编号
	private String RVA;			//所属块名
	private String farAddress;	//块数量
	private String type;		//类型
	
	public int getIndex()
	{
		return index;
	}
	public void setIndex(int index)
	{
		this.index = index;
	}
	public String getRVA()
	{
		return RVA;
	}
	public void setRVA(String rVA)
	{
		RVA = rVA;
	}
	public String getFarAddress()
	{
		return farAddress;
	}
	public void setFarAddress(String farAddress)
	{
		this.farAddress = farAddress;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
}
