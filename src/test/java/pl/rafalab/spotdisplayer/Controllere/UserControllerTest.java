package pl.rafalab.spotdisplayer.Controllere;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.rafalab.spotdisplayer.Controllers.UserController;
import pl.rafalab.spotdisplayer.model.Dto.MyUserDto;
import pl.rafalab.spotdisplayer.model.MyRole;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;
    private String jsonNode;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        MyUserDto myUserDto = new MyUserDto();
        myUserDto.setPassword("password");
        myUserDto.setUsername("Pawel");
        myUserDto.setRoles(getUserRoles());
        jsonNode = objectMapper.writeValueAsString(myUserDto);
        System.out.println(jsonNode);
    }

    private Set<MyRole> getUserRoles() {
        Set<MyRole> myRoles = new HashSet<>();
        myRoles.add(new MyRole(1, "ROLE_ADMIN"));
        myRoles.add(new MyRole(2, "ROLE_USER"));
        return myRoles;
    }

    @Test
    public void test_save_user_with_correct_data() throws Exception {
        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonNode))
                .andExpect(status().isOk()).andReturn();
    }


    @Test
    public void test_save_user_with_empty_username() throws Exception
    {
        ObjectMapper objectMapper = new ObjectMapper();

        MyUserDto myUserDto = new MyUserDto();
        myUserDto.setPassword("password");
        myUserDto.setUsername("");
        myUserDto.setRoles(getUserRoles());
        jsonNode = objectMapper.writeValueAsString(myUserDto);


        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonNode))
                .andExpect(status().is(400)).andReturn();
    }

    @Test
    public void test_save_user_with_empty_password() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        MyUserDto myUserDto = new MyUserDto();
        myUserDto.setPassword("");
        myUserDto.setUsername(null);
        myUserDto.setRoles(getUserRoles());
        jsonNode = objectMapper.writeValueAsString(myUserDto);


        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonNode))
                .andExpect(status().is(400)).andReturn();
    }


}
