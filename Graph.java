
public class Graph {
    // use Integer max value instead of infinity for path and cost
    private final int INFINITY = Integer.MAX_VALUE;
    private Map<String, Node> nodes; // Each node is identified by a string 
    HashMap<String, String> capDistMap = new HashMap<String, String>();HashMap<String, String> stateNameMap = new HashMap<String, String>();HashMap<String, String> bordersMap = new HashMap<String, String>();   
    
    // inner class to represent each node in the graph
    private class Node {
        String name; // node identifier, like country name
        boolean known; // to check if the shortest path to this node is already found
        int path; // to store the previous node in the shortest path
        int cost; // to store the cost to reach this node from the source
        Map<Node, Integer> adjacent; // adjacency list for the node with costs

        // constructor for node
        Node(String name) {
            // initialize the node with the given name and default values for other fields
        }
    }

    // constructor for the graph class
    public Graph() {
        // initialize the graph, hashmaps, nodes, etc.
    }

    // method to build the graph from data (hashmaps)
    private void buildGraph() {
        // populate the nodes and their adjacency lists using the provided hashmaps
    }

    // dijkstra's algorithm for finding shortest paths
    public void dijkstra(String startNode) {
        // implement dijkstra's algorithm starting from the given node
    }

}