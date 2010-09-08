package com.pe.operation.gdlfile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JRootPane;

import org.apache.commons.collections15.functors.ConstantTransformer;

import com.pe.entity.parser.Edge;
import com.pe.entity.parser.Node;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.util.Relaxer;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.ObservableGraph;
import edu.uci.ics.jung.graph.event.GraphEvent;
import edu.uci.ics.jung.graph.event.GraphEventListener;
import edu.uci.ics.jung.graph.util.Graphs;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;

public class DrawGraph extends JApplet
{
	private static final long serialVersionUID = 1L;
	public List<Node> nodeList = new ArrayList<Node>();
	public List<Edge> edgeList = new ArrayList<Edge>();
	public static String title = "";
	private int index = 0;

	private Graph<Number, Number> g = null;
	private VisualizationViewer<Number, Number> vv = null;
	private AbstractLayout<Number, Number> layout = null;
	Timer timer;
	boolean done;
	protected JButton switchLayout;

	public static final int EDGE_LENGTH = 100;

	public DrawGraph()
	{
//		this.nodeList = nodeList;
//		this.edgeList = edgeList;
//		this.title = title;
		
		File file = new File("d:/workJung/jung2/peparser/data/nodes.txt");
		try
		{
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
		catch(IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
		
	}

	public void init()
	{
		// create a graph
		Graph<Number, Number> ig = Graphs.<Number, Number> synchronizedDirectedGraph(new DirectedSparseMultigraph<Number, Number>());

		ObservableGraph<Number, Number> og = new ObservableGraph<Number, Number>(ig);
		og.addGraphEventListener(new GraphEventListener<Number, Number>()
		{

			public void handleGraphEvent(GraphEvent<Number, Number> evt)
			{
				System.err.println("got " + evt);

			}
		});
		this.g = og;
		// create a graphdraw
		layout = new FRLayout2<Number, Number>(g);
		// ((FRLayout)layout).setMaxIterations(200);

		vv = new VisualizationViewer<Number, Number>(layout, new Dimension(600, 600));

		JRootPane rp = this.getRootPane();
		rp.putClientProperty("defeatSystemEventQueueCheck", Boolean.TRUE);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(java.awt.Color.lightGray);
		getContentPane().setFont(new Font("Serif", Font.PLAIN, 12));

		vv.getModel().getRelaxer().setSleepTime(500);
		vv.setGraphMouse(new DefaultModalGraphMouse<Number, Number>());

		vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Number>());
		vv.setForeground(Color.white);
		getContentPane().add(vv);
		switchLayout = new JButton("Switch to SpringLayout");
		switchLayout.addActionListener(new ActionListener()
		{

			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent ae)
			{
				Dimension d = new Dimension(600, 600);
				if (switchLayout.getText().indexOf("Spring") > 0)
				{
					switchLayout.setText("Switch to FRLayout");
					layout = new SpringLayout<Number, Number>(g, new ConstantTransformer(EDGE_LENGTH));
					layout.setSize(d);
					vv.getModel().setGraphLayout(layout, d);
				}
				else
				{
					switchLayout.setText("Switch to SpringLayout");
					layout = new FRLayout<Number, Number>(g, d);
					vv.getModel().setGraphLayout(layout, d);
				}
			}
		});

		getContentPane().add(switchLayout, BorderLayout.SOUTH);

		timer = new Timer();
	}

	@Override
	public void start()
	{
		validate();
		// set timer so applet will change
		timer.schedule(new RemindTask(), 1, 1); // subsequent rate
		vv.repaint();
	}

	Integer v_prev = null;

	public void process()
	{

		try
		{
			if (index < 1)
			{
				layout.lock(true);
				Relaxer relaxer = vv.getModel().getRelaxer();
				relaxer.pause();
				
				for(Node node : nodeList)
				{
					Integer v = new Integer(node.getNodeId());
					g.addVertex(v);
					System.err.println("added node " + v);
				}
				
				int edgeNum = 0;
				for(Edge edge : edgeList)
				{
					g.addEdge(edgeNum++, Integer.parseInt(edge.getSource()), Integer.parseInt(edge.getTarget()));
				}
				layout.initialize();
				relaxer.resume();
				layout.lock(false);
			}
			done = true;
			
		}
		catch (Exception e)
		{
			System.out.println(e);

		}
	}

	class RemindTask extends TimerTask
	{

		@Override
		public void run()
		{
			process();
			if (done) cancel();

		}
	}
}
