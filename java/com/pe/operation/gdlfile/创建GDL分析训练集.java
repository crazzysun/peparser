package com.pe.operation.gdlfile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.pe.UserException;
import com.pe.dao.DaoManager;
import com.pe.dao.gdl.GdlTrainSetDao;
import com.pe.entity.gdl.GdlResult;
import com.pe.entity.gdl.GdlResultShow;
import com.pe.entity.gdl.GdlTrainSet;
import com.pe.operation.Operation;
import com.pe.operation.�ļ�.AbstractFileOperation;
import com.pe.gdl.CreateGdlHTML;
import com.pe.gdl.GdlClassifier;
import com.pe.util.Serialize;
import com.pe.util.SystemConfigure;
import com.pe.util.Util;

public class ����GDL����ѵ���� extends AbstractFileOperation implements Operation
{
	private String classifierName;
	private int classifier;
	private String dataset;
	private int testOptions;
	private int optionValue;
	private String trainSetName;

	public void execute() throws Exception
	{
		/** ����ѵ���� */
		validateClassifierName();
		validateTrainFile();
		GdlClassifier cls = GdlClassifier.BuildClassifier(classifierName, dataset, testOptions, optionValue);

		/** ����ѵ������������� */
		GdlTrainSet trainSet = new GdlTrainSet();
		trainSet.setName(trainSetName);
		trainSet.setClassifier(classifierName);
		trainSet.setTestOptions(testOptions);
		trainSet.setOptionValue(optionValue);
		trainSet.setCreateTime(Util.showNowTime());
		trainSet.setInstanceNum(cls.getInstanceNum());
		trainSet.setCorrectNum(cls.getCorrectNum());
		trainSet.setIncorrectNum(cls.getIncorrectNum());
		trainSet.setCorrectRate(cls.getCorrectRate());

		List<GdlResultShow> resultShow = new ArrayList<GdlResultShow>();

		for (int i = 0; i < cls.getClassSize(); i++)
		{
			GdlResultShow result = new GdlResultShow();
			result.setClassName(cls.getClassName(i));
			result.setTotalNum(cls.getTruePositiveNum(i) + cls.getFalsePositiveNum(i));
			result.setTrueNum(cls.getTruePositiveNum(i));
			result.setFalseNum(cls.getFalsePositiveNum(i));
			result.setPrecision(cls.getPrecision(i));
			resultShow.add(result);
		}
		trainSet.setResultShow(resultShow);
		
		String tree = cls.getModal();
		
		//��������ȥ������ǰ����
		int loc = 0;
		for (int i = 0; i < 3; i++)
		{
			loc = tree.indexOf("\n", loc);
			loc += 1;
		}
		trainSet.setTree(Util.replace2HTML(tree.substring(loc)));

		GdlResult rst = new GdlResult();
		rst.setClassifier(cls.getClassifier());
		rst.setInstance(cls.getTraining());
		trainSet.setResult(Serialize.serializeData(rst));
		// try
		// {
		// trainSet.setResult(Serialize.serializeData(cls));
		// }
		// catch (NotSerializableException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		
		/** ����������ݿ� */
		GdlTrainSetDao trainSetDao = DaoManager.getInstance().getDao(GdlTrainSetDao.class);
		trainSetDao.AddTrainSet(trainSet);

		/** ��������ļ� */
		CreateGdlHTML HTML = new CreateGdlHTML(trainSet);
		HTML.create();
	}

	private void validateTrainFile() throws Exception
	{
		if (dataset == null || dataset.equals(""))
		{
			dataset = SystemConfigure.get("DefaultGdlTrainSample");
		}
		else
		{
			/** ֻȡ�ļ��������ie�Ͱ汾�İ�ȫ���� */
			dataset = dataset.replace('\\', '/');
			int k = dataset.lastIndexOf("/");
			if (k > 0) dataset = dataset.substring(k + 1);

			File file = new File(getWorkFile(""), dataset);
			if (!file.isFile()) throw new UserException("ѵ�����ļ�ѡȡ����������ѡ��");
			dataset = file.getAbsolutePath();
		}
	}

	/** ����ǰ̨��select�ؼ�ֵ����ȡ��������ֵ */
	private void validateClassifierName() throws UserException
	{
		switch (classifier)
		{
		case 1:
			classifierName = "C4.5";
			break;
		case 2:
			classifierName = "MultilayerPerceptron";
			break;
		case 3:
			classifierName = "NaiveBayes";
			break;
		case 4:
			classifierName = "IBk";
			break;
		default:
			throw new UserException("classifierѡ�����������ѡ��");
		}
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

	public int getTestOptions()
	{
		return testOptions;
	}

	public void setTestOptions(int testOptions)
	{
		this.testOptions = testOptions;
	}

	public int getOptionValue()
	{
		return optionValue;
	}

	public void setOptionValue(int optionValue)
	{
		this.optionValue = optionValue;
	}

	public String getTrainSetName()
	{
		return trainSetName;
	}

	public void setTrainSetName(String trainSetName)
	{
		this.trainSetName = trainSetName;
	}
}
