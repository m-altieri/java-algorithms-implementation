package com.jwetherell.algorithms.mathematics;

/**
 * Recursive function of exponentiation is just an implementation of definition.
 * <p>
 *
 * @author Bartlomiej Drozd <mail@bartlomiejdrozd.pl>
 * @author Justin Wetherell <phishman3579@gmail.com>
 * @see <a href="https://en.wikipedia.org/wiki/Exponentiation">Exponentiation (Wikipedia)</a>
 * <p>
 * Complexity  - O(N) where N is exponent.
 * <p>
 * Fast exponentiation's complexity is O(lg N)
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Exponentiation_by_squaring">Exponentiation by Squaring (Wikipedia)</a>
 * <br>
 * Modular exponentiation is similar.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Modular_exponentiation">Modular Exponentiation (Wikipedia)</a>
 * <p>
 * This implementation is the fast version of this algorithm with a complexity of O(lg N) also
 * <br>
 */
public class Exponentiation {

    /**
     * Recursive exponentiation.
     *
     * @param base the base
     * @param exponent the exponent
     * @return the int
     */
    public static int recursiveExponentiation(int base, int exponent) {
        if (exponent == 0)
            return 1;
        if (exponent == 1)
            return base;

        return recursiveExponentiation(base, exponent - 1) * base;
    }

    /**
     * Fast recursive exponentiation.
     *
     * @param base the base
     * @param exponent the exponent
     * @return the int
     */
    public static int fastRecursiveExponentiation(int base, int exponent) {
        if (exponent == 0)
            return 1;
        if (exponent == 1)
            return base;

        final int resultOnHalfExponent = fastRecursiveExponentiation(base, exponent / 2);
        if ((exponent % 2) == 0)
            return resultOnHalfExponent * resultOnHalfExponent;
        else
            return resultOnHalfExponent * resultOnHalfExponent * base;

    }

    /**
     * Fast recursive exponentiation modulo.
     *
     * @param base the base
     * @param exponent the exponent
     * @param mod the mod
     * @return the int
     */
    public static int fastRecursiveExponentiationModulo(int base, int exponent, int mod) {
        if (exponent == 0)
            return 1;
        if (exponent == 1)
            return base;

        final int resultOnHalfExponent = fastRecursiveExponentiationModulo(base, exponent / 2, mod);
        if ((exponent % 2) == 0)
            return (resultOnHalfExponent * resultOnHalfExponent) % mod;
        else
            return (((resultOnHalfExponent * resultOnHalfExponent) % mod) * base) % mod;
    }
}
