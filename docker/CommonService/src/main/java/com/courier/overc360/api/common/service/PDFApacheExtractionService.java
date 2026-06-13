package com.courier.overc360.api.common.service;

import com.courier.overc360.api.common.config.PropertiesConfig;
import com.courier.overc360.api.common.controller.exception.BadRequestException;
import com.courier.overc360.api.common.model.pdf.UpdateCCR;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PDFApacheExtractionService {

    @Autowired
    PropertiesConfig propertiesConfig;

    @Autowired
    MidMileService midMileService;
    /**
     * old method - it will save the result in text file
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public List<String> extractDetailsFromPdf(String filePath) throws IOException {
        List<String> detailsList = new ArrayList<>();
        filePath = propertiesConfig.getDocStorageBasePath() + filePath;
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setSortByPosition(true);
            // Iterate through each page
            for (int page = 1; page <= document.getNumberOfPages(); page++) {
                List<String> ccrFoundList = new ArrayList<>();
                pdfStripper.setStartPage(page);
                pdfStripper.setEndPage(page);
                String pageText = pdfStripper.getText(document);
                // Extract CCR and table details
                String[] lines = pageText.split("\\r?\\n");
                String ccrValue = "";
                for (String line : lines) {
                    if (line.contains("CCR/")) {
                        ccrValue = extractCCRNumber(line);
//                        ccrFound = true;
                        ccrFoundList.add("True");
                    }
                    if ((ccrFoundList.size() == 1) && line.matches(".*\\d{4}\\.\\d{2}\\.\\d{2}.*")) {
                        String details = extractDetailsFromLine(line);
                        detailsList.add("CCRno - " + ccrValue + " , " + details);
                    }
                }
                if (ccrFoundList.size() == 1) {
                    pdfStripper.setStartPage(page + 1);
                    pdfStripper.setEndPage(page + 1);
                    String pageContinuousText = pdfStripper.getText(document);
                    // Extract CCR and table details
                    String[] pageContinuousLines = pageContinuousText.split("\\r?\\n");
                    for (String line : pageContinuousLines) {
                        if (line.contains("CCR/")) {
                            ccrFoundList.add("True");
                        }
                        if (ccrFoundList.size() == 2) {
                            break;
                        }
                        if ((ccrFoundList.size() == 1) && line.matches(".*\\d{4}\\.\\d{2}\\.\\d{2}.*")) {
                            String details = extractDetailsFromLine(line);
                            detailsList.add("CCRno - " + ccrValue + " , " + details);
                        }
                    }
                }
            }
        }
        FileWriter fileWriter = new FileWriter("sampleTextFile.txt");
        for (String str : detailsList) {
            fileWriter.write(str + System.lineSeparator());
        }
        fileWriter.close();
        return detailsList;
    }
    /**
     * @param line
     * @return
     */
    private String extractCCRNumber(String line) {
        // Extract the CCR number from the line (Assuming it follows "CCR/")
        int index = line.indexOf("CCR/");
        if (index != -1) {
            String ccrPart = line.substring(index).trim();
            String[] parts = ccrPart.split("\\s+");
            return parts[0]; // Assuming the first part after "CCR/" is the CCR number
        }
        return null;
    }
    /**
     * @param line
     * @return
     */
    private String extractDONumber(String line) {
        int index = line.indexOf("DO/");
        if (index != -1) {
            String doPart = line.substring(index).trim();
            String[] parts = doPart.split("\\s+");
            return parts[0]; // DO Number
        }
        return null;
    }
    /**
     * @param line
     * @return
     */
    private String extractOverCCCRNumber(String line) {
        if (line.contains("Discharge")) {
            int index = line.indexOf("Discharge");
            if (index != -1) {
                String ccrPart = line.substring(index).trim();
                String[] parts = ccrPart.split("\\s+");
                if (parts.length > 7 ? parts[7].contains("-") : false) {
                    String ccrNumber = parts[6];
                    return ccrNumber; // Our CCR Number
                }
            }
        }
        int partCount = 0;
        int ccrNoCount = 0;
        String ccrPart = line.trim();
        String[] parts = ccrPart.split("\\s+");
        Long dashCount = Arrays.stream(parts).filter(n -> n.contains("-")).count();
        if (!line.contains("Discharge") && line.contains("-") && dashCount == 2L) {
            for (String part : parts) {
                if (part.contains("-")) {
                    ccrNoCount = partCount - 1;
                }
                partCount++;
            }
            String ccrNumber = parts[ccrNoCount];
            return ccrNumber; // Our CCR Number
        }
        return null;
    }
    /**
     * @param line
     * @return
     */
    private String extractDetailsFromLine(String line) {
        // Extract relevant details from the line
        String[] parts = line.split("\\s+");
        String totalDuty = parts.length > 0 ? parts[0] : "-";
        String dRate = parts.length > 1 ? parts[1] : "-";
        String cifLocalValue = parts.length > 2 ? parts[2] : "-";
        String rate = parts.length > 3 ? parts[3] : "-";
        String type = parts.length > 4 ? parts[4] : "-";
        String foreignValue = parts.length > 5 ? parts[5] : "-";
        String goodsDescription = parts.length > 6 ? parts[6] : "-";
        String hsCode = parts.length > 7 ? parts[parts.length - 1] : "-";
//        String hsCode = parts.length > 7 ? extractHSCode(parts[7]) : "-";
        return String.format("TotalDuty - %s D.Rate - %s CIF Local Value - %s Rate - %s Type - %s ForeignValue - %s GoodsDescription - %s HSCode - %s",
                totalDuty, dRate, cifLocalValue, rate, type, foreignValue, goodsDescription, hsCode);
    }
    /**
     * @param input
     * @return
     */
    private String extractHSCode(String input) {
        Pattern pattern = Pattern.compile("\\d{4}\\.\\d{2}\\.\\d{2}");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group();
        }
        return "-";
    }
    /**
     * @param line
     * @return
     */
    private String[] extractDetailsFromLines(String line) {
        // Extract relevant details from the line
        String[] parts = line.split("\\s+");
        String totalDuty = parts.length > 0 ? parts[0] : null;
        String dRate = parts.length > 1 ? parts[1] : "-";
        String cifLocalValue = parts.length > 2 ? parts[2] : "-";
        String rate = parts.length > 3 ? parts[3] : "-";
        String type = parts.length > 4 ? parts[4] : "-";
        String foreignValue = parts.length > 5 ? parts[5] : "-";
        String goodsDescription = parts.length > 6 ? parts[6] : "-";
        String hsCode = parts.length > 7 ? parts[parts.length - 1] : "-";
        String[] dataExtracted = null;
        if (totalDuty != null && !totalDuty.contains("-")) {
            dataExtracted = new String[]{cifLocalValue, foreignValue, hsCode, totalDuty};
        } else {
            dataExtracted = new String[]{cifLocalValue, foreignValue, hsCode };
        }
        return dataExtracted;
    }
    /**
     * @param filePath
     * @return
     * @throws IOException
     */
    public Set<UpdateCCR> pdfExtractDetails(String filePath) throws IOException {
        filePath = propertiesConfig.getDocStorageBasePath() + filePath;
        List<UpdateCCR> updateCCRList = new ArrayList<>();
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setSortByPosition(true);
            // Iterate through each page
            for (int page = 1; page <= document.getNumberOfPages(); page++) {
                List<String> ccrFoundList = new ArrayList<>();
                List<String> doNoFoundList = new ArrayList<>();
                List<String> ourCcrFoundList = new ArrayList<>();
                List<String> detailLineList = new ArrayList<>();
                UpdateCCR updateCCR = new UpdateCCR();
                pdfStripper.setStartPage(page);
                pdfStripper.setEndPage(page);
                String pageText = pdfStripper.getText(document);
                // Extract CCR and table details
                String[] lines = pageText.split("\\r?\\n");
                String ccrValue = "";
                String deliveryOrderNo = "";
                String ourCCRNumber = "";
                int lineCount = 0;
                int ccrLineNo = 0;
                for (String line : lines) {
                    lineCount++;
                    if (detailLineList.size() == 0 && line.contains("DO/")) {
                        deliveryOrderNo = extractDONumber(line);
                        doNoFoundList.add("True");
                        updateCCR.setPrimaryDo(deliveryOrderNo);
                    }
                    if (line.contains("CCR/")) {
                        ccrValue = extractCCRNumber(line);
                        ccrFoundList.add("True");
                        updateCCR.setCustomsCcrNo(ccrValue);
                        ccrLineNo = lineCount;
                        innerloop:
                        if (doNoFoundList.size() == 0) {
                            PDFTextStripper pdfStripperPreviousPage = new PDFTextStripper();
                            pdfStripperPreviousPage.setSortByPosition(true);
                            pdfStripperPreviousPage.setStartPage(page - 1);
                            pdfStripperPreviousPage.setEndPage(page - 1);
                            String pagePreviousText = pdfStripperPreviousPage.getText(document);
                            // Extract DO
                            String[] pagePreviousLines = pagePreviousText.split("\\r?\\n");
                            for (String pline : pagePreviousLines) {
                                if (detailLineList.size() == 0 && pline.contains("DO/")) {
                                    deliveryOrderNo = extractDONumber(pline);
                                    updateCCR.setPrimaryDo(deliveryOrderNo);
                                    doNoFoundList.add("True");
                                }
                                if (doNoFoundList.size() == 1) {
                                    break innerloop;
                                }
                            }
                        }
                    }
                    boolean pass = lineCount == (ccrLineNo + 1);    //check next line of CCR to fetch our CCR Number
                    if (ccrFoundList.size() == 1 && pass) {
                        ourCCRNumber = extractOverCCCRNumber(line);
                        ourCcrFoundList.add("True");
                        updateCCR.setCcrId(ourCCRNumber);
                    }
                    if (doNoFoundList.size() == 1 && ccrFoundList.size() == 1 && ourCcrFoundList.size() == 1 && line.matches(".*\\d{4}\\.\\d{2}\\.\\d{2}.*")) {
                        String[] details = extractDetailsFromLines(line);
                        updateCCR.setCustomsKd(details[0]);
                        updateCCR.setConsignmentValue(details[1]);
                        updateCCR.setHsCode(details[2]);
                        if (details.length > 3) {
                            updateCCR.setTotalDuty(details[3]);
                        }
                        detailLineList.add("True");
                        if (updateCCR.getCcrId() != null && updateCCR.getCustomsCcrNo() != null && updateCCR.getCustomsKd() != null &&
                                updateCCR.getHsCode() != null && updateCCR.getPrimaryDo() != null && updateCCR.getConsignmentValue() != null) {
                            updateCCRList.add(updateCCR);
                            updateCCR = new UpdateCCR();
                            updateCCR.setPrimaryDo(deliveryOrderNo);
                            updateCCR.setCustomsCcrNo(ccrValue);
                            updateCCR.setCcrId(ourCCRNumber);
                        }
                    }
                }
                if (ccrFoundList.size() == 1) {
                    pdfStripper.setStartPage(page + 1);
                    pdfStripper.setEndPage(page + 1);
                    String pageContinuousText = pdfStripper.getText(document);
                    // Extract CCR and table details
                    String[] pageContinuousLines = pageContinuousText.split("\\r?\\n");
                    for (String line : pageContinuousLines) {
                        if (line.contains("CCR/")) {
                            ccrFoundList.add("True");
                        }
                        if (ccrFoundList.size() == 2) {
                            break;
                        }
                        if ((ccrFoundList.size() == 1) && line.matches(".*\\d{4}\\.\\d{2}\\.\\d{2}.*")) {
                            String[] details = extractDetailsFromLines(line);
                            updateCCR.setCustomsKd(details[0]);
                            updateCCR.setConsignmentValue(details[1]);
                            updateCCR.setHsCode(details[2]);
                            if (details.length > 3) {
                                updateCCR.setTotalDuty(details[3]);
                            }
                            if (updateCCR.getCcrId() != null && updateCCR.getCustomsCcrNo() != null && updateCCR.getCustomsKd() != null &&
                                    updateCCR.getHsCode() != null && updateCCR.getPrimaryDo() != null && updateCCR.getConsignmentValue() != null) {
                                updateCCRList.add(updateCCR);
                                updateCCR = new UpdateCCR();
                                updateCCR.setPrimaryDo(deliveryOrderNo);
                                updateCCR.setCustomsCcrNo(ccrValue);
                                updateCCR.setCcrId(ourCCRNumber);
                            }
                        }
                    }
                }
            }
        }
        log.info("UpdateCCR List : " + updateCCRList);
        Set<UpdateCCR> uniqueCcrList = updateCCRList.stream().collect(Collectors.toSet());
        log.info("Unique CCR List : " + uniqueCcrList);
        try {
            midMileService.updateCCRFromPdf(uniqueCcrList);
            return uniqueCcrList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception while updating CCR with pdf values");
        }
    }
}
