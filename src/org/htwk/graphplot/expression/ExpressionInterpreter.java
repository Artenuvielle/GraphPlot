package org.htwk.graphplot.expression;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.htwk.graphplot.expression.core.Expression;
import org.htwk.graphplot.expression.core.Function;
import org.htwk.graphplot.expression.core.Number;
import org.htwk.graphplot.expression.core.Operation;

/**
 * This is a factory class for expressions. It can transform a given string with
 * known operations and functions into a calculable object.
 * 
 * @author Ren� Martin, Sophie Eckenstaler
 * @version 1.0
 */
public class ExpressionInterpreter {

	private String normalizedExpressiontext;

	/**
	 * This constructor initializes the transformation of a given string into a
	 * calculable object.
	 * 
	 * @param inputExpression
	 *            The string which shall be transformed
	 * @throws InvalidExpressionException
	 *             is thrown if the given string does not represent a proper
	 *             expression.
	 */
	public ExpressionInterpreter(String inputExpression) throws InvalidExpressionException {
		StringTransformer bracketManager = new StringTransformer(inputExpression);
		normalizedExpressiontext = bracketManager.getTransformedString();
	}

	/**
	 * Creates a new calculable object from the string given in the consturctor
	 * 
	 * @return A calculable expression object
	 * @throws InvalidExpressionException
	 *             is thrown if the given string does not represent a proper
	 *             expression or has unknown functions or operations.
	 */
	public Expression getExpression() throws InvalidExpressionException {
		return recursiveExpressionInterpreter(0, normalizedExpressiontext.length() - 1);
	}

	/**
	 * Interprets a substring of the given string.
	 * 
	 * @param fromPostionInFormattedString
	 *            The position where the substring starts
	 * @param untilPostionInFormattedString
	 *            The position where the substring ends
	 * @return A calculable expression object for the determined substring
	 * @throws InvalidExpressionException
	 *             is thrown if the given string does not represent a proper
	 *             expression or has unknown functions or operations.
	 */
	private Expression recursiveExpressionInterpreter(int fromPostionInFormattedString, int untilPostionInFormattedString) throws InvalidExpressionException {
		if (StringTransformer.findAccordingBracket(normalizedExpressiontext, fromPostionInFormattedString) != untilPostionInFormattedString)
			throw new InvalidExpressionException("Ung�ltiger Ausdruck");
		if (normalizedExpressiontext.charAt(fromPostionInFormattedString + 1) == '(') {
			int endOfFirstBracket = StringTransformer.findAccordingBracket(normalizedExpressiontext, fromPostionInFormattedString + 1);
			if (endOfFirstBracket == untilPostionInFormattedString - 1) {
				// simple brackets detected
				return recursiveExpressionInterpreter(fromPostionInFormattedString + 1, untilPostionInFormattedString - 1);
			} else {
				// operation detected
				return interpretOperation(fromPostionInFormattedString, untilPostionInFormattedString, endOfFirstBracket);
			}
		} else {
			int positionOfFirstOpeningBracket = normalizedExpressiontext.indexOf("(", fromPostionInFormattedString + 1);
			int positionOfFirstClosingBracket = normalizedExpressiontext.indexOf(")", fromPostionInFormattedString + 1);
			if (positionOfFirstOpeningBracket < positionOfFirstClosingBracket && positionOfFirstOpeningBracket != -1) {
				// Function detected
				return interpretFunction(fromPostionInFormattedString, untilPostionInFormattedString, positionOfFirstOpeningBracket);
			} else {
				// Number or variable detected
				String numberOrVariable = normalizedExpressiontext.substring(fromPostionInFormattedString + 1, positionOfFirstClosingBracket);
				return new Number(numberOrVariable);
			}
		}
	}

