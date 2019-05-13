package pl.rafalab.spotdisplayer.Utils;

import org.springframework.stereotype.Component;
import pl.rafalab.spotdisplayer.Utils.Interfaces.TextWorker;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TextWorkerImpl implements TextWorker {


    @Override
    public List<String> findWeldingSpots(File modFile) {

        List<String> list = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(modFile.getAbsolutePath()))) {
            list = stream
                    .filter(line -> line.contains(Constants.SEARCHING_LINES))
                    .collect(Collectors.toList());
        } catch (UncheckedIOException d) {
            list = Collections.emptyList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
