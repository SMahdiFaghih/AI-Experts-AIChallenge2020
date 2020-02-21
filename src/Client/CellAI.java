package Client;

import Client.Model.*;

import java.util.List;

public class CellAI
{
    private static CellAI cellAI = new CellAI();
    private float[][] cellsAttackPossibility;
    private int safeCellsNum = 10;  //TODO set this field
    private float attackPossibilityUpperLimit = 0.8f;  //TODO set this field
    private float attackPossibilityLowerLimit = 0.6f;  //TODO set this field

    private CellAI()
    {

    }

    public static CellAI getInstance()
    {
        return cellAI;
    }

    public void setAttackPossibilityForPathCells(Map map)
    {
        cellsAttackPossibility = new float[map.getRowNum()][map.getColNum()];
        for (Path path : map.getPaths())
        {
            for (Cell cell : path.getCells())
            {
                cellsAttackPossibility[cell.getRow()][cell.getCol()] = 0.5f;
            }
        }
    }

    public void updateAttackPossibility(World world)
    {
        for (Unit unit : world.getFirstEnemy().getUnits())
        {
            if (unit.getTarget() != null)
            {
                int row = unit.getTargetCell().getRow();
                int column = unit.getTargetCell().getCol();
                cellsAttackPossibility[row][column] = cellsAttackPossibility[row][column] * 0.9f + 1f * 0.1f;
            }
        }
        for (Unit unit : world.getSecondEnemy().getUnits())
        {
            if (unit.getTarget() != null)
            {
                int row = unit.getTargetCell().getRow();
                int column = unit.getTargetCell().getCol();
                cellsAttackPossibility[row][column] = cellsAttackPossibility[row][column] * 0.9f + 1f * 0.1f;
            }
        }
        /*if (world.getFirstEnemy().isAlive() && world.getFirstEnemy().getKing().getTarget() != null)
        {
            world.getFirstEnemy().getKing().getTarget().getTargetCell().setAttackPossibility(world.getFirstEnemy().getKing().getTarget().getTargetCell().getAttackPossibility() * 1.1f);
        }
        if (world.getSecondEnemy().isAlive() && world.getSecondEnemy().getKing().getTarget() != null)
        {
            world.getSecondEnemy().getKing().getTargetCell().setAttackPossibility(world.getSecondEnemy().getKing().getTargetCell().getAttackPossibility() * 1.1f);
        }*/
        printCellConditions();
        setStrategy(world);
    }

    private void printCellConditions()
    {
        for (float[] cellRow : cellsAttackPossibility)
        {
            for (float cellAttackPossibility : cellRow)
            {
                if (cellAttackPossibility == 0)
                {
                    System.out.print("    ");
                }
                else
                {
                    System.out.printf("%.1f ", cellAttackPossibility);
                }
            }
            System.out.println();
        }
    }

    public void setStrategy(World world)
    {
        List<Path> paths = world.getMe().getPathsFromPlayer();
        paths.add(world.getMe().getPathToFriend());
        for (Path path : paths)
        {
            float attackPossibilitySum = 0;
            for (int i = 0; i < safeCellsNum; i++)
            {
                Cell cell = path.getCells().get(i);
                attackPossibilitySum += cellsAttackPossibility[cell.getRow()][cell.getCol()];
            }
            if (attackPossibilitySum > safeCellsNum * attackPossibilityUpperLimit)
            {
                path.setStrategy(PathStrategy.DEFENCE);
            }
            else if (attackPossibilitySum < safeCellsNum * attackPossibilityLowerLimit)
            {
                path.setStrategy(PathStrategy.ATTACK);
            }
            else
            {
                path.setStrategy(PathStrategy.DEFAULT);
            }
            System.out.println("Path " + path.getId() + "'s  Attack Possibility is " + attackPossibilitySum + " and its Strategy is " + path.getStrategy());
        }
    }

    public float[][] getCellsAttackPossibility()
    {
        return cellsAttackPossibility;
    }

    public int getSafeCellsNum()
    {
        return safeCellsNum;
    }

    public void setSafeCellsNum(int safeCellsNum)
    {
        this.safeCellsNum = safeCellsNum;
    }

    public float getAttackPossibilityUpperLimit()
    {
        return attackPossibilityUpperLimit;
    }

    public void setAttackPossibilityUpperLimit(float attackPossibilityUpperLimit)
    {
        this.attackPossibilityUpperLimit = attackPossibilityUpperLimit;
    }

    public float getAttackPossibilityLowerLimit()
    {
        return attackPossibilityLowerLimit;
    }

    public void setAttackPossibilityLowerLimit(float attackPossibilityLowerLimit)
    {
        this.attackPossibilityLowerLimit = attackPossibilityLowerLimit;
    }
}
