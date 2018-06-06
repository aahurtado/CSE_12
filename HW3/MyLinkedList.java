/**
 * Title: class MyLinkedList
 * Description: A doubly-linked list implementation. 
 * @author Philip Papadopoulos 
 * @version 1.0 13-April-2014
 */

import java.util.*;
public class MyLinkedList<E> extends AbstractList<E> {

	protected int nelems;
	protected Node head;
	protected Node tail;

	protected class Node {
		E data;
		Node next;
		Node prev;

		/** Constructor to create singleton Node */
		protected Node(E element)
		{
			data = element;
			next = null;
			prev = null;
		}
		/** Constructor to create singleton link it between previous and next 
		*   @param element 
		*		   Element to add, can be null
		*   @param prevNode 
		*		   predecessor Node, can be null
		*   @param nextNode 
		*		   successor Node, can be null 
		*/
			public Node(E element, Node prevNode, Node nextNode)
		{
			data = element;
			prev = prevNode;
			next = nextNode;
			if (prevNode != null) prevNode.next = this;
			if (nextNode != null) nextNode.prev = this;
		}
		/** Remove this node from the link. Update previous and next nodes */
		public void remove()
		{
			Node prevNode, nextNode;
			prevNode = prev;
			nextNode = next;
			if (nextNode != null) nextNode.setPrev(prevNode);
			if (prevNode != null) prevNode.setNext(nextNode);
		}
		/** Set the previous node in the list
		 *  @param p 
		 *	  new previous node
		 */
		public void setPrev(Node p)
		{
			prev = p;
		}
		/** Set the next node in the list
		 *  @param n 
		 *	  new next node
		 */
		public void setNext(Node n)
		{
			next = n;
		}
		   
		/** Set the element 
		 *  @param e 
		 *	  new element 
		 */
		public void setElement(E e)
		{
			data  = e;
		}
		/** Accessor to get the next Node in the list */
		public Node getNext()
		{
			return next;
		}
		/** Accessor to get the prev Node in the list */
		public Node getPrev()
		{
			return prev;
		} 
		/** Accessor to get the Nodes Element */
		public E getElement()
		{
			return data;
		} 
	}

	/** ListIterator implementation */ 
	protected class MyListIterator implements ListIterator<E> {

		private boolean forward;
		private boolean canRemove;
		private Node left,right; // Cursor sits between these two nodes
		private int idx;		// Tracks current position. what next() would
								// return 
		public MyListIterator()
		{
			left = head;
			right = head.getNext();
			forward = true;
			idx = 0;	
			canRemove = false;
		}
		/**
		 * Add an element at the current index
		 * 
		 * @param e
		 *			element to add
		 * @throws NullPointerException
		 */
		@Override
		public void add(E e) throws  NullPointerException
		{
			MyLinkedList.this.add(idx,e);
			idx++;
			left = left.getNext(); // The add above didn't change the
								// right side of the cursor. left now
								// indicates this element
			canRemove = false;  // See Specification
		}
		/**
		* Check whether there is a next element.
		 * 
		 * @return true if there is a next element and false otherwise.
		 */
		@Override
		public boolean hasNext()
		{
				// System.out.format("hasNext nelems:%d, idx:%d\n",nelems,idx);
				return idx < nelems;
		}

		/**
		 * Check whether there is a prev element.
		 * 
		 * @return true if there is a prev element and false otherwise.
		 */
		@Override
		public boolean hasPrevious()
		{
			return idx > 0;
		}
		@Override
		/**
		 * Get the next element and move forward.
		 * 
		 * @return data of the next element.
		 */
		public E next() throws NoSuchElementException
		{
			if (!hasNext()) throw new NoSuchElementException();
			canRemove = true;
			forward = true;
			E rval = right.getElement();
			// System.out.format("next(%d) %s\n",idx,rval.toString());
			idx++;
			right = right.getNext();
			left = right.getPrev();
			return rval;
		}
		/**
		 * Get the index of the next element.
		 * 
		 * @return the index.
		 */
		@Override
		public int nextIndex()
		{
			return idx;
		}
		/**
		 * Get the prev element and move backward.
		 * 
		 * @return data of the prev element.
		 */
		@Override
		public E previous() throws NoSuchElementException
		{
			if (!hasPrevious()) throw new NoSuchElementException();
			canRemove = true;
			forward = false;
			E rval = left.getElement();
			// System.out.format("prev(%d) %s\n",idx,rval.toString());
			idx--;
			left = left.getPrev();
			right = left.getNext();
			return rval;
		}

