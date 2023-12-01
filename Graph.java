
import java.util.HashMap;
import java.util.List;


public class Graph {
    // use Integer max value instead of infinity for path and cost
    private final int INFINITY = Integer.MAX_VALUE;
    private HashMap<String, Node> nodes; // Each node is identified by a string 
    HashMap<String, Integer> capDistMap = new HashMap<String, Integer>();
    HashMap<String, String> stateNameMap = new HashMap<String, String>();
    HashMap<String, List<String>> bordersMap = new HashMap<String, List<String>>();
    HashMap<String,HashMap<String,Integer>> graph = new HashMap<String,HashMap<String,Integer>>(); 
    
    // inner class to represent each node in the graph
    private class Node {
        String name; // node identifier, country name
        boolean known; // to check if the shortest path to this node is already found
        int path; // to store the previous node in the shortest path
        int cost; // to store the cost to reach this node from the source
        // weight/cost of edges is capdist
        HashMap<Node, Integer> adjacent; // adjacency list for the node with costs
        // borders

        // constructor for node
        Node(String name) {
            // initialize the node with the given name and default values for other fields
            this.name = name;
            this.known = false;
            this.path = -1;
            this.cost = INFINITY;
            this.adjacent = new HashMap<Node, Integer>();
        }
    }

    // constructor for the graph class
    public Graph(HashMap<String, Integer> capDistMap, HashMap<String, String> stateNameMap, HashMap<String, List<String>> bordersMap) {
        // initialize the graph, hashmaps, nodes, etc.
        this.capDistMap = capDistMap;
        this.stateNameMap = stateNameMap;
        this.bordersMap = bordersMap;
        this.nodes = new HashMap<String, Node>();
        buildGraph();
    }

    // method to build the graph from data (hashmaps)
    private void buildGraph() {
        // populate the nodes and their adjacency lists using the provided hashmaps
        // loop over state names and use them as keys, creating a new node for each one
        for (int i = 0; i < stateNameMap.size(); i++) {
            // get key at current index
            String key = stateNameMap.get(i);
            // create new node with key as name
            Node newNode = new Node(key);
            // add node to nodes hashmap
            nodes.put(key, newNode);
        }
        
    }

    // dijkstra's algorithm for finding shortest paths
    public void dijkstra() {
        // implement dijkstra's algorithm
        // get least cost unknown node by looping through nodes and checking if known is false, then comparing costs
        for (int k = 0; k < )

    }

}