package com.pe.operation.gdlfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pe.entity.gdl.Node;
import com.pe.operation.Operation;
import com.pe.operation.文件.AbstractFileOperation;

public class GdlAttribute extends AbstractFileOperation implements Operation
{
	private final int UNDEF = 0;
	private final int PROCEDURE = 1; // 子过程
	private final int FUNCTION = 2; // 内部定义的函数变量
	private final int WIDEFUN = 3; // 外部的函数
	private final int START = 4;
	private final int FUNFORWIDE = 5; // 内部定义的，为了引用外部的函数
	public List<Node> allnodeList = new ArrayList<Node>();
	private boolean[] visited;
	private double sumnodes; // 节点的数量
	private int sumSub; // 地址子过程数量
	private int exterSub; // 外部过程数量
	private int innerSub; // 内部子过程数量
	private int remoteSub; // 用于间接调用外部函数的函数(导入名称）
	private int stSub = 0; // start节点的数量，有可能为0；
	private int[] cGraphs; // 各个子图中节点的数量，index是节点数量，值是子图的个数
	private int[] cNonGraphs;// 各个无向子图中节点的数量
	private int sumGraphs; // 有向子图的总数
	private int sumNondirectGraphs; // 无向图数量
	private int startNodeId = -1; // 入口节点的节点号
	private double graphsVar = 0.00; // 有向子图节点数平均方差
	private double averageNodes = 0.00; // 有向子图的平均度数
	private int extendGraphsNodes;// 从start开始之后所有子图的节点之和（不计重合）
	private int foreGraphsNodes;// 在start节点之前的所有子图的节点和。
	private int startnodes = 0;// start子图的节点数量
	private int maxGraphNodes;// 最大有向图节点数
	private int maxNondirectedGraph;// 最大无向子图节点数。
	private boolean isStartMax = false;// start子图是不是最大的无向图
	private int maxDegree; // 最大度数
	private double averageDegree;// 平均度数
	private double degreeVar; // 度数的绝对平均方差
	private int absolutenodes; // 孤立的节点数量
	private int abSub; // 孤立地址子过程个数
	private int abExterSub; // 孤立外部过程数量
	private int abInnerSub;// 孤立内部过程数量
	private int abRemoteSub; // 孤立间接调用过程数量
	private int finalSub; // 非孤立点的终端节点
	private int finalAdressSub; // 终端地址子过程
	private int finalExterSub; // 终端外部过程
	private int finalInnerSub;
	private int finalRemoteSub;
	private boolean startNonGraph = false;// 在遍历无向图时检查是否start子图
	private int startNondirectNodes;// 无向子图节点数
	private double quotientsumSub;
	private double quotientinnerSub;
	private double quotientremoteSub;
	private double quotientexterSub;
	private double quotientStartNodeId;
	private double quotientStartGraphNodes;
	private double quotientfinalSub;
	private double quotientfinalAddressSub;
	private double quotientfinalInnerSub;
	private double quotientfinalremoteSub;
	private double quotientfinalexterSub;
	private double quotientabNodes;
	private double quotientabAddressSub;
	private double quotientabInnerSub;
	private double quotientabRemoteSub;
	private double quotientabExterSub;

	public File allFile[];

	private String fileName;
	
