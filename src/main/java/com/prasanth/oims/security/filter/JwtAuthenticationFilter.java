package com.prasanth.oims.security.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.prasanth.oims.entity.Role;
import com.prasanth.oims.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                String authHeader = request.getHeader("Authorization");
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    filterChain.doFilter(request, response);
                    return;
                }
                String token = authHeader.substring(7);
                if(jwtUtil.validateToken(token)){
                    Long userId = jwtUtil.extractUserId(token);
                    Role role = jwtUtil.extractRole(token);
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+role.name());

                    UsernamePasswordAuthenticationToken authentication =new UsernamePasswordAuthenticationToken(userId,null,List.of(authority));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
                
                filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getRequestURI();
        return path.startsWith("/swagger-ui/") || path.equals("/swagger-ui.html") || path.startsWith("/v3/api-docs/");
    }
    

}
