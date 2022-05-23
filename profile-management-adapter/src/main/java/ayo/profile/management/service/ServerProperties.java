package ayo.profile.management.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "ayo.properties")
public class ServerProperties {

    private String server;
    private String mtnServerUrl;

}
