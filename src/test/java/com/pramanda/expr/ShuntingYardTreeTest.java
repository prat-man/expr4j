package com.pramanda.expr;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ShuntingYardTreeTest extends TestCase {
	
	private ShuntingYard sy;
	
    public ShuntingYardTreeTest(String testName) {
        super(testName);
        sy = new ShuntingYardTree();
    }
    
    public static Test suite() {
        return new TestSuite(ShuntingYardTreeTest.class);
    }
    
    public void test1() {
    	double expected = 8.0298136373;
    	double actual = sy.evaluate("5+3/cos(sin(-6))^0.25");
        assertEquals(expected, actual);
    }
    
    public void test2() {
    	double expected = 99.99;
    	double actual = sy.evaluate("1e+2 - 1e-2");
        assertEquals(expected, actual);
    }
    
    public void test3() {
    	double expected = 1.0;
    	double actual = sy.evaluate("ceil(rand)");
        assertEquals(expected, actual);
    }
    
    public void test4() {
    	double expected = -1.0;
    	double actual = sy.evaluate("floor(-rand)");
        assertEquals(expected, actual);
    }
    
    public void test5() {
    	double expected = 30.0;
    	double actual = sy.evaluate("deg(asin(sin(rad(30))))");
        assertEquals(expected, actual);
    }
    
}
