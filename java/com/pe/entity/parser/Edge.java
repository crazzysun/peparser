package com.pe.entity.parser;

import com.pe.entity.Bean;

public class Edge implements Bean
{
	private static final long serialVersionUID = 1L;
	
	private String source;
	private String target;
	
	public String getSource()
	{
		return source;
	}
	public void setSource(String source)
	{
		this.source = source;
	}
	public String getTarget()
	{
		return target;
	}
	public void setTarget(String target)
	{
		this.target = target;
	}
}
