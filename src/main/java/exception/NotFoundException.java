package exception;

public class NotFoundException extends ApiException{

    public NotFoundException(int status, String message) {
        super(404, message);
    }
}
