package exceptions;

public class ExceededMaxPlaysInYearException extends Exception {
    public ExceededMaxPlaysInYearException(String message) {
        super(message);
    }
}
