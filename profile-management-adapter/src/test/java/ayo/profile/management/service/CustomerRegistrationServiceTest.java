package ayo.profile.management.service;

import ayo.profile.management.client.CustomerRegistrationClient;
import ayo.profile.management.entities.CustomerRegistrationEntity;
import ayo.profile.management.mapper.CustomerRegistrationRequestMapper;
import ayo.profile.management.mapper.CustomerRegistrationRequestMapperImpl;
import ayo.profile.management.models.CustomerRegistrationRequest;
import ayo.profile.management.models.Registration;
import ayo.profile.management.repository.CustomerRegistrationRepository;
import ayo.profile.management.util.JsonPojoConverter;
import io.micrometer.core.instrument.util.IOUtils;
import org.junit.*;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class CustomerRegistrationServiceTest {

    private CustomerRegistrationService classUnderTest;
    private CustomerRegistrationRequestMapper customerRegistrationRequestMapper;
    @Mock
    private CustomerRegistrationClient customerRegistrationClient;
    @Autowired
    private CustomerRegistrationRepository customerRegistrationRepository;
    @Autowired
    private ServerProperties serverProperties;

    @Before
    public void setUp() throws IOException {
        customerRegistrationRequestMapper = new CustomerRegistrationRequestMapperImpl();
        classUnderTest = new CustomerRegistrationService(customerRegistrationRepository, customerRegistrationRequestMapper, customerRegistrationClient, serverProperties);
    }

    @Test
    public void  testPerformSaveCustomerData() throws Exception {
        CustomerRegistrationRequest customerRegistrationRequest = loadCustomerRegistrationRequest("json/customerRegistrationRequestSuccess1.json");

        CustomerRegistrationEntity customerRegistrationEntity = classUnderTest.performSaveCustomerData(customerRegistrationRequest);

        Registration registration = customerRegistrationRequest.getData().getRegistration();
        Assert.assertNotNull(customerRegistrationEntity);
        Assert.assertNotNull(registration);
        Assert.assertNotNull(customerRegistrationEntity.getCaptDate());
        Assert.assertEquals(java.util.Optional.of(Long.parseLong("1")).get(), customerRegistrationEntity.getId());
        Assert.assertEquals(registration.getSurname(), customerRegistrationEntity.getSurname());
        Assert.assertEquals(registration.getName(), customerRegistrationEntity.getName());
        Assert.assertEquals(registration.getProCode(), customerRegistrationEntity.getProCode());
        Assert.assertEquals(registration.getGender(),customerRegistrationEntity.getGender());
        Assert.assertEquals(registration.getPhoneNo(), customerRegistrationEntity.getPhoneNo());
        Assert.assertEquals(registration.getDateOfBirth(), customerRegistrationEntity.getDateOfBirth());
        Assert.assertEquals(registration.getPlaceOfBirth(), customerRegistrationEntity.getPlaceOfBirth());
        Assert.assertEquals(registration.getSaIdNo(), customerRegistrationEntity.getSaIdNo());
        Assert.assertEquals(registration.getForeignIdNo(),customerRegistrationEntity.getForeignIdNo());
        Assert.assertEquals(registration.getStreetAddressLine(), customerRegistrationEntity.getStreetAddressLine());
        Assert.assertEquals(registration.getStreetSuburb(), customerRegistrationEntity.getStreetSuburb());
        Assert.assertEquals(registration.getStreetCity(), customerRegistrationEntity.getStreetCity());
        Assert.assertEquals(registration.getLocalDate(), customerRegistrationEntity.getLocalDate());
        Assert.assertEquals(registration.getLocalTime(),customerRegistrationEntity.getLocalTime());
        Assert.assertEquals(registration.getCountryCode(), customerRegistrationEntity.getCountryCode());
        Assert.assertEquals(registration.getRegNo(), customerRegistrationEntity.getRegNo());
        Assert.assertEquals(registration.getAccNo(), customerRegistrationEntity.getAccNo());
        Assert.assertEquals(registration.getAccType(), customerRegistrationEntity.getAccType());
        Assert.assertEquals(registration.getBranchCode(),customerRegistrationEntity.getBranchCode());
        Assert.assertEquals(registration.getBankName(), customerRegistrationEntity.getBankName());
        Assert.assertEquals(registration.getBankCode(), customerRegistrationEntity.getBankCode());
        Assert.assertEquals(registration.getRegStatus(), customerRegistrationEntity.getRegStatus());
        Assert.assertEquals(2022, customerRegistrationEntity.getCreatedDate().getYear());

    }

//    @Test
//    public void  testPerformSendData() throws Exception {
//        RegisterTransactionRequest registerTransactionRequest = loadRegisterTransactionRequest("request/registerTransactionRequest.json");
//        RegisterTransactionResponse registerTransactionResponse = classUnderTest.performSendData(registerTransactionRequest, "AYO-MTN-001");
//
//    }

    private CustomerRegistrationRequest loadCustomerRegistrationRequest(String filename) throws IOException {
        String json = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream(filename), StandardCharsets.UTF_8);
        return JsonPojoConverter.convertJsonToObject(json, CustomerRegistrationRequest.class);
    }

//    private RegisterTransactionRequest loadRegisterTransactionRequest(String filename) throws IOException {
//        String json = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream(filename), StandardCharsets.UTF_8);
//        return JsonPojoConverter.convertJsonToObject(json, RegisterTransactionRequest.class);
//    }

    @Configuration
    @EnableJpaRepositories(basePackages = "ayo.profile.management.repository")
    @EnableJpaAuditing
    @EntityScan(basePackages = "ayo.profile.management.entities")
    static class CustomerRegistrationRepositoryConfiguration {
    }

    @Configuration
    @EnableConfigurationProperties(ServerProperties.class)
    public static class TestConfiguration {}

}
