package pl.denys.gateway_service.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import pl.denys.gateway_service.security.filter.CorrelationFilter;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityWebFilterChain httpSecurity(ServerHttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(
                        authorize -> {
                            authorize.pathMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                            authorize.pathMatchers("/actuator/**").permitAll();
                            authorize.anyExchange().authenticated();
                        })
                .oauth2ResourceServer(o2 -> {
                    o2.jwt(Customizer.withDefaults());
                })
                .addFilterBefore(new CorrelationFilter(), SecurityWebFiltersOrder.AUTHENTICATION);
        return httpSecurity.build();
    }
}
