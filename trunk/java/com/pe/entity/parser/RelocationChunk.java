package com.pe.entity.parser;

import com.pe.entity.Bean;

public class RelocationChunk implements Bean
{
	private static final long serialVersionUID = 1L;
	
	private int index;			//���
	private String RVA;			//��������
	private String farAddress;	//������
	private String type;		//����
	
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
