package com.pe.dao;

import com.pe.entity.Indicator;

public class IndicatorDaoImpl extends BaseDao implements IndicatorDao, PostgreSQL
{

	public Indicator getIndicatorByName(String name) throws Exception
	{
		return esql.helper().query(Indicator.class, "t_indicator", null, null, "name = ?", name);
	}

}
