package pl.rafalab.spotdisplayer.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;
import pl.rafalab.spotdisplayer.Utils.Interfaces.WeldingSpotWorker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class WeldingSpotWorkerImpl implements WeldingSpotWorker {

    private Logger logger = LoggerFactory.getLogger(WeldingSpotWorkerImpl.class);


    public WeldingSpot extractWeldingSpotsForUser(String robTarget, MyUser myUser) {
        logger.info("Getting user " + myUser.getUsername() + " from Database");
        logger.info("Start retriving data from " + robTarget);
        WeldingSpot weldingSpotObject = createWeldingSpotObject(robTarget);
        weldingSpotObject.setMyUser(myUser);
        logger.info("Build " + weldingSpotObject + " and save it to database");
        return weldingSpotObject;
    }

    private WeldingSpot createWeldingSpotObject(String robTarget) {
        if (robTarget == null || robTarget.equals("")) {
            logger.error(robTarget + " is not correct");
            throw new NullPointerException();
        }
        Pattern p = Pattern.compile(Constants.WELDING_SPOT_DATA_EXTRACTION_PATTERN);
        Matcher matcher = p.matcher(robTarget.toLowerCase());
        WeldingSpot weldingSpot = null;
        while (matcher.find()) {
            weldingSpot = WeldingSpot.builder()
                    .spotName(matcher.group(1))
                    .modelName(matcher.group(2))
                    .pointX(Double.parseDouble(matcher.group(5)))
                    .pointY(Double.parseDouble(matcher.group(7)))
                    .pointZ(Double.parseDouble(matcher.group(9))).build();

        }
        return weldingSpot;
    }

}
