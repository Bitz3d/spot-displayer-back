package pl.rafalab.spotdisplayer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Repository
public interface WeldingSpotRepository extends JpaRepository<WeldingSpot, Long> {
    Set<WeldingSpot> findByMyUser(MyUser username);

    Set<WeldingSpot> findByMyUserAndModelName(MyUser myUser, String modelName);

    Set<WeldingSpot> findBySpotName(String spotName);

    Optional<WeldingSpot> findById(Long id);

    @Query(value = "select spot_name from welding_spot where my_user_id =:id", nativeQuery = true)
    Set<String> getAllBySpotNameAndUserId(@Param("id") long id);

    @Transactional
    @Query(value = "delete from welding_spot where my_user_id=:id", nativeQuery = true)
    void deleteAllByMyUser(@Param("id") long id);

    @Query(value = "SELECT DISTINCT ws.model_name FROM welding_spot ws WHERE my_user_id=:id", nativeQuery = true)
    Set<String> findDistinctModelNameByMyUser(@Param("id") long userId);

}
