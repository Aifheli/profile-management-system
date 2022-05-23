package ayo.profile.management.client;

import ayo.profile.management.mock.RegisterTransactionRequest;
import ayo.profile.management.mock.RegisterTransactionResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
/**
 * Created by Aifheli Maganya on 2022/05/21.
 */
@Component
public class CustomerRegistrationClient extends GenericRestClient {

    public CustomerRegistrationClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public ResponseEntity<RegisterTransactionResponse> sendCustomerRegistrationData(String uri, RegisterTransactionRequest body, String xCorrelationID) throws Exception {
        return super.executeGeneric(uri , body, xCorrelationID,new ParameterizedTypeReference<RegisterTransactionResponse>() {});
    }
}
