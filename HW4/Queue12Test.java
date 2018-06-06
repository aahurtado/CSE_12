/**
 * A simple jUnit tests for Queue12
 * 
 * @author Sunsern Cheamanunkul
 */
import junit.framework.TestCase;


// This code will not compile until you have created your Queue12 class
public class Queue12Test extends TestCase {
    private Queue12<Integer> empty;
    private Queue12<Integer> one;
    private Queue12<Integer> many;
    
    private final int CAP = 5;
    private final int N = 3;
    
	public void setUp()
	{
		empty = new Queue12<Integer>(CAP);
		one = new Queue12<Integer>(CAP);
		one.enqueue(new Integer(15));
		many = new Queue12<Integer>(CAP);
		for ( int i = 0; i < N; i++ ) {
		    many.enqueue(new Integer(i));
		}
	}
	
	public void testSize()
	{
		assertEquals(empty.size(), 0);
		assertEquals(one.size(), 1);
		assertEquals(many.size(), N);
	}
	
	public void testCapacity()
	{
	    assertEquals(CAP, empty.capacity() );
	    assertEquals(CAP, one.capacity() );
	    assertEquals(CAP, many.capacity() );
	}
	
	public void testEnqueue()
	{
	    assertTrue(empty.enqueue(new Integer(10)));
	    assertEquals(1, empty.size());
	    assertTrue(one.enqueue(new Integer(10)));
	    assertEquals(2, one.size());
	}
	
	public void testEnqueueFull()
    {
        for (int i=0; i<CAP-N; i++) {
            assertTrue(many.enqueue(new Integer(10)));
        }
        assertFalse(many.enqueue(new Integer(1)));
    }
	
	public void testEnqueueNull()
    {
	    try {
	        one.enqueue(null);
	        fail("Should not insert null");
	    }
	    catch (NullPointerException e) {
	        // Pass
	    }
    }
    
	public void testDequeue()
    {
        assertEquals(15, one.dequeue().intValue());
        assertEquals(0, one.size());
        assertEquals(0, many.dequeue().intValue());
        assertEquals(N-1, many.size());
    }
    
	public void testDequeueEmpty()
    {
        assertEquals(null, empty.dequeue());
    }
	
	public void testPeek()
    {
	    assertEquals(15, one.peek().intValue());
        assertEquals(1, one.size());
        assertEquals(0, many.peek().intValue());
        assertEquals(N, many.size());
    }
    
	public void testPeekEmpty()
    {
        assertEquals(null, empty.peek());
    }
	
}
