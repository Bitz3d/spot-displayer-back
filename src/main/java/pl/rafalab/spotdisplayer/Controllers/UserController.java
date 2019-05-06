package pl.rafalab.spotdisplayer.Controllers;

import org.springframework.web.bind.annotation.*;
import pl.rafalab.spotdisplayer.Services.MyUserService;
import pl.rafalab.spotdisplayer.model.Dto.MyUserDto;
import pl.rafalab.spotdisplayer.model.MyUser;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    private MyUserService myUserService;

    public UserController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public MyUser saveUser(@RequestBody MyUserDto user){
        return myUserService.save(user);
    }
}
