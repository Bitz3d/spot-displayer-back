package pl.rafalab.spotdisplayer.Services;

import org.springframework.stereotype.Service;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;
import pl.rafalab.spotdisplayer.Repository.WeldingSpotRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WeldingSpotServiceImpl implements WeldingSpotService {

    private WeldingSpotRepository weldingSpotRepository;
    private MyUserService myUserService;

    public WeldingSpotServiceImpl(WeldingSpotRepository weldingSpotRepository, MyUserService myUserService) {
        this.weldingSpotRepository = weldingSpotRepository;
        this.myUserService = myUserService;
    }

    @Override
    public WeldingSpot save(WeldingSpot weldingSpot) {
        return weldingSpotRepository.save(weldingSpot);
    }

    @Override
    public List<WeldingSpot> findByMyUser(MyUser username) {
        return weldingSpotRepository.findByMyUser(username);
    }

    @Override
    public List<WeldingSpot> findBySpotName(String spotName) {
        return weldingSpotRepository.findBySpotName(spotName);
    }

    @Override
    public List<WeldingSpot> findByMyUserAndModelName(MyUser myUser, String modelName) {
        return weldingSpotRepository.findByMyUserAndModelName(myUser,modelName);
    }

    @Override
    public Optional<WeldingSpot> findById(Long id) {
        return weldingSpotRepository.findById(id);
    }

}
