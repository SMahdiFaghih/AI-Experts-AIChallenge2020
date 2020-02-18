package Client;

import Client.Model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FindShortestPath
{
    public static Path getShortestPath(List<Path> paths)
    {
        paths.sort(new Comparator<Path>()
        {
            @Override
            public int compare(Path o1, Path o2)
            {
                if (o1.getCells().size() < o2.getCells().size())
                {
                    return -1;
                }
                else if (o1.getCells().size() > o2.getCells().size())
                {
                    return 1;
                }
                return 0;
            }
        });
        return paths.get(0);
    }
}
