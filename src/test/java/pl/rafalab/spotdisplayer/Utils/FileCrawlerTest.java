package pl.rafalab.spotdisplayer.Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class FileCrawlerTest extends BaseUtil {


    @Test
    void search_file_with_correct_extension() {
        List<String> list = fileCrawler.searchFileWithExtension(homeDirectory, ".mod");

        Assertions.assertEquals(1,list.size());
    }

    @Test
    void search_file_with_incorrect_extension() {
        List<String> list = fileCrawler.searchFileWithExtension(homeDirectory, ".opw");

        Assertions.assertEquals(0,list.size());
    }
}