import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

// 这个randomized queue是用数组实现的！！！！！！！！！

public class RandomizedQueue<Item> implements Iterable<Item>    // 类名<Item>就是说这是个泛型类
{
	private int size;
    private Item[] s;                        // 一个泛型数组，在后面construcor的时候，一定要强转。
    
    public RandomizedQueue()                 // Constructor.
    {
        size = 0;
        s = (Item[]) new Object[1];          // 使用了强转，而且将数组赋成了长度为 1.
    }
	
	public boolean isEmpty()                 // 常规定义。
	{ 	return size == 0; 	}
    
    public int size()                        // 常规定义。
	{ 	return size; 	}
    
    private void resize(int capacity)        // method的输入参数capacity就是新数组的size。
    {
	    Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            copy[i] = s[i];                  // 将原数组中的东西都复制到新的、长度为两倍的数组copy中。
        s = copy;	                         // 令s指向copy指向的数组。
	}
	
	public void enqueue(Item item)
    {
        if (item == null) throw new NullPointerException();
        s[size++] = item;                                    // 先拿原来的size用，就是最后一个位置用来存新的item，然后size++
        if (size == s.length) resize(2 * s.length);          // 加完之后看是不是下一个就装不下了，如果size==s.length,那么正好装不下了。
    }
    
    public Item dequeue()
    {
        if (size == 0) throw new java.util.NoSuchElementException();
        int r = StdRandom.uniform(size);
        Item item = s[r];                                    // 将要去掉的ele赋给item
        s[r] = s[size-1];                                    // 把目前的最后一个ele赋给s[r]
        s[--size] = null;                                    // 先做--size，然后对减一之后的size，将其set成null。
		                                                     // 实际完成了两个位置元素的互换，并且删掉了该删的那个元素，减小了size。
        if (size > 0 && size == s.length/4) resize(s.length/2);
        return item;
    }
    
    public Item sample()                                     // 取数组中一个随机ele并返回该ele。
    {
        if (size == 0) throw new java.util.NoSuchElementException();
        int r = StdRandom.uniform(size);
        return s[r];                  
    }
        
    public Iterator<Item> iterator() 
	{	return new RandomizedQueueIterator();	}            // 还是不理解这个iterator套着iterator是毛线。。。。。。。。
	
	private class RandomizedQueueIterator implements Iterator<Item>
    {
        private int i = size;
        private int[] order;
        
        public RandomizedQueueIterator()
        {
            order = new int[i];
            for (int j = 0; j < i; ++j) {
                order[j] = j;
            }
            StdRandom.shuffle(order);                        // shuffle应该就是打乱顺序的method。
        }
        
        public boolean hasNext() 
		{	return i > 0;	}
		
        public void remove()
		{	throw new java.lang.UnsupportedOperationException();	}
		
        public Item next() 
        { 
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return s[order[--i]]; 
        }
    }

}

// 也基本明白了