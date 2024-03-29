package com.pe.dao.GBID;

import com.pe.dao.PagedList;
import com.pe.entity.GBID.ChatResult;
import com.pe.entity.GBID.RulesLib;

public interface GBIDDao
{
	/**
	 * 增加模式集
	 * @param lib
	 * @return
	 * @throws Exception
	 */
	public long AddRuleslib(RulesLib lib) throws Exception;
	
	public PagedList<RulesLib> listRulesLib(int offset, int limit) throws Exception;
	
	public RulesLib getLibById(long id) throws Exception;
	
	public void deleteRulesLib(long id) throws Exception;
	
	public void deleteRulesLibByName(String name) throws Exception;
	
	public boolean isExist(String name) throws Exception;
	
	public void addChatResult(ChatResult result) throws Exception;
	
	public ChatResult getChatResult(String name) throws Exception;
	
	public void addChatResult1(ChatResult result) throws Exception;
	
	public ChatResult getChatResult1(String name) throws Exception;	
}
