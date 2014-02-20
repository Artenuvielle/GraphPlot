package org.htwk.graphplot.expression.operations;

import java.util.Map;

import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.OperationInformation;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Operation;

/**
 * This operation simulates negative expressions by replacing them with a very important subtraction.
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class Negative extends Operation {

	public static final OperationInformation operationInformation = new OperationInformation(Negative.class, "minus", OperationImportance.VERY_IMPORTANT);

	public Negative(Expression expressionBeforeOperator, Expression expressionAfterOperator) {
		super(expressionBeforeOperator, expressionAfterOperator);
	}

	public double calculateValue(Map<String, Double> variables) throws InvalidVariableNameException {
		return getExpressionBeforeOperator().calculateValue(variables) - getExpressionAfterOperator().calculateValue(variables);
	}

}
