
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Graph {
    // use Integer max value instead of infinity for path and cost
    private final int INFINITY = Integer.MAX_VALUE;

    HashMap<String, Integer> capDistMap = new HashMap<String, Integer>();
    HashMap<String, String> stateNameMap = new HashMap<String, String>();
    HashMap<String, List<String>> bordersMap = new HashMap<String, List<String>>();
    HashMap<String, String> reverseStateMap = new HashMap<String, String>();
    HashMap<String, String> edgeCases = new HashMap<String, String>();
    HashMap<String, String> edgeCasesbyString = new HashMap<String, String>();

    // full graph, with a country name (code) as the key and a hashmap of adjacent
    // countries and their distances as the value
    HashMap<String, HashMap<String, Integer>> graph = new HashMap<String, HashMap<String, Integer>>();

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

    public int returnGraphDist(String country1, String country2) {
        // initialize distance
        int distance = 0;
        // get code for country name
        String countryCode = reverseStateMap.get(country1);
        String countryCode2 = reverseStateMap.get(country2);
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
            // Get list of bordering countries
            List<String> borderingCountries = null;
            borderingCountries = bordersMap.get(countryString);
            // check if bordering countries exist
            if (borderingCountries == null) {
                // check if it is an edge case
                String countryasBorderString = checkEdgeCasebyCode(country, edgeCases);
                // if it is an edge case
                if (countryasBorderString != null) {
                    borderingCountries = bordersMap.get(countryasBorderString);
                    // System.out.println("edge case: " + countryasBorderString);
                } else {
                    continue;
                }
            }
            // check if bordering countries are still null
            if (borderingCountries != null) {
                // loop over bordering countries, get codes, and add to graph
                for (String borderingCountry : borderingCountries) {
                    // create inner hashmap with strings for each country and corresponding integers
                    HashMap<String, Integer> innerHashMap = graph.get(country);
                    if (innerHashMap == null) {
                        innerHashMap = new HashMap<String, Integer>();
                    }
                    // System.out.println( "bordering country name: " + borderingCountry);
                    // get country code for bordering country string
                   // System.out.println("bordering country: " + borderingCountry);
                    String borderingCountryCode = reverseStateMap.get(borderingCountry);
                   // System.out.println("bordering country code: " + borderingCountryCode);
                    if (borderingCountryCode == null) {
                        checkEdgeCasebyString(borderingCountry, edgeCasesbyString);
                    }
                    // System.out.println(borderingCountryCode + " is the code for " +
                    // borderingCountry);
                    // add each bordering country to the inner hashmap
                    // get distance
                    // System.out.println(country + "_" + borderingCountryCode);
                    // get key
                    String key = country + "_" + borderingCountryCode;
                    Integer distance = capDistMap.get(key);
                    // System.out.println(key + " at " + distance);
                    innerHashMap.put(borderingCountry, distance);
                    // System.out.println("country " + country + " bordering country " +
                    // borderingCountryCode);
                    // put inner hashmap into the graph
                    graph.put(country, innerHashMap);
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
        for (String string : edgeCasesbyString.keySet()) {
            if (string.equals(countryString)) {
                // get the corresponding code
                String code = edgeCasesbyString.get(string);
            //    System.out.println(countryString + code);
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
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> predecessors = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
        // put all of the countries in the graph into the queue, initializing all at
        // infinity
        for (String country : graph.keySet()) {
            if (country.equals(source)) {
                distances.put(country, 0);
            } else {
                distances.put(country, INFINITY);
            }
            // add to priority queue
            pq.offer(new HashMap.SimpleEntry<>(country, distances.get(country)));
        }
        // loop to the end of the queue
        while (!pq.isEmpty()) {
            // get next country in the queue, which is the lowest cost unknown vertext
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
            for (Map.Entry<String, Integer> neighborEntry : graph.get(currentCountry).entrySet()) {
                String neighbor = neighborEntry.getKey();
                int edgeWeight = neighborEntry.getValue();
                // total distance + distance between the two specific entries
                int newDistance = currentDistance + edgeWeight;
                // if the new distance is less than the prev distance, update it
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    predecessors.put(neighbor, currentCountry);
                    pq.offer(new HashMap.SimpleEntry<>(neighbor, newDistance));
                }
            }
        }

        return reconstructPath(predecessors, source, destination);
    }

    // Method to reconstruct the shortest path from source to destination
    // aka go backwards
    private List<String> reconstructPath(Map<String, String> predecessors, String source, String destination) {
        // list to store the path
        LinkedList<String> path = new LinkedList<>();
        // string to store each step in the path
        String step = destination;

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
