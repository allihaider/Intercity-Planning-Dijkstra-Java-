import java.util.ArrayList;
import java.util.Collections;

public class MinHeap {
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
