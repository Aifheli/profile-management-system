package ayo.profile.management.mapper;

import ayo.profile.management.entities.CustomerRegistrationEntity;
import ayo.profile.management.mock.RegisterTransactionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by Aifheli Maganya on 2022/05/21.
 */
@Mapper(componentModel = "spring")
public interface RegisterTransactionRequestMapper {

    @Mapping(target = "createdDate",  source = "customerRegistrationEntity.createdDate")
    @Mapping(target = "foreignIdNo",  source = "customerRegistrationEntity.foreignIdNo")
    @Mapping(target = "saIdNo",  source = "customerRegistrationEntity.saIdNo")
    @Mapping(target = "regNo",  source = "customerRegistrationEntity.regNo")
    @Mapping(target = "name",  source = "customerRegistrationEntity.name")
    @Mapping(target = "surname",  source = "customerRegistrationEntity.surname")
    @Mapping(target = "id",  source = "customerRegistrationEntity.id")
    RegisterTransactionRequest map(CustomerRegistrationEntity customerRegistrationEntity);

}
