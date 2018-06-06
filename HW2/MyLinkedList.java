/**
 * NAME: Aaron Hurtado
 * ID: A99128987
 * LOGIN: cs12scz
 */

import java.util.*;

public class MyLinkedList<E> extends AbstractList<E> {
	private int nelems;
	private Node head;
	private Node tail;

	protected class Node {
		E data;
		Node next;
		Node prev;

		/** Constructor to create singleton Node */
		public Node(E element) {
			this.data = element;
			this.next = null;
			this.prev = null;
		}

		/**
		 * Constructor to create singleton link it between previous and next
		 * @param element Element to add, can be null
		 * @param prevNode predecessor Node, can be null
		 * @param nextNode successor Node, can be null
		 */
		public Node(E element, Node prevNode, Node nextNode) {
			this.data = element;
			this.next = nextNode;
			this.prev = prevNode;
		}

		/** Remove this node from the link. Update previous and next nodes */
		public void remove() {
			this.prev.setNext(this.next);
			this.next.setPrev(this.prev);
			this.next = null;
			this.prev = null;
		}

		/**
		 * Set the previous node in the list
		 * @param p new previous node
		 */
		public void setPrev(Node p) {
			this.prev = p;
		}

		/**
		 * Set the next node in the list
		 * @param n new next node
		 */
		public void setNext(Node n) {
			this.next = n;
		}

		/**
		 * Set the element
		 * @param e new element
		 */
		public void setElement(E e) {
			this.data = e;
		}

		/** Accessor to get the next Node in the list */
		public Node getNext() {
			return this.next;
		}

		/** Accessor to get the prev Node in the list */
		public Node getPrev() {
			return this.prev;
		}

		/** Accessor to get the Nodes Element */
		public E getElement() {
			return this.data;
		}

	}

	/** ListIterator implementation */
	protected class MyListIterator implements ListIterator<E> {

		private boolean forward;
		private boolean canRemove;
		private Node left, right; // Cursor sits between these two nodes
		private int idx; // Tracks current position. what next() would
							// return

		public MyListIterator() {
			forward = false;
			canRemove = false;
			left = head;
			right = head.getNext();
			idx = 0;
		}

		/**
		 * Insert the given item into the list immediately before whatever
		 * would have been returned by a call to next()
		 * @param e data for newly created node
		 * @throws NullPointerException
		 */
		@Override
		public void add(E e) throws NullPointerException {
			if (e == null) {
				throw new NullPointerException();
			}
			Node newNode = new Node(e);
			newNode.setNext(right);
			newNode.setPrev(left);
			right.setPrev(newNode);
			left.setNext(newNode);
			left = newNode;
			idx++;
			canRemove = false;
			nelems++;
		}

		/**
		 * Return true if there are more elements when going in the forward
		 * direction.
		 * @return true if there are more elements when going in the forward
		 * direction
		 */
		@Override
		public boolean hasNext() {
			if (right.equals(tail)) {
				return false;
			}
			return true;
		}

		/**
		 * Return true if there are more elements when going in the reverse
		 * direction.
		 * @return true if there are more elements when going in the reverse
		 * direction.
		 */
		@Override
		public boolean hasPrevious() {
			if (left.equals(head)) {
				return false;
			}
			return true;
		}

		/**
		 * Return the next element in the list when going forward.
		 * @return the next element in the list when going forward.
		 * @throws NoSuchElementException
		 */
		@Override
		public E next() throws NoSuchElementException {
			if (right.equals(tail)) {
				throw new NoSuchElementException();
			}
			idx++;
			left = right;
			right = right.getNext();
			forward = true;
			canRemove = true;
			return left.getElement();
		}

		/**
		 * Return the index of the element that would be returned by a call to
		 * next()
		 * @return the index of the element that would be returned by a call to
		 * next
		 */
		@Override
		public int nextIndex() {
			if (right == tail) {
				return nelems;
			}
			return idx + 1;
		}

		/**
		 * Return the next element in the list when going backwards.
		 * @return the next element in the list when going backwards.
		 * @throws NoSuchElementException
		 */
		@Override
		public E previous() throws NoSuchElementException {
			if (left.equals(head)) {
				throw new NoSuchElementException();
			}
			idx--;
			right = left;
			left = left.getPrev();
			forward = false;
			canRemove = true;
			return right.getElement();
		}

		/**
		 * Return the index of the element that would be returned by a call to
		 * previous()
		 * @return the index of the element that would be returned by a call to
		 * previous()
		 */
		@Override
		public int previousIndex() {
			if (left == head) {
				return -1;
			}
			return idx - 1;
		}

		/**
		 * Remove the last element returned by the most recent call to either
		 * next/previous
		 * @throws IllegalStateException
		 */
		@Override
		public void remove() throws IllegalStateException {
			if (canRemove == false) {
				throw new IllegalStateException();
			}
			if (forward == true) {
				left.remove();
				idx--;
			} else if (forward == false) {
				right.remove();
			}
			canRemove = false;
			nelems--;
		}

