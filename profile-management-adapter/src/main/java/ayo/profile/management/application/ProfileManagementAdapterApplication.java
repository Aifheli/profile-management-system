package ayo.profile.management.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Created by Aifheli Maganya on 2022/05/18.
 */
@SpringBootApplication(scanBasePackages = {"ayo.profile.management"})
public class ProfileManagementAdapterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfileManagementAdapterApplication.class, args);
    }

}
