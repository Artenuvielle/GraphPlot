package org.htwk.graphplot.expression.core;

import java.util.Map;

import org.htwk.graphplot.expression.InvalidVariableNameException;

/**
 * This interface describes the most generalized form of a calculable object.
 * The mathematical term is supposed to be resolved with the use of a decorator
 * pattern and this is the generalization.
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public interface Expression {

	/**
	 * This function calculates the value of a term with a given value set of
	 * variables.
	 * 
	 * @param variables
	 *            Variable names and their associated values
	 * @return The calculated value for the term
	 * @throws InvalidVariableNameException
	 *             is thrown if a variable name is used without being declared
	 */
	public double calculateValue(Map<String, Double> variables) throws InvalidVariableNameException;

}
