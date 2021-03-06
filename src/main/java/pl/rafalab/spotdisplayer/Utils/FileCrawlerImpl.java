package pl.rafalab.spotdisplayer.Utils;

import org.springframework.stereotype.Component;
import pl.rafalab.spotdisplayer.Utils.Interfaces.FileCrawler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FileCrawlerImpl implements FileCrawler {

    public List<File> searchFileWithExtension(String folderToSearchPath, String extension) throws IOException {
        List<File> result = null;

        try (Stream<Path> walk = Files.walk(Paths.get(folderToSearchPath))) {

            result = walk.map(x -> x.toString())
                    .filter(f -> f.endsWith(extension))
                    .map(File::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
