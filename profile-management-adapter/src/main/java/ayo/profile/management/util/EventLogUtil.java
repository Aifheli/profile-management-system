package ayo.profile.management.util;

public class EventLogUtil {

    public enum LOGGER_EVENT {
        APPLICATION("Profile Management Adapter"),
        APPLICATION2("Ayo MTN Profile adapter"),
        CONVERT_JSON_EVENT("Convert-JSON"),
        REGISTER_EVENT("customerRegistration"),
        CUST_DATA_EVENT("Customer-Data Received"),
        FETCH_DATA_EVENT("Fetch-Data"),
        FETCH_ALL_DATA_EVENT("fetchAllCustomerData"),
        PERSIST_EVENT("Persist customer data"),
        PROFILE_SERVICE_EVENT("eProfileService"),
        PREFORM_REG_EVENT("performSaveCustomerData"),
        PREFORM_SEND_EVENT("performSendData"),
        PERFORM_DB_UPDATE_EVENT("performDbUpdate"),
        HTTP_ERROR_EVENT("http Response Exception"),
        EXECUTE_GENERIC_EVENT("executeGeneric"),
        NO_DATA_EVENT("Data not found."),
        PARSE_DATE("ParseDate");

        private String event;
        LOGGER_EVENT(String event){
            this.event=event;
        }

        @Override
        public String toString(){
            return event;
        }
    }

    public EventLogUtil() {
    }

}
