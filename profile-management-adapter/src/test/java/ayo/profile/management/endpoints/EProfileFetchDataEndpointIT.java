package ayo.profile.management.endpoints;

import ayo.profile.management.application.ProfileManagementAdapterApplication;
import ayo.profile.management.client.CustomerRegistrationClient;
import ayo.profile.management.client.GenericRestClientConfiguration;
import ayo.profile.management.config.DatabaseConnection;
import ayo.profile.management.jwt.JWTService;
import ayo.profile.management.mapper.CustomerRegistrationRequestMapper;
import ayo.profile.management.mapper.RegisterTransactionRequestMapper;
import ayo.profile.management.models.CustomerRegistrationRequest;
import ayo.profile.management.models.CustomerRegistrationResponse;
import ayo.profile.management.repository.CustomerRegistrationRepository;
import ayo.profile.management.security.SecurityConfig;
import ayo.profile.management.service.CustomerRegistrationService;
import ayo.profile.management.service.ServerProperties;
import ayo.profile.management.util.JsonPojoConverter;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(Parameterized.class)
@ContextConfiguration(classes = {ProfileManagementAdapterApplication.class, SecurityConfig.class,
        GenericRestClientConfiguration.class, ServerProperties.class, DatabaseConnection.class, JWTService.class})
@SpringBootTest(classes = ProfileManagementAdapterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EProfileFetchDataEndpointIT {

    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();
    public static final java.lang.String FETCH_CUST_V1_PATH = "/ayo/mtn/fetch-cust-data/v1";
    public static final java.lang.String FETCH_CUST_ALL_V1_PATH = "/ayo/mtn/fetch-all-cust-data/v1";

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @MockBean
    private CustomerRegistrationService customerRegistrationService;
    @MockBean
    private RegisterTransactionRequestMapper registerTransactionRequestMapper;
    @MockBean
    private CustomerRegistrationRepository customerRegistrationRepository;
    @MockBean
    private CustomerRegistrationRequestMapper customerRegistrationRequestMapper;
    @MockBean
    private CustomerRegistrationClient customerRegistrationClient;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    private String url;
    private String jsonFilename;
    private String jsonFilenameResult;
    private int status;

    @LocalServerPort
    private int port;

    @Parameterized.Parameters(name = "{1}")
    public static Collection byteParams() {
        return Arrays.asList(new Object[][]{
                {FETCH_CUST_V1_PATH, "registerTransactionRequest.json", "customerRegistrationResponseSuccessEmpty.json", 200},
                {FETCH_CUST_V1_PATH, "registerTransactionRequest2.json", "customerRegistrationResponseSuccessEmpty.json", 200},
        });
    }

    public EProfileFetchDataEndpointIT(String url, String jsonFilename, String jsonFilenameResult, int status) {
        this.url = url;
        this.jsonFilename = jsonFilename;
        this.jsonFilenameResult = jsonFilenameResult;
        this.status = status;
    }

    @Test
    public void testFetchCustomerData() throws Exception {

        String expectedJsonString = loadResource(this.jsonFilenameResult);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HashMap<String, String> additionalHeaders = JsonPojoConverter.convertJsonToObject(loadResource("profileRequestDefaultHeaders.json"), HashMap.class);
        additionalHeaders.put("Authorization", "Bearer " + "bypass");

        if (additionalHeaders != null) {
            for (Map.Entry<String, String> headerEntry : additionalHeaders.entrySet()) {
                if (additionalHeaders.containsKey(headerEntry.getKey())) {
                    additionalHeaders.get(headerEntry.getKey());
                }
                httpHeaders.add(headerEntry.getKey(), headerEntry.getValue());
            }
        }

        CustomerRegistrationRequest customerRegistrationRequest = loadCustomerRegistrationRequest(this.jsonFilename);

        HttpEntity<CustomerRegistrationRequest> entity = new HttpEntity<>(customerRegistrationRequest, httpHeaders);

        ResponseEntity<CustomerRegistrationResponse> response = restTemplate.exchange(createURLWithPort(url), HttpMethod.POST, entity, CustomerRegistrationResponse.class);

        assertEquals(this.status, response.getStatusCodeValue());
        assertEquals(expectedJsonString,  JsonPojoConverter.convertObjectToJson(response.getBody()));
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private String loadResource(String filename) throws IOException {
        return cleanupJson(IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("json/" + filename), StandardCharsets.UTF_8));
    }

    private String cleanupJson(String json) {
        return json.replaceAll("\\s*[\\r\\n]+\\s*", "").replaceAll(":\\s", ":").trim();
    }

    private CustomerRegistrationRequest loadCustomerRegistrationRequest(String filename) throws IOException {
        return JsonPojoConverter.convertJsonToObject(loadResource(filename), CustomerRegistrationRequest.class);
    }
}
