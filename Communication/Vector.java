package Communication;

public class Vector
{
    int num;
    int exp;
    public Vector(int num)
    {
        this.num = num;
        this.exp = Integer.toBinaryString(num).length();
    }


    /**
     * Method returns true whether the bit is set, otherwise false.
     * Additonally it is possible to flip the bit.
     * @param k bit to check
     * @param flip True to invert bit, false if no change.
     * @return true if bit is a one, otherwise false.
     */
    private boolean poke(int k, boolean flip)
    {
        int sum = 0;
        int temp = 0;

        if(k > exp || k < 0)
            throw new IndexOutOfBoundsException("Bit " + k + " is not within range.");

        for(int i = exp; i >= 0; i--)
        {
            temp = (int) Math.pow(2,i);
            /* If sum + 2^i does not override the number then this bit is set,
            * If k equals this exponent then the bit we wanted to check is set.*/
            if (sum + temp <= num)
            {
                if (k == i)
                {
                    if (flip)
                        this.num -= temp;
                    return true;
                }

                sum += temp;
                continue;
            }

            /* If this condition is true, then the bit we ant to check is not set. */
            if (k == i)
            {
                if(flip)
                    this.num += temp;

                return false;
            }

        }
        return false;
    }

    /**
     *
     * @param k The bit to flip.
     */
    public void flip(int k)
    {
        poke(k, true);
    }

    @Override
    public String toString()
    {
        return Integer.toBinaryString(num);
    }


    /**
     *
     * @return Vectors bit sequence as a decimal.
     */
    public int getNumber()
    {
        return this.num;
    }

    /**
     *
     * @return Length of this bit sequence.
     */
    public int length(){ return exp; }

}


