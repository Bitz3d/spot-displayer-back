package pl.rafalab.spotdisplayer.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;
import pl.rafalab.spotdisplayer.Services.WeldingSpotService;
import pl.rafalab.spotdisplayer.Utils.UsefulUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@RestController
public class WeldingSpotController {

    private WeldingSpotService weldingSpotService;
    private UsefulUtils usefulUtils;

    public WeldingSpotController(WeldingSpotService weldingSpotService, UsefulUtils usefulUtils) {
        this.weldingSpotService = weldingSpotService;
        this.usefulUtils = usefulUtils;
    }

    @GetMapping("/welding-spots")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Set<WeldingSpot>> getAllUserWeldingSpots(HttpServletRequest request) {
        Set<WeldingSpot> byMyUser = weldingSpotService.findByMyUser(usefulUtils.getUserFromRequest(request));
        return new ResponseEntity<>(byMyUser, HttpStatus.OK);
    }

    @GetMapping("/welding-spots/{model}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Set<WeldingSpot>> getAllUserWeldingSpotsForModel(HttpServletRequest request, @PathVariable(value = "model") String model) {
        Set<WeldingSpot> byMyUser = weldingSpotService.findByMyUserAndModelName(usefulUtils.getUserFromRequest(request), model.toLowerCase());
        return new ResponseEntity<>(byMyUser, HttpStatus.OK);
    }


}
