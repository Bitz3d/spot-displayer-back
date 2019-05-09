package pl.rafalab.spotdisplayer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeldingSpotRepository extends JpaRepository<WeldingSpot, Long> {
    List<WeldingSpot> findByMyUser(MyUser username);
    List<WeldingSpot> findByMyUserAndModelName(MyUser myUser, String modelName);
    List<WeldingSpot> findBySpotName(String spotName);
    Optional<WeldingSpot> findById(Long id);
    void updateWeldingSpot(WeldingSpot weldingSpot);

}
