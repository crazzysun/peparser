package com.pe.operation.PE����;

import java.io.File;
import java.util.List;

import com.pe.UserException;
import com.pe.operation.Operation;
import com.pe.operation.�ļ�.AbstractFileOperation;
import com.pe.parser.ListDirFiles;
import com.pe.util.Zip;

/**
 * ��������ļ�ʱ�����ȼ������Ŀ¼�����е��ļ���������ǰ̨
 * ��ʵ�ֶ��ļ������У�����һ����rpc����һ��
 * @author FangZhiyang
 *
 */
public class װ�ض��ļ�����Ŀ¼  extends AbstractFileOperation implements Operation
{
	private String folderPath;				//Ҫ������zip�ļ���
	private List<String> suffix;			//�ļ�����
	private boolean isDepth;				//�Ƿ������Ŀ¼
	
	private List<String> fileList;			//����������ļ�������
	
	public void execute() throws Exception
	{
		File folder = unzipFolder();
		
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

	private File unzipFolder() throws Exception
	{
		/** ȡ���ļ��� */
		if (!folderPath.endsWith(".zip"))
			throw new Exception("���ϴ����ļ���ʽ����ȷ������Ϊzip��ʽ��");
		
		folderPath = folderPath.replace('\\', '/');
		int k = folderPath.lastIndexOf("/");
		if (k > 0) folderPath  = folderPath.substring(k + 1);
		
		/** �õ���ѹĿ¼����ȥ����.zip�� */
		String folderName = folderPath.substring(0, folderPath.length() - 4);
		String parentPath = getWorkFile("").getAbsolutePath();
		
		/** �ڵ�ǰĿ¼��ѹ�ļ�  */
		Zip.unzip(parentPath + File.separator + folderPath, parentPath + File.separator + folderName);
		
		File folder = new File(getWorkFile(""), folderName);
		return folder;
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
