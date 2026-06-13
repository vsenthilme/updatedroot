package com.courier.overc360.api.common.service;

import com.courier.overc360.api.common.config.PropertiesConfig;
import com.courier.overc360.api.common.controller.exception.BadRequestException;
import com.courier.overc360.api.common.model.pdf.PDFMerger;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PDFMergeService {

    @Autowired
    PropertiesConfig propertiesConfig;

    private Path fileStorageLocation = null;

    public byte[] mergePdfs(List<InputStream> pdfs, String outputPath) throws IOException {
        PDFMergerUtility mergerUtility = new PDFMergerUtility();
        for (InputStream pdf : pdfs) {
            mergerUtility.addSource(pdf);
        }
        try (ByteArrayOutputStream mergedOutput = new ByteArrayOutputStream()) {
            mergerUtility.setDestinationStream(mergedOutput);
            mergerUtility.mergeDocuments(null);
            byte[] mergedBytes = mergedOutput.toByteArray();
            // Try saving merged pdf in local
            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                fos.write(mergedBytes);
            }
            return mergedBytes;
        }
    }
    /**
     * @param request
     * @return
     */
    public byte[] pdfMerge(PDFMerger request) throws IOException {
        System.out.println("Received filePaths: " + request.getFilePaths());
        System.out.println("Received outputPath: " + request.getOutputPath());
        List<InputStream> pdfStreams = new ArrayList<>();
        for (String path : request.getFilePaths()) {
            try {
                String filePath = getQualifiedFilePath(path);
                pdfStreams.add(Files.newInputStream(Paths.get(filePath)));
            } catch (IOException e) {
                e.printStackTrace();
                throw new BadRequestException("Failed to Read the PDF" + e);
            }
        }
        String fileOuputStoragePath = getQualifiedFilePath(request.getOutputPath());
        int location = fileOuputStoragePath.lastIndexOf("/");
        String directoryCreate = fileOuputStoragePath.substring(0,location);
        this.fileStorageLocation = Paths.get(directoryCreate).toAbsolutePath().normalize();
        if (!Files.exists(fileStorageLocation)) {
            try {
                Files.createDirectories(this.fileStorageLocation);
            } catch (Exception ex) {
                throw new BadRequestException(
                        "Could not create the directory where the merged files will be stored.");
            }
        }
        byte[] mergePdf = mergePdfs(pdfStreams, fileOuputStoragePath);
        return mergePdf;
    }
    /**
     *
     * @param pdfMergerList
     * @throws IOException
     */
    public List<String> batchPdfMerge(List<PDFMerger> pdfMergerList) throws IOException {
        List<String> fileNameWithPath = new ArrayList<>();
        for (PDFMerger request : pdfMergerList) {
            List<InputStream> pdfStreams = new ArrayList<>();
            for (String path : request.getFilePaths()) {
                try {
                    String filePath = getQualifiedFilePath(path);
                    pdfStreams.add(Files.newInputStream(Paths.get(filePath)));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new BadRequestException("Failed to Read the PDF" + e);
                }
            }
            String fileOuputStoragePath = getQualifiedFilePath(request.getOutputPath());
            int location = fileOuputStoragePath.lastIndexOf("/");
            String directoryCreate = fileOuputStoragePath.substring(0, location);
            this.fileStorageLocation = Paths.get(directoryCreate).toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)) {
                try {
                    Files.createDirectories(this.fileStorageLocation);
                } catch (Exception ex) {
                    throw new BadRequestException(
                            "Could not create the directory where the merged files will be stored.");
                }
            }
            byte[] mergePdf = mergePdfs(pdfStreams, fileOuputStoragePath);
            fileNameWithPath.add(fileOuputStoragePath);
//            copyToTempFolder(mergePdf);
        }
        return fileNameWithPath;
    }
    /**
     *
     * @param location
     * @return
     */
    public String getQualifiedFilePath(String location) {
        String filePath = propertiesConfig.getDocStorageBasePath();
        if (location.startsWith("/")) {
            filePath = filePath + location;
        } else {
            filePath = filePath + "/" + location;
        }
        return filePath;
    }
    /**
     *
     * @param pdfDocument
     * @throws IOException
     */
    public void copyToTempFolder(byte[] pdfDocument) throws IOException {
        String filePath = propertiesConfig.getDocStorageBasePath();
        filePath = filePath + "/temp";
        this.fileStorageLocation = Paths.get(filePath).toAbsolutePath().normalize();
        try {
            Files.deleteIfExists(fileStorageLocation);
        } catch (IOException e) {
            throw new BadRequestException("Could not delete the directory");
        }
        if (!Files.exists(fileStorageLocation)) {
            try {
                Files.createDirectories(this.fileStorageLocation);
            } catch (Exception ex) {
                throw new BadRequestException("Could not create the directory where the merged files will be stored.");
            }
        }
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(pdfDocument);
        }
    }
}
