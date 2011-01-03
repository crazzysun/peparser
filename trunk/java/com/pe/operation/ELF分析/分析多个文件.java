package com.pe.operation.ELF����;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.pe.elfParser.CreateHTML;
import com.pe.elfParser.Ielf;
import com.pe.elfParser.elfFactory;
import com.pe.entity.parser.MultiAnlysRslt;
import com.pe.operation.Operation;



public class ��������ļ� implements Operation
{
	private String filePath;				//�����ļ�·��
	private List<MultiAnlysRslt> data;		//���������ļ�������
	private Ielf elfFile;
	
	public void execute() throws Exception
	{
		/** װ��PE�ļ�������� */
		File file = new File(filePath);
		elfFactory factory = new elfFactory();
		elfFile = factory.create(filePath);
		data = new ArrayList<MultiAnlysRslt>();
		MultiAnlysRslt result = new MultiAnlysRslt();
		if (elfFile.startParse())
		{
			/** ����ҳ���ļ� */
			String path = filePath;
			path = path.substring(0, path.lastIndexOf("\\"));					//ȡ�ø��ļ���·��
			String parentFolder = path.substring(path.lastIndexOf("\\") + 1);	//ȡ�ø��ļ�������
			if (parentFolder.contains(":"))
				parentFolder = "";												//���ļ���Ϊ�̷������
			CreateHTML html = new CreateHTML(elfFile, parentFolder,file.getName());
			html.create();
			
			result.setPath(filePath);
			result.setName(file.getName());
			result.setSize("15");
			result.setStatus("�������");
			result.setParentFolder(parentFolder);
		}
		else
		{
			result.setPath(filePath);
			result.setSize("δ֪");
			result.setStatus("������Ч��PE�ļ�");
		}
		data.add(result);
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public List<MultiAnlysRslt> getData()
	{
		return data;
	}

	public void setData(List<MultiAnlysRslt> data)
	{
		this.data = data;
	}
}
