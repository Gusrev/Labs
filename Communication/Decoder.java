package Communication;

public abstract class Decoder
{
    int gPoly;

    /**
     *
     * @param gPoly Sets this decoder's generator polynomial.
     */
    public Decoder(int gPoly)
    {
        this.gPoly = gPoly;
    }

    /**
     * Decodes the message specified by the generator polynomial. Also
     * decides whether an error is detected or not. Then either
     * NoErrorDetected or ErrorDetected methods are invoked.
     * @param msg Message to receive
     */
    public void receive(Vector msg)
    {
        int res[] = BinaryUtils.xor(msg.getNumber(), gPoly);
        int remainder = res[1];

        int decodedBits = (msg.getNumber()>>(BinaryUtils.deg(gPoly)));
        Vector decodedMsg = new Vector(decodedBits) ;

        if(remainder == 0)
            NoErrorDetected(decodedMsg);
        else
            ErrorDetected(decodedMsg);
    }

    /**
     *
     * @return Generator polynomial.
     */
    public int getGPoly()
    {
       return this.gPoly;
    }

    /**
     *
     * @param gPoly Sets this decoder's generator polynomial.
     */
    public void setGPoly(int gPoly)
    {
        this.gPoly = gPoly;
    }

    /**
     * Invoked by the receive method
     * @param decodedMsg The received message on which an error has been detected.
     */
    public abstract void ErrorDetected(Vector decodedMsg);

    /**
     * Invoked by the receive method
     * @param decodedMsg The received message on which an error has not been detected.
     */
    public abstract void NoErrorDetected(Vector decodedMsg);

}
