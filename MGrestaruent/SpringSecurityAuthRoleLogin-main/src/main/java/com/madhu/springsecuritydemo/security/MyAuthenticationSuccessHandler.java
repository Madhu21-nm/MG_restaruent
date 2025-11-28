package com.madhu.springsecuritydemo.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        String from = request.getParameter("from");
        if ("checkout".equals(from)) {
            setDefaultTargetUrl("/checkout?confirmed=true");
        } else {
            boolean roleadmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
            if (roleadmin) {
                setDefaultTargetUrl("/admin");
            } else {
                setDefaultTargetUrl("/user");
            }
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}