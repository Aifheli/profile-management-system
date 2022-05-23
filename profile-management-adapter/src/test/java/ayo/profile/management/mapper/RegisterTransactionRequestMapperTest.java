package ayo.profile.management.mapper;

import ayo.profile.management.entities.CustomerRegistrationEntity;
import ayo.profile.management.mock.RegisterTransactionRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.OffsetDateTime;

/**
 * Created by Aifheli Maganya on 2022/05/23.
 */
public class RegisterTransactionRequestMapperTest {

    private RegisterTransactionRequestMapper classUnderTest;

    @Before
    public void setUp() throws IOException {
        classUnderTest = new RegisterTransactionRequestMapperImpl();
    }

    @Test
    public void  testMap() throws Exception {

        CustomerRegistrationEntity customerRegistrationEntity = getCustomerRegistrationEntity();
        RegisterTransactionRequest registerTransactionRequest = classUnderTest.map(customerRegistrationEntity);

        Assert.assertNotNull(registerTransactionRequest);
        Assert.assertNotNull(registerTransactionRequest.getCreatedDate());
        Assert.assertEquals(java.util.Optional.of(Long.parseLong("25")).get(), registerTransactionRequest.getId());
        Assert.assertEquals("Maganya", registerTransactionRequest.getSurname());
        Assert.assertEquals("Modise", registerTransactionRequest.getName());
        Assert.assertEquals("", registerTransactionRequest.getSaIdNo());
        Assert.assertEquals("5555555555555553698", registerTransactionRequest.getForeignIdNo());

    }

    private CustomerRegistrationEntity getCustomerRegistrationEntity() throws IOException {
        CustomerRegistrationEntity entity = new CustomerRegistrationEntity();
        entity.setId(25L);
        entity.setSurname("Maganya");
        entity.setName("Modise");
        entity.setRegNo("2589999");
        entity.setSaIdNo("");
        entity.setForeignIdNo("5555555555555553698");
        entity.setCreatedDate(OffsetDateTime.now());
        return entity;
    }

}
