package com.pe.dao.GBID;

import com.pe.dao.BaseDao;
import com.pe.dao.PagedList;
import com.pe.dao.PostgreSQL;
import com.pe.entity.GBID.ChatResult;
import com.pe.entity.GBID.RulesLib;

public class GBIDDaoImpl extends BaseDao implements GBIDDao, PostgreSQL
{

	@Override
	public long AddRuleslib(RulesLib lib) throws Exception
	{
		return esql.helper().insert(lib, "t_rules_lib", null, "id");
	}

	@Override
	public void deleteRulesLib(long id) throws Exception
	{
		esql.helper().delete("t_rules_lib", "id = ?", id);
	}

	@Override
	public void deleteRulesLibByName(String name) throws Exception
	{
		esql.helper().delete("t_rules_lib", "name = ?", name);
	}

	@Override
	public RulesLib getLibById(long id) throws Exception
	{
		return esql.helper().query(RulesLib.class, "t_rules_lib", null, null, "id = ?", id);
	}

	@Override
	public boolean isExist(String name) throws Exception
	{
		String sql = "select id from t_rules_lib where name = ?";
		Long id = esql.query(Long.class, sql, name);
		return id == null ? false : true;
	}

	@Override
	public PagedList<RulesLib> listRulesLib(int offset, int limit) throws Exception
	{
		PagedList<RulesLib> list = new PagedList<RulesLib>();
		
		/** 暂时不查出result的值，来提高查询速度 */
		String sql = "select * from t_rules_lib order by id offset ? limit ?";
		list.setData(esql.list(RulesLib.class, sql, offset, limit));
		
		sql = "select count(*) from t_rules_lib";
		list.setTotal(esql.query(Integer.class, sql));
		return list;
	}

	@Override
	public void addChatResult(ChatResult result) throws Exception
	{
		ChatResult rs = esql.helper().query(ChatResult.class, "t_chat_result", null, "result", "name=?", result.getName());
		if (rs != null)
		{
			esql.helper().delete("t_chat_result", "name=?", result.getName());
		}
		esql.helper().insert(result, "t_chat_result", null, null);
	}

	@Override
	public ChatResult getChatResult(String name) throws Exception
	{
		return esql.helper().query(ChatResult.class, "t_chat_result", null, null, "name=?", name);
	}

	@Override
	public void addChatResult1(ChatResult result) throws Exception
	{
		ChatResult rs = esql.helper().query(ChatResult.class, "t_chat_result1", null, "result", "name=?", result.getName());
		if (rs != null)
		{
			esql.helper().delete("t_chat_result1", "name=?", result.getName());
		}
		esql.helper().insert(result, "t_chat_result1", null, null);
	}

	@Override
	public ChatResult getChatResult1(String name) throws Exception
	{
		return esql.helper().query(ChatResult.class, "t_chat_result1", null, null, "name=?", name);
	}
}
