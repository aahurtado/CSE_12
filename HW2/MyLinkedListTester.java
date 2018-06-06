/**
 * NAME: Aaron Hurtado
 * ID: A99128987
 * LOGIN: cs12scz
 */

import junit.framework.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Title: class LinkedListTester Description: JUnit test class for LinkedList
 * class
 * 
 * @author Philip Papadopoulos
 * @version 2.0 03-April-2014
 */
public class MyLinkedListTester extends TestCase {
	private MyLinkedList<Integer> empty;
	private MyLinkedList<Integer> one;
	private MyLinkedList<Integer> several;
	private MyLinkedList<String> slist;
	static final int DIM = 5;
	static final int FIBMAX = 30;

	public MyLinkedListTester() {
		super();
	}

	/**
	 * Standard Test Fixture. An empty list, a list with one entry (0) and a
	 * list with several entries (0,1,2)
	 */
	public void setUp() {
		empty = new MyLinkedList<Integer>();
		one = new MyLinkedList<Integer>();
		one.add(0, new Integer(0));
		several = new MyLinkedList<Integer>();
		// List: 1,2,3,...,Dim
		for (int i = DIM; i > 0; i--)
			several.add(0, new Integer(i));

		// List: "First","Last"
		slist = new MyLinkedList<String>();
		slist.add(0, "First");
		slist.add(1, "Last");
	}

	/** Test if heads of the lists are correct */
	public void testGetHead() {
		assertEquals("Check 0", new Integer(0), one.get(0));
		assertEquals("Check 0", new Integer(1), several.get(0));
	}

	/** Test if size of lists are correct */
	public void testListSize() {
		assertEquals("Check Empty Size", 0, empty.size());
		assertEquals("Check One Size", 1, one.size());
		assertEquals("Check Several Size", DIM, several.size());
	}

	/** Test setting a specific entry */
	public void testSet() {
		slist.set(1, "Final");
		assertEquals("Setting specific value", "Final", slist.get(1));
	}

	/** Test isEmpty */
	public void testEmpty() {
		assertTrue("empty is empty", empty.isEmpty());
		assertTrue("one is not empty", !one.isEmpty());
		assertTrue("several is not empty", !several.isEmpty());
	}

	/** Test iterator on empty list and several list */
	public void testIterator() {
		int counter = 0;
		ListIterator<Integer> iter;
		for (iter = empty.listIterator(); iter.hasNext();) {
			fail("Iterating empty list and found element");
		}
		counter = 0;
		for (iter = several.listIterator(); iter.hasNext(); iter.next())
			counter++;
		assertEquals("Iterator several count", counter, DIM);
	}

	/** Test out of bounds exception on get */
	public void testGetException() {
		try {
			empty.get(0);
			fail("Should have generated an exception");
		} catch (IndexOutOfBoundsException e) {
			// normal
		}
	}

	/** test Iterator Fibonacci */
	public void testIteratorFibonacci() {

		MyLinkedList<Integer> fib = new MyLinkedList<Integer>();
		ListIterator<Integer> iter;
		// List: 0 1 1 2 3 5 8 13 ...
		// Build the list with integers 1 .. FIBMAX
		int t, p = 0, q = 1;
		fib.add(0, p);
		fib.add(1, q);
		for (int k = 2; k <= FIBMAX; k++) {
			t = p + q;
			fib.add(k, t);
			p = q;
			q = t;
		}
		// Now iterate through the list to near the middle, read the
		// previous two entries and verify the sum.
		iter = fib.listIterator();
		int sum = 0;
		for (int j = 1; j < FIBMAX / 2; j++)
			sum = iter.next();
		iter.previous();
		assertEquals(iter.previous() + iter.previous(), sum);
		// Go forward with the list iterator
		assertEquals(iter.next() + iter.next(), sum);
	}

	///////////////////// My tests ////////////////////////////////////////

	/** Test if add method adds to the end of the list */
	public void testAdd() {
		empty.add(5);
		one.add(5);
		several.add(5);
		assertEquals("Check 5", new Integer(5), empty.get(empty.size() - 1));
		assertEquals("Check 5", new Integer(5), one.get(one.size() - 1));
		assertEquals("Check 5", new Integer(5), several.get(several.size() - 1));
	}

