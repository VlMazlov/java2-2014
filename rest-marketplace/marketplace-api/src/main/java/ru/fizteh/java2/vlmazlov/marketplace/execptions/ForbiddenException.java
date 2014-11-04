package ru.fizteh.java2.vlmazlov.marketplace.execptions;

/**
 * Created by vlmazlov on 03.11.14.
 */
public class ForbiddenException extends Exception {
    public ForbiddenException()
    {
        super();
    }

    public ForbiddenException(String message)
    {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ForbiddenException(Throwable cause)
    {
        super(cause);
    }

    protected ForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
