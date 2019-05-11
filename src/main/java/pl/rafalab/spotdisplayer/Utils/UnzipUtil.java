package pl.rafalab.spotdisplayer.Utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipUtil {

    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;

    /**
     * Main unziped files folder
     */
    private String mainUnzipedFileFolder=null;


    public String getMainUnzipedFileFolder() {
        return mainUnzipedFileFolder;
    }

    public String unZipFile(MultipartFile files) throws IOException {


        //        Make main directory on user dekstop
        File desktop = new File(System.getProperty("user.home"), "Desktop/zipfiles");

        mainUnzipedFileFolder = desktop.getPath();
        if (!desktop.exists()) {
            desktop.mkdir();

        }

        final String destDirectory = desktop.getPath() + "/" + files.getOriginalFilename().replace(".zip", "");
        String filePath = null;
        File destDir = new File(destDirectory);


//        Trick to move file to server then unzip it.
        File rarFilePath = new File(files.getOriginalFilename());
        files.transferTo(rarFilePath);


//        Create new file if not exist
        if (!destDir.exists()) {
            destDir.mkdir();

        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(rarFilePath.getAbsoluteFile()));
        ZipEntry entry = zipIn.getNextEntry();

        while (entry != null) {
            filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);

            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
        return filePath;
    }

    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

}
