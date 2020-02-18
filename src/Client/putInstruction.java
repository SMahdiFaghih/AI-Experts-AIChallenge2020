package Client;

import Client.Model.Path;
import Client.Model.Unit;

public class putInstruction
{
    //Output of class PutAI will be a list of this class
    private Path path;
    private Unit unit;

    public putInstruction(Path path, Unit unit)
    {
        this.path = path;
        this.unit = unit;
    }

    public Path getPath()
    {
        return path;
    }

    public Unit getUnit()
    {
        return unit;
    }
}
