import java.util.Iterator;
import java.util.NoSuchElementException;
import junit.framework.TestCase;

/**
 * NAME: Aaron Hurtado
 * ID: A99128987
 * LOGIN: cs12scz
 */

public class Heap12Tester extends TestCase {

	private Heap12<Integer> emptyMin;
	private Heap12<Integer> oneMin;
	private Heap12<Integer> manyMin;

	private Heap12<Integer> emptyMax;
	private Heap12<Integer> oneMax;
	private Heap12<Integer> manyMax;

	private final int N = 3;
	private final int CAP = 5;		
	private final boolean MinHeap = false;
	private final boolean MaxHeap = true;


	/**
	 * Sets up my text fixture
	 */
	protected void setUp() throws Exception {
		emptyMin = new Heap12<Integer>(MinHeap);
		oneMin = new Heap12<Integer>(MinHeap);
		oneMin.offer(new Integer(15));
		manyMin = new Heap12<Integer>(CAP, MinHeap);
		for (int i = 0; i < N; i++) {
			manyMin.offer(new Integer(i));
		}

		emptyMax = new Heap12<Integer>(MaxHeap);
		oneMax = new Heap12<Integer>(MaxHeap);
		oneMax.offer(new Integer(15));
		manyMax = new Heap12<Integer>(CAP, MaxHeap);
		for (int i = N-1; i >= 0; i--) {
			manyMax.offer(new Integer(i));
		}
	}


	/**
	 * Test if all constructors work properly
	 */
	public void testConstructor() {
		// test normal constructor
		Heap12<Integer> heap = new Heap12<Integer>();

		// test if creating a min-heap works
		heap = new Heap12<Integer>(MinHeap);
		heap.offer(3);
		heap.offer(2);
		heap.offer(1);
		assertEquals(new Integer(1), heap.poll());
		assertEquals(new Integer(2), heap.poll());
		assertEquals(new Integer(3), heap.poll());

		// test if creating a max-heap works
		heap = new Heap12<Integer>(MaxHeap);
		heap.offer(1);
		heap.offer(2);
		heap.offer(3);
		assertEquals(new Integer(3), heap.poll());
		assertEquals(new Integer(2), heap.poll());
		assertEquals(new Integer(1), heap.poll());

		// test if copy constructor with a min-heap works
		heap = new Heap12<Integer>(manyMin);
		assertEquals(new Integer(0), heap.poll());
		assertEquals(new Integer(1), heap.poll());
		assertEquals(new Integer(2), heap.poll());

		// test if copy constructor with a max-heap works
		heap = new Heap12<Integer>(manyMax);
		assertEquals(new Integer(2), heap.poll());
		assertEquals(new Integer(1), heap.poll());
		assertEquals(new Integer(0), heap.poll());

		// test if trying to create a heap with negative capacity throws
		// an exception
		try {
			heap = new Heap12<Integer>(-10, MinHeap);
			fail();
		} catch (IllegalArgumentException e) {
			// pass
		}
		try {
			heap = new Heap12<Integer>(-10, MaxHeap);
			fail();
		} catch (IllegalArgumentException e) {
			// pass
		}
	}


	/**
	 * Test if size of heaps are correct
	 */
	public void testSize() {
		assertEquals(0, emptyMin.size());
		assertEquals(1, oneMin.size());
		assertEquals(N, manyMin.size());

		assertEquals(0, emptyMax.size());
		assertEquals(1, oneMax.size());
		assertEquals(N, manyMax.size());
	}


	/** Test iterator on emptyMin and manyMin */
	public void testIterator() {
		int counter = 0;
		Iterator<Integer> iter;
		for (iter = emptyMin.iterator(); iter.hasNext();) {
			fail("Iterating empty list and found element");
		}
		counter = 0;
		for (iter = manyMin.iterator(); iter.hasNext(); iter.next())
			counter++;
		assertEquals("Iterator several count", counter, N);
	}


