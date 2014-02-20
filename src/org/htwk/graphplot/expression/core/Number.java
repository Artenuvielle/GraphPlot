package org.htwk.graphplot.expression.core;

import java.util.Map;

import org.htwk.graphplot.expression.InvalidVariableNameException;

/**
 * This is the basic implementation of Expression for the decorator pattern. It
 * holds a simple number value or the name of an variable which can be
 * interpreted.
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class Number implements Expression {

	private double number;
	private String variableName = null;

	/**
	 * Constructs an empty expression object which will be represented as the
	 * number zero.
	 */
	public Number() {
		number = 0;
	}

	/**
	 * Constructs an expression value by using a given numerical value.
	 * 
	 * @param number
	 *            The given number.
	 */
	public Number(double number) {
		this.number = number;
	}

	/**
	 * Constructs an expression by using a given variable name. Its value can
	 * only be determined at calculation time.
	 * 
	 * @param inputString
	 *            The name of the given variable.
	 */
	public Number(String inputString) {
		try {
			number = Double.parseDouble(inputString);
		} catch (NumberFormatException e) {
			variableName = inputString;
		}
	}

	/**
	 * Returns the value of the number this object holds or the value of the
	 * variable name given by the calling instance. If a variable name is not
	 * defined an exception will be thrown.
	 */
	public double calculateValue(Map<String, Double> variables) throws InvalidVariableNameException {
		if (variableName != null) {
			if (variables.containsKey(variableName)) {
				return variables.get(variableName).doubleValue();
			} else {
				throw new InvalidVariableNameException("The variable name " + variableName + " is not declared.");
			}
		}
		return number;
	}

}
