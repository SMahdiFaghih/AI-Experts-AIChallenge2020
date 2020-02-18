package Client;

import Client.Model.*;

public class CellAI
{
    private static CellAI cellAI = new CellAI();
    private Cell[][] cells;
    private Map map;

    private CellAI()
    {
        map = Map.getMap();
        cells = map.getCells();
    }

    public static CellAI getInstance()
    {
        return cellAI;
    }

    public void updateDensity()
    {

    }

    public void updateAttackPossibility(Cell cell)
    {

    }

    public void updateStrategy()
    {

    }

    public Cell[][] getCells()
    {
        return cells;
    }
}
