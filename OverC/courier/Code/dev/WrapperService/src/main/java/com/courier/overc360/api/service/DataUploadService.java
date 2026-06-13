package com.courier.overc360.api.service;


import com.courier.overc360.api.batch.dto.ConsignmentDto;
import com.courier.overc360.api.batch.dto.PieceDto;
import com.courier.overc360.api.config.PropertiesConfig;
import com.courier.overc360.api.exception.BadRequestException;
import com.courier.overc360.api.model.auth.AuthToken;
import com.courier.overc360.api.model.lastmile.Delivery;
import com.courier.overc360.api.model.transaction.Console;
import com.courier.overc360.api.model.transaction.UploadApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Slf4j
@Service
public class DataUploadService {

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private LastMileService lastMileService;

    @Autowired
    MidMileService midMileService;

    @Autowired
    ExcelReadMultiSheet excelReadMultiSheet;

    @Autowired
    PropertiesConfig propertiesConfig;

    @Autowired
    ProcessExcelData processExcelData;

    @Autowired
    private JavaMailSender mailSender;

    private Path fileStorageLocation = null;

    //ProcessConsignmentOrders-V2
    public Map<String, String> processDeliveryUpload(MultipartFile file, String companyId, String languageId, String loginUserID) {
        this.fileStorageLocation = Paths.get(propertiesConfig.getFileUploadDir()).toAbsolutePath().normalize();
        if (!Files.exists(fileStorageLocation)) {
            try {
                Files.createDirectories(this.fileStorageLocation);
            } catch (Exception ex) {
                throw new BadRequestException(
                        "Could not create the directory where the uploaded files will be stored.");
            }
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        log.info("filename before: " + fileName);
        fileName = fileName.replace(" ", "_");
        log.info("filename after: " + fileName);

        try {
            if (fileName.contains("..")) {
                throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            List<Delivery> deliveries = processExcelData.uploadExcelFile(file, companyId, languageId);
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("Copied : " + targetLocation);

//            List<List<String>> allRowsList = readExcelData(targetLocation.toFile());

            fileName = fileName.trim(); // Remove any leading or trailing whitespace
            log.info("filename after trim: " + fileName);

            // Uploading Orders
            UploadApiResponse[] dbUploadApiResponse = new UploadApiResponse[0];
            AuthToken authToken = authTokenService.getLastMileServiceAuthToken();
            dbUploadApiResponse = lastMileService.createDeliveryUpload(deliveries, loginUserID, authToken.getAccess_token());

            if (dbUploadApiResponse != null) {
                Map<String, String> mapFileProps = new HashMap<>();
                mapFileProps.put("file", fileName);
                mapFileProps.put("status", "UPLOADED SUCCESSFULLY");
                return mapFileProps;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
        }
        return null;
    }

    //==============================================================CONSOLE_UPDATE==========================================================

    // Console_Upload
    public Map<String, String> processConsoleUpload(MultipartFile file, String loginUserID) {
        this.fileStorageLocation = Paths.get(propertiesConfig.getFileUploadDir()).toAbsolutePath().normalize();
        if (!Files.exists(fileStorageLocation)) {
            try {
                Files.createDirectories(this.fileStorageLocation);
            } catch (Exception ex) {
                throw new BadRequestException(
                        "Could not create the directory where the uploaded files will be stored.");
            }
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        log.info("filename before: " + fileName);
        fileName = fileName.replace(" ", "_");
        log.info("filename after: " + fileName);

        try {
            if (fileName.contains("..")) {
                throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("Copied : " + targetLocation);

//            List<List<String>> allRowsList = excelReadMultiSheet.readExcelDataForConsole(targetLocation.toFile());
            List<Map<Integer, List<List<String>>>> allSheetsData = excelReadMultiSheet.readExcelDataForConsole(targetLocation.toFile());
            List<Console> prepConsoleData = processExcelData.prepConsoleData(allSheetsData);
            log.info("Console data : " + prepConsoleData);

            // Uploading Orders
            UploadApiResponse[] dbUploadApiResponse = new UploadApiResponse[0];
            AuthToken authToken = authTokenService.getMidMileServiceAuthToken();
            dbUploadApiResponse = midMileService.consoleUpload(prepConsoleData, loginUserID, authToken.getAccess_token());

            if (dbUploadApiResponse != null) {
                Map<String, String> mapFileProps = new HashMap<>();
                mapFileProps.put("file", fileName);
                mapFileProps.put("status", "UPLOADED SUCCESSFULLY");
                return mapFileProps;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
        }
        return null;
    }
    //======================================================================================================================

    //Piece_Upload_Update
    public Map<String, String> processPieceUpdate(MultipartFile file, String loginUserID) {
        this.fileStorageLocation = Paths.get(propertiesConfig.getFileUploadDir()).toAbsolutePath().normalize();
        if (!Files.exists(fileStorageLocation)) {
            try {
                Files.createDirectories(this.fileStorageLocation);
            } catch (Exception e) {
                throw new BadRequestException(
                        "Could not create the directory where the uploaded files will be stored.");
            }
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        log.info("fileName before : " + fileName);
        fileName = fileName.replace("", "_");
        log.info("fileName after: " + fileName);
        try {
            if (fileName.contains("..")) {
                throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("Copied : " + targetLocation);

            List<List<String>> allRowsList = excelReadMultiSheet.readExcelData(targetLocation.toFile());
            List<PieceDto> prepPieceDto = processExcelData.prepPieceData(allRowsList);
            log.info("Piece data : " + prepPieceDto);

            // Uploading Orders
            UploadApiResponse[] uploadApiResponse = new UploadApiResponse[0];
            AuthToken authToken = authTokenService.getMidMileServiceAuthToken();
            uploadApiResponse = midMileService.pieceUpdateUpload(prepPieceDto, loginUserID, authToken.getAccess_token());
            if (uploadApiResponse != null) {
                Map<String, String> mapFileProps = new HashMap<>();
                mapFileProps.put("file", fileName);
                mapFileProps.put("status", "UPLOADED SUCCESSFULLY");
                return mapFileProps;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
        }
        return null;
    }

    // Piece Upload (fieldName)
    public Map<String, String> processPieceUpload(MultipartFile file, String loginUserID) {
        this.fileStorageLocation = Paths.get(propertiesConfig.getFileUploadDir()).toAbsolutePath().normalize();
        if (!Files.exists(fileStorageLocation)) {
            try {
                Files.createDirectories(this.fileStorageLocation);
            } catch (Exception ex) {
                throw new BadRequestException(
                        "Could not create the directory where the uploaded files will be stored.");
            }
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        log.info("filename before: " + fileName);
        fileName = fileName.replace(" ", "_");
        log.info("filename after: " + fileName);
        try {
            if (fileName.contains("..")) {
                throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            List<PieceDto> pieceDtoList = processExcelData.setPieceExcelFile(file);
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("Copied : " + targetLocation);

            fileName = fileName.trim(); // Remove any leading or trailing whitespace
            log.info("filename after trim: " + fileName);

            // Uploading Orders
            UploadApiResponse[] uploadApiResponse = new UploadApiResponse[0];
            AuthToken authToken = authTokenService.getMidMileServiceAuthToken();
            uploadApiResponse = midMileService.pieceUpdateUpload(pieceDtoList, loginUserID, authToken.getAccess_token());

            if (uploadApiResponse != null) {
                Map<String, String> mapFileProps = new HashMap<>();
                mapFileProps.put("file", fileName);
                mapFileProps.put("status", "UPLOADED SUCCESSFULLY");
                return mapFileProps;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
        }
        return null;
    }


    // Consignment_Upload (fieldName)
    public Map<String, String> consignmentUploadUpdate(MultipartFile file, String loginUserID) {
        this.fileStorageLocation = Paths.get(propertiesConfig.getFileUploadDir()).toAbsolutePath().normalize();
        if (!Files.exists(fileStorageLocation)) {
            try {
                Files.createDirectories(this.fileStorageLocation);
            } catch (Exception ex) {
                throw new BadRequestException(
                        "Could not create the directory where the uploaded files will be stored.");
            }
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        log.info("filename before: " + fileName);
        fileName = fileName.replace(" ", "_");
        log.info("filename after: " + fileName);
        try {
            if (fileName.contains("..")) {
                throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            List<ConsignmentDto> consignmentDtos = processExcelData.setConsignmentExcelFile(file);
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("Copied : " + targetLocation);

            fileName = fileName.trim(); // Remove any leading or trailing whitespace
            log.info("filename after trim: " + fileName);

            // Uploading Orders
            UploadApiResponse[] uploadApiResponse = new UploadApiResponse[0];
            AuthToken authToken = authTokenService.getMidMileServiceAuthToken();
            uploadApiResponse = midMileService.consignmentUploadUpdate(consignmentDtos, loginUserID, authToken.getAccess_token());

            if (uploadApiResponse != null) {
                Map<String, String> mapFileProps = new HashMap<>();
                mapFileProps.put("file", fileName);
                mapFileProps.put("status", "UPLOADED SUCCESSFULLY");
                return mapFileProps;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
        }
        return null;
    }

    // Send Email Pdf
    public void sendEmailPdf(File pdfFile) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("no-reply@overc360.com");
            helper.setTo("yogesh.m@tekclover.com");

            String subject = "Monthly shipment consolidated invoice - reg";
            String body = "Dear " +
                    "Please find the attached monthly invoice - June. For your reference";
            helper.setSubject(subject);
            helper.setText(body);

            // Attach PDF file
            FileSystemResource file = new FileSystemResource(pdfFile);
            helper.addAttachment(pdfFile.getName(), file);

            // Send the email
            mailSender.send(mimeMessage);

            System.out.println("Email sent successfully with attachment.");
        } catch (MessagingException e) {
            System.err.println("Error while sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Send Email Pdf (LMD_INVOICE)
    @Scheduled(cron = "0 */15 * * * ?")
    public void scheduledEmailPdf() {
        // Define source and destination directories
        Path sourceDir = Paths.get("/home/ubuntu/project/root/overc/invoice");
        Path destinationDir = Paths.get("/home/ubuntu/project/root/overc/send/invoice");

        try {
            // Ensure the destination directory exists
            if (!Files.exists(destinationDir)) {
                Files.createDirectories(destinationDir);
            }

            // Process all files in the source directory
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir, "*.pdf")) {
                for (Path file : stream) {
                    File pdfFile = file.toFile();

                    // Send email with the file as an attachment
                    MimeMessage mimeMessage = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                    helper.setFrom("no-reply@overc360.com");
                    helper.setTo("yogesh.m@tekclover.com");
                    helper.setSubject("Monthly shipment consolidated invoice - reg");
                    helper.setText("Dear,\n\nPlease find the attached monthly invoice - June. For your reference.");

                    FileSystemResource resource = new FileSystemResource(pdfFile);
                    helper.addAttachment(pdfFile.getName(), resource);

                    // Send the email
                    mailSender.send(mimeMessage);
                    System.out.println("Email sent successfully with attachment: " + pdfFile.getName());

                    // Move the file to the destination directory
                    Path destinationFile = destinationDir.resolve(pdfFile.getName());
                    Files.move(file, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("File moved to: " + destinationFile);

                    // Delete the original file (optional, as Files.move typically handles it)
                    if (Files.exists(file)) {
                        Files.delete(file);
                        System.out.println("Deleted original file: " + file);
                    }
                }
            }
        } catch (IOException | MessagingException e) {
            System.err.println("Error while processing files: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
