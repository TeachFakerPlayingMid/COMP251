import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){

        /* Fill this method (The statement return null is here only to compile) */
        DisjointSets new_set = new DisjointSets(g.getNbNodes());
        WGraph new_graph = new WGraph();

        ArrayList<Edge> sortedEdge = new ArrayList<Edge>(g.listOfEdgesSorted());
        for (Edge edge : sortedEdge)
        {
            int i = edge.nodes[0];
            int j = edge.nodes[1];
            Boolean safe = IsSafe(new_set,edge);

            if (safe)
            {
                int i_root = new_set.find(i);
                int j_root = new_set.find(j);
                new_set.union(i_root,j_root);
                new_graph.addEdge(edge);
            }
        }
        return new_graph;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e)
    {
        int root_i = p.find(e.nodes[0]);
        int root_j = p.find(e.nodes[1]);

        if (root_i == root_j)
        {
            return false;
        }
        else
            return true;


        /* Fill this method (The statement return 0 is here only to compile) */


        //return true;
    
    }

    public static void main(String[] args)
    {

        String file = "C:\\Users\\TYS\\Documents\\study\\University\\COMP251\\Assignment\\A2\\hw2_basecode_updated\\template\\g1.txt"; //args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        //System.out.println(t);

   } 
}
