package ua.nure.kn17.nikolenko.usermanagement.agent;

public class SearchException extends Exception 
{
    public SearchException() {super();}

    public SearchException(String message) {super(message);}

    public SearchException(String message, Throwable cause) {super(message, cause);}

    public SearchException(Throwable cause) {super(cause);}

    protected SearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) 
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}