package org.htwk.graphplot.expression.functions;

import java.util.HashMap;

import org.htwk.graphplot.expression.InvalidFunctionParamException;
import org.htwk.graphplot.expression.InvalidVariableNameException;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Function;

public class SquareRoot extends Function
{
    public static String functionName = "sqrt";
    public static int[] acceptedParameterNumber = {1};

    private Expression radicand;

    public SquareRoot(Expression[] parameters) throws InvalidFunctionParamException {
        if(parameters != null && parameters.length == 1) {
            this.radicand = parameters[0];
        } else {
            throw new InvalidFunctionParamException("Invalid number of parameters for sqrt");
        }
    }

    public double calculateValue(HashMap<String, Double> variables) throws InvalidVariableNameException
    {
        return Math.sqrt(radicand.calculateValue(variables));
    }
}
