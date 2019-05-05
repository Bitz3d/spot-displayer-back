package pl.rafalab.spotdisplayer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rafalab.spotdisplayer.model.MyUser;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser,Long> {

    MyUser findByUsername(String username);
}
