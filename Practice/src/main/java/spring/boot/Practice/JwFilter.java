package spring.boot.Practice;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import spring.boot.Practice.utils.JwUtil;

import java.io.IOException;
import java.util.List;

@Component

public class JwFilter extends OncePerRequestFilter {
@Autowired
    private  JwUtil jwUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
           String authHeaders = request.getHeader("Authorization");
           if (authHeaders != null && authHeaders.startsWith("Bearer ")){
               String token = authHeaders.substring(7);
               if (jwUtil.validateJwtToken(token)){
                   String email = jwUtil.extractEmail(token);
                   var auth = new UsernamePasswordAuthenticationToken(email,null, List.of());
                   SecurityContextHolder.getContext().setAuthentication(auth);
               }
           }
           filterChain.doFilter(request,response);
    }
}
