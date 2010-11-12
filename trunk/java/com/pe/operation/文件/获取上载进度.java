package com.pe.operation.文件;

import com.pe.operation.Operation;
import com.pe.operation.OperationContext;
import com.pe.web.servlet.UploadStatus;

public class 获取上载进度 implements Operation
{
	private UploadStatus status;
	
	public void execute() throws Exception
	{
		OperationContext context = OperationContext.getContext();
		
		status = (UploadStatus) context.getSession().getAttribute("upload");
	}

	public UploadStatus getStatus()
	{
		return status;
	}
}
