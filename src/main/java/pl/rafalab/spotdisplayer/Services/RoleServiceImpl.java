package pl.rafalab.spotdisplayer.Services;

import org.springframework.stereotype.Service;
import pl.rafalab.spotdisplayer.Repository.RoleRepository;
import pl.rafalab.spotdisplayer.Models.MyRole;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public MyRole findByRoleName(String roleName) {
        return roleRepository.findByRole(roleName);
    }
}
