/*
 * Author: Matilda Qvick 001105-0606
 * Generated: 30/9 - 2020
 * Last updated 6/10 - 2020
 * Solves: Created a graph  with vertices and edges. For adjacency's
 *         a bag is used to store the data. Adjacency's contain
 *         all edges to a specific vertex. An iterable has been
 *         implemented to iterate through the adjacency's.
 *         The class contains methods for both directed and undirected
 *         graphs. The only distinction being addEdge (for undirected)
 *         and addEdgeD (for directed).
 * Hos to use: Is used in classes Uppgift1, 2, 3
 */
public class Graph {
    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    /**
     * Constructor for graph with no edges and V vertices
     * Puts V nr of adjacency's in a bag
     * @param V is the number of vertices in graph
     */
    public Graph(int V){
        if(V < 0)throw new IllegalArgumentException();
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for(int v = 0; v < V; v++){
            adj[v] = new Bag<>();
        }
    }

    /**
     *
     * @return the number of vertices in graph
     */
    public int V(){
        return V;
    }

    /**
     * Checks if the vertex is within the limits of the graph
     * @param v is vertex to be validated
     */
    private void validateVertex(int v){
        if((v < 0)||(v >= V)) throw new IllegalArgumentException();
    }

    /**
     * Used in undirected graphs
     * Adds validated edges with adjacency's to one another
     * @param v is vertex one
     * @param w is vertex two
     */
    public void addEdge(int v, int w){
        validateVertex(v);
        validateVertex(w);
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }

    /**
     * Used in directed graphs
     * Adds validated vertices and adds one adjacency to the second edge
     * @param v
     * @param w
     */
    public void addEdgeD(int v, int w){
        validateVertex(v);
        validateVertex(w);
        E++;
        adj[v].add(w);
    }

    /**
     * Iterator that iterates through all adjacency's
     * @param v is given vertex in which we seek adjacency's of
     * @return the adjacency's of vertex v
     */
    Iterable<Integer> adj(int v){
        validateVertex(v);
        return adj[v];
    }
}
