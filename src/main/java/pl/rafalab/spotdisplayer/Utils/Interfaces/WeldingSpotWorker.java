package pl.rafalab.spotdisplayer.Utils.Interfaces;

import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;

public interface WeldingSpotWorker {
    WeldingSpot extractWeldingSpotsForUser(String robTarget, MyUser username);
}
