package pl.rafalab.spotdisplayer.Services;

import org.springframework.stereotype.Service;
import pl.rafalab.spotdisplayer.model.Dto.MyUserDto;
import pl.rafalab.spotdisplayer.model.MyUser;

import java.util.List;
import java.util.Optional;

@Service
public interface MyUserService {

    MyUser save(MyUserDto user);
    List<MyUser> findAll();
    void delete(long id);
    MyUser findOne(String username);

    Optional<MyUser> findById(Long id);
}
