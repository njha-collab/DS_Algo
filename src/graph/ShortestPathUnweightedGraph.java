package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringJoiner;

/**
 * Shortest Path In Unweighted Graph
 * 
 * https://youtu.be/09_LlHjoEiY?t=2208
 * https://www.geeksforgeeks.org/shortest-path-unweighted-graph/
 * 
 * @author Narendra Jha, njha.sde@gmail.com
 * 
 */

public class ShortestPathUnweightedGraph extends Graph {

    public ShortestPathUnweightedGraph(int numberOfVertices) {
        super(numberOfVertices);
    }
    
    // shortest path from source to destination
    public void shortestPath(int s, int d) {
        if (s == d) {
            System.out.println("Source and destination are the same");
            return;
        }
        
        int[] prev = new int[numberOfVertices];
        Arrays.fill(prev, -1);
        
        if (bfs(s, d, prev))
            reconstructPath(d, prev);
        else
            System.out.println("There is no path from given source to destination");
    }

    private boolean bfs(int s, int d, int[] prev) {
        boolean[] visited = new boolean[numberOfVertices];
        Queue<Integer> q = new LinkedList<Integer>();
        
        q.add(s);
        visited[s] = true;
        while (!q.isEmpty()) {
            int v = q.poll();
            for (Edge edge : adjList.get(v)) {
                int adjNode = edge.to;
                if (!visited[adjNode]) {
                    q.add(adjNode);
                    visited[adjNode] = true;
                    prev[adjNode] = v;
                    
                    // Stop BFS, and return true when destination node is found
                    if (adjNode == d)
                        return true;;
                }
            }
        }
        
        return false;
    }
    
    private void reconstructPath(int d, int[] prev) {
        List<Integer> path = new ArrayList<Integer>();
        for (int v = d; v != -1; v = prev[v])
            path.add(v);
        Collections.reverse(path);
        
        StringJoiner joiner = new StringJoiner("->");
        for (Integer item : path) {
            joiner.add(item.toString());
        }
        System.out.println("Shortest path length is: " + path.size());
        System.out.println("Shortest path is: " + joiner);
    }

    public static void main(String[] args) {
        ShortestPathUnweightedGraph graph = new ShortestPathUnweightedGraph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        
        graph.shortestPath(0, 2);
        // Shortest path length is: 3
        // Shortest path is: 0->1->2
        
        graph.shortestPath(0, 3);
        // There is no path from given source to destination
    }
}
