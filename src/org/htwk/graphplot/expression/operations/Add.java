package org.htwk.graphplot.expression.operations;

import java.util.Map;

import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.OperationInformation;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Operation;

/**
 * This operation simulates the addition of two expressions.
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class Add extends Operation {
	
	public static final OperationInformation operationInformation = new OperationInformation(Add.class, "+", OperationImportance.LEAST_IMPORTANT);

	public Add(Expression expressionBeforeOperator, Expression expressionAfterOperator) {
		super(expressionBeforeOperator, expressionAfterOperator);
	}

	public double calculateValue(Map<String, Double> variables) throws InvalidVariableNameException {
		return getExpressionBeforeOperator().calculateValue(variables) + getExpressionAfterOperator().calculateValue(variables);
	}
	
}
