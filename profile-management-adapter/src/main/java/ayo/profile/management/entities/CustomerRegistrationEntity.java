package ayo.profile.management.entities;

import lombok.Data;
import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "Customer_Registration")
public class CustomerRegistrationEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_record_seq")
    @SequenceGenerator(name="transaction_record_seq", sequenceName = "transaction_record_seq", allocationSize = 1)
    private Long id;
    @Column(length = 36)
    private String surname;
    @Column(length = 36)
    private String name;
    @Column(length = 36)
    private Integer proCode;
    @Column(length = 36)
    private String gender;
    @Column(length = 36)
    private String phoneNo;
    @Column(length = 36)
    private String dateOfBirth;
    @Column(length = 36)
    private String placeOfBirth;
    @Column(length = 13)
    private String saIdNo;
    @Column(length = 16)
    private String foreignIdNo;
    @Column(length = 36)
    private String streetAddressLine;
    @Column(length = 36)
    private String streetSuburb;
    @Column(length = 36)
    private String streetCity;
    @Column(length = 36)
    private Integer localDate;
    @Column(length = 36)
    private Integer localTime;
    @Column(length = 36)
    private Integer captDate;
    @Column(length = 36)
    private Integer countryCode;
    @Column(length = 36)
    private String regNo;
    @Column(length = 36)
    private String accNo;
    @Column(length = 36)
    private String accType;
    @Column(length = 36)
    private Integer branchCode;
    @Column(length = 36)
    private String bankName;
    @Column(length = 36)
    private String bankCode;
    @Column(length = 36)
    private String regStatus;
    @Column(length = 20)
    private OffsetDateTime createdDate;

}
