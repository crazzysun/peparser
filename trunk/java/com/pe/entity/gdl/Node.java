package com.pe.entity.gdl;

import java.util.List;

import com.pe.entity.Bean;

public class Node implements Bean
{
	private static final long serialVersionUID = 1L;

	private String nodeId;
	private String nodeLabel;
	private int sytle; 					// 节点的类型 子过程，函数，外部函数，或未定义
	private boolean origination; 		// 没有入度 true表示没有入度
	public boolean nondirectOg; 		// 是否无向图的一个起点
	private Node pathlink;				// 在当前路径上指向上一个节点
	private int passcount;				// 通过该节点路径的条数
	private int nearest;				// 距离程序入口最近的距离
	private String nessary;				// 由程序入口到达节点必经的节点序号
	private String access;				// 在遍历时被哪一个节点访问
	private List<String> linked;		//和该节点相连的所有节点(有方向）
	private List<String> nondirectedlinked;

	
	public List<String> getLinked()
	{
		return linked;
	}

	public void setLinked(List<String> linked)
	{
		this.linked = linked;
	}

	public List<String> getNondirectedlinked()
	{
		return nondirectedlinked;
	}

	public void setNondirectedlinked(List<String> nondirectedlinked)
	{
		this.nondirectedlinked = nondirectedlinked;
	}

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

	public int getSytle()
	{
		return sytle;
	}

	public void setSytle(int sytle)
	{
		this.sytle = sytle;
	}

	public boolean isOrigination()
	{
		return origination;
	}

	public void setOrigination(boolean origination)
	{
		this.origination = origination;
	}

	public boolean isNondirectOg()
	{
		return nondirectOg;
	}

	public void setNondirectOg(boolean nondirectOg)
	{
		this.nondirectOg = nondirectOg;
	}

	public Node getPathlink()
	{
		return pathlink;
	}

	public void setPathlink(Node pathlink)
	{
		this.pathlink = pathlink;
	}

	public int getPasscount()
	{
		return passcount;
	}

	public void setPasscount(int passcount)
	{
		this.passcount = passcount;
	}

	public int getNearest()
	{
		return nearest;
	}

	public void setNearest(int nearest)
	{
		this.nearest = nearest;
	}

	public String getNessary()
	{
		return nessary;
	}

	public void setNessary(String nessary)
	{
		this.nessary = nessary;
	}

	public String getAccess()
	{
		return access;
	}

	public void setAccess(String access)
	{
		this.access = access;
	}

//	/*----------------------------------------*/
//	public Node(String n, int r, Node last)
//	{
//		this.nodeId = n;
//		this.passcount = r;
//		this.pathlink = last;
//	}
//
//	public Node()
//	{
//		this.setNodeId("0");
//		this.setOrigination(true);
//		this.origination = true;
//	}
}
