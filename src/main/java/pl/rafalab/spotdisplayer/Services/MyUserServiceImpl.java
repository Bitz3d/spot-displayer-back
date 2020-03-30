package pl.rafalab.spotdisplayer.Services;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.rafalab.spotdisplayer.Models.Dtos.MyUserDto;
import pl.rafalab.spotdisplayer.Models.MyRole;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Repository.MyUserRepository;
import pl.rafalab.spotdisplayer.Repository.RoleRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class MyUserServiceImpl implements UserDetailsService, MyUserService {

    private MyUserRepository myUserRepository;
    private PasswordEncoder encoder;
    private RoleRepository roleRepository;

    public MyUserServiceImpl(MyUserRepository myUserRepository, PasswordEncoder encoder, RoleRepository roleRepository) {
        this.myUserRepository = myUserRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = myUserRepository.findByUsername(username);

        if (myUser == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(myUser.getUsername(), myUser.getPassword(), getAuthority(myUser));
    }

    private Set<SimpleGrantedAuthority> getAuthority(MyUser user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
        });
        return authorities;
    }

    @Override
    public MyUser save(final MyUserDto user) {
        final MyUser newUser = new MyUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(encoder.encode(user.getPassword()));
        Set<MyRole> myRoles = new HashSet<>();
        myRoles.add(roleRepository.findByRole("USER"));
        newUser.setRoles(myRoles);
        return myUserRepository.save(newUser);
    }

    private Set<MyRole> getUserRoles() {
        Set<MyRole> myRoles = new HashSet<>();
        MyRole myRole = roleRepository.findByRole("USER");
        myRoles.add(myRole);
        return myRoles;
    }

    @Override
    public List<MyUser> findAll() {
        return myUserRepository.findAll();
    }

    @Override
    public void delete(long id) {
        myUserRepository.deleteById(id);
    }

    @Override
    public MyUser findOne(String username) {

        MyUser myUser = myUserRepository.findByUsername(username);

        if (myUser == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return myUserRepository.findByUsername(username);
    }

    @Override
    public Optional<MyUser> findById(Long id) {
        return myUserRepository.findById(id);
    }
}
