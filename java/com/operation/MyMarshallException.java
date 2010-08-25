package com.operation;

import org.directwebremoting.extend.MarshallException;

public class MyMarshallException extends MarshallException
{
	private static final long serialVersionUID = 5576251341244074267L;

	public MyMarshallException(Class<?> paramType, Throwable ex)
	{
		super(paramType, ex);
	}

	@Override
	public String getMessage()
	{
		return this.getCause().getMessage();
	}
}
