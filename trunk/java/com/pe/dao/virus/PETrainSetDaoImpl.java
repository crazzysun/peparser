package com.pe.dao.virus;

import com.pe.dao.BaseDao;
import com.pe.dao.PagedList;
import com.pe.dao.PostgreSQL;
import com.pe.entity.virus.PETrainSet;

public class PETrainSetDaoImpl extends BaseDao implements PETrainSetDao, PostgreSQL
{
	public long AddTrainSet(PETrainSet trainSet) throws Exception
	{
		return esql.helper().insert(trainSet, "t_pe_trainSet", null, "id");
	}

	public PagedList<PETrainSet> listTrainSet(int offset, int limit) throws Exception
	{
		PagedList<PETrainSet> list = new PagedList<PETrainSet>();
		
		/** 暂时不查出result的值，来提高查询速度 */
		String sql = "select id, name, classifier, test_options, option_value, create_time from t_pe_trainSet order by id offset ? limit ?";
		list.setData(esql.list(PETrainSet.class, sql, offset, limit));
		
		sql = "select count(*) from t_pe_trainSet";
		list.setTotal(esql.query(Integer.class, sql));
		return list;
	}

	public PETrainSet getTrainSetById(long trainSetId) throws Exception
	{
		return esql.helper().query(PETrainSet.class, "t_pe_trainSet", null, null, "id = ?", trainSetId);
	}
	public boolean isExist(String name) throws Exception
	{
		String sql = "select id from t_pe_trainSet where name=?";
		Long id = esql.query(Long.class, sql, name);
		return id == null ? false : true;
	}

	public void deleteTrainSetByName(String name) throws Exception
	{
		esql.helper().delete("t_pe_trainSet", "name = ?", name);
	}
	public void deleteTrainSet(long trainSetId) throws Exception
	{
		esql.helper().delete("t_pe_trainSet", "id = ?", trainSetId);
	}
}
