package Client;

import Client.Model.BaseUnit;
import Client.Model.World;

import java.util.ArrayList;
import java.util.List;

public class PutAI
{
    //in this class we'll use CellAI class and other parameters such as AP to specify where to put each unit and return array of putInstructions
    private static PutAI putAI = new PutAI();
    private static List<PutInstruction> putInstructions;

    private PutAI()
    {

    }

    public List<PutInstruction> calculatePutAI(World world)
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
