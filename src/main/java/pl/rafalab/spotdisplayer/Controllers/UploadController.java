package pl.rafalab.spotdisplayer.Controllers;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.rafalab.spotdisplayer.Models.MyUser;
import pl.rafalab.spotdisplayer.Models.WeldingSpot;
import pl.rafalab.spotdisplayer.Services.MyUserService;
import pl.rafalab.spotdisplayer.Services.WeldingSpotService;
import pl.rafalab.spotdisplayer.Utils.Constants;
import pl.rafalab.spotdisplayer.Utils.Interfaces.FileCrawler;
import pl.rafalab.spotdisplayer.Utils.Interfaces.TextWorker;
import pl.rafalab.spotdisplayer.Utils.Interfaces.UnzipFile;
import pl.rafalab.spotdisplayer.Utils.Interfaces.WeldingSpotWorker;
import pl.rafalab.spotdisplayer.security.TokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class UploadController {

    Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Value("${target.upload.folder}")
    private String uploadFolder;

    private UnzipFile unzipFile;
    private FileCrawler fileCrawler;
    private TextWorker textWorker;
    private TokenProvider tokenProvider;
    private WeldingSpotWorker weldingSpotWorker;
    private WeldingSpotService weldingSpotService;
    private MyUserService myUserService;

    public UploadController(UnzipFile unzipFile, FileCrawler fileCrawler, TextWorker textWorker, TokenProvider tokenProvider, WeldingSpotWorker weldingSpotWorker, WeldingSpotService weldingSpotService, MyUserService myUserService) {
        this.unzipFile = unzipFile;
        this.fileCrawler = fileCrawler;
        this.textWorker = textWorker;
        this.tokenProvider = tokenProvider;
        this.weldingSpotWorker = weldingSpotWorker;
        this.weldingSpotService = weldingSpotService;
        this.myUserService = myUserService;
    }

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity submit(@RequestParam("files") List<MultipartFile> files, HttpServletRequest request) throws IOException {
        AtomicReference<HttpStatus> httpStatus = new AtomicReference<>();
        httpStatus.set(HttpStatus.OK);
        List<String> listOfUnzipedFiles = new ArrayList<>();

        if (files.size() <= 0 || files == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        File mailUploadFolder = makeUploadFolder();

        files.forEach(file -> {
            if ((!file.getOriginalFilename().contains(".zip")) && (!file.getOriginalFilename().contains(".rar"))) {
                httpStatus.set(HttpStatus.UNPROCESSABLE_ENTITY);
            } else {
                try {
                    listOfUnzipedFiles.add(unzipFile.unZipFile(file, mailUploadFolder.getAbsolutePath()));
                } catch (IOException e) {
                    httpStatus.set(HttpStatus.UNPROCESSABLE_ENTITY);
                    e.printStackTrace();
                }
            }
        });

        List<File> listOfFoundFiles = fileCrawler.searchFileWithExtension(mailUploadFolder.getAbsolutePath(), ".mod");

        List<List<String>> listOfWeldingSpotsList = new ArrayList<>();

        for (File file : listOfFoundFiles) {
            listOfWeldingSpotsList.add(textWorker.findWeldingSpots(file));
        }

        String token = request.getHeader(Constants.HEADER_STRING).replace(Constants.TOKEN_PREFIX, "");

        MyUser myUser = myUserService.findOne(tokenProvider.getUsernameFromToken(token));


        List<WeldingSpot> weldingSpotsList = new ArrayList<>();
        Set<String> allBySpotNameAndUserId = weldingSpotService.getAllBySpotNameAndUserId(myUser.getId());

        listOfWeldingSpotsList.forEach(x -> x.forEach(robTarget -> {
            WeldingSpot weldingSpot = weldingSpotWorker.extractAndSaveWeldingSpots(robTarget, myUser);
            if (allBySpotNameAndUserId.add(weldingSpot.getSpotName()))
                weldingSpotsList.add(weldingSpot);
            {

            }
        }));

        weldingSpotService.saveAllWeldingSpors(weldingSpotsList);
        removeUploadFolderContent(mailUploadFolder);
        return new ResponseEntity(httpStatus.get());
    }

    private void removeUploadFolderContent(File mailUploadFolder) {
        try {
            FileUtils.cleanDirectory(mailUploadFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File makeUploadFolder() {
        File uploadPlace = new File(System.getProperty("user.home"), uploadFolder);
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
