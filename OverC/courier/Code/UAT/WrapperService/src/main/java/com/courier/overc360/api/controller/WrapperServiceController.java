package com.courier.overc360.api.controller;

import com.courier.overc360.api.batch.dto.ConsignmentDto;
import com.courier.overc360.api.batch.dto.PieceDto;
import com.courier.overc360.api.batch.scheduler.BatchJobScheduler;
import com.courier.overc360.api.config.PropertiesConfig;
import com.courier.overc360.api.exception.BadRequestException;
import com.courier.overc360.api.model.auth.AuthToken;
import com.courier.overc360.api.model.auth.AuthTokenRequest;
import com.courier.overc360.api.model.dto.InvoicePDFMerger;
import com.courier.overc360.api.model.dto.MultipleUpload;
import com.courier.overc360.api.model.dto.PDFMerger;
import com.courier.overc360.api.model.dto.UpdateCCR;
import com.courier.overc360.api.model.transaction.UploadApiResponse;
import com.courier.overc360.api.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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

    @Autowired
    PropertiesConfig propertiesConfig;

    @Autowired
    DataUploadService dataUploadService;

    @Autowired
    MidMileService midMileService;

    private static final String GHOSTSCRIPT_PATH = "/snap/bin/gs";

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
    public ResponseEntity<?> extractPdf(@RequestParam("file") MultipartFile file, @RequestParam String filePath,
                                        @RequestParam String loginUserID) throws Exception {

        try {
            String storedFileResponse = fileStorageService.storeFile(file, filePath);
            if(!filePath.startsWith("/")){
                filePath = "/" + filePath;
            }
            String fileWithPath = filePath + "/" + storedFileResponse;
            UpdateCCR[] response = commonService.extractPdf(fileWithPath, loginUserID);
            return new ResponseEntity <> (response, HttpStatus.OK) ;
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to Extract PDF" + e.getMessage());
        }
    }

    @ApiOperation(response = Optional.class, value = "PDF Merge") // label for swagger
    @PostMapping("/pdf/merge/2")
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
            Thread.sleep(1000);                 //return after a one second delay
            return new ResponseEntity <> (response, HttpStatus.OK) ;
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to Upload" + e.getMessage());
        }
    }

    @ApiOperation(response = Optional.class, value = "Invoice Upload") // label for swagger
    @PostMapping("/invoice/Upload")
    public ResponseEntity<?> invoiceUploadFile(@RequestParam("file") MultipartFile file, @RequestParam String filePath) {
        try {
            log.info("Upload Initiated");
            Map<String, String> response = fileStorageService.invoiceUpload(file, filePath);
            Thread.sleep(1000);                 //return after a one second delay
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
    public ResponseEntity<?> consignmentUpload(@RequestParam("file") MultipartFile file, @RequestParam String loginUserID) {

        Map<String, String> response = fileStorageService.processConsignmentOrders(file, loginUserID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Consignment Upload-V2
    @ApiOperation(response = UploadApiResponse.class, value = " Consignment Upload V2")
    @PostMapping("/consignment/upload/v2")
    public ResponseEntity<?> consignmentUploadV2(@RequestParam("file") MultipartFile multipartFile,
                                                 @RequestParam String companyId, @RequestParam String loginUserID) {
        Map<String, String> response = fileStorageService.processConsignmentOrdersV2(multipartFile, companyId, loginUserID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ApiOperation(response = UploadApiResponse.class, value = "PreAlert Upload")
    @PostMapping("/preAlert/upload")
    public ResponseEntity<?> preAlertUpload(@RequestParam("file") MultipartFile multipartFile, @RequestParam String companyId,
                                            @RequestParam String partnerId, @RequestParam String partnerType,
                                            @RequestParam String partnerMasterAirwayBill,
                                            @RequestParam String flightNo, @RequestParam String flightName,
                                            @RequestParam(required = false) String originFlightCountry,
                                            @RequestParam(required = false) String subCustomerId,
                                            @RequestParam(required = false) String subCustomerName, @RequestParam String loginUserID,
                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date estimatedTimeOfDeparture,
                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date estimatedTimeOfArrival) {
        // Record start time
        long startTime = System.currentTimeMillis();
        Map<String, String> response = fileStorageService.processPreAlertUpload(multipartFile, companyId, partnerType,
                partnerId, partnerMasterAirwayBill, flightNo, flightName, estimatedTimeOfDeparture, estimatedTimeOfArrival,
                subCustomerId, subCustomerName, originFlightCountry, loginUserID);

        // Record end time
        long endTime = System.currentTimeMillis();

        // Calculate the elapsed time in milliseconds
        long durationInMillis = endTime - startTime;

        // Log the execution time
        log.info("PreAlert upload processing time: {} ms", durationInMillis);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/delivery/upload")
    @ApiOperation(response = UploadApiResponse.class, value = "Delivery Upload")
    public ResponseEntity<?>deliveryUpload(@RequestParam("file") MultipartFile multipartFile,
                                           @RequestParam String companyId, @RequestParam String languageId,
                                           @RequestParam String loginUserID) {
        Map<String, String> response = dataUploadService.processDeliveryUpload(multipartFile, companyId, languageId, loginUserID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "PDF Merge") // label for swagger
    @PostMapping("/pdf/merge")
    public void mergePdf(HttpServletResponse response, @RequestBody List<PDFMerger> pdfMergerList) throws Exception {
        try {
            if (pdfMergerList == null || pdfMergerList.isEmpty()) {
                throw new BadRequestException("PDF Merger list cannot be null or empty");
            }

            // Ensure each PDFMerger has a valid output path
            for (PDFMerger pdfMerger : pdfMergerList) {
                String outputPath = pdfMerger.getOutputPath();
                if (outputPath == null) {
                    throw new BadRequestException("Output Path should be specified");
                }
                if (outputPath.length() == 0) {
                    throw new BadRequestException("Invalid Output Path");
                }
                if (outputPath.endsWith("/")) {
                    throw new BadRequestException("Output Path specified without fileName and extension");
                }
                if (!outputPath.contains(".")) {
                    throw new BadRequestException("Output fileName not mentioned with extension");
                }
            }

            // Call the service to merge the PDFs
            byte[] mergedZipBytes = commonService.mergePdfs(pdfMergerList);

            if (mergedZipBytes != null && mergedZipBytes.length > 0) {
                log.info("MergedZipBytes <-----------------------------> {} ", mergedZipBytes);
                response.setContentType("application/zip");
                response.setHeader("Content-Disposition", "attachment;filename=mergedFiles.zip");
                response.setStatus(HttpServletResponse.SC_OK);

                response.getOutputStream().write(mergedZipBytes);
            } else {
                throw new BadRequestException("No files were merged.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Merge PDF files from directory", response = byte[].class)
    @PostMapping("/pdf/invoice/merge")
    public ResponseEntity<byte[]> mergeInvoicePdf(@RequestBody InvoicePDFMerger invoicePDFMerger) {
        try {
            validatePdfMergerRequest(invoicePDFMerger);

            byte[] mergedPdf = commonService.mergeInvoicePdf(invoicePDFMerger);
            String outputPath = invoicePDFMerger.getOutputPath();
            String fileName = outputPath.substring(outputPath.lastIndexOf("/") + 1);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(mergedPdf);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private void validatePdfMergerRequest(InvoicePDFMerger invoicePDFMerger) {
        String outputPath = invoicePDFMerger.getOutputPath();
        if (outputPath == null || outputPath.trim().isEmpty()) {
            throw new IllegalArgumentException("Output Path should be specified");
        }
        if (!outputPath.contains(".")) {
            throw new IllegalArgumentException("Output fileName not mentioned with extension");
        }
    }

    @ApiOperation(response = Optional.class, value = "Single Document Storage Upload All File Format") // label for swagger
    @PostMapping("/document/Upload/V2")
    public ResponseEntity<?> uploadAllFiles(@RequestParam("file") MultipartFile file, @RequestParam String filePath) {
        try {
            log.info("Upload Initiated");
            Map<String, String> response = fileStorageService.storeSingleFileAllFormat(file, filePath);
            Thread.sleep(1000);                 //return after a one second delay
            return new ResponseEntity <> (response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to Upload" + e.getMessage());
        }
    }

    // Api for compressing incoming pdf files
    @PostMapping("/pdfcompressed/upload")
    public ResponseEntity<?> uploadAndDownloadCompressedPdf(@RequestParam("file")MultipartFile file) {
        try {
            // Ensuring the uploaded file is a pdf
//            if (!file.getContentType().equals("application/pdf")) {
//                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(null);
//            }

            // Get the base path and create a 'temp' directory
            Path tempDirectoryPath = Paths.get(propertiesConfig.getDocStorageBasePath(), "temp");
            if (!Files.exists(tempDirectoryPath)) {
                Files.createDirectories(tempDirectoryPath);         // Creating the directory if it is not already present
            }

            // Save the uploaded file temporarily
            Path tempInputPath = Files.createTempFile(tempDirectoryPath,"input-", ".pdf");
            Path tempOutputPath = Files.createTempFile(tempDirectoryPath,"output-compressed-", ".pdf");
            Files.write(tempInputPath, file.getBytes());

            // Compress the PDF using Ghostscript
            compressPdf(tempInputPath.toString(), tempOutputPath.toString());

            // Create a response for the compressed file
            File compressedPdf = tempOutputPath.toFile();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(compressedPdf));

            // Set headers for downloading the file
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=" + file.getOriginalFilename());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(compressedPdf.length())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (IOException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Method to compress PDF using Ghostscript
    private void compressPdf(String inputPath, String outputPath) throws IOException, InterruptedException {
        Process process = new ProcessBuilder(
                GHOSTSCRIPT_PATH,
                "-sDEVICE=pdfwrite",
                "-dCompatibilityLevel=1.4",
                "-dPDFSETTINGS=/screen",
                "-dDownsampleColorImages=true",
                "-dDownsampleGrayImages=true",
                "-dDownsampleMonoImages=true",
                "-dColorImageResolution=360",
                "-dGrayImageResolution=360",
                "-dMonoImageResolution=300",
                "-dNOPAUSE",
                "-dQUIET",
                "-dBATCH",
                "-sOutputFile=" + outputPath, inputPath).start();
        process.waitFor();
    }

    @ApiOperation(response = Optional.class, value = "Multiple Document Storage Upload")
    @PostMapping("/doc/multiUpload")
    public ResponseEntity<?> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @RequestParam String location) {
        if (files == null || files.length == 0) {
            return new ResponseEntity<>("No files provided", HttpStatus.BAD_REQUEST);
        }

        if (location == null || location.isEmpty()) {
            return new ResponseEntity<>("No location provided", HttpStatus.BAD_REQUEST);
        }

        try {
            // List to store information about each file upload
            List<MultipleUpload> fileNames = new ArrayList<>();

            // Loop through each file
            for (MultipartFile file : files) {
                try {
                    // Call the service method to store the file and convert to PDF
                    String[] storedFileDetails = fileStorageService.storeMultipleFilesAllFormat(file, location);

                    // Create a MultipleUpload object to store response details for each file
                    MultipleUpload multipleUpload = new MultipleUpload();
                    if (storedFileDetails[0].endsWith(".png")) {
                        multipleUpload.setFileName(storedFileDetails[0].replace(".png" , ".pdf")); // Set the file name
                    } else if (storedFileDetails[0].endsWith(".jpg")) {
                        multipleUpload.setFileName(storedFileDetails[0].replace("jpg" , ".pdf")); // Set the file name
                    } else if (storedFileDetails[0].endsWith("jpeg")) {
                        multipleUpload.setFileName(storedFileDetails[0].replace("jpeg" , ".pdf")); // Set the file name
                    } else {
                        multipleUpload.setFileName(storedFileDetails[0]); // Set the file name
                    }
                    multipleUpload.setFilePath(storedFileDetails[1]); // Set the file path
                    multipleUpload.setResponse("Uploaded Successfully"); // Set the response message

                    // Add the file details to the list
                    fileNames.add(multipleUpload);
                } catch (Exception e) {
                    throw new BadRequestException("Exception while uploading file: " + e.getMessage());
                }
            }

            // Return the list of uploaded file information
            return new ResponseEntity<>(fileNames, HttpStatus.OK);

        } catch (Exception e) {
            // Handle any general exception
            return new ResponseEntity<>("Failed to upload files!", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @ApiOperation(response = Optional.class, value = "Document Storage Download All")
    @GetMapping("/doc-storage/download-all")
    public ResponseEntity<?> docStorageDownloadAll(@RequestParam String location) throws Exception {
        // Get the full file path for the directory
        String directoryPath = fileStorageService.getQualifiedFilePath(location);

        // Create temporary zip file
        File zipFile = fileStorageService.createZipOfDirectory(directoryPath);

        // Prepare the zip file as a ByteArrayResource for download
        Path path = Paths.get(zipFile.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipFile.getName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(zipFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @ApiOperation(response = UploadApiResponse.class, value = "Pickup Upload")
    @PostMapping("/pickup/upload")
    public ResponseEntity<?> pickupUpload(@RequestParam("file") MultipartFile multipartFile, @RequestParam String loginUserID){
        Map<String, String> response = fileStorageService.processPickupUpload(multipartFile, loginUserID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(response = UploadApiResponse.class, value = "Console Upload")
    @PostMapping("/console/upload")
    public ResponseEntity<?> consoleUpload(@RequestParam("file") MultipartFile multipartFile, @RequestParam String loginUserID){
        Map<String, String> response = dataUploadService.processConsoleUpload(multipartFile, loginUserID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(response = PieceDto.class, value = "Piece Update Upload")
    @PostMapping("/piece/update/upload")
    public ResponseEntity<?> pieceUpload(@RequestParam MultipartFile file, @RequestParam String loginUserID) {
        Map<String, String> response = dataUploadService.processPieceUpload(file, loginUserID);
         return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(response = ConsignmentDto.class, value = "Consignment Update Upload Program")
    @PostMapping("/consignment/update/upload")
    public ResponseEntity<?> consignmentUpdate(@RequestParam MultipartFile file, @RequestParam String loginUserID) {
        Map<String, String> response = dataUploadService.consignmentUploadUpdate(file, loginUserID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @ApiOperation(response = Optional.class, value = "Pdf Send Mail")
//    @PostMapping("/pdf/email")
//    public ResponseEntity<?> sendEmailWithAttachment(@RequestParam("file") MultipartFile file) {
////            emailService.sendEmailPdf(tempFile);
//            UploadApiResponse response = midMileService.sendEmail(file);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @ApiOperation(value = "Pdf Send Mail")
    @PostMapping("/pdf/email")
    public ResponseEntity<?> sendEmailWithAttachment(@RequestParam("file") MultipartFile file) throws IOException {
            File tempFile = File.createTempFile("attachment-", file.getOriginalFilename());
            file.transferTo(tempFile);
            // Send email with the PDF
            dataUploadService.sendEmailPdf(tempFile);
            // Clean up temporary file
            tempFile.delete();
            UploadApiResponse uploadResponse = new UploadApiResponse();
            uploadResponse.setStatusCode("200");
            uploadResponse.setMessage("Email Send Successfully");
            return new ResponseEntity<>(uploadResponse, HttpStatus.OK);
    }

    @ApiOperation(response = Optional.class, value = "Invoice Upload") // label for swagger
    @PostMapping("/invoice/pdf/Upload")
    public ResponseEntity<?> invoiceUploadForSendMail(@RequestParam("file") MultipartFile file) {
        try {
            log.info("Upload Initiated");
            Map<String, String> response = fileStorageService.lmdInvoiceUpload(file);
            Thread.sleep(1000);                 //return after a one second delay
            return new ResponseEntity <> (response, HttpStatus.OK) ;
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to Upload" + e.getMessage());
        }
    }
}