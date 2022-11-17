import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        MinHeap mH = new MinHeap();
        mH.add(1, 5);
        mH.add(11, 6);
        mH.add(2, 6);
        mH.add(3, 4);
        mH.add(10, 3);
        mH.add(9, 6);

        ArrayList<Integer> elements = mH.getHeap();
        System.out.println("MinHeap with default degree");

        for (int i=0; i<elements.size(); i++)
            System.out.printf("%5s", elements.get(i));
        System.out.println();

        mH = new MinHeap(4);
        mH.add(1, 5);
        mH.add(11, 6);
        mH.add(2, 6);
        mH.add(3, 4);
        mH.add(10, 3);
        mH.add(9, 6);

        elements = mH.getHeap();
        System.out.println("MinHeap with non-default degree");

        for (int i=0; i<elements.size(); i++)
            System.out.printf("%5s", elements.get(i));
        System.out.println();

    }
}