package org.htwk.graphplot.expression.operations;

import java.util.Map;

import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.OperationInformation;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Operation;

/**
 * This operation simulates the subtraction of two expressions.
 * 
 * @author Sophie Eckenstaler, Ren� Martin
 * @version 1.0
 */
public class Subtract extends Operation {

	public static final OperationInformation operationInformation = new OperationInformation(Subtract.class, "-", OperationImportance.NOT_VERY_IMPORTANT);

	public Subtract(Expression expressionBeforeOperator, Expression expressionAfterOperator) {
		super(expressionBeforeOperator, expressionAfterOperator);
	}

	public double calculateValue(Map<String, Double> variables) throws InvalidVariableNameException {
		return getExpressionBeforeOperator().calculateValue(variables) - getExpressionAfterOperator().calculateValue(variables);
	}
}
