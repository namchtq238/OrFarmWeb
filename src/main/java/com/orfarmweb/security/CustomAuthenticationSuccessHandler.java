package com.orfarmweb.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String redirectURL = request.getContextPath();
        if (userDetails.hasRole("ADMIN")) {
            redirectURL = "admin";
        } else if (userDetails.hasRole("CUSTOMER")) {
            redirectURL = "index";
        }
        HttpSession session =  request.getSession();
        session.setAttribute("username", userDetails.getUsername());

        response.sendRedirect(redirectURL);

    }
}
