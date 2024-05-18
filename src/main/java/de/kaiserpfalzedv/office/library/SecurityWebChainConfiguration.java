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



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;


/**
 * 
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2024-05-17
 */
@Configuration
public class SecurityWebChainConfiguration {    

    @Bean
    public WebSecurityCustomizer WebSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/actuator/**");
    }

    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        http
            .anonymous(a -> a.authorities("ANONYMOUS"))
            .authorizeHttpRequests(r -> r
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().hasRole("ANONYMOUS")
        );

        return http.build();
    }

    @Bean
    public RestTemplate rest() {
        RestTemplate result =  new RestTemplate();

        result.getInterceptors().add((r,b,e) -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null) {
                return e.execute(r, b);
            }

            if (!(authentication.getCredentials() instanceof AbstractOAuth2Token)) {
                return e.execute(r, b);
            }

            AbstractOAuth2Token token = (AbstractOAuth2Token) authentication.getCredentials();
            r.getHeaders().setBearerAuth(token.getTokenValue());
            return e.execute(r, b);
        });

        return result;
    }
}
