package ayo.profile.management.exception;

public class JwtInitializationException extends Exception {
    public JwtInitializationException() {
    }

    public JwtInitializationException(String message) {
        super(message);
    }

    public JwtInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtInitializationException(Throwable cause) {
        super(cause);
    }
}