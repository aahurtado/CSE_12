/**
 * NAME: Aaron Hurtado
 * ID: A99128987
 * LOGIN: cs12scz
 */

import junit.framework.TestCase;

public class BoundedDequeTester extends TestCase {

	private Deque12<Integer> empty;
	private Deque12<Integer> one;
	private Deque12<Integer> manyFront;
	private Deque12<Integer> manyBack;

	private final int CAP = 5;
	private final int N = 3;

	/**
	 * setUp method
	 */
	public void setUp() {
		empty = new Deque12<Integer>(CAP);
		one = new Deque12<Integer>(CAP);
		one.addBack(new Integer(15));
		manyFront = new Deque12<Integer>(CAP);
		for (int i = 0; i < N; i++) {
			manyFront.addFront(new Integer(i));
		}
		manyBack = new Deque12<Integer>(CAP);
		for (int i = 0; i < N; i++) {
			manyBack.addBack(new Integer(i));
		}
	}

	/**
	 * Test Deque12 constructor.
	 */
	public void testConstructor() {
		Deque12<Integer> deque = new Deque12<Integer>(10);
		try {
			Deque12<Integer> deque2 = new Deque12<Integer>(-10);
			fail();
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Tests capacity method to make sure it returns correct value.
	 */
	public void testCapacity() {
		assertEquals(CAP, empty.capacity());
		assertEquals(CAP, one.capacity());
		assertEquals(CAP, manyFront.capacity());
		assertEquals(CAP, manyBack.capacity());
	}

	/**
	 * Tests size method to make sure it returns correct value.
	 */
	public void testSize() {
		assertEquals(0, empty.size());
		assertEquals(1, one.size());
		assertEquals(N, manyFront.size());
		assertEquals(N, manyBack.size());
	}

	/**
	 * Tests if addFront method works correctly.
	 */
	public void testAddFront() {
		assertTrue(empty.addFront(new Integer(10)));
		assertEquals(1, empty.size());
		assertTrue(one.addFront(new Integer(10)));
		assertEquals(2, one.size());
	}

	/**
	 * Tests if addBack method works correctly.
	 */
	public void testAddBack() {
		assertTrue(empty.addBack(new Integer(10)));
		assertEquals(1, empty.size());
		assertTrue(one.addBack(new Integer(10)));
		assertEquals(2, one.size());
	}

	/**
	 * Tests if addFront correctly fills up the deque.
	 */
	public void testAddFrontFull() {
		for (int i = 0; i < CAP - N; i++) {
			assertTrue(manyFront.addFront(new Integer(10)));
		}
		assertFalse(manyFront.addFront(new Integer(1)));
	}

	/**
	 * Tests if addBack correctly fills up the deque.
	 */
	public void testAddBackFull() {
		for (int i = 0; i < CAP - N; i++) {
			assertTrue(manyBack.addBack(new Integer(10)));
		}
		assertFalse(manyBack.addBack(new Integer(1)));
	}

	/**
	 * Tests if addFront won't allow the user to add a null element.
	 */
	public void testAddFrontNull() {
		try {
			one.addFront(null);
			fail("Should not insert null");
		} catch (NullPointerException e) {
			// Pass
		}
	}

	/**
	 * Tests if addBack won't allow the user to add a null element.
	 */
	public void testAddBackNull() {
		try {
			one.addBack(null);
			fail("Should not insert null");
		} catch (NullPointerException e) {
			// Pass
		}
	}

	/**
	 * Tests if removeFront works properly.
	 */
	public void testRemoveFront() {
		assertEquals(15, one.removeFront().intValue());
		assertEquals(0, one.size());
		assertEquals(N - 1, manyFront.removeFront().intValue());
		assertEquals(N - 1, manyFront.size());
	}

	/**
	 * Tests if removeBack works properly.
	 */
	public void testRemoveBack() {
		assertEquals(15, one.removeBack().intValue());
		assertEquals(0, one.size());
		assertEquals(N - 1, manyBack.removeBack().intValue());
		assertEquals(N - 1, manyBack.size());
	}

	/**
	 * Tests if removeFront will return null if the size of the deque is zero.
	 */
	public void testRemoveFrontEmpty() {
		assertEquals(null, empty.removeFront());
	}

	/**
	 * Tests if removeBack will return null if the size of the deque is zero.
	 */
	public void testRemoveBackEmpty() {
		assertEquals(null, empty.removeBack());
	}

	/**
	 * Tests if peekFront works correctly.
	 */
	public void testPeekFront() {
		assertEquals(15, one.peekFront().intValue());
		assertEquals(1, one.size());
		assertEquals(N - 1, manyFront.peekFront().intValue());
		assertEquals(N, manyFront.size());
	}

	/**
	 * Tests if peekBack works correctly.
	 */
	public void testPeekBack() {
		assertEquals(15, one.peekBack().intValue());
		assertEquals(1, one.size());
		assertEquals(N - 1, manyBack.peekBack().intValue());
		assertEquals(N, manyBack.size());
	}

	/**
	 * Tests if peekFront will return null if the size of the deque is zero.
	 */
	public void testPeekFrontEmpty() {
		assertEquals(null, empty.peekFront());
	}

	/**
	 * Tests if peekBack will return null if the size of the deque is zero.
	 */
	public void testPeekBackEmpty() {
		assertEquals(null, empty.peekBack());
	}

}
