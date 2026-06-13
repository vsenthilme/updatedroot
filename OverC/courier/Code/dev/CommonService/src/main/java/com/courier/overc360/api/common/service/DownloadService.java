package com.courier.overc360.api.common.service;

import com.courier.overc360.api.common.config.PropertiesConfig;
import com.courier.overc360.api.common.controller.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class DownloadService {

    private static final Logger log = LoggerFactory.getLogger(DownloadService.class);
    @Autowired
    PropertiesConfig propertiesConfig;

    private Path fileStorageLocation = null;

    public void downloadPdf(String destinationDir, String documentName) throws IOException {

        this.fileStorageLocation = Paths.get(propertiesConfig.getDocStorageBasePath() + destinationDir).toAbsolutePath().normalize();
        if (!Files.exists(fileStorageLocation)) {
            try {
                Files.createDirectories(this.fileStorageLocation);
            } catch (Exception ex) {
                throw new BadRequestException(
                        "Could not create the directory where the uploaded files will be stored.");
            }
        }

        try {
            String docUrl = "https://dev-b2b.iwexpress.com/shippingLabel/AJX000017019.pdf";
            String fileName = null;
            if (documentName.contains(".pdf")) {
                fileName = documentName;
            } else {
                fileName = documentName + ".pdf";
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            InputStream in = new URL(docUrl).openStream();
            Files.copy(in, targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new BadRequestException("Document Download Failed: " + e);
        }

    }

    /**
     *
     * @param sourceUrl
     * @param destinationDir
     * @param documentName
     * @return
     * @throws IOException
     */
    public String downloadPdf(String sourceUrl, String destinationDir, String documentName) throws IOException {

        String filePath = null;
        if(destinationDir.startsWith("/")){
            filePath = destinationDir;
        } else {
            filePath = "/" + destinationDir;
        }

        if(!destinationDir.endsWith("/")){
            filePath = filePath + "/";
        }
        this.fileStorageLocation = Paths.get(propertiesConfig.getDocStorageBasePath() + filePath).toAbsolutePath().normalize();
        if (!Files.exists(fileStorageLocation)) {
            try {
                Files.createDirectories(this.fileStorageLocation);
            } catch (Exception ex) {
                throw new BadRequestException(
                        "Could not create the directory where the uploaded files will be stored.");
            }
        }

        try {
            String fileName = null;
            int fileFormatPosition = sourceUrl.lastIndexOf(".");
            String fileFormat = sourceUrl.substring(fileFormatPosition, sourceUrl.length());
            log.info("File Format: " + fileFormat);
            if (documentName.contains(fileFormat)) {
                fileName = documentName;
            } else {
                fileName = documentName + fileFormat;
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            InputStream in = new URL(sourceUrl).openStream();
            Files.copy(in, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            String fileStoredWithPath = filePath + fileName;
            return fileStoredWithPath;

        } catch (IOException e) {
            throw new BadRequestException("Document Download Failed: " + e);
        }

    }
}
