package com.pe.dao;

import com.pe.entity.Indicator;

public interface IndicatorDao
{

	/**
	 * 通过指标名称获取指标
	 * @return
	 * @throws Exception
	 */
	public Indicator getIndicatorByName(String name) throws Exception;
}
