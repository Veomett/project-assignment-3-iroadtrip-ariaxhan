
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Graph {
    /*
     * class variables
     * 
     * @param capDistMap: hashmap of country codes and distances between capitals
     * 
     * @param stateNameMap: hashmap of country codes and country names
     * 
     * @param bordersMap: hashmap of country codes and list of bordering countries
     * 
     * @param reverseStateMap: hashmap of country names and country codes
     * 
     * @param edgeCases: hashmap of country codes and edge cases
     * 
     * @param edgeCasesbyString: hashmap of country names and country codes
     * 
     * @param graph: hashmap of country codes and hashmaps of adjacent countries and
     * distances
     * 
     * @param keys: set of keys for the graph
     */
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
        this.graph = new HashMap<String, HashMap<String, Integer>>();
        this.keys = this.graph.keySet();
        this.capDistMap = capDistMap;
        this.stateNameMap = stateNameMap;
        this.bordersMap = bordersMap;
        this.reverseStateMap = reverseStateMap;
        this.edgeCases = edgeCases;
        this.edgeCasesbyString = edgeCasesbyString;
    }

    /*
     * function to return the distance between two countries
     * 
     * @param country1: string of country 1
     * 
     * @param country2: string of country 2
     * 
     * @return distance: integer of distance between countries
     */
    public int returnGraphDist(String country1, String country2) {
        // initialize distance
        int distance = 0;
        // get country code for country 1
        String countryCode = reverseStateMap.get(country1);
        // get hashmap of bordering countries
        HashMap<String, Integer> borderCountries = this.graph.get(countryCode);
        // get the keys for the hashmap
        Set<String> codeKeys = borderCountries.keySet();
        // loop over the keys
        for (String country : codeKeys) {
            // get the country code for the current country
            String country2Code = reverseStateMap.get(country2);
            if (country.equals(country2Code)) {
                // if the country is found, get the distance
                distance = borderCountries.get(country);
            }
        }
        return distance;
    }

    /*
     * function to build the graph from hashmaps
     * 
     * @param countryCode: set of country codes
     * 
     * @param borderingCountries: list of bordering countries
     * 
     * #param innerHashMap: hashmap of bordering countries and distances
     */
    public void buildGraph() {
        // Get all of the country codes from the state name hashmap
        Set<String> countryCode = stateNameMap.keySet();
        // Loop over the country codes
        for (String country : countryCode) {
            // Initialize variable for list of bordering countries
            List<String> borderingCountries = null;
            // get the list of bordering countries from the borders hashmap
            borderingCountries = bordersMap.get(country);
            // check if bordering countries exist
            if (borderingCountries == null) {
                // check if it is an edge case, using the code to check
                String countryasBorderString = checkEdgeCasebyCode(country, edgeCases);
                // if it is an edge case
                if (countryasBorderString != null) {
                    borderingCountries = bordersMap.get(countryasBorderString);
                } else {
                    // the country is not an edge case nor a valid country
                    continue;
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
                    // if it is null, check if it is an edge case
                    if (borderingCountryCode == null) {
                        borderingCountryCode = checkEdgeCasebyString(borderingCountry, edgeCasesbyString);
                    }
                    if (borderingCountryCode != null) {
                        String key = country + "_" + borderingCountryCode;
                        Integer distance = capDistMap.get(key);
                        if (distance != null) {
                            innerHashMap.put(borderingCountryCode, distance);
                            // put inner hashmap into the graph
                            graph.put(country, innerHashMap);
                        }
                    }
                }
            }
        }
    }

    /*
     * function to check if a country is an edge case when given the code
     */
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

    /*
     * function to check if a country is an edge case when given the string
     */

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

    // function to print the graph
    public void printGraph() {
        for (String country : graph.keySet()) {
            System.out.println(country + ": " + this.graph.get(country));
        }
    }

}
