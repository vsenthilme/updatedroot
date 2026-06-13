package com.courier.overc360.api.common.service;

import com.courier.overc360.api.common.config.PropertiesConfig;
import com.courier.overc360.api.common.controller.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class InvoicePdfMergeService {

    @Autowired
    private PropertiesConfig propertiesConfig;

    public byte[] mergePdfFiles(String directoryPath, String outputPath, String lineNo) {

        try {
            if (directoryPath == null) {
                throw new BadRequestException("Path should be specified");
            }
            if (directoryPath.startsWith("/")) {
                directoryPath = propertiesConfig.getDocStorageBasePath() + directoryPath;
            } else {
                directoryPath = propertiesConfig.getDocStorageBasePath() + "/" + directoryPath;
            }

            File directory = new File(directoryPath);

            if (!directory.exists() || !directory.isDirectory()) {
                throw new IllegalArgumentException("Invalid directory path provided.");
            }

            PDFMergerUtility pdfMerger = new PDFMergerUtility();

            List<File> pdfFiles = new ArrayList<>();

            // Step 1: Prioritize 'Invoice_.pdf'
            File invoicePdf = new File(directoryPath + File.separator + "Costing_Sheet.pdf");
            if (invoicePdf.exists() && invoicePdf.isFile()) {
                pdfFiles.add(invoicePdf);
            }

//            log.info("Pdf Step 1: ----> {}", pdfFiles);

            // Step 2: Add all other Pdf files except 'Invoice.pdf'
            File[] allPdfFiles = directory.listFiles(((dir, name) -> name.toLowerCase().endsWith(".pdf")));
            if (allPdfFiles != null) {
                for (File pdfFile : allPdfFiles) {
                    // If lineNo is provided filter files starts with lineNo in the prefix of their name
                    if (lineNo != null && !lineNo.trim().isEmpty()) {
                        if (pdfFile.getName().startsWith(lineNo) && !pdfFile.getName().equalsIgnoreCase("Costing_Sheet.pdf")) {
                            pdfFiles.add(pdfFile);
                        }
                    } else {
                        // If lineNo is Not Provided filter all files present in the directory path
                        if (!pdfFile.getName().equalsIgnoreCase("Costing_Sheet.pdf")) {
                            pdfFiles.add(pdfFile);
                        }
                    }
                }
            }

//            log.info("Pdf File after Step 2 condition : -----> {}", pdfFiles);

            if (pdfFiles.isEmpty()) {
                throw new IllegalArgumentException("No PDF files found in the specified directory.");
            }

            try (ByteArrayOutputStream mergedOutput = new ByteArrayOutputStream()) {
                // Step 3: Add files to PDFMergerUtility in the correct order
                for (File pdfFile : pdfFiles) {
                    if(pdfFile != null) {
                        pdfMerger.addSource(pdfFile);
                    }
                }

//                log.info("Pdf File after Step 3 : --------> {}", pdfFiles);

                pdfMerger.setDestinationStream(mergedOutput);
                pdfMerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

//                log.info("Merged Output path : -----> {}", mergedOutput);

                return mergedOutput.toByteArray();

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error while merging PDF Files", e);
            }

//            List<InputStream> pdfStreams = new ArrayList<>();
//            for (File path : pdfFiles) {
//                try {
//                    String filePath = String.valueOf(path);
//                    pdfStreams.add(Files.newInputStream(Paths.get(filePath)));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    throw new BadRequestException("Failed to Read the PDF: " + e);
//                }
//            }
//
//            log.info("PdfStreams ----> {}", pdfStreams);
//
//            byte[] mergedInvoiceByte = mergeInvoicePdfs(pdfStreams, outputPath);
//
//            log.info("MergedInvoiceByte ----> {}", mergedInvoiceByte);
//
//            return mergedInvoiceByte;

        } catch (Exception e) {
            throw new BadRequestException("Exception while merge : " + e);
        }
    }

    public byte[] mergeInvoicePdfs(List<InputStream> pdfs, String outputPath) throws IOException, InterruptedException {
        try {
            PDFMergerUtility mergerUtility = new PDFMergerUtility();

            for (InputStream pdf : pdfs) {
                mergerUtility.addSource(pdf);
            }

            try (ByteArrayOutputStream mergedOutput = new ByteArrayOutputStream()) {
                mergerUtility.setDestinationStream(mergedOutput);
                mergerUtility.mergeDocuments(null);
                byte[] mergedBytes = mergedOutput.toByteArray();

//                log.info("MergedBytes -----> {}", mergedBytes);

                return mergedBytes;
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}