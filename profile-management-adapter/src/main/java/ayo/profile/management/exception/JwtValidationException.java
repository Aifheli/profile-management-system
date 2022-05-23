package ayo.profile.management.exception;

public class JwtValidationException extends Exception {
    public JwtValidationException() {
    }

    public JwtValidationException(String message) {
        super(message);
    }

    public JwtValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtValidationException(Throwable cause) {
        super(cause);
    }
}
