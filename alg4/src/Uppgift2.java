/**
 * DO THIS
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Uppgift2 {
    public static int V;

    /**
     * Reads in file and puts all new states in a st with an index
     * Enqueues the indexes
     * @param myFile is file to be read from
     * @param st     contains state and index
     * @param queue  contains all indexes put in the st
     * @throws FileNotFoundException
     */
    public static void readFileToST(File myFile, BinarySearchST<String,
            Integer> st, SortQueue<Integer> queue) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(myFile);
        V = 0;

        while (fileScanner.hasNext()){
            String a = fileScanner.next();
            if(!st.contains(a)){
                st.put(a, V);
                V++;
            }
            queue.enqueue(st.get(a));
        }
    }

    /**
     * Makes a graph from the queue of indexes
     * The pairs are added as edges into the graph
     * @param queue is queue in which we dequeue from
     * @return created graph
     */
    public static Graph makeGraph(SortQueue<Integer> queue){
        Graph G = new Graph(V);
        while (!queue.isEmpty()){
            G.addEdge(queue.dequeue(), queue.dequeue());
        }
        return G;
    }

    /**
     * Getter of the keys from known values from st
     * @param st where the key value pairs are stored
     * @return an array of keys
     */
    private static String[] flip(BinarySearchST<String, Integer> st){
        String[]keys = new String[st.size()];
        for(String name: st.keys()){
            keys[st.get(name)] = name;
        }
        return keys;
    }

    /**
     * Creates a string instance of the paths
     * @param integers are all paths from x to y
     * @param keys are names of the paths
     */
    private static void GToString(Iterable<Integer> integers, String[] keys){
        StringBuilder sb = new StringBuilder();
        for(Integer i : integers){
            sb.append(keys[i]);
            sb.append(" - ");
        }
        for(int n = 0; n < 3; n++) {
            sb.deleteCharAt(sb.length()-1);
        }
        System.out.println(sb);
    }

    /**
     * Creates graph and a dfs-path from the x given by the
     * viewer. The paths from x to y (also given by viewer) is
     * determined and then displayed to the viewer
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[]args) throws FileNotFoundException {
        File myFile = new File("C:\\\\Users\\\\matil\\\\IdeaProjects\\\\txt\\\\delstater.txt");
        BinarySearchST<String, Integer> st = new BinarySearchST();
        SortQueue<Integer> queue = new SortQueue<>();
        readFileToST(myFile, st, queue);
        Graph G = makeGraph(queue);
        String[] keys = flip(st);

        Scanner inScanner = new Scanner(System.in);
        System.out.println("Enter point X: ");
        String a = inScanner.next();
        int x = st.get(a);
        BreadthFirstPaths paths = new BreadthFirstPaths(G, x);
        System.out.println("Enter point Y: ");
        String b = inScanner.next();
        int y = st.get(b);
        if(paths.hasPathTo(y)){
            Iterable<Integer> integers = paths.pathTo(y);
            GToString(integers, keys);
        }
        else System.out.println("No way");
    }
}
