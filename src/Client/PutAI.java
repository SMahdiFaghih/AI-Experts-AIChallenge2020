package Client;

import Client.Model.BaseUnit;
import Client.Model.Path;
import Client.Model.World;

import java.util.ArrayList;
import java.util.List;

public class PutAI
{
    //in this class we'll use CellAI class and other parameters such as AP to specify where to put each unit and return array of putInstructions
    private static PutAI putAI = new PutAI();
    private static List<PutInstruction> putInstructions;
    private int[] baseUnitsAttackPriority = {0, 7, 4, 8, 1, 2, 3, 6, 5};
    private int[] baseUnitsDefencePriority = {7, 1, 2, 4, 5, 0, 6, 8, 3};
    private int[] baseUnitsDefaultPriority = {6, 1, 5, 2, 0, 7, 3, 8, 4};
    private int defencePutLimit = 3;
    private int attackPutLimit = 1;
    private int defaultPutLimit = 2;
    private List<Path> paths;

    private PutAI()
    {

    }

    public void calculatePaths(World world)
    {
        paths = world.getMe().getPathsFromPlayer();
        paths.add(world.getMe().getPathToFriend());
    }

    public List<PutInstruction> calculatePutAI(World world)
    {
        //Collections.reverse(paths);
        List<BaseUnit> myHand = world.getMe().getHand();
        for (Path path : paths)
        {
            if (path.getStrategy() == PathStrategy.ATTACK)
            {
                addPutInstruction(myHand, baseUnitsAttackPriority, path, world, attackPutLimit);
            }
            else if (path.getStrategy() == PathStrategy.DEFENCE)
            {
                addPutInstruction(myHand, baseUnitsDefencePriority, path, world, defencePutLimit);
            }
            else if (path.getStrategy() == PathStrategy.DEFAULT)
            {
                addPutInstruction(myHand, baseUnitsDefaultPriority, path, world, defaultPutLimit);
            }
        }
        return putInstructions;
    }

    private void addPutInstruction(List<BaseUnit> hand, int[] baseUnitIDs, Path path, World world, int putLimit)
    {
        int currentAP = world.getMe().getAp();
        int unitsPutThisTurn = 0;
        for (int baseUnitID : baseUnitIDs)
        {
            BaseUnit baseUnit = BaseUnit.findBaseUnit(hand, baseUnitID);
            if (unitsPutThisTurn < putLimit && baseUnit != null && currentAP >= baseUnit.getAp())
            {
                PutInstruction putInstruction = new PutInstruction(path, baseUnit);
                putInstructions.add(putInstruction);
                currentAP -= baseUnit.getAp();
                unitsPutThisTurn ++;
            }
        }
    }

    public static PutAI getInstance()
    {
        putInstructions = new ArrayList<>();
        return putAI;
    }

    public int getAttackPutLimit()
    {
        return attackPutLimit;
    }

    public int getDefencePutLimit()
    {
        return defencePutLimit;
    }

    public int getDefaultPutLimit()
    {
        return defaultPutLimit;
    }
}
