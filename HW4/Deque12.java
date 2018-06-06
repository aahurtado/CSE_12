/**
 * NAME: Aaron Hurtado
 * ID: A99128987
 * LOGIN: cs12scz
 */

import java.util.ArrayList;

public class Deque12<E> implements BoundedDeque<E> {	

	private int front;
	private int rear;
	private int capacity;
	private ArrayList<E> list;		

	/**
	 * Constructs an empty Deque12 with the specified initial capacity.
	 * @param capacity the initial capacity of the list
	 * @throws IllegalArgumentException if the specified initial capacity is negative
	 */
	public Deque12 (int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException();
		}
		list = new ArrayList<E>(capacity);
		for (int i = 0; i < capacity; i++) {
			list.add(null);
		}
		this.capacity = capacity;
		front = 0;
		rear = 0;
	}


	/**
	 * Returns the capacity of this BoundedDeque, that is, the maximum number
	 * of elements it can hold.
	 * <br>PRECONDITION: none
	 * <br>POSTCONDITION: the BoundedDeque is unchanged.
	 * @return the capacity of this BoundedDeque
	 */
	@Override
	public int capacity() {
		return this.capacity;
	}


	/**
	 * Returns the number of elements in this BoundedDeque.
	 * <br>PRECONDITION: none
	 * <br>POSTCONDITION: the BoundedDeque is unchanged.
	 * @return the number of elements in this BoundedDeque
	 */
	@Override
	public int size() {
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null)
				count++;
		}
		return count;
	}


	/**
	 * Adds the specified element to the front of this BoundedDeque. Returns
	 * true if the operation succeeded, else false.
	 * <br>PRECONDITION: the BoundedDeque's size is less than its capacity.
	 * <br>POSTCONDITION: the element is now the front element in this
	 * BoundedDeque, none of the other elements have been changed, and the
	 * size is increased by 1.
	 * @param e the element to add to the front of the list
	 * @return true if the element was added, else false.
	 * @throws java.lang.NullPointerException if the specified element is
	 * null, and size is less than capacity
	 */
	@Override
	public boolean addFront(E e) {
		int check = front;
		if (e == null) {
			throw new NullPointerException();
		}	
		if (check == 0 && this.size() == 0) {
			check = 0;
		}
		else if (check == 0) {
			check = this.capacity - 1;
		}
		else {
			check--;
		}		
		if (list.get(check) != null) {
			return false;
		}			
		else {
			front = check;
			list.set(front, e);
			return true;
		}		
	}


	/**
	 * Adds the specified element to the back of this BoundedDeque. Returns
	 * true if the operation succeeded, else false.
	 * <br>PRECONDITION: the BoundedDeque's size is less than its capacity.
	 * <br>POSTCONDITION: the element is now the back element in this
	 * BoundedDeque, none of the other elements have been changed, and the
	 * size is increased by 1.
	 * @param e the element to add to the back of the list
	 * @return true if the element was added, else false.
	 * @throws java.lang.NullPointerException if the specified element is
	 * null, and size is less than capacity
	 */
	@Override
	public boolean addBack(E e) {
		int check = rear;
		if (e == null) {
			throw new NullPointerException();
		}	
		if (check == 0 && this.size() == 0) {
			check = 0;
		}
		else if (check == this.capacity - 1) {
			check = 0;
		}
		else {
			check++;
		}		
		if (list.get(check) != null) {
			return false;
		}	
		else {
			rear = check;
			list.set(rear, e);
			return true;
		}		
	}


	/**
	 * Removes the element at the front of this BoundedDeque. Returns the
	 * element removed, or null if there was no such element.
	 * <br>PRECONDITION: the BoundedDeque's size is greater than zero. 
	 * <br>POSTCONDITION: the front element in this BoundedDeque has been
	 * removed, none of the other elements have been changed, and the size
	 * is decreased by 1.
	 * @return the element removed, or null if the size was zero.
	 */
	@Override
	public E removeFront() {
		if (this.size() == 0) {
			return null;
		}
		E e = list.get(front); 
		list.set(front, null);		
		if (this.size() == 0) {
			front = front;
		}
		else if (front == this.capacity - 1) {
			front = 0;
		}
		else {
			front++;
		}	
		return e;
	}


	/**
	 * Removes the element at the back of this BoundedDeque. Returns the
	 * element removed, or null if there was no such element.
	 * <br>PRECONDITION: the BoundedDeque's size is greater than zero.
	 * <br>POSTCONDITION: the back element in this BoundedDeque has been
	 * removed, none of the other elements have been changed, and the size
	 * is decreased by 1.
	 * @return the element removed, or null if the size was zero.
	 */
	@Override
	public E removeBack() {
		if (this.size() == 0) {
			return null;
		}
		E e = list.get(rear); 
		list.set(rear, null);		
		if (this.size() == 0) {
			rear = rear;
		}
		else if (rear == 0) {
			rear = this.capacity - 1;
		}
		else {
			rear--;
		}
		return e;
	}


	/**
	 * Returns the element at the front of this BoundedDeque, or null if
	 * there was no such element.
	 * <br>PRECONDITION: the BoundedDeque's size is greater than zero.
	 * <br>POSTCONDITION: The BoundedDeque is unchanged.
	 * @return the element at the front, or null if the size was zero.
	 */
	@Override
	public E peekFront() {
		if (this.size() <= 0) {
			return null;
		}
		E e = list.get(front); 
		return e;
	}


	/**
	 * Returns the element at the back of this BoundedDeque, or null if
	 * there was no such element.
	 * <br>PRECONDITION: the BoundedDeque's size is greater than zero.
	 * <br>POSTCONDITION: The BoundedDeque is unchanged.
	 * @return the element at the back, or null if the size was zero.
	 */
	@Override
	public E peekBack() {
		if (this.size() <= 0) {
			return null;
		}
		E e = list.get(rear); 
		return e;
	}

}
