package com.pe.entity.gdl;



import com.pe.entity.Bean;

/**
 * ���ڱ���ӿ�ѵ��������Ҫָ��
 * 
 * @author FangZhiyang
 *
 */
public class GdlResultShow implements Bean
{
	private static final long serialVersionUID = -7384522680087612661L;

	private String className;			//��������
	private int totalNum;				//������������
	private int trueNum;				//�ж���ȷ��Ŀ
	private int falseNum;				//�жϴ�����Ŀ
	private String precision;				//��ȷ��
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
}
