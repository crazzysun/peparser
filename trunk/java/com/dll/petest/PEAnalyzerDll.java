package com.dll.petest;

import com.sun.jna.Library;
import com.sun.jna.Native;


public interface PEAnalyzerDll extends Library
{
	PEAnalyzerDll INSTANCE = (PEAnalyzerDll) Native.loadLibrary("PEAnalyzer", PEAnalyzerDll.class);
	
	/** 加载PE文件 */
	public int LoadPEHeader(String FileName);
	
	/** 获取PE头 */
	//获取header数目
	public int GetHeaderCount();
	//每个header的名称
	public String GetHeaderName(int HeaderIndex);
	//每个header的成员数
	public int GetMemberCount(int HeaderIndex);
	//获取HeaderIndex, MemberIndexd的成员名称
	public String GetMemberName(int HeaderIndex, int MemberIndex);
	//获取Header的成员字节长度
	public int GetMemberLen(int HeaderIndex, int MemberIndex);
	//获取Header的成员的值
	public int GetMemberValue(int HeaderIndex, int MemberIndex);
	
	/** 获取文件信息 */
	public String GetFileName();
	public String GetFileSiz();
	public String GetFileCreateTime();
	public String GetFileModifyTime();
	
	/** 获取节表信息 */
	public int GetSectionCount();
	public String GetSectionName(int SectionIndex);
	public int GetSectionMemberCount(int SectionIndex);
	public String GetSectionMemberName(int SectionIndex, int MemberIndex);
	public int GetSectionMemberLen(int SectionIndex, int MemberIndex);
	public int GetSectionMemberValue(int SectionIndex, int MemberIndex);
	
	
//	PEANALYZER_API int GetImportDllCount(void);
//	PEANALYZER_API char* GetImportDllName(int DllIndex);
//	PEANALYZER_API int GetImportDllFunCount(int DllIndex);
//	PEANALYZER_API char* GetImportDllFunName(int DllIndex, int FunIndex);
//	PEANALYZER_API char* GetExportDllName(void);
//	PEANALYZER_API int GetExportFunCount(void);
//	PEANALYZER_API int GetExportFunNameCount(void);
//	PEANALYZER_API char* GetExportFunName(int FunIndex);
//	PEANALYZER_API char* GetExportFunRVA(int FunIndex);
//	PEANALYZER_API char* GetExportFunIndex(int FunIndex);
//	PEANALYZER_API int GetRelocCount(void);
//	PEANALYZER_API char* GetRelocName(int RelocIndex);
//	PEANALYZER_API int GetRelocIndex(int RelocIndex);
//	PEANALYZER_API int GetRelocChunkCount(int RelocIndex);
//	PEANALYZER_API int GetRelocChunkIndex(int RelocIndex, int ChunkIndex);
//	PEANALYZER_API int GetRelocChunkRVA(int RelocIndex, int ChunkIndex);
//	PEANALYZER_API unsigned int GetRelocChunkFarAddr(int RelocIndex, int ChunkIndex);
//	PEANALYZER_API char* GetRelocChunkType(int RelocIndex, int ChunkIndex);
//	PEANALYZER_API int GetRSCCount(void);
//	PEANALYZER_API char* GetRSCType(int RSCIndex);
//	PEANALYZER_API int GetRSCItemCount(int RSCIndex);
//	PEANALYZER_API char* GetRSCItemName(int RSCIndex, int ItemIndex);
//	PEANALYZER_API unsigned int GetRSCItemRVA(int RSCIndex, int ItemIndex);
//	PEANALYZER_API int GetRSCItemSize(int RSCIndex, int ItemIndex);
}
