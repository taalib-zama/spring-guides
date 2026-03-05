// java
package com.sample.electronicStore.electronicStore.config;

import com.sample.electronicStore.electronicStore.security.JWTAuthenticationFilter;
import com.sample.electronicStore.electronicStore.security.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private JWTAuthenticationFilter filter;
    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        final String[] publicUrls = {
                "/auth/generate-token",
                "/auth/login-with-google",
                "/auth/regenerate-token",
                "/users/**",
                "/products/**",
                "/swagger-ui/**",
                "/webjars/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui.html"
        };

        // use the lambda form to avoid the deprecated cors() usage
        security.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        security.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint));

        security.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/v3/api-docs.yaml",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-ui/index.html",
                        "/swagger-resources/**",
                        "/webjars/**"
                ).permitAll()
                .requestMatchers(publicUrls).permitAll()

                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/**").hasAnyRole("ADMIN", "NORMAL")
                .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                .requestMatchers("/products/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers("/categories/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/auth/generate-token", "/auth/login-with-google", "/auth/regenerate-token").permitAll()

                .requestMatchers("/auth/**").authenticated()

                .anyRequest().authenticated()
        );

        security.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return security.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration cors = new CorsConfiguration();
            cors.setAllowedOriginPatterns(List.of("*"));
            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
            cors.setAllowCredentials(true);
            cors.setAllowedHeaders(List.of("*", "Authorization", "Content-Type", "Accept"));
            cors.setExposedHeaders(List.of("Authorization", "Content-Type"));
            cors.setMaxAge(3600L);
            return cors;
        };
    }

    // AuthenticationManager bean — use AuthenticationConfiguration to obtain the configured manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
