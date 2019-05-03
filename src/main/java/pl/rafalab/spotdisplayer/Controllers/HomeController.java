package pl.rafalab.spotdisplayer.Controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/")
public class HomeController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ResponseBody
    @Secured("ADMIN")
    public String hello(){
        return "Anita jest piękna";
    }

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    @ResponseBody
    @Secured("USER")
    public String okhello(){
        return "Anita NIE JEST PIEKNA";
    }


}
