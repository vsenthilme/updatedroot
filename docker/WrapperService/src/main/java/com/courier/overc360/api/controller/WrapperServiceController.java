package com.courier.overc360.api.controller;

import com.courier.overc360.api.batch.scheduler.BatchJobScheduler;
import com.courier.overc360.api.exception.BadRequestException;
import com.courier.overc360.api.model.auth.AuthToken;
import com.courier.overc360.api.model.auth.AuthTokenRequest;
import com.courier.overc360.api.model.dto.MultipleUpload;
import com.courier.overc360.api.model.dto.PDFMerger;
import com.courier.overc360.api.model.dto.UpdateCCR;
import com.courier.overc360.api.model.transaction.UploadApiResponse;
import com.courier.overc360.api.service.CommonService;
import com.courier.overc360.api.service.FileStorageService;
import com.courier.overc360.api.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@Api(tags = {"Wrapper Service"}, value = "Wrapper Service Operations") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "User", description = "Operations related to User")})
public class WrapperServiceController {

    @Autowired
    BatchJobScheduler batchJobScheduler;

    @Autowired
    RegisterService registerService;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    CommonService commonService;

    @ApiOperation(response = Optional.class, value = "OAuth Token") // label for swagger
    @PostMapping("/auth-token")
    public ResponseEntity<?> authToken(@Valid @RequestBody AuthTokenRequest authTokenRequest) {
        AuthToken authToken = registerService.getAuthToken(authTokenRequest);
        return new ResponseEntity<>(authToken, HttpStatus.OK);
    }

    //========================================ErrorLog==================================================
    @ApiOperation(response = Optional.class, value = "ErrorLogs - Write to DB") // label for swagger
    @PostMapping("/errorLog/toDB")
    public ResponseEntity<String> errorLogToDataBase() throws Exception {
        try {
            batchJobScheduler.runErrorLogJob();
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to write in db : " + e.getMessage());
        }
    }

    /*========================================PDF===============================================================*/

    @ApiOperation(response = Optional.class, value = "PDF Extract content") // label for swagger
    @PostMapping("/pdf/extract")
    public ResponseEntity<?> extractPdf(@RequestParam("file") MultipartFile file, @RequestParam String filePath) throws Exception {

        try {
            String storedFileResponse = fileStorageService.storeFile(file, filePath);
            if(!filePath.startsWith("/")){
                filePath = "/" + filePath;
            }
            String fileWithPath = filePath + "/" + storedFileResponse;
            UpdateCCR[] response = commonService.extractPdf(fileWithPath);
            return new ResponseEntity <> (response, HttpStatus.OK) ;
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to Extract PDF" + e.getMessage());
        }
    }

    @ApiOperation(response = Optional.class, value = "PDF Merge") // label for swagger
    @PostMapping("/pdf/merge")
    public ResponseEntity<?> mergePdf(@RequestBody PDFMerger pdfMerger) throws Exception {

        try {
            String outputPath = null;

            if(pdfMerger.getOutputPath() == null) {
                throw new BadRequestException("Output Path should be specified");
            }
            if(pdfMerger.getOutputPath() != null) {
                outputPath = pdfMerger.getOutputPath();
            }
            if(outputPath != null && outputPath.length() == 0) {
                throw new BadRequestException("Invalid Output Path");
            }
            if(outputPath != null && outputPath.endsWith("/")) {
                throw new BadRequestException("Output Path specified without fileName and extension");
            }
            if(outputPath != null && !outputPath.contains(".")) {
                throw new BadRequestException("Output fileName not mentioned with extension");
            }

            byte[] mergePdf = commonService.mergePdf(pdfMerger);
            int fileNameIndex = outputPath.lastIndexOf("/");
            String fileName = outputPath.substring(fileNameIndex,pdfMerger.getOutputPath().length());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ fileName + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(mergePdf);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception: " + e);
        }
    }

