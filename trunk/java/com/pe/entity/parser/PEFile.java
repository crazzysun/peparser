package com.pe.entity.parser;

import java.util.List;

import com.pe.entity.Bean;

/**
 * pe�ļ��������ṹ
 *
 * @author FangZhiyang
 */
public class PEFile implements Bean
{
	private static final long serialVersionUID = 1L;
	
	private FileInfo fileInfo;					//�ļ���Ϣ
	private List<PEHeader> headers;				//4���ļ�ͷ����Ϣ
	private List<DataDirectory> dataDirectory;	//����Ŀ¼
	private List<SectionHeader> sections;		//�ڱ�ͷ��
	private List<ImportTable> importTable;		//�����n����
	private ExportTable exportTable;			//������
	private List<Relocation> relocation;		//�ض�λ��
	private List<Resource> resource;			//��ԴĿ¼
	
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
