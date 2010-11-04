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

	private String dllName;				//瀵煎嚭鏂囦欢鍚�
	private int functionCount;			//瀵煎嚭鍑芥暟涓暟
	private int functionNameCount;		//瀵煎嚭鏈夊悕鍑芥暟鍚�
	
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
