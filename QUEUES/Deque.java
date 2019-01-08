import java.util.Iterator;
                                             // 这个程序教会了我们如何使用泛型。

public class Deque<Item> implements Iterable<Item> 
{
	                                         
	private Node first, last;                // 首先定义两个Node,作为两个指针。
	private int size;
	
	private class Node						 // 大写的 Item 就是指泛型对吗？ 就是说它是任意类型。
	{									     // 这是一个子类！！！！！！！！！！！
		Item item;
		Node next;
		Node prev;                           // 子类调用子类也是可以的！！！！！！！！
	}
	
	public Deque()                           // construct an empty deque
	{
		first = null;                        // 首先把first和last都定义为空指针，size设为0。
		last  = null;
		size  = 0;
	}
	
	public boolean isEmpty()                 // is the deque empty?
	{	return size == 0;	}
	
	public int size()                        // return the number of items on the deque
	{	return size;	}
	
	public void addFirst(Item item)          // add the item to the front
	{
		if (item == null) throw new NullPointerException(); 
		Node oldfirst = first;               // 并不需要oldfirst指向一个新对象，所以就不用new Node()了，只需要它指向first指向的对象。
		first = new Node();                  // 那么first就需要指向一个新对象了。
		first.item = item;                   // 新加入的这个item就被first所指向的object存储，作为最靠前的一个object。
		first.next = oldfirst;               // first的next指针指向oldfirst
		first.prev = null;                   // 由于first是最靠前的，它的prev指针就不用往前指了。
		if (isEmpty()) last = first;         // 由于写在了size++之前，这实际是判断在addFirst之前，queue是不是空的。如果空，则last=first
		else oldfirst.prev = first;          // 如果不空，那么oldfirst的prev指针指向first
		size++;                              // 这个时候再size++。所以什么时候size++很重要。
	}
	
	public void addLast(Item item)           // add the item to the end
	{                                        // addLast 和 addFirst是完全对称的！！！！
		if (item == null) throw new NullPointerException();
		Node oldlast = last;
		last = new Node();
		last.item = item;
		last.prev = oldlast;
		last.next = null;
		if (isEmpty()) first = last;
		else oldlast.next = last;
		size++;
	}
	
	public Item removeFirst()                // remove and return the item from the front
	{
		if (isEmpty()) throw new java.util.NoSuchElementException();
		Item item = first.item;              // 这个定义是自己写的！很开心！！！！
		first = first.next;
		size--;
		if (isEmpty()) last = first;         // 如果之前queue里只有一个Node，那么first=last=那个Node，它们的prev和last都是null。
											 // 现在将最后一个Node remove了，就要恢复成原始状态，也就是first=last=null。
											 // 明白了！！！！！！！！！！
        else first.prev = null;              // 如果没空，那么first的prev为null
		return item;                         // 以上两行操作并没懂。。。。感觉没必要。。。。。。。。。。。。。。。。。。。。。。。。
	}
	
	public Item removeLast()                 // remove and return the item from the end
	{
		if (isEmpty()) throw new java.util.NoSuchElementException(); 
		Item item = last.item;
		last = last.prev;
		size--;
		if (isEmpty()) first = last;
		else last.next = null;
		return item;
	}
	
	public Iterator<Item> iterator()         // return an iterator over items in order from front to end
	{	return new DequeIterator();	}        // 这个地方很新颖！！！！！！！！
	
	
	// 我觉得这个class应该放在这里。
	private class DequeIterator implements Iterator<Item>
	{
		private Node current = first;
		   
		public boolean hasNext() { return current != null; }
		public void remove() { throw new java.lang.UnsupportedOperationException(); }
		public Item next()
		{
			if (!hasNext()) throw new java.util.NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	
	//public static void main(String[] args)   // unit testing (optional)
}

// 终于明白itera是怎么回事了。


