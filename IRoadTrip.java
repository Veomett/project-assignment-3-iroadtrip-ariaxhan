import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class IRoadTrip {

  /*
   * node class for countries to be used in algorithm
   * 
   * @param countryCode: country code
   * 
   * @param distance: distance from source country
   */
  public class countryNode implements Comparable<countryNode> {
    String countryCode;
    int distance;

    public countryNode(String countryCode, int distance) {
      this.countryCode = countryCode;
      this.distance = distance;
    }

    public int compareTo(countryNode a) {
      return (distance - a.distance);
    }

  }

  /*
   * class variables
   * 
   * @param INFINITY: integer to represent infinity
   * 
   * @param country1: string of country 1
   * 
   * @param country2: string of country 2
   * 
   * @param stateNameMap: hashmap of country codes and country names
   * 
   * @param capDistMap: hashmap of country codes and distances between capitals
   * 
   * @param bordersMap: hashmap of country codes and list of bordering countries
   * 
   * @param reverseStateMap: hashmap of country names and country codes
   * 
   * @param edgeCases: hashmap of country codes and edge cases
   * 
   * @param edgeCasesbyString: hashmap of country names and country codes
   */
  private final int INFINITY = Integer.MAX_VALUE;
  String country1;
  String country2;
  HashMap<String, String> stateNameMap = new HashMap<String, String>();
  HashMap<String, Integer> capDistMap = new HashMap<String, Integer>();
  HashMap<String, List<String>> bordersMap = new HashMap<String, List<String>>();
  HashMap<String, String> reverseStateMap = new HashMap<String, String>();
  HashMap<String, String> edgeCases = new HashMap<String, String>();
  HashMap<String, String> edgeCasesbyString = new HashMap<String, String>();

  /*
   * Constructor
   * 
   * @param args command line arguments
   * files: borders.txt capdist.csv state_name.tsv.
   * Read the files and prepare to execute the implementation to respond to
   * requests.
   * The implementation must halt on any failure here.
   */
  public IRoadTrip(String[] args) {
    System.out.println("Loading constructor...");
    try {
      // get file names
      String state_name = args[0].trim();
      String capdist = args[1].trim();
      String borders = args[2].trim();
      // check file names
      if (state_name.equals("state_name.tsv") && capdist.equals("capdist.csv") && borders.equals("borders.txt")) {
        System.out.println("Filenames validated, starting reading...");
        // create object to read files
        readFiles files = new readFiles(
            stateNameMap,
            capDistMap,
            bordersMap,
            reverseStateMap,
            edgeCases,
            edgeCasesbyString);
      } else {
        System.out.println("Invalid file names");
        System.out.println("state_name: " + state_name);
        System.out.println("capdist: " + capdist);
        System.out.println("borders: " + borders);
        System.exit(0);
      }
    } catch (Exception e) {
      System.out.println("Error in IRoadTrip: " + e);
      // exit program
      System.exit(0);
    }
  }

  /*
   * This function provides the shortest path distance between the capitals of the
   * two countries passed as arguments.
   * If either of the countries does not exist or if the countries do not share a
   * land border,
   * this function must return a value of -1.
   */
  public int getDistance(String country1, String country2, Graph graph) {
    // initialize distance to be -1
    int distance = -1;
    // get both country names
    // get each country code from name
    try {
      distance = graph.returnGraphDist(country1, country2);
      // get distance from main graph
      return distance;
    } catch (Exception e) {
      System.out.println("Error in getDistance: " + e);
    }
    return distance;
  }

  /*
   * This function determines and returns a path between the two countries passed
   * as arguments.
   * This path must start in country1 and end in country 2.
   * If either of the countries does not exist or if there is no path between the
   * countries,
   * the function returns an empty List . Each element of the list must be a
   * String representing one2
   * step in a longer path in the format: starting_country --> ending_country
   * (DISTANCE_IN_KM.), eg:
   * Thailand --> Burma (573 km.)
   */
  public List<String> findPath(String country1, String country2, Graph graph) {
    // create empty list to return
    List<String> path = new ArrayList<String>();
    // get country codes from input
    String sourceCode = reverseStateMap.get(country1);
    String destinationCode = reverseStateMap.get(country2);
    // error handling
    if (sourceCode == null || destinationCode == null) {
      System.out.println("Country does not exist");
      // return empty list
      return path;
    }
    HashMap<String, String> predecessors = new HashMap<>(); // Map to store predecessors
    // create priority queue to store countries
    PriorityQueue<countryNode> pq = new PriorityQueue<>();
    // put all countries in the graph into the queue, initializing all at infinity
    Set<String> keys = graph.graph.keySet();
    // use keys to make comparable objects, adding to queue
    for (String key : keys) {
      countryNode country = new countryNode(key, INFINITY);
      pq.offer(country);
    }
    // update source country
    pq.offer(new countryNode(sourceCode, 0));

    // loop to the end of the queue
    while (!pq.isEmpty()) {
      // get the country entry
      countryNode fullCountryEntry = pq.poll();
      // get country code
      String countryEntry = fullCountryEntry.countryCode;
      // get distance of entry
      int curDist = fullCountryEntry.distance;

      // convert country string to code
      String toComp = reverseStateMap.get(country2);
      // compare entry code to destination code
      if (countryEntry.equals(toComp)) {
        System.out.println("Destination reached");
        break;
      }
      // get the hashmap of neighbors
      HashMap<String, Integer> neighbors = graph.graph.get(countryEntry);

      // loop through
      for (Map.Entry<String, Integer> entry : neighbors.entrySet()) {
        // update distances
        if (entry.getKey() != null && entry.getValue() != null) {
          // get neighbor name and distance
          String neighName = entry.getKey();
          int neighDist = entry.getValue();
          // calculate new distance
          int newDistance = curDist + neighDist;
          // compare distances
          if (newDistance < neighDist) {
            // add to predecessors
            predecessors.put(neighName, countryEntry);
            // update distance in queue
            pq.offer(new countryNode(neighName, newDistance));
          }
        }
      }
    }
    // run function to retrace steps and find path
    return reconstructPath(predecessors, sourceCode, destinationCode);
  }

  // Method to reconstruct the shortest path from source to destination
  // aka go backwards
  private List<String> reconstructPath(Map<String, String> predecessors, String source, String destination) {
    // create empty list to return
    LinkedList<String> path = new LinkedList<>();
    // step is the final destination
    String step = destination;
    // loop through predecessors
    while (!step.equals(source) && predecessors.containsKey(step)) {
      path.addFirst(stateNameMap.get(step)); // Convert code back to name
      step = predecessors.get(step);
    }
    // add source to path
    if (!path.isEmpty()) {
      path.addFirst(stateNameMap.get(source)); // Convert source code back to name
    }
    return path;
  }

  public String formatPath(HashMap<String, String> predecessors) {

    // String returnString = country1 + " --> " + country2 + " (" + distance + "
    // km.)";
    return "returnString";
  }

  /*
   * This function allows a user to interact with your implementation on the
   * console.
   * Through this function, your implementation is required to receive and
   * validate the names
   * of two countries from a user. The country names must be validated — i.e. your
   * implementation must not
   * accept invalid names. Once two valid country names have been entered by the
   * user, the implementation
   * must print the path between those countries if such a path exists. A sample
   * user interaction is shown
   * below, with user input in bold italics:
   */
  public String acceptUserInput() {
    // create scanner object
    Scanner scanner = new Scanner(System.in);
    // prompt user for input
    System.out.println(
        "Enter the name of the first country (type EXIT to quit):");
    // get first country name
    country1 = scanner.nextLine().trim();
    // check if user wants to exit
    if (country1.equals("EXIT")) {
      System.exit(0);
    }
    // prompt user for second country name
    System.out.println(
        "Enter the name of the second country (type EXIT to quit):");
    // get second country name
    country2 = scanner.nextLine().trim();
    // check if user wants to exit
    if (country2.equals("EXIT")) {
      System.exit(0);
    }
    // close scanner
    scanner.close();
    // return countries separated by underscore
    return country1 + "_" + country2;
  }

  public static void main(String[] args) {
    // create main object
    IRoadTrip roadTrip = new IRoadTrip(args);
    // get hashmaps
    HashMap<String, Integer> capDistMap = roadTrip.capDistMap;
    HashMap<String, String> stateNameMap = roadTrip.stateNameMap;
    HashMap<String, List<String>> bordersMap = roadTrip.bordersMap;
    HashMap<String, String> reverseStateMap = roadTrip.reverseStateMap;
    HashMap<String, String> edgeCases = roadTrip.edgeCases;
    HashMap<String, String> edgeCasesbyString = roadTrip.edgeCasesbyString;
    // build graph object
    Graph graph = new Graph(capDistMap, stateNameMap, bordersMap, reverseStateMap, edgeCases, edgeCasesbyString);
    // build graph
    graph.buildGraph();
    // get user input
    String inputCountries = roadTrip.acceptUserInput();
    // string split to get input countries
    String[] countries = inputCountries.split("_");
    String country1 = countries[0];
    String country2 = countries[1];
    // get path and distance
    List<String> path = roadTrip.findPath(country1, country2, graph);
    int distance = roadTrip.getDistance(country1, country2, graph);
    // print results
    for (String s : path) {
      System.out.println("country " + s);
    }
    System.out.println("distance between " + country1 + " and " + country2 + " is " + distance);
  }
}
