package exception;

public class DMLException extends RuntimeException {
    public DMLException() {}
    public DMLException(String message) {
        super(message);
    }
}
