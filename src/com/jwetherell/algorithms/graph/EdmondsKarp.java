package com.jwetherell.algorithms.graph;


import java.util.ArrayDeque;
import java.util.Queue;

// TODO: Auto-generated Javadoc
/**
 * In computer science, the Edmonds–Karp algorithm is an implementation of the Ford–Fulkerson method for 
 * computing the maximum flow in a flow network in O(V*E^2) time.
 * <p>
 *
 * @author Mateusz Cianciara <e.cianciara@gmail.com>
 * @author Justin Wetherell <phishman3579@gmail.com>
 * @see <a href="https://en.wikipedia.org/wiki/Edmonds%E2%80%93Karp_algorithm">Edmonds-Karp Algorithm (Wikipedia)</a>
 * <br>
 */
public class EdmondsKarp {

    private long[][] flow; //max flow beetween i and j verticles
    private long[][] capacity; // edge capacity
    private int[] parent; //parent
    private boolean[] visited; //just for checking if visited
    @SuppressWarnings("unused")
    private int n, m;

    /**
     * Instantiates a new edmonds karp.
     *
     * @param numOfVerticles the num of verticles
     * @param numOfEdges the num of edges
     */
    public EdmondsKarp(int numOfVerticles, int numOfEdges) {
        this.n = numOfVerticles;
        this.m = numOfEdges;
        this.flow = new long[n][n];
        this.capacity = new long[n][n];
        this.parent = new int[n];
        this.visited = new boolean[n];
    }

    /**
     * Adds the edge.
     *
     * @param from the from
     * @param to the to
     * @param theCapacity the the capacity
     */
    public void addEdge(int from, int to, long theCapacity) {
        assert theCapacity >= 0;
        this.capacity[from][to] += theCapacity;
    }

    /**
     * Get maximum flow.
     * 
     * @param s source
     * @param t target
     * @return maximum flow
     */
    public long getMaxFlow(int s, int t) {
        while (true) {
            final Queue<Integer> Q = new ArrayDeque<Integer>();
            Q.add(s);

            for (int i = 0; i < this.n; ++i)
                visited[i] = false;
            visited[s] = true;

            boolean check = false;
            int current;
            while (!Q.isEmpty()) {
                current = Q.peek();
                if (current == t) {
                    check = true;
                    break;
                }
                Q.remove();
                for (int i = 0; i < n; ++i) {
                    if (!visited[i] && capacity[current][i] > flow[current][i]) {
                        visited[i] = true;
                        Q.add(i);
                        parent[i] = current;
                    }
                }
            }
            if (check == false) 
                break;

            long temp = capacity[parent[t]][t] - flow[parent[t]][t];
            for (int i = t; i != s; i = parent[i])
                temp = Math.min(temp, (capacity[parent[i]][i] - flow[parent[i]][i]));

            for (int i = t; i != s; i = parent[i]) {
                flow[parent[i]][i] += temp;
                flow[i][parent[i]] -= temp;
            }
        }

        long result = 0;
        for (int i = 0; i < n; ++i)
            result += flow[s][i];
        return result;
    }
}
