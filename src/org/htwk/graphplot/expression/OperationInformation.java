package org.htwk.graphplot.expression;

import org.htwk.graphplot.expression.core.Operation;
import org.htwk.graphplot.expression.core.Operation.OperationImportance;

public class OperationInformation
{
    public Class<Operation> operationClass;
    public String operationString;
    public OperationImportance operationImportance;
    
    public OperationInformation(Class<Operation> functionClass, String operationString, OperationImportance operationImportance) {
        this.operationClass = functionClass;
        this.operationString = operationString;
        this.operationImportance = operationImportance;
    }
}
