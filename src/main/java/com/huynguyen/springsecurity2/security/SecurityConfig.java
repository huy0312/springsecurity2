package com.huynguyen.springsecurity2.security;

import com.huynguyen.springsecurity2.service.CustomSuccessHandler;
import com.huynguyen.springsecurity2.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomSuccessHandler customSuccessHandler;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers("/admin-page").hasAuthority("ADMIN")
                                .requestMatchers("/user-page").hasAuthority("USER")
                                .requestMatchers("/login").hasAuthority("LOGIN")
                                .requestMatchers("/registration", "/css/**","/avatar/**").permitAll()
                                .anyRequest().authenticated())
//                .oauth2Login(oauth2 ->
//                        oauth2
//                                .loginPage("/login")
//                                .userInfoEndpoint(userInfo ->
//                                        userInfo
//                                                .userService(oauth2UserService())
//                                )
//                                .successHandler(oauth2LoginSuccessHandler())
//                                .loginProcessingUrl("/login").permitAll())

                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .successHandler(customSuccessHandler).permitAll())
                .logout(form ->
                        form.invalidateHttpSession(true).clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login?logout").permitAll());
        return http.build();
    }

    @Bean
    public OidcUserService oidcUserService() {
        return new OidcUserService();
    }

    @Bean
    public OidcUserService oauth2UserService() {
        return new OidcUserService();
    }

    @Bean
    public AuthenticationSuccessHandler oauth2LoginSuccessHandler() {
        return (request, response, authentication) -> {
            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
            // Your custom logic after successful OAuth2 login
            response.sendRedirect("/user-page");
        };
    }

    @Autowired
    public void userConfig(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());

    }
}
