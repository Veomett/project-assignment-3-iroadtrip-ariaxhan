
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
    HashMap<String, String> reverseStateMap = new HashMap<String, String>();
    // full graph, with a country name (code) as the key and a hashmap of adjacent countries and their distances as the value
    HashMap<String, HashMap<String, Integer>> graph = new HashMap<String, HashMap<String, Integer>>();

    public Graph() {
    }

    // method to build the graph from data (hashmaps)
    public void buildGraph(
            HashMap<String, Integer> capDistMap,
            HashMap<String, String> stateNameMap,
            HashMap<String, List<String>> bordersMap,
            HashMap<String, String> reverseStateMap,
            HashMap<String, String> edgeCases) {

        System.out.println("BuildGraph function entered");

        // Get all of the country codes from the state name hashmap
        Set<String> countryCode = stateNameMap.keySet();
        //System.out.println(countryCode);

        // Loop over the country codes
        for (String country : countryCode) {
            // Get the country name
            String countryString = stateNameMap.get(country);
            //  System.out.println("Country string: " + countryString);
            // Get list of bordering countries
            List<String> borderingCountries = null;
            borderingCountries = bordersMap.get(countryString);
            // check if bordering countries exist
            if (borderingCountries == null) {
                // check if it is an edge case
                String countryasBorderString = checkEdgeCase(country, edgeCases);
                // if it is an edge case
                if (countryasBorderString != null) {
                    borderingCountries = bordersMap.get(countryasBorderString);
                    System.out.println("edge case: " + countryasBorderString);
                } else {
                    continue;
                }
            }
            // check if bordering countries are still null
            if (borderingCountries != null) {

                // loop over bordering countries, get codes, and add to graph
                for (String borderingCountry : borderingCountries) {
                    // create inner hashmap with strings for each country and corresponding integers
                    HashMap<String, Integer> innerHashMap = new HashMap<String, Integer>();
                    // System.out.println( "bordering country name: " + borderingCountry);
                    // get country code for bordering country string
                    //System.out.println("bordering country: " + borderingCountry);
                    String borderingCountryCode = reverseStateMap.get(borderingCountry);
                   
                    System.out.println(borderingCountryCode + " is the code for " + borderingCountry);
                    // add each bordering country to the inner hashmap
                    // get distance
                    //  System.out.println(country + "_" + borderingCountryCode);
                    // get key
                    String key = country + "_" + borderingCountryCode;
                    Integer distance = capDistMap.get(key);
                    System.out.println(key + " at " + distance);
                    innerHashMap.put(borderingCountry, distance);
                    // System.out.println("country " + country + " bordering country " + borderingCountryCode);
                    // put inner hashmap into the graph
                    graph.put(country, innerHashMap);
                    // System.out.println(country + ": " + innerHashMap);
                }
            }
        }
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

        }

    }

    public String checkEdgeCase(String countryCode, HashMap<String, String> edgeCases) {
        // check if given country code is in edge cases
        for (String code : edgeCases.keySet()) {
            if (countryCode.equals(code)) {
                // get the corresponding string
                String edgeCaseString = edgeCases.get(code);
                // split the string on the underscore
                String[] edgeCaseStringArray = edgeCaseString.split("_");
                // get borders name
                String bordersName = edgeCaseStringArray[0];
                // return borders name
                return bordersName;
            }
        }
        // if not in edge cases, return null
        return null;
    }
    
    public void printGraph() {
        for (String country : graph.keySet()) {
            System.out.println(country + ": " + graph.get(country));
        }
    }
}



