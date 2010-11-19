package com.pe.operation.gdlfile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.pe.UserException;
import com.pe.entity.gdl.Edge;
import com.pe.entity.gdl.Node;
import com.pe.operation.Operation;
import com.pe.operation.文件.AbstractFileOperation;

public class 文件关系分析 extends AbstractFileOperation implements Operation
{
	private static final long serialVersionUID = 1L;
	
	private List<Node> nodeList = new ArrayList<Node>();
	private List<Edge> edgeList = new ArrayList<Edge>();
	private String title = "";
	private int nodeCount;
	private int edgeCount;
	
	private String fileName;
	
	public void execute() throws Exception
	{
		try
		{
			File path = getWorkFile("");
			File file = new File(path, fileName);
			
			/** 将节点信息写入文件*/
			String destFileName = "nodes.txt";
			File destFile = new File(path, destFileName);
            FileWriter writer = new FileWriter(destFile); 
            BufferedWriter bw = new BufferedWriter(writer);
			
			/** 如果文件名为空或者null，则抛出异常 */
			if (fileName == null || fileName.equalsIgnoreCase("")) 
				throw new UserException("文件已上传，但前台页面获取的文件名为空！");
			
			FileInputStream fileinputstream = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(fileinputstream, "GB2312");
			BufferedReader br = new BufferedReader(reader);

			String str = "";
			while ((str = br.readLine()) != null)
			{
				if (str.startsWith("title"))
				{
					String ch = "\"";
					title = str.substring(str.indexOf(ch)+1, str.length());
					bw.write("title:" + title);
					bw.newLine();
				}
				if (str.startsWith("node"))
				{
					String nodestr = "node:";
					Node node = new Node();
					String ch = "\"";
					/** 节点Id */
					int startIndex = str.indexOf(ch, 0);
					int endIndex = str.indexOf(ch, startIndex + 1);
					String nodeIndex = str.substring(startIndex + 1, endIndex);

					/** 节点name */
//					int startName = str.indexOf(ch, endIndex + 1);
//					int endName = str.indexOf(ch, startName + 1);
//					String nodeName = str.substring(startName + 1, endName);
					
					node.setNodeId(nodeIndex);
//					node.setNodeLabel(nodeName);
					nodeList.add(node);
					
					bw.write(nodestr + nodeIndex);
					bw.newLine();
				}
				if (str.startsWith("edge"))
				{
					String edgestr = "edge:";
					Edge edge = new Edge();
					
					String ch = "\"";
					/** sourceNode节点Id */
					int startSrc = str.indexOf(ch, 0);
					int endSrc = str.indexOf(ch, startSrc + 1);
					String sourceNode = str.substring(startSrc + 1, endSrc);

					/** tagetNode节点Id */
					int startTrgt = str.indexOf(ch, endSrc + 1);
					int endTrgt = str.indexOf(ch, startTrgt + 1);
					String targetNode = str.substring(startTrgt + 1, endTrgt);
					
					edge.setSource(sourceNode);
					edge.setTarget(targetNode);
					edgeList.add(edge);
					
					bw.write(edgestr + sourceNode + "-" + targetNode);
					bw.newLine();
				}

			}
			
			nodeCount = nodeList.size();
			edgeCount = edgeList.size();
			
			br.close();
			
            bw.close();
            writer.close();
		}
		catch (IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
	}

	public List<Node> getNodeList()
	{
		return nodeList;
	}

	public void setNodeList(List<Node> nodeList)
	{
		this.nodeList = nodeList;
	}

	public List<Edge> getEdgeList()
	{
		return edgeList;
	}

	public void setEdgeList(List<Edge> edgeList)
	{
		this.edgeList = edgeList;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public int getNodeCount()
	{
		return nodeCount;
	}

	public void setNodeCount(int nodeCount)
	{
		this.nodeCount = nodeCount;
	}

	public int getEdgeCount()
	{
		return edgeCount;
	}

	public void setEdgeCount(int edgeCount)
	{
		this.edgeCount = edgeCount;
	}
}
