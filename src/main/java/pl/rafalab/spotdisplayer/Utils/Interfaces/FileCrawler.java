package pl.rafalab.spotdisplayer.Utils.Interfaces;

import java.io.File;
import java.util.List;

public interface FileCrawler {
    List<File> searchFileWithExtension(String folderToSearchPath, String extension);
}
