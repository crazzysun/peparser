package com.pe.parser;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
import com.pe.util.SystemConfigure;

/**
 * 创建结果页面
 * @author FangZhiyang
 *
 */
public class CreateHTML
{
	private static Log log = LogFactory.getLog(CreateHTML.class);
	
	public static final int SIZE_OF_WORD = 2;					//C++中WORD的长度
	public static final int SIZE_OF_DWORD = 4;					//C++中DWORD的长度
	public static final int SIZE_OF_BYTE = 1;					//C++中BYTE的长度
	
	private PEFile peFile;
	private String e_lfanew;				//pe文件表示的偏移量
	private String multiAnalyPath;			//用于存放"分析多个PE文件"时结果的文件夹路径
	
	public CreateHTML(PEFile file)
	{
		this.peFile = file;
		this.multiAnalyPath = "";
	}
	
	public CreateHTML(PEFile file, String multiPath)
	{
		this.peFile = file;
		this.multiAnalyPath = multiPath;
	}

	public void create() throws Exception
	{
		if (log.isTraceEnabled()) log.trace("开始创建文件：" +  peFile.getFileInfo().getFileName() + "的分析结果...");
		/** 读取模板文件 */
		String filePath = SystemConfigure.get("TemplateHome") + "/" + "pe.template";
		String templateContent;
		try
		{
			templateContent = ReadTemplates.getTlpContent(filePath);
		}
		catch (Exception e)
		{
			throw new Exception(e.getMessage());
		}
		
		/** 开始替换文件 */
		String result = replaceValue(templateContent);

		/** 生成分析结果 */
		String pathName = SystemConfigure.get("PEResultHome") + "/" + multiAnalyPath;
		String fileName = peFile.getFileInfo().getFileName() + ".html";
		
		try
		{
			WriteHtml.save(result, pathName, "/" + fileName);
			if (log.isTraceEnabled()) log.trace("完成文件：" +  peFile.getFileInfo().getFileName() + "分析结果的创建...");
		}
		catch (Exception e)
		{
			throw new Exception("分析结果发布失败。可能是目录不具备IO操作权限。请与管理员联系。");
		}
	}

