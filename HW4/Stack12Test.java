/**
 * A simple jUnit tests for Stack12
 * 
 * @author Sunsern Cheamanunkul
 */
import junit.framework.TestCase;

//This code will not compile until you have created your Stack12 class
public class Stack12Test extends TestCase {
    private Stack12<Integer> empty;
    private Stack12<Integer> one;
    private Stack12<Integer> many;
    
    private final int CAP = 5;
    private final int N = 3;
    
	public void setUp()
	{
		empty = new Stack12<Integer>(CAP);
		one = new Stack12<Integer>(CAP);
		one.push(new Integer(15));
		many = new Stack12<Integer>(CAP);
		for ( int i = 0; i < N; i++ ) {
		    many.push(new Integer(i));
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
	
	public void testPush()
	{
	    assertTrue(empty.push(new Integer(10)));
	    assertEquals(1, empty.size());
	    assertTrue(one.push(new Integer(10)));
	    assertEquals(2, one.size());
	}
	
	public void testPushFull()
    {
        for (int i=0; i<CAP-N; i++) {
            assertTrue(many.push(new Integer(10)));
        }
        assertFalse(many.push(new Integer(1)));
    }
	
	public void testPushNull()
    {
	    try {
	        one.push(null);
	        fail("Should not insert null");
	    }
	    catch (NullPointerException e) {
	        // Pass
	    }
    }
    
	public void testPop()
    {
        assertEquals(15, one.pop().intValue());
        assertEquals(0, one.size());
        assertEquals(N-1, many.pop().intValue());
        assertEquals(N-1, many.size());
    }
    
	public void testPopEmpty()
    {
        assertEquals(null, empty.pop());
    }
	
	public void testPeek()
    {
        assertEquals(15, one.peek().intValue());
        assertEquals(1, one.size());
        assertEquals(N-1, many.peek().intValue());
        assertEquals(N, many.size());
    }
    
	public void testPeekEmpty()
    {
        assertEquals(null, empty.peek());
    }
	
}
