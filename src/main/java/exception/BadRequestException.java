package exception;

public class BadRequestException extends ApiException {

    public BadRequestException(int status, String message) {
        super(400, message);
    }
}
