package com.pe.web.servlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pe.dll.petest.PEAnalyzerDll;
import com.pe.entity.parser.FileInfo;
import com.pe.entity.parser.PEHeader;
import com.pe.entity.parser.SectionHeader;

public class ParserServlet extends HttpServlet
{
	private static final long serialVersionUID = 296167702543002564L;
	
	private Log log = LogFactory.getLog(ParserServlet.class);
	
	@SuppressWarnings("unchecked")
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String fileName = "";
		File file = null;
		
		/** �����ļ���ָ��Ŀ¼���з��� */  
		try
		{
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1 * 1024 * 1024);
		
			ServletFileUpload upload = new ServletFileUpload(factory);
				
			List<FileItem> items = upload.parseRequest(request);
			System.out.println(items.toString());
			for (FileItem item : items)
			{
				if (item.isFormField()) continue;
					
				fileName = item.getName();
				fileName = fileName.replace('\\', '/');
				int k = fileName.lastIndexOf("/");
				if (k > 0) fileName  = fileName.substring(k + 1);
				
				
				OutputStream output = null;
				
				file = new File("C:\\Documents and Settings\\", fileName);
				if (file.exists()) file.delete();
			
				InputStream input = item.getInputStream();
				output = new BufferedOutputStream(new FileOutputStream(file));
				byte[] buffer = new byte[32 * 1024];
				for (; ; )
				{
					int n = input.read(buffer);
					if (n <= 0) break;
					output.write(buffer, 0, n);
				}
				if (output != null) 
				{ 
					output.close(); 
				}
				
				if (log.isDebugEnabled()) log.debug("׼�������ļ�: " + fileName);
			}
		}
		catch (Exception e)
		{
			if (log.isDebugEnabled()) log.debug("�����ļ�����", e);
		}
		
		//����ļ���Ϊ�ջ���null�����׳��쳣
		if (fileName == null || fileName.equalsIgnoreCase(""))
		{
			response.sendError(503, "δѡ����Ҫ�������ļ���");
			return;
		}
		
		/** ��ʼ�����ļ� */
		PEAnalyzerDll PE = PEAnalyzerDll.INSTANCE;
		
		//ͨ��dll����PE�ļ�
		PE.LoadPEHeader(file.getPath());
		//����װheader��Vector����
		Vector<PEHeader> peHeaders = new Vector<PEHeader>();
		
		/** װ���ļ���Ϣ */
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileName(PE.GetFileName());
		fileInfo.setFileSize(PE.GetFileSiz());
		fileInfo.setCreateTime(PE.GetFileCreateTime());
		fileInfo.setModifyTime(PE.GetFileModifyTime());
		
		int headerCount = PE.GetHeaderCount();			//��ȡheader����
		/** װ��DOSͷ��PE�ļ���־��PE�ļ�ͷ����ѡͷ�� */
		PEHeader peHeader;		
		Map<String, String> peDetail;						//���ڴ��dosͷ����ϸ���ݣ�����->ֵ
		
		for (int i = 0; i < headerCount; i++)
		{
			peHeader = new PEHeader();					
			peDetail = new HashMap<String, String>();
			
			peHeader.setName(PE.GetHeaderName(i));			//����ͷname
			
			int memberCount = PE.GetMemberCount(i);		
			for (int j = 0; j < memberCount; j++)
			{
				String memberKey = PE.GetMemberName(i, j);
				String memberValue = "0x" + Integer.toHexString(PE.GetMemberValue(i, j));
				peDetail.put(memberKey, memberValue);
			}
			peHeader.setValue(peDetail);					//�����Ա��ϸ��Ϣ
			peHeaders.add(peHeader);
		}
		
		/** װ�ؽڱ���Ϣ */
		Vector<SectionHeader> sectionHeaders = new Vector<SectionHeader>();	//�������нڱ���Ϣ
		SectionHeader sectionHeader;
		Map<String, String> sectionDetail;
		
		int sectionCount = PE.GetSectionCount();

		for (int i = 0; i < sectionCount; i++)
		{
			sectionHeader = new SectionHeader();
			sectionDetail = new HashMap<String, String>();
			
			sectionHeader.setName(PE.GetSectionName(i));	//����ͷname
			int memberCount = PE.GetSectionMemberCount(i);
			for (int j = 0; j < memberCount; j++)
			{
				String sectionKey = PE.GetSectionMemberName(i, j);
				String sectionValue = "0x" + Integer.toHexString(PE.GetSectionMemberValue(i, j));
				sectionDetail.put(sectionKey, sectionValue);
			}
			sectionHeader.setValue(sectionDetail);			//�����Ա��ϸ��Ϣ
			sectionHeaders.add(sectionHeader);
		}
		
		//��װ��PEͷ���ݷŵ�session���棬�Ա�ǰ̨����
		request.getSession().setAttribute("fileInfo", fileInfo);
		request.getSession().setAttribute("headers", peHeaders);
		request.getSession().setAttribute("sectionHeaders", sectionHeaders);
		
		try
		{
			//��ҳ��ת��"/parser/parser_result.jsp"
			RequestDispatcher rd;
			rd = getServletContext().getRequestDispatcher("/parser/parser_result.jsp");
			rd.forward(request, response);
		}
		catch (SocketException e)
		{
		}
		catch (Exception e)
		{
			response.sendError(503, e.getMessage());
		}
		finally
		{
			file.delete();
		}
	}
}
