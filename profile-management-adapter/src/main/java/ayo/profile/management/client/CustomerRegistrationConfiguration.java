package ayo.profile.management.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Aifheli Maganya on 2022/05/21.
 */
@Import(GenericRestClientConfiguration.class)
public class CustomerRegistrationConfiguration {

    @Bean
    public CustomerRegistrationClient customerRegistrationClient(RestTemplate genericRestClientRestTemplate) {
        return new CustomerRegistrationClient(genericRestClientRestTemplate);
    }
}
