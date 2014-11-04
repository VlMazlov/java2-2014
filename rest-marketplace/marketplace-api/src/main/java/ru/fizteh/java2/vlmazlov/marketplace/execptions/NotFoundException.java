package ru.fizteh.java2.vlmazlov.marketplace.execptions;

/**
 * Created by vlmazlov on 03.11.14.
 */
public class NotFoundException extends Exception {
    public NotFoundException()
    {
        super();
    }

    public NotFoundException(String message)
    {
        super(message);
    }

    public NotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NotFoundException(Throwable cause)
    {
        super(cause);
    }

    protected NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
