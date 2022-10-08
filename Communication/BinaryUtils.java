package Communication;

public class BinaryUtils
{
    /**
     *
     * @param n The number to square.
     * @return The number squared.
     */
    public static int pow2(int n)
    {
        return (int) Math.pow(2,n);
    }

    /**
     *
     * @param k Numeral to check number of bits.
     * @return Number of bits.
     */
    public static int numBits(int k)
    {
        if(k==0)
            return 0;

        return Integer.toBinaryString(k).length();
    }

    public static int appendBit(int k, int bit) { return (k<<1) + bit; }


    /**
     * Performs binary division of a and b.
     * @param a numerator
     * @param b denominator
     * @return An array of the quotient and the remainder.
     */
    public static int[] xor(int a, int b)
    {

        if(b == 0)
            throw new IllegalArgumentException("Cannot divide by zero.");

        int q = 0; // Quotient
        int r = 0; // Remainder
        int temp = 0; // Temp variable, Used for B.
        int xorSum = a;
        int bit = 0;
        int diff = numBits(a)-numBits(b);

        if(diff >= 0)
            q = appendBit(q, 1);
        else
            return new int[]{0,a};
        /* Loop iterates until no more numbers can be picked. */
        for(int i = 0; i <= diff; )
        {
            /* B vector is shifted diff-i times to the left*/
            temp = (b<<(diff-i));

            /* XORsum has to be shifted i times to the left. */
            if(i != 0)
              xorSum = (xorSum<<(diff-i));

            /* XOR operation is done between shifted b and a
            * and the result is then shifted back.*/
            xorSum = (temp^xorSum)>>(diff-i);

            /* The loop will decide how many bits has to be "brought down" from a to make
             * temp have the same number of bits again as b. */;
            for(int j = (diff-1)-i; ; j--)
            {
                /* i follows j */
                i+=1;
                /* If no more bits can be collected from a then we have a remainder. */
                if(j == -1)
                {
                    r = xorSum;
                    break;
                }
                /* The bit taken from a is appended to the result, either 1 or 0. */
                bit = (pow2(j)&a)>>j;
                xorSum = appendBit(xorSum, bit); // <-?

                /* Achieved same number of bits as the divisor, append 1 to the quotient. */
                if(numBits(xorSum) == numBits(b))
                {
                    q = appendBit(q, 1);
                    break;
                }

                    q = appendBit(q,0);

            }

        }

        return new int[]{q, r};

    };

    public static int deg(int k)
    {

        return numBits(k) - 1;
    }



}
