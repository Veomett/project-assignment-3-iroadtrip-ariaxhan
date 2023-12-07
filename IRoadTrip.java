import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class IRoadTrip {
  // list to hold all countries
  public List<String> totalCountryList = new ArrayList<String>();

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
        System.out.println("Files read, printing...");
        //
        // print out hashmaps for testing
        // System.out.println("stateNameMap: " + stateNameMap);
        // System.out.println("capDistMap: " + capDistMap);
        // System.out.println("bordersMap: " + bordersMap);
        // System.out.println("reverseStateMap: " + reverseStateMap);
        // System.out.println("edgeCases: " + edgeCases);
        // System.out.println("edgeCasesbyString: " + edgeCasesbyString);
        System.out.println("Printing complete...");
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
    path = graph.dijkstra(country1, country2, graph);
    // for (String s : path) {
    // int distance = roadTrip.getDistance(s, country2, graph);

    // }
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

    // print countries for testing
    // System.out.println("First country: " + country1);
    // System.out.println("Second country: " + country2);
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
