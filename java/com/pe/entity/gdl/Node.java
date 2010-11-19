package com.pe.entity.gdl;

import java.util.List;

import com.pe.entity.Bean;

public class Node implements Bean
{
	private static final long serialVersionUID = 1L;

	private String nodeId;
	private String nodeLabel;
	private int sytle; 					// �ڵ������ �ӹ��̣��������ⲿ��������δ����
	private boolean origination; 		// û����� true��ʾû�����
	public boolean nondirectOg; 		// �Ƿ�����ͼ��һ�����
	private Node pathlink;				// �ڵ�ǰ·����ָ����һ���ڵ�
	private int passcount;				// ͨ���ýڵ�·��������
	private int nearest;				// ��������������ľ���
	private String nessary;				// �ɳ�����ڵ���ڵ�ؾ��Ľڵ����
	private String access;				// �ڱ���ʱ����һ���ڵ����
	private List<String> linked;		//�͸ýڵ����������нڵ�(�з���
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
