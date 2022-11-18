import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PathFinder {

    Graph graph = new Graph();

    ArrayList<Integer> getNumsFromLineElement(String[] lineElement){
        ArrayList<Integer> nums = new ArrayList<>();

        for(int i=0; i < lineElement.length; i++){
            String item = lineElement[i];

            if(!item.equals("")){
                nums.add(Integer.parseInt(item));
            }
        }
        return nums;
    }

    public void readInput(String fileName) throws IOException {
        ArrayList<String[]> lineElements = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = null;

        while ((line = br.readLine()) != null) {
            lineElements.add(line.strip().split(" "));
        }

        ArrayList<Integer> nums = getNumsFromLineElement(lineElements.get(0));
        int n = nums.get(0);
        int m = nums.get(1);

        for(int i=1; i < n+1; i++){
            nums = getNumsFromLineElement(lineElements.get(i));
            Vertex v = new Vertex(nums.get(0), new int[]{nums.get(1), nums.get(2)});
            graph.vertices.put(nums.get(0), v);
        }

        Edges graph_edges = new Edges(n);

        for(int j=n+2; j < n+2+m; j++){
            nums = getNumsFromLineElement(lineElements.get(j));
            graph_edges.addEdge(nums.get(0), nums.get(1));
            graph_edges.addEdge(nums.get(1), nums.get(0));
        }

        graph.edges = graph_edges;
    }

    public void initializeGraphVertexValues(int sourceID){
        for (int vertexID : graph.vertices.keySet()){
            if (graph.vertices.get(vertexID).id == sourceID){
                graph.vertices.get(vertexID).distance = 0;
                graph.vertices.get(vertexID).numPaths = 1;
            }
            else{
                graph.vertices.get(vertexID).distance = Double.POSITIVE_INFINITY;
                graph.vertices.get(vertexID).numPaths = 0;
            }
        }
    }
}

class Vertex{
    int id;
    double distance;
    int[] coords = new int[2];
    int parentId;
    int numPaths;
    boolean explored;

    public Vertex(int idValue, int[] coordsValue) {
        this.id = idValue;
        this.coords = coordsValue;
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
    HashMap<Integer, Vertex> vertices = new HashMap<>();
    Edges edges;
    /*public Graph(HashMap<Integer, Vertex> verticesValues, Edges edgesValues) {
        this.vertices = verticesValues;
        this.edges = edgesValues;
    }*/

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