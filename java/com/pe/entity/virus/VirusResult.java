package com.pe.entity.virus;

import weka.core.Instances;

import com.pe.entity.Bean;

/**
 * 由于weka的Evaluation类存在bug，没有继承Serializable无法序列化
 * 所以此处专门写个PackedResult保存能序列号的对象
 * 为下一步，判断加壳服务
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
