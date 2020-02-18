package Client;

import java.util.ArrayList;
import java.util.List;

public class PutAI
{
    //in this class we'll use CellAI class and other parameters such as AP to specify where to put each unit and return array of putInstructions
    private static PutAI putAI = new PutAI();
    private static List<putInstruction> putInstructions;

    private PutAI()
    {

    }

    public List<putInstruction> calculatePutAI()
    {
        //TODO
        return putInstructions;
    }

    public static PutAI getInstance()
    {
        putInstructions = new ArrayList<>();
        return putAI;
    }
}
