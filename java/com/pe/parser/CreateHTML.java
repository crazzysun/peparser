package com.pe.parser;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pe.entity.parser.PEFile;
import com.pe.util.SystemConfigure;

public class CreateHTML
{
	private static Log log = LogFactory.getLog(CreateHTML.class);
	
	public CreateHTML(PEFile peFile)
	{
//		String filePath = SystemConfigure.get("TemplateHome");
//		filePath += "pe.template";   
//		  	
//		String templateContent;
//		try
//		{
//		  templateContent = ReadTemplates.getTlpContent(filePath);
//		}
//		catch(Exception e)
//		{
//		  throw new Exception(e.getMessage());
//		}
//		templateContent = ReplaceAll.replace(templateContent,flag[0],Title);
//		templateContent = ReplaceAll.replace(templateContent,flag[1],GetDate.getStringDate());
//		templateContent = ReplaceAll.replace(templateContent,flag[2],Author);
//		templateContent = ReplaceAll.replace(templateContent,flag[3],FirstFrom);
//		templateContent = ReplaceAll.replace(templateContent,flag[4],Content);
//		templateContent = ReplaceAll.replace(templateContent,flag[5],Editor);
//		//templateContent = ReplaceAll.replace(templateContent,flag[6],"等待添加购物车");
//		//templateContent = ReplaceAll.replace(templateContent,flag[7],"等待添加分类");
//		//templateContent = ReplaceAll.replace(templateContent,flag[8],"等待添加时尚新闻");
//		templateContent = ReplaceAll.replace(templateContent,flag[6],Department);
//
//		// 根据时间得文件名与路径名
//		Calendar calendar = Calendar.getInstance();
//		String fileName = String.valueOf(calendar.getTimeInMillis()) +".shtml";
//		// 如果是linux系统把 "\\" 改为 "/"
//		String pathName = application.getRealPath("news")+"/"+ calendar.get(Calendar.YEAR) + 
//			"/"+ (calendar.get(Calendar.MONTH)+1) +"/"+ calendar.get(Calendar.DAY_OF_MONTH)+"/";	
//		/**
//		* 写文件
//		*/
//		try {
//		  WriteHtml.save(templateContent,pathName,fileName);
//		}
//		catch(WriteFileException e){
//		  throw new Exception("新闻发布失败。可能是目录不具备IO操作权限。请与管理员联系。");
//		}
//		if (log.isTraceEnabled()) log.trace("通过DWR执行操作: " + operation);
	}
	
}
