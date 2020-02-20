package Client;

import Client.Model.Path;

public enum PathStrategy
{
    DEFEND,
    ATTACK;

    private Path path;

    public void setPath(Path path)
    {
        this.path = path;
    }

    public Path getPath()
    {
        return path;
    }
}
