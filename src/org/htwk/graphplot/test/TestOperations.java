/**
 * 
 */
package org.htwk.graphplot.test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.htwk.graphplot.expression.ExpressionInterpreter;
import org.htwk.graphplot.expression.InvalidExpressionException;
import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Function;
import org.htwk.graphplot.expression.core.Operation;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit 4 test cases for all operations
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class TestOperations {

	@Before
	public void setUp() throws Exception {
		Function.loadAllFunctions();
		Operation.loadAllOperators();
	}

	private Expression getExpression(String e) throws InvalidExpressionException {
		ExpressionInterpreter interpreter = new ExpressionInterpreter(e);
		return interpreter.getExpression();
	}

	@Test
	public void testAdd() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("x+5");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), 5.0, 0.001);
		e = getExpression("x+x");
		m.put("x", 10.0);
		assertEquals(e.calculateValue(m), 20.0, 0.001);
		m.put("x", -0.5);
		assertEquals(e.calculateValue(m), -1.0, 0.001);
	}

	@Test
	public void testDivide() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("x/5");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", 5.0);
		assertEquals(e.calculateValue(m), 1.0, 0.001);
		m.put("x", -1.0);
		assertEquals(e.calculateValue(m), -0.2, 0.001);
		e = getExpression("x/x");
		m.put("x", -0.5);
		assertEquals(e.calculateValue(m), 1.0, 0.001);
		m.put("x", 0.0);
		assertTrue(Double.isNaN(e.calculateValue(m)));
	}

	@Test
	public void testMultiply() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("5x");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", 1.0);
		assertEquals(e.calculateValue(m), 5.0, 0.001);
		m.put("x", -1.0);
		assertEquals(e.calculateValue(m), -5., 0.001);
		e = getExpression("x*x");
		m.put("x", -0.5);
		assertEquals(e.calculateValue(m), 0.25, 0.001);
	}

	@Test
	public void testNegative() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("-x");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", 5.0);
		assertEquals(e.calculateValue(m), -5.0, 0.001);
		m.put("x", -1.0);
		assertEquals(e.calculateValue(m), 1.0, 0.001);
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
	}

	@Test
	public void testPower() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("x^2");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", 5.0);
		assertEquals(e.calculateValue(m), 25.0, 0.001);
		m.put("x", -1.0);
		assertEquals(e.calculateValue(m), 1.0, 0.001);
		e = getExpression("x^x");
		m.put("x", 2.0);
		assertEquals(e.calculateValue(m), 4.0, 0.001);
	}

	@Test
	public void testSubtract() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("x-5");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", 5.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
		m.put("x", -1.0);
		assertEquals(e.calculateValue(m), -6.0, 0.001);
		e = getExpression("x-x");
		m.put("x", -0.5);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
	}

}
