package exceptions;

public class ClientDoNotExistException extends Exception {
    public ClientDoNotExistException(String message) {
        super(message);
    }
}
