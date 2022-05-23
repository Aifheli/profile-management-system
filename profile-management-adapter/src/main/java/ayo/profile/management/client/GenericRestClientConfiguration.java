package ayo.profile.management.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created by Aifheli Maganya on 2022/05/21.
 */
@Configuration
public class GenericRestClientConfiguration {

    @Value("${ayo.http.timeout}")
    private int timeout;

    @Bean
    public RestTemplate createSession() {
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter());
        restTemplate.setInterceptors(Collections.singletonList(new ClientHttpLogInterceptor()));
        return restTemplate;
    }

    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(new ObjectMapper());
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setReadTimeout(timeout);
        clientHttpRequestFactory.setConnectTimeout(timeout);
        clientHttpRequestFactory.setConnectionRequestTimeout(timeout);

        return clientHttpRequestFactory;
    }
}
