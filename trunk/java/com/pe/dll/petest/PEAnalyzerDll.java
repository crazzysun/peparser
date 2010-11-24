package com.pe.dll.petest;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface PEAnalyzerDll extends Library
{
	PEAnalyzerDll INSTANCE = (PEAnalyzerDll) Native.loadLibrary("PEAnalyzer", PEAnalyzerDll.class);
	
	/** 加载PE文件 */
	public int LoadPEHeader(String FileName);
	
	/** 检查文件是否是有效的PE文件 */
	public boolean IsPE();
	
	/** 获取PE头 */
	public int GetHeaderCount();
	public String GetHeaderName(int HeaderIndex);
	public int GetMemberCount(int HeaderIndex);
	public String GetMemberName(int HeaderIndex, int MemberIndex);
	public int GetMemberLen(int HeaderIndex, int MemberIndex);
	public int GetMemberValue(int HeaderIndex, int MemberIndex);
	
	/** 获取文件信息 */
	public String GetFileName();
	public String GetFileSiz();
	public String GetFileCreateTime();
	public String GetFileModifyTime();
	
	/** 获取资源目录 */
	public String GetDataDirectoryItemName(int DataDirectoryItemIndex);
	public int GetDataDirectoryItemVirtualAddress(int DataDirectoryItemIndex);
	public int GetDataDirectoryItemSize(int DataDirectoryItemIndex);
	
	/** 获取节表信息 */
	public int GetSectionCount();
	public String GetSectionName(int SectionIndex);
	public int GetSectionMemberCount(int SectionIndex);
	public String GetSectionMemberName(int SectionIndex, int MemberIndex);
	public int GetSectionMemberLen(int SectionIndex, int MemberIndex);
	public int GetSectionMemberValue(int SectionIndex, int MemberIndex);
	
	/** 获取导入表信息 */
	public int GetImportDllCount();
	public String GetImportDllName(int DllIndex);
	public int GetImportDllFunCount(int DllIndex);
	public String GetImportDllFunName(int DllIndex, int FunIndex);
	
	/** 获取导出表信息 */
	public String GetExportDllName();
	public int GetExportFunCount();
	public int GetExportFunNameCount();
	public String GetExportFunName(int FunIndex);
	public String GetExportFunRVA(int FunIndex);
	public String GetExportFunIndex(int FunIndex);
	
	/** 获取重定位表信息 */
	public int GetRelocCount();
	public String GetRelocName(int RelocIndex);
	public int GetRelocIndex(int RelocIndex);
	public int GetRelocChunkCount(int RelocIndex);
	public int GetRelocChunkIndex(int RelocIndex, int ChunkIndex);
	public int GetRelocChunkRVA(int RelocIndex, int ChunkIndex);
	public int GetRelocChunkFarAddr(int RelocIndex, int ChunkIndex);
	public String GetRelocChunkType(int RelocIndex, int ChunkIndex);
	
	/** 获取资源目录信息 */
	public int GetRSCCount();
	public String GetRSCType(int RSCIndex);
	public int GetRSCItemCount(int RSCIndex);
	public String GetRSCItemName(int RSCIndex, int ItemIndex);
	public int GetRSCItemRVA(int RSCIndex, int ItemIndex);
	public int GetRSCItemSize(int RSCIndex, int ItemIndex);
	
	/** PE feature extractor */
	public void OutputPEFeature(int hdr);
	public int GetStandardSectionNumber();
	public int GetNonStandardSectionNumber();
	public int GetExecutableSectionNumber();
	public int GetRWESectionNumber();
	public int GetIATEntryNumber();
	public float GetFileEntropy();
	public float GetHeaderEntropy();
	public float GetCodeSectionEntropy();
	public float GetDataSectionEntropy();
}
