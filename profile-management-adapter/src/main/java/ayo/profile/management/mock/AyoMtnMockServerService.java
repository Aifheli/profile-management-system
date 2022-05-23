package ayo.profile.management.mock;

import ayo.profile.management.exception.ProfileManagementException;
import ayo.profile.management.repository.CustomerRegistrationRepository;
import ayo.profile.management.util.EventLogUtil;
import org.springframework.stereotype.Service;

import javax.persistence.RollbackException;
/**
 * Created by Aifheli Maganya on 2022/05/21.
 */
@Service
public class AyoMtnMockServerService {

    private CustomerRegistrationRepository customerRegistrationRepository;

    public AyoMtnMockServerService(CustomerRegistrationRepository customerRegistrationRepository) {
        this.customerRegistrationRepository = customerRegistrationRepository;
    }

    public void performDbUpdate(RegisterTransactionRequest registerTransactionRequest) throws Exception {
        try {
            customerRegistrationRepository.updateEntity(registerTransactionRequest.getId(), "Registered");
        }catch (RollbackException e){
            throw new ProfileManagementException("Error: " + EventLogUtil.LOGGER_EVENT.PERFORM_DB_UPDATE_EVENT.toString(), e);
        }
    }
}
