package org.htwk.graphplot.expression.operations;

import java.util.Map;

import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Operation;

public class Add extends Operation
{
    public static final String operationString = "+";
    public static final OperationImportance operationImportance = OperationImportance.LEAST_IMPORTANT;
    
    public Add(Expression expressionBeforeOperator,
            Expression expressionAfterOperator)
    {
        super(expressionBeforeOperator, expressionAfterOperator);
    }
    
    public double calculateValue(Map<String, Double> variables) throws InvalidVariableNameException
    {
        return getExpressionBeforeOperator().calculateValue(variables)
             + getExpressionAfterOperator().calculateValue(variables);
    }   
}
