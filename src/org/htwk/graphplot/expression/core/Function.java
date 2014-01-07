package org.htwk.graphplot.expression.core;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.htwk.graphplot.expression.ClassEnumerator;
import org.htwk.graphplot.expression.FunctionInformation;
import org.htwk.graphplot.expression.InvalidExpressionException;

public abstract class Function implements Expression
{
    public static LinkedList<FunctionInformation> availableFunctions = new LinkedList<FunctionInformation>();
    
    @SuppressWarnings("unchecked")
    public static void loadAllFunctions()
    {
        List<Class<?>> classes = ClassEnumerator
                .getClassesForPackage(ClassEnumerator.class.getPackage());
        
        for (Class<?> c : classes) {
            if (c.getPackage().getName().contains("expression.function")
                    && c.getSuperclass() == Function.class) {
                String functionNameInClass = null;
                int[] acceptedParameterNumberInClass = null;
                for (Field f : c.getFields()) {
                    try {
                        switch (f.getName()) {
                            case "functionName":
                                if(f.get(null) != null) functionNameInClass = (String) f.get(null);
                                break;
                            case "acceptedParameterNumber":
                                if(f.get(null) != null) acceptedParameterNumberInClass = (int[]) f.get(null);
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
                if (functionNameInClass != null
                        && acceptedParameterNumberInClass != null) {
                    availableFunctions.add(
                            new FunctionInformation((Class<Function>) c,
                                    functionNameInClass,
                                    acceptedParameterNumberInClass));
                } else {
                    System.err.println("The class " + c.getName() + " is in the expression.functions package " +
                            "but does not contain the expected values.");
                }
            }
        }
    }
    
    public static FunctionInformation getFunctionInformationForOperator(String functionStringToLookFor)
            throws InvalidExpressionException
    {
        for(FunctionInformation currentInformation : availableFunctions) {
            if(functionStringToLookFor.equals(currentInformation.functionName))
                return currentInformation;
        }
        throw new InvalidExpressionException("No such function registered: " + functionStringToLookFor);
    }
}
