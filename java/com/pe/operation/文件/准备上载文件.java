package com.pe.operation.文件;

import java.io.File;

import javax.servlet.http.HttpSession;

import com.pe.UserException;
import com.pe.operation.Operation;
import com.pe.operation.OperationContext;
import com.pe.web.servlet.UploadServlet;
import com.pe.web.servlet.UploadStatus;

/**
 * 准备上载文件 需要和 UploadServlet 配合使用
 */
public class 准备上载文件 extends AbstractFileOperation implements Operation
{
	private String path;
	
	public void execute() throws Exception
	{
		File work = getWorkFile(path);
		if (!work.isDirectory()) throw new UserException("不能上载到目录: " + path);
		
		UploadStatus status = new UploadStatus(UploadServlet.MAX_FILE_SIZE);
		
		OperationContext context = OperationContext.getContext();
		
		HttpSession session = context.getSession();
		session.setAttribute("upload", status);
		session.setAttribute("upload_directory", work);
	}

	public void setPath(String path)
	{
		this.path = path;
	}
}