	/** Test NullPointerException on add */
	public void testAddException() {
		try {
			empty.add(null);
			fail("Should have generated an exception");
		} catch (NullPointerException e) {
			// normal
		}
	}

	/** Test out of bounds exception on add */
	public void testAddException2() {
		try {
			empty.add(-1, 5);
			fail("Should have generated an exception");
		} catch (IndexOutOfBoundsException e) {
			// normal
		}
	}

	/** Test out of bounds exception on add */
	public void testAddException3() {
		try {
			empty.add(100, 5);
			fail("Should have generated an exception");
		} catch (IndexOutOfBoundsException e) {
			// normal
		}
	}

	/** Test if get method returns correct value */
	public void testGet() {
		assertEquals("Check 0", new Integer(0), one.get(0));
		assertEquals("Check 1", new Integer(1), several.get(0));
		assertEquals("Check 2", new Integer(2), several.get(1));
		assertEquals("Check 3", new Integer(3), several.get(2));
		assertEquals("Check 4", new Integer(4), several.get(3));
		assertEquals("Check 5", new Integer(5), several.get(4));
	}

	/** Test null pointer exception on set */
	public void testSetException() {
		try {
			one.set(0, null);
			fail("Should have generated an exception");
		} catch (NullPointerException e) {
			// normal
		}
	}

	/** Test out of bounds exception on set */
	public void testSetException2() {
		try {
			one.set(-1, 5);
			fail("Should have generated an exception");
		} catch (IndexOutOfBoundsException e) {
			// normal
		}
	}

	/** Test if remove method works */
	public void testRemove() {
		several.remove(2);
		several.remove(0);
		several.remove(several.size() - 1);
		assertEquals("Check 2", new Integer(2), several.get(0));
		assertEquals("Check 4", new Integer(4), several.get(1));
	}

	/** Test if clear method works */
	public void testClear() {
		several.clear();
		assertEquals("Check true", true, several.isEmpty());
	}

	/** Test next method on several list */
	public void testNext() {
		ListIterator<Integer> iter = several.listIterator();
		assertEquals("Check 1", new Integer(1), iter.next());
		assertEquals("Check 2", new Integer(2), iter.next());
		assertEquals("Check 3", new Integer(3), iter.next());
		assertEquals("Check 4", new Integer(4), iter.next());
		assertEquals("Check 5", new Integer(5), iter.next());
	}

	/** Test hasPrevious method on several list */
	public void testHasPrevious() {
		ListIterator<Integer> iter = several.listIterator();
		assertEquals("Check false", false, iter.hasPrevious());
		iter.next();
		assertEquals("Check true", true, iter.hasPrevious());
	}

	/** Test NoSuchElementException on previous */
	public void testIteratorException() {
		try {
			ListIterator<Integer> iter = several.listIterator();
			iter.previous();
			fail("Should have generated an exception");
		} catch (NoSuchElementException e) {
			// normal
		}
	}

	/** Test nextIndex on one list */
	public void testNextIndex() {
		ListIterator<Integer> iter = one.listIterator();
		assertEquals("Check 1", 1, iter.nextIndex());
		iter.next();
		assertEquals("Check 1", 1, iter.nextIndex());
	}

	/** Test previousIndex on one list */
	public void testPreviousIndex() {
		ListIterator<Integer> iter = one.listIterator();
		assertEquals("Check -1", -1, iter.previousIndex());
		iter.next();
		assertEquals("Check 0", 0, iter.previousIndex());
	}

	/** Test add and remove on one list */
	public void testPrevious() {
		ListIterator<Integer> iter = one.listIterator();
		iter.next();
		iter.add(5);
		assertEquals("Check 5", new Integer(5), iter.previous());
		iter.remove();
		assertEquals("Check 0", new Integer(0), iter.previous());
	}
	
	/** Test if set returns the element previously at the position being set */
	public void testSetValue() {
		assertEquals("Check 0", new Integer(0), one.set(0, 5));
	}
}
