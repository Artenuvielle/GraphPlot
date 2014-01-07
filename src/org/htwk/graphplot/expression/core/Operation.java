package org.htwk.graphplot.expression.core;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.htwk.graphplot.expression.ClassEnumerator;
import org.htwk.graphplot.expression.InvalidExpressionException;
import org.htwk.graphplot.expression.OperationInformation;

public abstract class Operation implements Expression
{   
    public static LinkedList<OperationInformation> availableOperations = new LinkedList<OperationInformation>();
    
    private Expression expressionBeforeOperator, expressionAfterOperator;
    
    public Operation(Expression expressionBeforeOperator,
            Expression expressionAfterOperator)
    {
        this.expressionBeforeOperator = expressionBeforeOperator;
        this.expressionAfterOperator = expressionAfterOperator;
    }
    
    public Operation()
    {
        this.expressionBeforeOperator = new Number(0);
        this.expressionAfterOperator = new Number(0);
    }
    
    public void setExpressionBeforeOperator(Expression expr)
    {
        this.expressionBeforeOperator = expr;
    }
    
    public void setExpressionAfterOperator(Expression expr)
    {
        this.expressionAfterOperator = expr;
    }
    
    protected Expression getExpressionBeforeOperator()
    {
        return this.expressionBeforeOperator;
    }
    
    protected Expression getExpressionAfterOperator()
    {
        return this.expressionAfterOperator;
    }
    
    @SuppressWarnings("unchecked")
    public static void loadAllOperators()
    {
        List<Class<?>> classes = ClassEnumerator
                .getClassesForPackage(ClassEnumerator.class.getPackage());
        
        for (Class<?> c : classes) {
            if (c.getPackage().getName().contains("expression.operations")
                    && c.getSuperclass() == Operation.class) {
                String operationStringInClass = null;
                OperationImportance operationImportanceInClass = null;
                for (Field f : c.getFields()) {
                    try {
                        switch (f.getName()) {
                            case "operationString":
                                if(f.get(null) != null) operationStringInClass = (String) f.get(null);
                                break;
                            case "operationImportance":
                                if(f.get(null) != null) operationImportanceInClass = (OperationImportance) f.get(null);
                                break;
                        }
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (operationStringInClass != null && operationImportanceInClass != null) {
                    if(availableOperations.isEmpty()) {
                        availableOperations.add( 
                                new OperationInformation(
                                        (Class<Operation>)c,
                                        operationStringInClass,
                                        operationImportanceInClass
                                )
                        );
                    } else {
                        ListIterator<OperationInformation> importanceIterator = availableOperations.listIterator();
                        while(importanceIterator.hasNext() &&
                                importanceIterator.next().operationImportance.biggerThan(operationImportanceInClass));
                        if(importanceIterator.previous().operationImportance.biggerThan(operationImportanceInClass)) {
                            importanceIterator.next();
                        }
                        importanceIterator.add(
                                new OperationInformation((Class<Operation>)c,
                                        operationStringInClass,
                                        operationImportanceInClass)
                        );
                        for(OperationInformation o : availableOperations)
                            System.out.println(o.operationString + " ");
                    }
                } else {
                    System.err.println("The class " + c.getName() + " is in the expression.operations package " +
                            "but does not contain the expected values.");
                }
            }
        }
    }
    
    public static OperationInformation getOperationInformationForOperator(String operationStringToLookFor)
            throws InvalidExpressionException
    {
        for(OperationInformation currentInformation : availableOperations) {
            if(operationStringToLookFor.equals(currentInformation.operationString))
                return currentInformation;
        }
        throw new InvalidExpressionException("No such operation registered: " + operationStringToLookFor);
    }
    
    public enum OperationImportance {
        MOST_IMPORTANT(10), VERY_IMPORTANT(5), NORMALLY_IMPORTANT(0), NOT_VERY_IMPORTANT(-5), LEAST_IMPORTANT(-10);
        
        private int importanceValue;
        
        private OperationImportance(int importanceValue)
        {
            this.importanceValue = importanceValue;
        }
        
        public boolean biggerThan(OperationImportance other) {
            return importanceValue > other.importanceValue;
        }
    }
}
