package ua.denys.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import ua.denys.config.security.filter.TokenFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .authorizeHttpRequests(
            request -> {
              request.requestMatchers("/token").authenticated();
            })
        .addFilterAt(new TokenFilter(), AuthenticationFilter.class);

    return httpSecurity.build();
  }
}
