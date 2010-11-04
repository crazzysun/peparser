package com.pe.util.bean;

import java.beans.BeanInfo;
import java.lang.reflect.Method;

/**
 * Bean的属性信息
 */
class FieldInfo
{
	private Class<?> type;
	private Method setter;
	private BeanInfo beanInfo;

	public Method getSetter()
	{
		return setter;
	}

	public void setSetter(Method setter)
	{
		this.setter = setter;
	}

	public Class<?> getType()
	{
		return type;
	}

	public void setType(Class<?> type)
	{
		this.type = type;
	}

	public BeanInfo getBeanInfo()
	{
		return beanInfo;
	}

	public void setBeanInfo(BeanInfo beanInfo)
	{
		this.beanInfo = beanInfo;
	}
}
