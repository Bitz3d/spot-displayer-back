package pl.rafalab.spotdisplayer.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;
import pl.rafalab.spotdisplayer.Repository.WeldingSpotRepository;
import pl.rafalab.spotdisplayer.Services.MyUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/")
public class HomeController {


    private MyUserService myUserService;
    private WeldingSpotRepository weldingSpotService;

    public HomeController(MyUserService myUserService, WeldingSpotRepository weldingSpotService) {
        this.myUserService = myUserService;
        this.weldingSpotService = weldingSpotService;
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

        MyUser rafal14 = myUserService.findOne("rafal14");

        Set<String> byMyUser = weldingSpotService.getAllBySpotNameAndUserId(rafal14.getId());


        List<WeldingSpot> saadsa = new ArrayList<>();

        WeldingSpot build = WeldingSpot.builder().spotName("lsp12758")
                .modelName("F56").pointX(2222.22).pointY(4443.33).pointZ(4553.32).id(2L).myUser(new MyUser()).build();


        boolean add = !byMyUser.contains(build.getSpotName());


        System.out.println(add);
        return "test user";
    }

}
