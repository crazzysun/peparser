package com.pe.operation;

import java.util.ArrayList;
import java.util.List;


/**
 * ������
 */
public class OperationTree
{
	private OperationNode root = new OperationNode(null);
	
	/** ��������ӵ����� */
	public void add(String name)
	{
		String[] s = name.split("[.]");
		
		OperationNode n = root;
		for (int i = 0; i < s.length; i++)
		{
			OperationNode c = n.getChild(s[i]);
			if (c == null)
			{
				if (n.getName() == null)
					c = new OperationNode(s[i]);
				else
					c = new OperationNode(n.getName() + "." + s[i]);
				
				n.addChild(s[i], c);
			}
			
			n = c;
		}
	}
	
	/** ��ȡָ�������ڵ� */
	public OperationNode get(String name)
	{
		String[] s = name.split("[.]");
		
		OperationNode n = root;
		for (int i = 0; i < s.length; i++)
		{
			OperationNode c = n.getChild(s[i]);
			if (c == null) return null;
			
			n = c;
		}
		
		return n;
	}
	
	/** ��ȡָ���ڵ������������������� */
	public List<String> getAllChildren(String name)
	{
		OperationNode n = get(name);
		if (n == null) return null;
		
		List<String> list = new ArrayList<String>();
		getAllChildren1(n, list);
		
		return list;
	}

	private void getAllChildren1(OperationNode n, List<String> list)
	{
		list.add(n.getName());
		
		for (OperationNode c : n.getChildren().values())
		{
			getAllChildren1(c, list);
		}
	}
}
