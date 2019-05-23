package app.utils.exeptions;

public class ClientException extends RuntimeException {
    public ClientException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public ClientException(String errorMessage) {
        super(errorMessage);
    }
}
