package com.wanho.trip.shared.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanho.trip.shared.response.APIResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            String token = resolveToken((HttpServletRequest)servletRequest);

            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (JwtException | IllegalArgumentException e) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setContentType("application/json");

            Map<String, String> errors = new HashMap<>();
            errors.put("token", e.getMessage());

            APIResponse<?> errorResponse = APIResponse.failed(HttpStatus.UNAUTHORIZED, errors);

            httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
