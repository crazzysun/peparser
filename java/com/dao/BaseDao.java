package com.dao;

import zuojie.esql.Esql;

public abstract class BaseDao
{
	protected Esql esql;

	protected BaseDao()
	{
	}

	public void setEsql(Esql esql)
	{
		this.esql = esql;
	}

	public long getGeneratedId(String table) throws Exception
	{
		String sql = "select get_generated_id(?)";
		return esql.query(Long.class, sql, table);
	}
}
