package com.example.clientes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

/**
 * Configuração de segurança para autenticação básica no Spring Security.
 */
@Configuration
public class SecurityConfig {

    /**
     * Configuração de segurança baseada no HttpSecurity para definir as permissões das rotas e habilitar autenticação básica.
     *
     * @param http a configuração de segurança HTTP
     * @return SecurityFilterChain
     * @throws Exception em caso de erro na configuração
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()) // Autenticação HTTP Básica
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para facilitar os testes no Postman
                .headers(headers -> headers.frameOptions(frame -> frame.disable())); // Desabilita proteções de iframe para H2 Console

        return http.build();
    }

    /**
     * Configuração de UserDetailsService para autenticação em memória.
     * @return UserDetailsService com um usuário configurado em memória
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user")
                        .password("{noop}password") // 'noop' é um encoder que indica que a senha está em texto claro
                        .roles("USER") // Atribui a role 'USER'
                        .build()
        );
    }
}
