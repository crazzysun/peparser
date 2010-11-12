package com.pe.entity.parser;

import com.pe.entity.Bean;

/**
 * Êý¾ÝÄ¿Â¼
 *
 * @author FangZhiyang
 */
public class DataDirectory implements Bean
{
	private static final long serialVersionUID = 1L;

	private String name;
	private String virtualAddress;
	private String size;

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getVirtualAddress()
	{
		return virtualAddress;
	}
	public void setVirtualAddress(String virtualAddress)
	{
		this.virtualAddress = virtualAddress;
	}
	public String getSize()
	{
		return size;
	}
	public void setSize(String size)
	{
		this.size = size;
	}
}
