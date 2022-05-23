package ayo.profile.management.service;

import ayo.profile.management.client.CustomerRegistrationClient;
import ayo.profile.management.util.JsonPojoConverter;
import ayo.profile.management.entities.CustomerRegistrationEntity;
import ayo.profile.management.exception.ProfileManagementException;
import ayo.profile.management.mapper.CustomerRegistrationRequestMapper;
import ayo.profile.management.mock.RegisterTransactionRequest;
import ayo.profile.management.mock.RegisterTransactionResponse;
import ayo.profile.management.models.CustomerRegistrationRequest;
import ayo.profile.management.repository.CustomerRegistrationRepository;
import ayo.profile.management.util.ApplicationLoggerUtil;
import ayo.profile.management.util.EventLogUtil;
import ayo.profile.management.util.Helpers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by Aifheli Maganya on 2022/05/19.
 */
@Slf4j
@Service
@Configuration
@EnableConfigurationProperties({ServerProperties.class})
public class CustomerRegistrationService implements RegistrationProcessing {

    private CustomerRegistrationRepository customerRegistrationRepository;
    private CustomerRegistrationRequestMapper customerRegistrationRequestMapper;
    private CustomerRegistrationClient customerRegistrationClient;
    private final ServerProperties serverProperties;

    public CustomerRegistrationService(CustomerRegistrationRepository customerRegistrationRepository, CustomerRegistrationRequestMapper customerRegistrationRequestMapper, CustomerRegistrationClient customerRegistrationClient, ServerProperties serverProperties) {
        this.customerRegistrationRepository = customerRegistrationRepository;
        this.customerRegistrationRequestMapper = customerRegistrationRequestMapper;
        this.customerRegistrationClient = customerRegistrationClient;
        this.serverProperties = serverProperties;
    }

    @Override
    public CustomerRegistrationEntity performSaveCustomerData(CustomerRegistrationRequest customerRegistrationRequest) throws Exception {
        CustomerRegistrationEntity entity = null;
        try {
            CustomerRegistrationEntity customerRegistrationEntity = customerRegistrationRequestMapper.map(customerRegistrationRequest, OffsetDateTime.now());
             entity = customerRegistrationRepository.save(customerRegistrationEntity);
            ApplicationLoggerUtil.info(CustomerRegistrationService.class,  EventLogUtil.LOGGER_EVENT.APPLICATION.toString(),
                    JsonPojoConverter.convertObjectToJson(entity), EventLogUtil.LOGGER_EVENT.PERSIST_EVENT.toString());
        }catch (Exception e){
            ApplicationLoggerUtil.error(CustomerRegistrationService.class, EventLogUtil.LOGGER_EVENT.APPLICATION.toString(),
                    JsonPojoConverter.convertObjectToJson(e.getMessage()), EventLogUtil.LOGGER_EVENT.PREFORM_REG_EVENT.toString(), e);
        }
        return entity;
    }

    @Override
    public RegisterTransactionResponse performSendData(RegisterTransactionRequest body,  String xCorrelationID) throws Exception {
        ResponseEntity<RegisterTransactionResponse> responseEntity = null;
        RegisterTransactionResponse registerTransactionResponse = null;
        try {
            responseEntity = customerRegistrationClient.sendCustomerRegistrationData(serverProperties.getServer()+serverProperties.getMtnServerUrl(), body, xCorrelationID);
            registerTransactionResponse =  responseEntity.getBody();
            return registerTransactionResponse;
        }catch (RestClientResponseException e){
            responseEntity = Helpers.buildRegisterTransactionResponse(e.getRawStatusCode(),"Http error", e.getResponseBodyAsString(), HttpStatus.BAD_GATEWAY);
            ApplicationLoggerUtil.error(CustomerRegistrationService.class, EventLogUtil.LOGGER_EVENT.APPLICATION.toString(),
                    JsonPojoConverter.convertObjectToJson(responseEntity.getBody()),
                    EventLogUtil.LOGGER_EVENT.PREFORM_SEND_EVENT.toString(), e);
            return responseEntity.getBody();
        }catch (Exception e){
            throw new ProfileManagementException("Error: " + EventLogUtil.LOGGER_EVENT.PREFORM_SEND_EVENT.toString(), e);
        }
    }

    @Override
    public Optional<CustomerRegistrationEntity> performFetchCustomerData(Long id) throws Exception {
        return customerRegistrationRepository.findById(id);
    }

    @Override
    public List<CustomerRegistrationEntity> performFetchAllCustomerData() throws Exception {
        return customerRegistrationRepository.findAll();
    }
}
