package com.pe.operation;

/**
 * �������
 * 
 * ����û��ڲ��ʵ���ʱ��, ִ���˲��ʵ��Ĳ���, ����û�е�¼, û��Ȩ�޵�, �ͻ���ִ�б�����
 */
public class ������� implements Operation
{
	private Exception exception = null;
	
	public �������()
	{
	}
	
	public �������(Exception exception)
	{
		this.exception = exception;
	}
	
	public void execute() throws Exception
	{
		throw exception;
	}
	
	public String toString()
	{
		return getClass().getCanonicalName() + ": " + exception.getMessage();
	}
}