    @ApiOperation(response = Optional.class, value = "PDF Download") // label for swagger
    @PostMapping("/pdf/download")
    public ResponseEntity<?> downloadPdf(@RequestParam String sourceUrl, @RequestParam String destinationDir, @RequestParam String documentName) throws Exception {

        try {
            String response = commonService.downloadPdf(sourceUrl, destinationDir, documentName);
            return new ResponseEntity <> (response, HttpStatus.OK) ;
        } catch (Exception e) {
            return ResponseEntity.status(500).body(("Failed to download PDF" + e.getMessage()));
        }
    }

    @ApiOperation(response = Optional.class, value = "Document Storage Download") // label for swagger
    @GetMapping("/doc-storage/download")
    public ResponseEntity<?> docStorageDownload(@RequestParam String location, @RequestParam String fileName)
            throws Exception {
        String filePath = fileStorageService.getQualifiedFilePath (location, fileName);
        File file = new File (filePath);
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @ApiOperation(response = Optional.class, value = "Multiple Document Storage Upload") // label for swagger
    @PostMapping("/doc-storage/multiUpload")
    public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files, @RequestParam String location) {
        String[] fileName;

        try {
            List<MultipleUpload> fileNames = new ArrayList<>();

            for(MultipartFile file : files) {
                try {
                    MultipleUpload multipleUpload = new MultipleUpload();
                    fileName = fileStorageService.storeFileWithReturnLocation(file, location);
                    multipleUpload.setFileName(fileName[0]);
                    multipleUpload.setFilePath(fileName[1]);
                    multipleUpload.setResponse("Uploaded Successfully");
                    fileNames.add(multipleUpload);
                } catch (Exception e) {
                    throw new BadRequestException("Exception : " + e);
                }
            }
            return new ResponseEntity <> (fileNames, HttpStatus.OK) ;
        } catch (Exception e) {
            return new ResponseEntity <> ("Fail to upload files!", HttpStatus.EXPECTATION_FAILED) ;
        }
    }

    @ApiOperation(response = Optional.class, value = "Single Document Storage Upload") // label for swagger
    @PostMapping("/document/Upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String filePath) {
        try {
            log.info("Upload Initiated");
            Map<String, String> response = fileStorageService.storeSingleFile(file, filePath);
            return new ResponseEntity <> (response, HttpStatus.OK) ;
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to Upload" + e.getMessage());
        }
    }

    @ApiOperation(response = Optional.class, value = "Multiple Pdf Merge and download as Zip") // label for swagger
    @PostMapping("/pdf/downloadZip")
    public void downloadFile(HttpServletResponse response, @RequestBody List<PDFMerger> pdfMergerList) {

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=download.zip");
        response.setStatus(HttpServletResponse.SC_OK);

        String[] fileNames = commonService.batchMergePdf(pdfMergerList);

        log.info("############# file size ###########" + fileNames);

        try (ZipOutputStream zippedOut = new ZipOutputStream(response.getOutputStream())) {
            for (String file : fileNames) {
                FileSystemResource resource = new FileSystemResource(file);

                ZipEntry e = new ZipEntry(resource.getFilename());
                // Configure the zip entry, the properties of the file
                e.setSize(resource.contentLength());
                e.setTime(System.currentTimeMillis());
                // etc.
                zippedOut.putNextEntry(e);
                // And the content of the resource:
                StreamUtils.copy(resource.getInputStream(), zippedOut);
                zippedOut.closeEntry();
            }
            zippedOut.finish();
        } catch (Exception e) {
            // Exception handling goes here
            throw new BadRequestException("Merging Pdf Failed");
        }
    }

    // Consignment-Upload
    @ApiOperation(response = UploadApiResponse.class, value = " Consignment Upload")
    @PostMapping("/consignment/Upload")
    public ResponseEntity<?> consignmentUpload(@RequestParam("file") MultipartFile file) {

        Map<String, String> response = fileStorageService.processConsignmentOrders(file);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Consignment Upload-V2
    @ApiOperation(response = UploadApiResponse.class, value = " Consignment Upload V2")
    @PostMapping("/consignment/upload/v2")
    public ResponseEntity<?> consignmentUploadV2(@RequestParam("file") MultipartFile multipartFile) {
        Map<String, String> response = fileStorageService.processConsignmentOrdersV2(multipartFile);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}