package GraphPackage;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {

    //parameters for holding the number of graph edges, graph vertices, and the adjancency lists for each graph vertex
    private int numEdges;
    private int numVertices;
    public ArrayList<ArrayList<Edge>> adjancencyLists;


    public int getNumEdges() {
        return numEdges;
    }

    public void setNumEdges(int numEdges) {
        this.numEdges = numEdges;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public Graph(ArrayList<Edge> graphEdges, int numVertices, int numEdges){
        this.numVertices = numVertices;
        this.numEdges = numEdges;
        adjancencyLists = new ArrayList();
        
        //create an empty adjancency list for each vertex in the graph
        for(int i = 0; i < numVertices; i++){
            adjancencyLists.add(new ArrayList<>());
        }

        for (Edge graphEdge:graphEdges) {
            adjancencyLists.get(graphEdge.getSourceVertex()).add(graphEdge);
        }
    }

}
