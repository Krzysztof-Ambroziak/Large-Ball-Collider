package pl.cba.reallygrid.lbc.phys.exceptions;

public class NoObjectException extends Exception {
    public NoObjectException() {
    }
    
    public NoObjectException(String message) {
        super(message);
    }
    
    public NoObjectException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public NoObjectException(Throwable cause) {
        super(cause);
    }
    
    public NoObjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
