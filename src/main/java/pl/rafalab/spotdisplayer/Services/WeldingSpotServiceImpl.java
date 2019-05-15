package pl.rafalab.spotdisplayer.Services;

import org.springframework.stereotype.Service;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;
import pl.rafalab.spotdisplayer.Repository.WeldingSpotRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class WeldingSpotServiceImpl implements WeldingSpotService {

    private WeldingSpotRepository weldingSpotRepository;

    public WeldingSpotServiceImpl(WeldingSpotRepository weldingSpotRepository, MyUserService myUserService) {
        this.weldingSpotRepository = weldingSpotRepository;
    }

    @Override
    public WeldingSpot save(WeldingSpot weldingSpot) {
        return weldingSpotRepository.save(weldingSpot);
    }

    @Override
    public void saveAllWeldingSpors(List<WeldingSpot> weldingSpots) {
        weldingSpotRepository.saveAll(weldingSpots);
    }

    @Override
    public Set<WeldingSpot> findByMyUser(MyUser username) {
        return weldingSpotRepository.findByMyUser(username);
    }

    @Override
    public Set<WeldingSpot> findBySpotName(String spotName) {
        return weldingSpotRepository.findBySpotName(spotName);
    }

    @Override
    public Set<WeldingSpot> findByMyUserAndModelName(MyUser myUser, String modelName) {
        return weldingSpotRepository.findByMyUserAndModelName(myUser, modelName);
    }

    @Override
    public Optional<WeldingSpot> findById(Long id) {
        return weldingSpotRepository.findById(id);
    }

    @Override
    public Set<String> getAllBySpotNameAndUserId(long user_id) {
        return weldingSpotRepository.getAllBySpotNameAndUserId(user_id);
    }

}
