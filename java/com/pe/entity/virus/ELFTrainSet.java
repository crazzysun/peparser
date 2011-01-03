package com.pe.entity.virus;

import java.util.List;

import com.pe.entity.Bean;

/**
 * 判断加壳的训练集
 * 
 * @author FangZhiyang
 */
public class ELFTrainSet implements Bean
{
	private static final long serialVersionUID = -2123961060698504484L;
	
	private long id;
	private String name;			//训练集名称（默认为训练样本文件名）
	private String classifier;		//分类器
	private int testOptions;
	private int optionValue;
	private byte[] result;			//训练集结果
	private String createTime;		//创建时间
	
	/** 用于前台显示的字段，以下数据都可通过result对象得到 */
	private int instanceNum;		//样本总数
	private int correctNum;			//判断正确的数量
	private int incorrectNum;		//判断错误的数量
	private String correctRate;		//正确率
	private String tree;			//训练集生成树
	private List<VirusResultShow> resultShow;
	
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getClassifier()
	{
		return classifier;
	}
	public void setClassifier(String classifier)
	{
		this.classifier = classifier;
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
	public byte[] getResult()
	{
		return result;
	}
	public void setResult(byte[] result)
	{
		this.result = result;
	}
	public String getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
	public int getInstanceNum()
	{
		return instanceNum;
	}
	public void setInstanceNum(int instanceNum)
	{
		this.instanceNum = instanceNum;
	}
	public int getCorrectNum()
	{
		return correctNum;
	}
	public void setCorrectNum(int correctNum)
	{
		this.correctNum = correctNum;
	}
	public int getIncorrectNum()
	{
		return incorrectNum;
	}
	public void setIncorrectNum(int incorrectNum)
	{
		this.incorrectNum = incorrectNum;
	}
	public String getCorrectRate()
	{
		return correctRate;
	}
	public void setCorrectRate(String correctRate)
	{
		this.correctRate = correctRate;
	}
	public String getTree()
	{
		return tree;
	}
	public void setTree(String tree)
	{
		this.tree = tree;
	}
	public List<VirusResultShow> getResultShow()
	{
		return resultShow;
	}
	public void setResultShow(List<VirusResultShow> resultShow)
	{
		this.resultShow = resultShow;
	}	
}
