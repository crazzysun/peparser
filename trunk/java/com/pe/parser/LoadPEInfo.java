package com.pe.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pe.UserException;
import com.pe.dll.petest.PEAnalyzerDll;
import com.pe.entity.parser.DataDirectory;
import com.pe.entity.parser.ExportFunction;
import com.pe.entity.parser.ExportTable;
import com.pe.entity.parser.FileInfo;
import com.pe.entity.parser.ImportTable;
import com.pe.entity.parser.PEFile;
import com.pe.entity.parser.PEHeader;
import com.pe.entity.parser.Relocation;
import com.pe.entity.parser.RelocationChunk;
import com.pe.entity.parser.Resource;
import com.pe.entity.parser.ResourceItem;
import com.pe.entity.parser.SectionHeader;

/**
 * 加载pe文件信息
 * @author FangZhiyang
 *
 */
public class LoadPEInfo
{
	public static final int IMAGE_NUMBEROF_DIRECTORY_ENTRIES = 16;			//数据目录的个数
	
	private PEAnalyzerDll PE = PEAnalyzerDll.INSTANCE;
	private File file; 
	private boolean isSingle;			//是否是分析单个文件。（分析单个文件是上传到临时文件夹的，所以分析完成后需要file.delete()）
	
	public LoadPEInfo(File file)
	{
		this.file = file; 
		this.isSingle = true;
	}
	
	public LoadPEInfo(File file, boolean isSingle)
	{
		this.file = file; 
		this.isSingle = isSingle;
	}
	
	/** 开始分析文件 
	 * @throws UserException */
	public PEFile Analyze() throws UserException
	{
		/** 通过dll加载PE文件 */
		PE.LoadPEHeader(file.getPath());
		/** 检查文件是否是有效的PE文件 */
		if(!PE.IsPE())
			return null;
		
		PEFile peFile;
		try
		{
			peFile = new PEFile();
			peFile.setFileInfo(loadFileInfo());
			peFile.setHeaders(loadHeaders());
			peFile.setDataDirectory(loadDataDirectory());
			peFile.setSections(loadSectionHeaders());
			peFile.setImportTable(loadImportTables());
			peFile.setExportTable(loadExportTable());
			peFile.setRelocation(loadRelocation());
			peFile.setResource(loadResource());
		}
		catch (Exception e)
		{
			throw new UserException("分析文件出错，可能文件不符合有效的PE格式");
		}
		
		/** 分析单个文件是上传到临时文件夹的，所以分析完成后需要删除 */
		if (isSingle) file.delete();
		
		return peFile;
	}
	
