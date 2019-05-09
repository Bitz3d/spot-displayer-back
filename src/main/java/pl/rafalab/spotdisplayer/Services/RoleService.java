package pl.rafalab.spotdisplayer.Services;

import org.springframework.stereotype.Service;
import pl.rafalab.spotdisplayer.model.MyRole;

@Service
public interface RoleService {
    MyRole findByRoleName(String roleName);
}
