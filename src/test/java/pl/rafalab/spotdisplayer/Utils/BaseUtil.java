package pl.rafalab.spotdisplayer.Utils;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import pl.rafalab.spotdisplayer.Utils.Interfaces.UnzipFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public abstract class BaseUtil {
    UnzipFile unzipFile;
    String textFileName;
    String zippedFileName = "compressed.zip";
    String homeDirectory;
    String fileSeparator;
    String compressFilePath;
    String expectedFileLocation = "/home/rafau/someFolder/compressed/rafal.mod";

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        unzipFile = new UnzipUtil();
        fileSeparator = System.getProperty("file.separator");
        String folderName = "someFolder";
        homeDirectory = System.getProperty("user.home") + fileSeparator + folderName;

        File file = new File(homeDirectory);
        if (!file.exists()) {
            file.mkdir();
        }

        textFileName = homeDirectory + fileSeparator + "rafal.mod";
        File textFile = new File(textFileName);
        textFile.createNewFile();
        zipFile();

    }

    private void zipFile() throws IOException {
        FileOutputStream fos = new FileOutputStream(homeDirectory + fileSeparator + zippedFileName);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(textFileName);
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.close();
        fis.close();
        fos.close();
    }

}
