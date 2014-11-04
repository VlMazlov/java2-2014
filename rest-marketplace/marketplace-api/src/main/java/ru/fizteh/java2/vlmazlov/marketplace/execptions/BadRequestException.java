package ru.fizteh.java2.vlmazlov.marketplace.execptions;

/**
 * Created by vlmazlov on 03.11.14.
 */
public class BadRequestException extends Exception {
    public BadRequestException()
    {
        super();
    }

    public BadRequestException(String message)
    {
        super(message);
    }

    public BadRequestException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public BadRequestException(Throwable cause)
    {
        super(cause);
    }

    protected BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
