package pl.rafalab.spotdisplayer.Utils.Interfaces;

import java.util.List;

public interface FileCrawler {
    List<String> searchFileWithExtension(String folderToSearchPath, String extension);
}
