package org.htwk.graphplot.expression.functions;

import java.util.Map;

import org.htwk.graphplot.expression.FunctionInformation;
import org.htwk.graphplot.expression.FunctionInformation.ParameterCountCheck;
import org.htwk.graphplot.expression.InvalidFunctionParamException;
import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Function;

/**
 * Function for calculating the next smaller integer value for an expression.
 * 
 * @author Sophie Eckenstaler, Ren� Martin
 * @version 1.0
 */
public class Floor extends Function {

	private static final ParameterCountCheck parameterCountChecker = new ParameterCountCheck() {
		public boolean check(int parameterCount) {
			if (parameterCount == 1) {
				return true;
			} else {
				return false;
			}
		}
	};
	public static final FunctionInformation functionInformation = new FunctionInformation(Floor.class, "floor", parameterCountChecker);

	private Expression param;

	public Floor(Expression[] parameters) throws InvalidFunctionParamException {
		if (parameters != null && parameterCountChecker.check(parameters.length)) {
			this.param = parameters[0];
		} else {
			throw new InvalidFunctionParamException("Invalid number of parameters for " + functionInformation.getFunctionName());
		}
	}

	public double calculateValue(Map<String, Double> variables) throws InvalidVariableNameException {
		return Math.floor(param.calculateValue(variables));
	}

}
