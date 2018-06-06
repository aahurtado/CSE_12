package hw8solutions;
import junit.framework.*;
import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

/**
 * Title: class Quantity Tester Description: JUnit test class for AST class
 *
 * @author Christine Alvarado
 * @version 1.0 20-May-2014
 */
public class ASTTester {
    private Map<String, Quantity> db = new HashMap<String, Quantity>();
    private List<String> emp = new ArrayList<String>();
    private AST fiveKphValue, twoKmValue, tenKmValue;
    private Quantity fiveKph, twoKm, tenKm;
    
    public ASTTester() {
        super();
    }
    
    /**
     * Standard Test Fixture.
     */
    public void setUp() throws Exception {
        db.put("km", new Quantity(1000, Arrays.asList("meter"), emp));
        fiveKph = new Quantity(5.0, Arrays.asList("km"), Arrays.asList("hour"));
        twoKm = new Quantity(2.0, Arrays.asList("km"), emp);
        tenKm = new Quantity(10.0, Arrays.asList("km"), emp);
        fiveKphValue = new Value(fiveKph);
        twoKmValue = new Value(twoKm);
        tenKmValue = new Value(tenKm);
    }
    
    /* Test Product Equals */
    public void testProdEval() {
        Quantity expected = new Quantity(10.0, Arrays.asList("km", "km"),
                                         Arrays.asList("hour"));
        Product target = new Product(fiveKphValue, twoKmValue);
        assertEquals(expected, target.eval(db));
    }
    
    public void testQuotientEval() {
        Quantity expected = new Quantity(2.5, emp, Arrays.asList("hour"));
        Quotient target = new Quotient(fiveKphValue, twoKmValue);
        assertEquals(expected, target.eval(db));
    }
    
    public void testPowerEval() {
        Quantity expected = new Quantity(25, Arrays.asList("km", "km"),
                                         Arrays.asList("hour","hour"));
        Power target = new Power(fiveKphValue, 2);
        assertEquals(expected, target.eval(db));
    }
    
    public void testSumEval() {
        Quantity expected = new Quantity(12, Arrays.asList("km"), emp);
        Sum target = new Sum(twoKmValue, tenKmValue);
        assertEquals(expected, target.eval(db));
    }
    
    public void testDifferenceEval() {
        Quantity expected = new Quantity(-8, Arrays.asList("km"), emp);
        Difference target = new Difference(twoKmValue, tenKmValue);
        assertEquals(expected, target.eval(db));
    }
    
    public void testNegationEval() {
        Quantity expected = new Quantity(-2, Arrays.asList("km"), emp);
        Negation target = new Negation(twoKmValue);
        assertEquals(expected, target.eval(db));
        
    }
    
    public void testValueEval() {
        Quantity expected = new Quantity(10, Arrays.asList("km"), emp);
        assertEquals(expected, tenKmValue.eval(db));
        
        expected = new Quantity(5.0, Arrays.asList("km"), Arrays.asList("hour"));
        assertEquals(expected, fiveKphValue.eval(db));
    }
    
    public void testNormalizeEval() {
        Quantity expected = new Quantity(10000, Arrays.asList("meter"), emp);
        Normalize target = new Normalize(tenKmValue);
        assertEquals(expected, target.eval(db));
    }
    
    public void testDefineEval() {
        Define target = new Define("mytenkm", tenKmValue);
        target.eval(db);
        Quantity x = db.get("mytenkm");
        if (x == null || !x.equals(tenKm)) {
            fail("Map didn't get updated after eval()");
        }
    }
    
    public void testMixedNodes() {
        Sum s1 = new Sum(tenKmValue, twoKmValue);
        Product s2 = new Product(fiveKphValue, s1);
        Quantity expected = new Quantity(60.0, Arrays.asList("km", "km"),
                                         Arrays.asList("hour"));
        assertEquals(expected, s2.eval(db));
    }
    
}
