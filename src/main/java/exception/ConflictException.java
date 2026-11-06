package exception;

public class ConflictException extends ApiException{

    public ConflictException(int status, String message) {
        super(409, message);
    }
}
