package GraphPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GraphLibrary {

    public static ArrayList<Integer> vertexReverseOrder = new ArrayList<>();
    public static Double infinity = Double.POSITIVE_INFINITY;

    public static int DepthFirstSearch(Graph graph, int source, int clock, int[] postVisitClock, ArrayList<Integer> vertexReverseOrder, boolean[] visited){

        visited[source] = true;                                                 //set the corresponding source array index to true

        for (Edge destination : graph.adjancencyLists.get(source)){              //iterate over the each edge on the adjacency lists starting from the first list: eg source -> destination
            if(!visited[destination.getDestinationVertex()]){
                clock = DepthFirstSearch(graph, destination.getDestinationVertex(), clock, postVisitClock, vertexReverseOrder, visited);
            }
        }
        vertexReverseOrder.add(source);
        postVisitClock[source] = clock++;                       //increment the postvisit clock after all the edges with this source have been visited
        return clock;
    }

    public static boolean checkDirectedAcyclicGraph(Graph graph){

        int numberOfVertices = graph.getNumVertices();
        int clock = 0;                                          //start the clock at 0, it will be needed to check the postVisit clock.
        boolean[] visited = new boolean[numberOfVertices];         // creates a boolean with index equal to number of edges which stores the visited status of each vertex
        int[] postVisitClock = new int[numberOfVertices];         // same idea as previous array, only this time it is stored the postVisit clock for each vertex


        for (int vertexID = 0; vertexID < numberOfVertices; vertexID++){       //taking into account that numbers are correct and incremented by one
            if(!visited[vertexID]){
                clock = DepthFirstSearch(graph, vertexID, clock, postVisitClock, vertexReverseOrder, visited);
            }
        }

        for (int vertexId = 0; vertexId < numberOfVertices; vertexId++){
            for (Edge edge : graph.adjancencyLists.get(vertexId)) {
                if (postVisitClock[vertexId] <= postVisitClock[edge.getDestinationVertex()]){
                    return false;
                }
            }
        }
        return true;
    }

    public static void DirectedAcyclicGraph(Graph graph, int sourceVertex){
        if (GraphLibrary.checkDirectedAcyclicGraph(graph)) {
            System.out.println("Inputted Graph is a Directed Acyclic Graph");
            calculateDagShortestPath(graph, sourceVertex);
        }
        else {
            System.out.println("Inputted Graph is not a Directed Acyclic Graph");
            calculateNonDagShortestPath(graph, sourceVertex);
        }
    }

    public static ArrayList<Integer> dagLinearization(){
        ArrayList<Integer> topologicalOrder = new ArrayList<>();
        for (int j = vertexReverseOrder.size()-1; j>=0; j--)
        {
            topologicalOrder.add(vertexReverseOrder.get(j));
        }
        return topologicalOrder;
    }

    public static void calculateDagShortestPath(Graph graph, int source){
        double[] distance = new double[graph.getNumVertices()];
        ArrayList<Integer> graphLinearization = dagLinearization();
        int[] previous = new int[graph.getNumVertices()];

        for(int i=0; i<distance.length; i++)
        {
            distance[i] = infinity;
        }
        distance[source] = 0;
        previous[source] = -1;

        for (int i=0; i<graphLinearization.size(); i++){
            int nextVertex = graphLinearization.get(i);
            if(distance[nextVertex] != infinity){
                for (Edge neighbor : graph.adjancencyLists.get(nextVertex)) {
                    int destination = neighbor.getDestinationVertex();
                    double weight = neighbor.getWeight();
                    if(distance[destination] >= distance[nextVertex] + weight){
                        distance[destination] = distance[nextVertex] + weight;
                        previous[destination] = nextVertex;
                    }
                }
            }
        }
        showShortestPathCost(graph, source, distance, previous);
    }

    public static void calculateNonDagShortestPath(Graph graph, int source){
        PriorityQueue<Vertex> minHeap = new PriorityQueue<>(Comparator.comparing(vertex -> vertex.getWeight()));
        double[] distance = new double[graph.getNumVertices()];
        boolean[] completed = new boolean[graph.getNumVertices()];
        int[] previous = new int[graph.getNumVertices()];
        for(int i=0; i<distance.length; i++)
        {
            distance[i] = infinity;
        }
        distance[source] = 0;
        minHeap.add(new Vertex(source, 0.0));
        previous[source] = -1;
        completed[source] = true;

        for (int i=source+1; i<graph.getNumVertices()-1; i++) {
            minHeap.add(new Vertex(i, infinity));
        }
        while (!minHeap.isEmpty()){
            int vertexId = minHeap.poll().getId();
            for (Edge neighbor : graph.adjancencyLists.get(vertexId)) {
                int destination = neighbor.getDestinationVertex();
                double weight = neighbor.getWeight();
                if(!completed[destination]){
                    if(distance[destination] >= distance[vertexId] + weight) {
                        distance[destination] = distance[vertexId] + weight;
                        previous[destination] = vertexId;
                        minHeap.add(new Vertex(destination, distance[destination]));
                    }
                }
            }
            completed[vertexId] = true;
        }
        showShortestPathCost(graph, source, distance, previous);
    }

    public static void showShortestPathCost(Graph graph, int source, double[] distance, int[] previous){
        System.out.println("The distance from source vertex " + source + " to other vertices is shown below:");
        for(int vertex = 0; vertex < graph.getNumVertices(); vertex++){
            System.out.print("Distance from " + source + " to " + vertex + " is: ");
            if(distance[vertex] == infinity){
                System.out.println("Infinity (Unreachable)");
            }
            else{
                System.out.print(distance[vertex]);
                System.out.print("\tPath from " + source + " to " + vertex + " is: ");
                showShortestPathRoute(vertex, previous);
            }
        }
    }

    public static void showShortestPathRoute(int current, int[] previous){
        ArrayList<Integer> route = new ArrayList<>();
        while (current >= 0){
            route.add(current);
            current = previous[current];
        }
        for (int element = route.size()-1; element >= 0; element--){
            if(element == 0){
                System.out.print(route.get(element));
            }
            else{
                System.out.print(route.get(element) + " -> ");
            }
        }
        System.out.println();
    }

    public static Graph readFile(String path){
        int numOfVertices;
        int numOfEdges;
        ArrayList<Edge> edges = new ArrayList<>();

        ArrayList<String> fileLines = new ArrayList<>();
        try {
            Scanner input = new Scanner(new File(path));
            while (input.hasNextLine()){
                String inputLine = input.nextLine();
                if(inputLine.startsWith("#")){
                    continue;
                }
                fileLines.add(inputLine);
            }
            input.close();
        }  catch (FileNotFoundException fe){
           fe.printStackTrace();
        }

        String[] graphSubstrings = fileLines.get(0).split(" ");
        numOfVertices = Integer.parseInt(graphSubstrings[0]);
        numOfEdges = Integer.parseInt(graphSubstrings[1]);

        for (int i = 1; i < fileLines.size(); i++){
                String[] edgeSubstrings = fileLines.get(i).split(" ");
                int source = Integer.parseInt(edgeSubstrings[0]);
                int destination = Integer.parseInt(edgeSubstrings[1]);
                double weight = Double.parseDouble(edgeSubstrings[2]);
                edges.add(new Edge(source, destination, weight));
        }
        return new Graph(edges, numOfVertices, numOfEdges);
    }
}
