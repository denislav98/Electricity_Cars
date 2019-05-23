package app.utils.exeptions;

public class TripException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String error;

    public TripException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}