package com.pe.operation.PE����;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.pe.UserException;
import com.pe.entity.parser.MultiAnlysRslt;
import com.pe.entity.parser.PEFile;
import com.pe.operation.Operation;
import com.pe.parser.CreateHTML;
import com.pe.parser.LoadPEInfo;

public class ��������ļ� implements Operation
{
	private String folderPath;				//�����ļ���·��
	private List<MultiAnlysRslt> data;		//���������ļ�������
	private int total;
	
	public void execute() throws Exception
	{
		File folder = new File(folderPath);
		if (folder == null || !folder.isDirectory())
		{
			throw new UserException("������ļ���·�������������������");
		}
		
		/** �г�ָ��Ŀ¼�������ļ� */
		data = new ArrayList<MultiAnlysRslt>();
		File[] files = folder.listFiles();
		for (File f : files)
		{
			if (!f.isFile()) continue;		//ֻ����һ��Ŀ¼���ļ�
			MultiAnlysRslt result = new MultiAnlysRslt();
			
			PEFile peFile = new PEFile();
			/** װ��PE�ļ�������� */
			LoadPEInfo loadPEInfo = new LoadPEInfo(f, false);
			peFile = loadPEInfo.Analyze();
			if (peFile == null)
			{
				result.setName(f.getName());
				result.setSize("δ֪");
				result.setStatus("������Ч��PE�ļ�");
				data.add(result);
				continue;
			}
			
			/** ����ҳ���ļ� */
			String path = f.getPath();
			path = path.substring(0, path.lastIndexOf("\\"));					//ȡ�ø��ļ���·��
			String parentFolder = path.substring(path.lastIndexOf("\\") + 1);	//ȡ�ø��ļ�������
			CreateHTML html = new CreateHTML(peFile, parentFolder);
			html.create();
			
			result.setName(f.getName());
			result.setSize(peFile.getFileInfo().getFileSize());
			result.setStatus("�������");
			result.setParentFolder(parentFolder);
			data.add(result);
		}
		total = data.size();
	}

	public String getFolderPath()
	{
		return folderPath;
	}

	public void setFolderPath(String folderPath)
	{
		this.folderPath = folderPath;
	}

	public List<MultiAnlysRslt> getData()
	{
		return data;
	}

	public void setData(List<MultiAnlysRslt> data)
	{
		this.data = data;
	}

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}
}
