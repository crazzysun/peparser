package com.pe.entity.virus;

import weka.core.Instances;

import com.pe.entity.Bean;

/**
 * ����weka��Evaluation�����bug��û�м̳�Serializable�޷����л�
 * ���Դ˴�ר��д��PackedResult���������кŵĶ���
 * Ϊ��һ�����жϼӿǷ���
 * 
 * @author FangZhiyang
 *
 */
public class VirusResult implements Bean
{
	private static final long serialVersionUID = -3052016730065244260L;

	private Instances instance;
	private weka.classifiers.Classifier classifier;
	public Instances getInstance()
	{
		return instance;
	}
	public void setInstance(Instances instance)
	{
		this.instance = instance;
	}
	public weka.classifiers.Classifier getClassifier()
	{
		return classifier;
	}
	public void setClassifier(weka.classifiers.Classifier classifier)
	{
		this.classifier = classifier;
	}
}
