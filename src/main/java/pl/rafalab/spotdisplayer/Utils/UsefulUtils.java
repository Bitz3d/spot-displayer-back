package pl.rafalab.spotdisplayer.Utils;

import org.springframework.stereotype.Component;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Services.MyUserService;
import pl.rafalab.spotdisplayer.security.TokenProvider;

import javax.servlet.http.HttpServletRequest;

@Component
public class UsefulUtils {

    private TokenProvider tokenProvider;
    private MyUserService myUserService;

    public UsefulUtils(MyUserService myUserService, TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
        this.myUserService = myUserService;
    }

    public MyUser getUserFromRequest(HttpServletRequest request) {
        String token = request.getHeader(Constants.HEADER_STRING).replace(Constants.TOKEN_PREFIX, "");
        return myUserService.findOne(tokenProvider.getUsernameFromToken(token));
    }
}
