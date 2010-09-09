package com.pe.operation.PE����;

import java.io.File;
import java.util.List;

import com.pe.UserException;
import com.pe.operation.Operation;
import com.pe.parser.ListDirFiles;

/**
 * ��������ļ�ʱ�����ȼ������Ŀ¼�����е��ļ���������ǰ̨
 * ��ʵ�ֶ��ļ������У�����һ����rpc����һ��
 * @author FangZhiyang
 *
 */
public class װ�ض��ļ�����Ŀ¼ implements Operation
{
	private String folderPath;				//�����ļ���·��
	private List<String> suffix;			//�ļ�����
	private boolean isDepth;				//�Ƿ������Ŀ¼
	
	private List<String> fileList;			//����������ļ�������
	
	public void execute() throws Exception
	{
		File folder = new File(folderPath);
		if (folder == null || !folder.isDirectory())
		{
			throw new UserException("������ļ���·��\"" + folderPath + "\"�����������������");
		}
		
		ListDirFiles.initalize();		
		
		/** �г�ָ��Ŀ¼�������ļ� */
		for (File f : folder.listFiles())
		{
			fileList = ListDirFiles.listFile(f, suffix, isDepth);
		}
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

	public List<String> getSuffix()
	{
		return suffix;
	}

	public void setSuffix(List<String> suffix)
	{
		this.suffix = suffix;
	}

	public boolean getIsDepth()
	{
		return isDepth;
	}

	public void setIsDepth(boolean isDepth)
	{
		this.isDepth = isDepth;
	}
}
