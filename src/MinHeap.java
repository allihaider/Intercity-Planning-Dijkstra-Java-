public class MinHeap {
    int k;

    public MinHeap(){
        this.k = 2;
    }

    public int getParentIndex(int Index){
        if ((Index % k)== 0){
            return (int)((Index / k) - 1);
        }
        else{
            return (int)Math.floor(Index / k);
        }
    }

}
