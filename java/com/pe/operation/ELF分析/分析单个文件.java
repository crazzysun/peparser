package com.pe.operation.ELF����;

import java.io.File;
import com.pe.elfParser.*;
import com.pe.UserException;
import com.pe.operation.Operation;
import com.pe.operation.�ļ�.AbstractFileOperation;

public class ���������ļ� extends AbstractFileOperation implements Operation
{
	private String fileName;
	private Ielf elfFile;
	
	public void execute() throws Exception
	{
        File path = getWorkFile("");
		
		/** ����ļ���Ϊ�ջ���null�����׳��쳣 */
		if (fileName == null || fileName.equalsIgnoreCase("")) throw new UserException("�ļ����ϴ�����ǰ̨ҳ���ȡ���ļ���Ϊ�գ�");
		
		/** װ��PE�ļ�������� */
		File file = new File(path, fileName);
		elfFactory factory = new elfFactory();
		elfFile = factory.create(file.getAbsolutePath());
		if (elfFile.startParse())
		{
			if (elfFile == null) throw new UserException("�ļ�" + fileName + "������Ч��ELF�ļ���");
			
			/** ����ҳ���ļ� */
			CreateHTML html = new CreateHTML(elfFile,fileName);
			html.create();
		}
		else
			throw new UserException("�ļ�" + fileName + "������Ч��ELF�ļ���");
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
