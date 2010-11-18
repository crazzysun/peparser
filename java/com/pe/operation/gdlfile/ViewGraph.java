package com.pe.operation.gdlfile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.pe.entity.parser.Edge;
import com.pe.entity.parser.Node;
import com.pe.operation.Operation;
import com.pe.operation.文件.AbstractFileOperation;

public class ViewGraph extends AbstractFileOperation implements Operation
{
	private List<Node> nodeList = new ArrayList<Node>();
	private List<Edge> edgeList = new ArrayList<Edge>();
	private String title;
	
	public void execute() throws Exception
	{
		try
		{
			String fileName = "nodes.txt";
			File path = getWorkFile("");
			File file = new File(path, fileName);
			
			FileInputStream fileinputstream = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(fileinputstream, "GB2312");
			BufferedReader br = new BufferedReader(reader);

			String str = "";
			while ((str = br.readLine()) != null)
			{
				if (str.startsWith("title"))
				{
					title = str.substring(6, str.length());
				}
				if (str.startsWith("node"))
				{
					Node node = new Node();
					/** 节点Id */
					String nodeIndex = str.substring(5, str.length());

					node.setNodeId(nodeIndex);
					nodeList.add(node);
				}
				if (str.startsWith("edge"))
				{
					Edge edge = new Edge();
					
					String ch = "-";
					/** sourceNode节点Id */
					String sourceNode = str.substring(5, str.indexOf(ch));

					/** tagetNode节点Id */
					String targetNode = str.substring(str.indexOf(ch) + 1, str.length());
					
					edge.setSource(sourceNode);
					edge.setTarget(targetNode);
					edgeList.add(edge);
				}
			}
			
			br.close();
		}
		catch(Exception ioe)
		{
			System.out.println(ioe.getMessage());
		}
		
		DrawGraph dg = new DrawGraph(nodeList, edgeList, title);
    	JFrame frame = new JFrame();
    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	frame.getContentPane().add(dg);

    	dg.init();
    	dg.start();
    	frame.pack();
    	frame.setVisible(true);
	}
	
}
