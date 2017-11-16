package com.jwetherell.algorithms.mathematics.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.jwetherell.algorithms.mathematics.Coprimes;

// TODO: Auto-generated Javadoc
/**
 * 
 */
public class CoprimesTest {

    /**
     * 
     */
    @Test
    public void totientTest(){
        final List<Long> args = Arrays.asList(1L, 17L, 96L, 498L, 4182119424L);
        final List<Long> expected = Arrays.asList(1L, 16L, 32L, 164L, 1194891264L);

        for(int i = 0; i < args.size(); i++)
            assertEquals(expected.get(i), Coprimes.getNumberOfCoprimes(args.get(i)));
    }
}
