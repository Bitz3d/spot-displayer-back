package pl.rafalab.spotdisplayer.Utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextWorkerImplTest {

    @InjectMocks
    TextWorkerImpl textWorkerImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void should_return_list_of_welding_spots() {
        List<String> weldingSpots = textWorkerImpl.findWeldingSpots(new File("/home/rafau/Desktop/1.0/F15M04_10R05_BACKUP_2015-09-27/RAPID/TASK1/PROGMOD/F15M04_10R05.mod"));
        weldingSpots.forEach(System.out::println);
        assertEquals(weldingSpots.size(), 27);
    }

    @Test
    void should_return_empty_list() {
        List<String> weldingSpots = textWorkerImpl.findWeldingSpots(new File("/home/rafau/Desktop/1.0/F15M04_10R05_BACKUP_2015-09-27/RAPID/TASK1/PROGMOD/ComUser.mod"));
        weldingSpots.forEach(System.out::println);
        assertEquals(weldingSpots.size(), 0);
    }

    @Test
    void should_return_null_pointer_exception() {
        assertThrows(NullPointerException.class, () -> textWorkerImpl.findWeldingSpots(null));
    }
}