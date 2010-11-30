package com.pe.util;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class Main
{
	@SuppressWarnings("unchecked")
	public static void main(String[] agrs) throws Exception
	{
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		
		List<Integer> list2 = new ArrayList<Integer>();
		list2 = list;
		System.out.println(list.toString());
		System.out.println(list2.toString());
		change(list2);
		System.out.println(list.toString());
		System.out.println(list2.toString());
	}
	
	public static void change(List<Integer> list)
	{
		list.add(4);
	}
	
	@SuppressWarnings("unchecked")
	public static ChatEntity analysize(String name) throws FileNotFoundException, IOException
	{
		/** ��� */
		List<Double> list1 = new ArrayList<Double>();
		List<Double> list2 = new ArrayList<Double>();
		double min = 0.0;
		double max = 0.0;
		
		//ֱ��ͼ������
		List<Double> arrList = new ArrayList<Double>();
		
		
		/** ��ȡ�ļ� */
		String m_TrainingFile = "c:/default_packed_samples.arff";
		Instances m_Training = new Instances(new BufferedReader(new FileReader(m_TrainingFile)));
		m_Training.setClassIndex(m_Training.numAttributes() - 1);

		/** ��Ҫ������ָ����ָ��ֵ����������ȷ��ָ��ֵ����С���ֵ���� */
		Attribute attribute = m_Training.attribute(name);
//		m_Training.sort(attribute);

		/** ����ÿһ������ */
		Enumeration<Instance> e = m_Training.enumerateInstances();
		double objectValue;
		double classValue;
		while (e.hasMoreElements())
		{
			Instance in = e.nextElement();
			objectValue = in.value(attribute);
			classValue = in.value(m_Training.numAttributes() - 1);
			
			/** ȡ�������Сֵ */
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
	
		/** ����list */
		for (int i = 0; i < arrList.size() - 1; i++)
		{
			String str;
			if (i == arrList.size() - 2)
				str = "[" + arrList.get(i) + ", " + arrList.get(i + 1) + "]";
			else
				str = "[" + arrList.get(i) + ", " + arrList.get(i + 1) + ")";
			
			list.add(str);
		}
		result.setList(list);
		
		/** ����map1 */
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int count4 = 0;
		int count5 = 0;
		int count6 = 0;
		int count7 = 0;
		int count8 = 0;
		int count9 = 0;
		
		for (int index = 0; index < arrList.size() - 1; index++)
		{
			double left = arrList.get(index);
			double right = arrList.get(index + 1);
			int count = 0;
			for (Double d : list1)
			{
				if(d >= left && d < right)
				{
					count++;
				}
				
				//�������һ��
				if (right == arrList.get(arrList.size() - 1))
				{
					if (d == right)
					{
						count++;
					}
				}
			}
		}
		
		
//		for (Double d : list1)
//		{
//			int index = 0;
//			if (d >= arrList.get(index) && d < arrList.get(index + 1))
//			{
//				count1++;
//			}
//			if (d >= arrList.get(index + 1) && d < arrList.get(index + 2))
//			{
//				count2++;
//			}
//			if (d >= arrList.get(index + 2) && d < arrList.get(index + 3))
//			{
//				count3++;
//			}
//			if (d >= arrList.get(index + 3) && d < arrList.get(index + 4))
//			{
//				count4++;	
//			}
//			if (d >= arrList.get(index + 4) && d < arrList.get(index + 5))
//			{
//				count5++;
//			}
//			if (d >= arrList.get(index + 5) && d < arrList.get(index + 6))
//			{
//				count6++;
//			}
//			if (d >= arrList.get(index + 6) && d < arrList.get(index + 7))
//			{
//				count7++;
//			}
//			if (d >= arrList.get(index + 7) && d < arrList.get(index + 8))
//			{
//				count8++;	
//			}
//			if (d >= arrList.get(index + 8) && d <= arrList.get(index + 9))
//			{
//				count9++;	
//			}
//		}
//		
//		map1.put(list.get(0), count1);
		
		
		/** ����map2 */
		
		return null;
	}
	
	public static List<Double> getList(double min, double max)
	{
		double d = (max - min)/9;
		List<Double> list = new ArrayList<Double>();
		for (int i = 0; i < 10; i++)
		{
			list.add(max + i*d);
		}
		return list;
	}
}