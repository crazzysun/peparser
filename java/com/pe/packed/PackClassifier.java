package com.pe.packed;

/*
 * PackClassifier.java
 * Copyright (C) 2010 SiChuan University. All rights reserved.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;

public class PackClassifier
{
	// Property
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
		 */
		return "1.0.0";
	}

	public static PackClassifier BuildClassifier(String ClassifierName, String Dataset) throws Exception
	{
		// Command line parameters
		if (ClassifierName.equals("") || Dataset.equals(""))
		{
			System.out.println("Not all parameters provided!");
			System.out.println(PackClassifier.usage());
			System.exit(2);
		}

		// Internal parameters
		String filter = "weka.filters.unsupervised.instance.Randomize";
		Vector<String> classifierOptions = new Vector<String>();
		Vector<String> filterOptions = new Vector<String>();
		if (ClassifierName.equals("weka.classifiers.trees.J48")) 
			classifierOptions.add("-U");

		// Build specific classifier
		PackClassifier Classifier;
		Classifier = new PackClassifier();
		Classifier.setClassifier(ClassifierName, (String[]) classifierOptions.toArray(new String[classifierOptions.size()]));
		Classifier.setFilter(filter, (String[]) filterOptions.toArray(new String[filterOptions.size()]));
		Classifier.setTraining(Dataset);
		Classifier.execute();

		return Classifier;
	}

	/*
	 * outputs some evaluation data about the classifier
	 */
	public String GetEvaluation()
	{
		StringBuffer result;

		result = new StringBuffer();
		result.append("Classifier...: " + m_Classifier.getClass().getName() + " " + Utils.joinOptions(m_Classifier.getOptions()) + "\n");
		if (m_Filter instanceof OptionHandler)
			result.append("Filter.......: " + m_Filter.getClass().getName() + " "
					+ Utils.joinOptions(((OptionHandler) m_Filter).getOptions()) + "\n");
		else
			result.append("Filter.......: " + m_Filter.getClass().getName() + "\n");
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

	/**
	 * sets the classifier to use
	 * 
	 * @param name
	 *            the classname of the classifier
	 * @param options
	 *            the options for the classifier
	 */
	private void setClassifier(String name, String[] options) throws Exception
	{
		m_Classifier = Classifier.forName(name, options);
	}

	/**
	 * sets the filter to use
	 * 
	 * @param name
	 *            the classname of the filter
	 * @param options
	 *            the options for the filter
	 */
	private void setFilter(String name, String[] options) throws Exception
	{
		m_Filter = (Filter) Class.forName(name).newInstance();
		if (m_Filter instanceof OptionHandler) ((OptionHandler) m_Filter).setOptions(options);
	}

	/**
	 * sets the file to use for training
	 */
	private void setTraining(String name) throws Exception
	{
		m_TrainingFile = name;
		m_Training = new Instances(new BufferedReader(new FileReader(m_TrainingFile)));
		m_Training.setClassIndex(m_Training.numAttributes() - 1);
	}

	/**
	 * runs 10fold CV over the training file
	 */
	private void execute() throws Exception
	{
		// run filter
		m_Filter.setInputFormat(m_Training);
		Instances filtered = Filter.useFilter(m_Training, m_Filter);

		// train classifier on complete file for tree
		m_Classifier.buildClassifier(filtered);

		// 10fold CV with seed=1
		m_Evaluation = new Evaluation(filtered);
		m_Evaluation.crossValidateModel(m_Classifier, filtered, 10, m_Training.getRandomNumberGenerator(1));
	}

	/**
	 * returns the usage of the class
	 */
	private static String usage()
	{
		return "\nusage:\n  " + PackClassifier.class.getName() + "  CLASSIFIER <classname> [options] \n"
				+ "  FILTER <classname> [options]\n" + "  DATASET <trainingfile>\n\n" + "e.g., \n"
				+ "  java -classpath \".:weka.jar\" WekaDemo \n" + "    CLASSIFIER weka.classifiers.trees.J48 -U \n"
				+ "    FILTER weka.filters.unsupervised.instance.Randomize \n" + "    DATASET iris.arff\n";
	}
}
