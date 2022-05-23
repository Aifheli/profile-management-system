package ayo.profile.management.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/**
 * Created by Aifheli Maganya on 2022/05/21.
 */
@Configuration
@EnableJpaRepositories(basePackages = "ayo.profile.management.repository")
@EnableJpaAuditing
@EntityScan(basePackages = "ayo.profile.management.entities")
public class CustomerRegistrationRepositoryConfig {
}
