package org.htwk.graphplot.expression.operations;

import java.util.HashMap;

import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Operation;

public class Subtract extends Operation
{
    public static final String operationString = "-";
    public static final OperationImportance operationImportance = OperationImportance.NOT_VERY_IMPORTANT;
    
    public Subtract(Expression expressionBeforeOperator,
            Expression expressionAfterOperator)
    {
        super(expressionBeforeOperator, expressionAfterOperator);
    }
    
    public double calculateValue(HashMap<String, Double> variables) throws InvalidVariableNameException
    {
        return getExpressionBeforeOperator().calculateValue(variables)
                - getExpressionAfterOperator().calculateValue(variables);
    }
}
