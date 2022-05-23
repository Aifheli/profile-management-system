package ayo.profile.management.util;

import ayo.profile.management.mock.RegisterTransactionResponse;
import ayo.profile.management.mock.Status;
import ayo.profile.management.models.CustomerRegistrationResponse;
import ayo.profile.management.models.CustomerRegistrationResponseData;
import ayo.profile.management.models.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Created by Aifheli Maganya on 2022/05/18.
 */
@Component("Helpers")
public class Helpers {

    public static ResponseEntity<RegisterTransactionResponse> buildRegisterTransactionResponse(Integer code, String description, Object error, HttpStatus httpStatus) {
        RegisterTransactionResponse registerTransactionResponse = new RegisterTransactionResponse();
        Status status = new Status();
        status.setCode(code);
        status.setDescription(description);
        registerTransactionResponse.setStatus(status);
        registerTransactionResponse.setError(error);
        return new ResponseEntity(registerTransactionResponse, httpStatus);
    }

    public static ResponseEntity<CustomerRegistrationResponse> buildCustomerRegistrationResponse(CustomerRegistrationResponseData registrationResponseData, String code, String
            status, Object error, HttpStatus httpStatus) {
        CustomerRegistrationResponse customerRegistrationResponse = new CustomerRegistrationResponse();
        customerRegistrationResponse.setData(registrationResponseData);
        Result result = new Result();
        result.setCode(code);
        result.setStatus(status);
        result.setError(error);
        customerRegistrationResponse.setResult(result);
        return new ResponseEntity(customerRegistrationResponse, httpStatus);
    }

    public static ResponseEntity<CustomerRegistrationResponse> buildCustomerRegistrationResponse(String code, String status, Object error,  HttpStatus httpStatus) {
        CustomerRegistrationResponse customerRegistrationResponse = new CustomerRegistrationResponse();
        customerRegistrationResponse.setData(new CustomerRegistrationResponseData());
        return buildCustomerRegistrationResponse(customerRegistrationResponse.getData(), code, status, error, httpStatus);
    }


}
