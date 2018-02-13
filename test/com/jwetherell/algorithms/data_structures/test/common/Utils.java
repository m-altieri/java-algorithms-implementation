package com.jwetherell.algorithms.data_structures.test.common;

import java.util.Arrays;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class Utils.
 */
public class Utils {

    /**
     * Parses the T.
     *
     * @param <T> the generic type
     * @param value the value
     * @param type the type
     * @return the t
     */
    public static final <T> T parseT(final Integer value, final Class<T> type) {
        T returnValue = null;

        if (type == null) {
            throw new NullPointerException("Type can not be null");
        } else if(Integer.class.equals(type)) {
            returnValue = type.cast(value);
        } else if(String.class.equals(type)) {
            returnValue = type.cast(String.valueOf(value));
        } else {
            throw new IllegalArgumentException("Unsupported type " + type.getName());
        }
        return returnValue;
    }

    /**
     * Handle error.
     *
     * @param obj the obj
     */
    public static void handleError(Object obj) {
        System.err.println("Object={" + System.getProperty("line.separator") + obj.toString()+ System.getProperty("line.separator") + "}");
        throw new RuntimeException("Error in test.");
    }

    /**
     * Handle error.
     *
     * @param data the data
     * @param obj the obj
     */
    public static void handleError(Object data, Object obj) {
        System.err.println("Data={"+data+"}");
        System.err.println("Object={" + System.getProperty("line.separator") + obj.toString() + System.getProperty("line.separator") + "}");
        throw new RuntimeException("Error in test.");
    }

    /**
     * Handle error.
     *
     * @param data the data
     * @param obj the obj
     */
    public static void handleError(Object[] data, Object obj) {
        System.err.println("Data={");
        for (Object o : data)
            System.err.print(o.toString()+", ");
        System.err.println(System.getProperty("line.separator")+"}");
        System.err.println("Object={" + System.getProperty("line.separator")+obj.toString()+System.getProperty("line.separator") + "}");
        throw new RuntimeException("Error in test.");
    }

    private static final Random RANDOM = new Random();

    /**
     * Test data.
     *
     * @param integers the integers
     * @return the test data
     */
    public static TestData testData(int... integers) {
        TestData data = new TestData(integers.length);

        StringBuilder builder = new StringBuilder();
        data.unsorted = new Integer[integers.length];
        java.util.Set<Integer> set = new java.util.HashSet<Integer>();
        builder.append("Array=");
        for (int i = 0; i < integers.length; i++) {
            Integer j = integers[i];
            data.unsorted[i] = j;
            if (i != integers.length-1) 
                builder.append(j).append(',');
        }
        set.clear();
        set = null;
        builder.append(System.getProperty("line.separator"));
        data.string = builder.toString();

        data.sorted = Arrays.copyOf(data.unsorted, data.unsorted.length);
        Arrays.sort(data.sorted);

        return data;
    }

    /**
     * Generate test data.
     *
     * @param data_size the data size
     * @return the test data
     */
    public static TestData generateTestData(int data_size) {
        TestData data = new TestData(data_size);

        StringBuilder builder = new StringBuilder();
        data.unsorted = new Integer[data_size];
        java.util.Set<Integer> set = new java.util.HashSet<Integer>();
        builder.append("Array=");
        for (int i = 0; i < data_size; i++) {
            Integer j = RANDOM.nextInt(data.random_size);
            // Make sure there are no duplicates
            boolean found = true;
            while (found) {
                if (set.contains(j)) {
                    j = RANDOM.nextInt(data.random_size);
                } else {
                    data.unsorted[i] = j;
                    set.add(j);
                    found = false;
                }
            }
            data.unsorted[i] = j;
            if (i != data_size-1) 
                builder.append(j).append(',');
        }
        set.clear();
        set = null;
        builder.append(System.getProperty("line.separator"));
        data.string = builder.toString();

        data.sorted = Arrays.copyOf(data.unsorted, data.unsorted.length);
        Arrays.sort(data.sorted);

        return data;
    }

    /**
     * The Class TestData.
     */
    public static class TestData {

        /**
         * The random size.
         */
        public int random_size = 0;
        
        /**
         * The invalid.
         */
        public Integer invalid = 0;
        
        /**
         * The unsorted.
         */
        public Integer[] unsorted = null;
        
        /**
         * The sorted.
         */
        public Integer[] sorted = null;
        
        /**
         * The string.
         */
        public String string = null;

        /**
         * Instantiates a new test data.
         *
         * @param size the size
         */
        public TestData(int size) {
            this.random_size = 1000 * size;
            this.invalid = random_size + 10;
        }

        /**
         * Instantiates a new test data.
         *
         * @param _unsorted the unsorted
         */
        public TestData(Integer[] _unsorted) {
            this(_unsorted.length);
            unsorted = _unsorted;
            sorted = unsorted.clone();
            Arrays.sort(sorted);
            setString(unsorted);
        }

        private static final String setString(Integer[] _unsorted) {
            StringBuilder builder = new StringBuilder();
            builder.append("Array=");
            for (int i=0; i < _unsorted.length; i++) {
                Integer d = _unsorted[i];
                if (i != _unsorted.length-1) builder.append(d).append(',');
            }
            builder.append(System.getProperty("line.separator"));
            return builder.toString();
        }
    }
}