	/** Test iterator on emptyMax and manyMax */
	public void testIterator2() {
		int counter = 0;
		Iterator<Integer> iter;
		for (iter = emptyMax.iterator(); iter.hasNext();) {
			fail("Iterating empty list and found element");
		}
		counter = 0;
		for (iter = manyMax.iterator(); iter.hasNext(); iter.next())
			counter++;
		assertEquals("Iterator several count", counter, N);
	}


	/**
	 * Test if calling next when there are no more elements throws proper
	 * exception
	 */
	public void testIterator3() {
		Iterator<Integer> iter = emptyMin.iterator();
		try {
			iter.next();
			fail();
		}
		catch (NoSuchElementException e) {
			// Pass
		}

		iter = emptyMax.iterator();
		try {
			iter.next();
			fail();
		}
		catch (NoSuchElementException e) {
			// Pass
		}
	}

	/**
	 * Test if calling remove when next has not been called throws proper
	 * exception
	 */
	public void testIterator4() {
		Iterator<Integer> iter = oneMin.iterator();
		try {
			iter.remove();
			fail("next method has not yet been called");
		}
		catch (IllegalStateException e) {
			// Pass
		}

		iter = oneMax.iterator();
		try {
			iter.remove();
			fail("next method has not yet been called");
		}
		catch (IllegalStateException e) {
			// Pass
		}
	}


	/**
	 * Test if iterator throws exception when the remove method has already
	 * been called after the last call to the next method
	 */
	public void testIterator5() {
		Iterator<Integer> iter = manyMin.iterator();
		iter.next();
		iter.remove();
		try {
			iter.remove();
			fail("next method has not yet been called");
		}
		catch (IllegalStateException e) {
			// Pass
		}

		iter = manyMax.iterator();
		iter.next();
		iter.remove();
		try {
			iter.remove();
			fail("next method has not yet been called");
		}
		catch (IllegalStateException e) {
			// Pass
		}
	}


	/**
	 * Test if iterator hasNext, next, and remove work
	 */
	public void testIterator6() {
		Iterator<Integer> iter = manyMin.iterator();
		assertEquals(N, manyMin.size());
		assertEquals(true, iter.hasNext());
		assertEquals(new Integer(0), iter.next());
		assertEquals(true, iter.hasNext());		
		assertEquals(new Integer(1), iter.next());
		assertEquals(true, iter.hasNext());	
		assertEquals(new Integer(2), iter.next());
		assertEquals(false, iter.hasNext());
		iter.remove();
		assertEquals(N-1, manyMin.size());

		iter = manyMax.iterator();
		assertEquals(N, manyMax.size());
		assertEquals(true, iter.hasNext());
		assertEquals(new Integer(2), iter.next());
		assertEquals(true, iter.hasNext());		
		assertEquals(new Integer(1), iter.next());
		assertEquals(true, iter.hasNext());	
		assertEquals(new Integer(0), iter.next());
		assertEquals(false, iter.hasNext());
		iter.remove();
		assertEquals(N-1, manyMax.size());
	}


	/**
	 * Test if peek method returns correct values and size remains the same
	 */
	public void testPeek() {		
		assertEquals(new Integer(15), oneMin.peek());
		assertEquals(1, oneMin.size());
		assertEquals(new Integer(0), manyMin.peek());
		assertEquals(N, manyMin.size());

		assertEquals(new Integer(15), oneMax.peek());
		assertEquals(1, oneMax.size());
		assertEquals(new Integer(2), manyMax.peek());
		assertEquals(N, manyMax.size());
	}


	/**
	 * Test if peek method returns null if heap is empty
	 */
	public void testPeekEmpty()
	{
		assertEquals(null, emptyMin.peek());
		assertEquals(null, emptyMax.peek());
	}