	private String replaceValue(String templateContent)
	{
		int offset = 0;
		
		/** 替换文件信息 */
		FileInfo fileInfo = peFile.getFileInfo();
		templateContent = replace(templateContent, "{FileName}", fileInfo.getFileName());
		templateContent = replace(templateContent, "{FileSize}", fileInfo.getFileSize());
		templateContent = replace(templateContent, "{FileCreateTime}", fileInfo.getCreateTime());
		templateContent = replace(templateContent, "{FileModifyTime}", fileInfo.getModifyTime());
		templateContent = replace(templateContent, "{FileCreateReportTime}", showNowTime());
		
		/** 替换DOS头，PE文件标志，PE文件头，可选头部 */
		//DOS头
		PEHeader dosHeader = peFile.getHeaders().get(0);
		Map<String, String> dosValue = dosHeader.getValue();
		e_lfanew = dosValue.get("e_lfanew");
		
		templateContent = replace(templateContent, "{e_magic}", "MZ");
		templateContent = replace(templateContent, "{e_cblp}", dosValue.get("e_cblp"));
		templateContent = replace(templateContent, "{e_cp}", dosValue.get("e_cp"));
		templateContent = replace(templateContent, "{e_crlc}", dosValue.get("e_crlc"));
		templateContent = replace(templateContent, "{e_cparhdr}", dosValue.get("e_cparhdr"));
		templateContent = replace(templateContent, "{e_minalloc}", dosValue.get("e_minalloc"));
		templateContent = replace(templateContent, "{e_maxalloc}", dosValue.get("e_maxalloc"));
		templateContent = replace(templateContent, "{e_ss}", dosValue.get("e_ss"));
		templateContent = replace(templateContent, "{e_sp}", dosValue.get("e_sp"));
		templateContent = replace(templateContent, "{e_csum}", dosValue.get("e_csum"));
		templateContent = replace(templateContent, "{e_ip}", dosValue.get("e_ip"));
		templateContent = replace(templateContent, "{e_cs}", dosValue.get("e_cs"));
		templateContent = replace(templateContent, "{e_lfarlc}", dosValue.get("e_lfarlc"));
		templateContent = replace(templateContent, "{e_ovno}", dosValue.get("e_ovno"));
		templateContent = replace(templateContent, "{e_res}", "0x0");
		templateContent = replace(templateContent, "{e_oemid}", dosValue.get("e_oemid"));
		templateContent = replace(templateContent, "{e_oeminfo}", dosValue.get("e_oeminfo"));
		templateContent = replace(templateContent, "{e_res2}", "0x0");
		templateContent = replace(templateContent, "{e_lfanew}", dosValue.get("e_lfanew"));
		
		//PE文件标志
		templateContent = replace(templateContent, "{signature_add}", dosValue.get("e_lfanew"));
		templateContent = replace(templateContent, "{signature}", "PE00");
		
		//PE文件头
		PEHeader fileHeader = peFile.getHeaders().get(2);
		Map<String, String> fileValue = fileHeader.getValue();
		
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{machine}", fileValue.get("Machine"));
		templateContent = replace(templateContent, "{machine_add}", getNewOffset(offset));
		offset += CreateHTML.SIZE_OF_WORD;
		templateContent = replace(templateContent, "{NumberOfSections}", fileValue.get("NumberOfSections"));
		templateContent = replace(templateContent, "{NOS_add}", getNewOffset(offset));
		offset += CreateHTML.SIZE_OF_WORD;
		templateContent = replace(templateContent, "{TimeDateStamp}", fileValue.get("TimeDateStamp"));
		templateContent = replace(templateContent, "{TDS_add}", getNewOffset(offset));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{PointerToSymbolTable}", fileValue.get("PointerToSymbolTable"));
		templateContent = replace(templateContent, "{PTST_add}", getNewOffset(offset));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{NumberOfSymbols}", fileValue.get("NumberOfSymbols"));
		templateContent = replace(templateContent, "{NOSym_add}", getNewOffset(offset));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{SizeOfOptionalHeader}", fileValue.get("SizeOfOptionalHeader"));
		templateContent = replace(templateContent, "{SOOH_add}", getNewOffset(offset));
		offset += CreateHTML.SIZE_OF_WORD;
		templateContent = replace(templateContent, "{Characteristics}", fileValue.get("Characteristics"));
		templateContent = replace(templateContent, "{HAR_add}", getNewOffset(offset));
		offset += CreateHTML.SIZE_OF_WORD;
		
		//可选头部
		PEHeader optionalHeader = peFile.getHeaders().get(3);
		Map<String, String> optionalValue = optionalHeader.getValue();
		templateContent = replace(templateContent, "{magic_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{magic}", optionalValue.get("Magic"));
		offset += CreateHTML.SIZE_OF_WORD;
		templateContent = replace(templateContent, "{MajLV_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{MajorLinkerVersion}", optionalValue.get("MajorLinkerVersion"));
		offset += CreateHTML.SIZE_OF_BYTE;
		templateContent = replace(templateContent, "{MinLV_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{MinorLinkerVersion}", optionalValue.get("MinorLinkerVersion"));
		offset += CreateHTML.SIZE_OF_BYTE;
		templateContent = replace(templateContent, "{SOC_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{SizeOfCode}", optionalValue.get("SizeOfCode"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{SOID_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{SizeOfInitializedData}", optionalValue.get("SizeOfInitializedData"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{SOUD_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{SizeOfUninitializedData}", optionalValue.get("SizeOfUninitializedData"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{AOP_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{AddressOfEntryPoint}", optionalValue.get("AddressOfEntryPoint"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{BOC_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{BaseOfCode}", optionalValue.get("BaseOfCode"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{BOD_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{BaseOfData}", optionalValue.get("BaseOfData"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{IB_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{ImageBase}", optionalValue.get("ImageBase"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{SA_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{SectionAlignment}", optionalValue.get("SectionAlignment"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{FA_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{FileAlignment}", optionalValue.get("FileAlignment"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{MajOSV_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{MajorOperatingSystemVersion}", optionalValue.get("MajorOperatingSystemVersion"));
		offset += CreateHTML.SIZE_OF_WORD;
		templateContent = replace(templateContent, "{MinOSV_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{MinorOperatingSystemVersion}", optionalValue.get("MinorOperatingSystemVersion"));
		offset += CreateHTML.SIZE_OF_WORD;
		templateContent = replace(templateContent, "{MaxIV_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{MajorImageVersion}", optionalValue.get("MajorImageVersion"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{MinIV_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{MinorImageVersion}", optionalValue.get("MinorImageVersion"));
		offset += CreateHTML.SIZE_OF_WORD;
		templateContent = replace(templateContent, "{MajSV_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{MajorSubsystemVersion}",optionalValue.get("MajorSubsystemVersion") );
		offset += CreateHTML.SIZE_OF_WORD;
		templateContent = replace(templateContent, "{MinSV_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{MinorSubsystemVersion}", optionalValue.get("MinorSubsystemVersion"));
		offset += CreateHTML.SIZE_OF_WORD;
		templateContent = replace(templateContent, "{W32VV_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{Win32VersionValue}", optionalValue.get("Win32VersionValue"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{SOI_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{SizeOfImage}", optionalValue.get("SizeOfImage"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{SOH_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{SizeOfHeaders}", optionalValue.get("SizeOfHeaders"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{PECS_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{PECheckSum}", optionalValue.get("CheckSum"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{SUBS_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{Subsystem}", optionalValue.get("Subsystem"));
		offset += CreateHTML.SIZE_OF_WORD;
		templateContent = replace(templateContent, "{DllChar_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{DllCharacteristics}", optionalValue.get("DllCharacteristics"));
		offset += CreateHTML.SIZE_OF_WORD;
		templateContent = replace(templateContent, "{SOSR_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{SizeOfStackReserve}", optionalValue.get("SizeOfStackReserve"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{SOSC_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{SizeOfStackCommit}", optionalValue.get("SizeOfStackCommit"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{SOHR_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{SizeOfHeapReserve}", optionalValue.get("SizeOfHeapReserve"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{SOHC_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{SizeOfHeapCommit}", optionalValue.get("SizeOfHeapCommit"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{LF_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{LoaderFlags}", optionalValue.get("LoaderFlags"));
		offset += CreateHTML.SIZE_OF_DWORD;
		templateContent = replace(templateContent, "{NRS_add}", getNewOffset(offset));
		templateContent = replace(templateContent, "{NumberOfRvaAndSizes}", optionalValue.get("NumberOfRvaAndSizes"));
		offset += CreateHTML.SIZE_OF_DWORD;
		
		
		/** 替换数据目录 */
		List<DataDirectory> dataDirectory = peFile.getDataDirectory();
		String strDataDirData = getTempleteChunk(templateContent, "<!--DataDir_Item_Start-->", "<!--DataDir_Item_End-->");
		String strDataDirTemplate = strDataDirData;
		String strTemp = "";
		
		for (int i = 0; i < dataDirectory.size(); i++)
		{
			strDataDirData = strDataDirTemplate;
			String dwRVA, dwSize, strName;

			dwRVA = dataDirectory.get(i).getVirtualAddress();
			dwSize = dataDirectory.get(i).getSize();
			strName = dataDirectory.get(i).getName();

			strDataDirData = replace(strDataDirData, "{DDN_add}", getNewOffset(offset));
			offset += CreateHTML.SIZE_OF_DWORD * 2;

			strDataDirData = replace(strDataDirData, "{VirtualAddress}", dwRVA);
			strDataDirData = replace(strDataDirData, "{isize}", dwSize);

			strDataDirData = replace(strDataDirData, "{Data_Dir_Name}", strName);   //{Data_Dir_Name}

			strTemp += strDataDirData;
		}

		templateContent = replace(templateContent, strDataDirTemplate, strTemp);
		offset += CreateHTML.SIZE_OF_DWORD << 5;
		
		/** 替换节表信息 */
		List<SectionHeader> sectionHeader = peFile.getSections();
		String strSectionData = getTempleteChunk(templateContent, "<!--SECTION_HEADER_START-->", "<!--SECTION_HEADER_END-->");
		String strSectionTemplate = strSectionData;
		strTemp = "";
		
		for (int i = 0; i < sectionHeader.size(); i++)
		{
			strSectionData = strSectionTemplate;
			Map<String, String> sectionValue = sectionHeader.get(i).getValue();
			String strBgClr = (i % 2 != 0 ? "#161f35" : "#222942");
			strSectionData = replace(strSectionData, "{SHS_BGCOLOR}", strBgClr);
			
			strSectionData = replace(strSectionData, "{Name1_add}", getNewOffset(offset));
			strSectionData = replace(strSectionData, "{Name1}", sectionHeader.get(i).getName());

			offset += CreateHTML.SIZE_OF_BYTE * 8;

			strSectionData = replace(strSectionData, "{MisV_add}", getNewOffset(offset));
			strSectionData = replace(strSectionData, "{Misc.VirtualSize}", sectionValue.get("VirtualSize"));
			offset += CreateHTML.SIZE_OF_DWORD;

			strSectionData = replace(strSectionData, "{VA_add}", getNewOffset(offset));
			strSectionData = replace(strSectionData, "{VirtualAddress}", sectionValue.get("VirtualAddress"));
			offset += CreateHTML.SIZE_OF_DWORD;

			strSectionData = replace(strSectionData, "{SORD_add}", getNewOffset(offset));
			strSectionData = replace(strSectionData, "{SizeOfRawData}", sectionValue.get("SizeOfRawData"));
			offset += CreateHTML.SIZE_OF_DWORD;
	
			strSectionData = replace(strSectionData,"{PTRD_add}", getNewOffset(offset));
			strSectionData = replace(strSectionData,"{PointerToRawData}", sectionValue.get("PointerToRawData"));
			offset += CreateHTML.SIZE_OF_DWORD;
	
			strSectionData = replace(strSectionData,"{PTR_add}", getNewOffset(offset));
			strSectionData = replace(strSectionData,"{PointerToRelocations}", sectionValue.get("PointerToRelocations"));
			offset += CreateHTML.SIZE_OF_DWORD;
	
			strSectionData = replace(strSectionData,"{PTL_add}", getNewOffset(offset));
			strSectionData = replace(strSectionData,"{PointerToLinenumbers}", sectionValue.get("PointerToLinenumbers"));
			offset += CreateHTML.SIZE_OF_DWORD;
	
			strSectionData = replace(strSectionData,"{NOR_add}", getNewOffset(offset));
			strSectionData = replace(strSectionData,"{NumberOfRelocations}", sectionValue.get("NumberOfRelocations"));
			offset += CreateHTML.SIZE_OF_WORD;
	
			strSectionData = replace(strSectionData,"{NOL_add}", getNewOffset(offset));
			strSectionData = replace(strSectionData,"{NumberOfLinenumbers}", sectionValue.get("NumberOfLinenumbers"));
			offset += CreateHTML.SIZE_OF_WORD;
	
			strSectionData = replace(strSectionData,"{Char_add}", getNewOffset(offset));
			String strChar = "", strCharAll;
			long Characteristics = hexStringToint(sectionValue.get("Characteristics").substring(2));	//去掉String前面的"0x"
			if (Characteristics == WINNT_H.IMAGE_SCN_CNT_CODE)
				strChar += (strChar == "" ? " Code" : ", Code");

			if (Characteristics == WINNT_H.IMAGE_SCN_CNT_INITIALIZED_DATA)
				strChar += (strChar == "" ? " Initialized Data" : ", Initialized Data");

			if (Characteristics == WINNT_H.IMAGE_SCN_CNT_UNINITIALIZED_DATA)
				strChar += (strChar == "" ? " Uninitialized Data" : ", Uninitialized Data");

			if (Characteristics == WINNT_H.IMAGE_SCN_LNK_INFO)
				strChar += (strChar == "" ? " Link Information" : ", Link Information");

			if (Characteristics == WINNT_H.IMAGE_SCN_LNK_REMOVE)
				strChar += (strChar == "" ? " Remove" : ", Remove");

			if (Characteristics == WINNT_H.IMAGE_SCN_LNK_COMDAT)
				strChar += (strChar == "" ? " COMDAT" : ", COMDAT");

			if (Characteristics == WINNT_H.IMAGE_SCN_MEM_DISCARDABLE)
				strChar += (strChar == "" ? " Discardable" : ", Discardable");
			
			if (Characteristics == WINNT_H.IMAGE_SCN_MEM_SHARED)
				strChar += (strChar == "" ? " Shared" : ", Shared");
			
			if (Characteristics == WINNT_H.IMAGE_SCN_MEM_EXECUTE)
				strChar += (strChar == "" ? " Execute" : ", Execute");
			
			if (Characteristics == WINNT_H.IMAGE_SCN_MEM_READ)
				strChar += (strChar == "" ? " Read" : ", Read");
			
			if (Characteristics == WINNT_H.IMAGE_SCN_MEM_WRITE)
				strChar += (strChar == "" ? " Write" : ", Write");
			
			strCharAll = sectionValue.get("Characteristics");

			if (strChar != "")
				strCharAll += "<br /><sub class='special'>" + strChar + "</sub>";
			
			strSectionData = replace(strSectionData,"{Section_Characteristics}", strCharAll);
			offset += CreateHTML.SIZE_OF_DWORD;
			strTemp += strSectionData;
		}
		templateContent = replace(templateContent, strSectionTemplate, strTemp);

		/** 替换导入表 */
		List<ImportTable> importTable = peFile.getImportTable();
		String strImpData = getTempleteChunk(templateContent, "<!--IMPORT_TABLE_START-->", "<!--IMPORT_TABLE_END-->");
		String strImpFun = getTempleteChunk(templateContent, "<!--IMP_TABLE_ITME_START-->", "<!--IMP_TABLE_ITME_END-->");
		String strImpTemplate = strImpData;
		String strImpAll = "";

		for (int i = 0; i < importTable.size(); i++)
		{
			strImpData = strImpTemplate;
			//取出每一个导入表的数据
			String strDllName = importTable.get(i).getName();
			
			List<String> funName = importTable.get(i).getFunctionName();
			String strFunAll = "";
			Arrays.sort(funName.toArray());
			for (int j = 0; j < funName.size(); j += 3)
			{
				String strFunTemp = strImpFun;
				String strFunName[] = {"", "", ""};
				strFunName[j % 3] = funName.get(j);
				if (j + 1 < funName.size())
					strFunName[j % 3 + 1] = funName.get(j + 1);
				if (j + 2 < funName.size())
					strFunName[j % 3 + 2] = funName.get(j + 2);
				
				strFunTemp = replace(strFunTemp, "{IMP_FUNNAME1}", strFunName[j % 3]);
				strFunTemp = replace(strFunTemp, "{IMP_FUNNAME2}", strFunName[j % 3 + 1]);
				strFunTemp = replace(strFunTemp, "{IMP_FUNNAME3}", strFunName[j % 3 + 2]);
				strFunAll += strFunTemp;
			}

			strImpData = replace(strImpData, "{IMP_DLL_NAME}", strDllName);
			strImpData = replace(strImpData, strImpFun, strFunAll);
			strImpAll += strImpData;
		}
		templateContent = replace(templateContent, strImpTemplate, strImpAll);
		

		/** 替换导出表 */
		ExportTable exportTable = peFile.getExportTable();
		String strExpData = getTempleteChunk(templateContent, "<!--EXPORT_TABLE_START-->", "<!--EXPORT_TABLE_END-->");
		String strExpFun = getTempleteChunk(templateContent, "<!--EXP_TABLE_ITME_START-->", "<!--EXP_TABLE_ITME_END-->");
		String strExpTemplate = strExpData;
		String strExpAll = "";
		
		//替换导出表模板中前三个信息
		if (exportTable != null && !exportTable.getDllName().equals(""))
		{
			strExpData = replace(strExpData, "{EXP_DLL_NAME}", exportTable.getDllName());
			strExpData = replace(strExpData, "{EXP_COUNT}", String.valueOf(exportTable.getFunctionCount()));
			strExpData = replace(strExpData, "{EXP_NAME_COUNT}", String.valueOf(exportTable.getFunctionNameCount()));
			String strExpFunAll = "";
			List<ExportFunction> exportFunction = exportTable.getExportFunction();
			for (int i = 0; i < exportFunction.size(); i++)
			{
				String strFunTemp = strExpFun;

				strFunTemp = replace(strFunTemp, "{EXP_Index}", exportFunction.get(i).getIndex());
				strFunTemp = replace(strFunTemp, "{EXP_RVA}", exportFunction.get(i).getRVA());
				strFunTemp = replace(strFunTemp, "{EXP_FUN}", exportFunction.get(i).getFunctionName());

				strExpFunAll += strFunTemp;
			}
			strExpData = replace(strExpData, strExpFun, strExpFunAll);
			strExpAll = strExpData;
		}
		templateContent = replace(templateContent, strExpTemplate, strExpAll);
		
		/** 替换重定位表 */
		List<Relocation> relocation = peFile.getRelocation();
		String strRelocData = getTempleteChunk(templateContent, "<!--RELOC_TABLE_START-->", "<!--RELOC_TABLE_END-->");
		String strRelocItem = getTempleteChunk(templateContent, "<!--RELOC_TABLE_ITME_START-->", "<!--RELOC_TABLE_ITME_END-->");
		String strRelocTemplate = strRelocData;
		String strRelocAll = "";
		
		for (int i = 0; i < relocation.size(); i++)
		{
			strRelocData = strRelocTemplate;
			strRelocData = replace(strRelocData, "{REL_INDEX}", String.valueOf(relocation.get(i).getItemIndex()));
			strRelocData = replace(strRelocData, "{REL_SECTION_NAME}", relocation.get(i).getSectionName());
			strRelocData = replace(strRelocData, "{REL_NUM}", String.valueOf(relocation.get(i).getRecCount()));
			String strRelocItemTemp = "";
			List<RelocationChunk> relocChunk = relocation.get(i).getRelocChunk();
			
			for (int j = 0; j < relocChunk.size(); j++)
			{
				String strFunTemp = strRelocItem;
				strFunTemp = replace(strFunTemp, "{REL_Index}", String.valueOf(relocChunk.get(j).getIndex()));
				strFunTemp = replace(strFunTemp, "{REL_RVA}", relocChunk.get(j).getRVA());
				strFunTemp = replace(strFunTemp, "{REL_FarAdd}", relocChunk.get(j).getFarAddress());
				strFunTemp = replace(strFunTemp, "{REL_Type}", relocChunk.get(j).getType());
				strRelocItemTemp += strFunTemp;
			}
			strRelocData = replace(strRelocData, strRelocItem, strRelocItemTemp);
			strRelocAll += strRelocData;
		}
		templateContent = replace(templateContent, strRelocTemplate, strRelocAll);
		
		/** 替换资源目录 */
		List<Resource> resource = peFile.getResource();
		String strRecData = getTempleteChunk(templateContent, "<!--RES_TABLE_START-->", "<!--RES_TABLE_END-->");
		String strRecItem = getTempleteChunk(templateContent, "<!--RES_TABLE_ITME_START-->", "<!--RES_TABLE_ITME_END-->");
		String strRecTemplate = strRecData;
		String strRecAll = "";
		
		for (int i = 0; i < resource.size(); i++)
		{
			strRecData = strRecTemplate;
			strRecData = replace(strRecData, "{REC_TYPE}", resource.get(i).getRecType());
			strRecData = replace(strRecData, "{REC_NUM}", String.valueOf(resource.get(i).getRecCount()));
			String strRecItemTemp = "";
			List<ResourceItem> resourceItem = resource.get(i).getItem();
			
			for (int j = 0; j < resourceItem.size(); j++)
			{
				String strFunTemp = strRecItem;
				strFunTemp = replace(strFunTemp, "{REC_RVA}", resourceItem.get(j).getRAV());
				strFunTemp = replace(strFunTemp, "{REC_Size}", String.valueOf(resourceItem.get(j).getSize()));
				strFunTemp = replace(strFunTemp, "{REC_Name}", resourceItem.get(j).getName());
				strRecItemTemp += strFunTemp;
			}
			strRecData = replace(strRecData, strRecItem, strRecItemTemp);
			strRecAll += strRecData;
		}
		templateContent = replace(templateContent, strRecTemplate, strRecAll);
		
		return templateContent;
	}
	
	private String replace(String s, String s1, String s2)
	{
		if (s == null) return null;
		StringBuffer stringbuffer = new StringBuffer();
		int i = s.length();
		int j = s1.length();
		int k;
		int l;
		for (k = 0; (l = s.indexOf(s1, k)) >= 0; k = l + j)
		{
			stringbuffer.append(s.substring(k, l));
			stringbuffer.append(s2);
		}

		if (k < i) stringbuffer.append(s.substring(k));
		return stringbuffer.toString();
	}
	
	/** 显示当前日期 */
	private String showNowTime()
	{
		Date date = new Date();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = simpledateformat.format(date);
		return s;
	}
	
	/** 将16进制String转换为long，以便进行offset的加运算 */
	private long hexStringToint(String str)
	{
		String hexString = "0123456789abcdef";
		long result = 0L;
		int length = str.length();
		for (int i = 0; i < length; i++)
		{
			int a = hexString.indexOf(str.substring(i, i+1));
			double b = Math.pow(16, length - 1 - i);
			result += a * b;
		}
		return result;
	}
	
	/** 给一个String类型的偏移量数据进行加操作 */
	private String getNewOffset(int offset)
	{
		String value = e_lfanew.substring(2);								//去掉“0x”
		return "0x" + Long.toHexString(hexStringToint(value) + offset);	 	//加上“0x”，返回
	}
	
	/**
	 * 截取模块字符串
	 * strSource原字符串，strStart开始字符串，strEnd结束字符串
	 */
	private String getTempleteChunk(String strSource, String strStart, String strEnd)
	{
		String strRet = "";
		int posStart = 0, posEnd = 0;

		if (strStart.length()== 0 || strEnd.length()== 0)
			return strRet;
		
		posStart = strSource.indexOf(strStart) + strStart.length();
		posEnd = strSource.indexOf(strEnd);

		if (posStart < posEnd)
			strRet = strSource.substring(posStart, posEnd);
		
		return strRet;
	}
}
