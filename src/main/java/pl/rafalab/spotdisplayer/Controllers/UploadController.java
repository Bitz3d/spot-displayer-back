package pl.rafalab.spotdisplayer.Controllers;

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

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class UploadController {

    Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Value("${target.upload.folder}")
    private String uploadFolder;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity submit(@RequestParam("files") List<MultipartFile> files) {
        AtomicReference<HttpStatus> httpStatus = new AtomicReference<>();
        httpStatus.set(HttpStatus.OK);

        if (files.size() <= 0) {
            httpStatus.set(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        makeUploadFolder();

        files.forEach(file -> {
            if ((!file.getOriginalFilename().contains(".zip")) && (!file.getOriginalFilename().contains(".rar"))) {
                httpStatus.set(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        });

        return new ResponseEntity(httpStatus.get());
    }

    private void makeUploadFolder() {
        File uploadPlace = new File(System.getProperty("user.home"), uploadFolder);
        if (!uploadPlace.exists()) {
            if (uploadPlace.mkdir()) {
                logger.info("Folder " + uploadFolder + " has been created");
            } else {
                logger.error("Folder hasn't been created");
            }
        }
    }

}