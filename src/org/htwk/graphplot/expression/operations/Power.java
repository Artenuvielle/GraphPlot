package org.htwk.graphplot.expression.operations;

import java.util.Map;

import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Operation;

public class Power extends Operation
{
    public static final String operationString = "^";
    public static final OperationImportance operationImportance = OperationImportance.MOST_IMPORTANT;
    
    public Power(Expression expressionBeforeOperator,
            Expression expressionAfterOperator)
    {
        super(expressionBeforeOperator, expressionAfterOperator);
    }
    
    public double calculateValue(Map<String, Double> variables) throws InvalidVariableNameException
    {
        return Math.pow(getExpressionBeforeOperator().calculateValue(variables),
                        getExpressionAfterOperator().calculateValue(variables));
    }  
}
