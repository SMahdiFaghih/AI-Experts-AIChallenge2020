package Client;

import Client.Model.*;

import java.util.List;

public class CellAI
{
    private static CellAI cellAI = new CellAI();
    private Cell[][] cells;
    private int safeCellsNum = 10;  //TODO set this field
    private float attackPossibilityUpperLimit;  //TODO set this field
    private float attackPossibilityLowerLimit;  //TODO set this field

    private CellAI()
    {

    }

    public static CellAI getInstance()
    {
        return cellAI;
    }

    public void setAttackPossibilityForPathCells(Map map)
    {
        setCells(map.getCells());
        for (Path path : map.getPaths())
        {
            for (Cell cell : path.getCells())
            {
                cell.setAttackPossibility(1f);
            }
        }
    }

    public void updateAttackPossibility(World world)
    {
        for (Unit unit : world.getFirstEnemy().getUnits())
        {
            if (unit.getTarget() != null)
            {
                unit.getTargetCell().setAttackPossibility(unit.getTargetCell().getAttackPossibility() * 1.1f);
            }
        }
        for (Unit unit : world.getSecondEnemy().getUnits())
        {
            if (unit.getTarget() != null)
            {
                unit.getTargetCell().setAttackPossibility(unit.getTargetCell().getAttackPossibility() * 1.1f);
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
        for (Cell[] cellRow : cells)
        {
            for (Cell cell : cellRow)
            {
                if (cell.getAttackPossibility() == 0)
                {
                    System.out.print("    ");
                }
                else
                {
                    System.out.printf("%.1f ", cell.getAttackPossibility());
                }
            }
            System.out.println();
        }
    }

    public void setStrategy(World world)
    {
        List<Path> paths = world.getMe().getPathsFromPlayer();
        for (Path path : paths)
        {
            int attackPossibilitySum = 0;
            for (int i = 0; i < safeCellsNum; i++)
            {
                Cell cell = path.getCells().get(i);
                attackPossibilitySum += cell.getAttackPossibility();
            }
            System.out.println(attackPossibilitySum);
            if (attackPossibilitySum > safeCellsNum * attackPossibilityUpperLimit)
            {
                path.setStrategy(PathStrategy.DEFEND);
            }
            else if (attackPossibilitySum < safeCellsNum * attackPossibilityLowerLimit)
            {
                path.setStrategy(PathStrategy.ATTACK);
            }
            else
            {
                path.setStrategy(PathStrategy.DEFAULT);
            }
        }
    }

    public Cell[][] getCells()
    {
        return cells;
    }

    public void setCells(Cell[][] cells)
    {
        this.cells = cells;
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
