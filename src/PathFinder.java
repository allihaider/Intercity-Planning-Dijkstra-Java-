import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PathFinder {

    Graph graph = new Graph();
    String filePath;

    public PathFinder(){

    }
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
        filePath = fileName;
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

    public double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2)* (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public double distToDest (int sourceId, int destId, int kValue) throws IOException{
        readInput(filePath);
        initializeGraphVertexValues(sourceId);
        MinHeap myHeap = new MinHeap(kValue);

        for (int vertexID : graph.vertices.keySet()){
            myHeap.add(graph.vertices.get(vertexID).id, graph.vertices.get(vertexID).distance);
        }

        while(myHeap.heapElements.size() != 0){
            double[] u = myHeap.extractMin();
            int uId = (int)u[0];
            double uDistance = u[1];

            if ((uId == destId) && (uDistance != Double.POSITIVE_INFINITY)){
                return uDistance;
            }

            graph.vertices.get(uId).explored = true;
            HashMap<Integer, Integer> vertexID2HeapIndex = new HashMap<>();

            for(int i=0; i < myHeap.heapElements.size(); i++){
                double[] element = myHeap.heapElements.get(i);
                vertexID2HeapIndex.put((int)element[0], i);
            }

            ArrayList<Integer> uNeighbourIds = graph.getVertexNeighbours(uId);

            for(int j=0; j < uNeighbourIds.size(); j++){
                int neighbourId = uNeighbourIds.get(j);

                if(graph.vertices.get(neighbourId).explored == false){
                    double euclideanDistance = distance(graph.vertices.get(uId).coords[0], graph.vertices.get(uId).coords[1], graph.vertices.get(neighbourId).coords[0], graph.vertices.get(neighbourId).coords[1]);

                    if (graph.vertices.get(neighbourId).distance > graph.vertices.get(uId).distance + euclideanDistance){
                        graph.vertices.get(neighbourId).distance = graph.vertices.get(uId).distance + euclideanDistance;

                        int heapIndex = vertexID2HeapIndex.get(neighbourId);
                        myHeap.updateHeap(heapIndex, graph.vertices.get(neighbourId).distance);
                    }
                }
            }

        }
        return -1;
    }

    public int noOfShortestPaths(int sourceId, int destId, int kValue) throws IOException{
        readInput(filePath);
        initializeGraphVertexValues(sourceId);
        MinHeap myHeap = new MinHeap(kValue);

        for (int vertexID : graph.vertices.keySet()){
            myHeap.add(graph.vertices.get(vertexID).id, graph.vertices.get(vertexID).distance);
        }

        while(myHeap.heapElements.size() != 0){
            double[] u = myHeap.extractMin();
            int uId = (int)u[0];
            double uDistance = u[1];

            if (uId == destId){
                return graph.vertices.get(uId).numPaths;
            }

            graph.vertices.get(uId).explored = true;
            HashMap<Integer, Integer> vertexID2HeapIndex = new HashMap<>();

            for(int i=0; i < myHeap.heapElements.size(); i++){
                double[] element = myHeap.heapElements.get(i);
                vertexID2HeapIndex.put((int)element[0], i);
            }

            ArrayList<Integer> uNeighbourIds = graph.getVertexNeighbours(uId);

            for(int j=0; j < uNeighbourIds.size(); j++){
                int neighbourId = uNeighbourIds.get(j);

                if(graph.vertices.get(neighbourId).explored == false){
                    double euclideanDistance = distance(graph.vertices.get(uId).coords[0], graph.vertices.get(uId).coords[1], graph.vertices.get(neighbourId).coords[0], graph.vertices.get(neighbourId).coords[1]);

                    if (graph.vertices.get(neighbourId).distance > graph.vertices.get(uId).distance + euclideanDistance){
                        graph.vertices.get(neighbourId).distance = graph.vertices.get(uId).distance + euclideanDistance;
                        graph.vertices.get(neighbourId).numPaths = graph.vertices.get(uId).numPaths;

                        int heapIndex = vertexID2HeapIndex.get(neighbourId);
                        myHeap.updateHeap(heapIndex, graph.vertices.get(neighbourId).distance);
                    }

                    else if(graph.vertices.get(neighbourId).distance == graph.vertices.get(uId).distance + euclideanDistance){
                        graph.vertices.get(neighbourId).numPaths = graph.vertices.get(neighbourId).numPaths + graph.vertices.get(uId).numPaths;
                    }
                }
            }
        }
        return 0;
    }

    public ArrayList<Integer> fromSrcToDest(int sourceId, int destId, int kValue) throws IOException{
        readInput(filePath);
        initializeGraphVertexValues(sourceId);
        MinHeap myHeap = new MinHeap(kValue);

        for (int vertexID : graph.vertices.keySet()){
            myHeap.add(graph.vertices.get(vertexID).id, graph.vertices.get(vertexID).distance);
        }

        ArrayList<Integer> pathIds = new ArrayList<>();

        while(myHeap.heapElements.size() != 0){
            double[] u = myHeap.extractMin();
            int uId = (int)u[0];

            if (uId == destId){
                Vertex currentVertex = graph.vertices.get(uId);
                pathIds.add(0, uId);

                if (currentVertex.parentId == -1){
                    return null;
                }

                while (currentVertex.parentId != sourceId){
                    pathIds.add(0, currentVertex.parentId);
                    currentVertex = graph.vertices.get(currentVertex.parentId);
                }

                pathIds.add(0, sourceId);

                return pathIds;
            }

            graph.vertices.get(uId).explored = true;
            HashMap<Integer, Integer> vertexID2HeapIndex = new HashMap<>();

            for(int i=0; i < myHeap.heapElements.size(); i++){
                double[] element = myHeap.heapElements.get(i);
                vertexID2HeapIndex.put((int)element[0], i);
            }

            ArrayList<Integer> uNeighbourIds = graph.getVertexNeighbours(uId);

            for(int j=0; j < uNeighbourIds.size(); j++){
                int neighbourId = uNeighbourIds.get(j);

                if(graph.vertices.get(neighbourId).explored == false){
                    double euclideanDistance = distance(graph.vertices.get(uId).coords[0], graph.vertices.get(uId).coords[1], graph.vertices.get(neighbourId).coords[0], graph.vertices.get(neighbourId).coords[1]);
                    double euclideanDistanceVT = distance(graph.vertices.get(neighbourId).coords[0], graph.vertices.get(neighbourId).coords[1], graph.vertices.get(destId).coords[0], graph.vertices.get(destId).coords[1]);
                    double euclideanDistanceUT = distance(graph.vertices.get(uId).coords[0], graph.vertices.get(uId).coords[1], graph.vertices.get(destId).coords[0], graph.vertices.get(destId).coords[1]);

                    if (graph.vertices.get(neighbourId).distance > graph.vertices.get(uId).distance + euclideanDistance + euclideanDistanceVT - euclideanDistanceUT){
                        graph.vertices.get(neighbourId).distance = graph.vertices.get(uId).distance + euclideanDistance + euclideanDistanceVT - euclideanDistanceUT;
                        graph.vertices.get(neighbourId).parentId = uId;

                        int heapIndex = vertexID2HeapIndex.get(neighbourId);
                        myHeap.updateHeap(heapIndex, graph.vertices.get(neighbourId).distance);
                    }
                }
            }
        }
        return null;
    }
}

