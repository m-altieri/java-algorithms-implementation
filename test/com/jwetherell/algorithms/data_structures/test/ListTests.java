package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.ArrayList;
import com.jwetherell.algorithms.data_structures.DoublyLinkedList;
import com.jwetherell.algorithms.data_structures.SinglyLinkedList;
import com.jwetherell.algorithms.data_structures.test.common.JavaCollectionTest;
import com.jwetherell.algorithms.data_structures.test.common.ListTest;
import com.jwetherell.algorithms.data_structures.test.common.Utils;
import com.jwetherell.algorithms.data_structures.test.common.Utils.TestData;

// TODO: Auto-generated Javadoc
/**
 * 
 */
public class ListTests {

	private void addElementsTest(TestData data, ArrayList<Integer> aList, String aName, int where) {
		for (int i = 0; i < data.unsorted.length; i++) {
            Integer item = data.unsorted[i];
            boolean added = aList.add(where, item);
            if ((!aList.validate() || (aList.size() != i+1))) {
                System.err.println(aName+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,aList);
                assertTrue(false);
            }
            if ((!added || !aList.contains(item))) {
                System.err.println(aName+" YIKES!! " + item + " doesn't exists but has been added.");
                Utils.handleError(data,aList);
                assertTrue(false);
            }
        }

        boolean contains = aList.contains(data.invalid);
        boolean removed = aList.remove(data.invalid);
        if (contains || removed) {
            System.err.println(aName+" invalidity check. contains=" + contains + " removed=" + removed);
            Utils.handleError(data.invalid,aList);
            assertTrue(false);
        }

        int size = aList.size();
        for (int i = 0; i < size; i++) {
            Integer item = data.unsorted[i];
            removed = aList.remove(item);
            if ((!aList.validate() || (aList.size() != data.unsorted.length-(i+1)))) {
                System.err.println(aName+" YIKES!! " + item + " caused a size mismatch.");
                Utils.handleError(data,aList);
                assertTrue(false);
            }
            if ((!removed || aList.contains(item))) {
                System.err.println(aName+" YIKES!! " + item + " still exists but it has been remove.");
                Utils.handleError(data,aList);
                assertTrue(false);
            }
        }
	}
	
    /**
     * 
     */
    @Test
    public void testArrayList() {
        TestData data = Utils.generateTestData(1000);

        String aName = "List [array]";
        ArrayList<Integer> aList = new ArrayList<Integer>();
        Collection<Integer> aCollection = aList.toCollection();

        assertTrue(ListTest.testList(aList, aName, 
                                     data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(aCollection, Integer.class, aName,
                                                     data.unsorted, data.sorted, data.invalid));

        // Try some array list specific tests
        // Adding new element at the first spot
        addElementsTest(data, aList, aName, 0);
        
        // Adding new element at the middle spot
        int idx = (int) Math.floor(aList.size() / 2);
        addElementsTest(data, aList, aName, idx);
    }

    /**
     * 
     */
    @Test
    public void testSinglyLinkedList() {
        TestData data = Utils.generateTestData(1000);

        String lName = "List [Singlylinked]";
        SinglyLinkedList<Integer> lList = new SinglyLinkedList<Integer>();
        Collection<Integer> lCollection = lList.toCollection();

        assertTrue(ListTest.testList(lList, lName,
                                     data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(lCollection, Integer.class, lName,
                                                      data.unsorted, data.sorted, data.invalid));
    }

    /**
     * 
     */
    @Test
    public void testDoublyLinkedList() {
        TestData data = Utils.generateTestData(1000);

        String lName = "List [Doublylinked]";
        DoublyLinkedList<Integer> lList = new DoublyLinkedList<Integer>();
        Collection<Integer> lCollection = lList.toCollection();

        assertTrue(ListTest.testList(lList, lName,
                                     data.unsorted, data.invalid));
        assertTrue(JavaCollectionTest.testCollection(lCollection, Integer.class, lName,
                                                      data.unsorted, data.sorted, data.invalid));
    }
}
