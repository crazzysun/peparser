package com.pe.operation.ELF分析;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.pe.elfParser.CreateHTML;
import com.pe.elfParser.Ielf;
import com.pe.elfParser.elfFactory;
import com.pe.entity.parser.MultiAnlysRslt;
import com.pe.operation.Operation;



public class 分析多个文件 implements Operation
{
	private String filePath;				//分析文件路径
	private List<MultiAnlysRslt> data;		//分析过的文件名数组
	private Ielf elfFile;
	
	public void execute() throws Exception
	{
		/** 装载PE文件分析结果 */
		File file = new File(filePath);
		elfFactory factory = new elfFactory();
		elfFile = factory.create(filePath);
		data = new ArrayList<MultiAnlysRslt>();
		MultiAnlysRslt result = new MultiAnlysRslt();
		if (elfFile.startParse())
		{
			/** 生成页面文件 */
			String path = filePath;
			path = path.substring(0, path.lastIndexOf("\\"));					//取得父文件夹路径
			String parentFolder = path.substring(path.lastIndexOf("\\") + 1);	//取得父文件夹名称
			if (parentFolder.contains(":"))
				parentFolder = "";												//父文件夹为盘符的情况
			CreateHTML html = new CreateHTML(elfFile, parentFolder,file.getName());
			html.create();
			
			result.setPath(filePath);
			result.setName(file.getName());
			result.setSize("15");
			result.setStatus("分析完成");
			result.setParentFolder(parentFolder);
		}
		else
		{
			result.setPath(filePath);
			result.setSize("未知");
			result.setStatus("不是有效的PE文件");
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
