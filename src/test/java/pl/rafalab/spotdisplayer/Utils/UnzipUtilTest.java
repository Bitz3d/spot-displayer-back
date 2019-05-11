package pl.rafalab.spotdisplayer.Utils;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnzipUtilTest extends BaseUtil {


    @Test
    void check_if_it_is_possible_to_unzip_file() throws IOException {
        compressFilePath = homeDirectory + fileSeparator + zippedFileName;
        File file = new File(compressFilePath);
        FileInputStream input = new FileInputStream(compressFilePath);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", input);

        String unZipFile = unzipFile.unZipFile(multipartFile, homeDirectory);

        assertEquals(expectedFileLocation, unZipFile);
    }

    @AfterEach
    void cleanUp() throws IOException {

        FileUtils.deleteDirectory(new File(compressFilePath.replace(".zip", "")));
        Files.delete(Paths.get(compressFilePath));

    }

}
