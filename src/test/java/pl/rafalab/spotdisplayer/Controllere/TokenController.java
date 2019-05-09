package pl.rafalab.spotdisplayer.Controllere;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import pl.rafalab.spotdisplayer.security.TokenProvider;

import java.io.IOException;

public class TokenController {

    private MockMvc mockMvc;

    @Mock
    private TokenProvider tokenProvider;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private TokenController tokenController;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(tokenController)
                .build();

    }

    @Test
    public void test_register_should_return_token(){

    }

}
