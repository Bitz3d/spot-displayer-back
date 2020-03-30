package pl.rafalab.spotdisplayer.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.rafalab.spotdisplayer.Models.Dtos.MyUserDto;
import pl.rafalab.spotdisplayer.Models.MyRole;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Services.MyUserService;

import javax.validation.Valid;
import java.util.HashSet;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    private MyUserService myUserService;

    public UserController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MyUser> saveUser(@RequestBody @Valid MyUserDto user) {
        MyUser save = myUserService.save(user);

        return new ResponseEntity<>(save, HttpStatus.OK);
    }
}
