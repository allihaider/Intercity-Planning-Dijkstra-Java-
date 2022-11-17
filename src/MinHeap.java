import java.util.ArrayList;
import java.util.Collections;

public class MinHeap {
    int k;
    ArrayList<double[]> heapElements = new ArrayList<>();

    public MinHeap(){
        this.k = 2;
    }

    public MinHeap(int k_value) {
        this.k = k_value;
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
}
