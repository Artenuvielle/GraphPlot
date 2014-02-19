package org.htwk.graphplot.expression;

/**
 * This exception describes problems when variable names are used which are not
 * declared.
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class InvalidVariableNameException extends Exception {

	private static final long serialVersionUID = -976929032110762631L;

	/**
	 * Proxy for an empty exception constructor.
	 */
	public InvalidVariableNameException() {
		super();
	}

	/**
	 * Proxy for exception constructor with a message.
	 */
	public InvalidVariableNameException(String msg) {
		super(msg);
	}

	/**
	 * Proxy for exception constructor with a message and another exception or
	 * error.
	 */
	public InvalidVariableNameException(String msg, Throwable throwing) {
		super(msg, throwing);
	}

}
