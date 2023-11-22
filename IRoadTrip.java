import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class IRoadTrip {

  String country1;
  String country2;
  HashMap<String, String> capDistMap = new HashMap<String, String>();
  HashMap<String, String> stateNameMap = new HashMap<String, String>();

  /*
   * Constructor
   * @param args command line arguments
   * files: borders.txt capdist.csv state_name.tsv.
   * Read the files and prepare to execute the implementation to respond to requests.
   * The implementation must halt on any failure here.
   */
  public IRoadTrip(String[] args) {
    try {
      // get file names
      String borders = args[0].trim();
      String capdist = args[1].trim();
      String state_name = args[2].trim();
      // check file names
      if (borders.equals("borders.txt") &&
          capdist.equals("capdist.csv") &&
          state_name.equals("state_name.tsv")) {
        // if file names are valid, read them in
        readFile(capdist, capDistMap, ",");
        readFile(state_name, stateNameMap, "\\t");
      } else {
        System.out.println("Invalid file names");
        System.out.println("borders: " + borders);
        System.out.println("capdist: " + capdist);
        System.out.println("state_name: " + state_name);
      }
    } catch (Exception e) {
      System.out.println("Error in IRoadTrip: " + e);
    }
  }

  // function to read in the files and use the putinHash function to put in hashmap
  public void readFile(
      String filename,
      HashMap<String, String> mapName,
      String divider) {
    String line;
    try {
      // read files using buffered reader
      BufferedReader reader = new BufferedReader(new FileReader(filename));
      // read in the first line for the keys
      line = reader.readLine();
      System.out.println(line);
      System.out.println(divider);

      String[] keys = line.split(divider);
      // put keys in an array
      while (line != null) {
        //  System.out.println(line);
        if (line != null) { // Check if the line is not null before processing
          putinHash(line, divider, keys, mapName);
        }
        // close the reader
        reader.close();
        // read in borders
        // get distance between two hashmaps
        // ignore border length
      }
    } catch (Exception e) {
      System.out.println("Error in readFile: " + e);
    }
  }

  // function to put specific lines in the specified hashmap
  public void putinHash(
      String line,
      String divider,
      String[] keys,
      HashMap<String, String> mapName) {
    String[] lineArray = line.split(divider);
    for (int i = 0; i < keys.length; i++) {
      String key = keys[i];
      String value = lineArray[i];
      mapName.put(key, value);
    }
  }

  public class Graph {
    private class Node {
      boolean known;
      int path;
      int cost;
    }

    // use Integer max value instead of infinity for path and cost
    int i = Integer.MAX_VALUE;

    // select least cost unknown vertex and make it known
    // update cost of unknown vertices adjacent to known vertex
    // repeat until all vertices are known

    // int v = least_cost_unknown_vertex();

    // known(v) = true;
    // for (int n = 0; n < v; n++) {
    // if (cost(n) > cost(v) + edge_weight(v, n)
    // update distance(n, v);
    // update path(n, v);
    // while v not null
    // }

    // Node[] nodes = 
    // adjacency list
    // adjacency matrix
    // create a public function called 
    // dijkstras (int v) {

  }

}

  /* This function provides the shortest path distance between the capitals of the two countries passed as arguments. 
    If either of the countries does not exist or if the countries do not share a land border, 
    this function must return a value of -1.
    */
  public int getDistance(String country1, String country2) {
    //
    return -1;
  }

  /*
   * This function determines and returns a path between the two countries passed as arguments.
   * This path must start in country1 and end in country 2.
   * If either of the countries does not exist or if there is no path between the countries,
   * the function returns an empty List . Each element of the list must be a String representing one2
   * step in a longer path in the format: starting_country --> ending_country (DISTANCE_IN_KM.), eg:
   * Thailand --> Burma (573 km.)
   */
  public List<String> findPath(String country1, String country2) {
    // create empty list to return
    List<String> path = new ArrayList<String>();
    // use bfs to find path if edge paths are all the same
    // if edge paths are not the same, use dijkstra's algorithm
    // set up a table and use directed, weighted graph 
    // use hashmap to store the table
    // use hashmap to store the graph

    return path;
  }

  public String formatPath(String country1, String country2, int distance) {
    String returnString = country1 + " --> " + country2 + " (" + distance + " km.)";
    return returnString;
  }

  /*
   * This function allows a user to interact with your implementation on the console.
   * Through this function, your implementation is required to receive and validate the names
   * of two countries from a user. The country names must be validated — i.e. your implementation must not
   * accept invalid names. Once two valid country names have been entered by the user, the implementation
   * must print the path between those countries if such a path exists. A sample user interaction is shown
   * below, with user input in bold italics:
   */
  public void acceptUserInput() {
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
    System.out.println("First country: " + country1);
    System.out.println("Second country: " + country2);
    // close scanner
    scanner.close();
  }

  public static void main(String[] args) {
    IRoadTrip roadTrip = new IRoadTrip(args);
    roadTrip.acceptUserInput();
  }
}

// just put country name
// only use country names, not the capital names

