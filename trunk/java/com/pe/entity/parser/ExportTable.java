package com.pe.entity.parser;

import java.util.List;

import com.pe.entity.Bean;

/**
 * 导出表
 * @author FangZhiyang
 *
 */
public class ExportTable implements Bean
{
	private static final long serialVersionUID = 1L;

	private String dllName;				//导出文件名
	private int functionCount;			//导出函数个数
	private int functionNameCount;		//导出有名函数名
	
	private List<ExportFunction> exportFunction;
	
	public String getDllName()
	{
		return dllName;
	}
	public void setDllName(String dllName)
	{
		this.dllName = dllName;
	}
	public int getFunctionCount()
	{
		return functionCount;
	}
	public void setFunctionCount(int functionCount)
	{
		this.functionCount = functionCount;
	}
	public int getFunctionNameCount()
	{
		return functionNameCount;
	}
	public void setFunctionNameCount(int functionNameCount)
	{
		this.functionNameCount = functionNameCount;
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
