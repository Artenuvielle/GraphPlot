package org.htwk.graphplot.expression;

import org.htwk.graphplot.expression.core.Function;

public class FunctionInformation {
    public Class<Function> functionClass;
    public int[] acceptedParameterNumber;
    public String functionName;
    
    public FunctionInformation(Class<Function> functionClass, String functionName, int[] acceptedParameterNumber) {
        this.functionClass = functionClass;
        this.functionName = functionName;
        this.acceptedParameterNumber = acceptedParameterNumber;
    }
}
