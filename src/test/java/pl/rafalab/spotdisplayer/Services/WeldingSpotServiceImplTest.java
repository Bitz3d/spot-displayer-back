package pl.rafalab.spotdisplayer.Services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;
import pl.rafalab.spotdisplayer.Repository.WeldingSpotRepository;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class WeldingSpotServiceImplTest {

    @Mock
    WeldingSpotRepository weldingSpotRepository;
    @Mock
    MyUserService myUserService;

    WeldingSpotServiceImpl weldingSpotService;

    WeldingSpot weldingSpot;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        weldingSpotService = new WeldingSpotServiceImpl(weldingSpotRepository, myUserService);

        weldingSpot = WeldingSpot.builder()
                .id(1L)
                .spotName("spot12")
                .line("1.1")
                .modelName("F56")
                .myUser(new MyUser())
                .pointX(122.2)
                .pointY(432.2)
                .pointZ(133.2)
                .robName("ROB12")
                .build();


    }

    @Test
    void test_save_with_correct_data(){
        when(weldingSpotRepository.save(any())).thenReturn(weldingSpot);
        WeldingSpot returnedWeldingSpot = weldingSpotService.save(weldingSpot);
        Assertions.assertEquals(returnedWeldingSpot,weldingSpot);
    }

//    @Test
//    void test_find_by_user_with_correct_data(){
//        when(weldingSpotRepository.findByMyUser(any())).thenReturn(Collections.EMPTY_LIST);
//        WeldingSpot returnedWeldingSpot = w`eldingSpotService.save(weldingSpot);
//        Assertions.assertEquals(returnedWeldingSpot,weldingSpot);
//    }

}
