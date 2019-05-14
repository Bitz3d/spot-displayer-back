package pl.rafalab.spotdisplayer.Services;

import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;

import java.util.List;
import java.util.Optional;

public interface WeldingSpotService {
    WeldingSpot save(WeldingSpot weldingSpot);
    List<WeldingSpot> findByMyUser(MyUser username);
    List<WeldingSpot> findBySpotName(String spotName);
    List<WeldingSpot> findByMyUserAndModelName(MyUser myUser, String modelName);
    Optional<WeldingSpot> findById(Long id);
}
