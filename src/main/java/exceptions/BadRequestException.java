package exceptions;

public class BadRequestException extends ApiException{
    public BadRequestException(String message) {
        super(Error.BAD_REQUEST, message, 400);
    }

    @Override
    public void logException() {
        System.err.println("Status code: " + getStatus() + ": " + getMessage());
    }
}
