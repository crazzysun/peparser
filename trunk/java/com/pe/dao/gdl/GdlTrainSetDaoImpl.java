package com.pe.dao.gdl;

import com.pe.dao.BaseDao;
import com.pe.dao.PagedList;
import com.pe.dao.PostgreSQL;
import com.pe.entity.gdl.GdlTrainSet;


public class GdlTrainSetDaoImpl extends BaseDao implements GdlTrainSetDao, PostgreSQL
{
	public long AddTrainSet(GdlTrainSet trainSet) throws Exception
	{
		return esql.helper().insert(trainSet, "t_gdl_trainSet", null, "id");
	}

	public PagedList<GdlTrainSet> listTrainSet(int offset, int limit) throws Exception
	{
		PagedList<GdlTrainSet> list = new PagedList<GdlTrainSet>();

		/** 暂时不查出result的值，来提高查询速度 */
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

}
