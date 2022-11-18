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

class Edges{
    int size;
    int[][] adjMatrix;
    public Edges(int sizeValue) {
        this.size = sizeValue;
        this.adjMatrix = new int[sizeValue][sizeValue];
    }
    public void addEdge(int fromId, int toId){
        this.adjMatrix[fromId][toId] = 1;
    }
}