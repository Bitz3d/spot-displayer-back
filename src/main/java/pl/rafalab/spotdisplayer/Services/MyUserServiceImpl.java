package pl.rafalab.spotdisplayer.Services;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.rafalab.spotdisplayer.Repository.MyUserRepository;
import pl.rafalab.spotdisplayer.model.Dto.MyUserDto;
import pl.rafalab.spotdisplayer.model.MyUser;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class MyUserServiceImpl implements UserDetailsService, MyUserService {

    private MyUserRepository myUserRepository;
    private PasswordEncoder encoder;

    public MyUserServiceImpl(MyUserRepository myUserRepository, PasswordEncoder encoder) {
        this.myUserRepository = myUserRepository;
        this.encoder = encoder;
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
            //authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
        });
        return authorities;
        //return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public MyUser save(MyUserDto user) {
        MyUser newUser = new MyUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(encoder.encode(user.getPassword()));
        return myUserRepository.save(newUser);
    }

    @Override
    public List<MyUser> findAll() {
        List<MyUser> myUsers = new ArrayList<>();
        myUserRepository.findAll().iterator().forEachRemaining(myUsers::add);
        return myUsers;
    }

    @Override
    public void delete(long id) {
        myUserRepository.deleteById(id);
    }

    @Override
    public MyUser findOne(String username) {
        return myUserRepository.findByUsername(username);
    }

    @Override
    public Optional<MyUser> findById(Long id) {
        return myUserRepository.findById(id);
    }
}
