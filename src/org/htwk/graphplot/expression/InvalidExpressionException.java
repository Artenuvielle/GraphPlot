package org.htwk.graphplot.expression;

/**
 * This exception describes problems with a given string which should represent
 * an expression but can somehow not be transformed.
 * 
 * @author Sophie Eckenstaler, René Martin
 * @version 1.0
 */
public class InvalidExpressionException extends Exception {

	private static final long serialVersionUID = -4082878927122639699L;

	/**
	 * Proxy for an empty exception constructor.
	 */
	public InvalidExpressionException() {
		super();
	}

	/**
	 * Proxy for exception constructor with a message.
	 */
	public InvalidExpressionException(String msg) {
		super(msg);
	}

	/**
	 * Proxy for exception constructor with a message and another exception or
	 * error.
	 */
	public InvalidExpressionException(String msg, Throwable throwing) {
		super(msg, throwing);
	}

}
