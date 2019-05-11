package pl.rafalab.spotdisplayer.Utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UnzipFile {
    public String unZipFile(MultipartFile files,String mainUnzipedFileFolder) throws IOException;
}
