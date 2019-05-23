package app.utils.exeptions;

public class CarException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String error;

    public CarException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
