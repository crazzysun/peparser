package com.pe.operation.PE分析;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.pe.UserException;
import com.pe.dao.DaoManager;
import com.pe.dao.virus.PETrainSetDao;
import com.pe.entity.virus.PETrainSet;
import com.pe.entity.virus.VirusResult;
import com.pe.entity.virus.VirusResultShow;
import com.pe.operation.Operation;
import com.pe.operation.文件.AbstractFileOperation;
import com.pe.util.Serialize;
import com.pe.util.SystemConfigure;
import com.pe.util.Util;
import com.pe.virus.CreatePEHTML;
import com.pe.virus.VirusClassifier;

public class 创建PE恶意软件分析训练集 extends AbstractFileOperation implements Operation
{
	private String classifierName;
	private int classifier;
	private String dataset;
	private int testOptions;
	private int optionValue;
	private PETrainSet trainSet;
	private String trainSetName;
	
	public void execute() throws Exception
	{
		/** 创建训练集 */
		validateClassifierName();
		validateTrainFile();
		VirusClassifier cls = VirusClassifier.BuildClassifier(classifierName, dataset, testOptions, optionValue);
		
		/** 保存训练集的相关属性 */
		trainSet = new PETrainSet();
		trainSet.setName(trainSetName);
		trainSet.setClassifier(classifierName);
		trainSet.setTestOptions(testOptions);
		trainSet.setOptionValue(optionValue);
		
		VirusResult rst = new VirusResult();
		rst.setClassifier(cls.getClassifier());
		rst.setInstance(cls.getTraining());
		
		trainSet.setResult(Serialize.serializeData(rst));

		trainSet.setCreateTime(Util.showNowTime());
		
		trainSet.setInstanceNum(cls.getInstanceNum());
		trainSet.setCorrectNum(cls.getCorrectNum());
		trainSet.setIncorrectNum(cls.getIncorrectNum());
		trainSet.setCorrectRate(cls.getCorrectRate());
		
		List<VirusResultShow> resultShow = new ArrayList<VirusResultShow>();
		
		for (int i = 0; i < cls.getClassSize(); i++)
		{
			VirusResultShow result = new VirusResultShow();
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
		
		/** 结果加入数据库 */
		PETrainSetDao trainSetDao = DaoManager.getInstance().getDao(PETrainSetDao.class);
		boolean exist = trainSetDao.isExist(trainSetName);
		if (exist) trainSetDao.deleteTrainSetByName(trainSetName);

		trainSetDao.AddTrainSet(trainSet);
		
		/** 创建结果文件 */
		CreatePEHTML HTML = new CreatePEHTML(trainSet);
		HTML.create();
	}

	private void validateTrainFile() throws Exception
	{
		if (dataset == null || dataset.equals(""))
		{
			dataset = SystemConfigure.get("DefaultPETrainSample");
		}
		else
		{
			File file = new File(getWorkFile(""), dataset);
			if (!file.isFile())
				throw new UserException("训练集文件选取错误，请重新选择！");
			dataset = file.getAbsolutePath();		
		}
	}

	/** 根据前台的select控件值，获取分类器的值 */
	private void validateClassifierName() throws UserException
	{
		switch(classifier)
		{
		case 1:
			classifierName = "C4.5";
			break;
		case 2:
			classifierName = "BFTree";
			break;
		case 3:
			classifierName = "BayesNet";
			break;
		case 4:
			classifierName = "IBk";
			break;
		default:
			throw new UserException("classifier选择错误，请重新选择！");
		}
	}

	public PETrainSet getTrainSet()
	{
		return trainSet;
	}

	public void setTrainSet(PETrainSet trainSet)
	{
		this.trainSet = trainSet;
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
