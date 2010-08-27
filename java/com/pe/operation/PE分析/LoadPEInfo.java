package com.pe.operation.PE分析;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pe.UserException;
import com.pe.dll.petest.PEAnalyzerDll;
import com.pe.entity.parser.DataDirectory;
import com.pe.entity.parser.FileInfo;
import com.pe.entity.parser.ImportTable;
import com.pe.entity.parser.PEFile;
import com.pe.entity.parser.PEHeader;
import com.pe.entity.parser.SectionHeader;

public class LoadPEInfo
{
	public static final int IMAGE_NUMBEROF_DIRECTORY_ENTRIES = 16;			//数据目录的个数
	
	private PEAnalyzerDll PE = PEAnalyzerDll.INSTANCE;
	private File file; 
	
	public LoadPEInfo(File file)
	{
		this.file = file; 
	}
	
	/** 开始分析文件 */
	public PEFile Analyze()
	{
		/** 通过dll加载PE文件 */
		PE.LoadPEHeader(file.getPath());
		file.delete();
		return null;
	}
	
	/** 装载文件信息 
	 * @throws UserException */
	private FileInfo LoadFileInfo() throws UserException
	{
		try
		{
			FileInfo fileInfo = new FileInfo();
			fileInfo.setFileName(PE.GetFileName());
			fileInfo.setFileSize(PE.GetFileSiz());
			fileInfo.setCreateTime(PE.GetFileCreateTime());
			fileInfo.setModifyTime(PE.GetFileModifyTime());
			return fileInfo;
		}
		catch (Exception e)
		{
			throw new UserException(file.getName() + "装载文件信息出错！");
		}
	}
	
	/** 装载DOS头，PE文件标志，PE文件头，可选头部 
	 * @throws UserException */
	private List<PEHeader> LoadHeaders() throws UserException
	{
		try
		{
			List<PEHeader> peHeaders = new ArrayList<PEHeader>();
			int headerCount = PE.GetHeaderCount(); //获取header个数
			PEHeader peHeader;
			Map<String, String> peDetail; //用于存放dos头的详细数据：名称->值
			for (int i = 0; i < headerCount; i++)
			{
				peHeader = new PEHeader();
				peDetail = new HashMap<String, String>();

				peHeader.setName(PE.GetHeaderName(i)); //保存头name

				int memberCount = PE.GetMemberCount(i);
				for (int j = 0; j < memberCount; j++)
				{
					String memberKey = PE.GetMemberName(i, j);
					String memberValue = "0x" + Integer.toHexString(PE.GetMemberValue(i, j));
					peDetail.put(memberKey, memberValue);
				}
				peHeader.setValue(peDetail); //保存成员详细信息
				peHeaders.add(peHeader);
			}
			return peHeaders;
		}
		catch (Exception e)
		{
			throw new UserException(file.getName() + "装载DOS头，PE文件标志，PE文件头，可选头部出错！");
		}
	}
	
	/** 装载数据目录 
	 * @throws UserException */
	private List<DataDirectory> LoadDataDirectory() throws UserException
	{
		try
		{
			List<DataDirectory> dataDirectorys = new ArrayList<DataDirectory>();
			DataDirectory dataDirectory = new DataDirectory();
			String virtualAddress;
			String size;
			String name;
			for (int i = 0; i < 16; i++)
			{
				if (PE.GetDataDirectoryItemVirtualAddress(i) == 0 && PE.GetDataDirectoryItemSize(i) == 0) continue;

				virtualAddress = "0x" + Integer.toHexString(PE.GetDataDirectoryItemVirtualAddress(i));
				size = "0x" + Integer.toHexString(PE.GetDataDirectoryItemSize(i));
				name = PE.GetDataDirectoryItemName(i);

				dataDirectory.setName(name);
				dataDirectory.setSize(size);
				dataDirectory.setVirtualAddress(virtualAddress);
				dataDirectorys.add(dataDirectory);
			}
			return dataDirectorys;
		}
		catch (Exception e)
		{
			throw new UserException(file.getName() + "装载数据目录出错！");
		}
	}
	
	/** 装载节表信息 
	 * @throws UserException */
	private List<SectionHeader> LoadSectionHeaders() throws UserException
	{
		try
		{
			List<SectionHeader> sectionHeaders = new ArrayList<SectionHeader>(); //保存所有节表信息
			int sectionCount = PE.GetSectionCount();
			SectionHeader sectionHeader;
			Map<String, String> sectionDetail;
			for (int i = 0; i < sectionCount; i++)
			{
				sectionHeader = new SectionHeader();
				sectionDetail = new HashMap<String, String>();

				sectionHeader.setName(PE.GetSectionName(i)); //保存头name
				int memberCount = PE.GetSectionMemberCount(i);
				for (int j = 0; j < memberCount; j++)
				{
					String sectionKey = PE.GetSectionMemberName(i, j);
					String sectionValue = "0x" + Integer.toHexString(PE.GetSectionMemberValue(i, j));
					sectionDetail.put(sectionKey, sectionValue);
				}
				sectionHeader.setValue(sectionDetail); //保存成员详细信息
				sectionHeaders.add(sectionHeader);
			}
			return sectionHeaders;
		}
		catch (Exception e)
		{
			throw new UserException(file.getName() + "装载节表信息出错！");
		}
	}
	
	/** 装载导入表 */
	private List<ImportTable> LoadImportTables()
	{
		List<ImportTable> importTables = new ArrayList<ImportTable>();
		ImportTable importTable = new ImportTable();
		
		int importCount = PE.GetImportDllCount();
		for (int i = 0; i < importCount; i++)
		{
			
		}
		return importTables;
	}
	
	
	/** 装载导出表 */
	/** 装载重定位表 */
	/** 装载资源目录 */
}
