package pl.rafalab.spotdisplayer.Controllers;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;
import pl.rafalab.spotdisplayer.Services.WeldingSpotService;
import pl.rafalab.spotdisplayer.Utils.Constants;
import pl.rafalab.spotdisplayer.Utils.Interfaces.FileCrawler;
import pl.rafalab.spotdisplayer.Utils.Interfaces.TextWorker;
import pl.rafalab.spotdisplayer.Utils.Interfaces.UnzipFile;
import pl.rafalab.spotdisplayer.Utils.Interfaces.WeldingSpotWorker;
import pl.rafalab.spotdisplayer.Utils.UsefulUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UploadController {

    Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Value("${target.upload.folder}")
    private String uploadFolder;

    private final UnzipFile unzipFile;
    private final FileCrawler fileCrawler;
    private final TextWorker textWorker;
    private final WeldingSpotWorker weldingSpotWorker;
    private final WeldingSpotService weldingSpotService;
    private final UsefulUtils usefulUtils;

    public UploadController(UnzipFile unzipFile, FileCrawler fileCrawler, TextWorker textWorker, WeldingSpotWorker weldingSpotWorker, WeldingSpotService weldingSpotService, UsefulUtils usefulUtils) {
        this.unzipFile = unzipFile;
        this.fileCrawler = fileCrawler;
        this.textWorker = textWorker;
        this.weldingSpotWorker = weldingSpotWorker;
        this.weldingSpotService = weldingSpotService;
        this.usefulUtils = usefulUtils;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") List<MultipartFile> files, HttpServletRequest request) throws IOException {
        AtomicReference<HttpStatus> httpStatus = new AtomicReference<>();
        AtomicReference<String> responseMessageCode = new AtomicReference<>("");
        httpStatus.set(HttpStatus.OK);
        List<String> listOfUnzipedFiles = new ArrayList<>();

        if (files == null || files.size() <= 0) {
            return new ResponseEntity<>("noFile", HttpStatus.BAD_REQUEST);
        }

        File makeUploadFolder = makeUploadFolder();

        files.forEach(file -> {
            if ((!file.getOriginalFilename().contains(".zip")) && (!file.getOriginalFilename().contains(".rar"))) {
                httpStatus.set(HttpStatus.UNPROCESSABLE_ENTITY);
                responseMessageCode.set("wrongFileFormat");
            } else {
                try {
                    listOfUnzipedFiles.add(unzipFile.unZipFile(file, makeUploadFolder.getAbsolutePath()));
                } catch (IOException e) {
                    httpStatus.set(HttpStatus.UNPROCESSABLE_ENTITY);
                    responseMessageCode.set("unknownError");
                    e.printStackTrace();
                }
            }
        });

        List<File> listOfFoundFiles = fileCrawler.searchFileWithExtension(makeUploadFolder.getAbsolutePath(), ".mod");

        if (listOfFoundFiles.size() > 0) {
            List<WeldingSpot> weldingSpotsList = getWeldingSpotList(request, listOfFoundFiles);

            if(weldingSpotsList.isEmpty()){
                responseMessageCode.set("noWeldingSpotsFound");
            }

            weldingSpotService.saveAllWeldingSpots(weldingSpotsList);
            removeUploadFolderContent(makeUploadFolder);

            if (httpStatus.get() == HttpStatus.OK) {
                responseMessageCode.set("weldingUploadOk");
            }
        }else {
            responseMessageCode.set("noModFilesFound");
        }

        return new ResponseEntity<>(responseMessageCode.get(), httpStatus.get());
    }

    private List<WeldingSpot> getWeldingSpotList(HttpServletRequest request, List<File> listOfFoundFiles) throws IOException {
        List<List<String>> listOfWeldingSpotsList = new ArrayList<>();

        for (File file : listOfFoundFiles) {
            listOfWeldingSpotsList.add(textWorker.findWeldingSpots(file));
        }

        MyUser myUser = usefulUtils.getUserFromRequest(request);

        List<WeldingSpot> weldingSpotsList = new ArrayList<>();
        Set<String> allBySpotNameAndUserId = weldingSpotService.getAllBySpotNameAndUserId(myUser.getId());

        listOfWeldingSpotsList.forEach(x -> x.forEach(robTarget -> {
            WeldingSpot weldingSpot = weldingSpotWorker.extractWeldingSpotsForUser(robTarget, myUser);
            if (allBySpotNameAndUserId.add(weldingSpot.getSpotName()))
                weldingSpotsList.add(weldingSpot);
        }));
        return weldingSpotsList;
    }

    private void removeUploadFolderContent(File mailUploadFolder) {
        try {
            FileUtils.cleanDirectory(mailUploadFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File makeUploadFolder() {
        File uploadPlace = new File(System.getProperty(Constants.TEMP_DIR), uploadFolder);
        if (!uploadPlace.exists()) {
            if (uploadPlace.mkdir()) {
                logger.info("Folder " + uploadFolder + " has been created");
            } else {
                logger.error("Folder hasn't been created");
            }
        }
        return uploadPlace;
    }

}
