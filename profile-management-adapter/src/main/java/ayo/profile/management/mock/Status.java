package ayo.profile.management.mock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Status
 */
@Getter
@Setter
public class Status   {
    @JsonProperty("code")
    private Integer code = null;
    @JsonProperty("description")
    private String description = null;

}
