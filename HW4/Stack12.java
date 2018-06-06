/**
 * NAME: Aaron Hurtado
 * ID: A99128987
 * LOGIN: cs12scz
 */

public class Stack12<E> implements BoundedStack<E> {

	private Deque12<E> deque;

	/**
	 * Constructs an empty Stack12 with the specified initial capacity.
	 * @param capacity the initial capacity of the list
	 * @throws IllegalArgumentException if the specified initial capacity is negative
	 */
	public Stack12 (int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException();
		}
		deque = new Deque12<E>(capacity);
	}

	/**
	 * Returns the capacity of this BoundedStack, that is,
	 * the maximum number of elements it can hold.
	 * <br>PRECONDITION: none
	 * <br>POSTCONDITION: the BoundedStack is unchanged.
	 * @return the capacity of this BoundedStack
	 */	
	@Override
	public int capacity() {
		return deque.capacity();
	}

	/**
	 * Returns the number of elements in this BoundedStack.
	 * <br>PRECONDITION: none
	 * <br>POSTCONDITION: the BoundedStack is unchanged.
	 * @return the number of elements in this BoundedStack
	 */
	@Override
	public int size() {
		return deque.size();
	}

	/**
	 * Adds the specified element to the top of this BoundedStack.
	 * Returns true if the operation succeeded, else false.
	 * <br>PRECONDITION: the BoundedStack's size is less than its capacity.
	 * <br>POSTCONDITION: the element is now the top element in this
	 * BoundedStack, none of the other elements have been changed, and
	 * the size is increased by 1.
	 * @param e the element to add to the stack
	 * @return <tt>true</tt> if the element was added, else <tt>false</tt>.
	 * @throws NullPointerException if the specified element is null,
	 * and size is less than capacity
	 */
	@Override
	public boolean push(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		if (deque.size() >= deque.capacity()) {
			return false;
		}
		deque.addBack(e);
		return true;
	}

	/**
	 * Removes the element at the top of this BoundedStack.
	 * Returns the element removed, or <tt>null</tt> if there was no such element.
	 * <br>PRECONDITION: the BoundedStack's size is greater than zero.
	 * <br>POSTCONDITION: the top element in this BoundedStack has been removed,
	 * none of the other elements have been changed, and
	 * the size is decreased by 1.
	 * @return  the element removed, or <tt>null</tt> if the size was zero.
	 */
	@Override
	public E pop() {	
		if (this.size() <= 0) {
			return null;
		}
		return deque.removeBack();
	}

	/**
	 * Returns the element at the top of this BoundedStack,
	 * or <tt>null</tt> if there was no such element.
	 * <br>PRECONDITION: the BoundedStack's size is greater than zero.
	 * <br>POSTCONDITION: The BoundedStack is unchanged.
	 * @return  the element at the top, or <tt>null</tt> if the size was zero.
	 */
	@Override
	public E peek() {
		if (this.size() <= 0) {
			return null;
		}
		return deque.peekBack();
	}

}
