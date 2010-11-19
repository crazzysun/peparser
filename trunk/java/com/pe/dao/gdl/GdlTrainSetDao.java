package com.pe.dao.gdl;

import com.pe.dao.PagedList;
import com.pe.entity.gdl.GdlTrainSet;

/**
 * All about trainSet
 * 
 * @author ZhaoZongQu
 *
 */
public interface GdlTrainSetDao
{
	/**
	 * Add trainSet
	 * @param trainSet
	 * @return TrainSetId
	 * @throws Exception
	 */
	public long AddTrainSet(GdlTrainSet trainSet) throws Exception;
	
	/**
	 * PagedList all trainSet
	 * @param offset
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public PagedList<GdlTrainSet> listTrainSet(int offset, int limit) throws Exception;
	
	/**
	 * GetTrainSet by id
	 * @param trainSetId
	 * @return
	 * @throws Exception
	 */
	public GdlTrainSet getTrainSetById(long trainSetId) throws Exception;
}
