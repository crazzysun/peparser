package com.pe.entity.GBID;

import com.pe.entity.Bean;

/***
 * 
 * @author FangZhiyang
 *
 */
public class ChatResult implements Bean
{
	private static final long serialVersionUID = 876156468017194042L;

	private String name;
	private byte[] result;
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public byte[] getResult()
	{
		return result;
	}
	public void setResult(byte[] result)
	{
		this.result = result;
	}
}
