package com.pe.operation.����;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import weka.core.Attribute;
import weka.core.Instances;

import com.pe.UserException;
import com.pe.dao.DaoManager;
import com.pe.dao.IndicatorDao;
import com.pe.entity.Indicator;
import com.pe.operation.Operation;
import com.pe.util.FileManager;

public class ��arff�ļ�ָ�� implements Operation
{
	private String dataset;
	private List<Indicator> data;
	private int total;
	
	@SuppressWarnings("unchecked")
	public void execute() throws Exception
	{
		IndicatorDao dao = DaoManager.getInstance().getDao(IndicatorDao.class);
		
		validateTrainFile();
		Instances m_Training = new Instances(new BufferedReader(new FileReader(dataset)));
		m_Training.setClassIndex(m_Training.numAttributes() - 1);
		
		Enumeration<Attribute> e = m_Training.enumerateAttributes();
		List<String> indicatorNames = new ArrayList<String>();
		
		while (e.hasMoreElements())
		{
			Attribute att = e.nextElement();
			
			//�ų����һ������ָ��
			if (att.name().equalsIgnoreCase(m_Training.classAttribute().name())) continue;
			indicatorNames.add(att.name());
		}
		
		data = new ArrayList<Indicator>();
		for (String name : indicatorNames)
		{
			data.add(dao.getIndicatorByName(name));
		}
		
		total = data.size();
	}
	
	private void validateTrainFile() throws Exception
	{
		/** ֻȡ�ļ��������ie�Ͱ汾�İ�ȫ���� */
		dataset = dataset.replace('\\', '/');
		int k = dataset.lastIndexOf("/");
		if (k > 0) dataset = dataset.substring(k + 1);

		File file = new File(FileManager.getInstance().getPEHome(), dataset);
		if (!file.isFile()) throw new UserException("ѵ�����ļ�ѡȡ����������ѡ��");
		dataset = file.getAbsolutePath();
	}

	public String getDataset()
	{
		return dataset;
	}

	public void setDataset(String dataset)
	{
		this.dataset = dataset;
	}

	public List<Indicator> getData()
	{
		return data;
	}

	public void setData(List<Indicator> data)
	{
		this.data = data;
	}

	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}
}
