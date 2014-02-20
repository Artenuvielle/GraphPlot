package org.htwk.graphplot.expression.functions;

import java.util.Map;

import org.htwk.graphplot.expression.FunctionInformation;
import org.htwk.graphplot.expression.FunctionInformation.ParameterCountCheck;
import org.htwk.graphplot.expression.InvalidFunctionParamException;
import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Function;

/**
 * Function for calculating the arc cosine value for an expression.
 * 
 * @author Sophie Eckenstaler, Ren� Martin
 * @version 1.0
 */
public class Arccos extends Function {

	private static final ParameterCountCheck parameterCountChecker = new ParameterCountCheck() {
		public boolean check(int parameterCount) {
			if (parameterCount == 1) {
				return true;
			} else {
				return false;
			}
		}
	};
	public static final FunctionInformation functionInformation = new FunctionInformation(Arccos.class, "arccos", parameterCountChecker);

	private Expression param;

	public Arccos(Expression[] parameters) throws InvalidFunctionParamException {
		if (parameters != null && parameterCountChecker.check(parameters.length)) {
			this.param = parameters[0];
		} else {
			throw new InvalidFunctionParamException("Invalid number of parameters for " + functionInformation.getFunctionName());
		}
	}

	public double calculateValue(Map<String, Double> variables) throws InvalidVariableNameException {
		return Math.acos(param.calculateValue(variables));
	}

}
