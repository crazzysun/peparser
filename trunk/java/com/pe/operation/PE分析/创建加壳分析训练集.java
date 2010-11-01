package com.pe.operation.PE分析;

import java.io.File;

import com.pe.UserException;
import com.pe.operation.Operation;
import com.pe.operation.文件.AbstractFileOperation;
import com.pe.packed.PackClassifier;

public class 创建加壳分析训练集 extends AbstractFileOperation implements Operation
{
	private String classifierName;
	private int classifier;
	private String dataset;
	private String result;
	
	public void execute() throws Exception
	{
		validateClassifierName();
		validateTrainFile();
		
		PackClassifier cls = PackClassifier.BuildClassifier(classifierName, dataset);
		result = cls.GetEvaluation();
		System.out.println(result);
		result = replace(result, "\n", "<br/>");
		result = replace(result, " ", "&nbsp;");
		System.out.println(result);
	}

	private void validateTrainFile() throws UserException
	{
		File file = new File(dataset);
		if (!file.isFile())
			throw new UserException("训练集文件选取错误，请重新选择！");
	}

	private void validateClassifierName() throws UserException
	{
		switch(classifier)
		{
		case 0:
			classifierName = "weka.classifiers.trees.J48";
			break;
		case 1:
			classifierName = "weka.classifiers.functions.MultilayerPerceptron";
			break;
		case 2:
			classifierName = "weka.classifiers.bayes.NaiveBayes";
			break;
		case 3:
			classifierName = "weka.classifiers.lazy.IBk";
			break;
		default:
			throw new UserException("classifier选择错误，请重新选择！");
		}
	}
	
	private String replace(String s, String s1, String s2)
	{
		if (s == null) return null;
		StringBuffer stringbuffer = new StringBuffer();
		int i = s.length();
		int j = s1.length();
		int k;
		int l;
		for (k = 0; (l = s.indexOf(s1, k)) >= 0; k = l + j)
		{
			stringbuffer.append(s.substring(k, l));
			stringbuffer.append(s2);
		}

		if (k < i) stringbuffer.append(s.substring(k));
		return stringbuffer.toString();
	}

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

	public String getClassifierName()
	{
		return classifierName;
	}

	public void setClassifierName(String classifierName)
	{
		this.classifierName = classifierName;
	}

	public int getClassifier()
	{
		return classifier;
	}

	public void setClassifier(int classifier)
	{
		this.classifier = classifier;
	}

	public String getDataset()
	{
		return dataset;
	}

	public void setDataset(String dataset)
	{
		this.dataset = dataset;
	}
}
