package ayo.profile.management.mock;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
/**
 * RegisterTransactionResponse
 */
@Getter
@Setter
public class RegisterTransactionResponse {

    @JsonProperty("status")
    private Status status = null;

    @JsonProperty("source")
    private Object error = new Object();





}
