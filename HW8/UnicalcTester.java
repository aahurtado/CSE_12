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

public class UnicalcTester{
    private Unicalc calc1, calc2;
    private List<String> emp = new ArrayList<String>();
    private AST oneVal, twoVal, threeVal, kmVal;
    private Quantity one, two, three, km;
    
    public UnicalcTester() {
        super();
    }
    
    /**
     * Standard Test Fixture.
     */
    public void setUp() throws Exception {
        calc1 = new Unicalc();
        calc2 = new Unicalc();
        km = new Quantity(1.0, Arrays.asList("km"), emp);
        one = new Quantity(1.0, emp, emp);
        two = new Quantity(2.0, emp, emp);
        three = new Quantity(3.0, emp, emp);
        oneVal = new Value(one);
        twoVal = new Value(two);
        threeVal = new Value(three);
        kmVal = new Value(km);
    }
    
    public void testS_def() {
        calc1.tokenize("def smoot 2");
        AST tree = calc1.S();
        assertEquals(tree, new Define("smoot", twoVal));
    }
    
    public void testS_L() {
        calc1.tokenize("1 + 2");
        calc2.tokenize("1 + 2");
        AST tree = calc1.S();
        assertEquals(tree, calc2.L());
    }
    
    public void testL_normalize() {
        calc1.tokenize("# 1 km");
        AST tree = calc1.L();
        assertEquals(tree, new Normalize(new Product(oneVal, kmVal)));
    }
    
    public void testL_E() {
        calc1.tokenize("1 + 2");
        calc2.tokenize("1 + 2");
        AST tree = calc1.L();
        assertEquals(tree, calc2.E());
    }
    public void testE_plus() {
        calc1.tokenize("1 + 2");
        AST tree = calc1.E();
        assertEquals(tree, new Sum(oneVal, twoVal));
    }
    public void testE_minus() {
        calc1.tokenize("1 - 2");
        AST tree = calc1.E();
        assertEquals(tree, new Difference(oneVal, twoVal));
    }
    public void testP_muliply() {
        calc1.tokenize("2 * 3");
        AST tree = calc1.P();
        assertEquals(tree, new Product(twoVal, threeVal));
    }
    public void testP_divide() {
        calc1.tokenize("2 / 3");
        AST tree = calc1.P();
        assertEquals(tree, new Quotient(twoVal, threeVal));
    }
    public void testK_negate() {
        calc1.tokenize("-2");
        AST tree = calc1.K();
        assertEquals(tree, new Negation(twoVal));
    }
    public void testK_Q() {
        calc1.tokenize("km");
        calc2.tokenize("km");
        AST tree = calc1.K();
        assertEquals(tree, calc2.Q());
    }
    public void testQ_R() {
        calc1.tokenize("km");
        calc2.tokenize("km");
        AST tree = calc1.Q();
        assertEquals(tree, calc2.R());
    }
    public void testQ_RQ() {
        calc1.tokenize("1 km");
        AST tree = calc1.Q();
        assertEquals(tree, new Product(oneVal, kmVal));
    }
    public void testR_V() {
        calc1.tokenize("2.0");
        calc2.tokenize("2.0");
        AST tree = calc1.R();
        assertEquals(tree, calc2.V());
    }
    public void testR_VExpJ() {
        calc1.tokenize("1^2");
        AST tree = calc1.R();
        assertEquals(tree, new Power(oneVal, 2));
    }
    
    
}
