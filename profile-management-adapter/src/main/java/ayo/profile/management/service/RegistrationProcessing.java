package ayo.profile.management.service;

import ayo.profile.management.entities.CustomerRegistrationEntity;
import ayo.profile.management.mock.RegisterTransactionRequest;
import ayo.profile.management.mock.RegisterTransactionResponse;
import ayo.profile.management.models.CustomerRegistrationRequest;

import java.util.List;
import java.util.Optional;

/**
 * Created by Aifheli Maganya on 2022/05/19.
 */
public interface RegistrationProcessing {

    CustomerRegistrationEntity performSaveCustomerData(CustomerRegistrationRequest customerRegistrationRequest) throws Exception;

    RegisterTransactionResponse performSendData(RegisterTransactionRequest body, String xCorrelationID)throws Exception;

    Optional<CustomerRegistrationEntity> performFetchCustomerData(Long id)throws Exception;

    List<CustomerRegistrationEntity> performFetchAllCustomerData()throws Exception;

}
