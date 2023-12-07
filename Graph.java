
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
        Map<String, String> predecessors = new HashMap<>(); // Map to store predecessors

        // make a list to store the visited countries
        // List<String> visited = new LinkedList<>();
        // create priority queue to store countries
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
        // put all of the countries in the graph into the queue, initializing all at
        // infinity
        // System.out.println("priority queue: " + pq);

        for (String country : graph.keySet()) {
            distances.put(country, INFINITY);
            // add to priority queue
            pq.offer(new HashMap.SimpleEntry<>(country, distances.get(country)));
        }

        // Set distance for source after initializing others
        distances.put(source, 0);
        pq.offer(new HashMap.SimpleEntry<>(source, 0));

        System.out.println("updated priority queue: " + pq);
        // loop to the end of the queue
        while (!pq.isEmpty()) {
            String currentCountry = pq.poll().getKey();
            int currentDistance = distances.get(currentCountry);

            if (currentDistance == INFINITY) {
                continue;
            }

            if (currentCountry.equals(destination)) {
                break;
            }

            String currentCountryCode = reverseStateMap.get(currentCountry);
            if (graph.get(currentCountryCode) != null) {
                for (String neighbor : graph.get(currentCountryCode).keySet()) {
                    String neighborCode = reverseStateMap.get(neighbor);
                    int distance = returnGraphDist(currentCountry, neighbor);
                    int newDistance = currentDistance + distance;

                    if (distances.containsKey(neighborCode) && newDistance < distances.get(neighborCode)) {
                        distances.put(neighbor, newDistance);
                        predecessors.put(neighborCode, currentCountryCode);
                        pq.offer(new HashMap.SimpleEntry<>(neighbor, newDistance));
                    }
                }
            }

        }
        return reconstructPath(predecessors, source, destination);
    }

    // Method to reconstruct the shortest path from source to destination
    // aka go backwards
    private List<String> reconstructPath(Map<String, String> predecessors, String source, String destination) {
        System.out.println("reconstructPath function entered");
        // list to store the path
        LinkedList<String> path = new LinkedList<>();
        // string to store each step in the path
        String step = destination;
        System.out.println("step: " + step);
        // convert step to code
        step = reverseStateMap.get(step);
        // convert source to code
        source = reverseStateMap.get(source);

        // Check if a path exists
        System.out.println("predecessors: " + predecessors);
        System.out.println(predecessors.get(step));
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
        System.out.println("path: " + path);
        return path;
    }
}
