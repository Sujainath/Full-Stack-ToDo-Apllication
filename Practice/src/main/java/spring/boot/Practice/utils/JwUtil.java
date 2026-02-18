package spring.boot.Practice.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Component
public class JwUtil {
    private final String SECRET ="This is the Secret Key for the login validation";
    private final Integer EXPIRATION = 1000*60*5;
    private  final Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(secretKey, SignatureAlgorithm.HS256)// is like post cover seal
                .compact();
    }

    public String extractEmail(String token){
        return Jwts.parserBuilder() // Is like a open the post letter
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean validateJwtToken(String token){
        try{
            extractEmail(token);
            return  true;
        }catch (JwtException exception){
            return false;
        }
    }


}
