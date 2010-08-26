package com.pe.operation;

import java.util.HashMap;
import java.util.Map;

/**
 * 操作树的节点
 */
public class OperationNode
{
	private String name;
	
	private Map<String, OperationNode> children = new HashMap<String, OperationNode>();

	public OperationNode(String name)
	{
		this.name = name;
	}
	
	public OperationNode getChild(String s)
	{
		return children.get(s);
	}
	
	public void addChild(String s, OperationNode c)
	{
		children.put(s, c);
	}
	
	public String getName()
	{
		return name;
	}

	public Map<String, OperationNode> getChildren()
	{
		return children;
	}
	
	
}
