package pl.rafalab.spotdisplayer.Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;
import pl.rafalab.spotdisplayer.TesetUtils.TestUtils;
import pl.rafalab.spotdisplayer.Utils.Interfaces.WeldingSpotWorker;


class WeldingSpotWorkerImplTest{

    MyUser myUser;
    String robTarger ="CONST robtarget lsp38823_f56:=[[2376.12,-273.83,1009.32],[0.00156573,-0.230028,0.0361345,-0.972511],[0,-1,-1,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];";

    WeldingSpotWorker weldingSpotWorker;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        myUser = new MyUser();
        myUser.setId(1L);
        myUser .setUsername("user");
        myUser .setPassword("password");
        myUser .setRoles(TestUtils.getUserRoles());
        weldingSpotWorker = new WeldingSpotWorkerImpl();

    }


    @Test
    void test_should_return_list_of_weldingSpots_for_uer(){
        WeldingSpot weldingSpot = weldingSpotWorker.extractWeldingSpotsForUser(robTarger, myUser);
        System.out.println(weldingSpot);
        Assertions.assertEquals("lsp38823",weldingSpot.getSpotName());
        Assertions.assertEquals("f56",weldingSpot.getModelName());
        Assertions.assertEquals(2376.12,weldingSpot.getPointX());
        Assertions.assertEquals(-273.83,weldingSpot.getPointY());
        Assertions.assertEquals(1009.32,weldingSpot.getPointZ());
        Assertions.assertEquals(myUser,weldingSpot.getMyUser());


    }
}