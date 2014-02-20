/**
 * 
 */
package org.htwk.graphplot.expression.functions;

import java.util.Map;

import org.htwk.graphplot.expression.FunctionInformation;
import org.htwk.graphplot.expression.FunctionInformation.ParameterCountCheck;
import org.htwk.graphplot.expression.InvalidFunctionParamException;
import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Function;

/**
 * Function for selecting the biggest of any number of given expressions.
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class Maximum extends Function {

	private static final ParameterCountCheck parameterCountChecker = new ParameterCountCheck() {
		public boolean check(int parameterCount) {
			if (parameterCount >= 2) {
				return true;
			} else {
				return false;
			}
		}
	};
	public static final FunctionInformation functionInformation = new FunctionInformation(Maximum.class, "max", parameterCountChecker);

	private Expression[] parameters;

	public Maximum(Expression[] parameters) throws InvalidFunctionParamException {
		if (parameters != null && parameterCountChecker.check(parameters.length)) {
			this.parameters = parameters;
		} else {
			throw new InvalidFunctionParamException("Invalid number of parameters for " + functionInformation.getFunctionName());
		}
	}

	public double calculateValue(Map<String, Double> variables) throws InvalidVariableNameException {
		double max = parameters[0].calculateValue(variables);
		for (int i = 1; i < parameters.length; i++) {
			max = Math.max(max, parameters[i].calculateValue(variables));
		}
		return max;
	}

}
