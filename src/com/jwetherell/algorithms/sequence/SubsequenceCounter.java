package com.jwetherell.algorithms.sequence;

// TODO: Auto-generated Javadoc
/**
 * Finds the number of times a string occurs as a subsequence in a text.
 * <p>
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 * @see <a href="https://www.geeksforgeeks.org/find-number-times-string-occurs-given-string">Substring occurs in String (GeeksForGeeks)</a>
 * <br>
 */
public class SubsequenceCounter {

    private static int[][] tbl = null;

    private SubsequenceCounter() { }

    /**
     * Finds the number of times a string occurs as a subsequence in a text.
     * 
     * @param sequence Text to find subsequence in.
     * @param subSequence subsequence to find in the text.
     * @return Number of times a string occurs as a subsequence in a text
     */
    public static int getCount(char[] sequence, char[] subSequence) {
        try {
            tbl = new int[sequence.length + 1][subSequence.length + 1];
    
            for (int row = 0; row < tbl.length; row++)
                for (int col = 0; col < tbl[row].length; col++)
                    tbl[row][col] = countMatches(sequence, subSequence, row, col);
    
            return tbl[sequence.length][subSequence.length];
        } finally {
            sequence = null;
            subSequence = null;
            tbl = null;
        }
    }

    private static int countMatches(char[] seq, char[] subseq, int seqDigitsLeft, int subseqDigitsLeft) {
        if (subseqDigitsLeft == 0)
            return 1;

        if (seqDigitsLeft == 0)
            return 0;

        final char currSeqDigit = seq[seq.length - seqDigitsLeft];
        final char currSubseqDigit = subseq[subseq.length - subseqDigitsLeft];

        int result = 0;
        if (currSeqDigit == currSubseqDigit)
            result += tbl[seqDigitsLeft - 1][subseqDigitsLeft - 1];
        result += tbl[seqDigitsLeft - 1][subseqDigitsLeft];

        return result;
    }
}
