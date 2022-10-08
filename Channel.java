import Communication.*;

import java.util.ArrayList;
import java.util.Random;

public class Channel {

    MessageDecoder decoder;
    MessageEncoder encoder;

    public static final double PROBABILITY_BIT_ERROR = 0.001;

    int[] informationSequences;
    int gPoly;

    int sequences;
    Random random;
    private double p;
    private int introducedErrors;

    public static void println(String t)
    {
        System.out.println(t);
    }

    public static void main(String[] args)
    {
        /* x^3 + x^2 + 1 */
        int gPoly = 0b1101;

        Channel channel = new Channel(gPoly);

        int messages = 100000;
        channel.errorDetectionProbability(messages, 0.001);
        channel.errorDetectionProbability(messages, 0.005);
        channel.errorDetectionProbability(messages, 0.01);
        channel.errorDetectionProbability(messages, 0.2);
        channel.errorDetectionProbability(messages, 0.5);
        channel.errorDetectionProbability(messages, 1);


    }

    private void errorDetectionProbability(int n, double p)
    {
        println("---------------------------------------------------");
        /* Probability of a bit being flipped. */
        setP(p);
        /* Number of messages transmitted. */
        sendMessages(n);
        println("p:  " + p);
        println("Detected errors:  " + getDecoder().getDetectedErrors());
        println("Introduced errors:  " + getIntroducedErrors());
        println("Error detection probability:  " + (double)getDecoder().getDetectedErrors()/getIntroducedErrors());
        println("Theoretical bound: " + theoreticalBound(3,p));
    }

    /**
     *
     * @param d Hamming distance
     * @param p Probability of bit error
     * @return Theoretical bound of error detection
     */
    private double theoreticalBound(int d, double p)
    {
        double pe = 0;
        int n = 2*BinaryUtils.deg(gPoly) + 1;
        for(int i = 1; i <= d-1; ++i)
        {
           pe += binomi(n,i) * Math.pow(p,i) *  Math.pow((1 - p),(n-i));
        }

        return pe;
    }

    private long binomi(int n, int k) {
        if ((n == k) || (k == 0))
            return 1;
        else
            return binomi(n - 1, k) + binomi(n - 1, k - 1);
    }

    /**
     * Creates a channel containing a decoder and encoder.
     * @param gPoly Generator polynomial to be used for this channel.
     */
    public Channel(int gPoly)
    {
        this.gPoly = gPoly;
        decoder = new MessageDecoder(gPoly);
        encoder = new MessageEncoder(gPoly);

        /* Number of valid information sequences 2^N*/
        sequences = (int) Math.pow(BinaryUtils.deg(gPoly),2)*2;

        random = new Random();

        /* The probability of a bit being flipped. */
        p = PROBABILITY_BIT_ERROR;
        introducedErrors = 0;
    }

    public Channel(int gPoly, int p)
    {
        this(gPoly);
        this.p = p;
    }

    /**
     *
     * @param msg Message to send
     * @throws Exception If the given message does not match the valid sequence stated by the generator polynomial
     */
    public void sendMessage(Vector msg) throws Exception
    {

        if(!validSequence(msg.getNumber()))
            throw new Exception("Given message is not a valid information sequence. ");

        Vector encodedMsg = encoder.encode(msg);

        /*  Binary symmetric channel, iterates through each bits and with a given probability of p, the bit is flipped.
        * If atleast one bit is flipped an error is introduced in the message. */
        boolean error = false;
        //int[] randomIndex = randomIndexArray(encodedMsg.length());
        for(int i = 0; i < encodedMsg.length(); i++)
            if(Math.random() <= p)
            {
                encodedMsg.flip(i);
                if(!error) {
                    introducedErrors++;
                    error = true;
                }
            }

        decoder.receive(encodedMsg);


    }

    /**
     *
     * @param messages Simulates sending x amount of messages
     */
    public void sendMessages(int messages)
    {
        /* Resets Simulations detected errors. */
        this.getDecoder().setDetectedErrors(0);
       for(int i = 0; i < messages; i++)
       {
           /* Returns a random message within the valid number of sequences. */
           Vector msg = new Vector(random.nextInt(sequences));

           try
           {
               sendMessage(msg);
           }catch(Exception e)
           {
               println(e.getMessage());
           }

       }

    }


    /**
     *
     * @param k Checks whether this is a valid sequence
     * @return
     */
    public boolean validSequence(int k)
    {
        return k <= sequences;
    }

    /**
     *
     * @return Decoder used for this channel.
     */
    public MessageDecoder getDecoder()
    {
        return decoder;
    }

    /**
     *
     * @return Introduced errors obtained after a simulation.
     */
    public int getIntroducedErrors()
    {
        return introducedErrors;
    }

    /**
     *
     * @param length Length of the array
     * @return An array containing random numbers from 0 to length
     * @deprecated Used before when deciding which bit to flip during a simulation
     */
    private int[] randomIndexArray(int length)
    {
        int[] randomIndex = new int[length];
        ArrayList<Integer> list = new ArrayList<>();

        for(int i = 0; i < length; i++)
            list.add(i);

        int i = 0;
        while(!list.isEmpty())
        {
            int num = random.nextInt(list.size());
            randomIndex[i] = list.remove(num);
            i++;
        }

        return randomIndex;

    }

    /**
     *
     * @param p Sets the probability of having a bit error
     */
    public void setP(double p)
    {
        this.p = p;
    }




}
