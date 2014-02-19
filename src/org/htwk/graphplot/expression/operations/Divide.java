package org.htwk.graphplot.expression.operations;

import java.util.Map;

import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Operation;

public class Divide extends Operation
{
    public static final String operationString = "/";
    public static final OperationImportance operationImportance = OperationImportance.NORMALLY_IMPORTANT;
    
    public Divide(Expression expressionBeforeOperator,
            Expression expressionAfterOperator)
    {
        super(expressionBeforeOperator, expressionAfterOperator);
    }
    
    public double calculateValue(Map<String, Double> variables) throws InvalidVariableNameException
    {
        double valueOfExpressionafterOperator = getExpressionAfterOperator().calculateValue(variables);
        if(valueOfExpressionafterOperator == 0) return Double.NaN;
        return getExpressionBeforeOperator().calculateValue(variables)
             / valueOfExpressionafterOperator;
    }  
}