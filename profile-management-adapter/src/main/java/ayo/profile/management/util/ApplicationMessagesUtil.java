package ayo.profile.management.util;

public class ApplicationMessagesUtil {

    public enum MSG {
        SUCCESS("201", "CUSTOMER VERIFIED! & REGISTERED SUCCESSFULLY"),

        FAILURE("202", "CLIENT RETURNED AN ERROR SERVICE"),

        RETRIEVE_RECORD("203", "SERVICE RETURNED SPECIFIC-RECORD FROM (AYO) - DB"),

        EMPTY_RETRIEVE_RECORDS("204", "SERVICE RETURNED AN EMPTY-RECORDS FROM (AYO) - DB"),

        EMPTY_REQUEST("205", "SERVICE PROVIDER RETURNED AN ERROR (REQUEST CAN NOT BE EMPTY)");

        private String code;
        private String message;
        MSG(String code, String message){
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
