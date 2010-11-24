package com.pe.operation.packed;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.pe.UserException;
import com.pe.dao.DaoManager;
import com.pe.dao.packed.PackedTrainSetDao;
import com.pe.entity.packed.PackedResult;
import com.pe.entity.packed.PackedResultShow;
import com.pe.entity.packed.PackedTrainSet;
import com.pe.operation.Operation;
import com.pe.operation.�ļ�.AbstractFileOperation;
import com.pe.packed.CreatePackedHTML;
import com.pe.packed.PackClassifier;
import com.pe.util.Serialize;
import com.pe.util.SystemConfigure;
import com.pe.util.Util;

public class �����ӿǷ���ѵ���� extends AbstractFileOperation implements Operation
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
		PackClassifier cls = PackClassifier.BuildClassifier(classifierName, dataset, testOptions, optionValue);

		/** ����ѵ������������� */
		PackedTrainSet trainSet = new PackedTrainSet();
		trainSet.setName(trainSetName);
		trainSet.setClassifier(classifierName);
		trainSet.setTestOptions(testOptions);
		trainSet.setOptionValue(optionValue);
		trainSet.setCreateTime(Util.showNowTime());
		trainSet.setInstanceNum(cls.getInstanceNum());
		trainSet.setCorrectNum(cls.getCorrectNum());
		trainSet.setIncorrectNum(cls.getIncorrectNum());
		trainSet.setCorrectRate(cls.getCorrectRate());

		List<PackedResultShow> resultShow = new ArrayList<PackedResultShow>();

		for (int i = 0; i < cls.getClassSize(); i++)
		{
			PackedResultShow result = new PackedResultShow();
			result.setClassName(cls.getClassName(i));
			result.setTotalNum(cls.getTruePositiveNum(i) + cls.getFalsePositiveNum(i));
			result.setTrueNum(cls.getTruePositiveNum(i));
			result.setFalseNum(cls.getFalsePositiveNum(i));
			result.setTrueRate(cls.getTruePositiveRate(i));
			result.setFalseRate(cls.getFalsePositiveRate(i));
			result.setPrecision(cls.getPrecision(i));
			resultShow.add(result);
		}
		trainSet.setResultShow(resultShow);
		trainSet.setTree(Util.replace2HTML(cls.getModal()));

		/** ���л��ö��� */
		// ����weka����bug��Evaluation��û�м̳С����л������������Բ��ñ�ķ������л�
		// try
		// {
		// trainSet.setResult(Serialize.serializeData(cls));
		// }
		// catch (NotSerializableException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		PackedResult rst = new PackedResult();
		rst.setClassifier(cls.getClassifier());
		rst.setInstance(cls.getTraining());
		trainSet.setResult(Serialize.serializeData(rst));

		/** ����������ݿ� */
		PackedTrainSetDao trainSetDao = DaoManager.getInstance().getDao(PackedTrainSetDao.class);
		// ���ݿ���û��ͬ���ľ����, ������ɾ�������
		boolean exist = trainSetDao.isExist(trainSetName);
		if (exist) trainSetDao.deleteTrainSetByName(trainSetName);

		trainSetDao.AddTrainSet(trainSet);

		/** ��������ļ� */
		CreatePackedHTML HTML = new CreatePackedHTML(trainSet);
		HTML.create();
	}

	private void validateTrainFile() throws Exception
	{
		if (dataset == null || dataset.equals(""))
		{
			dataset = SystemConfigure.get("DefaultPackedTrainSample");
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
