package pl.rafalab.spotdisplayer.Services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;
import pl.rafalab.spotdisplayer.Repository.WeldingSpotRepository;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class WeldingSpotServiceImplTest {

    @Mock
    WeldingSpotRepository weldingSpotRepository;
    @Mock
    MyUserService myUserService;

    WeldingSpotServiceImpl weldingSpotService;

    WeldingSpot weldingSpot;

    Set<WeldingSpot> weldingSpotList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        weldingSpotService = new WeldingSpotServiceImpl(weldingSpotRepository, myUserService);

        weldingSpot = WeldingSpot.builder()
                .id(1L)
                .spotName("spot12")
                .modelName("F56")
                .myUser(new MyUser())
                .pointX(122.2)
                .pointY(432.2)
                .pointZ(133.2)
                .build();

        weldingSpotList = new HashSet<>();
        weldingSpotList.add(weldingSpot);
    }

    @Test
    void test_save_with_correct_data() {
        when(weldingSpotRepository.save(any())).thenReturn(weldingSpot);
        WeldingSpot returnedWeldingSpot = weldingSpotService.save(weldingSpot);
        Assertions.assertEquals(returnedWeldingSpot, weldingSpot);
    }

    @Test
    void test_find_by_user_with_correct_data() {
        when(weldingSpotRepository.findByMyUser(any(MyUser.class))).thenReturn((weldingSpotList));
        Set<WeldingSpot> weldingSpotByUsername = weldingSpotService.findByMyUser(new MyUser());
        Assertions.assertEquals(weldingSpotList.size(), weldingSpotByUsername.size());
    }

}
