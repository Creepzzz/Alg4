import java.io.File;
import java.util.NoSuchElementException;

public class Graph {
    private final int V;
    private int E;
    private Bag<Integer>[] adj;


    public Graph(int V){
        if(V < 0)throw new IllegalArgumentException();
        this.V = V;
        this.E = E;
        adj = (Bag<Integer>[]) new Bag[V];
        for(int v = 0; v < V; v++){
            adj[v] = new Bag<Integer>();
        }
    }

    public Graph(In in){
        if (in == null)throw new IllegalArgumentException();
        try{
            this.V = in.readInt();
            if(V < 0) throw new IllegalArgumentException();
            adj = (Bag<Integer>[]) new Bag[V];
            for(int v = 0; v < V; v++){
                adj[v] = new Bag<Integer>();
            }
            int E = in.readInt();
            if(E < 0) throw new IllegalArgumentException();
            for(int i = 0; i < E; i++){
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w);
            }

        }
        catch (NoSuchElementException e) {throw new IllegalArgumentException();}
    }

    public Graph(Graph G){
        this.V = G.V();
        this.E = G.E();
        if(V < 0) throw new IllegalArgumentException();
        adj = (Bag<Integer>[]) new Bag[V];
        for(int v = 0; v < V; v++){
            adj[v] = new Bag<Integer>();
        }
    }

    public int V(){
        return V;
    }
    public int E(){
        return E;
    }

    private void validateVertex(int v){
        if((v < 0)||(v >= V)) throw new IllegalArgumentException();
    }

    private void addEdge(int v, int w){
        validateVertex(v);
        validateVertex(w);
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }

    Iterable<Integer> adj(int v){
        validateVertex(v);
        return adj[v];
    }

    public int degree(int v){
        validateVertex(v);
        return adj[v].size();
    }
}
