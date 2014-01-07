package org.htwk.graphplot.expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htwk.graphplot.expression.core.Function;
import org.htwk.graphplot.expression.core.Operation;
import org.htwk.graphplot.expression.core.Operation.OperationImportance;

public final class StringTransformer
{
    private final InitialTransformationHolder[] initialTransformations = new InitialTransformationHolder[] {
        new InitialTransformationHolder("\\s+", "", "stripping whitespaces resulted in "),
        new InitialTransformationHolder("(\\d)x", "$1*x", "Multipliers before variable resulted in "),
        new InitialTransformationHolder("(x|\\d+)", "($1)", "Numbers in brackets resulted in "),
        new InitialTransformationHolder("(?<!\\))-", "(0)minus", "Negative transformation resulted in ")
    };
    
    private String inputExpression;
    private String originalInput;
    
    public StringTransformer()
    {
        originalInput = "";
    }
    
    public StringTransformer(String input)
    {
        originalInput = input;
    }
    
    public void setStringFoFormatting(String input)
    {
        originalInput = input;
    }
    
    public String getTransformedString() throws InvalidExpressionException
    {
        Log("Original equals " + originalInput);
        inputExpression = originalInput.toLowerCase();
        performInitialTransformation();
        putAllFunctionsInBrackets();
        putAllOperationsInBrackets();
        return inputExpression;
    }

    private void performInitialTransformation() throws InvalidExpressionException
    {
        for(InitialTransformationHolder tranformation : initialTransformations) {
            String currentPattern = tranformation.getPattern();
            inputExpression = inputExpression.replaceAll(currentPattern, tranformation.getReplacement());
            Log(tranformation.getReason() + inputExpression);
        }
    }

    private void putAllFunctionsInBrackets() throws InvalidExpressionException
    {
        for (FunctionInformation info : Function.availableFunctions) {
            putSingleFunctionInBrackets(info.functionName);
            Log("Function " + info.functionName + " resulted in " + inputExpression);
        }
    }

    private void putSingleFunctionInBrackets(String function) throws InvalidExpressionException
    {
        Matcher functionMatcher;
        int endingBracket, lastFunctionPosition;
        functionMatcher = Pattern.compile(Pattern.quote(function)).matcher(inputExpression);
        lastFunctionPosition = 0;
        while (functionMatcher.find(lastFunctionPosition)) {
            endingBracket = StringTransformer.findAccordingBracket(inputExpression, functionMatcher.end());
            inputExpression = inputExpression.substring(0, functionMatcher.start()) + '('
                            + inputExpression.substring(functionMatcher.start(), endingBracket) + ')'
                            + inputExpression.substring(endingBracket, inputExpression.length());
            lastFunctionPosition = functionMatcher.end() + 1;
            functionMatcher.reset(inputExpression);
        }
    }
    
    private void putAllOperationsInBrackets() throws InvalidExpressionException {
        for (OperationInformation operator : Operation.availableOperations) {
            putSingleOperationInBrackets(operator.operationString);
            Log("Operation " + operator.operationString + " resulted in " + inputExpression);
        }
    }

    private void putSingleOperationInBrackets(String operator) throws InvalidExpressionException
    {
        Pattern operatorPattern = Pattern.compile(Pattern.quote(operator));
        int beginningBracket, endingBracket, lastOperatorPosition = 0;
        Matcher matcher = operatorPattern.matcher(inputExpression);
        
        while (matcher.find(lastOperatorPosition)) {
            // Find matching opening Brackets
            beginningBracket = StringTransformer.findAccordingBracket(inputExpression, matcher.start() - 1);
            // Find matching closing brackets
            endingBracket = StringTransformer.findAccordingBracket(inputExpression, matcher.end());
            inputExpression = inputExpression.substring(0, beginningBracket) + '('
                            + inputExpression.substring(beginningBracket, endingBracket) + ')'
                            + inputExpression.substring(endingBracket, inputExpression.length());
            lastOperatorPosition = matcher.end() + 1;
            matcher.reset(inputExpression);
        }
    }
    
    private void Log(String message) {
        System.out.println("StringTransformer: " + message);
    }

    public static int findAccordingBracket(String inputExpression, int positionOfFirstBracket) throws InvalidExpressionException
    {
        int foundOpenBrackets;
        int targetBracket;
        Boolean searchingClosingBracket = (inputExpression.charAt(positionOfFirstBracket)=='(');
        if (!searchingClosingBracket && inputExpression.charAt(positionOfFirstBracket) != ')')
            throw new InvalidExpressionException("Bracket expected near  \"" +
                    inputExpression.substring(Math.max(positionOfFirstBracket - 5, 0),
                                              Math.min(positionOfFirstBracket + 6, inputExpression.length()))
                    + "\"");
        foundOpenBrackets = 1;
        for (targetBracket = positionOfFirstBracket + (searchingClosingBracket ? 1 : -1);
            foundOpenBrackets > 0;
            targetBracket += searchingClosingBracket ? 1 : -1)
        {
            switch(inputExpression.charAt(targetBracket)) {
                case ')':
                    foundOpenBrackets += searchingClosingBracket ? -1 : 1;
                    break;
                case '(':
                    foundOpenBrackets += searchingClosingBracket ? 1 : -1;
                    break;
            }
        }
        // Return corrected position
        return targetBracket + (searchingClosingBracket ? -1 : 1);
    }
    
    private class InitialTransformationHolder {
        private String pattern, replacement, reason;
        
        protected InitialTransformationHolder(String pattern, String replacement, String reason) {
            this.pattern = pattern;
            this.replacement = replacement;
            this.reason = reason;
        }
        
        protected String getPattern() {
            return pattern;
        }
        
        protected String getReplacement() {
            return replacement;
        }
        
        protected String getReason() {
            return reason;
        }
    }
}
