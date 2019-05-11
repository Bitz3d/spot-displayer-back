package pl.rafalab.spotdisplayer.Utils.Interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UnzipFile {
    String unZipFile(MultipartFile files, String mainUnzipedFileFolder) throws IOException;
}