	/**
	 * Interprets a substring of the given string as operation combining two
	 * expressions. The expected format is e.g. "(...)*(...)" for a
	 * multiplication where "..." represents other expressions.
	 * 
	 * @param fromPostionInFormattedString
	 *            The position where the substring starts
	 * @param untilPostionInFormattedString
	 *            The position where the substring ends
	 * @param endOfFirstBracket
	 *            The position where first bracket in the substring is closed
	 * @return The interpreted calculable expression object of a operation
	 * @throws InvalidExpressionException
	 *             is thrown if the given string does not represent a proper
	 *             expression or has unknown functions or operations.
	 */
	private Operation interpretOperation(int fromPostionInFormattedString, int untilPostionInFormattedString, int endOfFirstBracket) throws InvalidExpressionException {
		int startOfLastBracket = StringTransformer.findAccordingBracket(normalizedExpressiontext, untilPostionInFormattedString - 1);
		OperationInformation currentOperation = Operation.getOperationInformationForOperator(normalizedExpressiontext.substring(endOfFirstBracket + 1, startOfLastBracket));
		Expression firstExpression = recursiveExpressionInterpreter(fromPostionInFormattedString + 1, endOfFirstBracket);
		Expression secondeExpression = recursiveExpressionInterpreter(startOfLastBracket, untilPostionInFormattedString - 1);
		try {
			Constructor<? extends Operation> constructor = currentOperation.getOperationClass().getConstructor(new Class[] { Expression.class, Expression.class });
			return constructor.newInstance(firstExpression, secondeExpression);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InvalidExpressionException("Could not create operation instance", e);
		}
	}

	/**
	 * Interprets a substring of the given string as function with optional
	 * parameters. The expected format is e.g. "min(...,...[,...])" for the
	 * minimum of the given expressions where "..." represents other expression
	 * and "[,...]" optional parameters.
	 * 
	 * @param fromPostionInFormattedString
	 *            The position where the substring starts
	 * @param untilPostionInFormattedString
	 *            The position where the substring ends
	 * @param positionOfFirstOpeningBracket
	 *            The position where the bracket of the function is opened
	 * @return The interpreted calculable expression object of a function
	 * @throws InvalidExpressionException
	 *             is thrown if the given string does not represent a proper
	 *             expression or has unknown functions or operations.
	 */
	private Function interpretFunction(int fromPostionInFormattedString, int untilPostionInFormattedString, int positionOfFirstOpeningBracket) throws InvalidExpressionException {
		String functionName = normalizedExpressiontext.substring(fromPostionInFormattedString + 1, positionOfFirstOpeningBracket);
		FunctionInformation currentFunction = Function.getFunctionInformationForFunctionName(functionName);
		Expression[] parameters = interpretFunctionParameters(positionOfFirstOpeningBracket, untilPostionInFormattedString);
		try {
			Constructor<? extends Function> constructor = currentFunction.getFunctionClass().getConstructor(new Class[] { Expression[].class });
			if (!currentFunction.isParameterCountAcceptable(parameters.length))
				throw new InvalidExpressionException("The function " + currentFunction.getFunctionName() + " does not accept " + parameters.length + " parameters.");
			return constructor.newInstance((Object) parameters);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InvalidExpressionException("Could not create function instance", e);
		}
	}

	/**
	 * Interprets a substring of the given string as function parameters.
	 * 
	 * @param fromPostionInFormattedString
	 *            The position where the substring starts
	 * @param untilPostionInFormattedString
	 *            The position where the substring ends
	 * @return An array of interpreted calculable expression objects
	 * @throws InvalidExpressionException
	 *             is thrown if the given string does not represent a proper
	 *             expression or has unknown functions or operations.
	 */
	private Expression[] interpretFunctionParameters(int fromPostionInFormattedString, int untilPostionInFormattedString) throws InvalidExpressionException {
		int analyzerPosition = fromPostionInFormattedString + 1, endPositionForCurrentParameter;
		ArrayList<Expression> expressionList = new ArrayList<Expression>();
		while (analyzerPosition < untilPostionInFormattedString - 1) {
			if (normalizedExpressiontext.charAt(analyzerPosition) == ',') {
				// null expression for ",,"
				endPositionForCurrentParameter = analyzerPosition;
				expressionList.add(null);
			} else if (normalizedExpressiontext.charAt(analyzerPosition) == '(') {
				// interpret expression for ",(...),"
				endPositionForCurrentParameter = StringTransformer.findAccordingBracket(normalizedExpressiontext, analyzerPosition);
				expressionList.add(recursiveExpressionInterpreter(analyzerPosition, endPositionForCurrentParameter));
			} else {
				throw new InvalidExpressionException("Function parameters could not be correctly interpreted.");
			}
			// move beyond ","
			analyzerPosition = endPositionForCurrentParameter + 2;
		}
		// convert ArrayList into an array
		Expression[] actualReturnArray = new Expression[expressionList.size()];
		for (int i = 0; i < actualReturnArray.length; i++) {
			actualReturnArray[i] = expressionList.get(i);
		}
		return actualReturnArray;
	}

}
