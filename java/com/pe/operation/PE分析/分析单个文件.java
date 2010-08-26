package com.pe.operation.PE分析;

import com.pe.operation.Operation;

public class 分析单个文件 implements Operation
{
	private String name;
	
	public void execute() throws Exception
	{
		System.out.println("操作执行成功！");
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
