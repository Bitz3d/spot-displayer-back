package pl.rafalab.spotdisplayer.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.rafalab.spotdisplayer.Commons.Mapper;
import pl.rafalab.spotdisplayer.Models.Dtos.WeldingSpotsDto;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;
import pl.rafalab.spotdisplayer.Services.WeldingSpotService;
import pl.rafalab.spotdisplayer.Utils.UsefulUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class WeldingSpotController {

    private final WeldingSpotService weldingSpotService;
    private final UsefulUtils usefulUtils;
    private final Mapper<WeldingSpot, WeldingSpotsDto> weldingSpotsDtoMapper;

    public WeldingSpotController(WeldingSpotService weldingSpotService, UsefulUtils usefulUtils, Mapper<WeldingSpot, WeldingSpotsDto> weldingSpotsDtoMapper) {
        this.weldingSpotService = weldingSpotService;
        this.usefulUtils = usefulUtils;
        this.weldingSpotsDtoMapper = weldingSpotsDtoMapper;
    }

    @GetMapping("/welding-spots")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Set<WeldingSpotsDto>> getAllUserWeldingSpots(HttpServletRequest request) {
        Set<WeldingSpot> byMyUser = weldingSpotService.findByMyUser(usefulUtils.getUserFromRequest(request));

        Set<WeldingSpotsDto> weldingSpotsDtosSet = new HashSet<>();

        byMyUser.forEach(x -> weldingSpotsDtosSet.add(weldingSpotsDtoMapper.map(x)));

        return new ResponseEntity<>(weldingSpotsDtosSet, HttpStatus.OK);
    }

    @GetMapping("/models-names")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Set<String>> getAllUserModels(HttpServletRequest request) {
        Set<String> distinctModelNameByUser = weldingSpotService.findDistinctModelNameByUser(usefulUtils.getUserFromRequest(request));
        return new ResponseEntity<>(distinctModelNameByUser, HttpStatus.OK);
    }

    @GetMapping("/welding-spots/{model}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Set<WeldingSpotsDto>> getAllUserWeldingSpotsForModel(HttpServletRequest request, @PathVariable(value = "model") String model) {
        Set<WeldingSpot> byMyUser = weldingSpotService.findByMyUserAndModelName(usefulUtils.getUserFromRequest(request), model.toLowerCase());

        Set<WeldingSpotsDto> weldingSpotsDtosSet = new HashSet<>();

        byMyUser.forEach(x -> weldingSpotsDtosSet.add(weldingSpotsDtoMapper.map(x)));

        return new ResponseEntity<>(weldingSpotsDtosSet, HttpStatus.OK);
    }

    @DeleteMapping("/welding-spots/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteWeldingSpot(@PathVariable(value = "id") String id) {
        weldingSpotService.deleteWeldingSpotById(Long.parseLong(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/welding-spots")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteAllUsersWeldingSpots(HttpServletRequest request) {
        MyUser user = usefulUtils.getUserFromRequest(request);
        weldingSpotService.deleteAllUserWeldingSpots(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
