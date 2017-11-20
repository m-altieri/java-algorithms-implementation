package com.jwetherell.algorithms.data_structures.timing;

import com.jwetherell.algorithms.data_structures.HashMap;
import com.jwetherell.algorithms.data_structures.timing.DataStructuresTiming.Testable;

/**
 * The Class MapsTiming.
 */
public class MapsTiming {

	static class TestHashMapChaining extends Testable {
        String name = "Chaining HashMap <Integer>";

        public String getName() {
            return name;
        }

        public boolean run(Integer[] unsorted, Integer[] sorted, String input) {
            this.input = input;
            HashMap<Integer,String> cHashMap = new HashMap<Integer,String>(HashMap.Type.CHAINING, DataStructuresTiming.ARRAY_SIZE/2);
            java.util.Map<Integer,String> jMap = cHashMap.toMap();
            if (!DataStructuresTiming.testJavaMap(jMap,Integer.class,String.class,name, unsorted, sorted, input)) return false;
            return true;
        }

    }
}
