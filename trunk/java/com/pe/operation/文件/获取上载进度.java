package com.pe.operation.�ļ�;

import com.pe.operation.Operation;
import com.pe.operation.OperationContext;
import com.pe.web.servlet.UploadStatus;

public class ��ȡ���ؽ��� implements Operation
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
