package com.madhu.springsecuritydemo.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // This makes ?redirect=/checkout work perfectly
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
            String redirect = request.getParameter("redirect");
            String target = (redirect != null && !redirect.isEmpty()) ? redirect : "/restarent1";
            response.sendRedirect(target);
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // keep disabled for now

            .authorizeHttpRequests(auth -> auth
                // PUBLIC PAGES – NO LOGIN NEEDED
                .requestMatchers(
                    "/", "/home", "/index",
                    "/menu", "/restaurant1", "/restarent1", "/about",
                    "/checkout", "/order-success",
                    "/login", "/register", "/signup",
                    "/error", "/favicon.ico"
                ).permitAll()

                // STATIC FILES
                .requestMatchers("/css/**", "/js/**", "/images/**", "/img/**", "/fonts/**").permitAll()

                // JavaScript calls this to check login status
                .requestMatchers("/api/check-auth").permitAll()

                // ONLY LOGGED-IN USERS CAN PLACE ORDER
                .requestMatchers("/order/place-order").authenticated()

                // THIS LINE MUST BE LAST
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(successHandler())   // uses our redirect logic
                .defaultSuccessUrl("/restarent1")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }
}