package com.pe.entity.parser;

import com.pe.entity.Bean;

public class Node implements Bean
{
	private static final long serialVersionUID = 1L;
	
	private String nodeId;
	private String nodeLabel;
	
	public String getNodeId()
	{
		return nodeId;
	}
	public void setNodeId(String nodeId)
	{
		this.nodeId = nodeId;
	}
	public String getNodeLabel()
	{
		return nodeLabel;
	}
	public void setNodeLabel(String nodeLabel)
	{
		this.nodeLabel = nodeLabel;
	}
}
