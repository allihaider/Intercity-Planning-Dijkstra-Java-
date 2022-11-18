import java.util.ArrayList;
import java.util.HashMap;

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

class Graph{
    HashMap<Integer, Vertex> vertices;
    Edges edges;
    public Graph(HashMap<Integer, Vertex> verticesValues, Edges edgesValues) {
        this.vertices = verticesValues;
        this.edges = edgesValues;
    }

    public ArrayList<Integer> getVertexNeighbours(int vertexId){
        ArrayList<Integer> vertexNeighbours = new ArrayList<>();

        for(int i=0; i < edges.size; i++){
            if(edges.adjMatrix[vertexId][i] != 0){
                vertexNeighbours.add(i);
            }
        }
        return vertexNeighbours;
    }
}