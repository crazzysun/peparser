package com.pe.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
//		HttpSession session = request.getSession();
//		UploadStatus status = (UploadStatus) session.getAttribute("upload");
//		File work = (File) session.getAttribute("upload_directory");
//
//		DaoManager dm = DaoManager.getInstance();
//		try
//		{
//			dm.begin();
//			
//			if (status == null || work == null) throw new UserException("上载未准备就绪");
//
//			// 获取指定用户的磁盘配额
//			Account account = (Account) session.getAttribute("account");
//			User user = account.getUser();
//			UserDao dao = DaoManager.getInstance().getDao(UserDao.class);
//			Quota quota = dao.getUserQuota(user);
//			
//			// 上载文件 
//			DiskFileItemFactory factory = new DiskFileItemFactory();
//			factory.setSizeThreshold(1 * 1024 * 1024);
//
//			ServletFileUpload upload = new ServletFileUpload(factory);
//			upload.setHeaderEncoding("UTF-8");
//			upload.setSizeMax(MAX_FILE_SIZE);
//			upload.setFileSizeMax(MAX_FILE_SIZE);
//			upload.setProgressListener(status);
//			
//			List<FileItem> items = upload.parseRequest(request);
//			for (FileItem item : items)
//			{
//				if (item.isFormField()) continue;
//				
//				// 检查是否超过磁盘配额
//				if (item.getSize() + quota.getUsed() > quota.getQuota()) throw new UserException("上载文件超过磁盘配额");
//				
//				String name = item.getName();
//				name = name.replace('\\', '/');
//				int k = name.lastIndexOf("/");
//				if (k > 0) name  = name.substring(k + 1);
//				
//				if (log.isDebugEnabled()) log.debug("准备上载文件: " + name);
//				
//				upload(user, work, name, item);
//				
//				break;
//			}
//			
//			status.complete();
//			
//			dm.commit();
//		}
//		catch (Exception e)
//		{
//			status.abort(e.getMessage());
//			if (log.isDebugEnabled()) log.debug("上载文件错误", e);
//		}
//		finally
//		{
//			dm.end();
//		}
	}

//	private void upload(User user, File work, String name, FileItem item) throws Exception
//	{
//		UserDao dao = DaoManager.getInstance().getDao(UserDao.class);
//
//		OutputStream output = null;
//		try
//		{
//			File file = new File(work, name);
//			if (file.exists())
//			{
//				dao.updateUserDiskUse(user, file.length());
//
//				file.delete();
//			}
//
//			dao.updateUserDiskUse(user, item.getSize());
//			
//			InputStream input = item.getInputStream();
//			output = new BufferedOutputStream(new FileOutputStream(file));
//			byte[] buffer = new byte[32 * 1024];
//			for (; ; )
//			{
//				int n = input.read(buffer);
//				if (n <= 0) break;
//				output.write(buffer, 0, n);
//			}
//		}
//		finally
//		{
//			if (output != null) try { output.close(); } catch (Exception e) {}
//		}
//	}
}
