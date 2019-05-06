package pl.rafalab.spotdisplayer.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.rafalab.spotdisplayer.Services.MyUserService;
import pl.rafalab.spotdisplayer.model.AuthToken;
import pl.rafalab.spotdisplayer.model.LoginUser;
import pl.rafalab.spotdisplayer.model.MyUser;
import pl.rafalab.spotdisplayer.security.TokenProvider;

@RestController
@RequestMapping("/token")
public class TokenController {

    private TokenProvider tokenProvider;
    private AuthenticationManager authenticationManager;
    private MyUserService myUserService;

    public TokenController(TokenProvider tokenProvider, AuthenticationManager authenticationManager, MyUserService myUserService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.myUserService = myUserService;
    }

    @PostMapping("/generate-token")
    public ResponseEntity<?> register(@RequestBody LoginUser loginUser){

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }
}


