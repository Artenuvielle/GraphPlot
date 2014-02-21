package org.htwk.graphplot.expression;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htwk.graphplot.expression.core.Function;
import org.htwk.graphplot.expression.core.Operation;

/**
 * This class shall help to perform basic string transformations in order to
 * create a standardized string format for a correct interpretation of the
 * string as expression.
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public final class StringTransformer {

	private static final Logger logger = Logger.getLogger(StringTransformer.class.getName());

	/*
	 * This array holds regular expression patterns and replacement strings for
	 * the initial string transformations. These are:
	 * 
	 * - remove all whitespace characters
	 * 
	 * - move commas and divide these numbers by 10 instead
	 * 
	 * - remove all commas after they have no numbers after them anymore
	 * 
	 * - insert multiplication symbols between numbers and variables
	 * 
	 * - insert brackets around numbers and variables
	 * 
	 * - analyze negative signs and transform them into important subtractions
	 */
	private final InitialTransformationHolder[] initialTransformations = new InitialTransformationHolder[] { new InitialTransformationHolder("\\s+", "", "Stripping whitespaces resulted in "),
			new InitialTransformationHolder("(\\d*)\\.(\\d)", "$1$2/10", "Moving commas reulted in ", true), new InitialTransformationHolder("(\\d+)\\.", "$1", "Removing commas reulted in "), new InitialTransformationHolder("(\\d)x", "$1*x", "Multipliers before variable resulted in "),
			new InitialTransformationHolder("(\\d+)", "($1)", "Numbers in brackets resulted in "), new InitialTransformationHolder("(?<![a-z])(x)(?![a-z])", "($1)", "Variables in brackets resulted in "),
			new InitialTransformationHolder("(?<!\\))-", "(0)minus", "Negative transformation resulted in ") };

	private String inputExpression;
	private String originalInput;

	/**
	 * Construct an objects for the transformation of the given string.
	 * 
	 * @param input
	 *            The string which shall be transformed
	 */
	public StringTransformer(String input) {
		setStringForFormatting(input);
	}

	/**
	 * Set the untransformed String.
	 * 
	 * @param input
	 *            The untransformed string
	 */
	public void setStringForFormatting(String input) {
		originalInput = input;
	}

	/**
	 * Finish the transformation of the given String and get a well formatted
	 * string.
	 * 
	 * @return Formatted string
	 * @throws InvalidExpressionException
	 *             is thrown if the given string does not represent a proper
	 *             expression.
	 */
	public String getTransformedString() throws InvalidExpressionException {
		logger.info("Original equals " + originalInput);
		inputExpression = originalInput.toLowerCase();
		performInitialTransformation();
		putAllFunctionsInBrackets();
		putAllOperationsInBrackets();
		return inputExpression;
	}

	/**
	 * Cycles through the array of innitial RegEx transformations and processes
	 * the given string with them.
	 * 
	 * @throws InvalidExpressionException
	 *             is thrown if the given string does not represent a proper
	 *             expression.
	 */
	private void performInitialTransformation() throws InvalidExpressionException {
		for (InitialTransformationHolder tranformation : initialTransformations) {
			boolean stringStaysConstant;
			do {
				String currentPattern = tranformation.getPattern();
				String result = inputExpression.replaceAll(currentPattern, tranformation.getReplacement());
				stringStaysConstant = result.equals(inputExpression);
				inputExpression = result;
				logger.fine(tranformation.getReason() + inputExpression);
			} while (tranformation.getRepeatAllways() && !stringStaysConstant);
		}
	}

	/**
	 * Cycle through all known functions, searches the given string for them and
	 * put these occurrences in brackets.
	 * 
	 * @throws InvalidExpressionException
	 *             is thrown if the given string does not represent a proper
	 *             expression.
	 */
	private void putAllFunctionsInBrackets() throws InvalidExpressionException {
		for (FunctionInformation info : Function.availableFunctions) {
			putSingleFunctionInBrackets(info.getFunctionName());
			logger.fine("Function " + info.getFunctionName() + " resulted in " + inputExpression);
		}
	}

	/**
	 * Search the given string for a given function name and put these
	 * occurrences in brackets.
	 * 
	 * @param function
	 *            The function name to search for
	 * @throws InvalidExpressionException
	 *             is thrown if the given string does not represent a proper
	 *             expression.
	 */
	private void putSingleFunctionInBrackets(String function) throws InvalidExpressionException {
		Matcher functionMatcher;
		int endingBracket, lastFunctionPosition;
		functionMatcher = Pattern.compile("(?<![a-z])" + Pattern.quote(function + "(")).matcher(inputExpression);
		lastFunctionPosition = 0;
		while (functionMatcher.find(lastFunctionPosition)) {
			endingBracket = StringTransformer.findAccordingBracket(inputExpression, functionMatcher.end() - 1);
			putBracketsAroundArea(functionMatcher.start(), endingBracket);
			lastFunctionPosition = functionMatcher.end();
			functionMatcher.reset(inputExpression);
		}
	}

	/**
	 * Cycle through all known operations, searches the given string for them
	 * and put these occurrences in brackets.
	 * 
	 * @throws InvalidExpressionException
	 *             is thrown if the given string does not represent a proper
	 *             expression.
	 */
	private void putAllOperationsInBrackets() throws InvalidExpressionException {
		for (OperationInformation operator : Operation.availableOperations) {
			putSingleOperationInBrackets(operator.getOperationString());
			logger.fine("Operation " + operator.getOperationString() + " resulted in " + inputExpression);
		}
	}

	/**
	 * Search the given string for a given operator string and put these
	 * occurrences in brackets.
	 * 
	 * @param operator
	 * @throws InvalidExpressionException
	 *             is thrown if the given string does not represent a proper
	 *             expression.
	 */
	private void putSingleOperationInBrackets(String operator) throws InvalidExpressionException {
		Pattern operatorPattern = Pattern.compile(Pattern.quote(operator));
		int beginningBracketPosition, endingBracketPosition, lastOperatorPosition = 0;
		Matcher matcher = operatorPattern.matcher(inputExpression);

		while (matcher.find(lastOperatorPosition)) {
			// Find matching opening Brackets
			beginningBracketPosition = StringTransformer.findAccordingBracket(inputExpression, matcher.start() - 1);
			// Find matching closing brackets
			endingBracketPosition = StringTransformer.findAccordingBracket(inputExpression, matcher.end());
			putBracketsAroundArea(beginningBracketPosition, endingBracketPosition);
			lastOperatorPosition = matcher.end() + 1;
			matcher.reset(inputExpression);
		}
	}

	/**
	 * Inserts an additional pair of brackets in the transformation string at
	 * the given positions.
	 * 
	 * @param fromPosition
	 *            The position to insert an opening bracket
	 * @param toPosition
	 *            The position to insert a closing bracket
	 */
	private void putBracketsAroundArea(int fromPosition, int toPosition) {
		String beforeBracketArea = inputExpression.substring(0, fromPosition);
		String actualBracketArea = inputExpression.substring(fromPosition, toPosition);
		String afterBracketArea = inputExpression.substring(toPosition, inputExpression.length());
		inputExpression = beforeBracketArea + '(' + actualBracketArea + ')' + afterBracketArea;
	}

	/**
	 * This class finds an according opening or closing bracket in a
	 * mathematical term given as string.
	 * 
	 * @param inputExpression
	 *            The mathematical term as string
	 * @param positionOfFirstBracket
	 *            The position of the first bracket for which the according
	 *            bracket shall be found
	 * @return The position of the opening or closing bracket in the given
	 *         string
	 * @throws InvalidExpressionException
	 *             is thrown if the given string does not represent a proper
	 *             expression.
	 */
	public static int findAccordingBracket(String inputExpression, int positionOfFirstBracket) throws InvalidExpressionException {
		int foundOpenBrackets, targetBracket;
		Boolean searchingClosingBracket = (inputExpression.charAt(positionOfFirstBracket) == '(');
		if (!searchingClosingBracket && inputExpression.charAt(positionOfFirstBracket) != ')')
			throw new InvalidExpressionException("Bracket expected near  \"" + inputExpression.substring(Math.max(positionOfFirstBracket - 5, 0), Math.min(positionOfFirstBracket + 6, inputExpression.length())) + "\"");
		foundOpenBrackets = 1;
		for (targetBracket = positionOfFirstBracket + (searchingClosingBracket ? 1 : -1); foundOpenBrackets > 0; targetBracket += searchingClosingBracket ? 1 : -1) {
			switch (inputExpression.charAt(targetBracket)) {
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

	/**
	 * This private class hold information about initial string transformations.
	 * 
	 * @author Sophie Eckenstaler, René Martin
	 * @version 1.0
	 */
	private class InitialTransformationHolder {

		private String pattern, replacement, reason;
		private boolean repeatAllways = false;

		/**
		 * Instantiate an object to hold information for a regular expression
		 * replacement.
		 * 
		 * @param pattern
		 *            The RegEx pattern to search for
		 * @param replacement
		 *            The RegEx replacement to replace the patterns with
		 * @param reason
		 *            A string which will be print in the log as the reason for
		 *            the replacement
		 */
		protected InitialTransformationHolder(String pattern, String replacement, String reason) {
			this.pattern = pattern;
			this.replacement = replacement;
			this.reason = reason;
		}

		/**
		 * Instantiate an object to hold information for a regular expression
		 * replacement.
		 * 
		 * @param pattern
		 *            The RegEx pattern to search for
		 * @param replacement
		 *            The RegEx replacement to replace the patterns with
		 * @param reason
		 *            A string which will be print in the log as the reason for
		 *            the replacement
		 * @param repeatUntilNotFoundAnymore
		 *            True, if this transformation should be applied until it is
		 *            not found anymore
		 */
		protected InitialTransformationHolder(String pattern, String replacement, String reason, boolean repeatUntilNotFoundAnymore) {
			this.pattern = pattern;
			this.replacement = replacement;
			this.reason = reason;
			this.repeatAllways = repeatUntilNotFoundAnymore;
		}

		/**
		 * Get the RegEx pattern for the replacement
		 * 
		 * @return The RegEx pattern
		 */
		protected String getPattern() {
			return pattern;
		}

		/**
		 * Get the RegEx replacement string for the replacement
		 * 
		 * @return The RegEx replacement string
		 */
		protected String getReplacement() {
			return replacement;
		}

		/**
		 * Get the reason string for the replacement
		 * 
		 * @return The reason for the replacement
		 */
		protected String getReason() {
			return reason;
		}

		/**
		 * Get if the transformation should be applied until not found anymore.
		 * 
		 * @return False, if the pattern should only be applied once
		 */
		protected boolean getRepeatAllways() {
			return repeatAllways;
		}

	}

}
