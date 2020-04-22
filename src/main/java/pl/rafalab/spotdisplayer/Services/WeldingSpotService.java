package pl.rafalab.spotdisplayer.Services;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface WeldingSpotService {
    WeldingSpot save(WeldingSpot weldingSpot);

    void saveAllWeldingSpots(List<WeldingSpot> weldingSpots);

    Set<String> findDistinctModelNameByUser(MyUser myUser);

    Set<WeldingSpot> findByMyUser(MyUser username);

    Set<WeldingSpot> findBySpotName(String spotName);

    Set<WeldingSpot> findByMyUserAndModelName(MyUser myUser, String modelName);

    Optional<WeldingSpot> findById(Long id);

    Set<String> getAllBySpotNameAndUserId(long user_id);

    void deleteWeldingSpotById(Long id);

    void deleteAllUserWeldingSpots(MyUser myUser);
}
