package com.pe.entity.virus;

import com.pe.entity.Bean;

/**
 * �ж������Ƿ�ӿǵĽ��
 * 
 * @author FangZhiyang
 *
 */
public class JudgedResult implements Bean
{
	private static final long serialVersionUID = 3093848875062075436L;
	
	private String name;
	private String result;
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getResult()
	{
		return result;
	}
	public void setResult(String result)
	{
		this.result = result;
	}
}
