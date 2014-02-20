package org.htwk.graphplot.expression.functions;

import java.util.Map;

import org.htwk.graphplot.expression.FunctionInformation;
import org.htwk.graphplot.expression.FunctionInformation.ParameterCountCheck;
import org.htwk.graphplot.expression.InvalidFunctionParamException;
import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Function;

/**
 * Function for calculating the logarithm value for an expression and an
 * expression as base.
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class Logarithm extends Function {

	private static final ParameterCountCheck parameterCountChecker = new ParameterCountCheck() {
		public boolean check(int parameterCount) {
			if (parameterCount == 2) {
				return true;
			} else {
				return false;
			}
		}
	};
	public static final FunctionInformation functionInformation = new FunctionInformation(Logarithm.class, "log", parameterCountChecker);

	private Expression param;
	private Expression base;

	public Logarithm(Expression[] parameters) throws InvalidFunctionParamException {
		if (parameters != null && parameterCountChecker.check(parameters.length)) {
			this.param = parameters[0];
			this.base = parameters[1];
		} else {
			throw new InvalidFunctionParamException("Invalid number of parameters for " + functionInformation.getFunctionName());
		}
	}

	public double calculateValue(Map<String, Double> variables) throws InvalidVariableNameException {
		return Math.log(param.calculateValue(variables)) / Math.log(base.calculateValue(variables));
	}

}
