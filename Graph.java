
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;


public class Graph {
    // use Integer max value instead of infinity for path and cost
    private final int INFINITY = Integer.MAX_VALUE;
    HashMap<String, Integer> capDistMap = new HashMap<String, Integer>();
    HashMap<String, String> stateNameMap = new HashMap<String, String>();
    HashMap<String, List<String>> bordersMap = new HashMap<String, List<String>>();
    // full graph, with a country name (code) as the key and a hashmap of adjacent countries and their distances as the value
    HashMap<String,HashMap<String,Integer>> graph = new HashMap<String,HashMap<String,Integer>>(); 

    // constructor for the graph class
    public Graph(HashMap<String, Integer> capDistMap, HashMap<String, String> stateNameMap, HashMap<String, List<String>> bordersMap) {
        // initialize the graph, hashmaps, nodes, etc.
        this.capDistMap = capDistMap;
        this.stateNameMap = stateNameMap;
        this.bordersMap = bordersMap;
        this.graph = new HashMap<String, HashMap<String, Integer>>();
        buildGraph();
    }

    // method to build the graph from data (hashmaps)
    private void buildGraph() {
        // first use the borders hashmap to get the names of the countries that border each country
        // get all of the keys from the borders hashmap
        for (String country : bordersMap.keySet()) {
            String currentCountryString = country;
            // create inner hashmap with strings for each country and corresponding integers
            HashMap<String, Integer> innerHashMap = new HashMap<String, Integer>();
            // get the list of bordering countries for the current country
            List<String> borderingCountries = bordersMap.get(country);
            // add each bordering country to the inner hashmap
            for (String borderingCountry : borderingCountries) {
                innerHashMap.put(borderingCountry, capDistMap.get(currentCountryString + "_" + borderingCountry));
            }
            // put inner hashmap into the graph
            graph.put(currentCountryString, innerHashMap);
            System.out.println(currentCountryString + ": " + innerHashMap);

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
    public void dijkstra(String source, String destination) {
        // implement dijkstra's algorithm
        // look at all of the items in the graph 
        // priority queue to store each country name and distance 
        PriorityQueue<HashMap<String, Integer>> pq = new PriorityQueue<HashMap<String, Integer>>();
        // add all countries with distance as max int
        // get string keys
        Set<String> countryNameKeys = graph.keySet();
        for (String country : countryNameKeys) {
            // get inner hashmap for corresponding country
            HashMap<String, Integer> innerHashMap = graph.get(country);
            pq.add(innerHashMap);
        }


        // set distance of starting country to 0
        // pull it out of the priority queue 
        // add anything that's out to a pathlist 
        // null -> usa = 0;
        // put distances for corresponding bordering countries
        // usa -> canada = 763
        // proceed until you will have multiple paths for each country
        // work backwards from destnation to source
        // keep going until you get to the destination country
        // use a hashmap to store the path
    }

}