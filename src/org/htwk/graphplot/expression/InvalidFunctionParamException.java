package org.htwk.graphplot.expression;

public class InvalidFunctionParamException extends Exception
{
    private static final long serialVersionUID = 861440868581151699L;
    
    public InvalidFunctionParamException() {
        super();
    }
    public InvalidFunctionParamException(String msg) {
        super(msg);
    }
    public InvalidFunctionParamException(String msg, Throwable throwing) {
        super(msg, throwing);
    }
}
