package com.sensor.app.sensor_app_movil.security.jwt;


import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private static final String EMAIL_CLAIM = "email";
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${app.jwt.issuer}")
    private String issuer;

    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + expiration*1000);



        return Jwts.builder()
                .setIssuer(issuer)
                .claim(EMAIL_CLAIM,email)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();


    }

    public String getEmail(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.get(EMAIL_CLAIM).toString();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            //Firma JWT no valida
            logger.error("firma no valida");
            throw new JwtException("Problemas con el inicio de sesion, por favor inicie sesion nuevamente, firma no valida");

        } catch (MalformedJwtException ex) {
            //Token JWT no valida
            logger.error("Token mal formado");
            throw new JwtException("Problemas con el inicio de sesion, por favor inicie sesion nuevamente, Token mal formado");

        } catch (ExpiredJwtException ex) {
            //expiro el JWT
            logger.error("Token expirado");
            throw new JwtException("Se expiro el inicio de sesion, por favor inicie sesion nuevamente, Token expirado");

        } catch (UnsupportedJwtException ex) {
            //Token JWT no compatible
            logger.error("Token no soportado");
            throw new JwtException("Problemas con el inicio de sesion, por favor inicie sesion nuevamente, Token no soportado");

        } catch (IllegalArgumentException ex) {
            //La cadena claims JWT esta vacia
            logger.error("Token vacio");
            throw new JwtException("Problemas con el inicio de sesion, por favor inicie sesion nuevamente, Token vacio");

        }


    }


}


