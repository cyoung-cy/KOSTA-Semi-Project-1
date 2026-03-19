package exception;

public class ScheduleConflictException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ScheduleConflictException(String message) {
        super(message);
    }
	
	public ScheduleConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
