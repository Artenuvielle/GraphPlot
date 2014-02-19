package org.htwk.graphplot.expression.operations;

import java.util.Map;

import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Operation;

public class Negative extends Operation
{
    public static final String operationString = "minus";
    public static final OperationImportance operationImportance = OperationImportance.VERY_IMPORTANT;
    
    public Negative(Expression expressionBeforeOperator,
            Expression expressionAfterOperator)
    {
        super(expressionBeforeOperator, expressionAfterOperator);
    }
    
    public double calculateValue(Map<String, Double> variables) throws InvalidVariableNameException
    {
        return getExpressionBeforeOperator().calculateValue(variables)
                - getExpressionAfterOperator().calculateValue(variables);
    }
}
