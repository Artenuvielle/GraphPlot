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
 * JUnit 4 test cases for all functions
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class TestFunctions {

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
	public void testAbs() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("abs(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
		m.put("x", 10.0);
		assertEquals(e.calculateValue(m), 10.0, 0.001);
		m.put("x", -10.0);
		assertEquals(e.calculateValue(m), 10.0, 0.001);
	}

	@Test
	public void testArccos() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("arccos(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), Math.PI / 2, 0.001);
		m.put("x", -1.0);
		assertEquals(e.calculateValue(m), Math.PI, 0.001);
		m.put("x", 1.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
	}

	@Test
	public void testArcsin() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("arcsin(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
		m.put("x", -1.0);
		assertEquals(e.calculateValue(m), -Math.PI / 2, 0.001);
		m.put("x", 1.0);
		assertEquals(e.calculateValue(m), Math.PI / 2, 0.001);
	}

	@Test
	public void testArctan() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("arctan(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), 0, 0.001);
		m.put("x", -4.0);
		assertEquals(e.calculateValue(m), Math.atan(-4), 0.001);
		m.put("x", 4.0);
		assertEquals(e.calculateValue(m), Math.atan(4), 0.001);
	}

	@Test
	public void testCeil() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("ceil(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), 0, 0.001);
		m.put("x", -1.9);
		assertEquals(e.calculateValue(m), -1.0, 0.001);
		m.put("x", 1.1);
		assertEquals(e.calculateValue(m), 2.0, 0.001);
	}

	@Test
	public void testFloor() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("floor(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), 0, 0.001);
		m.put("x", -1.9);
		assertEquals(e.calculateValue(m), -2.0, 0.001);
		m.put("x", 1.1);
		assertEquals(e.calculateValue(m), 1.0, 0.001);
	}

	@Test
	public void testCosine() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("cos(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", -Math.PI);
		assertEquals(e.calculateValue(m), -1.0, 0.001);
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), 1.0, 0.001);
		m.put("x", Math.PI / 2);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
	}

	@Test
	public void testCosineHyperbolic() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("cosh(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", -2.0);
		assertEquals(e.calculateValue(m), Math.cosh(-2), 0.001);
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), 1.0, 0.001);
		m.put("x", 1.0);
		assertEquals(e.calculateValue(m), Math.cosh(1), 0.001);
	}

	@Test
	public void testCotangent() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("cot(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", -Math.PI / 2);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
		m.put("x", 0.0);
		assertTrue(Double.isInfinite(e.calculateValue(m)));
		m.put("x", 1.0);
		assertEquals(e.calculateValue(m), 1 / Math.tan(1), 0.001);
	}

	@Test
	public void testEuler() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("e()");
		HashMap<String, Double> m = new HashMap<String, Double>();
		assertEquals(e.calculateValue(m), Math.E, 0.001);
	}

	@Test
	public void testPi() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("pi()");
		HashMap<String, Double> m = new HashMap<String, Double>();
		assertEquals(e.calculateValue(m), Math.PI, 0.001);
	}

	@Test
	public void testLogarithm() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("log(x,(y))");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", 3.0);
		m.put("y", 10.0);
		assertEquals(e.calculateValue(m), Math.log10(3), 0.001);
		m.put("x", 1.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
		m.put("x", 13.44);
		m.put("y", Math.E);
		assertEquals(e.calculateValue(m), Math.log(13.44), 0.001);
	}

	@Test
	public void testLogarithmDecade() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("lg(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", 10.0);
		assertEquals(e.calculateValue(m), 1.0, 0.001);
		m.put("x", 1.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
		m.put("x", 1000.0);
		assertEquals(e.calculateValue(m), 3.0, 0.001);
	}

	@Test
	public void testLogarithmDual() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("ld(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", 4.0);
		assertEquals(e.calculateValue(m), 2.0, 0.001);
		m.put("x", 1.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
		m.put("x", 1024.0);
		assertEquals(e.calculateValue(m), 10.0, 0.001);
	}

	@Test
	public void testLogarithmNatural() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("ln(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", Math.E);
		assertEquals(e.calculateValue(m), 1.0, 0.001);
		m.put("x", 1.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
		m.put("x", Math.exp(17.13));
		assertEquals(e.calculateValue(m), 17.13, 0.001);
	}

	@Test
	public void testMaximum() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("max(0,x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", Math.E);
		assertEquals(e.calculateValue(m), Math.E, 0.001);
		m.put("x", -1.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
	}

	@Test
	public void testMinimum() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("min(0,x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", Math.E);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
		m.put("x", -1.0);
		assertEquals(e.calculateValue(m), -1.0, 0.001);
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
	}

	@Test
	public void testRound() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("round(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", Math.E);
		assertEquals(e.calculateValue(m), 3.0, 0.001);
		m.put("x", -0.9);
		assertEquals(e.calculateValue(m), -1.0, 0.001);
		m.put("x", -0.5);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
	}

	@Test
	public void testSignum() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("sign(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", -Math.E);
		assertEquals(e.calculateValue(m), -1.0, 0.001);
		m.put("x", 0.01);
		assertEquals(e.calculateValue(m), 1.0, 0.001);
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
	}

	@Test
	public void testSine() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("sin(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", Math.PI / 2 * 3);
		assertEquals(e.calculateValue(m), -1.0, 0.001);
		m.put("x", Math.PI / 2);
		assertEquals(e.calculateValue(m), 1.0, 0.001);
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
	}

	@Test
	public void testSineHyperbolic() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("sinh(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", -Math.E);
		assertEquals(e.calculateValue(m), Math.sinh(-Math.E), 0.001);
		m.put("x", 0.01);
		assertEquals(e.calculateValue(m), Math.sinh(0.01), 0.001);
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
	}

	@Test
	public void testSquareRoot() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("sqrt(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", -Math.E);
		assertTrue(Double.isNaN(e.calculateValue(m)));
		m.put("x", 64.0);
		assertEquals(e.calculateValue(m), 8.0, 0.001);
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
	}

	@Test
	public void testTangent() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("tan(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", Math.PI / 2);
		assertEquals(e.calculateValue(m), Math.tan(Math.PI / 2), 0.001);
		m.put("x", 1.0);
		assertEquals(e.calculateValue(m), Math.tan(1.0), 0.001);
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
	}

	@Test
	public void testTangentHyperbolic() throws InvalidExpressionException, InvalidVariableNameException {
		Expression e = getExpression("tanh(x)");
		HashMap<String, Double> m = new HashMap<String, Double>();
		m.put("x", -Math.E);
		assertEquals(e.calculateValue(m), Math.tanh(-Math.E), 0.001);
		m.put("x", 0.01);
		assertEquals(e.calculateValue(m), Math.tanh(0.01), 0.001);
		m.put("x", 0.0);
		assertEquals(e.calculateValue(m), 0.0, 0.001);
	}

}
