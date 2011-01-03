package com.pe.entity.virus;

import com.pe.entity.Bean;

/**
 * 加壳分析分类器
 * 
 * @author FangZhiyang
 */
public class Classifier implements Bean
{
	private static final long serialVersionUID = 6766643869671590383L;

	private long id;
	private String name;
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
}
