package org.htwk.graphplot.expression.core;

import java.util.HashMap;

import org.htwk.graphplot.expression.InvalidVariableNameException;


public class Number implements Expression
{
    private double number;
    private String variableName = null;
    
    public Number()
    {
        number = 0;
    }
    
    public Number(double number)
    {
        this.number = number;
    }
    
    public Number(String inputString)
    {
        try {
            number = Double.parseDouble(inputString);
        } catch(NumberFormatException e) {
            variableName = inputString;   
        }
    }

    public double calculateValue(HashMap<String, Double> variables) throws InvalidVariableNameException
    {
        if(variableName != null) {
            if(variables.containsKey(variableName)) {
                return variables.get(variableName).doubleValue();
            } else {
                throw new InvalidVariableNameException("The variable name " + variableName + " is not declared.");
            }
        }
        return number;
    }
}
