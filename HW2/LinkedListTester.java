import junit.framework.* ;
import java.util.LinkedList;
import java.util.ListIterator;
/**
 *  Title: class LinkedListTester
 *  Description: JUnit test class for LinkedList class
 *  @author Philip Papadopoulos
 *  @version 2.0 03-April-2014
 */
public class LinkedListTester extends TestCase
{
  private LinkedList<Integer> empty ;
  private LinkedList<Integer> one ;
  private LinkedList<Integer> several ;
  private LinkedList<String>  slist ;
  static final int DIM = 5;
  static final int FIBMAX = 30;

  public LinkedListTester()
  {
    super() ;
  }
  /**
   * Standard Test Fixture. An empty list, a list with one entry (0) and 
   * a list with several entries (0,1,2)
   */ 
  public void setUp()
  {
    empty = new LinkedList<Integer>();
    one = new LinkedList<Integer>();
    one.add(0,new Integer(0));
    several = new LinkedList<Integer>() ;
    // List: 1,2,3,...,Dim
    for (int i = DIM; i > 0; i--)
    	several.add(0,new Integer(i));

    // List: "First","Last"
    slist = new LinkedList<String>();
    slist.add(0,"First");
    slist.add(1,"Last");
  }
  /** Test if heads of the lists are correct */
  public void testGetHead()
  {
    assertEquals("Check 0",new Integer(0),one.get(0)) ;
    assertEquals("Check 0",new Integer(1),several.get(0)) ;
  }

  /** Test if size of lists are correct */
  public void testListSize()
  {
    assertEquals("Check Empty Size",0,empty.size()) ;
    assertEquals("Check One Size",1,one.size()) ;
    assertEquals("Check Several Size",DIM,several.size()) ;
  }

  /** Test setting a specific entry */
  public void testSet()
  {
    slist.set(1,"Final");
    assertEquals("Setting specific value", "Final",slist.get(1));
  }

  /** Test isEmpty */
  public void testEmpty()
  {
    assertTrue("empty is empty",empty.isEmpty()) ;
    assertTrue("one is not empty",!one.isEmpty()) ;
    assertTrue("several is not empty",!several.isEmpty()) ;
  }

  /** Test iterator on empty list and several list */
  public void testIterator()
  {
    int counter = 0 ;
    ListIterator<Integer> iter;
    for (iter = empty.listIterator() ; iter.hasNext(); )
    {
      fail("Iterating empty list and found element") ;
    }
    counter = 0 ;
    for (iter = several.listIterator() ; iter.hasNext(); iter.next())
	counter++;
    assertEquals("Iterator several count", counter, DIM);
  }

  /** Test out of bounds exception on get */
  public void testGetException()
  {
	try 
	{
		empty.get(0);
		fail("Should have generated an exception");
	}
	catch(IndexOutOfBoundsException e)
	{
		//  normal
	}
  }

  /** test Iterator Fibonacci */
  public void testIteratorFibonacci()
  {

	LinkedList<Integer> fib  = new LinkedList<Integer>();
	ListIterator<Integer> iter;
	// List: 0 1 1 2 3 5 8 13 ... 
	// Build the list with integers 1 .. FIBMAX
	int t, p = 0, q = 1;
	fib.add(0,p);
	fib.add(1,q);
	for (int k = 2; k <= FIBMAX; k++)
	{
		t = p+q;
		fib.add(k,t);
		p = q; q = t; 
	}
	// Now iterate through the list to near the middle, read the
	// previous two entries and verify the sum.
	iter = fib.listIterator();
	int sum = 0;
	for (int j = 1; j < FIBMAX/2; j++)
		sum = iter.next();
	iter.previous();
	assertEquals(iter.previous() + iter.previous(),sum);
	// Go forward with the list iterator
	assertEquals(iter.next() + iter.next(),sum);
  }
}
