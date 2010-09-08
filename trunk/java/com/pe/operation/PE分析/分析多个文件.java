package com.pe.operation.PE����;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.pe.entity.parser.MultiAnlysRslt;
import com.pe.entity.parser.PEFile;
import com.pe.operation.Operation;
import com.pe.parser.CreateHTML;
import com.pe.parser.LoadPEInfo;

public class ��������ļ� implements Operation
{
	private String filePath;				//�����ļ�·��
	private List<MultiAnlysRslt> data;		//���������ļ�������
	
	public void execute() throws Exception
	{
		/** װ��PE�ļ�������� */
		File file = new File(filePath);
		LoadPEInfo loadPEInfo = new LoadPEInfo(file, false);
		PEFile peFile = loadPEInfo.Analyze();
		
		data = new ArrayList<MultiAnlysRslt>();
		MultiAnlysRslt result = new MultiAnlysRslt();
		if (peFile == null)
		{
			result.setPath(filePath);
			result.setSize("δ֪");
			result.setStatus("������Ч��PE�ļ�");
		}
		else
		{
			/** ����ҳ���ļ� */
			String path = filePath;
			path = path.substring(0, path.lastIndexOf("\\"));					//ȡ�ø��ļ���·��
			String parentFolder = path.substring(path.lastIndexOf("\\") + 1);	//ȡ�ø��ļ�������
			if (parentFolder.contains(":"))
				parentFolder = "";												//���ļ���Ϊ�̷������
			CreateHTML html = new CreateHTML(peFile, parentFolder);
			html.create();
			
			result.setPath(filePath);
			result.setName(file.getName());
			result.setSize(peFile.getFileInfo().getFileSize());
			result.setStatus("�������");
			result.setParentFolder(parentFolder);
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
