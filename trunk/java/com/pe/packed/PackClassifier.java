package com.pe.packed;
/*
 * PackClassifier.java
 * 
 * Copyright (C) 2010 SiChuan University. All rights reserved.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Vector;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import java.io.Serializable;

public class PackClassifier implements Serializable
{
	private static final long serialVersionUID = -8199292047586576593L;
	// Property
	protected String m_ClassifierName = null;
	protected Classifier m_Classifier = null;
	protected Filter m_Filter = null;
	protected String m_TrainingFile = null;
	protected Instances m_Training = null;
	protected Evaluation m_Evaluation = null;

	public static String GetVersion()
	{
		/*
		 * Version Date Comments -------- --------- -----------------------
		 * 1.0.0 Oct. 21, 2010 First version 
		 * 1.1.0 Nov. 06, 2010 Added parameter
		 * configuration
		 */
		return "1.1.0";
	}

	public static PackClassifier BuildClassifier(String ClassifierName, String Dataset, int TestType, int TestParam) throws Exception
	{
		// Parameter validation
		if (ClassifierName.equals("") || Dataset.equals("")) 
			throw new Exception("Classifier name or dataset isn't provided");
		if (TestType != 0 && TestType != 1) 
			throw new Exception("Unknown test types!");
		if (TestType == 0 && TestParam < 2)
			throw new Exception("Fold number must be greater than 1");
		if (TestType == 1 && (TestParam <= 0 || TestParam >= 100)) 
			throw new Exception("Percentage split value need to be > 0 and < 100!");

		// Internal parameters
		String WekaClassifierName = null;
		String filter = "weka.filters.unsupervised.instance.Randomize";
		Vector<String> classifierOptions = new Vector<String>();
		Vector<String> filterOptions = new Vector<String>();

		 if (ClassifierName.equals("C4.5")) {
		 WekaClassifierName = "weka.classifiers.trees.J48";
		 classifierOptions.add("-U");
		 } else if (ClassifierName.equals("MultiLayerPerceptron")) {
		 WekaClassifierName =
		 "weka.classifiers.functions.MultilayerPerceptron";
		 } else if (ClassifierName.equals("IBk")) {
		 WekaClassifierName = "weka.classifiers.lazy.IBk";
		 } else if (ClassifierName.equals("NaiveBayes")) {
		 WekaClassifierName = "weka.classifiers.bayes.NaiveBayes";
		 } else
		 throw new Exception("Unknown classifier type!");

		// Build specific classifier
		PackClassifier Classifier = new PackClassifier();
		Classifier.setClassifier(ClassifierName, WekaClassifierName, (String[]) classifierOptions.toArray(new String[classifierOptions.size()]));
		Classifier.setFilter(filter, (String[]) filterOptions.toArray(new String[filterOptions.size()]));
		Classifier.setTraining(Dataset);
		if (TestType == 0)
		{
			Classifier.CrossValidateExecute(TestParam);
		}
		else
			Classifier.SplitValidateExecute(TestParam);

		return Classifier;
	}

	public String ClassifyInstance(String filename) throws Exception
	{
		String FileName = filename + ".arff";
		Instances test = DataSource.read(FileName);
		test.setClassIndex(test.numAttributes() - 1);
		if (!m_Training.equalHeaders(test)) throw new IllegalArgumentException("Train data and test data are not compatible!");

		double pred = m_Classifier.classifyInstance(test.instance(0));
		String ClassType = test.classAttribute().value((int) pred);

		return ClassType;
	}

	public String GetEvaluation()
	{
		StringBuffer result;

		result = new StringBuffer();
		result.append("Classifier...: " + m_ClassifierName + " " + Utils.joinOptions(m_Classifier.getOptions()) + "\n");
		if (m_Filter != null)
		{
			if (m_Filter instanceof OptionHandler)
				result.append("Filter.......: " + m_Filter.getClass().getName() + " "
						+ Utils.joinOptions(((OptionHandler) m_Filter).getOptions()) + "\n");
			else
				result.append("Filter.......: " + m_Filter.getClass().getName() + "\n");
		}
		result.append("Training file: " + m_TrainingFile + "\n");
		result.append("\n");

		result.append(m_Classifier.toString() + "\n");
		result.append(m_Evaluation.toSummaryString() + "\n");
		try
		{
			result.append(m_Evaluation.toMatrixString() + "\n");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			result.append(m_Evaluation.toClassDetailsString() + "\n");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result.toString();
	}

	// ////////////////////////////////////////////////////////////////////////
	private void setClassifier(String OfficialName, String WekaName, String[] options) throws Exception
	{
		m_ClassifierName = OfficialName;
		m_Classifier = Classifier.forName(WekaName, options);
	}

	private void setFilter(String name, String[] options) throws Exception
	{
		/*
		 * m_Filter = (Filter) Class.forName(name).newInstance(); 
		 * if (m_Filter instanceof OptionHandler) ((OptionHandler) m_Filter).setOptions(options);
		 */
	}

	private void setTraining(String name) throws Exception
	{
		m_TrainingFile = name;
		m_Training = new Instances(new BufferedReader(new FileReader(m_TrainingFile)));
		m_Training.setClassIndex(m_Training.numAttributes() - 1);
	}

	private void CrossValidateExecute(int FoldNum) throws Exception
	{
		m_Classifier.buildClassifier(m_Training);

		m_Evaluation = new Evaluation(m_Training);
		m_Evaluation.crossValidateModel(m_Classifier, m_Training, FoldNum, m_Training.getRandomNumberGenerator(1));
	}

	private void SplitValidateExecute(int SplitPercentage) throws Exception
	{
		int TrainSize = (int) Math.round(m_Training.numInstances() * SplitPercentage / 100);
		int TestSize = m_Training.numInstances() - TrainSize;
		//Instances TrainInstance = new Instances(m_Training, 0, TrainSize);
		Instances TestInstance = new Instances(m_Training, TrainSize, TestSize);

		m_Classifier.buildClassifier(m_Training);

		m_Evaluation = new Evaluation(m_Training);
		m_Evaluation.evaluateModel(m_Classifier, TestInstance);
	}
	
	public Instances getTraining()
	{
		return m_Training;
	}
	
	public Classifier getClassifier()
	{
		return m_Classifier;
	}
	
	public int getClassSize() throws Exception
	{
		return m_Training.numClasses();
	}
	
	public String getModal() throws Exception
	{
		return m_Classifier.toString();
	}

	public String getMatrix() throws Exception
	{
		return m_Evaluation.toMatrixString();
	}

	public String getClassName(int ClassIndex)
	{
		return m_Training.classAttribute().value(ClassIndex);
	}

	public int getInstanceNum()
	{
		return (int) m_Evaluation.numInstances();
	}

	public int getCorrectNum()
	{
		return (int) m_Evaluation.correct();
	}

	public int getIncorrectNum()
	{
		return (int) m_Evaluation.incorrect();
	}

	public double getErrorRate()
	{
		return m_Evaluation.errorRate();
	}
	
	/** 得到正确率（6位小数） */
	public String getCorrectRate()
	{
		BigDecimal a = new BigDecimal(1 - getErrorRate());   
		return a.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
	}

	public int getTruePositiveNum(int ClassIndex)
	{
		return (int) m_Evaluation.numTruePositives(ClassIndex);
	}

	public int getFalsePositiveNum(int ClassIndex)
	{
		return (int) m_Evaluation.numFalsePositives(ClassIndex);
	}

	public int getTrueNegativeNum(int ClassIndex)
	{
		return (int) m_Evaluation.numTrueNegatives(ClassIndex);
	}

	public int getFalseNegativeNum(int ClassIndex)
	{
		return (int) m_Evaluation.numFalseNegatives(ClassIndex);
	}

	public double getTruePositiveRate(int ClassIndex)
	{
		return m_Evaluation.truePositiveRate(ClassIndex);
	}

	public double getFalsePositiveRate(int ClassIndex)
	{
		return m_Evaluation.falsePositiveRate(ClassIndex);
	}

	public double getTrueNegativeRate(int ClassIndex)
	{
		return m_Evaluation.trueNegativeRate(ClassIndex);
	}

	public double getFalseNegativeRate(int ClassIndex)
	{
		return m_Evaluation.falseNegativeRate(ClassIndex);
	}

	public String getPrecision(int ClassIndex)
	{
		BigDecimal a = new BigDecimal(m_Evaluation.precision(ClassIndex));   
		return a.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
	}
}
