package exceptions;

public abstract class ApiException extends Exception {
    private final Error error;
    private final Integer status;

    public ApiException(Error error, String message, Integer status){
        super(message);
        this.error = error;
        this.status = status;
    }

    public void logException() {
        System.err.println("Error " + status + ": " + getMessage());
    }

    public Error getError() {
        return error;
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "error=" + error +
                ", message=" + getMessage() +
                '}';
    }
}
