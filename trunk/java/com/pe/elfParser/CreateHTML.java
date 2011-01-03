package com.pe.elfParser;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pe.parser.ReadTemplates;
import com.pe.parser.WriteHtml;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.pe.util.SystemConfigure;
import org.python.core.PyString;
/**
 * �������ҳ��
 * @author FangZhiyang
 *
 */
public class CreateHTML
{
	private static Log log = LogFactory.getLog(CreateHTML.class);
	
	private Ielf elfFile;
	private String filename;
	private String multiAnalyPath;			//���ڴ��"�������PE�ļ�"ʱ������ļ���·��
	
	public CreateHTML(Ielf file,String filename)
	{
		this.elfFile = file;
		this.multiAnalyPath = "";
		this.filename = filename;
	}
	
	public CreateHTML(Ielf file, String multiPath, String filePath)
	{
		this.elfFile = file;
		this.multiAnalyPath = multiPath;
		this.filename = filePath;
	}

	public void create() throws Exception
	{
		if (log.isTraceEnabled()) log.trace("��ʼ�����ļ���" +  this.filename + "�ķ������...");
		/** ��ȡģ���ļ� */
		String filePath = SystemConfigure.get("TemplateHome") + File.separator + "elf.template";
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
		String pathName = SystemConfigure.get("PEResultHome") + File.separator + multiAnalyPath;
		String fileName = this.filename + ".html";
		
		try
		{
			WriteHtml.save(result, pathName, File.separator + fileName);
			if (log.isTraceEnabled()) log.trace("����ļ���" +  this.filename + "��������Ĵ���...");
		}
		catch (Exception e)
		{
			throw new Exception("�����������ʧ�ܡ�������Ŀ¼���߱�IO����Ȩ�ޡ��������Ա��ϵ��");
		}
	}

	private String replaceValue(String templateContent)
	{
		/** �滻�ļ���Ϣ */
		templateContent = replace(templateContent, "{FileName}", this.filename);
		templateContent = replace(templateContent, "{FileSize}", elfFile.getFileSize());
		templateContent = replace(templateContent, "{FileCreateTime}", elfFile.getCreateTime());
		templateContent = replace(templateContent, "{FileModifyTime}", elfFile.getModifyTime());
		templateContent = replace(templateContent, "{FileCreateReportTime}",showNowTime());
		
		/** �滻DOSͷ��PE�ļ���־��PE�ļ�ͷ����ѡͷ�� */
		//DOSͷ
		templateContent = replace(templateContent, "{EI_NIDENT}", elfFile.getEI_NIDENT());
		templateContent = replace(templateContent, "{ElfHeader}", elfFile.getHeader());
		templateContent = replace(templateContent, "{Sections}", elfFile.getSections());
		templateContent = replace(templateContent, "{Programs}", elfFile.getPrograms());
		
		return templateContent;
	}
	
	private String replace(String s, String s1, PyString s2)
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
	
	/** ��ʾ��ǰ���� */
	private String showNowTime()
	{
		Date date = new Date();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = simpledateformat.format(date);
		return s;
	}
}
