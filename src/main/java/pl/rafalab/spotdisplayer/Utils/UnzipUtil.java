package pl.rafalab.spotdisplayer.Utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.rafalab.spotdisplayer.Utils.Interfaces.UnzipFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class UnzipUtil implements UnzipFile {

    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;

    /**
     * Main unziped files folder
     */
    private String mainUnzipedFileFolder;


    public String getMainUnzipedFileFolder() {
        return mainUnzipedFileFolder;
    }

    public String unZipFile(MultipartFile files, String mainUnzipedFileFolder) throws IOException {

        this.mainUnzipedFileFolder = mainUnzipedFileFolder;

        final String destDirectory = mainUnzipedFileFolder + System.getProperty("file.separator") + files.getOriginalFilename().replace(".zip", "");

        String filePath = null;

        File destDir = new File(destDirectory);


//        Trick to move file to server then unzip it.
        File rarFilePath = new File(System.getProperty("user.temp"), files.getOriginalFilename());
        files.transferTo(rarFilePath);


//        Create new file if not exist
        if (!destDir.exists()) {
            destDir.mkdir();

        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(rarFilePath.getAbsoluteFile()));
        ZipEntry entry = zipIn.getNextEntry();

        while (entry != null) {
            filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);

            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
        Files.deleteIfExists(Paths.get(rarFilePath.getAbsolutePath()));
        return destDirectory;
    }

    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

}
