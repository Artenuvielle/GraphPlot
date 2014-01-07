package org.htwk.graphplot.expression;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Function;
import org.htwk.graphplot.expression.core.Number;
import org.htwk.graphplot.expression.core.Operation;

public class ExpressionInterpreter
{
    private String normalizedExpressiontext;
    
    public ExpressionInterpreter(String inputExpression) throws InvalidExpressionException
    {
        StringTransformer bracketManager = new StringTransformer(inputExpression);
        normalizedExpressiontext = bracketManager.getTransformedString();
    }
    
    public Expression getExpression() throws InvalidExpressionException
    {
        return recursiveExpressionInterpreter(0, normalizedExpressiontext.length() - 1);
    }
    
    private Expression recursiveExpressionInterpreter(int fromPostionInFormattedString,
            int untilPostionInFormattedString) throws InvalidExpressionException
    {
        if (StringTransformer.findAccordingBracket(normalizedExpressiontext, fromPostionInFormattedString) != untilPostionInFormattedString)
            throw new InvalidExpressionException("Ungültiger Ausdruck");
        if (normalizedExpressiontext.charAt(fromPostionInFormattedString + 1) == '(') {
            int endOfFirstBracket = StringTransformer.findAccordingBracket(normalizedExpressiontext,
                    fromPostionInFormattedString + 1);
            if (endOfFirstBracket == untilPostionInFormattedString - 1) {
                // simple brackets detected
                return recursiveExpressionInterpreter(fromPostionInFormattedString + 1,
                        untilPostionInFormattedString - 1);
            } else {
                // operation detected
                return interpretOperation(fromPostionInFormattedString, untilPostionInFormattedString,
                        endOfFirstBracket);
            }
        } else {
            int positionOfFirstOpeningBracket = normalizedExpressiontext.indexOf("(", fromPostionInFormattedString + 1);
            int positionOfFirstClosingBracket = normalizedExpressiontext.indexOf(")", fromPostionInFormattedString + 1);
            if (positionOfFirstOpeningBracket < positionOfFirstClosingBracket && positionOfFirstOpeningBracket != -1) {
                // Function detected
                return interpretFunction(fromPostionInFormattedString, untilPostionInFormattedString,
                        positionOfFirstOpeningBracket);
            } else {
                // Number or variable detected
                String numberOrVariable = normalizedExpressiontext.substring(fromPostionInFormattedString + 1,
                        positionOfFirstClosingBracket);
                return new Number(numberOrVariable);
            }
        }
    }
    
    private Expression interpretOperation(int fromPostionInFormattedString, int untilPostionInFormattedString,
            int endOfFirstBracket) throws InvalidExpressionException
    {
        int startOfLastBracket = StringTransformer.findAccordingBracket(normalizedExpressiontext,
                untilPostionInFormattedString - 1);
        OperationInformation currentOperation = Operation.getOperationInformationForOperator(normalizedExpressiontext
                .substring(endOfFirstBracket + 1, startOfLastBracket));
        Expression firstExpression = recursiveExpressionInterpreter(fromPostionInFormattedString + 1, endOfFirstBracket);
        Expression secondeExpression = recursiveExpressionInterpreter(startOfLastBracket,
                untilPostionInFormattedString - 1);
        try {
            return currentOperation.operationClass.getConstructor(new Class[] { Expression.class, Expression.class })
                    .newInstance(firstExpression, secondeExpression);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new InvalidExpressionException("Could not create operation instance", e);
        }
    }
    
    private Expression interpretFunction(int fromPostionInFormattedString, int untilPostionInFormattedString,
            int positionOfFirstOpeningBracket) throws InvalidExpressionException
    {
        String functionName = normalizedExpressiontext.substring(fromPostionInFormattedString + 1,
                positionOfFirstOpeningBracket);
        FunctionInformation currentFunction = Function.getFunctionInformationForOperator(functionName);
        Expression[] parameters = interpretFunctionParameters(positionOfFirstOpeningBracket,
                untilPostionInFormattedString);
        try {
            Constructor<Function> c = currentFunction.functionClass.getConstructor(new Class[] { Expression[].class });
            return c.newInstance((Object)parameters);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new InvalidExpressionException("Could not create function instance", e);
        }
    }
    
    private Expression[] interpretFunctionParameters(int fromPostionInFormattedString, int untilPostionInFormattedString)
            throws InvalidExpressionException
    {
        int analyzerPosition = fromPostionInFormattedString + 1, endPositionForCurrentParameter;
        ArrayList<Expression> expressionList = new ArrayList<Expression>();
        while (analyzerPosition < untilPostionInFormattedString - 1) {
            if (normalizedExpressiontext.charAt(analyzerPosition) == ',') {
                endPositionForCurrentParameter = analyzerPosition;
                expressionList.add(null);
            } else if (normalizedExpressiontext.charAt(analyzerPosition) == '(') {
                endPositionForCurrentParameter = StringTransformer.findAccordingBracket(normalizedExpressiontext,
                        analyzerPosition);
                expressionList.add(recursiveExpressionInterpreter(analyzerPosition, endPositionForCurrentParameter));
            } else {
                throw new InvalidExpressionException("");
            }
            analyzerPosition = endPositionForCurrentParameter + 2;
        }
        if (analyzerPosition == untilPostionInFormattedString - 1)
            expressionList.add(null);
        Expression[] actualReturnArray = new Expression[expressionList.size()];
        for(int i = 0; i < actualReturnArray.length; i++) {
            actualReturnArray[i] = expressionList.get(i);
        }
        return actualReturnArray;
    }
}
