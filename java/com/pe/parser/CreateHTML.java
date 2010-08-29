package com.pe.parser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pe.entity.parser.FileInfo;
import com.pe.entity.parser.PEFile;
import com.pe.entity.parser.PEHeader;
import com.pe.util.SystemConfigure;

public class CreateHTML
{
	private static Log log = LogFactory.getLog(CreateHTML.class);
	
	public static final int SIZE_OF_WORD = 2;					//C++中WORD的长度
	public static final int SIZE_OF_DWORD = 4;					//C++中DWORD的长度
	
	private PEFile peFile;
	private String e_lfanew;				//pe文件表示的偏移量
	
	public CreateHTML(PEFile peFile) throws Exception
	{
		this.peFile = peFile;
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
		replaceValue(templateContent);

		/** 生成分析结果 */
		String pathName = SystemConfigure.get("PEResultHome");
		String fileName = peFile.getFileInfo().getFileName() + ".html";
		
		try
		{
			WriteHtml.save(templateContent, pathName, fileName);
		}
		catch (Exception e)
		{
			throw new Exception("分析结果发布失败。可能是目录不具备IO操作权限。请与管理员联系。");
		}
//		if (log.isTraceEnabled()) log.trace("通过DWR执行操作: " + operation);
	}

	private void replaceValue(String templateContent)
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
		PEHeader peSignature = peFile.getHeaders().get(1);
		templateContent = replace(templateContent, "{signature_add}", dosValue.get("e_lfanew"));
		templateContent = replace(templateContent, "{signature}", peSignature.getValue().get("Signature"));
		
		//PE文件头，可选头部
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
		
		/** 替换数据目录 */
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
//		templateContent = replace(templateContent, "", );
		
		
		
		/** 替换节表信息 */
		/** 替换导入表 */
		/** 替换导出表 */
		/** 替换重定位表 */
		/** 替换资源目录 */
		
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
	
	/** 将16进制String转换为int，以便进行offset的加运算 */
	private int hexStringToint(String str)
	{
		String hexString = "0123456789abcdef";
		int result = 0;
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
		return "0x" + Integer.toHexString(hexStringToint(value) + offset);	//加上“0x”，返回
	}
}