	private List<String> baseAttrList = new ArrayList<String>();
	private Map<String, String> baseMap = new HashMap<String, String>();
	@Override
	public void execute() throws Exception
	{
		try
		{
			File path = getWorkFile("");
			File file = new File(path, fileName);
			this.allnodeList.clear();
			FileInputStream fileinputstream = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(fileinputstream, "GB2312");
			BufferedReader breader = new BufferedReader(reader);
			try
			{
				buildTable(breader);
			}
			catch (IOException ioe)
			{
				System.out.println(ioe.getMessage());

			}
			if (startNodeId >= 1) quotientStartNodeId = this.sumnodes / startNodeId;
			childgraph(true);
			if (this.startnodes >= 1) quotientStartGraphNodes = this.sumnodes / this.startnodes;
			childgraph(false);
			calAbsolutenodes();
			calDegree();
			calAveDegVar();
			extendStartGraphs();

			if (sumSub != 0) quotientsumSub = this.sumnodes / sumSub;
			if (innerSub != 0) quotientinnerSub = this.sumnodes / innerSub;
			if (remoteSub != 0) quotientremoteSub = this.sumnodes / remoteSub;
			if (exterSub != 0) quotientexterSub = this.sumnodes / exterSub;
			if (this.absolutenodes != 0) quotientabNodes = this.sumnodes / absolutenodes;
			if (quotientabAddressSub != 0) quotientabAddressSub = this.sumnodes / quotientabAddressSub;
			if (abInnerSub != 0) quotientabInnerSub = this.sumnodes / abInnerSub;
			if (abRemoteSub != 0) quotientabRemoteSub = this.sumnodes / abRemoteSub;
			if (abExterSub != 0) quotientabExterSub = this.sumnodes / abExterSub;
			if (this.finalSub != 0) quotientfinalSub = this.sumnodes / finalSub;
			if (quotientfinalAddressSub != 0) quotientfinalAddressSub = this.sumnodes / quotientfinalAddressSub;
			if (finalInnerSub != 0) quotientfinalInnerSub = this.sumnodes / finalInnerSub;
			if (finalRemoteSub != 0) quotientfinalremoteSub = this.sumnodes / finalRemoteSub;
			if (finalExterSub != 0) quotientfinalexterSub = this.sumnodes / finalExterSub;
		}
		catch(IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}

	}

	public void setMaxGraphNodes(int maxGraphNodes)
	{
		this.maxGraphNodes = maxGraphNodes;
	}

	public void createAttributes(String sourcefile) throws Exception
	{
		this.allnodeList.clear();
		File gdlfile = new File(sourcefile);
		FileInputStream fileinputstream = new FileInputStream(gdlfile);
		InputStreamReader reader = new InputStreamReader(fileinputstream, "GB2312");
		BufferedReader breader = new BufferedReader(reader);
		try
		{
			buildTable(breader);
		}
		catch (IOException ioe)
		{
			System.out.println(ioe.getMessage());

		}
		if (startNodeId >= 1) quotientStartNodeId = this.sumnodes / startNodeId;
		childgraph(true);
		if (this.startnodes >= 1) quotientStartGraphNodes = this.sumnodes / this.startnodes;
		childgraph(false);
		calAbsolutenodes();
		calDegree();
		calAveDegVar();
		extendStartGraphs();

		if (sumSub != 0) quotientsumSub = this.sumnodes / sumSub;
		if (innerSub != 0) quotientinnerSub = this.sumnodes / innerSub;
		if (remoteSub != 0) quotientremoteSub = this.sumnodes / remoteSub;
		if (exterSub != 0) quotientexterSub = this.sumnodes / exterSub;
		if (this.absolutenodes != 0) quotientabNodes = this.sumnodes / absolutenodes;
		if (quotientabAddressSub != 0) quotientabAddressSub = this.sumnodes / quotientabAddressSub;
		if (abInnerSub != 0) quotientabInnerSub = this.sumnodes / abInnerSub;
		if (abRemoteSub != 0) quotientabRemoteSub = this.sumnodes / abRemoteSub;
		if (abExterSub != 0) quotientabExterSub = this.sumnodes / abExterSub;
		if (this.finalSub != 0) quotientfinalSub = this.sumnodes / finalSub;
		if (quotientfinalAddressSub != 0) quotientfinalAddressSub = this.sumnodes / quotientfinalAddressSub;
		if (finalInnerSub != 0) quotientfinalInnerSub = this.sumnodes / finalInnerSub;
		if (finalRemoteSub != 0) quotientfinalremoteSub = this.sumnodes / finalRemoteSub;
		if (finalExterSub != 0) quotientfinalexterSub = this.sumnodes / finalExterSub;
	}

