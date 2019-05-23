package app.utils.exeptions;

public class EmployeeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String error;

    public EmployeeException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

}
