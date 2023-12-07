
import java.util.AbstractMap;
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
            LinkedList<String> graphCountryKeys = new LinkedList<String>();
    HashMap<String, Integer> capDistMap = new HashMap<String, Integer>();
    HashMap<String, String> stateNameMap = new HashMap<String, String>();
    HashMap<String, List<String>> bordersMap = new HashMap<String, List<String>>();
    HashMap<String, String> reverseStateMap = new HashMap<String, String>();
    HashMap<String, String> edgeCases = new HashMap<String, String>();
    HashMap<String, String> edgeCasesbyString = new HashMap<String, String>();
    // full graph, with a country name (code) as the key and a hashmap of adjacent
    HashMap<String, HashMap<String, Integer>> graph = new HashMap<String, HashMap<String, Integer>>();
    Set<String> keys;

    // constructor, taking in maps as input
    public Graph(HashMap<String, Integer> capDistMap,
            HashMap<String, String> stateNameMap,
            HashMap<String, List<String>> bordersMap,
            HashMap<String, String> reverseStateMap,
            HashMap<String, String> edgeCases,
            HashMap<String, String> edgeCasesbyString) {
        // countries and their distances as the value
        this.graphCountryKeys = new LinkedList<String>();
        this.graph = new HashMap<String, HashMap<String, Integer>>();
        this.keys = this.graph.keySet();
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
                        borderingCountryCode = checkEdgeCasebyString(borderingCountry, edgeCasesbyString);
                    }
                    String key = country + "_" + borderingCountryCode;
                    Integer distance = capDistMap.get(key);
                    if (distance != null) {
                        innerHashMap.put(borderingCountry, distance);
                        // put inner hashmap into the graph
                        graph.put(country, innerHashMap);
                        // create list of countries as keys
                        graphCountryKeys.add(country);
                        System.out.println(country);
                    }
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
                // return the code
                return code;
            }
        }
        // if not in edge cases, return null
        return null;
    }

    public void printGraph() {
        for (String country : graph.keySet()) {
            System.out.println(country + ": " + this.graph.get(country));
        }
    }

   
}
