package com.courier.overc360.api.common.controller;

import com.courier.overc360.api.common.config.PropertiesConfig;
import com.courier.overc360.api.common.model.pdf.InvoicePDFMerger;
import com.courier.overc360.api.common.service.InvoicePdfMergeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;

@RestController
@RequestMapping("/api/invoice/pdf")
public class InvoicePdfMergeController {

    private static final Logger log = LoggerFactory.getLogger(InvoicePdfMergeController.class);
    @Autowired
    private InvoicePdfMergeService invoicePdfMergeService;

    @PostMapping("/merge")
    public ResponseEntity<byte[]> mergeInvoicePdf(@RequestBody InvoicePDFMerger invoicePDFMerger) {
        try {
            validatePdfMergerRequest(invoicePDFMerger);

            byte[] mergedPdf = invoicePdfMergeService.mergePdfFiles(invoicePDFMerger.getPath(), invoicePDFMerger.getOutputPath(), invoicePDFMerger.getLineNo());
//            log.info("mergedPdf ----> {}", mergedPdf);
            String outputPath = invoicePDFMerger.getOutputPath();
//            log.info("outputPath -----> {}", outputPath);
            String fileName = outputPath.substring(outputPath.lastIndexOf("/") + 1);
//            log.info("fileName ----> {}", fileName);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(mergedPdf);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage().getBytes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage().getBytes());
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
}