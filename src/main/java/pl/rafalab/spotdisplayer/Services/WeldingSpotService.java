package pl.rafalab.spotdisplayer.Services;

import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface WeldingSpotService {
    WeldingSpot save(WeldingSpot weldingSpot);

    void saveAllWeldingSpors(List<WeldingSpot> weldingSpots);

    Set<WeldingSpot> findByMyUser(MyUser username);

    Set<WeldingSpot> findBySpotName(String spotName);

    Set<WeldingSpot> findByMyUserAndModelName(MyUser myUser, String modelName);
    Optional<WeldingSpot> findById(Long id);

    Set<String> getAllBySpotNameAndUserId(long user_id);

}
