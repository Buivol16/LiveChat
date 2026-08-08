package pl.denys.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import pl.denys.configuration.filter.CorrelationFilter;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .addFilterBefore(new CorrelationFilter(), AuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        requests -> {
                            requests.anyRequest().permitAll();
                        })
                .oauth2ResourceServer(o2 -> {
                    o2.jwt(Customizer.withDefaults());
                });

        return httpSecurity.build();
    }
}
