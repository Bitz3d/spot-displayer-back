package pl.rafalab.spotdisplayer.Utils.Interfaces;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileCrawler {
    List<File> searchFileWithExtension(String folderToSearchPath, String extension) throws IOException;
}
