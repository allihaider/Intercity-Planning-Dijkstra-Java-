public class PathFinder {
}

class Vertex{
    int id;
    int distance;
    int[] coords = new int[2];
    int parentId;
    int numPaths;
    boolean explored;

    public Vertex(int idValue, int distanceValue, int[] coordsValue, int parentIdValue, int numPathsValue, boolean exploredValue) {
        this.id = idValue;
        this.distance = distanceValue;
        this.coords = coordsValue;
        this.parentId = parentIdValue;
        this.numPaths = numPathsValue;
        this.explored = exploredValue;
    }
}