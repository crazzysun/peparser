package com.pe.operation;

/**
 * 错误操作
 * 
 * 如果用户在不适当的时候, 执行了不适当的操作, 比如没有登录, 没有权限等, 就会变成执行本操作
 */
public class 错误操作 implements Operation
{
	private Exception exception = null;
	
	public 错误操作()
	{
	}
	
	public 错误操作(Exception exception)
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
