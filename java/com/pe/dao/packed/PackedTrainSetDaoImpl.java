package com.pe.dao.packed;

import com.pe.dao.BaseDao;
import com.pe.dao.PagedList;
import com.pe.dao.PostgreSQL;
import com.pe.entity.packed.PackedTrainSet;

public class PackedTrainSetDaoImpl extends BaseDao implements PackedTrainSetDao, PostgreSQL
{
	public long AddTrainSet(PackedTrainSet trainSet) throws Exception
	{
		return esql.helper().insert(trainSet, "t_packed_trainSet", null, "id");
	}

	public PagedList<PackedTrainSet> listTrainSet(int offset, int limit) throws Exception
	{
		PagedList<PackedTrainSet> list = new PagedList<PackedTrainSet>();
		
		/** 暂时不查出result的值，来提高查询速度 */
		String sql = "select id, name, classifier, test_options, option_value, create_time from t_packed_trainSet order by id offset ? limit ?";
		list.setData(esql.list(PackedTrainSet.class, sql, offset, limit));
		
		sql = "select count(*) from t_packed_trainSet";
		list.setTotal(esql.query(Integer.class, sql));
		return list;
	}

	public PackedTrainSet getTrainSetById(long trainSetId) throws Exception
	{
		return esql.helper().query(PackedTrainSet.class, "t_packed_trainSet", null, null, "id = ?", trainSetId);
	}
	
}
