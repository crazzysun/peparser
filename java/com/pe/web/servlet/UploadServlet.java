package com.pe.web.servlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pe.UserException;

/**
 * 上载文件到用户目录
 */
public class UploadServlet extends HttpServlet
{
	private static final long serialVersionUID = 7182578205999284495L;

	public static final int MAX_FILE_SIZE = 1000 * 1024 * 1024;

	private Log log = LogFactory.getLog(UploadServlet.class);

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		UploadStatus status = (UploadStatus) session.getAttribute("upload");
		File work = (File) session.getAttribute("upload_directory");

//		DaoManager dm = DaoManager.getInstance();
		try
		{
//			dm.begin();
			
			if (status == null || work == null) throw new UserException("上载未准备就绪");

			// 上载文件 
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1 * 1024 * 1024);

			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			upload.setSizeMax(MAX_FILE_SIZE);
			upload.setFileSizeMax(MAX_FILE_SIZE);
			upload.setProgressListener(status);
			
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items)
			{
				if (item.isFormField()) continue;
				
				String name = item.getName();
				name = name.replace('\\', '/');
				int k = name.lastIndexOf("/");
				if (k > 0) name  = name.substring(k + 1);
				
				upload(work, name, item);
				
				if (log.isDebugEnabled()) log.debug("文件: " + name + " 上载完成");
			}
			
			status.complete();
			
//			dm.commit();
		}
		catch (Exception e)
		{
			status.abort(e.getMessage());
			if (log.isDebugEnabled()) log.debug("上载文件错误", e);
		}
		finally
		{
//			dm.end();
		}
	}

	private void upload(File work, String name, FileItem item) throws Exception
	{
		OutputStream output = null;
		try
		{
			File file = new File(work, name);
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
		}
		finally
		{
			if (output != null) try { output.close(); } catch (Exception e) {}
		}
	}
}
