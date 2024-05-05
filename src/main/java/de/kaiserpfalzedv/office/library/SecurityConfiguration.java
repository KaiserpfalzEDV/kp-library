/*
 * Copyright (c) 2024 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.kaiserpfalzedv.office.library;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import de.kaiserpfalzedv.office.library.security.KeycloakLogoutHandler;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


/**
 * 
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2024-04-21
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Slf4j
public class SecurityConfiguration {
    private static final String GROUPS = "groups";
    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";

    private final KeycloakLogoutHandler keycloakLogoutHandler;

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(sessionRegistry());
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers(new AntPathRequestMatcher("/clients*", HttpMethod.OPTIONS.name()))
            .permitAll()

            .requestMatchers(new AntPathRequestMatcher("/clients*"))
            .hasRole("user")
            
            .requestMatchers(new AntPathRequestMatcher("/"))
            .permitAll()
            
            .anyRequest()
            .authenticated()
            );

        http.oauth2ResourceServer(oauth2 -> oauth2
            .jwt(Customizer.withDefaults())
            );

        http.oauth2Login(Customizer.withDefaults())
            .logout(logout -> logout
                .addLogoutHandler(keycloakLogoutHandler).logoutSuccessUrl("/")
            );
        
        return http.build();
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return authorities -> {
            var authority = authorities.iterator().next();

            if (authority instanceof OidcUserAuthority) {
                return getOidcUserInfo(authority);
            } else {
                return getOauth2UserAttributes(authority);
            }
        };
    }

    @SuppressWarnings("unchecked")
    private Set<GrantedAuthority> getOidcUserInfo(GrantedAuthority authority) {
        Set<GrantedAuthority> result = new HashSet<>();

        log.debug("Reading OIDC UserInfo. authority={}", authority);

        var oidcUserAuthority = (OidcUserAuthority) authority;
        var userInfo = oidcUserAuthority.getUserInfo();

        // Tokens can be configured to return roles under
        // Groups or REALM ACCESS hence have to check both
        if (userInfo.hasClaim(REALM_ACCESS_CLAIM)) {
            var realmAccess = userInfo.getClaimAsMap(REALM_ACCESS_CLAIM);
            var roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);
            result.addAll(generateAuthoritiesFromClaim(roles));
        } else if (userInfo.hasClaim(GROUPS)) {
            Collection<String> roles = (Collection<String>) userInfo.getClaim(GROUPS);
            result.addAll(generateAuthoritiesFromClaim(roles));
        } else {
            log.info("This OIDC UserInfo does weather contain a '{}' nor a '{}'. No Roles extracted.", REALM_ACCESS_CLAIM, GROUPS);
        }

        log.debug("Read roles from OIDC UserInfo. roles={}", result);
        return result;
    }

    @SuppressWarnings("unchecked")
    private Set<GrantedAuthority> getOauth2UserAttributes(GrantedAuthority authority) {
        Set<GrantedAuthority> result = new HashSet<>();

        log.debug("Reading OAuth2 User Attributes. authority={}", authority);

        var oauth2UserAuthority = (OAuth2UserAuthority) authority;
        Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

        if (userAttributes.containsKey(REALM_ACCESS_CLAIM)) {
            Map<String, Object> realmAccess =
                    (Map<String, Object>) userAttributes.get(REALM_ACCESS_CLAIM);
            Collection<String> roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);
            result.addAll(generateAuthoritiesFromClaim(roles));
        } else {
            log.info("This OAuth2 User Attributes don't contain '{}'. No roles extracted.", ROLES_CLAIM);
        }

        log.debug("Read roles from OAuth2 User Attributes. roles={}", result);
        return result;
    }

    Collection<GrantedAuthority> generateAuthoritiesFromClaim(Collection<String> roles) {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toList());
    }
}
