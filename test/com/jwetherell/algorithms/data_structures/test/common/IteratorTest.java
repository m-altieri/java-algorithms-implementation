package com.jwetherell.algorithms.data_structures.test.common;

import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * The Class IteratorTest.
 */
public class IteratorTest {

    /**
     * Test iterator.
     *
     * @param <T> the generic type
     * @param iter the iter
     * @return true, if successful
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
