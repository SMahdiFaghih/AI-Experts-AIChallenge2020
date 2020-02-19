package Client;

import Client.Model.*;

public class CellAI
{
    private static CellAI cellAI = new CellAI();
    private Cell[][] cells;

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
    }

    private void printCellConditions()
    {
        for (Cell[] cellRow : cells)
        {
            for (Cell cell : cellRow)
            {
                System.out.print(cell.getAttackPossibility() + " ");
            }
            System.out.println();
        }
    }

    public void updateStrategy()
    {

    }

    public Cell[][] getCells()
    {
        return cells;
    }

    public void setCells(Cell[][] cells)
    {
        this.cells = cells;
    }
}