		/**
		 * Get the index of the prev element.
		 * 
		 * @return the index.
		 */
		@Override
		public int previousIndex()
		{
			return idx - 1;
		}
		/**
		 * Remove the last element returned by the most recent call to either next/previous
		 * @throws IllegalStateException
		 */
		@Override
		public void remove() throws IllegalStateException
		{
			if (!canRemove) throw new IllegalStateException();
			// idx points to the idx that next() would return
			//	  if moving forwards, then the current element is at idx - 1
			//	  if moving backwards then the current element is at idx
			MyLinkedList.this.remove(forward ? idx-1 : idx);
			idx--;
			canRemove = false;
		}
		/**
		 * Change the value in the node returned by the most recent next/previous with a new value.
		 * @param e
		 *			the new value
		 */
		@Override
		public void set(E e) throws NullPointerException
		{
			if (!canRemove) throw new IllegalStateException();
			// see comments above. Identical logic
			MyLinkedList.this.set(forward ? idx-1: idx,e);	
		}

	}


	//  Implementation of the MyLinkedList Class

	/** Only 0-argument constructor is define */
	public MyLinkedList()
	{
		super();
		nelems = 0;
		head = new Node(null);
		tail = new Node(null);
		head.setNext(tail);
		tail.setPrev(head);
	}
	@Override
	public int size()
	{
		// need to implement the size method
		return nelems; 
	}

	@Override
	public E get(int index) throws IndexOutOfBoundsException
	{
		if (index < 0 || index >= nelems) throw new IndexOutOfBoundsException();
		// Note: size is at least 1, else Bounds exception already thrown
		return getNth(index).getElement(); 
	}

	@Override
	/** Add an element to the list 
	 * @param index  where in the list to add
	 * @param data data to add
	 * @throws IndexOutOfBoundsException
	 * @throws NullPointerException
	 */ 
	public void add(int index, E data) 
		throws IndexOutOfBoundsException,NullPointerException
	{
		if (index < 0 || index > nelems) throw new IndexOutOfBoundsException();
		if (data == null) throw new NullPointerException();
		Node where = getNth(index);
		new Node(data,where.getPrev(),where); 
		nelems++;
	}
	/** Add an element to the end of the list 
	 * @param data data to add
	 * @throws NullPointerException
	 */ 
	public boolean add(E data) throws NullPointerException
	{
		this.add(this.nelems, data);
		return true;
	}

	/** Set the element at an index in the list 
	 * @param index  
	 *	  where in the list to add
	 * @param data data to add
	 * @return previous element 
	 * @throws IndexOutOfBoundsException
	 * @throws NullPointerException
	 */ 
	public E set(int index, E data) 
		throws IndexOutOfBoundsException,NullPointerException
	{
        E rdata; Node N;
		if (index < 0 || index > nelems) throw new IndexOutOfBoundsException();
		if (data == null) throw new NullPointerException();
        N = getNth(index);
        rdata = N.getElement();
		N.setElement(data); 
		return rdata;
	}

	/** Remove the element at an index in the list 
	 * @param index  where in the list to add
	 * @return element the data found
	 * @throws IndexOutOfBoundsException
	 */ 
	public E remove(int index) throws IndexOutOfBoundsException
	{
		if (index < 0 || index > nelems) throw new IndexOutOfBoundsException();
		Node where = getNth(index);
		where.remove(); 
		nelems--;
		return where.getElement();
	}

	/** Clear the linked list */
	public void clear()
	{
		nelems=0;
		/* let the java garbage collector clean up all of the references
		 * we will leave dangling 
		 */
		head.setNext(tail);
		tail.setPrev(head);
	}

	/** Determine if the list empty 
	 *  @return true if empty, false otherwise */
	public boolean isEmpty()
	{
		return nelems == 0;
	}

	public Iterator<E> QQQiterator()
	{
        return new MyListIterator();
	}
	public ListIterator<E> QQQlistIterator()
	{
        return new MyListIterator();
	}

	// Helper method to get the Node at the Nth index
	private Node getNth(int index) 
	{
		// Walk from the head
		if (index <= nelems/2)
		{
			Node where = head;
			for (int i = 0; i <= index; i++)
				where = where.getNext();
			return where;
		}
		// Walk from the tail
		else
		{
			Node where = tail;
			for (int i = nelems; i > index; i--)
				where = where.getPrev();
			return where;
		}
	}

/*  UNCOMMENT the following when you believe your MyListIterator class is
	functioning correctly */
	public Iterator<E> iterator()
	{
    	return new MyListIterator();
	}
	public ListIterator<E> listIterator()
	{
    	return new MyListIterator();
	}
/**/
}

// VIM: set the tabstop and shiftwidth to 4 
// vim:tw=78:ts=4:et:sw=4

