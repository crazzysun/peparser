package com.entity.parser;

import java.util.List;

import com.entity.Bean;

/**
 * 导出表
 * @author FangZhiyang
 *
 */
public class ExportTable implements Bean
{
	private static final long serialVersionUID = 1L;

	private String dllName;
	private int functionNum;
	private int functionNameNum;
	private List<ExportFunction> exportFunction;
	
	public String getDllName()
	{
		return dllName;
	}
	public void setDllName(String dllName)
	{
		this.dllName = dllName;
	}
	public int getFunctionNum()
	{
		return functionNum;
	}
	public void setFunctionNum(int functionNum)
	{
		this.functionNum = functionNum;
	}
	public int getFunctionNameNum()
	{
		return functionNameNum;
	}
	public void setFunctionNameNum(int functionNameNum)
	{
		this.functionNameNum = functionNameNum;
	}
	public List<ExportFunction> getExportFunction()
	{
		return exportFunction;
	}
	public void setExportFunction(List<ExportFunction> exportFunction)
	{
		this.exportFunction = exportFunction;
	}
}
