package pl.rafalab.spotdisplayer.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.rafalab.spotdisplayer.Commons.Mapper;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;
import pl.rafalab.spotdisplayer.Services.WeldingSpotService;
import pl.rafalab.spotdisplayer.Utils.UsefulUtils;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WeldingSpotControllerTest {

    WeldingSpot weldingSpot;
    Set<WeldingSpot> weldingSpotList;
    MyUser myUser;
    String json;
    private MockMvc mockMvc;
    private WeldingSpotController weldingSpotController;
    @Mock
    private WeldingSpotService weldingSpotService;
    @Mock
    private UsefulUtils usefulUtils;
    @Mock
    private Mapper mapper;

    @BeforeEach
    void setUp() throws JsonProcessingException {

        MockitoAnnotations.initMocks(this);
        weldingSpotController = new WeldingSpotController(weldingSpotService, usefulUtils, mapper);
        mockMvc = MockMvcBuilders
                .standaloneSetup(weldingSpotController)
                .build();


        myUser = new MyUser(1L, "rafal", "password", null);


        weldingSpot = WeldingSpot.builder()
                .id(1L)
                .spotName("spot12")
                .modelName("F56")
                .myUser(myUser)
                .pointX(122.2)
                .pointY(432.2)
                .pointZ(133.2)
                .build();

        weldingSpotList = new HashSet<>();
        weldingSpotList.add(weldingSpot);

        ObjectMapper objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(weldingSpotList);
    }

    @Test
    void get_all_user_welding_spots() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();

        when(usefulUtils.getUserFromRequest(request)).thenReturn(myUser);
        when(weldingSpotService.findByMyUser(myUser)).thenReturn(weldingSpotList);

        this.mockMvc.perform(get("/welding-spots"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getAllUserWeldingSpotsForModel() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        weldingSpotController.getAllUserWeldingSpots(request);

        when(usefulUtils.getUserFromRequest(request)).thenReturn(myUser);
        when(weldingSpotService.findByMyUser(myUser)).thenReturn(weldingSpotList);

        this.mockMvc.perform(get("/welding-spots/f56"))
                .andExpect(status().isOk())
                .andReturn();
    }
}