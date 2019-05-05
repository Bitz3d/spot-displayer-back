package pl.rafalab.spotdisplayer.Controllers;

import org.springframework.web.bind.annotation.*;
import pl.rafalab.spotdisplayer.model.MyUser;
import pl.rafalab.spotdisplayer.security.JwtGenerator;

@RestController
@RequestMapping("/token")
public class TokenController {

    private JwtGenerator jwtGenerator;

    public TokenController(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping
    public String generateToken(@RequestBody final MyUser myUser){
        return jwtGenerator.generate(myUser);
    }
}


