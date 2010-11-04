package com.pe.entity.parser;

import java.util.List;

import com.pe.entity.Bean;

/**
 * pe文件的完整结构
 *
 * @author FangZhiyang
 */
public class PEFile implements Bean
{
	private static final long serialVersionUID = 1L;
	
	private FileInfo fileInfo;					//文件信息
	private List<PEHeader> headers;				//4个文件头部信息
	private List<DataDirectory> dataDirectory;	//数据目录
	private List<SectionHeader> sections;		//节表头部
	private List<ImportTable> importTable;		//导入表（n个）
	private ExportTable exportTable;			//导出表
	private List<Relocation> relocation;		//重定位表
	private List<Resource> resource;			//资源目录
	
	public List<DataDirectory> getDataDirectory()
	{
		return dataDirectory;
	}
	public void setDataDirectory(List<DataDirectory> dataDirectory)
	{
		this.dataDirectory = dataDirectory;
	}
	public List<Relocation> getRelocation()
	{
		return relocation;
	}
	public void setRelocation(List<Relocation> relocation)
	{
		this.relocation = relocation;
	}
	public List<Resource> getResource()
	{
		return resource;
	}
	public void setResource(List<Resource> resource)
	{
		this.resource = resource;
	}
	public FileInfo getFileInfo()
	{
		return fileInfo;
	}
	public void setFileInfo(FileInfo fileInfo)
	{
		this.fileInfo = fileInfo;
	}
	public List<PEHeader> getHeaders()
	{
		return headers;
	}
	public void setHeaders(List<PEHeader> headers)
	{
		this.headers = headers;
	}
	public List<SectionHeader> getSections()
	{
		return sections;
	}
	public void setSections(List<SectionHeader> sections)
	{
		this.sections = sections;
	}
	public List<ImportTable> getImportTable()
	{
		return importTable;
	}
	public void setImportTable(List<ImportTable> importTable)
	{
		this.importTable = importTable;
	}
	public ExportTable getExportTable()
	{
		return exportTable;
	}
	public void setExportTable(ExportTable exportTable)
	{
		this.exportTable = exportTable;
	}
}
