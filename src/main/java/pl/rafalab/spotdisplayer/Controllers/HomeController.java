package pl.rafalab.spotdisplayer.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.rafalab.spotdisplayer.security.TokenProvider;

@RestController
@RequestMapping("/api/")
public class HomeController {


    TokenProvider tokenProvider;

    public HomeController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String hello() {
        return "Test admin";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public String okhello() {

        return "test user";
    }

}
