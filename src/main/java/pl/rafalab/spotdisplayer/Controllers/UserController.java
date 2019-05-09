package pl.rafalab.spotdisplayer.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.rafalab.spotdisplayer.Services.MyUserService;
import pl.rafalab.spotdisplayer.model.Dto.MyUserDto;
import pl.rafalab.spotdisplayer.model.MyUser;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    private MyUserService myUserService;

    public UserController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MyUser> saveUser(@RequestBody  @Valid MyUserDto user){

        MyUser save = myUserService.save(user);

        return  new ResponseEntity<>(save, HttpStatus.OK);
    }
}