	/** 装载文件信息 
	 * @throws UserException */
	private FileInfo loadFileInfo() throws UserException
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
	private List<PEHeader> loadHeaders() throws UserException
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
	private List<DataDirectory> loadDataDirectory() throws UserException
	{
		try
		{
			List<DataDirectory> dataDirectorys = new ArrayList<DataDirectory>();
			String virtualAddress;
			String size;
			String name;
			for (int i = 0; i < 16; i++)
			{
				if (PE.GetDataDirectoryItemVirtualAddress(i) == 0 && PE.GetDataDirectoryItemSize(i) == 0) continue;

				virtualAddress = "0x" + Integer.toHexString(PE.GetDataDirectoryItemVirtualAddress(i));
				size = "0x" + Integer.toHexString(PE.GetDataDirectoryItemSize(i));
				name = PE.GetDataDirectoryItemName(i);

				DataDirectory dataDirectory = new DataDirectory();
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
	private List<SectionHeader> loadSectionHeaders() throws UserException
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
	
	/** 装载导入表 
	 * @throws UserException */
	private List<ImportTable> loadImportTables() throws UserException
	{
		try
		{
			List<ImportTable> importTables = new ArrayList<ImportTable>();
			
			List<String> functionName;
			int importCount = PE.GetImportDllCount();
			for (int i = 0; i < importCount; i++)
			{
				ImportTable importTable = new ImportTable();
				importTable.setName(PE.GetImportDllName(i));
				functionName = new ArrayList<String>();
				for (int j = 0; j < PE.GetImportDllFunCount(i); j++)
				{
					functionName.add(PE.GetImportDllFunName(i, j));
				}
				importTable.setFunctionName(functionName);
				importTables.add(importTable);
			}
			return importTables;
		}
		catch (Exception e)
		{
			throw new UserException(file.getName() + "装载导入表出错！");
		}
	}
	
	/** 装载导出表 
	 * @throws UserException */
	private ExportTable loadExportTable() throws UserException
	{
		try
		{
			ExportTable exportTable = new ExportTable();
			exportTable.setDllName(PE.GetExportDllName());
			exportTable.setFunctionCount(PE.GetExportFunCount());
			exportTable.setFunctionNameCount(PE.GetExportFunNameCount());
			List<ExportFunction> exportFunctionList = new ArrayList<ExportFunction>();
			
			for (int i = 0; i < PE.GetExportFunCount(); i++)
			{
				ExportFunction exportFun = new ExportFunction();
				exportFun.setFunctionName(PE.GetExportFunName(i));
				exportFun.setRVA(PE.GetExportFunRVA(i));
				exportFun.setIndex(PE.GetExportFunIndex(i));
				exportFunctionList.add(exportFun);
			}
			exportTable.setExportFunction(exportFunctionList);
			return exportTable;
		}
		catch (Exception e)
		{
			throw new UserException(file.getName() + "装载导出表出错！");
		}
	}
	
	/** 装载重定位表 
	 * @throws UserException */
	private List<Relocation> loadRelocation() throws UserException
	{
		try
		{
			List<Relocation> relocationList = new ArrayList<Relocation>();
			
			for (int i = 0; i < PE.GetRelocCount(); i++)
			{
				Relocation relocation = new Relocation();
				relocation.setItemIndex(PE.GetRelocIndex(i));
				relocation.setSectionName(PE.GetRelocName(i));
				relocation.setRecCount(PE.GetRelocChunkCount(i));
				List<RelocationChunk> relocChunkList = new ArrayList<RelocationChunk>();
				
				for (int j = 0; j < PE.GetRelocChunkCount(i); j++)
				{
					RelocationChunk relocChunk = new RelocationChunk();
					relocChunk.setIndex(PE.GetRelocChunkIndex(i, j));
					relocChunk.setFarAddress("0x" + Integer.toHexString(PE.GetRelocChunkFarAddr(i, j)));
					relocChunk.setRVA("0x" + Integer.toHexString(PE.GetRelocChunkRVA(i, j)));
					relocChunk.setType(PE.GetRelocChunkType(i, j));
					relocChunkList.add(relocChunk);
				}
				relocation.setRelocChunk(relocChunkList);
				relocationList.add(relocation);
			}
			return relocationList;
		}
		catch (Exception e)
		{
			throw new UserException(file.getName() + "装载重定位表出错！");
		}
	}
	
	/** 装载资源目录 
	 * @throws UserException */
	private List<Resource> loadResource() throws UserException
	{
		try
		{
			List<Resource> resourceList = new ArrayList<Resource>();
			
			for (int i = 0; i < PE.GetRSCCount(); i++)
			{
				Resource resource = new Resource();
				resource.setRecType(PE.GetRSCType(i));
				resource.setRecCount(PE.GetRSCItemCount(i));
				List<ResourceItem> resourceItemList = new ArrayList<ResourceItem>();
				
				for (int j = 0; j < PE.GetRSCItemCount(i); j++)
				{
					ResourceItem resourceItem = new ResourceItem();
					resourceItem.setName(PE.GetRSCItemName(i, j));
					resourceItem.setRAV("0x" + Integer.toHexString(PE.GetRSCItemRVA(i, j)));
					resourceItem.setSize(PE.GetRSCItemSize(i, j));
					resourceItemList.add(resourceItem);
				}
				resource.setItem(resourceItemList);
				resourceList.add(resource);
			}
			return resourceList;
		}
		catch (Exception e)
		{
			throw new UserException(file.getName() + "装载资源目录出错！");
		}
	}
}
