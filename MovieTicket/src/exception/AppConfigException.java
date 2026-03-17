package exception;

public class AppConfigException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AppConfigException(String message) {
        super(message);
    }
	
	public AppConfigException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
