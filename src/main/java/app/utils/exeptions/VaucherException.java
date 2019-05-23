package app.utils.exeptions;

public class VaucherException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String error;

    public VaucherException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
