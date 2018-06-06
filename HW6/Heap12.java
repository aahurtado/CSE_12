import java.util.ArrayList;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * NAME: Aaron Hurtado
 * ID: A99128987
 * LOGIN: cs12scz
 */

/** Heap12 class that implements an unbounded array-backed heap structure and is
 * an extension of the Java Collections AbstractQueue class 
 * <p>
 * The elements of the heap are ordered according to their natural 
 * ordering,  Heap12 does not permit null elements. 
 * The top of this heap is the minimal or maximal element (called min/max)  
 * with respect to the specified natural ordering.  
 * If multiple elements are tied for min/max value, the top is one of 
 * those elements -- ties are broken arbitrarily. 
 * The queue retrieval operations poll and  peek 
 * access the element at the top of the heap.
 * <p>
 * A Heap12 is unbounded, but has an internal capacity governing the size of 
 * an array used to store the elements on the queue. It is always at least as 
 * large as the queue size. As elements are added to a Heap12, its capacity 
 * grows automatically. The details of the growth policy are not specified.
 * <p>
 * This class and its iterator implements the optional methods of the 
 * Iterator interface (including remove()). The Iterator provided in method 
 * iterator() is not guaranteed to traverse the elements of the Heap12 in 
 * any particular order. 
 * <p>
 * Note that this implementation is not synchronized. Multiple threads 
 * should not access a Heap12 instance concurrently if any of the 
 * threads modifies the Heap12. 
 */
