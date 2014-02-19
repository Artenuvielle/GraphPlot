/**
 * 
 */
package org.htwk.graphplot.expression.functions;

import java.util.Map;

import org.htwk.graphplot.expression.InvalidFunctionParamException;
import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Function;

/**
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class Minimum extends Function {

	public static String functionName = "min";
	public static int[] acceptedParameterNumber = { 2 };

	private Expression[] parameters;

	public Minimum(Expression[] parameters) throws InvalidFunctionParamException {
		if (parameters != null && parameters.length >= 2) {
			this.parameters = parameters;
		} else {
			throw new InvalidFunctionParamException("Invalid number of parameters for sqrt");
		}
	}

	public double calculateValue(Map<String, Double> variables) throws InvalidVariableNameException {
		double min = parameters[0].calculateValue(variables);
		for(int i = 1; i < parameters.length; i++) {
			min = Math.min(min, parameters[i].calculateValue(variables));
		}
		return min;
	}

}
