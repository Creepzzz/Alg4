/**
 * DO THIS
 */
public class DepthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    /**
     * Constructor for depth first paths
     * @param G is graph in which search is to be made
     * @param s is source vertex
     */
    public DepthFirstPaths(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        validateVertex(s);
        dfs(G, s);
    }

    /**
     * Marks all adjacency's to v and draws an edge association to them
     * @param G is graph that is searched
     * @param v is vertex to search from
     */
    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    /**
     * Checks if the vertex and source vertex has a link
     * @param v is vertex to be validated
     * @return true if there is a path between source and vertex
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Iterates through all edges and puts them in a stack
     * @param v is vertex with wanted paths to
     * @return a stack of all the edges of v
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }

    /**
     * Checks if vertex is within the limits of the graph
     * @param v is vertex to validate
     */
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
}
