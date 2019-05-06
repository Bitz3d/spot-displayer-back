package pl.rafalab.spotdisplayer.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class HomeController {


    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String hello(){
        return "Test admin";
    }

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public String okhello(){

        return "test user";
    }


}