class Vertex{
    int id;
    double distance;
    int[] coords = new int[2];
    int parentId = -1;
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

class MinHeap {
    int k;
    ArrayList<double[]> heapElements = new ArrayList<>();

    public MinHeap(){
        this.k = 2;
    }

    public MinHeap(int kValue) {
        this.k = kValue;
    }

    public void add(int key, double value){
        heapElements.add(new double[]{key, value});
        heapifyUp(heapElements.size() - 1);
    }
    public int getParentIndex(int Index){
        if ((Index % k)== 0){
            return (int)((Index / k) - 1);
        }
        else{
            return (int)Math.floor(Index / k);
        }
    }

    public ArrayList<Integer> getChildrenIndices(int Index){
        ArrayList<Integer> childrenIndices = new ArrayList<Integer>();

        for(int i=(Index * k) + 1; i <= (Index + 1) * k; i++){
            childrenIndices.add(i);
        }

        return childrenIndices;
    }

    public void heapifyUp(int Index){
        if(Index == 0){
            return;
        }

        if(heapElements.get(Index)[1] > heapElements.get(getParentIndex(Index))[1]){
            return;
        }

        else if(heapElements.get(Index)[1] == heapElements.get(getParentIndex(Index))[1]){
            if(heapElements.get(Index)[0] > heapElements.get(getParentIndex(Index))[0]){
                return;
            }

            else{
                Collections.swap(heapElements, Index, getParentIndex(Index));
                heapifyUp(getParentIndex(Index));
            }
        }

        else{
            Collections.swap(heapElements, Index, getParentIndex(Index));
            heapifyUp(getParentIndex(Index));
        }
    }

    public ArrayList<Integer> getHeap(){
        ArrayList<Integer> heapElementKeys = new ArrayList<Integer>();

        for (int i=0; i<heapElements.size(); i++) {
            heapElementKeys.add((int)heapElements.get(i)[0]);
        }

        return heapElementKeys;
    }

    public double[] extractMin(){
        if (heapElements.size() == 0){
            return null;
        }

        Collections.swap(heapElements, 0, heapElements.size() - 1);
        double[] minElement = heapElements.get(heapElements.size() - 1);
        heapElements.remove(heapElements.size() - 1);
        heapifyDown(0);

        return minElement;
    }
    public void heapifyDown(int Index){
        ArrayList<Integer> childrenIndices = getChildrenIndices(Index);

        double minChild = -1;
        int minChildIndex = -1;

        if (childrenIndices.get(0) < heapElements.size()){
            minChild = heapElements.get(childrenIndices.get(0))[1];
            minChildIndex = childrenIndices.get(0);
        }

        for(int i=1; i < childrenIndices.size(); i++){
            int childIndex = childrenIndices.get(i);

            if(childIndex < heapElements.size()){
                if(heapElements.get(childIndex)[1] < minChild){
                    minChild = heapElements.get(childIndex)[1];
                    minChildIndex = childIndex;
                }

                else if((heapElements.get(childIndex)[1] == minChild) && (heapElements.get(childIndex)[0] < heapElements.get(minChildIndex)[0])){
                    minChild = heapElements.get(childIndex)[1];
                    minChildIndex = childIndex;
                }
            }
        }

        if ((minChild != -1) && (heapElements.get(Index)[1] > minChild)){
            Collections.swap(heapElements, Index, minChildIndex);
            heapifyDown(minChildIndex);
        }

        else if((minChild != -1) && (heapElements.get(Index)[1] == minChild) && (heapElements.get(Index)[0] > heapElements.get(minChildIndex)[0])){
            Collections.swap(heapElements, Index, minChildIndex);
            heapifyDown(minChildIndex);
        }
    }
    public void updateHeap(int Index, double Distance){
        heapElements.set(Index,new double[]{heapElements.get(Index)[0], Distance});
        heapifyUp(Index);
    }

}