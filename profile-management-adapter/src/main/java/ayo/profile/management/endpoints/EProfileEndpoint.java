package ayo.profile.management.endpoints;

import ayo.profile.management.api.EprofileApi;
import ayo.profile.management.util.JsonPojoConverter;
import ayo.profile.management.entities.CustomerRegistrationEntity;
import ayo.profile.management.mapper.CustomerRegistrationResponseDataMapper;
import ayo.profile.management.mapper.RegisterTransactionRequestMapper;
import ayo.profile.management.mock.RegisterTransactionRequest;
import ayo.profile.management.mock.RegisterTransactionResponse;
import ayo.profile.management.mock.Status;
import ayo.profile.management.models.CustomerRegistrationRequest;
import ayo.profile.management.models.CustomerRegistrationResponse;
import ayo.profile.management.service.CustomerRegistrationService;
import ayo.profile.management.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
/**
 * Created by Aifheli Maganya on 2022/05/18.
 */
@RestController
public class EProfileEndpoint implements EprofileApi {

    private CustomerRegistrationService customerRegistrationService;
    private RegisterTransactionRequestMapper registerTransactionRequestMapper;

    public EProfileEndpoint(CustomerRegistrationService customerRegistrationService, RegisterTransactionRequestMapper registerTransactionRequestMapper) {
        this.customerRegistrationService = customerRegistrationService;
        this.registerTransactionRequestMapper = registerTransactionRequestMapper;
    }

    @ExecutionTime
    @Override
    public ResponseEntity<CustomerRegistrationResponse> eProfileService(@Validated CustomerRegistrationRequest body, String xRequestID, String authorization, String xCorrelationID) {
        ResponseEntity<CustomerRegistrationResponse> responseEntity = null;
        RegisterTransactionResponse registerTransactionResponse = null;
        ApplicationLoggerUtil.setxCorrelationID(xCorrelationID);
        try {
            ApplicationLoggerUtil.info(EProfileEndpoint.class,  EventLogUtil.LOGGER_EVENT.APPLICATION.toString(),  JsonPojoConverter.convertObjectToJson(body),
                    EventLogUtil.LOGGER_EVENT.CUST_DATA_EVENT.toString());

            CustomerRegistrationEntity entity = customerRegistrationService.performSaveCustomerData(body);
            RegisterTransactionRequest registerTransactionRequest = registerTransactionRequestMapper.map(entity);
            registerTransactionResponse = customerRegistrationService.performSendData(registerTransactionRequest, xCorrelationID);
            Status status = registerTransactionResponse.getStatus();

            if(status.getCode() != null && status.getCode().equals(201)){
                responseEntity = Helpers.buildCustomerRegistrationResponse(ApplicationMessagesUtil.MSG.SUCCESS.getCode(),
                        ApplicationMessagesUtil.MSG.SUCCESS.getMessage(), "", HttpStatus.OK);
            }else {
                responseEntity = Helpers.buildCustomerRegistrationResponse(ApplicationMessagesUtil.MSG.FAILURE.getCode(),
                        ApplicationMessagesUtil.MSG.FAILURE.getMessage(), registerTransactionResponse.getError(),
                        HttpStatus.EXPECTATION_FAILED);
            }
            return responseEntity;
        }catch (Exception e) {
            responseEntity = Helpers.buildCustomerRegistrationResponse("UNKNOWN_ERROR",  e.getMessage(), "",HttpStatus.INTERNAL_SERVER_ERROR);
            ApplicationLoggerUtil.error(EProfileEndpoint.class, EventLogUtil.LOGGER_EVENT.APPLICATION.toString(),  JsonPojoConverter.convertObjectToJson(responseEntity.getBody()),
                    EventLogUtil.LOGGER_EVENT.PROFILE_SERVICE_EVENT.toString(), e);
            return responseEntity;
        }
    }
}
