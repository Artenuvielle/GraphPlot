package org.htwk.graphplot.expression.operations;

import java.util.Map;

import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.OperationInformation;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Operation;

/**
 * This operation simulates the power of two expressions.
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class Power extends Operation {

	public static final OperationInformation operationInformation = new OperationInformation(Power.class, "^", OperationImportance.MOST_IMPORTANT);

	public Power(Expression expressionBeforeOperator, Expression expressionAfterOperator) {
		super(expressionBeforeOperator, expressionAfterOperator);
	}

	public double calculateValue(Map<String, Double> variables) throws InvalidVariableNameException {
		return Math.pow(getExpressionBeforeOperator().calculateValue(variables), getExpressionAfterOperator().calculateValue(variables));
	}

}
