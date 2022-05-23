package ayo.profile.management.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Aifheli Maganya on 2022/05/21.
 */
@Data
@ConfigurationProperties(prefix = "ayo.ssl")
public class SSLConfiguration {

    private String keyStore;
    private String trustStore;
    private String keyStorePassword;
    private String trustStorePassword;
    private String keysPath;
    private final String JKS = "jks";

}
