import java.util.*;

public class FordFulkerson {

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph)
	{
		ArrayList<Integer> path = new ArrayList<Integer>();
		/* YOUR CODE GOES HERE */
		//ArrayList<Integer> queue = new ArrayList<Integer>();

		boolean[] visited = new boolean[graph.getNbNodes()];
		for (int i = 0; i < graph.getNbNodes(); i++)
		{
			visited[i]=false;
		}

		if (source.equals(destination))
		{
			path.add(source);
			return path;
		}

		traverse(source,destination,graph,visited,path);

		if (path.get(path.size() - 1).equals(destination))
		{
			//System.out.println("222");
			System.out.println(path.toString());
			return path;
		}
//		else if (!path.get(path.size() - 1).equals(destination))
//		{
//			System.out.println("5555555555");
//			path.clear();
//			System.out.println(path.toString());
//		}

		//System.out.println(path.toString());
		return new ArrayList<>();
	}

	public static String fordfulkerson(WGraph graph)
	{
		String answer = "";
		int maxFlow = 0;
		
		/* YOUR CODE GOES HERE */
		//Create a residual graph and fill the residual graph
		//int max_flow = Integer.MAX_VALUE;

		WGraph Residual_graphs = new WGraph(graph);

		int source = Residual_graphs.getSource();
		int destination = Residual_graphs.getDestination();

		//ArrayList<Integer> path_tool = new ArrayList<Integer>();
		ArrayList<Integer> path; //= new ArrayList<>();

		//path = pathDFS(source,destination,Residual_graphs);

		while (! pathDFS(source,destination,Residual_graphs).isEmpty()) {
			//System.out.println("1");
			int flow = Integer.MAX_VALUE;
			path = pathDFS(source, destination, Residual_graphs);

			int counter1 = 0;
			while ((counter1 + 1) < path.size()) {
				//System.out.println("1");
				int min_weight = (Residual_graphs.getEdge(path.get(counter1), path.get(counter1 + 1)).weight);
				flow = Math.min(flow, min_weight);
				counter1++;
			}

			int counter2 = 0;
			while ((counter2 + 1) < path.size()) {
				//System.out.println("2");
				int node1 = path.get(counter2);
				int node2 = path.get(counter2 + 1);
				int new_weight = Residual_graphs.getEdge(node1, node2).weight - flow;

				Residual_graphs.setEdge(node1, node2, new_weight);

				if (Residual_graphs.getEdge(node2, node1) == null) {
					Edge e = new Edge(node2, node1, flow);
					Residual_graphs.addEdge(e);
				} else {
					int back_weight = (Residual_graphs.getEdge(node2, node1)).weight + flow;
					Residual_graphs.setEdge(node2, node1, back_weight);
				}
				counter2++;
			}
			maxFlow = maxFlow + flow;
		}
			//update the old the graph
			for (Edge e1: graph.getEdges())
			{
				for (Edge e2 : Residual_graphs.getEdges())
				{
					if (e1.nodes[0] == e2.nodes[0] && e1.nodes[1] == e2.nodes[1])
					{
						int update_weight = e1.weight-e2.weight;
						graph.setEdge(e1.nodes[0],e1.nodes[1],update_weight);
					}
				}
			}
			answer += maxFlow + "\n" + graph.toString() + "***********";
			return answer;
	}
	

	 public static void main(String[] args)
	 {
		 String file = "C:\\Users\\TYS\\Documents\\study\\University\\COMP251\\Assignment\\A3\\HW3_2021_template\\HW3_2021\\ff2.txt"; //args[0];
		 WGraph g = new WGraph(file);
		 System.out.println(fordfulkerson(g));
	 }

	 private static void traverse(Integer source, Integer destination,
								  WGraph graph,boolean[] visited, ArrayList<Integer> path )
	 {
	 	ArrayList<Integer> queue = new ArrayList<Integer>();
	 	for (int i = 0; i < graph.getEdges().size();i++)
		{
			Edge e1 = graph.getEdges().get(i);
			int node0 = e1.nodes[0];
			int node1 = e1.nodes[1];
			if (node0 == source)
			{
				queue.add(node1);
			}
		}

	 	path.add(source);
	 	visited[source] = true;

	 	for (int j = 0; j < queue.size(); j++)
		{
			int temp_node = queue.get(j);
			Edge e2 = graph.getEdge(source,temp_node);

			if (!visited[temp_node] && e2.weight > 0) //&& !source.equals(destination))
			{
				traverse(temp_node,destination,graph,visited,path);
				int last = path.get(path.size()-1);
				if (last != destination)
					path.remove(path.size()-1);
			}
		}
	 }
}

