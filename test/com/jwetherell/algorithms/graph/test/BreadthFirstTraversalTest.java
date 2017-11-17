package com.jwetherell.algorithms.graph.test;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.Graph;
import com.jwetherell.algorithms.graph.BreadthFirstTraversal;

import junit.framework.Assert;

// TODO: Auto-generated Javadoc
/**
 * The Class BreadthFirstTraversalTest.
 */
public class BreadthFirstTraversalTest extends TraversalTest {

    private static final byte[][]                       adjacencyMatrix = new byte[4][4];
    static {
        // v0
        adjacencyMatrix[0][1] = 1;
        adjacencyMatrix[0][2] = 1;
        // v1
        adjacencyMatrix[1][2] = 1;
        // v2
        adjacencyMatrix[2][0] = 1;
        adjacencyMatrix[2][3] = 1;
        // v3
        adjacencyMatrix[3][3] = 1;
    }

    /**
     * Test 0.
     */
    @Test
    public void test0() {
        final int[] result = BreadthFirstTraversal.breadthFirstTraversal(4, adjacencyMatrix, 2);
        Assert.assertTrue(result[0]==2);
        Assert.assertTrue(result[1]==0);
        Assert.assertTrue(result[2]==3);
        Assert.assertTrue(result[3]==1);
    }

    /**
     * Test 1.
     */
    @Test
    public void test1() {
        final int[] result = BreadthFirstTraversal.breadthFirstTraversal(4, adjacencyMatrix, 0);
        Assert.assertTrue(result[0]==0);
        Assert.assertTrue(result[1]==1);
        Assert.assertTrue(result[2]==2);
        Assert.assertTrue(result[3]==3);
    }

    /**
     * Test 2.
     */
    @Test
    public void test2() {
        final Graph.Vertex<Integer>[] result = BreadthFirstTraversal.breadthFirstTraversal(graph, v2);
        Assert.assertTrue(result[0].getValue()==2);
        Assert.assertTrue(result[1].getValue()==0);
        Assert.assertTrue(result[2].getValue()==3);
        Assert.assertTrue(result[3].getValue()==1);
    }

    /**
     * Test 3.
     */
    @Test
    public void test3() {
        final Graph.Vertex<Integer>[] result = BreadthFirstTraversal.breadthFirstTraversal(graph, v0);
        Assert.assertTrue(result[0].getValue()==0);
        Assert.assertTrue(result[1].getValue()==1);
        Assert.assertTrue(result[2].getValue()==2);
        Assert.assertTrue(result[3].getValue()==3);
    }
}

