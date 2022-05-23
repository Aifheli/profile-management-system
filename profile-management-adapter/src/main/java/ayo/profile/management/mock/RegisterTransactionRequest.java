package ayo.profile.management.mock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

/**
 * RegisterTransactionRequest
 */
@Getter
@Setter
public class RegisterTransactionRequest {

    @JsonProperty("id")
    private Long id = null;
    @JsonProperty("surname")
    private String surname = null;
    @JsonProperty("name")
    private String name = null;
    @JsonProperty("reg_no")
    private String regNo = null;
    @JsonProperty("sa_id_no")
    private String saIdNo;
    @JsonProperty("foreign_id_no")
    private String foreignIdNo;
    @JsonProperty("created_date")
    private OffsetDateTime createdDate;

}
