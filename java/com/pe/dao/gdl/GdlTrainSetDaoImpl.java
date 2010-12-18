package com.pe.dao.gdl;

import com.pe.dao.BaseDao;
import com.pe.dao.PagedList;
import com.pe.dao.PostgreSQL;
import com.pe.entity.gdl.GdlTrainSet;


public class GdlTrainSetDaoImpl extends BaseDao implements GdlTrainSetDao, PostgreSQL
{
	public long AddTrainSet(GdlTrainSet trainSet) throws Exception
	{
		esql.helper().insert(trainSet, "t_gdl_trainSet", null, "id");
		return getGeneratedId("t_gdl_trainSet");
	}

	public PagedList<GdlTrainSet> listTrainSet(int offset, int limit) throws Exception
	{
		PagedList<GdlTrainSet> list = new PagedList<GdlTrainSet>();

		/** ��ʱ�����result��ֵ������߲�ѯ�ٶ� */
		String sql = "select id, name, classifier, test_options, option_value, create_time from t_gdl_trainSet order by id offset ? limit ?";
		list.setData(esql.list(GdlTrainSet.class, sql, offset, limit));

		sql = "select count(*) from t_gdl_trainSet";
		list.setTotal(esql.query(Integer.class, sql));
		return list;
	}

	public GdlTrainSet getTrainSetById(long trainSetId) throws Exception
	{
		return esql.helper().query(GdlTrainSet.class, "t_gdl_trainSet", null, null, "id = ?", trainSetId);
	}

	public void deleteTrainSet(long trainSetId) throws Exception
	{
		esql.helper().delete("t_gdl_trainSet", "id = ?", trainSetId);
	}

	public boolean isExist(String name) throws Exception
	{
		String sql = "select id from t_gdl_trainSet where name=?";
		Long id = esql.query(Long.class, sql, name);
		return id == null ? false : true;
	}

	public void deleteTrainSetByName(String name) throws Exception
	{
		esql.helper().delete("t_gdl_trainSet", "name = ?", name);
	}
}
