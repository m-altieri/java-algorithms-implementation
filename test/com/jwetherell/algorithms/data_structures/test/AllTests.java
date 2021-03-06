package com.jwetherell.algorithms.data_structures.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

// TODO: Auto-generated Javadoc
/**
 * The Class AllTests.
 */
@RunWith(Suite.class)
@SuiteClasses( {
                AVLTreeTests.class,
                BinarySearchTreeTests.class,
                BTreeTests.class,
                DisjointSetTests.class,
                FenwickTreeTests.class,
                GraphTests.class,
                HashArrayMappedTreeTests.class,
                HashMapTests.class,
                IntervalTreeTests.class,
                KdTreeTests.class,
                ListTests.class,
                MatrixTests.class,
                QuadTreeTests.class,
                QueueTests.class,
                RedBlackTreeTests.class,
                SkipListMapTests.class,
                SkipListTests.class,
                SplayTreeTests.class,
                StackTests.class,
                SuffixTreeTests.class,
                SuffixTrieTests.class,
                TreapTests.class,
                TreeMapTests.class,
                TrieTests.class,
                TrieMapTests.class
               }
             )

public class AllTests {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {   
        JUnitCore core = new JUnitCore(); 
        core.run(AllTests.class); 
    }
}
