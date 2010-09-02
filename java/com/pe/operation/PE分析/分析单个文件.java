package com.pe.operation.PE����;

import java.io.File;

import com.pe.UserException;
import com.pe.entity.parser.PEFile;
import com.pe.operation.Operation;
import com.pe.operation.�ļ�.AbstractFileOperation;
import com.pe.parser.CreateHTML;
import com.pe.parser.LoadPEInfo;

public class ���������ļ� extends AbstractFileOperation implements Operation
{
	private String fileName;
	private PEFile peFile;
	
	public void execute() throws Exception
	{
		File path = getWorkFile("");
		
		/** ����ļ���Ϊ�ջ���null�����׳��쳣 */
		if (fileName == null || fileName.equalsIgnoreCase("")) throw new UserException("�ļ����ϴ�����ǰ̨ҳ���ȡ���ļ���Ϊ�գ�");
		
		/** װ��PE�ļ�������� */
		File file = new File(path, fileName);
		LoadPEInfo loadPEInfo = new LoadPEInfo(file);
		peFile = loadPEInfo.Analyze();
		if (peFile == null) throw new UserException("�ļ�" + fileName + "������Ч��PE�ļ���");
		
		/** ����ҳ���ļ� */
		CreateHTML html = new CreateHTML(peFile);
		html.create();
	}

	public PEFile getPeFile()
	{
		return peFile;
	}

	public void setPeFile(PEFile peFile)
	{
		this.peFile = peFile;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
}
