import java.util.*;
import java.util.Iterator;
public class BellmanFord{

    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    class BellmanFordException extends Exception
    {
        public BellmanFordException(String str){
            super(str);
        }
    }

    class NegativeWeightException extends BellmanFordException
    {
        public NegativeWeightException(String str){
            super(str);
        }
    }

    class PathDoesNotExistException extends BellmanFordException
    {
        public PathDoesNotExistException(String str){
            super(str);
        }
    }

    BellmanFord(WGraph g, int source) throws NegativeWeightException
    {
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         */

        //step 1 : initialize the graph
        this.distances = new int[g.getNbNodes()];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;

        this.predecessors = new int[g.getNbNodes()];
        Arrays.fill(predecessors,-1);

        ArrayList<Edge> edgesArray = new ArrayList<Edge>(g.getEdges());
        //step 2 : relax all edges |V|-1 times
        for (int i = 0; i < g.getNbNodes()-1; i++)
        {
            for (int j = 0; j < edgesArray.size();j++)
            {
                int node0 = edgesArray.get(j).nodes[0];
                int node1 = edgesArray.get(j).nodes[1];
                int weight = edgesArray.get(j).weight;

                if (distances[node0] != Integer.MAX_VALUE && distances[node0] + weight < distances[node1])
                {
                    distances[node1] = distances[node0] + weight;
                    System.out.println("distance  node: " +distances[node1]);
                    predecessors[node1] = node0;
                    System.out.println("weight: " + weight);
                    System.out.println(node1);
                    System.out.println("predecessors: " + distances[node1]);
                }
            }
        }

        //step3 : check for negative-weight cycles
        for (Edge e : g.getEdges())
        {
            int node0 = e.nodes[0];
            int node1 = e.nodes[1];
            int weight = e.weight;
            if (distances[node0] != Integer.MAX_VALUE && distances[node0] + weight < distances[node1])
            {
                throw new NegativeWeightException("it is a negative cycle");
            }

        }
    }

    public int[] shortestPath(int destination) throws PathDoesNotExistException
    {
        /* Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If not path exists an Error is thrown
         */

        ArrayList<Integer> path = new ArrayList<Integer>();
        path.add(destination);
        int current_node = destination;

        while (path.get(0) != this.source) {
            if (current_node != -1) {
                path.add(0, this.predecessors[current_node]);
                current_node = this.predecessors[current_node];
            } else {
                throw new PathDoesNotExistException("No path");
            }
        }
        int[] output_path = new int[path.size()];
        for (int i = 0; i < output_path.length;i++)
        {
            output_path[i] = path.get(i);
        }
        return output_path;
    }

    public void printPath(int destination)
    {
        /* Print the path in the format s->n1->n2->destination
         * if the path exists, else catch the Error and 
         * prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){
        String file = "C:\\Users\\TYS\\Documents\\study\\University\\COMP251\\Assignment\\A3\\HW3_2021_template\\HW3_2021\\bf1.txt";
        WGraph g = new WGraph(file);
        try
        {
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

   } 
}

