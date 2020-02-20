package Client;

import Client.Model.BaseUnit;
import Client.Model.Path;

public class PutInstruction
{
    //Output of class PutAI will be a list of this class
    private Path path;
    private BaseUnit baseUnit;

    public PutInstruction(Path path, BaseUnit baseUnit)
    {
        this.path = path;
        this.baseUnit = baseUnit;
    }

    public Path getPath()
    {
        return path;
    }

    public BaseUnit getBaseUnit()
    {
        return baseUnit;
    }
}
