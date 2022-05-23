package ayo.profile.management.client;

import ayo.profile.management.util.JsonPojoConverter;
import ayo.profile.management.util.ApplicationLoggerUtil;
import ayo.profile.management.util.EventLogUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
/**
 * Created by Aifheli Maganya on 2022/05/19.
 */
public abstract class GenericRestClient {

    private RestTemplate restTemplate;

    public GenericRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected  <A, B> ResponseEntity<B> executeGeneric(String uri, A request, String xCorrelationID, ParameterizedTypeReference<B> parameterizedTypeReference) throws Exception {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            httpHeaders.add("X-Correlation-ID", xCorrelationID);
            HttpEntity<A> entity = new HttpEntity(request, httpHeaders);
            ApplicationLoggerUtil.info(GenericRestClient.class, EventLogUtil.LOGGER_EVENT.APPLICATION.toString(),  uri +" : "+
                            JsonPojoConverter.convertObjectToJson(entity), EventLogUtil.LOGGER_EVENT.EXECUTE_GENERIC_EVENT.toString());
            ResponseEntity<B> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, parameterizedTypeReference);

            return responseEntity;
        } catch (RestClientResponseException e) {
            ApplicationLoggerUtil.error(GenericRestClient.class, EventLogUtil.LOGGER_EVENT.APPLICATION.toString(),
                    EventLogUtil.LOGGER_EVENT.EXECUTE_GENERIC_EVENT.toString(),uri +" : "+ e.getResponseBodyAsString(), e);
            throw e;
        } catch (Exception e) {
            ApplicationLoggerUtil.error(GenericRestClient.class, EventLogUtil.LOGGER_EVENT.APPLICATION.toString(),
                    EventLogUtil.LOGGER_EVENT.EXECUTE_GENERIC_EVENT.toString(), uri, e);
            throw e;
        }
    }
}
