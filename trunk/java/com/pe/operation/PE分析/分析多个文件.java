package com.pe.operation.PE分析;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.pe.UserException;
import com.pe.entity.parser.MultiAnlysRslt;
import com.pe.entity.parser.PEFile;
import com.pe.operation.Operation;
import com.pe.parser.CreateHTML;
import com.pe.parser.LoadPEInfo;

public class 分析多个文件 implements Operation
{
	private String folderPath;				//分析文件夹路径
	private List<MultiAnlysRslt> data;		//分析过的文件名数组
	private int total;
	
	public void execute() throws Exception
	{
		File folder = new File(folderPath);
		if (folder == null || !folder.isDirectory())
		{
			throw new UserException("输入的文件夹路径有误，请检测后重新输入");
		}
		
		/** 列出指定目录中所有文件 */
		data = new ArrayList<MultiAnlysRslt>();
		File[] files = folder.listFiles();
		for (File f : files)
		{
			if (!f.isFile()) continue;		//只分析一层目录的文件
			MultiAnlysRslt result = new MultiAnlysRslt();
			
			PEFile peFile = new PEFile();
			/** 装载PE文件分析结果 */
			LoadPEInfo loadPEInfo = new LoadPEInfo(f, false);
			peFile = loadPEInfo.Analyze();
			if (peFile == null)
			{
				result.setName(f.getName());
				result.setSize("未知");
				result.setStatus("不是有效的PE文件");
				data.add(result);
				continue;
			}
			
			/** 生成页面文件 */
			String path = f.getPath();
			path = path.substring(0, path.lastIndexOf("\\"));					//取得父文件夹路径
			String parentFolder = path.substring(path.lastIndexOf("\\") + 1);	//取得父文件夹名称
			CreateHTML html = new CreateHTML(peFile, parentFolder);
			html.create();
			
			result.setName(f.getName());
			result.setSize(peFile.getFileInfo().getFileSize());
			result.setStatus("分析完成");
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
