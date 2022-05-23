package ayo.profile.management.exception;

/**
 * Created by Aifheli Maganya on 2022/05/21.
 */
public class ProfileManagementException extends RuntimeException  {

    private String code, description;

    public ProfileManagementException() {
        super();
    }

    public ProfileManagementException(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public ProfileManagementException(String message) {
        super(message);
    }

    public ProfileManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProfileManagementException(Throwable cause) {
        super(cause);
    }
}
