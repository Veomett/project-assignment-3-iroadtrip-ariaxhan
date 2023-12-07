
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Graph {
    // use Integer max value instead of infinity for path and cost
    private final int INFINITY = Integer.MAX_VALUE;
    // global variables
    HashMap<String, Integer> capDistMap = new HashMap<String, Integer>();
    HashMap<String, String> stateNameMap = new HashMap<String, String>();
    HashMap<String, List<String>> bordersMap = new HashMap<String, List<String>>();
    HashMap<String, String> reverseStateMap = new HashMap<String, String>();
    HashMap<String, String> edgeCases = new HashMap<String, String>();
    HashMap<String, String> edgeCasesbyString = new HashMap<String, String>();
    // full graph, with a country name (code) as the key and a hashmap of adjacent
    HashMap<String, HashMap<String, Integer>> graph = new HashMap<String, HashMap<String, Integer>>();

    // constructor, taking in maps as input
    public Graph(HashMap<String, Integer> capDistMap,
            HashMap<String, String> stateNameMap,
            HashMap<String, List<String>> bordersMap,
            HashMap<String, String> reverseStateMap,
            HashMap<String, String> edgeCases,
            HashMap<String, String> edgeCasesbyString) {
        // countries and their distances as the value
        this.graph = new HashMap<String, HashMap<String, Integer>>();
        this.capDistMap = capDistMap;
        this.stateNameMap = stateNameMap;
        this.bordersMap = bordersMap;
        this.reverseStateMap = reverseStateMap;
        this.edgeCases = edgeCases;
        this.edgeCasesbyString = edgeCasesbyString;
    }

    // function to return the distance between two countries
    public int returnGraphDist(String country1, String country2) {
        // initialize distance
        int distance = 0;
        // get code for country name
        String countryCode = reverseStateMap.get(country1);
        HashMap<String, Integer> borderCountries = this.graph.get(countryCode);
        Set<String> codeKeys = borderCountries.keySet();
        for (String country : codeKeys) {
            if (country.equals(country2)) {
                // if the country is found, get the distance
                distance = borderCountries.get(country);
            }
        }
        return distance;
    }

    // function to build the graph
    // method to build the graph from data (hashmaps)
    public void buildGraph() {
        System.out.println("BuildGraph function entered");
        // Get all of the country codes from the state name hashmap
        Set<String> countryCode = stateNameMap.keySet();
        // System.out.println(countryCode);
        // Loop over the country codes
        for (String country : countryCode) {
            // Get the country name
            String countryString = stateNameMap.get(country);
            // System.out.println("Country string: " + countryString);
            // Get list of bordering countries, initializing variable
            List<String> borderingCountries = null;
            borderingCountries = bordersMap.get(countryString);
            // check if bordering countries exist
            if (borderingCountries == null) {
                // check if it is an edge case, using the code to check
                String countryasBorderString = checkEdgeCasebyCode(country, edgeCases);
                // if it is an edge case
                if (countryasBorderString != null) {
                    borderingCountries = bordersMap.get(countryasBorderString);
                    // System.out.println("edge case: " + countryasBorderString);
                } else {
                    // the country is not an edge case nor a valid country
                    // System.err.println(countryString + " is not a valid country");
                }
            }
            // check if bordering countries are still null, proceed if they are not
            if (borderingCountries != null) {
                // loop over bordering countries, get codes, and add to graph
                for (String borderingCountry : borderingCountries) {
                    // create inner hashmap with strings for each country and corresponding integers
                    HashMap<String, Integer> innerHashMap = graph.get(country);
                    // check if the inner hashmap is null, if it is, initialize it
                    if (innerHashMap == null) {
                        innerHashMap = new HashMap<String, Integer>();
                    }
                    // get the country code for the current bordering country
                    String borderingCountryCode = reverseStateMap.get(borderingCountry);
                    // System.out.println("bordering country code: " + borderingCountryCode);

                    // if it is null, check if it is an edge case
                    if (borderingCountryCode == null) {
                        // System.out.println("potential bordering country string : " +
                        // borderingCountry);
                        borderingCountryCode = checkEdgeCasebyString(borderingCountry, edgeCasesbyString);
                    }
                    String key = country + "_" + borderingCountryCode;
                    Integer distance = capDistMap.get(key);
                    if (distance != null) {

                        // System.out.println(key + " at " + distance);
                        innerHashMap.put(borderingCountry, distance);
                        // System.out.println("country " + country + " bordering country " +
                        // borderingCountryCode);
                        // put inner hashmap into the graph
                        graph.put(country, innerHashMap);
                    }
                    // System.out.println(country + ": " + innerHashMap);
                }
            }
        }
    }

    public String checkEdgeCasebyCode(String countryCode, HashMap<String, String> edgeCases) {
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

    public String checkEdgeCasebyString(String countryString, HashMap<String, String> edgeCasesbyString) {
        // check if given country code is in edge cases
        // System.out.println("country string inputted in check edge case by string: " +
        // countryString);
        for (String string : edgeCasesbyString.keySet()) {
            if (string.equals(countryString)) {
                // System.out.println("string in edge cases by string: " + string);
                // get the corresponding code
                String code = edgeCasesbyString.get(string);
                // System.out.println(code);
                // return the code
                return code;
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

    // Dijkstra's algorithm to find the shortest path (polished by ChatGPT)
    public List<String> dijkstra(String source, String destination) {
        System.out.println("Dijkstra function entered");
        Map<String, Integer> distances = new HashMap<>();
        // make a list to store the visited countries
        List<String> visited = new LinkedList<>();
        // create priority queue to store countries
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
        // put all of the countries in the graph into the queue, initializing all at
        // infinity
        System.out.println("priority queue: " + pq);
        // first put source with value 0
        distances.put(source, 0);
        pq.offer(new HashMap.SimpleEntry<>(source, distances.get(source)));
        for (String country : graph.keySet()) {
            distances.put(country, INFINITY);
            // add to priority queue
            pq.offer(new HashMap.SimpleEntry<>(country, distances.get(country)));
        }
        System.out.println("updated priority queue: " + pq);
        // loop to the end of the queue
        while (!pq.isEmpty()) {
            // get next country in the queue, which is the lowest cost unknown vertex
            String currentCountry = pq.poll().getKey();
            int currentDistance = distances.get(currentCountry);
            // set known, which is the removal from the queue in this case
            // the distance being infinity marks it as unknown
            if (currentDistance == INFINITY)
                continue;
            // stop when the destination is reached
            if (currentCountry.equals(destination))
                break;
            // for each neighbor, update cost (cost to get to current) + cumilative distance
            System.out.println("current country: " + currentCountry);
            // get code of current country
            String currentCountryCode = reverseStateMap.get(currentCountry);
            System.out.println("current country code: " + currentCountryCode);
            if (graph.get(currentCountryCode) == null) {
                break;
            }
            // loop through neighbors
            for (String neighbor : graph.get(currentCountryCode).keySet()) {
                System.out.println("neighbor " + neighbor);
                // convert neighbor to code
                String neighborCode = reverseStateMap.get(neighbor);
                // get the distance between the two countries
                int distance = returnGraphDist(currentCountry, neighbor);
                System.out.println("distance: " + distance);
                // total distance + distance between the two specific entries
                int newDistance = currentDistance + distance;
                System.out.println("new distance: " + newDistance);
                int PrevDistance = distances.get(neighborCode);
                // if the new distance is less than the prev distance, update it
                if (newDistance < PrevDistance) {
                    distances.put(neighbor, newDistance);
                    String returnString = currentCountry + " --> " + neighbor + " (" + newDistance + " km.)";
                    System.out.println("return string: " + returnString);
                    visited.add(returnString);
                    
                    pq.offer(new HashMap.SimpleEntry<>(neighbor, newDistance));
                }
            }
        }
        Collections.reverse(visited);
        return visited;
        // return the path
    }

    // Method to reconstruct the shortest path from source to destination
    // aka go backwards
    private List<String> reconstructPath(Map<String, String> predecessors, String source, String destination) {
        // list to store the path
        LinkedList<String> path = new LinkedList<>();
        // string to store each step in the path
        String step = destination;
        System.out.println("step: " + step);

        // Check if a path exists
        if (predecessors.get(step) == null) {
            return path; // empty list if no path is found
        }
        // if it does, add it
        path.add(step);
        while (predecessors.containsKey(step) && !step.equals(source)) {
            step = predecessors.get(step);
            path.addFirst(step); // add at the beginning
        }

        // Check if the source is reached
        if (!path.getFirst().equals(source)) {
            return new LinkedList<>(); // return an empty list if the source isn't reached
        }

        return path;
    }
}
