package com.pe.entity;

/**
 * arff文件的指标
 * 
 * @author FangZhiyang
 *
 */
public class Indicator implements Bean
{
	private static final long serialVersionUID = 8198803261102067819L;

	private long id;
	private String name;
	private String comment;
	
	
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
	public String getComment()
	{
		return comment;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}
}
