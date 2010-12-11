package com.pe.entity.GBID;

import com.pe.entity.Bean;

/**
 * ģʽ��
 * 
 * @author FangZhiyang
 *
 */
public class RulesLib implements Bean
{
	private static final long serialVersionUID = 6622207450978306252L;

	private long id;
	private String name;
	private String allLibFilePath;			//�����������ģʽ�������·��
	private String ntvLibFilePath;			//�������������ģʽ�������·��
	private String createTime;
	
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
	
	public String getAllLibFilePath()
	{
		return allLibFilePath;
	}
	public void setAllLibFilePath(String allLibFilePath)
	{
		this.allLibFilePath = allLibFilePath;
	}
	public String getNtvLibFilePath()
	{
		return ntvLibFilePath;
	}
	public void setNtvLibFilePath(String ntvLibFilePath)
	{
		this.ntvLibFilePath = ntvLibFilePath;
	}
	public String getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
}
