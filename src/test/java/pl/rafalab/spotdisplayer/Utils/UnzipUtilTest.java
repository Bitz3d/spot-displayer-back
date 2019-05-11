package pl.rafalab.spotdisplayer.Utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnzipUtilTest {

    private UnzipFile unzipFile;
    private String textFileName;
    private String zippedFileName = "compressed.zip";
    private String homeDirectory;
    private String fileSeparator;
    private String compressFilePath;
    private String expectedFileLocation ="/home/rafau/someFolder/compressed/rafal.txt";

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

        textFileName = homeDirectory + fileSeparator + "rafal.txt";
        File textFile = new File(textFileName);
        textFile.createNewFile();
        zipFile();

    }

    @Test
    void check_if_it_is_possible_to_unzip_file() throws IOException {
        compressFilePath = homeDirectory + fileSeparator + zippedFileName;
        File file = new File(compressFilePath);
        FileInputStream input = new FileInputStream(compressFilePath);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", input);

        String unZipFile = unzipFile.unZipFile(multipartFile,homeDirectory);

        assertEquals(expectedFileLocation ,unZipFile);


    }

    private void zipFile() throws IOException {
        FileOutputStream fos = new FileOutputStream(homeDirectory+fileSeparator+ zippedFileName);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(textFileName);
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.close();
        fis.close();
        fos.close();
    }

    @AfterEach
    void cleanUp(){
        File file =new File(compressFilePath);
        if(file.exists()){
            file.delete();
        }
    }

}
