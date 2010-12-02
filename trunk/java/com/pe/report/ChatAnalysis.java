package com.pe.report;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pe.entity.ChatEntity;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class ChatAnalysis
{
	public static void main(String[] agrs) throws Exception
	{
		String m_TrainingFile = "c:/default_packed_samples.arff";
		Instances m_Training = new Instances(new BufferedReader(new FileReader(m_TrainingFile)));
		m_Training.setClassIndex(m_Training.numAttributes() - 1);
		
		Attribute a = (Attribute) m_Training.enumerateAttributes().nextElement();
		System.out.println(a.numValues());
//		analysize("", m_TrainingFile);
	}

	@SuppressWarnings("unchecked")
	public static ChatEntity analysize(String name, String m_TrainingFile) throws FileNotFoundException, IOException
	{
		/** 结果 */
		List<Double> list1 = new ArrayList<Double>();
		List<Double> list2 = new ArrayList<Double>();
		double min = 0.0;
		double max = 0.0;

		// 直方图横坐标
		List<Double> arrList = new ArrayList<Double>();

		/** 读取文件 */
		// String m_TrainingFile = "c:/default_packed_samples.arff";
		Instances m_Training = new Instances(new BufferedReader(new FileReader(m_TrainingFile)));
		m_Training.setClassIndex(m_Training.numAttributes() - 1);
		
		/** 对要分析的指定的指标值进行排序，以确定指标值的最小最大值区间 */
		Attribute attribute = m_Training.attribute(name);
		// m_Training.sort(attribute);

		/** 遍历每一个样本 */
		Enumeration<Instance> e = m_Training.enumerateInstances();
		double objectValue;
		double classValue;
		while (e.hasMoreElements())
		{
			Instance in = e.nextElement();
			objectValue = in.value(attribute);
			classValue = in.value(m_Training.numAttributes() - 1);

			/** 取得最大最小值 */
			if (objectValue > max) max = objectValue;
			if (objectValue < min) min = objectValue;

			if (classValue == 0.0)
			{
				list1.add(objectValue);
			}
			else
			{
				list2.add(objectValue);
			}
		}
		arrList = getList(min, max);

		ChatEntity result = new ChatEntity();
		List<String> list = new ArrayList<String>();
		Map<String, Integer> map1 = new HashMap<String, Integer>();
		Map<String, Integer> map2 = new HashMap<String, Integer>();
		List<String> classNameList = new ArrayList<String>();
		
		for (int i = 0; i < m_Training.numClasses(); i++)
		{
			classNameList.add(m_Training.classAttribute().value(i));
		}

		/** 处理list */
		for (int i = 0; i < arrList.size() - 1; i++)
		{
			String str;
			if (i == arrList.size() - 2)
				str = "[" + arrList.get(i) + ", " + arrList.get(i + 1) + "]";
			else
				str = "[" + arrList.get(i) + ", " + arrList.get(i + 1) + ")";

			list.add(str);
			map1.put(str, 0);
			map2.put(str, 0);
		}

		/** 处理map1 */
		for (Double d : list1)
		{
			for (int index = 0; index < arrList.size() - 1; index++)
			{
				double left = arrList.get(index);
				double right = arrList.get(index + 1);
				if (d >= left && d < right)
				{
					map1 = mapValuePP(list.get(index), map1);
				}

				// 处理最右边的边界情况
				if (right == arrList.get(arrList.size() - 1))
				{
					if (d == right)
					{
						map1 = mapValuePP(list.get(index), map1);
					}
				}
			}
		}

		/** 处理map2 */
		for (Double d : list2)
		{
			for (int index = 0; index < arrList.size() - 1; index++)
			{
				double left = arrList.get(index);
				double right = arrList.get(index + 1);
				if (d >= left && d < right)
				{
					map2 = mapValuePP(list.get(index), map2);
				}

				// 处理最右边的边界情况
				if (right == arrList.get(arrList.size() - 1))
				{
					if (d == right)
					{
						map2 = mapValuePP(list.get(index), map2);
					}
				}
			}
		}

		result.setList(list);
		result.setMap1(map1);
		result.setMap2(map2);
		result.setClassNameList(classNameList);

		return result;
	}

	/**
	 * 得到直方图横坐标的区间等差数组
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static List<Double> getList(double min, double max)
	{
		double d = (max - min) / 9;
		BigDecimal a = new BigDecimal(d);
		d = a.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		List<Double> list = new ArrayList<Double>();
		for (int i = 0; i < 9; i++)
		{
			double k = mul(i, d);
			list.add(add(min, k));
		}
		list.add(max);
		return list;
	}

	/**
	 * 对map指定的key的计数器++
	 * 
	 * @param key
	 * @param map
	 * @return
	 */
	public static Map<String, Integer> mapValuePP(String key, Map<String, Integer> map)
	{
		int count = map.get(key);
		count++;
		map.put(key, count);
		return map;
	}

	public static double add(double a1, double a2)
	{
		BigDecimal b1 = new BigDecimal(Double.toString(a1));
		BigDecimal b2 = new BigDecimal(Double.toString(a2));
		return b1.add(b2).doubleValue();
	}

	public static double mul(double a1, double a2)
	{
		BigDecimal b1 = new BigDecimal(Double.toString(a1));
		BigDecimal b2 = new BigDecimal(Double.toString(a2));
		return b1.multiply(b2).doubleValue();
	}
}
