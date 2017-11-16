package com.jwetherell.algorithms.mathematics.timing;

import java.text.DecimalFormat;

import com.jwetherell.algorithms.mathematics.Division;
import com.jwetherell.algorithms.mathematics.Multiplication;

// TODO: Auto-generated Javadoc
/**
 * 
 */
public class MathematicsTiming {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.######");

    private static void multiplication(int a, int b) {
        System.out.println("Multiplication using a loop.");
        long before = System.nanoTime();
        long result = Multiplication.multiplyUsingLoop(a, b);
        long after = System.nanoTime();
        long check = Multiplication.multiplication(a, b);
        if (result != check)
            System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
        System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
        System.gc();

        System.out.println("Multiplication using recursion.");
        before = System.nanoTime();
        result = Multiplication.multiplyUsingRecursion(a, b);
        after = System.nanoTime();
        check = Multiplication.multiplication(a, b);
        if (result != check)
            System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
        System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
        System.gc();

        System.out.println("Multiplication using shifts.");
        before = System.nanoTime();
        result = Multiplication.multiplyUsingShift(a, b);
        after = System.nanoTime();
        check = Multiplication.multiplication(a, b);
        if (result != check)
            System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
        System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
        System.gc();

        System.out.println("Multiplication using logs.");
        before = System.nanoTime();
        result = Multiplication.multiplyUsingLogs(a, b);
        after = System.nanoTime();
        check = Multiplication.multiplication(a, b);
        if (result != check)
            System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
        System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
        System.gc();

        System.out.println("Multiplication using FFT.");
        before = System.nanoTime();
        result = Long.parseLong(Multiplication.multiplyUsingFFT(String.valueOf(a), String.valueOf(b)));
        after = System.nanoTime();
        check = Multiplication.multiplication(a, b);
        if (result != check)
            System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
        System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
        System.gc();

        System.out.println("Multiplication using loop, String input.");
        before = System.nanoTime();
        result = Long.parseLong(Multiplication.multiplyUsingLoopWithStringInput(String.valueOf(a), String.valueOf(b)));
        after = System.nanoTime();
        check = Multiplication.multiplication(a, b);
        if (result != check)
            System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
        System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
        System.gc();

        System.out.println("Multiplication using loop, Integer input.");
        before = System.nanoTime();
        result = Multiplication.multiplyUsingLoopWithIntegerInput(a, b);
        after = System.nanoTime();
        check = Multiplication.multiplication(a, b);
        if (result != check)
            System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
        System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
        System.out.println();
        System.gc();
    }
    
    private static void division(int a, int b) {
    	System.out.println("Division using a loop.");
        long before = System.nanoTime();
        long result = Division.divisionUsingLoop(a, b);
        long after = System.nanoTime();
        long check = Division.division(a, b);
        if (result != check)
            System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
        System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
        System.gc();

        System.out.println("Division using recursion.");
        before = System.nanoTime();
        result = Division.divisionUsingRecursion(a, b);
        after = System.nanoTime();
        check = Division.division(a, b);
        if (result != check)
            System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
        System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
        System.gc();

        System.out.println("Division using shifts.");
        before = System.nanoTime();
        result = Division.divisionUsingShift(a, b);
        after = System.nanoTime();
        check = Division.division(a, b);
        if (result != check)
            System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
        System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
        System.gc();

        System.out.println("Division using logs.");
        before = System.nanoTime();
        result = Division.divisionUsingLogs(a, b);
        after = System.nanoTime();
        check = Division.division(a, b);
        if (result != check)
            System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
        System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
        System.gc();

        System.out.println("Division using multiplication.");
        before = System.nanoTime();
        result = Division.divisionUsingMultiplication(a, b);
        after = System.nanoTime();
        check = Division.division(a, b);
        if (result != check)
            System.out.println("ERROR with a=" + a + " b=" + b + " result=" + result + " check=" + check);
        System.out.println("Computed in " + FORMAT.format(after - before) + " ns");
        System.out.println();
        System.gc();
    }
    
    /**
     * 
     *
     * @param args 
     */
    public static void main(String[] args) {
        
        // Multiplication Positive * Positive
    	int a = 1200;
    	int b = 1400;
        multiplication(a, b);
        
        // Multiplication Negative * Positive
        a = -7400;
        b = 6200;
        multiplication(a, b);    
       
        // Multiplication Positive * Negative
        a = 8400;
        b = -2900;
        multiplication(a, b);
        
        // Multiplication Negative * Negative
        a = -9200;
        b = -3700;
        multiplication(a, b);
        
        // Division Positive / Positive
        a = 9;
        b = 3;
        division(a, b);
        
        // Division Negative / Positive
        a = -54;
        b = 6;
        division(a, b);
        
        // Division Positive / Negative
        a = 98;
        b = -7;
        division(a, b);
        
        // Division Negative / Negative
        a = -568;
        b = -15;
        division(a, b);
    }
}
