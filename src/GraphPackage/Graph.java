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





































    /*private int vertices;
    private int edges;
    private LinkedList<Integer> adjacencyLists[];
    private boolean visitedVertices[];

    public Graph(int vertices, int edges) {
        adjacencyLists = new LinkedList[vertices];                      // creates an linked list array representation of adjacency lists
        visitedVertices = new boolean[vertices];                        // creates an array of boolean which is equal to the size of the vertices
        for(int i = 0; i<vertices; i++){
            adjacencyLists[i] = new LinkedList<>();                     // creates an adjancency linked list for each vertex
        }
    }

    public void insertEdge(Edge e){
        int sourceNum = e.getSource();                            // Get the source num of the edge
        int destinationNum = e.getDestination();                  // Get the destination num of the edge
        adjacencyLists[sourceNum].add(e.getDestination());                     // Add to adjacency list
    }

    public int getVertices() {
        return vertices;
    }

    public void setVertices(int vertices) {
        this.vertices = vertices;
    }

    public int getEdges() {
        return edges;
    }

    public void setEdges(int edges) {
        this.edges = edges;
    }*/


}
