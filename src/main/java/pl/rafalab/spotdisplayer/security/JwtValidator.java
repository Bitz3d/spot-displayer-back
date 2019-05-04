package pl.rafalab.spotdisplayer.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.rafalab.spotdisplayer.model.MyUser;

@Component
public class JwtValidator {

    @Value("${signing.key.secret}")
    private String secret;

    public MyUser validate(String token) {


        MyUser myUser = null;
        try {
        Claims body = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        myUser = new MyUser();

        myUser.setUsername(body.getSubject());
        myUser.setId(Long.parseLong((String) body.get("userId")));
        myUser.setRole((String) body.get("role"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return myUser;
    }
}
