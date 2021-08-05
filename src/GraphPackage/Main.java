package GraphPackage;

public class Main {

    public static void main(String[] args){
        String path = "./src/graphFiles/Graph.txt";
        Graph graph = GraphLibrary.readFile(path);
        int sourceVertex = 0;

        // check if the given directed graph is DAG or not
        GraphLibrary.DirectedAcyclicGraph(graph, sourceVertex);

    }
}
