package pl.rafalab.spotdisplayer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rafalab.spotdisplayer.model.MyRole;

@Repository
public interface RoleRepository extends JpaRepository<MyRole, Integer> {

    MyRole findByRole(String roleName);
}