		/**
		 * Change the value in the node returned by the most recent
		 * next/previous with the new value.
		 * @param e the data to set in the node
		 * @throws NullPointerException
		 */
		@Override
		public void set(E e) throws NullPointerException {
			if (canRemove == false) {
				throw new IllegalStateException();
			}
			if (e == null) {
				throw new NullPointerException();
			}
			if (forward == true) {
				left.setElement(e);
			} else if (forward == false) {
				right.setElement(e);
			}
		}

	}

	// Implementation of the MyLinkedList Class

	/** Only 0-argument constructor is define */
	public MyLinkedList() {
		this.nelems = 0;
		this.head = new Node(null);
		this.tail = new Node(null);

		head.setPrev(null);
		head.setNext(tail);

		tail.setPrev(head);
		tail.setNext(null);
	}

	/**
	 * return the number of elements being stored
	 * @return the number of elements being stored
	 */
	@Override
	public int size() {
		return this.nelems;
	}

	/**
	 * get contents at position i
	 * @return contents at position i
	 */
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if (this.isEmpty() || index < 0 || index > nelems) {
			throw new IndexOutOfBoundsException();
		}
		return this.getNth(index).getElement();
	}

	@Override
	/**
	 * Add an element to the list 
	 * @param index where in the list to add
	 * @param data data to add
	 * @throws IndexOutOfBoundsException
	 * @throws NullPointerException
	 */
	public void add(int index, E data) throws IndexOutOfBoundsException,
			NullPointerException {
		if (data == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > nelems) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 0) {
			Node newNode = new Node(data);
			newNode.setNext(head.getNext());
			newNode.setPrev(head);
			head.getNext().setPrev(newNode);
			head.setNext(newNode);
		} else if (index == this.nelems) {
			Node newNode = new Node(data);
			newNode.setNext(tail);
			newNode.setPrev(tail.getPrev());
			tail.getPrev().setNext(newNode);
			tail.setPrev(newNode);
		} else {
			Node newNode = new Node(data);
			newNode.setNext(this.getNth(index));
			newNode.setPrev(this.getNth(index - 1));
			this.getNth(index).setPrev(newNode);
			this.getNth(index - 1).setNext(newNode);
		}
		this.nelems++;
	}

	/**
	 * Add an element to the end of the list
	 * @param data data to add
	 * @throws NullPointerException
	 */
	public boolean add(E data) throws NullPointerException {
		if (data == null) {
			throw new NullPointerException();
		} else {
			Node newNode = new Node(data);
			newNode.setNext(tail);
			newNode.setPrev(tail.getPrev());
			tail.getPrev().setNext(newNode);
			tail.setPrev(newNode);
			this.nelems++;
			return true;
		}
	}

	/**
	 * Set the element at an index in the list
	 * @param index where in the list to add
	 * @param data data to add
	 * @return the element previously at the position being set
	 * @throws IndexOutOfBoundsException
	 * @throws NullPointerException
	 */
	public E set(int index, E data) throws IndexOutOfBoundsException,
			NullPointerException {
		if (data == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > nelems) {
			throw new IndexOutOfBoundsException();
		}
		E e = this.getNth(index).getElement();
		this.getNth(index).setElement(data);
		return e;
	}

	/**
	 * Remove the element at an index in the list
	 * @param index where in the list to add
	 * @return element the data found
	 * @throws IndexOutOfBoundsException
	 */
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > nelems) {
			throw new IndexOutOfBoundsException();
		}
		Node current = this.getNth(index);
		current.getPrev().setNext(current.getNext());
		current.getNext().setPrev(current.getPrev());
		current.setNext(null);
		current.setPrev(null);
		this.nelems--;
		return current.getElement();
	}

	/** Clear the linked list */
	public void clear() {
		int size = this.nelems;
		for (int i = 0; i < size; i++) {
			this.remove(0);
		}
	}

	/**
	 * Determine if the list empty
	 * @return true if empty, false otherwise
	 */
	public boolean isEmpty() {
		if (this.head.getNext().equals(tail)) {
			return true;
		}
		return false;
	}

	public Iterator<E> QQQiterator() {
		return new MyListIterator();
	}

	public ListIterator<E> QQQlistIterator() {
		return new MyListIterator();
	}

	// Helper method to get the Node at the Nth index
	private Node getNth(int index) {
		if (index < 0 || index > this.nelems - 1)
			return null;

		Node current = this.head;
		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}

		return current.getNext();
	}

	public Iterator<E> iterator() {
		return new MyListIterator();
	}

	/**
	 * Returns a list-iterator of the elements in this list (in proper
	 * sequence), starting at the specified position in the list. Obeys the
	 * general contract of List.listIterator(int).
	 */
	public ListIterator<E> listIterator() {
		return new MyListIterator();
	}

}

// VIM: set the tabstop and shiftwidth to 4
// vim:tw=78:ts=4:et:sw=4
