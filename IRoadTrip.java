import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class IRoadTrip {
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
      }
    } catch (Exception e) {
      System.out.println("Error in IRoadTrip: " + e);
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

      /*
       * use country codes to get distance
       * distance = capDistMap.get((String) country1Code + "_" + (String)
       * country2Code);
       * System.out.println(distance);
       * System.out.println("distance: " + distance);
       */
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
    // set up a table and use directed, weighted graph
    System.out.println(country1 + country2 + graph);
    graph.graphCountryKeys = new LinkedList<String>();
    for (int a = 0; a < graph.graphCountryKeys.size(); a++) {
      System.out.println("key set" + a);
    }

    System.out.println("Dijkstra function entered");
    String sourceCode = reverseStateMap.get(country1);
    String destinationCode = reverseStateMap.get(country2);

    HashMap<String, Integer> distances = new HashMap<>();
    HashMap<String, String> predecessors = new HashMap<>(); // Map to store predecessors

    // make a list to store the visited countries
    // create priority queue to store countries
    PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Map.Entry.comparingByValue());
    // put all of the countries in the graph into the queue, initializing all at
    // infinity
    Set<String> keys = graph.graph.keySet();
    System.out.println("keys" + keys);
   // graphforEach(country -> distances.put(country, INFINITY));
    System.out.println("distances: " + distances);
    System.out.println("key set: " + graph.keys);
    distances.put(sourceCode, 0);

    pq.offer(new AbstractMap.SimpleEntry<>(sourceCode, 0));
    System.out.println("updated priority queue: " + pq);
    // loop to the end of the queue
    while (!pq.isEmpty()) {
      Map.Entry<String, Integer> countryEntry = pq.poll();
      // System.out.println("Processing country: " + currentCountryCode);
      String countryCode = countryEntry.getKey();
      int distance = countryEntry.getValue();

      if (countryCode.equals(country2)) {
        System.out.println("Destination reached");
        break;
      }

      int currentDistance = distances.get(countryCode);
      graph.graph.getOrDefault(countryCode, new HashMap<>()).forEach((neighbor, current) -> {
        System.out.println(neighbor);
        int newDistance = currentDistance + distance;
        if (newDistance < distances.get(neighbor)) {
          distances.put(neighbor, newDistance);
          // predecessors.put(neighbor, current);
          pq.offer(new AbstractMap.SimpleEntry<>(neighbor, newDistance));
        }
      });
    }

    return reconstructPath(predecessors, sourceCode, destinationCode);
  }

  // Method to reconstruct the shortest path from source to destination
  // aka go backwards
  private List<String> reconstructPath(Map<String, String> predecessors, String source, String destination) {
    LinkedList<String> path = new LinkedList<>();
    String step = destination;

    while (!step.equals(source) && predecessors.containsKey(step)) {
      path.addFirst(stateNameMap.get(step)); // Convert code back to name
      step = predecessors.get(step);
    }

    if (!path.isEmpty()) {
      path.addFirst(stateNameMap.get(source)); // Convert source code back to name
    }

    return path;
  }

  public String formatPath(String country1, String country2, int distance) {
    String returnString = country1 + " --> " + country2 + " (" + distance + " km.)";
    return returnString;
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
    System.out.println("Starting IRoadTrip...");
    IRoadTrip roadTrip = new IRoadTrip(args);
    // get hashmaps
    HashMap<String, Integer> capDistMap = roadTrip.capDistMap;
    HashMap<String, String> stateNameMap = roadTrip.stateNameMap;
    HashMap<String, List<String>> bordersMap = roadTrip.bordersMap;
    HashMap<String, String> reverseStateMap = roadTrip.reverseStateMap;
    HashMap<String, String> edgeCases = roadTrip.edgeCases;
    HashMap<String, String> edgeCasesbyString = roadTrip.edgeCasesbyString;

    System.out.println("Starting graph...");
    // build graph object
    Graph graph = new Graph(capDistMap, stateNameMap, bordersMap, reverseStateMap, edgeCases, edgeCasesbyString);
    System.out.println("Graph object made..");
    // build graph
    graph.buildGraph();
    // graph.printGraph();
    System.out.println("Graph built...");
    String inputCountries = roadTrip.acceptUserInput();
    // string split to get input countries
    String[] countries = inputCountries.split("_");
    String country1 = countries[0];
    String country2 = countries[1];

    List<String> path = roadTrip.findPath(country1, country2, graph);
    // print path
    for (String s : path) {
      System.out.println(s);
    }
  }
}