	/**
	 * Test if poll method returns correct value and size changes accordingly
	 */
	public void testPoll() {
		assertEquals(new Integer(15), oneMin.poll());
		assertEquals(0, oneMin.size());
		assertEquals(new Integer(0), manyMin.poll());
		assertEquals(N-1, manyMin.size());
		assertEquals(new Integer(1), manyMin.poll());
		assertEquals(N-2, manyMin.size());
		assertEquals(new Integer(2), manyMin.poll());
		assertEquals(N-3, manyMin.size());

		assertEquals(new Integer(15), oneMax.poll());
		assertEquals(0, oneMax.size());
		assertEquals(new Integer(2), manyMax.poll());
		assertEquals(N-1, manyMax.size());
		assertEquals(new Integer(1), manyMax.poll());
		assertEquals(N-2, manyMax.size());
		assertEquals(new Integer(0), manyMax.poll());
		assertEquals(N-3, manyMax.size());
	}


	/**
	 * Test if poll returns null if heap is empty
	 */
	public void testPollEmpty() {
		assertEquals(null, emptyMin.poll());
		assertEquals(null, emptyMax.poll());
	}


	/**
	 * Test if offer method works correctly
	 */
	public void testOffer() {
		assertTrue(emptyMin.offer(new Integer(10)));
		assertEquals(1, emptyMin.size());
		assertTrue(oneMin.offer(new Integer(10)));
		assertEquals(2, oneMin.size());

		assertTrue(emptyMax.offer(new Integer(10)));
		assertEquals(1, emptyMax.size());
		assertTrue(oneMax.offer(new Integer(10)));
		assertEquals(2, oneMax.size());
	}


	/**
	 * Test when offering to the heap when it is full, if the heap's backing
	 * store array will double
	 */
	public void testOfferFull() {
		for (int i=0; i < CAP-N; i++) {
			assertTrue(manyMin.offer(new Integer(10)));
		}
		assertTrue(manyMin.offer(new Integer(20)));
		assertEquals(6, manyMin.size());

		for (int i=0; i < CAP-N; i++) {
			assertTrue(manyMax.offer(new Integer(10)));
		}
		assertTrue(manyMax.offer(new Integer(20)));
		assertEquals(6, manyMax.size());
	}


	/**
	 * Test if user attempts to offer a null object if exception is thrown
	 */
	public void testOfferNull() {
		try {
			oneMin.offer(null);
			fail("Should not insert null");
		}
		catch (NullPointerException e) {
			// Pass
		}

		try {
			oneMax.offer(null);
			fail("Should not insert null");
		}
		catch (NullPointerException e) {
			// Pass
		}
	}


	/**
	 * Test if heaps return elements in the correct order
	 */
	public void testOrder() {
		Heap12<Integer> heap = new Heap12<Integer>(MinHeap);
		for (int i = 5; i >= 0; i--) {
			heap.offer(i);
		}	
		for (int i = 0; i < 6; i++) {
			assertEquals(new Integer(i), heap.poll());
		}

		heap = new Heap12<Integer>(MaxHeap);
		for (int i = 0; i < 6; i++) {
			heap.offer(i);
		}	
		for (int i = 5; i >= 0; i--) {
			assertEquals(new Integer(i), heap.poll());
		}
	}


	/**
	 * Test if heap will return data in correct order when the data is inputed
	 * in random order
	 */
	public void testOrderRandom() {
		Heap12<Integer> heap = new Heap12<Integer>(MinHeap);
		heap.offer(100);
		heap.offer(50);
		heap.offer(80);
		heap.offer(10);
		heap.offer(30);
		assertEquals(new Integer(10), heap.poll());
		assertEquals(new Integer(30), heap.poll());
		assertEquals(new Integer(50), heap.poll());
		assertEquals(new Integer(80), heap.poll());
		assertEquals(new Integer(100), heap.poll());

		heap = new Heap12<Integer>(MaxHeap);
		heap.offer(100);
		heap.offer(50);
		heap.offer(80);
		heap.offer(10);
		heap.offer(30);
		assertEquals(new Integer(100), heap.poll());
		assertEquals(new Integer(80), heap.poll());
		assertEquals(new Integer(50), heap.poll());
		assertEquals(new Integer(30), heap.poll());
		assertEquals(new Integer(10), heap.poll());		
	}

}
