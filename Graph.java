
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
    HashMap<String, HashMap<String, Integer>> graph = new HashMap<String, HashMap<String, Integer>>();

    public Graph() {
    }

    // method to build the graph from data (hashmaps)
    public void buildGraph(
            HashMap<String, Integer> capDistMap,
            HashMap<String, String> stateNameMap,
            HashMap<String, List<String>> bordersMap) {

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
            List<String> borderingCountries = bordersMap.get(countryString);
            // check if bordering countries exist
            if (borderingCountries == null) {
                // check to see if it is an edge case
                // checkEdgeCase(countryString);
            } else {
                // loop over bordering countries, get codes, and add to graph
                for (String borderingCountry : borderingCountries) {
                    // create inner hashmap with strings for each country and corresponding integers
                    HashMap<String, Integer> innerHashMap = new HashMap<String, Integer>();
                    // System.out.println( "bordering country name: " + borderingCountry);
                    // get country code for bordering country string
                    String borderingCountryCode = stateNameMap.get(borderingCountry);
                    // add each bordering country to the inner hashmap
                    // get distance
                    Integer distance = capDistMap.get(country + "_" + borderingCountryCode);
                    System.out.println(distance);
                    innerHashMap.put(borderingCountry, distance);
                    // System.out.println("country " + country + " bordering country " + borderingCountryCode);
                    // put inner hashmap into the graph
                    graph.put(country, innerHashMap);
                    //  System.out.println(country + ": " + innerHashMap);
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
}
        
        /* 
    public void checkEdgeCase(String countryString) {
        // check to see if it is an edge case
        // read in edge cases file
        // make reader
        BufferedReader bufReader = new BufferedReader(new FileReader("edgecases.tsv"));
        // make string and string array to hold values
        String strCurrentLine;
        String[] currentValues;
        // loop through file and set each line as the current line
        while ((strCurrentLine = bufReader.readLine()) != null) {
            // split current line based on tab
            currentValues = strCurrentLine.split("\t");
            // place each part of the line in the appropriate place
            String code = "";
            String stringBorders = "";
            String stringState = "";
            // loop through values to check if it is an edge case
            for (int p = 0; p < currentValues.length; p++) {
                // get 0, which is the country code
                if (p == 0) {
                    code = currentValues[p];
                } else if (p == 1) {
                    stringState = currentValues[p];
                } else if (p == 3) {
                    stringBorders = currentValues[p];
                } else {
                    continue;
                }
            }
            // compare countryString to the stringBorders string

            if (countryString.equals(stringState)) {
                // if they are equal, get the list of bordering countries
                // split the stringBorders string on commas
                String[] borderingCountriesArray = stringBorders.split(",");
                // add each bordering country to the list of bordering countries
                for (int i = 0; i < borderingCountriesArray.length; i++) {
                    borderingCountries.add(borderingCountriesArray[i]);
                }
            } else {
                System.out.println("No bordering countries for " + countryString);
                continue;
            }
        }
    }

*/




    