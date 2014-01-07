package org.htwk.graphplot.expression.core;

import java.util.HashMap;

import org.htwk.graphplot.expression.InvalidVariableNameException;

public interface Expression
{
    public double calculateValue(HashMap<String, Double> variables) throws InvalidVariableNameException;
}
