package app.utils.exeptions;

public class CarServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String error;

    public CarServiceException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
