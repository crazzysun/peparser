package com.pe.util.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pe.util.bean.convert.Converter;

/**
 * Bean创建器 通过一系列参数创建指定类型的Bean
 * 
 * @param <T>
 */
public class BeanCreator<T>
{
	private static final Log log = LogFactory.getLog(BeanCreator.class);

	private static Map<String, FieldInfo> cache = new HashMap<String, FieldInfo>();

	private Class<T> type;
	private Map<String, Object> objects = new HashMap<String, Object>();

	public static <TT> TT create(Class<TT> type, Map<String, ? extends Object> values) throws Exception
	{
		BeanCreator<TT> creator = new BeanCreator<TT>();

		return creator.create1(type, values);
	}

	private BeanCreator()
	{
	}

	private T create1(Class<T> type, Map<String, ? extends Object> values) throws Exception
	{
		this.type = type;

		T root = type.newInstance();
		objects.put("", root);

		for (Iterator<String> i = values.keySet().iterator(); i.hasNext();)
		{
			String key = i.next();
			Object value = values.get(key);

			try
			{
				key = '.' + key;
				int n = key.lastIndexOf('.');
				String k1 = key.substring(0, n);
				Object bean = getBean(k1);

				FieldInfo fi = getFieldInfo(type, key);

				Converter converter = Converter.getConverter(fi.getType());
				Method setter = fi.getSetter();
				Object o = converter.convert(value);

				if (log.isTraceEnabled()) log.trace(String.format("设置属性值: %s%s: %s", type.getName(), key, o.getClass().getName()));

				setter.invoke(bean, o);
			}
			catch (Exception e)
			{
				log.warn(String.format("不能设置属性 %s%s: %s", type.getName(), key, value.getClass().getName()), e);
			}
		}

		return root;
	}

	private Object getBean(String key) throws Exception
	{
		Object bean = objects.get(key);
		if (bean == null)
		{
			bean = createBean(key);
			objects.put(key, bean);
		}

		return bean;
	}

	private Object createBean(String key) throws Exception
	{
		int n = key.lastIndexOf('.');
		String k1 = key.substring(0, n);
		Object o = getBean(k1);

		FieldInfo fi = getFieldInfo(type, key);
		Object f = fi.getType().newInstance();

		fi.getSetter().invoke(o, f);

		return f;
	}

	private static FieldInfo getFieldInfo(Class<?> type, String key) throws Exception
	{
		String k = type.getName() + " " + key;

		FieldInfo fi;
		synchronized (cache)
		{
			fi = (FieldInfo) cache.get(k);
			if (fi == null)
			{
				fi = createFieldInfo(type, key);
				cache.put(k, fi);
			}
		}

		return fi;
	}

	private static FieldInfo createFieldInfo(Class<?> type, String key) throws Exception
	{
		if ("".equals(key))
		{
			Class<?> c = (Class<?>) type;

			FieldInfo fi2 = new FieldInfo();
			fi2.setType(c);
			fi2.setBeanInfo(Introspector.getBeanInfo(c));

			return fi2;
		}

		int n = key.lastIndexOf('.');
		String k1 = key.substring(0, n);
		String k2 = key.substring(n + 1);

		FieldInfo fi = getFieldInfo(type, k1);
		BeanInfo bi = fi.getBeanInfo();
		PropertyDescriptor[] pds = bi.getPropertyDescriptors();
		int i;
		for (i = 0; i < pds.length; i++)
		{
			PropertyDescriptor pd = pds[i];
			if (!pd.getName().equals(k2)) continue;

			break;
		}

		if (i >= pds.length) throw new Exception("没有属性: '" + key + "'");
		PropertyDescriptor pd = pds[i];

		Class<?> c = pd.getPropertyType();

		FieldInfo fi2 = new FieldInfo();
		fi2.setType(c);
		fi2.setSetter(pd.getWriteMethod());
		fi2.setBeanInfo(Introspector.getBeanInfo(c));

		return fi2;
	}
}
