package org.htwk.graphplot.expression;

import org.htwk.graphplot.expression.core.Function;

/**
 * This class is supposed to hold information about an interpretable function.
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class FunctionInformation {

	private Class<Function> functionClass;
	private String functionName;
	private int[] acceptedParameterNumber;

	/**
	 * Initializes a object which holds information about a class extending
	 * Function.
	 * 
	 * @param functionClass
	 *            The class extending Function
	 * @param functionName
	 *            The name of the function
	 * @param acceptedParameterNumber
	 *            Accepted number of parameters
	 */
	public FunctionInformation(Class<Function> functionClass, String functionName, int[] acceptedParameterNumber) {
		this.functionClass = functionClass;
		this.functionName = functionName;
		this.acceptedParameterNumber = acceptedParameterNumber;
	}

	/**
	 * Get the class of the function.
	 * 
	 * @return the function class
	 */
	public Class<Function> getFunctionClass() {
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
	 * Get the numbers of accepted parameters.
	 * 
	 * @return the accepted parameter numbers
	 */
	public int[] getAcceptedParameterNumber() {
		return acceptedParameterNumber;
	}

}
