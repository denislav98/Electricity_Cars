package app.utils.exeptions;

public class CreditCardException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String error;

    public CreditCardException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

}
