package Client;

import Client.Model.*;

import java.util.*;
import java.util.Map;

/**
 * You must put your code in this class {@link AI}.
 * This class has {@link #pick}, to choose units before the start of the game;
 * {@link #turn}, to do orders while game is running;
 * {@link #end}, to process after the end of the game;
 */

public class AI
{
    private int rows;
    private int cols;
    private Random random = new Random();
    private Path pathToFriend;
    private List<Path> allPathsFromMeToEnemies = new ArrayList<>();
    private Path pathToEnemy;
    private boolean deadEnemy = false;

    public void pick(World world)
    {
        System.out.println("pick started");

        Client.Model.Map map = world.getMap();
        rows = map.getRowNum();
        cols = map.getColNum();

        System.out.println(map.getPaths().size());

        System.out.println(world.getMe().getKing().getCenter().getRow() + " " + world.getMe().getKing().getCenter().getCol());
        System.out.println(world.getFriend().getKing().getCenter().getRow() + " " + world.getFriend().getKing().getCenter().getCol());
        System.out.println(world.getFirstEnemy().getKing().getCenter().getRow() + " " + world.getFirstEnemy().getKing().getCenter().getCol());
        System.out.println(world.getSecondEnemy().getKing().getCenter().getRow() + " " + world.getSecondEnemy().getKing().getCenter().getCol());

        for (Path path : map.getPaths())
        {
            System.out.println("From " + path.getCells().get(0).getRow() + " " + path.getCells().get(0).getCol());
            System.out.println("To " + path.getCells().get(path.getCells().size() - 1).getRow() + " " + path.getCells().get(path.getCells().size() - 1).getCol());
        }
        System.out.println("\n\n");

        List<BaseUnit> allBaseUnits = world.getAllBaseUnits();
        BaseUnit.sort(allBaseUnits);

        world.chooseHand(allBaseUnits);

        for (Path path : world.getMe().getPathsFromPlayer())
        {
            if (path.getCells().contains(world.getMe().getKing().getCenter()))
            {
                allPathsFromMeToEnemies.add(path);
                System.out.println("From " + path.getCells().get(0).getRow() + " " + path.getCells().get(0).getCol());
                System.out.println("To " + path.getCells().get(path.getCells().size() - 1).getRow() + " " + path.getCells().get(path.getCells().size() - 1).getCol());

            }
        }
        pathToEnemy = FindShortestPath.getShortestPath(allPathsFromMeToEnemies); //also this method sorts allPathsToEnemies
    }

    public void turn(World world)
    {
        System.out.println("turn started: " + world.getCurrentTurn());

        Player myself = world.getMe();
        List<BaseUnit> myHand = myself.getHand();
        BaseUnit.sort(myHand);

        /*if (!deadEnemy)
        {
            if (!world.getFirstEnemy().isAlive())
            {
                allPathsToEnemies.removeAll(world.getFirstEnemy().getPathsFromPlayer());
                pathToEnemy = FindShortestPath.getShortestPath(allPathsToEnemies);
                deadEnemy = true;
            }
            else if (!world.getSecondEnemy().isAlive())
            {
                allPathsToEnemies.removeAll(world.getSecondEnemy().getPathsFromPlayer());
                pathToEnemy = FindShortestPath.getShortestPath(allPathsToEnemies);
                deadEnemy = true;
            }
        }*/

        List<PutInstruction> putInstructions = PutAI.getInstance().calculatePutAI(world);
        for (PutInstruction putInstruction : putInstructions)
        {
            world.putUnit(putInstruction.getBaseUnit(), putInstruction.getPath());
        }

        // this code tries to cast the received spell
        Spell receivedSpell = world.getReceivedSpell();
        if (receivedSpell != null)
        {
            if (receivedSpell.isAreaSpell())
            {
                switch (receivedSpell.getTarget())
                {
                    case ENEMY:
                        List<Unit> enemyUnits = world.getFirstEnemy().getUnits();
                        if (!enemyUnits.isEmpty())
                        {
                            world.castAreaSpell(enemyUnits.get(0).getCell(), receivedSpell);
                        }
                        break;
                    case ALLIED:
                        List<Unit> friendUnits = world.getFriend().getUnits();
                        if (!friendUnits.isEmpty())
                        {
                            world.castAreaSpell(friendUnits.get(0).getCell(), receivedSpell);
                        }
                        break;
                    case SELF:
                        List<Unit> myUnits = myself.getUnits();
                        if (!myUnits.isEmpty())
                        {
                            world.castAreaSpell(myUnits.get(0).getCell(), receivedSpell);
                        }
                }
            }
            else
            {
                List<Unit> myUnits = myself.getUnits();
                if (!myUnits.isEmpty())
                {
                    Unit unit = myUnits.get(0);
                    List<Path> myPaths = myself.getPathsFromPlayer();
                    Path path = myPaths.get(random.nextInt(myPaths.size()));
                    int size = path.getCells().size();
                    Cell cell = path.getCells().get((size + 1) / 2);

                    world.castUnitSpell(unit, path, cell, receivedSpell);
                }
            }
        }

        // this code tries to upgrade damage of first unit. in case there's no damage token, it tries to upgrade range
        if (myself.getUnits().size() != 0)
        {
            Unit unit = myself.getUnits().get(0);
            world.upgradeUnitDamage(unit);
            world.upgradeUnitRange(unit);
        }
    }

    public void end(World world, Map<Integer, Integer> scores)
    {
        System.out.println("end started");
        System.out.println("My score: " + scores.get(world.getMe().getPlayerId()));
    }
}