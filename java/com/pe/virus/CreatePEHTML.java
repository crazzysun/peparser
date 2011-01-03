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
 * �������ҳ��
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
		if (log.isTraceEnabled()) log.trace("��ʼ����ѵ������" +  trainSet.getName() + "�ķ������...");
		
		/** ��ȡģ���ļ� */
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
		
		/** ��ʼ�滻�ļ� */
		String result = replaceValue(templateContent);

		/** ���ɷ������ */
		String pathName = SystemConfigure.get("PEVirusResultHome");
		String fileName = trainSet.getName() + ".html";
		
		try
		{
			WriteHtml.save(result, pathName, File.separator + fileName);
			if (log.isTraceEnabled()) log.trace("���ѵ������" +  trainSet.getName() + "��������Ĵ���...");
		}
		catch (Exception e)
		{
			throw new Exception("�����������ʧ�ܡ�������Ŀ¼���߱�IO����Ȩ�ޡ��������Ա��ϵ��");
		}
	}

	private String replaceValue(String templateContent)
	{
		/** �滻�ļ���Ϣ */
		templateContent = replace(templateContent, "{FileName}", trainSet.getName());
		templateContent = replace(templateContent, "{InstanceNum}", String.valueOf(trainSet.getInstanceNum()));
		templateContent = replace(templateContent, "{CorrectNum}", String.valueOf(trainSet.getCorrectNum()));
		templateContent = replace(templateContent, "{IncorrectNum}", String.valueOf(trainSet.getIncorrectNum()));
		templateContent = replace(templateContent, "{CorrectRate}", trainSet.getCorrectRate());
		templateContent = replace(templateContent, "{FileCreateReportTime}", trainSet.getCreateTime());
		
		/** �滻��Ҫָ�� */
		String strContentData = getTempleteChunk(templateContent, "<!--Content_Start-->", "<!--Content_End-->");
		String strContentTemplate = strContentData;
		String strTemp = "";
		List<VirusResultShow> resultShow = trainSet.getResultShow();
		
		for (int i = 0; i < resultShow.size(); i++)
		{
			strContentData = strContentTemplate;
			VirusResultShow result = resultShow.get(i);

			strContentData = replace(strContentData, "{name}", result.getClassName().equals("virus") ? "�������" : "�����ļ�");
			strContentData = replace(strContentData, "{totalNum}", String.valueOf(result.getTotalNum()));
			strContentData = replace(strContentData, "{trueNum}", String.valueOf(result.getTrueNum()));
			strContentData = replace(strContentData, "{falseNum}", String.valueOf(result.getFalseNum()));
			strContentData = replace(strContentData, "{trueRate}", String.valueOf(result.getTrueRate()));
			strContentData = replace(strContentData, "{falseRate}", String.valueOf(result.getFalseRate()));
			strContentData = replace(strContentData, "{precision}", result.getPrecision());

			strTemp += strContentData;
		}

		templateContent = replace(templateContent, strContentTemplate, strTemp);
		
		/** �滻ѵ�������� */
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
	 * ��ȡģ���ַ���
	 * strSourceԭ�ַ�����strStart��ʼ�ַ�����strEnd�����ַ���
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
