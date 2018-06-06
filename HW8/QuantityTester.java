package hw8solutions;
import junit.framework.* ;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
/**
 * Title: class Quantity Tester
 * Description: JUnit test class for Quantity class
 * @author Philip Papadopoulos
 * @version 2.0 19-May-2014
 */
public class QuantityTester
{
    private Map<String,Quantity> db = new HashMap<String,Quantity>();
    private List<String> emp = new ArrayList<String>();
    
    private Quantity one, gravity, distance, speed, velocity;
    public QuantityTester()
    {
        super() ;
    }
    
    /**
     * Standard Test Fixture.
     */
    public void setUp() throws Exception
    {
        db.put("km", new Quantity(1000, Arrays.asList("meter"), emp));
        db.put("day", new Quantity(24, Arrays.asList("hour"), emp));
        db.put("hour", new Quantity(60, Arrays.asList("minute"), emp));
        db.put("minute", new Quantity(60, Arrays.asList("second"), emp));
        db.put("hertz", new Quantity(1, emp, Arrays.asList("second")));
        db.put("kph", new Quantity(1, Arrays.asList("km"), Arrays.asList("hour")));
        one = new Quantity();
    }
    
    /* Normal Add */
    public void testAdd()
    {
        Quantity addEm = db.get("km").add(db.get("km"));
        assertTrue("Valid Add",
                   addEm.equals(new Quantity(2000,Arrays.asList("meter"), emp)));
    }
    
    /* Exception Add */
    public void testAddException()
    {
        try
        {
            Quantity addEm = db.get("km").add(db.get("minute"));
            fail("Add Should have Generated an Exception");
        }
        catch (IllegalArgumentException e)
        {
        }
    }
    
    public void testAddNullException()
    {
        try
        {
            Quantity addEm = db.get("km").add( null );
            fail("Add Should have Generated an Exception");
        }
        catch (IllegalArgumentException e)
        {
        }
    }
    
    
    
    /* Normal Sub */
    public void testSub()
    {
        Quantity subEm = db.get("km").sub(db.get("km"));
        assertTrue("Valid Sub",
                   subEm.equals(new Quantity(0,Arrays.asList("meter"), emp)));
    }
    
    /* Exception sub */
    public void testSubException()
    {
        try
        {
            Quantity subEm = db.get("km").sub(db.get("minute"));
            fail("Sub Should have Generated an Exception");
        }
        catch (IllegalArgumentException e)
        {
        }
    }
    
    /* Normal Multiply */
    public void testMul()
    {
        Quantity multEm = db.get("km").mul(db.get("minute"));
        assertTrue("Valid Mult",
                   multEm.equals(new Quantity(60000,Arrays.asList("meter","second"),
                                              emp)));
    }
    
    /* Exception Multiply */
    public void testMulException()
    {
        try
        {
            Quantity multEm = db.get("km").mul((Quantity)null);
            fail("Multiply Should have Generated an Exception");
        }
        catch (IllegalArgumentException e)
        {
        }
    }
    
    /* Normal Divide */
    public void testDiv()
    {
        Quantity divEm = db.get("km").div(db.get("minute"));
        assertTrue("Valid Div",
                   divEm.equals(new Quantity(1000./60.,Arrays.asList("meter"),
                                             Arrays.asList("second"))));
    }
    
    /* Exception Divide */
    public void testDivException()
    {
        try
        {
            Quantity divEm = db.get("km").div((Quantity)null);
            fail("Divide Should have Generated an Exception (null arg)");
        }
        catch (IllegalArgumentException e)
        {
        }
        try
        {
            Quantity divEm = db.get("km").div(new Quantity(0.0));
            fail("Divide Should have Generated an Exception (0 divisor)");
        }
        catch (IllegalArgumentException e)
        {
        }
    }
    
    /* Normal Power */
    public void testPow()
    {
        Quantity powEm = db.get("km").pow(2);
        assertTrue("Valid Power",
                   powEm.equals(new Quantity(1000.*1000.,
                                             Arrays.asList("meter", "meter"), emp)));
    }
    
    public void testZeroPow()
    {
        Quantity powEm = db.get("km").pow(0);
        assertTrue( "Zero Power", powEm.equals( new Quantity( 1, emp, emp ) ) );
    }
    
    public void testHashCode()
    {
        Quantity  localday = new Quantity(24, Arrays.asList("hour"), emp);
        assertEquals("Hashcode", db.get("day").hashCode(), localday.hashCode());
    }
    
    public void testNormalizeUnit()
    {
        Quantity smoot = new Quantity(1, Arrays.asList("smoot"), emp);
        Quantity days = new Quantity(24*60*60, Arrays.asList("second"), emp);
        assertTrue("Normalize smoot",
                   smoot.equals(Quantity.normalizedUnit("smoot", db)));
        assertTrue("Normalize day",
                   days.equals(Quantity.normalizedUnit("day", db)));
    }
}


// VIM: set the tabstop and shiftwidth to 4 
// vim:tw=78:ts=4:sw=4
