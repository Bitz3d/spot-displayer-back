package pl.rafalab.spotdisplayer.Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

class FileCrawlerImplTest extends BaseUtil {


    @Test
    void search_file_with_correct_extension() throws IOException {
        List<File> list = fileCrawlerImpl.searchFileWithExtension(homeDirectory, ".mod");

        Assertions.assertEquals(1, list.size());
    }

    @Test
    void search_file_with_incorrect_extension() throws IOException {
        List<File> list = fileCrawlerImpl.searchFileWithExtension(homeDirectory, ".opw");

        Assertions.assertEquals(0, list.size());
    }
}