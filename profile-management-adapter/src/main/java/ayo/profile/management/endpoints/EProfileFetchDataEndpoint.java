package ayo.profile.management.endpoints;

import ayo.profile.management.util.*;
import ayo.profile.management.entities.CustomerRegistrationEntity;
import ayo.profile.management.mapper.CustomerRegistrationResponseDataMapper;
import ayo.profile.management.mock.RegisterTransactionRequest;
import ayo.profile.management.models.CustomerRegistrationResponse;
import ayo.profile.management.models.CustomerRegistrationResponseData;
import ayo.profile.management.service.CustomerRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Aifheli Maganya on 2022/05/18.
 */
@RestController
public class EProfileFetchDataEndpoint {

    private CustomerRegistrationService customerRegistrationService;
    private CustomerRegistrationResponseDataMapper customerRegistrationResponseDataMapper;

    public EProfileFetchDataEndpoint(CustomerRegistrationService customerRegistrationService, CustomerRegistrationResponseDataMapper customerRegistrationResponseDataMapper) {
        this.customerRegistrationService = customerRegistrationService;
        this.customerRegistrationResponseDataMapper = customerRegistrationResponseDataMapper;
    }

    @ExecutionTime
    @RequestMapping(value = "/ayo/mtn/fetch-cust-data/v1", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<CustomerRegistrationResponse> fetchCustomerData(@RequestBody RegisterTransactionRequest body, @RequestHeader(value="X-Request-ID", required=true) String xRequestID, @RequestHeader(value="X-Correlation-ID", required=true) String xCorrelationID){
        CustomerRegistrationResponseData customerRegistrationResponseData = null;
        try {
            ApplicationLoggerUtil.setxCorrelationID(xCorrelationID);
            ApplicationLoggerUtil.info(EProfileFetchDataEndpoint.class,  EventLogUtil.LOGGER_EVENT.APPLICATION.toString(),
                    JsonPojoConverter.convertObjectToJson(body), EventLogUtil.LOGGER_EVENT.FETCH_DATA_EVENT.toString());
            Optional<CustomerRegistrationEntity> customerRegistrationEntity = customerRegistrationService.performFetchCustomerData(body.getId());

            if(customerRegistrationEntity.isPresent()){
                customerRegistrationResponseData = customerRegistrationResponseDataMapper.map(customerRegistrationEntity.get());
                return Helpers.buildCustomerRegistrationResponse(customerRegistrationResponseData,  ApplicationMessagesUtil.MSG.RETRIEVE_RECORD.getCode(),
                        ApplicationMessagesUtil.MSG.RETRIEVE_RECORD.getMessage(), "", HttpStatus.OK);
            }else {
                return Helpers.buildCustomerRegistrationResponse(customerRegistrationResponseData,  ApplicationMessagesUtil.MSG.EMPTY_RETRIEVE_RECORDS.getCode(),
                        ApplicationMessagesUtil.MSG.EMPTY_RETRIEVE_RECORDS.getMessage(), "", HttpStatus.OK);
            }
        }catch (Exception e){
            ApplicationLoggerUtil.error(EProfileFetchDataEndpoint.class, EventLogUtil.LOGGER_EVENT.APPLICATION.toString(),
                    JsonPojoConverter.convertObjectToJson(body), EventLogUtil.LOGGER_EVENT.FETCH_DATA_EVENT.toString(), e);
            return Helpers.buildCustomerRegistrationResponse(customerRegistrationResponseData,  ApplicationMessagesUtil.MSG.FAILURE.getCode(),
                    ApplicationMessagesUtil.MSG.FAILURE.getMessage(), e.getMessage(), HttpStatus.OK);
        }
    }

    @ExecutionTime
    @RequestMapping(value = "/ayo/mtn/fetch-all-cust-data/v1", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.GET)
    public String fetchAllCustomerData(@RequestHeader(value="X-Request-ID", required=true) String xRequestID, @RequestHeader(value="X-Correlation-ID", required=true) String xCorrelationID){
        List<CustomerRegistrationEntity> customerRegistrationEntitys = null;
        try {
            ApplicationLoggerUtil.setxCorrelationID(xCorrelationID);
            customerRegistrationEntitys = customerRegistrationService.performFetchAllCustomerData();
            ApplicationLoggerUtil.info(EProfileFetchDataEndpoint.class,  EventLogUtil.LOGGER_EVENT.APPLICATION.toString(),
                    JsonPojoConverter.convertObjectToJson(customerRegistrationEntitys),
                    EventLogUtil.LOGGER_EVENT.FETCH_ALL_DATA_EVENT.toString());
            if(!customerRegistrationEntitys.isEmpty()){
                return JsonPojoConverter.convertObjectToJson(customerRegistrationEntitys);
            }else {
                return JsonPojoConverter.convertObjectToJson(ApplicationMessagesUtil.MSG.EMPTY_RETRIEVE_RECORDS.getCode() +
                        " "+ ApplicationMessagesUtil.MSG.EMPTY_RETRIEVE_RECORDS.getMessage() );
            }
        }catch (Exception e){
            ApplicationLoggerUtil.error(EProfileFetchDataEndpoint.class, EventLogUtil.LOGGER_EVENT.APPLICATION.toString(),
                    JsonPojoConverter.convertObjectToJson(customerRegistrationEntitys), EventLogUtil.LOGGER_EVENT.FETCH_ALL_DATA_EVENT.toString(), e);
            return JsonPojoConverter.convertObjectToJson(ApplicationMessagesUtil.MSG.FAILURE.getCode() +
                    " "+ ApplicationMessagesUtil.MSG.FAILURE.getMessage());
        }
    }
}
