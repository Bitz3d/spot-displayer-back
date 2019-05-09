package pl.rafalab.spotdisplayer.Services;

import org.springframework.stereotype.Service;
import pl.rafalab.spotdisplayer.Models.MyRole;

@Service
public interface RoleService {
    MyRole findByRoleName(String roleName);
}
