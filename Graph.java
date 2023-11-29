
public class Graph {
    // use Integer max value instead of infinity for path and cost
    int INFINITY = Integer.MAX_VALUE;

    private class Node {
      boolean known;
      int path;
      int cost;
    }


    int n = graph.size(); // Number of nodes, aka countries
    // make a matrix where x and y are each a country, identified by country code
    String[][] adjacencyMatrix = new String[x][y];
    // if two countries share a border, they are connected
    // set each spot between two countries to the distance between them
    // use the appropriate hash maps to extract country codes and distances
    // capdist: country1_country2, distance
    // state_name: countrycode_countryname, date
    // borders: countryname1_countryname2, 1 or 0 to represent shared border
    for (int i = 0; i < n; i++) {
      List<Integer> edges = graph.get(i);
      int distance = 
      for (int j : edges) {
        adjacencyMatrix[i][j] = distance;
    }
}



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