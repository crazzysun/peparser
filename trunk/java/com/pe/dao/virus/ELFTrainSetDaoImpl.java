package com.pe.dao.virus;

import com.pe.dao.BaseDao;
import com.pe.dao.PagedList;
import com.pe.dao.PostgreSQL;
import com.pe.entity.virus.ELFTrainSet;

public class ELFTrainSetDaoImpl extends BaseDao implements ELFTrainSetDao, PostgreSQL
{
	public long AddTrainSet(ELFTrainSet trainSet) throws Exception
	{
		return esql.helper().insert(trainSet, "t_elf_trainSet", null, "id");
	}

	public PagedList<ELFTrainSet> listTrainSet(int offset, int limit) throws Exception
	{
		PagedList<ELFTrainSet> list = new PagedList<ELFTrainSet>();
		
		/** 暂时不查出result的值，来提高查询速度 */
		String sql = "select id, name, classifier, test_options, option_value, create_time from t_elf_trainSet order by id offset ? limit ?";
		list.setData(esql.list(ELFTrainSet.class, sql, offset, limit));
		
		sql = "select count(*) from t_elf_trainSet";
		list.setTotal(esql.query(Integer.class, sql));
		return list;
	}

	public ELFTrainSet getTrainSetById(long trainSetId) throws Exception
	{
		return esql.helper().query(ELFTrainSet.class, "t_elf_trainSet", null, null, "id = ?", trainSetId);
	}
	public boolean isExist(String name) throws Exception
	{
		String sql = "select id from t_elf_trainSet where name=?";
		Long id = esql.query(Long.class, sql, name);
		return id == null ? false : true;
	}

	public void deleteTrainSetByName(String name) throws Exception
	{
		esql.helper().delete("t_elf_trainSet", "name = ?", name);
	}
	public void deleteTrainSet(long trainSetId) throws Exception
	{
		esql.helper().delete("t_elf_trainSet", "id = ?", trainSetId);
	}
}
