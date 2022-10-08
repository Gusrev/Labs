package Communication;

public abstract class Encoder {

    int gPoly;

    public Encoder(int gPoly)
    {
        this.gPoly = gPoly;
    }

    public Vector encode(Vector msg)
    {
        return new Vector(calculateCrc(msg.getNumber()));
    }

    /**
     *
     * @param bits Sequence of bits.
     * @return The crc(remainder) given by the specific generator polynomial.
     */
    public int calculateCrc(int bits)
    {
        /* x^r msg  +  x^r msg mod g */
        int num = (bits << BinaryUtils.deg(gPoly));
        return num + BinaryUtils.xor(num,gPoly)[1];
    }
}
