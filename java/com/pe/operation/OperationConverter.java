package com.pe.operation;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.convert.BeanConverter;
import org.directwebremoting.convert.StringConverter;
import org.directwebremoting.dwrp.ParseUtil;
import org.directwebremoting.dwrp.ProtocolConstants;
import org.directwebremoting.extend.ConverterManager;
import org.directwebremoting.extend.InboundContext;
import org.directwebremoting.extend.InboundVariable;
import org.directwebremoting.extend.MarshallException;
import org.directwebremoting.util.LocalUtil;
import org.directwebremoting.util.Messages;

import com.pe.UserException;

/**
 * 用于DWR的Operation转换器
 */
public class OperationConverter extends BeanConverter
{
	private Log log = LogFactory.getLog(OperationConverter.class);
	
	private StringConverter stringConverter = new StringConverter();
	private BeanConverter beanConverter = new BeanConverter();

	@SuppressWarnings("unchecked")
	public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException
	{
		String value = iv.getValue();

		// If the text is null then the whole bean is null
		if (value.trim().equals(ProtocolConstants.INBOUND_NULL)) { return null; }

		if (!value.startsWith(ProtocolConstants.INBOUND_MAP_START)) 
		{ 
			throw new MarshallException(paramType, Messages.getString("BeanConverter.FormatError", ProtocolConstants.INBOUND_MAP_START)); 
		}

		if (!value.endsWith(ProtocolConstants.INBOUND_MAP_END)) 
		{ 
			throw new MarshallException(paramType, Messages.getString("BeanConverter.FormatError", ProtocolConstants.INBOUND_MAP_START)); 
		}

		value = value.substring(1, value.length() - 1);

		try
		{
			// Loop through the properties passed in
			Map<String, String> tokens = extractInboundTokens(paramType, value);

			// 通过OperationManager获得操作实现类
			String name = tokens.get("name");

			String[] split = ParseUtil.splitInbound(name);
			String splitValue = split[LocalUtil.INBOUND_INDEX_VALUE];
			String splitType = split[LocalUtil.INBOUND_INDEX_TYPE];
			InboundVariable iv2 = new InboundVariable(iv.getLookup(), null, splitType, splitValue);
			
			// 转换操作名
			name = (String) stringConverter.convertInbound(String.class, iv2, inctx);
			
			Class<? extends Operation> type = OperationManager.getInstance().getOperationClass(name);
			
			log.trace("转换操作类: " + type.getName());
			
			// 转换对象
			String data = tokens.get("data");
			
			split = ParseUtil.splitInbound(data);
			splitValue = split[LocalUtil.INBOUND_INDEX_VALUE];
			splitType = split[LocalUtil.INBOUND_INDEX_TYPE];
			iv2 = new InboundVariable(iv.getLookup(), null, splitType, splitValue);

			// 通过BeanConverter转换成实际的Operation对象
			return beanConverter.convertInbound(type, iv2, inctx);
		}
		catch (MarshallException ex)
		{
			throw ex;
		}
		catch (UserException ex)
		{
			return new 错误操作(ex);
		}
		catch (Exception ex)
		{
			throw new MyMarshallException(paramType, ex);
		}
	}
	
	public void setConverterManager(ConverterManager converterManager)
	{
		super.setConverterManager(converterManager);
		beanConverter.setConverterManager(converterManager);
	}
	
}
