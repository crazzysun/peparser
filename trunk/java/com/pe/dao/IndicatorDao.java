package com.pe.dao;

import com.pe.entity.Indicator;

public interface IndicatorDao
{

	/**
	 * ͨ��ָ�����ƻ�ȡָ��
	 * @return
	 * @throws Exception
	 */
	public Indicator getIndicatorByName(String name) throws Exception;
}
