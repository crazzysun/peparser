package com.pe.operation.PE分析;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.pe.entity.parser.MultiAnlysRslt;
import com.pe.entity.parser.PEFile;
import com.pe.operation.Operation;
import com.pe.parser.CreateHTML;
import com.pe.parser.LoadPEInfo;

public class 分析多个文件 implements Operation
{
	private String filePath;				//分析文件路径
	private List<MultiAnlysRslt> data;		//分析过的文件名数组
	
	public void execute() throws Exception
	{
		/** 装载PE文件分析结果 */
		File file = new File(filePath);
		LoadPEInfo loadPEInfo = new LoadPEInfo(file);
		PEFile peFile = loadPEInfo.Analyze();
		
		data = new ArrayList<MultiAnlysRslt>();
		MultiAnlysRslt result = new MultiAnlysRslt();
		if (peFile == null)
		{
			result.setPath(filePath);
			result.setSize("未知");
			result.setStatus("不是有效的PE文件");
		}
		else
		{
			/** 生成页面文件 */
			String path = filePath;
			path = path.substring(0, path.lastIndexOf("\\"));					//取得父文件夹路径
			String parentFolder = path.substring(path.lastIndexOf("\\") + 1);	//取得父文件夹名称
			if (parentFolder.contains(":"))
				parentFolder = "";												//父文件夹为盘符的情况
			CreateHTML html = new CreateHTML(peFile, parentFolder);
			html.create();
			
			result.setPath(filePath);
			result.setName(file.getName());
			result.setSize(peFile.getFileInfo().getFileSize());
			result.setStatus("分析完成");
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
