package com.pe.operation.PE分析;

import java.io.File;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.RequestDispatcher;

import com.pe.UserException;
import com.pe.dll.petest.PEAnalyzerDll;
import com.pe.entity.parser.FileInfo;
import com.pe.entity.parser.PEHeader;
import com.pe.entity.parser.SectionHeader;
import com.pe.operation.Operation;
import com.pe.operation.文件.AbstractFileOperation;

public class 分析单个文件 extends AbstractFileOperation implements Operation
{
	private String fileName;
	
	public void execute() throws Exception
	{
		File path = getWorkFile("");
		File file = new File(path, fileName);
		
		/** 如果文件名为空或者null，则抛出异常 */
		if (fileName == null || fileName.equalsIgnoreCase("")) throw new UserException("文件已上传，但前台页面获取的文件名为空！");
		
		/** 开始分析文件 */
		PEAnalyzerDll PE = PEAnalyzerDll.INSTANCE;
		
		/** 通过dll加载PE文件 */
		PE.LoadPEHeader(file.getPath());
		
		/** 定义装header的Vector容器 */
		Vector<PEHeader> peHeaders = new Vector<PEHeader>();
		
		/** 装载文件信息 */
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileName(PE.GetFileName());
		fileInfo.setFileSize(PE.GetFileSiz());
		fileInfo.setCreateTime(PE.GetFileCreateTime());
		fileInfo.setModifyTime(PE.GetFileModifyTime());
		
		int headerCount = PE.GetHeaderCount();			//获取header个数
		/** 装载DOS头，PE文件标志，PE文件头，可选头部 */
		PEHeader peHeader;		
		Map<String, String> peDetail;						//用于存放dos头的详细数据：名称->值
		
		for (int i = 0; i < headerCount; i++)
		{
			peHeader = new PEHeader();					
			peDetail = new HashMap<String, String>();
			
			peHeader.setName(PE.GetHeaderName(i));			//保存头name
			
			int memberCount = PE.GetMemberCount(i);		
			for (int j = 0; j < memberCount; j++)
			{
				String memberKey = PE.GetMemberName(i, j);
				String memberValue = "0x" + Integer.toHexString(PE.GetMemberValue(i, j));
				peDetail.put(memberKey, memberValue);
			}
			peHeader.setValue(peDetail);					//保存成员详细信息
			peHeaders.add(peHeader);
		}
		
		/** 装载节表信息 */
		Vector<SectionHeader> sectionHeaders = new Vector<SectionHeader>();	//保存所有节表信息
		SectionHeader sectionHeader;
		Map<String, String> sectionDetail;
		
		int sectionCount = PE.GetSectionCount();

		for (int i = 0; i < sectionCount; i++)
		{
			sectionHeader = new SectionHeader();
			sectionDetail = new HashMap<String, String>();
			
			sectionHeader.setName(PE.GetSectionName(i));	//保存头name
			int memberCount = PE.GetSectionMemberCount(i);
			for (int j = 0; j < memberCount; j++)
			{
				String sectionKey = PE.GetSectionMemberName(i, j);
				String sectionValue = "0x" + Integer.toHexString(PE.GetSectionMemberValue(i, j));
				sectionDetail.put(sectionKey, sectionValue);
			}
			sectionHeader.setValue(sectionDetail);			//保存成员详细信息
			sectionHeaders.add(sectionHeader);
		}
		
		file.delete();
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
