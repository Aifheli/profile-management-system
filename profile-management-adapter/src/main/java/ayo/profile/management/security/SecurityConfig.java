package ayo.profile.management.security;

import ayo.profile.management.jwt.JWTService;
import ayo.profile.management.models.CustomerRegistrationResponse;
import ayo.profile.management.models.CustomerRegistrationResponseData;
import ayo.profile.management.models.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${ayo.jwt.security.filter.bypass}")
    private boolean jwtBypassEnabled;
    private JWTService jwtService;
    private ObjectMapper objectMapper;

    public SecurityConfig(JWTService jwtService, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/ayo/mtn/fetch-cust-data/v1")
                .antMatchers("/ayo/mtn/fetch-all-cust-data/v1")
                .antMatchers("/actuator/health");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager(), jwtService, jwtBypassEnabled) {
            @Override
            public String createError(String code, String details) throws JsonProcessingException {
                CustomerRegistrationResponse customerRegistrationResponse = new CustomerRegistrationResponse();
                customerRegistrationResponse.setData(new CustomerRegistrationResponseData());
                Result result = new Result();
                result.setCode(code);
                result.setStatus(details);
                customerRegistrationResponse.setResult(result);
                return objectMapper.writeValueAsString(customerRegistrationResponse);
            }};


        http.cors().and().csrf().disable()
                .anonymous().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(jwtAuthenticationFilter)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

}
