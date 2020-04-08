package pl.rafalab.spotdisplayer.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.rafalab.spotdisplayer.Models.AuthToken;
import pl.rafalab.spotdisplayer.Models.LoginUser;
import pl.rafalab.spotdisplayer.security.TokenProvider;

import javax.validation.Valid;
import java.util.concurrent.atomic.AtomicReference;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class TokenController {

    private TokenProvider tokenProvider;
    private AuthenticationManager authenticationManager;

    public TokenController(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/generate-token")
    public ResponseEntity<?> register(@RequestBody @Valid LoginUser loginUser) {
        AtomicReference<HttpStatus> httpStatus = new AtomicReference<>();
        String token = "";
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUser.getUsername(),
                            loginUser.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = tokenProvider.generateToken(authentication);
            httpStatus.set(HttpStatus.OK);
        } catch (Exception e) {
            httpStatus.set(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (token.isEmpty()) {
            return new ResponseEntity<>("Bad login or password", httpStatus.get());
        } else {
            return new ResponseEntity<>(new AuthToken(token), httpStatus.get());
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout() {
        return new ResponseEntity("Logout", HttpStatus.OK);
    }
}


