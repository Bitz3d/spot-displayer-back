package pl.rafalab.spotdisplayer.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.rafalab.spotdisplayer.model.MyUser;

@Component
public class JwtGenerator {

    @Value("${signing.key.secret}")
    private String secret;

    public String generate(MyUser myUser) {

        Claims claims = Jwts.claims().setSubject(myUser.getUsername());
        claims.put("userId", myUser.getId());
        claims.put("role", myUser.getRoles());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.ES512, secret)
                .compact();
    }
}
