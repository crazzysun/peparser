package com.pe.operation.PE分析;

import com.pe.operation.Operation;
import com.pe.operation.文件.AbstractFileOperation;

public class 检测单个文件是否加壳 extends AbstractFileOperation implements Operation
{
	private String name;
	
	public void execute() throws Exception
	{
		
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
