package ayo.profile.management.mapper;

import ayo.profile.management.entities.CustomerRegistrationEntity;
import ayo.profile.management.models.CustomerRegistrationResponseData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by Aifheli Maganya on 2022/05/21.
 */
@Mapper(componentModel = "spring")
public interface CustomerRegistrationResponseDataMapper {

    @Mapping(target = "registration.regStatus",  source = "entity.regStatus")
    @Mapping(target = "registration.bankCode",  source = "entity.bankCode")
    @Mapping(target = "registration.bankName",  source = "entity.bankName")
    @Mapping(target = "registration.branchCode",  source = "entity.branchCode")
    @Mapping(target = "registration.accType",  source = "entity.accType")
    @Mapping(target = "registration.accNo",  source = "entity.accNo")
    @Mapping(target = "registration.regNo",  source = "entity.regNo")
    @Mapping(target = "registration.countryCode",  source = "entity.countryCode")
    @Mapping(target = "registration.captDate",  source = "entity.captDate")
    @Mapping(target = "registration.localTime",  source = "entity.localTime")
    @Mapping(target = "registration.localDate",  source = "entity.localDate")
    @Mapping(target = "registration.streetCity",  source = "entity.streetCity")
    @Mapping(target = "registration.streetSuburb",  source = "entity.streetSuburb")
    @Mapping(target = "registration.streetAddressLine",  source = "entity.streetAddressLine")
    @Mapping(target = "registration.foreignIdNo",  source = "entity.foreignIdNo")
    @Mapping(target = "registration.saIdNo",  source = "entity.saIdNo")
    @Mapping(target = "registration.placeOfBirth",  source = "entity.placeOfBirth")
    @Mapping(target = "registration.dateOfBirth",  source = "entity.dateOfBirth")
    @Mapping(target = "registration.phoneNo",  source = "entity.phoneNo")
    @Mapping(target = "registration.gender",  source = "entity.gender")
    @Mapping(target = "registration.proCode",  source = "entity.proCode")
    @Mapping(target = "registration.name",  source = "entity.name")
    @Mapping(target = "registration.surname",  source = "entity.surname")
    CustomerRegistrationResponseData map(CustomerRegistrationEntity entity);
}
