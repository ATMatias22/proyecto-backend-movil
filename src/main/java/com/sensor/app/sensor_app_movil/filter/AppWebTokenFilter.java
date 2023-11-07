package com.sensor.app.sensor_app_movil.filter;

import com.sensor.app.sensor_app_movil.exception.JwtAppWebException;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

public class AppWebTokenFilter extends OncePerRequestFilter {

    @Value("${app.jwt.secret.backend-web}")
    private String secret;

    private static final Logger log = LoggerFactory.getLogger(AppWebTokenFilter.class);

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("estoy en filter app web token");
        String methodHttp = request.getMethod();
        System.out.println(resolver);

        if (methodHttp.equalsIgnoreCase("POST")) {

            String jwt = this.getJwtFromRequest(request);

            if (jwt != null) {
                try{
                    if (this.validateToken(jwt)) {
                        filterChain.doFilter(request, response);
                    }
                }catch (JwtAppWebException je){
                    log.info(je.getMessage());
                    this.resolver.resolveException(request,response,null, new JwtAppWebException(je.getMessage()));
                }catch (Exception ex){
                    log.info(ex.getMessage());
                    resolver.resolveException(request, response, null, new Exception("Problemas en el servidor"));
                }
            } else {
                resolver.resolveException(request, response, null, new JwtAppWebException("Es necesario que el header exista y no sea vacio"));
            }
        }

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String jwt = request.getHeader("X-Backend-Token");
        if (jwt != null && !jwt.isEmpty()) {
            return jwt;
        }
        return null;
    }


    private boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            //Firma JWT no valida
            logger.error("firma no valida");
            throw new JwtAppWebException("Firma no valida");

        } catch (MalformedJwtException ex) {
            //Token JWT no valida
            logger.error("Token mal formado");
            throw new JwtAppWebException("Token mal formado");

        } catch (ExpiredJwtException ex) {
            //expiro el JWT
            logger.error("Token expirado");
            throw new JwtAppWebException("Token expirado");

        } catch (UnsupportedJwtException ex) {
            //Token JWT no compatible
            logger.error("Token no soportado");
            throw new JwtAppWebException("Token no soportado");

        } catch (IllegalArgumentException ex) {
            //La cadena claims JWT esta vacia
            logger.error("Token vacio");
            throw new JwtAppWebException("Token vacio");
        }


    }


}
