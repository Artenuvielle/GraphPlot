package org.htwk.graphplot.expression;

/**
 * This exception describes problems when the given parameters for a function
 * does not match its definition.
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class InvalidFunctionParamException extends Exception {

	private static final long serialVersionUID = 861440868581151699L;

	/**
	 * Proxy for an empty exception constructor.
	 */
	public InvalidFunctionParamException() {
		super();
	}

	/**
	 * Proxy for exception constructor with a message.
	 */
	public InvalidFunctionParamException(String msg) {
		super(msg);
	}

	/**
	 * Proxy for exception constructor with a message and another exception or
	 * error.
	 */
	public InvalidFunctionParamException(String msg, Throwable throwing) {
		super(msg, throwing);
	}

}
