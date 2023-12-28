package ipt.lei.dam.ncrapi.security;

import ipt.lei.dam.ncrapi.LoggingInterceptor;
import ipt.lei.dam.ncrapi.database.repos.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.recoverToken(request);
        logger.info("LOGGED TOKEN: " + token);
        if(token != null && SecurityContextHolder.getContext().getAuthentication() == null){
            String email = tokenService.validateToken(token);
            logger.info("LOGGED EMAIL: " + email);
            UserDetails user = userRepository.getUserByEmail(email);
            if(user != null){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null , user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                logger.warn("ERROR GETTING USER");
            }
        }else{
            logger.warn("ERROR GETTING TOKEN: " + token + " AND AUTHENTICATION CONTEXT: " + SecurityContextHolder.getContext().getAuthentication());
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
         String authHeader = request.getHeader("Authorization");
         if(authHeader == null)
             return null;
         return authHeader.replace("Bearer ", "");
    }
}
