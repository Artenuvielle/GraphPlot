package org.htwk.graphplot.expression.functions;

import java.util.Map;

import org.htwk.graphplot.expression.InvalidFunctionParamException;
import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Function;

public class Sinus extends Function
{
    public static String functionName = "sin";
    public static int[] acceptedParameterNumber = {1};

    private Expression radicand;

    public Sinus(Expression[] parameters) throws InvalidFunctionParamException {
        if(parameters != null && parameters.length == 1) {
            this.radicand = parameters[0];
        } else {
            throw new InvalidFunctionParamException("Invalid number of parameters for sin");
        }
    }

    public double calculateValue(Map<String, Double> variables) throws InvalidVariableNameException
    {
        return Math.sin(radicand.calculateValue(variables));
    }
}
