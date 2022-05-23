package ayo.profile.management.mapper;

import ayo.profile.management.entities.CustomerRegistrationEntity;
import ayo.profile.management.models.CustomerRegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;

/**
 * Created by Aifheli Maganya on 2022/05/21.
 */
@Mapper(componentModel = "spring")
public interface CustomerRegistrationRequestMapper {

    @Mapping(target = "createdDate",  source = "createdDate")
    @Mapping(target = "regStatus",  source = "request.data.registration.regStatus")
    @Mapping(target = "bankCode",  source = "request.data.registration.bankCode")
    @Mapping(target = "bankName",  source = "request.data.registration.bankName")
    @Mapping(target = "branchCode",  source = "request.data.registration.branchCode")
    @Mapping(target = "accType",  source = "request.data.registration.accType")
    @Mapping(target = "accNo",  source = "request.data.registration.accNo")
    @Mapping(target = "regNo",  source = "request.data.registration.regNo")
    @Mapping(target = "countryCode",  source = "request.data.registration.countryCode")
    @Mapping(target = "captDate",  source = "request.data.registration.captDate")
    @Mapping(target = "localTime",  source = "request.data.registration.localTime")
    @Mapping(target = "localDate",  source = "request.data.registration.localDate")
    @Mapping(target = "streetCity",  source = "request.data.registration.streetCity")
    @Mapping(target = "streetSuburb",  source = "request.data.registration.streetSuburb")
    @Mapping(target = "streetAddressLine",  source = "request.data.registration.streetAddressLine")
    @Mapping(target = "foreignIdNo",  source = "request.data.registration.foreignIdNo")
    @Mapping(target = "saIdNo",  source = "request.data.registration.saIdNo")
    @Mapping(target = "placeOfBirth",  source = "request.data.registration.placeOfBirth")
    @Mapping(target = "dateOfBirth",  source = "request.data.registration.dateOfBirth")
    @Mapping(target = "phoneNo",  source = "request.data.registration.phoneNo")
    @Mapping(target = "gender",  source = "request.data.registration.gender")
    @Mapping(target = "proCode",  source = "request.data.registration.proCode")
    @Mapping(target = "name",  source = "request.data.registration.name")
    @Mapping(target = "surname",  source = "request.data.registration.surname")
    CustomerRegistrationEntity map(CustomerRegistrationRequest request,  OffsetDateTime createdDate);

}
