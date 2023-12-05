
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

    /* constructor for the graph class
    public Graph(HashMap<String, Integer> capDistMap, HashMap<String, String> stateNameMap,
            HashMap<String, List<String>> bordersMap) {
        // initialize the graph, hashmaps, nodes, etc.
        this.capDistMap = capDistMap;
        this.stateNameMap = stateNameMap;
        this.bordersMap = bordersMap;
        this.graph = new HashMap<String, HashMap<String, Integer>>();
        buildGraph();
    }
    */
    
    public Graph() {}

    // method to build the graph from data (hashmaps)
    public void buildGraph() {
        // first use the borders hashmap to get the names of the countries that border each country
        System.out.println("BuildGraph function entered");
        // get country codes and corresponding country names from state map
        // use country name to get corresponding list of border countries
        // use country code to get corresponding distance

        // get all of the country names from the state name hashmap
        Set<String> countryCode = stateNameMap.keySet();
        // loop over the country codes

        String countryString = stateNameMap.get(countryCode);
        // get all of the keys from the borders hashmap
        Set<String> countryKeys = bordersMap.keySet();
        for (String country : countryKeys) {
            String currentCountryString = country;
            // create inner hashmap with strings for each country and corresponding integers
            HashMap<String, Integer> innerHashMap = new HashMap<String, Integer>();
            System.out.println("Current country: " + currentCountryString);
            // get the list of bordering countries for the current country
            List<String> borderingCountries = bordersMap.get(country);
            System.out.println("Bordering countries for " + currentCountryString + ": " + borderingCountries);
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
            // if country is equal to the source
            // place it into the pq with a weight of 0
            if (country.equals(source)) {
                // get inner hashmap for corresponding country
                HashMap<String, Integer> innerHashMap = graph.get(country);
                // add to queue with distance of 0
                innerHashMap.put(country, 0);
                pq.add(innerHashMap);
            } else {
                // otherwise, add to queue with distance of infinity
                // get inner hashmap for corresponding country
                HashMap<String, Integer> innerHashMap = graph.get(country);
                // add to queue with distance of infinity
                innerHashMap.put(country, Math.abs(INFINITY));
                pq.add(innerHashMap);
            }
        }
        // initialize name of country
        String currentCountryName = "";
            // while queue is not empty
            while (!pq.isEmpty() && !currentCountryName.equals(destination)) {
            //pull out the next priority item (country and distance)
            HashMap<String, Integer> currentItem = pq.poll();
            // get the country name
            currentCountryName = currentItem.keySet().toString();
            // get the distance
            int currentDistance = currentItem.get(currentCountryName);
            // get the inner hashmap for the current country
            HashMap<String, Integer> innerHashMap = graph.get(currentCountryName);
            // get the keys for the inner hashmap
            Set<String> innerHashMapKeys = innerHashMap.keySet();
            // for each key in the inner hashmap
            for (String key : innerHashMapKeys) {
                // get the distance for the current key
                int distance = innerHashMap.get(key);
                // if the distance is less than the current distance
                if (distance < currentDistance) {
                    // update the distance
                    currentDistance = distance;
                    // update the country name
                    currentCountryName = key;
                }
            }

            // proceed until you will have multiple paths for each country
            // work backwards from destnation to source
            // keep going until you get to the destination country
            // use a hashmap to store the path
        }
    }
}