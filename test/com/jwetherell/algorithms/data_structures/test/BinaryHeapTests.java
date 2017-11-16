package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.BinaryHeap;
import com.jwetherell.algorithms.data_structures.BinaryHeapArray;
import com.jwetherell.algorithms.data_structures.BinaryHeapTree;
import com.jwetherell.algorithms.data_structures.test.common.HeapTest;
import com.jwetherell.algorithms.data_structures.test.common.JavaCollectionTest;
import com.jwetherell.algorithms.data_structures.test.common.Utils;
import com.jwetherell.algorithms.data_structures.test.common.Utils.TestData;

// TODO: Auto-generated Javadoc
/**
 * 
 */
public class BinaryHeapTests {

    /**
     * 
     */
    @Test
    public void testMinHeap() {
        TestData data = Utils.generateTestData(100);

        String aNameMin = "Min-Heap [array]";
        BinaryHeapArray<Integer> aHeapMin = new BinaryHeapArray<Integer>(BinaryHeap.Type.MIN);
        Collection<Integer> aCollectionMin = aHeapMin.toCollection();
        assertTrue(HeapTest.testHeap(BinaryHeap.Type.MIN, aHeapMin, Integer.class, aNameMin,  
                                     data.unsorted, data.sorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(aCollectionMin, Integer.class, aNameMin,
                                                     data.unsorted, data.sorted, data.invalid));

        String tNameMin = "Min-Heap [tree]";
        BinaryHeapTree<Integer> tHeapMin = new BinaryHeapTree<Integer>(BinaryHeap.Type.MIN);
        Collection<Integer> tCollectionMin = tHeapMin.toCollection();
        assertTrue(HeapTest.testHeap(BinaryHeap.Type.MIN, tHeapMin, Integer.class, tNameMin,
                                     data.unsorted, data.sorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(tCollectionMin, Integer.class, tNameMin,
                                                     data.unsorted, data.sorted, data.invalid));
    }

    /**
     * 
     */
    @Test
    public void testMaxHeap() {
        TestData data = Utils.generateTestData(1000);

        String aNameMax = "Max-Heap [array]";
        BinaryHeapArray<Integer> aHeapMax = new BinaryHeapArray<Integer>(BinaryHeap.Type.MAX);
        Collection<Integer> aCollectionMax = aHeapMax.toCollection();
        assertTrue(HeapTest.testHeap(BinaryHeap.Type.MAX, aHeapMax, Integer.class, aNameMax, 
                                     data.unsorted, data.sorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(aCollectionMax, Integer.class, aNameMax,
                                                 data.unsorted, data.sorted, data.invalid));

        String lNameMax = "Max-Heap [tree]";
        BinaryHeapTree<Integer> tHeapMax = new BinaryHeapTree<Integer>(BinaryHeap.Type.MAX);
        Collection<Integer> tCollectionMax = tHeapMax.toCollection();
        assertTrue(HeapTest.testHeap(BinaryHeap.Type.MAX, tHeapMax, Integer.class, lNameMax, 
                                     data.unsorted, data.sorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(tCollectionMax, Integer.class, lNameMax,
                                                 data.unsorted, data.sorted, data.invalid));
    }
}
