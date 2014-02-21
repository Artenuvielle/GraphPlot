package org.htwk.graphplot.expression.operations;

import java.util.Map;

import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.OperationInformation;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Operation;

/**
 * This operation simulates the division of two expressions.
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class Divide extends Operation {
	
	public static final OperationInformation operationInformation = new OperationInformation(Divide.class, "/", OperationImportance.A_BIT_IMPORTANT);

	public Divide(Expression expressionBeforeOperator, Expression expressionAfterOperator) {
		super(expressionBeforeOperator, expressionAfterOperator);
	}

	public double calculateValue(Map<String, Double> variables) throws InvalidVariableNameException {
		double valueOfExpressionafterOperator = getExpressionAfterOperator().calculateValue(variables);
		if (valueOfExpressionafterOperator == 0)
			return Double.NaN;
		return getExpressionBeforeOperator().calculateValue(variables) / valueOfExpressionafterOperator;
	}
	
}
