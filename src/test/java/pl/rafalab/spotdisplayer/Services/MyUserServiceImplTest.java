package pl.rafalab.spotdisplayer.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.rafalab.spotdisplayer.Repository.MyUserRepository;
import pl.rafalab.spotdisplayer.Repository.RoleRepository;
import pl.rafalab.spotdisplayer.model.Dto.MyUserDto;
import pl.rafalab.spotdisplayer.model.MyRole;
import pl.rafalab.spotdisplayer.model.MyUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class MyUserServiceImplTest {


    private MyUserServiceImpl myUserService;

    @Mock
    private MyUserRepository myUserRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder encoder;

    private MyUser myUser;
    private List<MyUser> myUserList;
    private MyUserDto myUserDto;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        myUserService = new MyUserServiceImpl(myUserRepository, encoder,roleRepository);

        myUser = new MyUser();
        myUser.setId(1L);
        myUser.setPassword("password");
        myUser.setUsername("Pawel");
        myUser.setRoles(getUserRoles());

        myUserDto = new MyUserDto();
        myUserDto.setPassword("password");
        myUserDto.setUsername("Pawel");
        myUserDto.setRoles(getUserRoles());

        myUserList = getMyUserList();

    }

    private Set<SimpleGrantedAuthority> getAuthority(MyUser user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole())));
        return authorities;
    }

    private List<MyUser> getMyUserList(){
        List<MyUser> myUsersList =  new ArrayList<>();
        myUsersList.add(myUser);
        myUsersList.add(myUser);
        return myUsersList;
    }

    private Set<MyRole> getUserRoles() {
        Set<MyRole> myRoles = new HashSet<>();
        myRoles.add(new MyRole(1, "ROLE_ADMIN"));
        myRoles.add(new MyRole(2, "ROLE_USER"));
        return myRoles;
    }

    @Test
    final void test_load_user_by_username_exist_in_database() {
        when(myUserRepository.findByUsername(anyString())).thenReturn(myUser);
        UserDetails userDetails = myUserService.loadUserByUsername("Pawel");
        assertEquals(userDetails.getUsername(), myUser.getUsername());
        assertEquals(userDetails.getPassword(), myUser.getPassword());
        assertEquals(userDetails.getAuthorities().size(), myUser.getRoles().size());
    }

    @Test
    final void test_load_user_by_username_not_name_in_database() {
        when(myUserRepository.findByUsername(anyString())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> myUserService.findOne("NoName"));
    }


    @Test
    final void test_find_one_with_correct_exist_in_database() {
        when(myUserRepository.findByUsername(anyString())).thenReturn(myUser);
        MyUser myUser = myUserService.findOne("Pawel");
        assertNotNull(myUser);
        assertEquals("Pawel", myUser.getUsername());
    }

    @Test
    final void test_find_one_with_not_name_in_database() {
        when(myUserRepository.findByUsername(anyString())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> myUserService.findOne("NoName"));
    }

    @Test
    final void test_save_user_into_database() {
        when(myUserRepository.save(any())).thenReturn(myUser);

        MyUser returnedMyUser = myUserService.save(myUserDto);

        assertEquals(myUser.getUsername(),myUserDto.getUsername());
        assertEquals(myUser.getPassword(), returnedMyUser.getPassword());
        assertEquals(myUser.getId(), returnedMyUser.getId());
        assertEquals(myUser.getRoles().size(), returnedMyUser.getRoles().size());

    }

    @Test
    final void test_findAll_user_in_database() {
        when(myUserRepository.findAll()).thenReturn(myUserList);
        List<MyUser> allUsers = myUserService.findAll();
        assertEquals(allUsers.size(),myUserList.size());
    }
}
