
import java.util.HashMap;
import java.util.List;
import java.util.*;

import org.w3c.dom.Node;


public class Graph {
    // use Integer max value instead of infinity for path and cost
    private final int INFINITY = Integer.MAX_VALUE;
    private HashMap<String, Vertex> vertices; // Each node is identified by a string 
    HashMap<String, Integer> capDistMap = new HashMap<String, Integer>();
    HashMap<String, String> stateNameMap = new HashMap<String, String>();
    HashMap<String, List<String>> bordersMap = new HashMap<String, List<String>>();
    // full graph, with a country name (code) as the key and a hashmap of adjacent countries and their distances as the value
    HashMap<String,HashMap<String,Integer>> graph = new HashMap<String,HashMap<String,Integer>>(); 
    
    // inner class to represent each node in the graph
    private class Vertex {
        String name; // node identifier, country name
        boolean known; // to check if the shortest path to this node is already found
        int path; // to store the previous node in the shortest path
        int cost; // to store the cost to reach this node from the source
        // weight/cost of edges is capdist
        // borders

        // constructor for node
        Vertex(String name) {
            // initialize the node with the given name and default values for other fields
            this.name = name;
            this.known = false;
            this.path = -1;
            this.cost = INFINITY;
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
            Node newNode = new Vertex(key);
            // add node to nodes hashmap
            vertices.put(key, newNode);
        }

        // first use the borders hashmap to get the names of the countries that border each country
        for (int w = 0; w < bordersMap.size(); w++) {
            String country = bordersMap.getKey();
        }
        // for each country, get the list of its bordering countries
        // loop over the list of bordering countries and add them to the inner hashmap
        // use state name map to get the country code for each country
        // replace the country name with the country code
        // use the capdist hashmap to get the distance between the two countries
        // add distance and country 2 name to the graph
        // use another hashmap to store the aliases 
    }
    // dijkstra's algorithm for finding shortest paths
    public void dijkstra() {
        // implement dijkstra's algorithm
        // get least cost unknown node by looping through nodes and checking if known is false, then comparing costs
        for (int k = 0; k < )

    }

}