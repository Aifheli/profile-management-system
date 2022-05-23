package ayo.profile.management.mapper;

import ayo.profile.management.entities.CustomerRegistrationEntity;
import ayo.profile.management.models.CustomerRegistrationResponseData;
import ayo.profile.management.models.Registration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

/**
 * Created by Aifheli Maganya on 2022/05/23.
 */
public class CustomerRegistrationResponseDataMapperTest {

    private CustomerRegistrationResponseDataMapper classUnderTest;

    @Before
    public void setUp() throws IOException {
        classUnderTest = new CustomerRegistrationResponseDataMapperImpl();
    }

    @Test
    public void  testMap() throws Exception {

        CustomerRegistrationEntity customerRegistrationEntity = getCustomerRegistrationEntity();
        CustomerRegistrationResponseData customerRegistrationResponseData = classUnderTest.map(customerRegistrationEntity);
        Registration registration = customerRegistrationResponseData.getRegistration();

        Assert.assertNotNull(customerRegistrationResponseData);
        Assert.assertEquals("Maganya", registration.getSurname());
        Assert.assertEquals("Modise", registration.getName());
        Assert.assertEquals("555555555555555", registration.getSaIdNo());
    }

    private CustomerRegistrationEntity getCustomerRegistrationEntity() throws IOException {
        CustomerRegistrationEntity entity = new CustomerRegistrationEntity();
        entity.setSurname("Maganya");
        entity.setName("Modise");
        entity.setSaIdNo("555555555555555");
        return entity;
    }
}
