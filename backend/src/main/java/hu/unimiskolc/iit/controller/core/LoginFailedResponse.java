package hu.unimiskolc.iit.controller.core;

public class LoginFailedResponse {
    private String[] errors;

    public LoginFailedResponse(String[] errors) {
        this.errors = errors;
    }

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }
}
