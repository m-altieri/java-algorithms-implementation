package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * In computer science, the longest common prefix array (LCP array) is an auxiliary
 * data structure to the suffix array. It stores the lengths of the longest common
 * prefixes (LCPs) between all pairs of consecutive suffixes in a sorted suffix array.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/LCP_array">LCP Array (Wikipedia)</a>
 * <br>
 * @author Jakub Szarawarski <kubaszarawarski@gmail.com>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class LCPArray<C extends CharSequence> {

    private static final char DEFAULT_END_SEQ_CHAR = '$';

    private final char endSeqChar;

    private SuffixArray suffixArray;
    private ArrayList<Integer> lcp;

    /**
     * 
     *
     * @param sequence 
     */
    public LCPArray(C sequence){
        this(sequence, DEFAULT_END_SEQ_CHAR);
    }

    /**
     * 
     *
     * @param sequence 
     * @param endChar 
     */
    public LCPArray(C sequence, char endChar) {
        endSeqChar = endChar;
        suffixArray = new SuffixArray(sequence, endSeqChar);
    }

    /**
     * 
     *
     * @return 
     */
    public ArrayList<Integer> getLCPArray() {
        if (lcp == null)
            LCPAlgorithm();
        return lcp;
    }

    private void LCPAlgorithm() {
        final ArrayList<Integer> LCPR = getLCPR();
        getLCPfromLCPR(LCPR);
    }

    private ArrayList<Integer> getLCPR() {
        final ArrayList<Integer> KMRArrayList = suffixArray.getKMRarray();
        final ArrayList<Integer> suffixArrayList = suffixArray.getSuffixArray();
        final String string = suffixArray.getString();
        final int length = KMRArrayList.size();
        final ArrayList<Integer> LCPR = new ArrayList<Integer>();             // helper array, LCP[i] = LCPR[suffixArray[i]]

        int startingValue = 0;
        for (int i=0; i<length; i++) {
            if(KMRArrayList.get(i).equals(0)) {
                LCPR.add(0);
                startingValue = 0;
            } else {
                int LCPRValue = startingValue;
                final int predecessor = suffixArrayList.get(KMRArrayList.get(i)-1);
                while (string.charAt(i+LCPRValue) == string.charAt(predecessor+LCPRValue))
                    LCPRValue++;
                LCPR.add(LCPRValue);
                startingValue = LCPRValue-1 > 0 ? LCPRValue-1 : 0;
            }
        }

        return LCPR;
    }

    private void getLCPfromLCPR(ArrayList<Integer> LCPR) {
        final ArrayList<Integer> suffixArrayList = suffixArray.getSuffixArray();
        final int length = suffixArrayList.size();

        lcp = new ArrayList<Integer>();
        lcp.add(null);                                                  //no value for LCP[0]
        for (int i=1; i<length; i++)
            lcp.add(LCPR.get(suffixArrayList.get(i)));
    }
}