public class Heap12<E extends Comparable <? super E>> extends 
AbstractQueue<E> 
{	

	private ArrayList<E> list;
	private int capacity;
	private boolean isMaxHeap;


	/** O-argument constructor. Creates and empty Heap12 with unspecified
	 *  initial capacity, and is a min-heap 
	 */ 
	public Heap12()
	{
		this.list = new ArrayList<E>();
		this.capacity = 5;
		this.isMaxHeap = false;
		for (int i = 0; i < this.capacity; i++) {
			list.add(null);
		}
	}


	/** 
	 * Constructor to build a min or max heap
	 * @param isMaxHeap 	if true, this is a max-heap, else a min-heap 
	 */ 
	public Heap12( boolean isMaxHeap)
	{
		this.list = new ArrayList<E>();
		this.capacity = 5;
		this.isMaxHeap = isMaxHeap;
		for (int i = 0; i < this.capacity; i++) {
			list.add(null);
		}	
	}


	/** 
	 * Constructor to build a with specified initial capacity 
	 *  min or max heap
	 * @param capacity  	initial capacity of the heap.	
	 * @param isMaxHeap 	if true, this is a max-heap, else a min-heap 
	 */ 
	public Heap12( int capacity, boolean isMaxHeap)
	{
		if (capacity < 1) throw new IllegalArgumentException();

		this.list = new ArrayList<E>();
		this.capacity = capacity;
		this.isMaxHeap = isMaxHeap;
		for (int i = 0; i < this.capacity; i++) {
			list.add(null);
		}
	}


	/** Copy constructor. Creates Heap12 with a deep copy of the argument
	 * @param toCopy      the heap that should be copied
	 */
	public Heap12 (Heap12<E> toCopy)
	{
		if (toCopy == null) throw new IllegalArgumentException();

		this.list = new ArrayList<E>();
		this.capacity = toCopy.capacity;
		this.isMaxHeap = toCopy.isMaxHeap;
		for (int i = 0; i < this.capacity; i++) {
			list.add(toCopy.list.get(i));
		}
	}


	/* The following are defined "stub" methods that provide degenerate
	 * implementations of methods defined as abstract in parent classes.
	 * These need to be coded properly for Heap12 to function correctly
	 */

	/** Size of the heap
	 * @return the number of elements stored in the heap
	 */
	public int size()
	{
		int size = 0;
		for (int i = 0; i < this.capacity; i++) {
			if (list.get(i) != null) size ++;
		}
		return size;
	}


	/** 
	 * @return an Iterator for the heap 
	 */
	public Iterator<E> iterator()
	{
		return new Heap12Iterator();
	}


	/** peek - see AbstractQueue for details 
	 * 
	 * @return Element at top of heap. Do not remove 
	 */
	public E peek()
	{
		if (this.size() == 0) return null;
		return this.list.get(0);
	}


	/** poll - see AbstractQueue for details 
	 * @return Element at top of heap. And remove it from the heap. 
	 * 	return <tt>null</tt> if the heap is empty
	 */
	public E poll()
	{
		if (this.size() == 0) return null;

		E oldTop = this.list.get(0);

		this.list.set(0, this.getRightMostLeaf());		
		this.list.set(this.getRightMostLeafIndex(), null);

		this.trickleDown(0);
		return oldTop;
	}


	/** offer -- see AbstractQueue for details
	 * insert an element in the heap
	 * @return true
	 * @throws NullPointerException 
	 * 	if the specified element is null	
	 */
	public boolean offer (E e)
	{
		if (e == null) throw new NullPointerException();
		if (this.size() == this.capacity) {
			this.expand();
		}

		int newRightMostLeafIndex = this.getNewRightMostLeaf();
		this.list.set(newRightMostLeafIndex, e);
		this.bubbleUp(newRightMostLeafIndex);
		return true;
	}


	/* ------ Private Helper Methods ----
	 *  DEFINE YOUR HELPER METHODS HERE
	 */	
	/**
	 * Performs the bubbleUp function on the heap. Comparisons are based on if
	 * isMaxHeap is true or false.
	 * @param indx the index to perform the method on
	 */
	private void bubbleUp(int indx) {		

		E node = this.getNode(indx);
		E parent = this.getParent(indx);		
		int parentIndex = this.getParentIndex(indx);

		if (indx == 0) return;
		if (this.isMaxHeap) {
			if (node.compareTo(parent) <= 0) return;
		}
		else {
			if (node.compareTo(parent) >= 0) return;
		}

		this.swap(parent, parentIndex, node, indx);
		this.bubbleUp(parentIndex);
	}


	/**
	 * Performs the trickleDown function on the heap. Comparisons are based on
	 * if isMaxHeap is true or false.
	 * @param indx the index to perform the method on
	 */
	private void trickleDown(int indx) {
		E node = this.getNode(indx);
		E leftChild = null, rightChild = null;		
		boolean hasLC = false, hasRC = false;
		boolean gtLC = false, gtRC = false;
		int leftChildIndex = this.getLeftChildIndex(indx);
		int rightChildIndex = this.getRightChildIndex(indx);

		if (this.hasLeftChild(indx)) {
			leftChild = this.getLeftChild(indx);			
			hasLC = true;
			gtLC = this.compareTrickle(node, leftChild);
		}
		if (this.hasRightChild(indx)) {
			rightChild = this.getRightChild(indx);	
			hasRC = true;
			gtRC = this.compareTrickle(node, rightChild);
		}	

		if ((hasLC || hasRC) && (gtLC || gtRC)) {
			if (hasLC && hasRC) {
				boolean swap = false;
				E smallestChild = this.getSmallestChild(leftChild, rightChild);
				int smallestChildIndex = this.getSmallestChildIndex(indx);					
				if (this.isMaxHeap)	swap = node.compareTo(smallestChild) < 0;
				else swap = node.compareTo(smallestChild) > 0;				
				if (swap) {
					this.swap(node, indx, smallestChild, smallestChildIndex);
					this.trickleDown(smallestChildIndex);
				}
			}
			else if (hasLC) {
				if (gtLC) {
					this.swap(node, indx, leftChild, leftChildIndex);
					this.trickleDown(leftChildIndex);
				}
			}
			else if (hasRC) {
				if (gtRC) {
					this.swap(node, indx, rightChild, rightChildIndex);
					this.trickleDown(rightChildIndex);
				}
			}
			else
				return;
		}
	}	


	/**
	 * Returns the node object at the specified index. If index is greater than
	 * backing store array's length returns null.
	 * @param index index of the desired node
	 * @return the node object at the specified index
	 */
	private E getNode(int index) {
		if (index >= this.capacity) return null;
		return this.list.get(index);
	}


	/**
	 * Returns the index of the parent node of the inputed index
	 * @param index index of the node whose parent's index is wanted
	 * @return the index of the parent node of the inputed index
	 */
	private int getParentIndex(int index) {
		return (index - 1)/2;
	}


	/**
	 * Returns the parent node object of the inputed index
	 * @param index index of the node whose parent node is wanted
	 * @return the parent node object of the inputed index
	 */
	private E getParent(int index) {
		return this.list.get((index - 1)/2);
	}


	/**
	 * Returns the index of the left child of the inputed index
	 * @param index index of the node whose left child is wanted
	 * @return the index of the left child of the inputed index
	 */
	private int getLeftChildIndex(int index) {
		return 2*index + 1;
	}


	/**
	 * Returns the index of the right child of the inputed index
	 * @param index index of the node whose right child is wanted
	 * @return the index of the right child of the inputed index
	 */
	private int getRightChildIndex(int index) {
		return 2*index + 2;
	}	


	/**
	 * Returns the left child object of the inputed index
	 * @param index index of the node whose left child is wanted
	 * @return the left child object of the inputed index
	 */
	private E getLeftChild(int index) {
		return this.list.get(this.getLeftChildIndex(index));
	}


	/**
	 * Returns the right child object of the inputed index
	 * @param index index of the node whose right child is wanted
	 * @return the right child object of the inputed index
	 */
	private E getRightChild(int index) {
		return this.list.get(this.getRightChildIndex(index));
	}


	/**
	 * Returns true if node has a left child, else returns false
	 * @param index index of the node
	 * @return true if node has a left child, else returns false
	 */
	private boolean hasLeftChild(int index) {
		if ( (2*index + 1) >= this.capacity)
			return false;	
		if (this.list.get(2*index + 1) == null)
			return false;
		else
			return true;
	}


	/**
	 * Returns true if node has a right child, else returns false
	 * @param index index of the node
	 * @return true if node has a right child, else returns false
	 */
	private boolean hasRightChild(int index) {
		if ((2*index + 2) >= this.capacity)
			return false;
		if (this.list.get(2*index + 2) == null)
			return false;		
		else
			return true;
	}


	/**
	 * Returns the smallest child node or largest child of a node based on
	 * if isMaxHeap is true or false.
	 * @param leftChild leftChild of the node
	 * @param rightChild rightChild of the node
	 * @return the largest child node if isMaxHeap is true. Returns
	 * the smallest child if isMaxHeap is false. If both child nodes are equal
	 * in comparison returns left child node.
	 */
	private E getSmallestChild(E leftChild, E rightChild) {
		if (this.isMaxHeap) {
			if (leftChild.compareTo(rightChild) > 0) return leftChild;
			else if (rightChild.compareTo(leftChild) > 0) return rightChild;
			else
				return leftChild;
		}
		else {
			if (leftChild.compareTo(rightChild) < 0) return leftChild;
			else if (rightChild.compareTo(leftChild) < 0) return rightChild;
			else
				return leftChild;
		}
	}


	/**
	 * Returns the smallest child index or largest child index of a node
	 * based on if isMaxHeap is true or false.
	 * @param index the index of the node whose child nodes are to be compared
	 * @return the largest child node's index if isMaxHeap is true. Returns
	 * the smallest child node's index if isMaxHeap is false. If both child
	 * nodes are equal in comparison returns left child node's index.
	 */
	private int getSmallestChildIndex(int index) {
		E leftChild = this.getLeftChild(index);
		E rightChild = this.getRightChild(index);

		if (this.isMaxHeap) {
			if (leftChild.compareTo(rightChild) > 0) 
				return this.getLeftChildIndex(index);
			else if (rightChild.compareTo(leftChild) > 0)
				return this.getRightChildIndex(index);
			else
				return this.getLeftChildIndex(index);
		}
		else {
			if (leftChild.compareTo(rightChild) < 0) 
				return this.getLeftChildIndex(index);
			else if (rightChild.compareTo(leftChild) < 0) 
				return this.getRightChildIndex(index);
			else
				return this.getLeftChildIndex(index);
		}
	}


	/**
	 * Returns the right most leaf object in the heap
	 * @return the right most leaf object in the heap
	 */
	private E getRightMostLeaf() {
		return this.list.get(this.size()-1);
	}


	/**
	 * Returns the index of the right most leaf object in the heap
	 * @return the index of the right most leaf object in the heap
	 */
	private int getRightMostLeafIndex() {
		return this.size()-1;
	}


	/**
	 * Returns the index of the new right most leaf node in the heap
	 * @return the index of the new right most leaf node in the heap
	 */
	private int getNewRightMostLeaf() {
		return this.size();
	}


	/**
	 * Enlarges an ArrayList by 2x if the array is full and copies its contents
	 * to the new array.
	 */
	private void expand() {

		this.capacity = this.capacity * 2;
		ArrayList<E> array = new ArrayList<E>();

		for (int i = 0; i < this.capacity; i++) {
			array.add(null);
		}

		for (int i = 0; i < this.capacity/2; i++) {
			array.set(i, this.list.get(i));
		}

		this.list = array;
	}


	/**
	 * Swaps two nodes in a heap
	 * @param parent parent node of the swap
	 * @param parentIndex index of the parent node
	 * @param child child node of the swap
	 * @param childIndex index of the child node
	 */
	private void swap (E parent, int parentIndex, E child, int childIndex) {
		E tmp = parent;
		this.list.set(parentIndex, child);
		this.list.set(childIndex, tmp);
	}


	/**
	 * This method compares to nodes for the trickleDown method. It compares
	 * the nodes based on whether or not isMaxHeap is true or false.
	 * @param node the parent node to be compared
	 * @param child the child node to be compared
	 * @return if isMaxHeap is true and the node is less than its child then
	 * returns true, else false. if isMaxHeap is false and the node is greater
	 * than its child then returns true, else false.
	 */
	private boolean compareTrickle(E node, E child) {
		if (this.isMaxHeap) {
			if (node.compareTo(child) < 0) return true;
			else return false;
		}
		else {
			if (node.compareTo(child) > 0) return true;
			else return false;
		}
	}


	/**
	 * Same as poll method but works on any index.
	 * @param idx
	 */
	private void poll(int idx)
	{
		if (this.size() == 0) return;

		this.list.set(idx, this.getRightMostLeaf());		
		this.list.set(this.getRightMostLeafIndex(), null);

		this.trickleDown(idx);
	}


	/** Inner Class for an Iterator **/
	/* stub routines */
	private class Heap12Iterator implements Iterator<E>
	{

		private boolean canRemove;
		private int idx;		

		private Heap12Iterator()
		{
			idx = 0;			
			canRemove = false;
		}


		/* hasNext() to implement the Iterator<E> interface */
		public boolean hasNext()
		{
			return idx < Heap12.this.size();
		}


		/* next() to implement the Iterator<E> interface */
		public E next() throws NoSuchElementException
		{
			if (!hasNext()) throw new NoSuchElementException();
			E rval = Heap12.this.list.get(idx);
			idx++;
			canRemove = true;			
			return rval;
		}


		/* remove() to implement the Iterator<E> interface */
		public void remove() throws IllegalStateException
		{
			if (!canRemove) throw new IllegalStateException();
			Heap12.this.poll(idx-1);
			idx--;
			canRemove = false;
		}
	}


}
// vim:ts=4:sw=4:tw=78: