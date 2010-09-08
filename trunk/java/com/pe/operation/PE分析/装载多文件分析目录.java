package com.pe.operation.PE����;

import java.util.List;

import com.pe.UserException;
import com.pe.operation.Operation;
import com.pe.parser.ListDirFiles;

public class װ�ض��ļ�����Ŀ¼ implements Operation
{
	private String folderPath;				//�����ļ���·��
	private String suffix;					//�ļ�����
	private boolean isDepth;				//�Ƿ������Ŀ¼
	
	private List<String> fileList;			//����������ļ�������
	
	public void execute() throws Exception
	{
		/** �г�ָ��Ŀ¼�������ļ� */
		fileList = ListDirFiles.getListFiles(folderPath, suffix, isDepth);
		if (fileList.isEmpty())
		{
			throw new UserException("ָ��Ŀ¼��û�з���Ҫ����ļ�!");
		}
	}
	
	public String getFolderPath()
	{
		return folderPath;
	}

	public void setFolderPath(String folderPath)
	{
		this.folderPath = folderPath;
	}

	public List<String> getFileList()
	{
		return fileList;
	}

	public void setFileList(List<String> fileList)
	{
		this.fileList = fileList;
	}

	public String getSuffix()
	{
		return suffix;
	}

	public void setSuffix(String suffix)
	{
		this.suffix = suffix;
	}

	public boolean isDepth()
	{
		return isDepth;
	}

	public void setDepth(boolean isDepth)
	{
		this.isDepth = isDepth;
	}
}
