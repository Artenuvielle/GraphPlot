package org.htwk.graphplot.expression;

import org.htwk.graphplot.expression.core.Function;

/**
 * This class is supposed to hold information about an interpretable function.
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class FunctionInformation {

	private Class<? extends Function> functionClass;
	private String functionName;
	private ParameterCountCheck parameterCountChecker;

	/**
	 * Initializes a object which holds information about a class extending
	 * Function.
	 * 
	 * @param functionClass
	 *            The class extending Function
	 * @param functionName
	 *            The name of the function
	 * @param delegate
	 *            Checking object for parameter numbers
	 */
	public FunctionInformation(Class<? extends Function> functionClass, String functionName, ParameterCountCheck delegate) {
		this.functionClass = functionClass;
		this.functionName = functionName;
		this.parameterCountChecker = delegate;
	}

	/**
	 * Get the class of the function.
	 * 
	 * @return the function class
	 */
	public Class<? extends Function> getFunctionClass() {
		return functionClass;
	}

	/**
	 * Get the function name.
	 * 
	 * @return the function name
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * Tests if a given number if parameters is acceptable for this function.
	 * 
	 * @param parameterCount The number of parameters to test for
	 * @return True, if the given number of parameters is acceptable for this
	 *         function
	 */
	public boolean isParameterCountAcceptable(int parameterCount) {
		return parameterCountChecker.check(parameterCount);
	}

	public interface ParameterCountCheck {

		public boolean check(int paramterCount);

	}

}