	// 根据gdl文件创建邻接表
	public void buildTable(BufferedReader br) throws Exception
	{
		initData();
		int snode = 0;// start 节点的个数
		int enode = 0; // 外部节点的个数
		int inode = 0;// 内部过程节点的个数
		int subnode = 0;// sub过程节点的个数
		int remotenode = 0;// 间接调用节点个数
		try
		{
			String str = "";
			while ((str = br.readLine()) != null)
			{
				if (str.startsWith("node:"))
				{
					int sty = checkStyleofNode(str);

					Node node = new Node();
					node.setLinked(new ArrayList<String>());
					node.setNondirectedlinked(new ArrayList<String>());
					node.setSytle(sty);
					String ch = "\"";
					/** sourceNode节点Id */
					int startSrc = str.indexOf(ch, 0);
					int endSrc = str.indexOf(ch, startSrc + 1);
					String nodeId = str.substring(startSrc + 1, endSrc);
					node.setNodeId(nodeId.trim());//
					this.allnodeList.add(node);
					if (sty == 1) subnode++;
					if (sty == 2) inode++;
					if (sty == 3) enode++;
					if (sty == 4)
					{
						snode++;
						this.startNodeId = Integer.parseInt(nodeId);
					}
					if (sty == 5) remotenode++;

				}
				if (str.startsWith("edge"))
				{
					String ch = "\"";
					/** sourceNode节点Id */
					int startSrc = str.indexOf(ch, 0);
					int endSrc = str.indexOf(ch, startSrc + 1);
					String sourceNodeId = str.substring(startSrc + 1, endSrc);
					/** tagetNode节点Id */
					int startTrgt = str.indexOf(ch, endSrc + 1);
					int endTrgt = str.indexOf(ch, startTrgt + 1);
					String targetNodeId = str.substring(startTrgt + 1, endTrgt);

					Node sourceNode = findNode(sourceNodeId);
					Node targetNode = findNode(targetNodeId);

					sourceNode.getLinked().add(targetNodeId);
					sourceNode.getNondirectedlinked().add(targetNodeId);
					targetNode.getNondirectedlinked().add(sourceNodeId);

					if (!sourceNode.getNodeId().equalsIgnoreCase(targetNode.getNodeId()))
					{
						targetNode.setOrigination(false);
						targetNode.nondirectOg = false; // 还需要进一步合并，在过程childtGraphs()中进行
					}
				}
			}
			this.sumnodes = this.allnodeList.size();
			this.sumSub = subnode;
			this.exterSub = enode;
			this.innerSub = inode;
			this.stSub = snode;
			this.remoteSub = remotenode;
			br.close();

		}
		catch (IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
	}

	int checkStyleofNode(String str)
	{
		if (str.indexOf("color: green") != -1) return START;
		if (str.indexOf("color: 76 textcolor: 73") != -1) return PROCEDURE;
		if (str.indexOf("color: 75 textcolor: 73") != -1) return FUNCTION;
		if (str.indexOf("color: 80 bordercolor: black ") != -1) return WIDEFUN;
		if (str.indexOf("color: 80 textcolor: 73") != -1)
			return FUNFORWIDE;
		else
			return UNDEF;
	}

//	void sumChildGraphs()
//	{
//		// 初始化有向和无向子图的个数
//		int sumChildGraphs = 0; // 有向子图计数
//		int sumNondirection = 0; // 无向子图计数
//		Iterator<Node> it = this.allnodeList.iterator();
//		while (it.hasNext())
//		{
//			Node temp = it.next();
//			if (temp.isOrigination())
//			{
//				sumChildGraphs++;
//			}
//			if (temp.nondirectOg)
//			{
//				sumNondirection++;
//			}
//		}
//		this.sumGraphs = sumChildGraphs;
//		this.sumNondirectGraphs = sumNondirection;
//	}

	// 求各个子图的节点数
	void childgraph(boolean direct)
	{
		// direct为true时，求有向子图，否则求无向子图的节点数
		int maxGp = 0;
		int maxNonGp = 0;
		int sumChildGraphs = 0; // 有向子图计数
		int sumNondirection = 0; // 无向子图计数
		int nodesum = this.allnodeList.size();
		visited = new boolean[nodesum];
		cGraphs = new int[nodesum + 1];
		cNonGraphs = new int[nodesum + 1];

		for (int loop = 0; loop < nodesum; loop++)
		{
			if (direct)
				cGraphs[loop] = 0;
			else
				cNonGraphs[loop] = 0;
		}

		Iterator<Node> it = this.allnodeList.iterator();
		while (it.hasNext())
		{
			Node tnode = it.next();

			if (direct)
			{
				// 有向子图
				if (!tnode.isOrigination()) continue;
				sumChildGraphs++;
				for (int loop = 0; loop < nodesum; loop++)
				{
					visited[loop] = false;
				}
				simpleTrave(tnode, direct);
				int traveNumber = 0;
				for (int loop = 0; loop < nodesum; loop++)
				{
					if (visited[loop]) traveNumber++;
				}
				cGraphs[traveNumber]++;
				if (tnode.getSytle() == START)
				{
					this.startnodes = traveNumber;
				}
			}
			else
			{
				if (!tnode.nondirectOg) continue;
				sumNondirection++; // 无向子图计数加1
				for (int loop = 0; loop < nodesum; loop++)
				{
					visited[loop] = false;
				}
				if (tnode.getSytle() == START) this.startNonGraph = true;
				simpleTrave(tnode, direct);
				int traveNumber = 0;
				for (int loop = 0; loop < nodesum; loop++)
				{
					if (visited[loop]) traveNumber++;
				}
				this.cNonGraphs[traveNumber]++;
				if (this.startNonGraph)
				{
					this.startNondirectNodes = traveNumber;
					this.startNonGraph = false;
				}
			}
		}

		if (direct)
		{
			this.absolutenodes = cGraphs[1];
			for (int loop = 0; loop < this.sumnodes; loop++)
			{
				if (cGraphs[loop] > 0) maxGp = loop;
			}
			this.maxGraphNodes = maxGp;
			this.sumGraphs = sumChildGraphs;
			double total = 0.00;
			double average = 0.00;
			double result = 0.00;
			double averagetotal = 0;
			for (int loop = 0; loop < this.cGraphs.length; loop++)
			{
				total = total + loop * this.cGraphs[loop];
			}
			average = (this.sumGraphs == 0) ? 0.00 : total / this.sumGraphs;
			this.averageNodes = average;
			int cGraphsCount = 0;// 子图类型（按节点数分）
			for (int loop = 0; loop < this.cGraphs.length; loop++)
			{ // 求平均方差
				if (cGraphs[loop] > 0)
				{
					cGraphsCount++;
					result = Math.abs(loop - average); // 不考虑子图个数
					averagetotal = averagetotal + result;
				}
			}
			result = (cGraphsCount == 0) ? 0.00 : averagetotal / cGraphsCount;
			this.graphsVar = result;
		}
		else
		{
			for (int loop = 0; loop < this.sumnodes; loop++)
			{
				if (0 < cNonGraphs[loop]) maxNonGp = loop;
			}
			this.maxNondirectedGraph = maxNonGp;
			this.sumNondirectGraphs = sumNondirection;
		}
	}

	void simpleTrave(Node start, boolean direct)
	{
		// 有向或无向遍历 true表示有向
		int npos = new Integer(start.getNodeId()).intValue();
		visited[npos] = true;
		Iterator<String> it;
		if (direct == true)
		{
			it = start.getLinked().iterator();
			while (it.hasNext())
			{
				String tempnodestr = it.next();
				int tempID = new Integer(tempnodestr).intValue();
				Node next = findNode(tempnodestr);
				next.nondirectOg = false;
				if (!visited[tempID])
				{
					simpleTrave(next, direct);
				}
			}
		}
		else
		{
			it = start.getNondirectedlinked().iterator();
			while (it.hasNext())
			{
				String tempnodestr = it.next();
				int tempID = new Integer(tempnodestr).intValue();
				Node next = findNode(tempnodestr);
				if (next.getSytle() == START) this.startNonGraph = true;
				next.nondirectOg = false;
				if (!visited[tempID])
				{
					simpleTrave(next, direct);
				}
			}
		}
	}

	private Node findNode(String id)
	{
		Iterator<Node> it = this.allnodeList.iterator();
		int index = 0;

		while (it.hasNext())
		{
			String nodeID = it.next().getNodeId();
			if (nodeID.equalsIgnoreCase(id)) { return this.allnodeList.get(index); }
			index++;
		}
		return null;
	}

//	private int findNodebyID(String id)
//	{
//		Iterator<Node> it = this.allnodeList.iterator();
//		int index = 0;
//
//		while (it.hasNext())
//		{
//			String nodeID = it.next().getNodeId();
//			if (nodeID.equalsIgnoreCase(id)) { return index; }
//			index++;
//		}
//		return -1;
//	}

	// 求start扩展子图（在start节点之后的所有节点为初始点的有向子图集合,返回节点个数
	private void extendStartGraphs()
	{
		if (this.startNodeId == -1) return;
		boolean findnode = false;
		int achor = 0;
		int traved = 0;
		int tempforenodes = 0;
		Node startNode = new Node();
		for (int loop = 0; loop < this.sumnodes; loop++)
		{
			visited[loop] = false;
		}
		for (; achor < this.sumnodes; achor++)
		{
			startNode = this.allnodeList.get(achor);
			if (startNode.getSytle() == START)
			{
				findnode = true;
				// 先计算foreGraphsNodes,先临时保存
				for (int loop = 0; loop < this.sumnodes; loop++)
				{
					if (visited[loop]) traved++;
				}
				tempforenodes = traved;
				traved = 0;
				for (int loop = 0; loop < this.sumnodes; loop++)
				{
					visited[loop] = false;
				}
			}
			if (!startNode.isOrigination())
				continue;
			else
				simpleTrave(startNode, true);

		}
		if (findnode)
		{
			for (int loop = 0; loop < this.sumnodes; loop++)
			{
				if (visited[loop]) traved++;
			}
			this.extendGraphsNodes = traved;
			this.foreGraphsNodes = tempforenodes;

		}

	}

	// 最大有向和无向子图的节点数
	// 求最大度数，包含出度和入度之和.以及评价度数
	private void calDegree()
	{
		int maxNond = 0;
		double sum = 0;
		double average = 0;
		Iterator<Node> it = this.allnodeList.iterator();

		while (it.hasNext())
		{
			Node loopNode = new Node();
			loopNode = it.next();
			sum = sum + loopNode.getNondirectedlinked().size();
			if (maxNond < loopNode.getNondirectedlinked().size()) maxNond = loopNode.getNondirectedlinked().size();
		}
		this.maxDegree = maxNond;
		average = sum / this.sumnodes;
		this.averageDegree = average;
	}

	// 求孤立节点的类型
	private void calAbsolutenodes()
	{
		int tempSub = 0;
		int tempExSub = 0;
		int tempInnSub = 0;
		int tempStSub = 0;
		int tempRemSub = 0;
		int tempFinalSub = 0;
		int tempFnSub = 0;
		int tempFnExSub = 0;
		int tempFnInnSub = 0;
		int tempFnStSub = 0;
		int tempFnRemSub = 0;

		Iterator<Node> it = this.allnodeList.iterator();
		Node loopNode = new Node();
		while (it.hasNext())
		{
			loopNode = it.next();
			if (loopNode.getNondirectedlinked().size() == 0)
			{
				if (loopNode.getSytle() == PROCEDURE) tempSub++;
				if (loopNode.getSytle() == FUNCTION) tempInnSub++;
				if (loopNode.getSytle() == WIDEFUN) tempExSub++;
				if (loopNode.getSytle() == START) tempStSub++;
				if (loopNode.getSytle() == FUNFORWIDE) tempRemSub++;

			}
			else
			{
				if (loopNode.getLinked().size() == 0)
				{
					// 出度为0的非孤立点 就是终点
					tempFinalSub++;
					if (loopNode.getSytle() == PROCEDURE) tempFnSub++;
					if (loopNode.getSytle() == FUNCTION) tempFnInnSub++;
					if (loopNode.getSytle() == WIDEFUN) tempFnExSub++;
					if (loopNode.getSytle() == START) tempFnStSub++;
					if (loopNode.getSytle() == FUNFORWIDE) tempFnRemSub++;

				}
			}

		}

		this.abSub = tempSub; // 孤立地址子过程个数
		this.abExterSub = tempExSub; // 孤立外部过程数量
		this.abInnerSub = tempInnSub;// 孤立内部过程数量
		this.abRemoteSub = tempRemSub; // 孤立间接调用过程数量

		this.finalSub = tempFinalSub;
		this.finalAdressSub = tempFnSub;
		this.finalExterSub = tempFnExSub;
		this.finalInnerSub = tempFnInnSub;
		this.finalRemoteSub = tempFnRemSub;
	}

	// 求度平均方差
	private void calAveDegVar()
	{
		if (this.averageDegree == 0) calDegree();
		double asum = 0;
		Iterator<Node> it = this.allnodeList.iterator();
		Node loopNode = new Node();
		while (it.hasNext())
		{
			loopNode = it.next();
			asum = asum + Math.abs(loopNode.getNondirectedlinked().size() - this.averageDegree);
		}
		this.degreeVar = asum / this.sumnodes;
	}

//	private void startMax()
//	{
//		if (this.startnodes == this.maxGraphNodes)
//			this.isStartMax = true;
//		else
//			this.isStartMax = false;
//	}

	// 初始化属性
	private void initData()
	{
		allnodeList.clear();

		sumnodes = 0;
		sumSub = 0;
		exterSub = 0;
		innerSub = 0;
		remoteSub = 0;
		stSub = 0;
		sumGraphs = 0;
		sumNondirectGraphs = 0;
		startNodeId = -1;
		graphsVar = 0.00;
		averageNodes = 0.00;
		extendGraphsNodes = 0;
		foreGraphsNodes = 0;
		startnodes = 0;
		maxGraphNodes = 0;
		maxNondirectedGraph = 0;
		isStartMax = false;

		maxDegree = 0;
		averageDegree = 0;
		degreeVar = 0;

		absolutenodes = 0;
		abSub = 0;
		abExterSub = 0;
		abInnerSub = 0;
		abRemoteSub = 0;

		finalSub = 0;
		finalAdressSub = 0;
		finalExterSub = 0;
		finalInnerSub = 0;
		finalRemoteSub = 0;
		startNonGraph = false;
		startNondirectNodes = 0;
	}
	/*---------------------------------------------*/
	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public boolean[] getVisited()
	{
		return visited;
	}

	public void setVisited(boolean[] visited)
	{
		this.visited = visited;
	}

	public double getSumnodes()
	{
		return sumnodes;
	}

	public void setSumnodes(double sumnodes)
	{
		this.sumnodes = sumnodes;
	}

	public int getSumSub()
	{
		return sumSub;
	}

	public void setSumSub(int sumSub)
	{
		this.sumSub = sumSub;
	}

	public int getExterSub()
	{
		return exterSub;
	}

	public void setExterSub(int exterSub)
	{
		this.exterSub = exterSub;
	}

	public int getInnerSub()
	{
		return innerSub;
	}

	public void setInnerSub(int innerSub)
	{
		this.innerSub = innerSub;
	}

	public int getRemoteSub()
	{
		return remoteSub;
	}

	public void setRemoteSub(int remoteSub)
	{
		this.remoteSub = remoteSub;
	}

	public int getStSub()
	{
		return stSub;
	}

	public void setStSub(int stSub)
	{
		this.stSub = stSub;
	}

	public int[] getCGraphs()
	{
		return cGraphs;
	}

	public void setCGraphs(int[] graphs)
	{
		cGraphs = graphs;
	}

	public int[] getCNonGraphs()
	{
		return cNonGraphs;
	}

	public void setCNonGraphs(int[] nonGraphs)
	{
		cNonGraphs = nonGraphs;
	}

	public int getSumGraphs()
	{
		return sumGraphs;
	}

	public void setSumGraphs(int sumGraphs)
	{
		this.sumGraphs = sumGraphs;
	}

	public int getSumNondirectGraphs()
	{
		return sumNondirectGraphs;
	}

	public void setSumNondirectGraphs(int sumNondirectGraphs)
	{
		this.sumNondirectGraphs = sumNondirectGraphs;
	}

	public int getStartNodeId()
	{
		return startNodeId;
	}

	public void setStartNodeId(int startNodeId)
	{
		this.startNodeId = startNodeId;
	}

	public double getAverageNodes()
	{
		return averageNodes;
	}

	public void setAverageNodes(double averageNodes)
	{
		this.averageNodes = averageNodes;
	}

	public int getExtendGraphsNodes()
	{
		return extendGraphsNodes;
	}

	public void setExtendGraphsNodes(int extendGraphsNodes)
	{
		this.extendGraphsNodes = extendGraphsNodes;
	}

	public int getForeGraphsNodes()
	{
		return foreGraphsNodes;
	}

	public void setForeGraphsNodes(int foreGraphsNodes)
	{
		this.foreGraphsNodes = foreGraphsNodes;
	}

	public int getStartnodes()
	{
		return startnodes;
	}

	public void setStartnodes(int startnodes)
	{
		this.startnodes = startnodes;
	}

	public int getMaxNondirectedGraph()
	{
		return maxNondirectedGraph;
	}

	public void setMaxNondirectedGraph(int maxNondirectedGraph)
	{
		this.maxNondirectedGraph = maxNondirectedGraph;
	}

	public boolean isStartMax()
	{
		return isStartMax;
	}

	public void setStartMax(boolean isStartMax)
	{
		this.isStartMax = isStartMax;
	}

	public int getMaxDegree()
	{
		return maxDegree;
	}

	public void setMaxDegree(int maxDegree)
	{
		this.maxDegree = maxDegree;
	}

	public double getAverageDegree()
	{
		return averageDegree;
	}

	public void setAverageDegree(double averageDegree)
	{
		this.averageDegree = averageDegree;
	}

	public double getDegreeVar()
	{
		return degreeVar;
	}

	public void setDegreeVar(double degreeVar)
	{
		this.degreeVar = degreeVar;
	}

	public int getAbsolutenodes()
	{
		return absolutenodes;
	}

	public void setAbsolutenodes(int absolutenodes)
	{
		this.absolutenodes = absolutenodes;
	}

	public int getAbSub()
	{
		return abSub;
	}

	public void setAbSub(int abSub)
	{
		this.abSub = abSub;
	}

	public int getAbExterSub()
	{
		return abExterSub;
	}

	public void setAbExterSub(int abExterSub)
	{
		this.abExterSub = abExterSub;
	}

	public int getAbInnerSub()
	{
		return abInnerSub;
	}

	public void setAbInnerSub(int abInnerSub)
	{
		this.abInnerSub = abInnerSub;
	}

	public int getAbRemoteSub()
	{
		return abRemoteSub;
	}

	public void setAbRemoteSub(int abRemoteSub)
	{
		this.abRemoteSub = abRemoteSub;
	}

	public int getFinalSub()
	{
		return finalSub;
	}

	public void setFinalSub(int finalSub)
	{
		this.finalSub = finalSub;
	}

	public int getFinalAdressSub()
	{
		return finalAdressSub;
	}

	public void setFinalAdressSub(int finalAdressSub)
	{
		this.finalAdressSub = finalAdressSub;
	}

	public int getFinalExterSub()
	{
		return finalExterSub;
	}

	public void setFinalExterSub(int finalExterSub)
	{
		this.finalExterSub = finalExterSub;
	}

	public int getFinalInnerSub()
	{
		return finalInnerSub;
	}

	public void setFinalInnerSub(int finalInnerSub)
	{
		this.finalInnerSub = finalInnerSub;
	}

	public int getFinalRemoteSub()
	{
		return finalRemoteSub;
	}

	public void setFinalRemoteSub(int finalRemoteSub)
	{
		this.finalRemoteSub = finalRemoteSub;
	}

	public boolean isStartNonGraph()
	{
		return startNonGraph;
	}

	public void setStartNonGraph(boolean startNonGraph)
	{
		this.startNonGraph = startNonGraph;
	}

	public int getStartNondirectNodes()
	{
		return startNondirectNodes;
	}

	public void setStartNondirectNodes(int startNondirectNodes)
	{
		this.startNondirectNodes = startNondirectNodes;
	}

	public double getQuotientsumSub()
	{
		return quotientsumSub;
	}

	public void setQuotientsumSub(double quotientsumSub)
	{
		this.quotientsumSub = quotientsumSub;
	}

	public double getQuotientinnerSub()
	{
		return quotientinnerSub;
	}

	public void setQuotientinnerSub(double quotientinnerSub)
	{
		this.quotientinnerSub = quotientinnerSub;
	}

	public double getQuotientremoteSub()
	{
		return quotientremoteSub;
	}

	public void setQuotientremoteSub(double quotientremoteSub)
	{
		this.quotientremoteSub = quotientremoteSub;
	}

	public double getQuotientexterSub()
	{
		return quotientexterSub;
	}

	public void setQuotientexterSub(double quotientexterSub)
	{
		this.quotientexterSub = quotientexterSub;
	}

	public double getQuotientStartNodeId()
	{
		return quotientStartNodeId;
	}

	public void setQuotientStartNodeId(double quotientStartNodeId)
	{
		this.quotientStartNodeId = quotientStartNodeId;
	}

	public double getQuotientStartGraphNodes()
	{
		return quotientStartGraphNodes;
	}

	public void setQuotientStartGraphNodes(double quotientStartGraphNodes)
	{
		this.quotientStartGraphNodes = quotientStartGraphNodes;
	}

	public double getQuotientfinalSub()
	{
		return quotientfinalSub;
	}

	public void setQuotientfinalSub(double quotientfinalSub)
	{
		this.quotientfinalSub = quotientfinalSub;
	}

	public double getQuotientfinalAddressSub()
	{
		return quotientfinalAddressSub;
	}

	public void setQuotientfinalAddressSub(double quotientfinalAddressSub)
	{
		this.quotientfinalAddressSub = quotientfinalAddressSub;
	}

	public double getQuotientfinalInnerSub()
	{
		return quotientfinalInnerSub;
	}

	public void setQuotientfinalInnerSub(double quotientfinalInnerSub)
	{
		this.quotientfinalInnerSub = quotientfinalInnerSub;
	}

	public double getQuotientfinalremoteSub()
	{
		return quotientfinalremoteSub;
	}

	public void setQuotientfinalremoteSub(double quotientfinalremoteSub)
	{
		this.quotientfinalremoteSub = quotientfinalremoteSub;
	}

	public double getQuotientfinalexterSub()
	{
		return quotientfinalexterSub;
	}

	public void setQuotientfinalexterSub(double quotientfinalexterSub)
	{
		this.quotientfinalexterSub = quotientfinalexterSub;
	}

	public double getQuotientabNodes()
	{
		return quotientabNodes;
	}

	public void setQuotientabNodes(double quotientabNodes)
	{
		this.quotientabNodes = quotientabNodes;
	}

	public double getQuotientabAddressSub()
	{
		return quotientabAddressSub;
	}

	public void setQuotientabAddressSub(double quotientabAddressSub)
	{
		this.quotientabAddressSub = quotientabAddressSub;
	}

	public double getQuotientabInnerSub()
	{
		return quotientabInnerSub;
	}

	public void setQuotientabInnerSub(double quotientabInnerSub)
	{
		this.quotientabInnerSub = quotientabInnerSub;
	}

	public double getQuotientabRemoteSub()
	{
		return quotientabRemoteSub;
	}

	public void setQuotientabRemoteSub(double quotientabRemoteSub)
	{
		this.quotientabRemoteSub = quotientabRemoteSub;
	}

	public double getQuotientabExterSub()
	{
		return quotientabExterSub;
	}

	public void setQuotientabExterSub(double quotientabExterSub)
	{
		this.quotientabExterSub = quotientabExterSub;
	}

	public List<String> getBaseAttrList()
	{
		return baseAttrList;
	}

	public void setBaseAttrList(List<String> baseAttrList)
	{
		this.baseAttrList = baseAttrList;
	}

	public Map<String, String> getBaseMap()
	{
		return baseMap;
	}

	public void setBaseMap(Map<String, String> baseMap)
	{
		this.baseMap = baseMap;
	}

	public int getMaxGraphNodes()
	{
		return maxGraphNodes;
	}

	public double getGraphsVar()
	{
		return graphsVar;
	}

	public void setGraphsVar(double graphsVar)
	{
		this.graphsVar = graphsVar;
	}

}
