package pl.rafalab.spotdisplayer.Controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import pl.rafalab.spotdisplayer.Models.LoginUser;
import pl.rafalab.spotdisplayer.security.TokenProvider;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TokenControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private AuthenticationManager authenticationManager;

    private TokenController tokenController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        tokenController = new TokenController(tokenProvider, authenticationManager);

        mockMvc = MockMvcBuilders
                .standaloneSetup(tokenController)
                .build();

    }

    @Test
    void test_register_should_return_token() throws Exception {
        LoginUser loginUser = new LoginUser();

        ObjectMapper objectMapper = new ObjectMapper();
        loginUser.setUsername("user");
        loginUser.setPassword("password");

        String jsonNode = objectMapper.writeValueAsString(loginUser);

        mockMvc.perform(post("/token/generate-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonNode))
                .andExpect(status().is(200)).andReturn();
    }

    @Test
    void test_with_null_user_should_return_400() throws Exception {
        LoginUser loginUser = new LoginUser();

        ObjectMapper objectMapper = new ObjectMapper();
        loginUser.setUsername(null);
        loginUser.setPassword("password");

        String jsonNode = objectMapper.writeValueAsString(loginUser);

        mockMvc.perform(post("/token/generate-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonNode))
                .andExpect(status().is(400)).andReturn();
    }

    @Test
    void test_with_null_password_should_return_400() throws Exception {
        LoginUser loginUser = new LoginUser();

        ObjectMapper objectMapper = new ObjectMapper();
        loginUser.setUsername("user");
        loginUser.setPassword(null);

        String jsonNode = objectMapper.writeValueAsString(loginUser);

        mockMvc.perform(post("/token/generate-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonNode))
                .andExpect(status().is(400)).andReturn();
    }
}
