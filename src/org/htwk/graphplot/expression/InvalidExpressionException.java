package org.htwk.graphplot.expression;

public class InvalidExpressionException extends Exception
{
    private static final long serialVersionUID = -4082878927122639699L;
    
    public InvalidExpressionException() {
        super();
    }
    public InvalidExpressionException(String msg) {
        super(msg);
    }
    public InvalidExpressionException(String msg, Throwable throwing) {
        super(msg, throwing);
    }
}
