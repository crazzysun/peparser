package com.pe.entity.packed;

import com.pe.entity.Bean;

/**
 * 用于保存加壳训练集的主要指标
 * 
 * @author FangZhiyang
 *
 */
public class PackedResultShow implements Bean
{
	private static final long serialVersionUID = -7384522680087612661L;

	private String className;			//分类名称
	private int totalNum;				//该类样本总数
	private int trueNum;				//判断正确数目
	private int falseNum;				//判断错误数目
	private String trueRate;				//判断正确比率
	private String falseRate;				//判断错误比率
	private String precision;			//正确率
	public String getClassName()
	{
		return className;
	}
	public void setClassName(String className)
	{
		this.className = className;
	}
	public int getTotalNum()
	{
		return totalNum;
	}
	public void setTotalNum(int totalNum)
	{
		this.totalNum = totalNum;
	}
	public int getTrueNum()
	{
		return trueNum;
	}
	public void setTrueNum(int trueNum)
	{
		this.trueNum = trueNum;
	}
	public int getFalseNum()
	{
		return falseNum;
	}
	public void setFalseNum(int falseNum)
	{
		this.falseNum = falseNum;
	}
	public String getPrecision()
	{
		return precision;
	}
	public void setPrecision(String precision)
	{
		this.precision = precision;
	}
	public String getTrueRate()
	{
		return trueRate;
	}
	public void setTrueRate(String trueRate)
	{
		this.trueRate = trueRate;
	}
	public String getFalseRate()
	{
		return falseRate;
	}
	public void setFalseRate(String falseRate)
	{
		this.falseRate = falseRate;
	}
}
