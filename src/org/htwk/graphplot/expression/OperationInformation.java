package org.htwk.graphplot.expression;

import org.htwk.graphplot.expression.core.Operation;
import org.htwk.graphplot.expression.core.Operation.OperationImportance;

/**
 * This class is supposed to hold information about an interpretable operation.
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class OperationInformation {

	private Class<? extends Operation> operationClass;
	private String operationString;
	private OperationImportance operationImportance;

	/**
	 * Initializes a object which holds information about a class extending
	 * Operation.
	 * 
	 * @param operationClass
	 *            The class extending Operation
	 * @param operationString
	 *            The string of the operation
	 * @param operationImportance
	 *            The importance of the operation
	 */
	public OperationInformation(Class<? extends Operation> operationClass, String operationString, OperationImportance operationImportance) {
		this.operationClass = operationClass;
		this.operationString = operationString;
		this.operationImportance = operationImportance;
	}

	/**
	 * Gets the class of the operation.
	 * 
	 * @return the operation class
	 */
	public Class<? extends Operation> getOperationClass() {
		return operationClass;
	}

	/**
	 * Get the string of the operation.
	 * 
	 * @return the operation string
	 */
	public String getOperationString() {
		return operationString;
	}

	/**
	 * Get the importance of the operation.
	 * 
	 * @return the operation importance
	 */
	public OperationImportance getOperationImportance() {
		return operationImportance;
	}

}
