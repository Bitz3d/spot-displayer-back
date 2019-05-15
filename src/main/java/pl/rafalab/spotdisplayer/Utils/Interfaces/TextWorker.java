package pl.rafalab.spotdisplayer.Utils.Interfaces;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface TextWorker {
    List<String> findWeldingSpots(File modFile) throws IOException;
}
