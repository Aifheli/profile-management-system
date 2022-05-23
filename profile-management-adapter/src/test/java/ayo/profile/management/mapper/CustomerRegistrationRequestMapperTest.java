package ayo.profile.management.mapper;

import ayo.profile.management.entities.CustomerRegistrationEntity;
import ayo.profile.management.models.CustomerRegistrationRequest;
import ayo.profile.management.models.Registration;
import ayo.profile.management.util.JsonPojoConverter;
import io.micrometer.core.instrument.util.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Aifheli Maganya on 2022/05/23.
 */
@RunWith(Parameterized.class)
@SpringBootTest
public class CustomerRegistrationRequestMapperTest {

    private CustomerRegistrationRequestMapper classUnderTest;

    private String  actualResults;

    @Parameterized.Parameters(name = "{0}")
    public static Collection byteParams() {
        return Arrays.asList(new Object[][]{
                {"json/customerRegistrationRequestSuccess1.json"},
                {"json/customerRegistrationRequestFailure2.json"},
        });
    }

    public CustomerRegistrationRequestMapperTest(String actualResults) {
        this.actualResults = actualResults;
    }

    @Before
    public void setUp() throws IOException {
        classUnderTest = new CustomerRegistrationRequestMapperImpl();
    }

    @Test
    public void  testMap() throws Exception {
        CustomerRegistrationRequest customerRegistrationRequest = loadCustomerRegistrationRequest(actualResults);
        CustomerRegistrationEntity customerRegistrationEntity = classUnderTest.map(customerRegistrationRequest, OffsetDateTime.now());
        Registration registration = customerRegistrationRequest.getData().getRegistration();

        Assert.assertNotNull(customerRegistrationEntity);
        Assert.assertNotNull(customerRegistrationEntity.getCaptDate());
        Assert.assertEquals(null, customerRegistrationEntity.getId());
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

    private CustomerRegistrationRequest loadCustomerRegistrationRequest(String filename) throws IOException {
        String json = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream(filename), StandardCharsets.UTF_8);
        return JsonPojoConverter.convertJsonToObject(json, CustomerRegistrationRequest.class);
    }
}
