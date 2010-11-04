package com.pe.web.servlet;

import org.apache.commons.fileupload.ProgressListener;

import com.pe.entity.Bean;

public class UploadStatus implements ProgressListener, Bean
{
	private static final long serialVersionUID = 6184793287646817218L;

	private long limit;

	private boolean completed = false;
	
	private boolean aborted = false;
	private String reason = "";

	private int item;
	private long read;
	private long length = 1;

	public UploadStatus(long limit)
	{
		this.limit = limit;
	}

	public synchronized void update(long read, long length, int item)
	{
		if (length > limit) throw new RuntimeException("文件大小超界");

		this.item = item;
		this.read = read;
		this.length = length;
	}

	public void abort(String reason)
	{
		this.aborted = true;
		this.reason = reason;
	}
	public void complete()
	{
		this.completed = true;
	}
	
	//======================================
	
	public long getRead()
	{
		return read;
	}
	public long getLength()
	{
		return length;
	}
	public boolean isCompleted()
	{
		return completed;
	}
	public boolean isAborted()
	{
		return aborted;
	}
	public int getItem()
	{
		return item;
	}
	public String getReason()
	{
		return reason;
	}
}
