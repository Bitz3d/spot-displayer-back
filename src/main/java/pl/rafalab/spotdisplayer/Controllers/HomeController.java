package pl.rafalab.spotdisplayer.Controllers;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class HomeController {


    @RequestMapping(value = "/admin ",method = RequestMethod.GET)
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
