
public class Graph {
    // use Integer max value instead of infinity for path and cost
    int INFINITY = Integer.MAX_VALUE;

    private class Node {
      boolean known;
      int path;
      int cost;
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