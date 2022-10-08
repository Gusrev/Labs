import Communication.*;
public class MessageDecoder extends Decoder
{
        private int detectedErrors;
    public MessageDecoder(int gPoly)
    {
        super(gPoly);
    }
    @Override
    public void ErrorDetected(Vector decodedMsg) {
        detectedErrors++;
    }

    @Override
    public void NoErrorDetected(Vector decodedMsg) {

    }

    public int getDetectedErrors()
    {
        return this.detectedErrors;
    }

    public void setDetectedErrors(int k)
    {
        this.detectedErrors = k;
    }
}
