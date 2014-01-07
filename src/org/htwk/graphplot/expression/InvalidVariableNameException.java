package org.htwk.graphplot.expression;

public class InvalidVariableNameException extends Exception
{
    private static final long serialVersionUID = -976929032110762631L;
    
    public InvalidVariableNameException() {
        super();
    }
    public InvalidVariableNameException(String msg) {
        super(msg);
    }
    public InvalidVariableNameException(String msg, Throwable throwing) {
        super(msg, throwing);
    }
}
