package com.jwetherell.algorithms.data_structures.test.common;

import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * 
 */
public class IteratorTest {

    /**
     * 
     *
     * @param <T> 
     * @param iter 
     * @return 
     */
    public static <T extends Comparable<T>> boolean testIterator(Iterator<T> iter) {
        while (iter.hasNext()) {
            T item = iter.next();
            if (item==null) {
                System.err.println("Iterator failure.");
                return false;
            }
        }
        return true;
    }
}
