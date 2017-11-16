package com.jwetherell.algorithms.graph.test;

import java.util.ArrayList;
import java.util.List;

import com.jwetherell.algorithms.data_structures.Graph;

public class TraversalTest {

    protected static final List<Graph.Vertex<Integer>>    vertices    = new ArrayList<Graph.Vertex<Integer>>();
    protected static final List<Graph.Edge<Integer>>      edges       = new ArrayList<Graph.Edge<Integer>>();

    protected static final Graph.Vertex<Integer>          v0          = new Graph.Vertex<Integer>(0);
    protected static final Graph.Vertex<Integer>          v1          = new Graph.Vertex<Integer>(1);
    protected static final Graph.Vertex<Integer>          v2          = new Graph.Vertex<Integer>(2);
    protected static final Graph.Vertex<Integer>          v3          = new Graph.Vertex<Integer>(3);

    static {
        vertices.add(v0);
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        edges.add(new Graph.Edge<Integer>(0, v0, v1));
        edges.add(new Graph.Edge<Integer>(0, v0, v2));
        edges.add(new Graph.Edge<Integer>(0, v1, v2));
        edges.add(new Graph.Edge<Integer>(0, v2, v0));
        edges.add(new Graph.Edge<Integer>(0, v2, v3));
        edges.add(new Graph.Edge<Integer>(0, v3, v3));
    }

    protected static final Graph<Integer>                 graph       = new Graph<Integer>(Graph.TYPE.DIRECTED, vertices, edges);

}
