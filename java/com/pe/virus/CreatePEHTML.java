package com.pe.virus;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pe.entity.virus.PETrainSet;
import com.pe.entity.virus.VirusResultShow;
import com.pe.parser.ReadTemplates;
import com.pe.parser.WriteHtml;
import com.pe.util.SystemConfigure;

/**
 * 创建结果页面
 * @author FangZhiyang
 *
 */
public class CreatePEHTML
{
	private static Log log = LogFactory.getLog(CreatePEHTML.class);
	
	private PETrainSet trainSet;
	
	public CreatePEHTML(PETrainSet trainSet)
	{
		this.trainSet = trainSet;
	}

	public void create() throws Exception
	{
		if (log.isTraceEnabled()) log.trace("开始创建训练集：" +  trainSet.getName() + "的分析结果...");
		
		/** 读取模板文件 */
		String filePath = SystemConfigure.get("TemplateHome") + File.separator + "virus.template";
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
		String pathName = SystemConfigure.get("PEVirusResultHome");
		String fileName = trainSet.getName() + ".html";
		
		try
		{
			WriteHtml.save(result, pathName, File.separator + fileName);
			if (log.isTraceEnabled()) log.trace("完成训练集：" +  trainSet.getName() + "分析结果的创建...");
		}
		catch (Exception e)
		{
			throw new Exception("分析结果发布失败。可能是目录不具备IO操作权限。请与管理员联系。");
		}
	}

	private String replaceValue(String templateContent)
	{
		/** 替换文件信息 */
		templateContent = replace(templateContent, "{FileName}", trainSet.getName());
		templateContent = replace(templateContent, "{InstanceNum}", String.valueOf(trainSet.getInstanceNum()));
		templateContent = replace(templateContent, "{CorrectNum}", String.valueOf(trainSet.getCorrectNum()));
		templateContent = replace(templateContent, "{IncorrectNum}", String.valueOf(trainSet.getIncorrectNum()));
		templateContent = replace(templateContent, "{CorrectRate}", trainSet.getCorrectRate());
		templateContent = replace(templateContent, "{FileCreateReportTime}", trainSet.getCreateTime());
		
		/** 替换主要指标 */
		String strContentData = getTempleteChunk(templateContent, "<!--Content_Start-->", "<!--Content_End-->");
		String strContentTemplate = strContentData;
		String strTemp = "";
		List<VirusResultShow> resultShow = trainSet.getResultShow();
		
		for (int i = 0; i < resultShow.size(); i++)
		{
			strContentData = strContentTemplate;
			VirusResultShow result = resultShow.get(i);

			strContentData = replace(strContentData, "{name}", result.getClassName().equals("virus") ? "恶意软件" : "正常文件");
			strContentData = replace(strContentData, "{totalNum}", String.valueOf(result.getTotalNum()));
			strContentData = replace(strContentData, "{trueNum}", String.valueOf(result.getTrueNum()));
			strContentData = replace(strContentData, "{falseNum}", String.valueOf(result.getFalseNum()));
			strContentData = replace(strContentData, "{trueRate}", String.valueOf(result.getTrueRate()));
			strContentData = replace(strContentData, "{falseRate}", String.valueOf(result.getFalseRate()));
			strContentData = replace(strContentData, "{precision}", result.getPrecision());

			strTemp += strContentData;
		}

		templateContent = replace(templateContent, strContentTemplate, strTemp);
		
		/** 替换训练生成树 */
		templateContent = replace(templateContent, "{trainSet_tree}", trainSet.getTree());
		
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
