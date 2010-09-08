package com.pe.operation.gdlfile;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

//import javax.swing.JApplet;
import javax.swing.JFrame;

import com.pe.entity.gdlfile.Edge;
import com.pe.entity.gdlfile.Node;
import com.pe.operation.Operation;

public class ViewGraph implements Operation
{
	private List<Node> nodeList;
	private List<Edge> edgeList;
	private String title;
	
	public void execute() throws Exception
	{
//		JApplet applet = new DrawGraph(nodeList, edgeList, title);
//    	JFrame frame = new JFrame(title);
//    	setupClosing(frame);
//    	frame.getContentPane().add(applet);
//
//    	applet.init();
//    	applet.start();
//		frame.pack();
//		frame.setVisible(true);
	}

	public void setupClosing(JFrame frame)
	{
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
//				System.exit(0);
			}
		});
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

}
