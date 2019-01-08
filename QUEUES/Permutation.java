import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args)
    {
        RandomizedQueue<String> strs = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            strs.enqueue(StdIn.readString());
        }                                        // 经典用法，只要还有输入的string，就读入。
        
        int k = Integer.parseInt(args[0]);       // 将输入强制转化为整数的经典用法。
        for (int i = 0; i < k; i++) {
            StdOut.println(strs.dequeue());
        }
    }
}