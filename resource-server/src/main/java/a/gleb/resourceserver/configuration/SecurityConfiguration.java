package a.gleb.resourceserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfiguration {

    private static final List<String> DEFAULT_UNPROTECTED_PATTERNS = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/actuator/**",
            "/error"
    );

    @Bean
    public SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement(it -> it.sessionCreationPolicy(STATELESS))
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(this::configuraOauth2Requests)
                .oauth2ResourceServer(
                        (oauth2ResourceServer) -> oauth2ResourceServer.jwt((jwt) -> {
                            var jwtAuthenticationConverter = new JwtAuthenticationConverter();
                            jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                                    new KeycloakJwtGrantedAuthoritiesConverter()
                            );

                            jwt.jwtAuthenticationConverter(jwtAuthenticationConverter);
                        })
                );

        return httpSecurity.build();
    }

    private void configuraOauth2Requests(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry matcherRegistry
    ) {
        matcherRegistry.requestMatchers("/api/v1/**").authenticated();
        matcherRegistry.requestMatchers(DEFAULT_UNPROTECTED_PATTERNS.toArray(String[]::new)).permitAll();
    }

    private static class KeycloakJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

        public static final String ROLE_PREFIX = "ROLE_%s";
        public static final String REALM_ACCESS_CLAIM = "realm_access";
        public static final String REALM_ACCESS_CLAIM_ROLES_KEY = "roles";

        @Override
        public Collection<GrantedAuthority> convert(@NonNull Jwt token) {

            ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            getRoles(token).forEach(
                    role -> grantedAuthorities.add(new SimpleGrantedAuthority(String.format(ROLE_PREFIX, role)))
            );

            return grantedAuthorities;
        }

        private Collection<String> getRoles(Jwt jwt) {
            Object realmAccess = jwt.getClaim(REALM_ACCESS_CLAIM);
            if (realmAccess instanceof Map) {
                Object roles = ((Map<?, ?>) realmAccess).get(REALM_ACCESS_CLAIM_ROLES_KEY);
                if (roles instanceof Collection) {
                    return ((Collection<?>) roles).stream().map(Object::toString).toList();
                }
            }

            return emptyList();
        }
    }
}
