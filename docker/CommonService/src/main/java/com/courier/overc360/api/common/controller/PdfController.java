package com.courier.overc360.api.common.controller;

import com.courier.overc360.api.common.config.PropertiesConfig;
import com.courier.overc360.api.common.controller.exception.BadRequestException;
import com.courier.overc360.api.common.model.pdf.PDFMerger;
import com.courier.overc360.api.common.model.pdf.UpdateCCR;
import com.courier.overc360.api.common.service.DownloadService;
import com.courier.overc360.api.common.service.PDFApacheExtractionService;
import com.courier.overc360.api.common.service.PDFMergeService;
import com.courier.overc360.api.common.service.PDFSplitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Validated
@Api(tags = {"PDFController" }, value = "PDFController Operations related to PDFController")
@SwaggerDefinition(tags = { @Tag(name = "PDF", description = "Operations related to PDF") })
@RequestMapping("/pdf")
@RestController
public class PdfController {

    @Autowired
    private PDFApacheExtractionService pdfApacheExtractionService;

    @Autowired
    private PDFMergeService pdfMergeService;

    @Autowired
    private PDFSplitService pdfSplitService;

    @Autowired
    PropertiesConfig propertiesConfig;

    @Autowired
    DownloadService downloadService;

    private Path fileStorageLocation = null;

    @PostMapping("/extract")
    public ResponseEntity<List<String>> extractDetails(@RequestParam String fileName) {
        try {
            // Extract details from the PDF
            List<String> details = pdfApacheExtractionService.extractDetailsFromPdf(fileName.trim());

            return new ResponseEntity<>(details, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/extract/v2")
    public ResponseEntity<?> extractPdfDetails(@RequestParam String fileName) {
        try {
            // Extract details from the PDF
            Set<UpdateCCR> details = pdfApacheExtractionService.pdfExtractDetails(fileName.trim());

            return new ResponseEntity<>(details, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/merge")
    public ResponseEntity<byte[]> mergePdfs(@RequestBody PDFMerger request) throws IOException {
        System.out.println("Received filePaths: " + request.getFilePaths());
        System.out.println("Received outputPath: " + request.getOutputPath());

        List<InputStream> pdfStreams = new ArrayList<>();

        for(String path : request.getFilePaths()) {
                    try {
                String filePath = propertiesConfig.getDocStorageBasePath() + path;
                pdfStreams.add(Files.newInputStream(Paths.get(filePath)));
                    } catch (IOException e) {
                e.printStackTrace();
                throw new BadRequestException("Failed to Read the PDF" + e);
            }
        }

        String fileOuputStoragePath = propertiesConfig.getDocStorageBasePath() + request.getOutputPath();
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

        byte[] mergePdf = pdfMergeService.mergePdfs(pdfStreams, fileOuputStoragePath);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"merged.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(mergePdf);
    }

    @PostMapping("/merge/v2")
    public ResponseEntity<byte[]> pdfMerge(@RequestBody PDFMerger request) throws IOException {

        byte[] mergePdf = pdfMergeService.pdfMerge(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"merged.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(mergePdf);
    }

    @PostMapping("/merge/batch")
    public ResponseEntity<?> pdfMergeBatch(@RequestBody List<PDFMerger> request) throws IOException {
        try {
            List<String> fileNameWithPath = pdfMergeService.batchPdfMerge(request);
            return new ResponseEntity <> (fileNameWithPath, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity <> ("Exception: Merge Failed", HttpStatus.OK);
        }
    }

    @GetMapping("/splitPdf")
    public String splitPdf(@RequestParam String sourceFilePath, @RequestParam String destinationDir) {
        try {
            pdfSplitService.splitPdf(sourceFilePath, destinationDir);
            return "PDF split successfully!";
        } catch (IOException e) {
            return "Error occurred: " + e.getMessage();
        }
    }

    @GetMapping("/download")
    public String downloadPdf(@RequestParam String destinationDir, @RequestParam String documentName) {
        try {
            downloadService.downloadPdf(destinationDir, documentName);
            return "PDF download successfully!";
        } catch (IOException e) {
            return "Error occurred: " + e.getMessage();
        }
    }

    @GetMapping("/v2/download")
    public String downloadDocument(@RequestParam String sourceUrl, @RequestParam String destinationDir, @RequestParam String documentName) {
        try {
            String filePathWithName = downloadService.downloadPdf(sourceUrl, destinationDir, documentName);
            return filePathWithName;
        } catch (IOException e) {
            return "Error occurred: " + e.getMessage();
        }
    }
}