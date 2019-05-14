package pl.rafalab.spotdisplayer.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;
import pl.rafalab.spotdisplayer.Services.MyUserService;
import pl.rafalab.spotdisplayer.Services.WeldingSpotService;
import pl.rafalab.spotdisplayer.Utils.Interfaces.WeldingSpotWorker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class WeldingSpotWorkerImpl implements WeldingSpotWorker {

    Logger logger = LoggerFactory.getLogger(WeldingSpotWorkerImpl.class);

    WeldingSpotService weldingSpotService;
    MyUserService myUserService;

    public WeldingSpotWorkerImpl(WeldingSpotService weldingSpotService, MyUserService myUserService) {
        this.weldingSpotService = weldingSpotService;
        this.myUserService = myUserService;
    }

    public void extractAndSaveWeldingSpots(String robTarget, String userName) {
        MyUser myUser = myUserService.findOne(userName);
        WeldingSpot.builder().myUser(myUser);
        WeldingSpot weldingSpotObject = createWeldingSpotObject(robTarget);
        weldingSpotService.save(weldingSpotObject);

    }


    public WeldingSpot createWeldingSpotObject(String robTarget) {
        if (robTarget == null || robTarget.equals("")) {
            logger.error(robTarget + " is not correct");
            throw new NullPointerException();
        }
        Pattern p = Pattern.compile(Constants.WELDING_SPOT_DATA_EXTRACTION_PATTERN);
        Matcher matcher = p.matcher(robTarget);
        while (matcher.find()) {
            WeldingSpot.builder()
                    .spotName(matcher.group(1))
                    .modelName(matcher.group(2))
                    .pointX(Double.parseDouble(matcher.group(4)))
                    .pointY(Double.parseDouble(matcher.group(5)))
                    .pointZ(Double.parseDouble(matcher.group(6)));


        }
        return WeldingSpot.builder().build();
    }

}
