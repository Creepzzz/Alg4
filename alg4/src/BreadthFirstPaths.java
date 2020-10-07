/*
 * Author: Matilda Qvick 001105-0606
 * Generated: 30/9 - 2020
 * Last updated: 7/10 - 2020
 * Solves: Finds paths between two vertices by using breadth
 *         first search.
 *         Class also contains an iterable that iterates through
 *         the paths, placing the vertices in a stack of paths.
 * How to use: Used from Uppgift 2 and 3
 */
public class BreadthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;

    /**
     * Constructor for breadth first path
     * @param G is graph to search in
     * @param s is source vertex
     */
    public BreadthFirstPaths(Graph G, int s){
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        validateVertex(s);
        bfs(G, s);
        assert check(G, s);
    }

    /**
     * Searches graph from a source.
     * Marks visited edges and adds edge association in edgeTo array
     * Iterates over all adjacency to source
     * @param G is graph searched
     * @param s is source vertex
     */
    private void bfs(Graph G, int s){
        SortQueue<Integer> queue = new SortQueue<>();
        for(int v = 0; v < G.V(); v++){
            distTo[v] = Integer.MAX_VALUE;
        }
        distTo[s] = 0;
        marked[s] = true;
        queue.enqueue(s);
        while (!queue.isEmpty()){
            int v = queue.dequeue();
            for(int w: G.adj(v)){
                if(!marked[w]){
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] +1;
                    marked[w] = true;
                    queue.enqueue(w);
                }
            }
        }
    }

    /**
     *
     * @param v is vertex currently investigated
     * @return true if the path has a path to this particular vertex
     */
    public boolean hasPathTo(int v){
        validateVertex(v);
        return marked[v];
    }

    /**
     * Iterates over all the paths of particular vertex
     * continuously adding them to a stack
     * @param v is vertex in which path is to be found from source
     * @return a stack of linked vertices to the source
     */
    public Iterable<Integer> pathTo(int v){
        validateVertex(v);
        if(!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<>();
        int x;
        for(x = v; distTo[x] != 0; x = edgeTo[x]){
            path.push(x);
        }
        path.push(x);
        return path;
    }

    /**
     * Checks if the given vertex is within the limits of the graph
     * @param v is vertex to validate
     */
    private void validateVertex(int v){
        int V = marked.length;
        if ((v < 0) || (v >= V)) throw new IllegalArgumentException();
    }

    /**
     * Tests following:
     * -That the distance to the source from the source is zero
     * -That all adjacency's has a path between them and that the
     *  distance between these are exactly one
     * -That two edges satisfies v = edgeTo[w] satisfies distTo[w] = distTo[v] + 1
     * @param G is graph in which search is made in
     * @param s is source vertex
     * @return true if all the above mentioned tests pass
     */
    private boolean check(Graph G, int s){
        if(distTo[s] != 0) {
            System.out.println("Distance to source isn't zero");
            return false;
        }
        for(int v = 0; v < G.V(); v++){
            for(int w : G.adj(v)){
                if(hasPathTo(v) != hasPathTo(w)){
                    System.out.println("Edge " + v + " - " + w);
                    System.out.println("hasPathTo(" + v + ") = " + hasPathTo(v));
                    System.out.println("hasPathTo(" + w + ") = " + hasPathTo(w));
                    return false;
                }
                if(hasPathTo(v) && (distTo[w] > distTo[v] + 1)){
                    System.out.println("Edge " + v + " - " + w);
                    System.out.println("distTo[" + v + "] = " + distTo[v]);
                    System.out.println("distTo[" + w + "] = " + distTo[w]);
                    return false;
                }
            }
        }
        for(int w = 0; w < G.V(); w++){
            if(!hasPathTo(w) || w == s) continue;
            int v = edgeTo[w];
            if(distTo[w] != distTo[v] +1){
                System.out.println("Shortest path: " + v + " - " + w);
                System.out.println("distTo[" + v + "] = " + distTo[v]);
                System.out.println("distTo[" + w + "] = " + distTo[w]);
                return false;
            }
        }
        return true;
    }
}
