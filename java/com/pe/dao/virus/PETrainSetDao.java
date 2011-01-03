package com.pe.dao.virus;

import com.pe.dao.PagedList;
import com.pe.entity.virus.PETrainSet;

/**
 * All about trainSet
 * 
 * @author FangZhiyang
 *
 */
public interface PETrainSetDao
{
	/**
	 * Add trainSet
	 * @param trainSet
	 * @return TrainSetId
	 * @throws Exception
	 */
	public long AddTrainSet(PETrainSet trainSet) throws Exception;
	/**
	 * Delete the trainSet
	 * @param trainSetId
	 * @throws Exception
	 */
	public void deleteTrainSet(long trainSetId) throws Exception;
	/**
	 * Delete the trainSet by name
	 * @param name
	 * @throws Exception
	 */
	public void deleteTrainSetByName(String name) throws Exception;
	/**
	 * PagedList all trainSet
	 * @param offset
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	public PagedList<PETrainSet> listTrainSet(int offset, int limit) throws Exception;
	
	/**
	 * GetTrainSet by id
	 * @param trainSetId
	 * @return
	 * @throws Exception
	 */
	public PETrainSet getTrainSetById(long trainSetId) throws Exception;
	/**
	 * Judge the existence of a particular training set
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public boolean isExist(String name) throws Exception;
}
