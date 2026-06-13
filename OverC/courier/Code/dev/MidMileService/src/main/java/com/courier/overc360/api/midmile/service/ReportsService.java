package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.customscosting.CustomCostingInvoice;
import com.courier.overc360.api.midmile.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.midmile.primary.model.reports.*;
import com.courier.overc360.api.midmile.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.midmile.primary.util.DateUtils;
import com.courier.overc360.api.midmile.replica.model.clearancecharges.ReplicaClearanceCharges;
import com.courier.overc360.api.midmile.replica.model.console.ConsoleImpl;
import com.courier.overc360.api.midmile.replica.model.dto.ConsignmentImpl;
import com.courier.overc360.api.midmile.replica.model.dto.CustomClearanceInvoiceReport;
import com.courier.overc360.api.midmile.replica.model.dto.FindInvoice;
import com.courier.overc360.api.midmile.replica.model.prealert.CustomsCalculationReport;
import com.courier.overc360.api.midmile.replica.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReportsService {

    @Autowired
    private ReplicaPreAlertRepository replicaPreAlertRepository;

    @Autowired
    private ReplicaUnconsolidationRepository replicaUnconsolidationRepository;

    @Autowired
    private ReplicaConsignmentEntityRepository replicaConsignmentEntityRepository;

    @Autowired
    private ReplicaConsoleRepository replicaConsoleRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private ReplicaClearanceChargesRepository clearanceChargesRepository;

    @Autowired
    private ReplicaCustomsCostingRepository replicaCustomsCostingRepository;

    DecimalFormat decimalFormat = new DecimalFormat("#.###");

    /*---------------------------------------------------------------------------------------------------------------*/

    /**
     * Get Mobile Dashboard
     *
     * @param mobileDashboardRequest
     * @return
     */
    public MobileDashboard getMobileDashboard(MobileDashboardRequest mobileDashboardRequest) {

        MobileDashboard mobileDashboard = new MobileDashboard();
        long consoleCount = replicaConsoleRepository.getMobileDashboardCount(
                mobileDashboardRequest.getLanguageId(), mobileDashboardRequest.getCompanyId(), "45");
        log.info("consoleCount --> {}", consoleCount);
        mobileDashboard.setConsoleCount(consoleCount);
        return mobileDashboard;
    }

//    /**
//     * Generate Location Sheet
//     *
//     * @param locationSheetInputList
//     * @param loginUserID
//     * @return
//     */
//    public List<LocationSheetOutput> generateLocationSheet(List<LocationSheetInput> locationSheetInputList, String loginUserID) {
//
//        Set<String> processedKeys = new HashSet<>();
//        List<LocationSheetOutput> createdLocationSheetOutputList = new ArrayList<>();
//
//        for (LocationSheetInput sheetInput : locationSheetInputList) {
//            String uniqueKey = generateUniqueKey(sheetInput);
//            if (processedKeys.contains(uniqueKey)) {
//                continue; // Skip this sheetInput if it has already been processed
//            }
//
//            if(sheetInput.getConsoleId() != null && !sheetInput.getConsoleId().isEmpty()) {
//                boolean consolesPresent = replicaConsoleRepository.existsByLanguageIdAndCompanyIdAndPartnerMasterAirwayBillAndConsoleIdAndDeletionIndicator(
//                        sheetInput.getLanguageId(), sheetInput.getCompanyId(), sheetInput.getPartnerMasterAirwayBill(),
//                        sheetInput.getConsoleId(), 0L);
//                if (!consolesPresent) {
//                    throw new BadRequestException("No console Data found for given inputs : " + sheetInput);
//                }
//            } else {
//                boolean consolePresent = replicaConsoleRepository.existsByLanguageIdAndCompanyIdAndPartnerMasterAirwayBillAndDeletionIndicator(
//                        sheetInput.getLanguageId(), sheetInput.getCompanyId(), sheetInput.getPartnerMasterAirwayBill(), 0L);
//                if (!consolePresent) {
//                    throw new BadRequestException("No console Data found for given inputs : " + sheetInput);
//                }
//            }
//
//            log.info("given Inputs to generate locationSheet --> {}", sheetInput);
//
////            LocationSheetOutput sheetOutput = new LocationSheetOutput();
////            BeanUtils.copyProperties(sheetInput, sheetOutput, CommonUtils.getNullPropertyNames(sheetInput));
//
//            // Get total Sum of NetWeight and TotalQuantity
//            List<ConsoleImpl> sumValues = replicaConsoleRepository.getSumValuesGroupedByConsoleId(sheetInput.getLanguageId(), sheetInput.getCompanyId(),
//                    sheetInput.getPartnerId(), sheetInput.getConsoleId(), sheetInput.getPartnerMasterAirwayBill());
//
//            sumValues.stream().forEach(sumValue -> {
//                LocationSheetOutput sheetOutput = new LocationSheetOutput();
//                sheetOutput.setTotalNoOfPieces(sumValue.getTotalQuantity());
//                sheetOutput.setTotalSumOfWeights(sumValue.getTotalQuantity());
//            });
//
////            if (sumValues != null) {
////                sheetOutput.setTotalNoOfPieces(sumValues.getTotalQuantity());
////                sheetOutput.setTotalSumOfWeights(sumValues.getTotalWeight());
////            }
//
//            // Get Location Sheet values
//            ConsoleImpl locationSheetValues = replicaConsoleRepository.getLocationSheetValues(sheetInput.getLanguageId(), sheetInput.getCompanyId(),
//                    sheetInput.getPartnerId(), sheetInput.getConsoleId(), sheetInput.getPartnerMasterAirwayBill());
//            if (locationSheetValues != null) {
//                sheetOutput.setLanguageDescription(locationSheetValues.getLangDesc());
//                sheetOutput.setCompanyName(locationSheetValues.getCompanyDesc());
//                sheetOutput.setPartnerName(locationSheetValues.getPartnerName());
//                sheetOutput.setPartnerType(locationSheetValues.getPartnerType());
//
//                sheetOutput.setOrigin(locationSheetValues.getOrigin());
////                sheetOutput.setConsigneeName(locationSheetValues.getConsigneeName());
//            }
//
//            sheetOutput.setNatureOfGoods("COURIER MATERIALS");
//            sheetOutput.setConsigneeName("IW EXPRESS");
//            sheetOutput.setLocationSheetTimeStamp(new Date());
//
//            createdLocationSheetOutputList.add(sheetOutput);
//            processedKeys.add(uniqueKey); // Mark this uniqueKey as processed
//            log.info("uniqueKey - {}", uniqueKey);
//        }
//
//        return createdLocationSheetOutputList;
//    }


    /**
     * Generate Location Sheet
     *
     * @param locationSheetInputList
     * @param loginUserID
     * @return
     */
    public List<LocationSheetOutput> generateLocationSheet(List<LocationSheetInput> locationSheetInputList, String loginUserID) {

        Set<String> processedKeys = new HashSet<>();
        List<LocationSheetOutput> createdLocationSheetOutputList = new ArrayList<>();

        for (LocationSheetInput sheetInput : locationSheetInputList) {
            String uniqueKey = generateUniqueKey(sheetInput);
            if (processedKeys.contains(uniqueKey)) {
                continue; // Skip this sheetInput if it has already been processed
            }

            if (sheetInput.getConsoleId() != null && !sheetInput.getConsoleId().isEmpty()) {
                boolean consolesPresent = replicaConsoleRepository.existsByLanguageIdAndCompanyIdAndPartnerMasterAirwayBillAndConsoleIdAndDeletionIndicator(
                        sheetInput.getLanguageId(), sheetInput.getCompanyId(), sheetInput.getPartnerMasterAirwayBill(),
                        sheetInput.getConsoleId(), 0L);
                if (!consolesPresent) {
                    throw new BadRequestException("No console Data found for given inputs : " + sheetInput);
                }
            } else {
                boolean consolePresent = replicaConsoleRepository.existsByLanguageIdAndCompanyIdAndPartnerMasterAirwayBillAndDeletionIndicator(
                        sheetInput.getLanguageId(), sheetInput.getCompanyId(), sheetInput.getPartnerMasterAirwayBill(), 0L);
                if (!consolePresent) {
                    throw new BadRequestException("No console Data found for given inputs : " + sheetInput);
                }
            }
            log.info("given Inputs to generate locationSheet --> {}", sheetInput);

            // Get total Sum of NetWeight and TotalQuantity
            List<ConsoleImpl> sumValues = replicaConsoleRepository.getSumValuesGroupedByConsoleId(sheetInput.getLanguageId(), sheetInput.getCompanyId(),
                    sheetInput.getPartnerId(), sheetInput.getConsoleId(), sheetInput.getPartnerMasterAirwayBill());

            sumValues.stream().forEach(sumValue -> {
                LocationSheetOutput sheetOutput = new LocationSheetOutput();
                sheetOutput.setLanguageId(sheetInput.getLanguageId());
                sheetOutput.setCompanyId(sheetInput.getCompanyId());
                sheetOutput.setPartnerMasterAirwayBill(sheetInput.getPartnerMasterAirwayBill());
                sheetOutput.setLocation(sheetInput.getLocation());
                sheetOutput.setPartnerId(sumValue.getPartnerId());
                sheetOutput.setConsoleId(sumValue.getConsoleId());
                sheetOutput.setMasterAirwayBill(sumValue.getMasterAirwayBill());
                sheetOutput.setTotalNoOfPieces(sumValue.getTotalQuantity());
                sheetOutput.setTotalSumOfWeights(sumValue.getTotalWeight());
                sheetOutput.setLanguageDescription(sumValue.getLangDesc());
                sheetOutput.setCompanyName(sumValue.getCompanyDesc());
                sheetOutput.setPartnerName(sumValue.getPartnerName());
                sheetOutput.setPartnerType(sumValue.getPartnerType());
                sheetOutput.setOrigin(sumValue.getOrigin());
                sheetOutput.setReferenceField1(sumValue.getReferenceField1());
                sheetOutput.setOriginFlightCountry(sumValue.getOriginFlightCountry());
                sheetOutput.setAirportOriginCode(sumValue.getAirportCode()); // airportOriginCode
                sheetOutput.setNatureOfGoods("COURIER MATERIALS");
                sheetOutput.setConsigneeName("IW EXPRESS");
                sheetOutput.setLocationSheetTimeStamp(new Date());
                createdLocationSheetOutputList.add(sheetOutput);
            });

            processedKeys.add(uniqueKey); // Mark this uniqueKey as processed
            log.info("uniqueKey - {}", uniqueKey);
        }

        return createdLocationSheetOutputList;
    }

    // GenerateUniqueKey
    private String generateUniqueKey(LocationSheetInput sheetInput) {
        String uniqueKey = "languageId-" + sheetInput.getLanguageId() + "-" + "companyId-" + sheetInput.getCompanyId()
                + "-" + "partnerId-" + sheetInput.getPartnerId() + "-" + "consoleId-" + sheetInput.getConsoleId()
                + "-" + "partnerMasterAirwayBill-" + sheetInput.getPartnerMasterAirwayBill();
        return uniqueKey;
    }

    private List<String> getNonNullStringList(List<String> stringList) {
        return stringList != null ? stringList : Collections.singletonList(null);
    }

    /**
     * Generate Console Tracking Report
     *
     * @param reportInput
     * @param loginUserID
     * @return
     */
    public List<ConsoleTrackingReportOutput> generateConsoleTracking(ConsoleTrackingReportInput reportInput,
                                                                     String loginUserID) throws ParseException {
        Instant startTime = Instant.now();
        List<ConsoleTrackingReportOutput> createdConsoleTrackingOutputList = new ArrayList<>();

        if (reportInput.getLanguageId() == null || reportInput.getCompanyId() == null) {
            throw new BadRequestException("LanguageId or companyId cannot be null");
        }

        List<String> langIdList = getNonNullStringList(reportInput.getLanguageId());
        List<String> cIdList = getNonNullStringList(reportInput.getCompanyId());
//        List<String> pIdList = getNonNullStringList(reportInput.getPartnerId());
        List<String> pMawbList = getNonNullStringList(reportInput.getPartnerMasterAirwayBill());
        List<String> pHawbList = getNonNullStringList(reportInput.getPartnerHouseAirwayBill());

        if (reportInput.getFromDate() != null && reportInput.getToDate() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearchV2(reportInput.getFromDate(), reportInput.getToDate());
            reportInput.setFromDate(dates[0]);
            reportInput.setToDate(dates[1]);
        }
        log.info("given Inputs to generate ConsoleTracking --> {}", reportInput);

        if ((reportInput.getLanguageId() != null && reportInput.getCompanyId() != null) &&
                (reportInput.getPartnerMasterAirwayBill() == null && reportInput.getPartnerHouseAirwayBill() == null)) {

            createdConsoleTrackingOutputList = postConsoleTrackingListPage(langIdList, cIdList, loginUserID);

        } else {

            for (String langId : langIdList) {
                for (String cId : cIdList) {
//                    for (String pId : pIdList) {
                    for (String pMawb : pMawbList) {
                        for (String pHawb : pHawbList) {

                            long noOfShipments = replicaPreAlertRepository.getNoOfShipmentsScanned(
                                    langId, cId, pMawb, pHawb,
                                    reportInput.getFromDate(), reportInput.getToDate());

                            long noOfConsoles = replicaConsoleRepository.getNoOfConsoles(
                                    langId, cId, pMawb, pHawb, 0L,
                                    reportInput.getFromDate(), reportInput.getToDate());

                            long noOfUnconsolidatedShipments = replicaUnconsolidationRepository.getNoOfUnconsolidatedShipments(
                                    langId, cId, pMawb, pHawb,
                                    reportInput.getFromDate(), reportInput.getToDate());

                            ConsoleImpl scanValues = replicaConsoleRepository.getScanValues(langId, cId, pMawb, pHawb);

                            if (noOfShipments != 0 || noOfConsoles != 0 || noOfUnconsolidatedShipments != 0) {

                                ConsoleTrackingReportOutput reportOutput = new ConsoleTrackingReportOutput();

                                reportOutput.setLanguageId(langId);
                                reportOutput.setCompanyId(cId);
//                                    reportOutput.setPartnerId(pId);
                                reportOutput.setPartnerMasterAirwayBill(pMawb);
                                reportOutput.setPartnerHouseAirwayBill(pHawb);

                                log.info("No Of Shipments Scanned --> {}", noOfShipments);
                                reportOutput.setNoOfShipmentsScanned(noOfShipments);

                                log.info("No Of Consoles --> {}", noOfConsoles);
                                reportOutput.setNoOfConsoles(noOfConsoles);

                                log.info("No Of Unconsolidated Shipments --> {}", noOfUnconsolidatedShipments);
                                reportOutput.setNoOfUnconsolidatedShipments(noOfUnconsolidatedShipments);

                                if (scanValues != null) {
                                    reportOutput.setScanningOfficer(scanValues.getScannedBy());
                                    reportOutput.setScanningDate(scanValues.getScannedOn());
                                }

                                createdConsoleTrackingOutputList.add(reportOutput);
                            }
                        }
                    }
//                    }
                }
            }
        }
        Instant endTime = Instant.now();
        log.info("Time to load ConsoleTracking --> {}ms", Duration.between(startTime, endTime).toMillis());
        return createdConsoleTrackingOutputList;
    }

    public List<ConsoleTrackingReportOutput> postConsoleTrackingListPage(List<String> languageIdList, List<String> companyIdList,
                                                                         String loginUserID) throws ParseException {
        List<ConsoleTrackingReportOutput> createdConsoleTrackingOutputList = new ArrayList<>();

        List<ConsignmentImpl> allPMawbValuesList = replicaPreAlertRepository.getAllPMawbCount(languageIdList, companyIdList);
        if (allPMawbValuesList != null && !allPMawbValuesList.isEmpty()) {

            List<Object[]> consoleCountResults = replicaConsoleRepository.getConsoleCountByPMawb(languageIdList, companyIdList);
            List<Object[]> unconsolidatedCountResults = replicaUnconsolidationRepository.getUnconsolidatedCountByPMawb(languageIdList, companyIdList);

            Map<String, Long> consoleCountMap = convertToMap(consoleCountResults);
            Map<String, Long> unconsolidatedCountMap = convertToMap(unconsolidatedCountResults);

            createdConsoleTrackingOutputList = allPMawbValuesList
                    .stream()
                    .map(pMawbValues -> {
                        String pMawb = pMawbValues.getPartnerMasterAirwayBill();
                        String key = pMawb + "_" + pMawbValues.getLanguageId() + "_" + pMawbValues.getCompanyId();
                        long consolesCount = consoleCountMap.getOrDefault(key, 0L);
                        long unconsolidatedCount = unconsolidatedCountMap.getOrDefault(key, 0L);

                        ConsoleTrackingReportOutput reportOutput = new ConsoleTrackingReportOutput();
                        reportOutput.setLanguageId(pMawbValues.getLanguageId());
                        reportOutput.setCompanyId(pMawbValues.getCompanyId());
                        reportOutput.setPartnerMasterAirwayBill(pMawb);
                        reportOutput.setNoOfShipmentsScanned(pMawbValues.getPMawbCount());
                        reportOutput.setNoOfConsoles(consolesCount);
                        reportOutput.setNoOfUnconsolidatedShipments(unconsolidatedCount);

                        return reportOutput;
                    })
                    .collect(Collectors.toList());
        }
        return createdConsoleTrackingOutputList;
    }

    private Map<String, Long> convertToMap(List<Object[]> results) {
        return results
                .stream()
                .collect(Collectors.toMap(
                                result -> result[0] + "_" + result[1] + "_" + result[2],
                                result -> ((Number) result[3]).longValue()
                        )
                );
    }

    // Customs Calculation Report
    public List<CustomsCalculationReport> customsCalculationReportList() {
        return replicaPreAlertRepository.getCalculatedCount();
    }


    //============================================Reports_ErrorLog=====================================================
    private void createReportLog(List<LocationSheetInput> locationSheetInputs, String error) {

//        List<ErrorLog> errorLogList = new ArrayList<>();
        for (LocationSheetInput sheetInput : locationSheetInputs) {
            ErrorLog errorLog = new ErrorLog();

            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(sheetInput.getLanguageId());
            errorLog.setCompanyId(sheetInput.getCompanyId());
            errorLog.setRefDocNumber(sheetInput.getPartnerMasterAirwayBill());
            errorLog.setMethod("Exception thrown in generateLocationSheet");
            errorLog.setReferenceField1(sheetInput.getConsoleId());
            errorLog.setReferenceField2(sheetInput.getLocation());
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
//            errorLogList.add(errorLog);
        }
//        errorLogService.writeLog(errorLogList);
    }


    /**
     * CustomClearanceInvoice
     *
     * @param findInvoice
     * @return
     */
    public List<CustomClearanceInvoiceReport> customClearanceInvoices(FindInvoice findInvoice) {

        List<CustomClearanceInvoiceReport> customClearanceInvoicesList = new CopyOnWriteArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);             // Adjust Thread Pool Size
        try {
            if (findInvoice.getFromDate() != null && findInvoice.getToDate() != null) {
                Date[] dates = DateUtils.addTimeToDatesForSearchV2(findInvoice.getFromDate(), findInvoice.getToDate());
                findInvoice.setFromDate(dates[0]);
                findInvoice.setToDate(dates[1]);
            }

            List<IKeyValuePair> partnerMasterAirwayBill = replicaPreAlertRepository.getPartnerMaster(
                    findInvoice.getFromDate(), findInvoice.getToDate(), findInvoice.getPartnerMasterAirwayBill(),
                    findInvoice.getPartnerId());

            if(partnerMasterAirwayBill.isEmpty()){
                throw new BadRequestException("No DDP shipments found for PartnerMasterAirwayBill" + findInvoice.getPartnerMasterAirwayBill());
            }

            List<CompletableFuture<CustomClearanceInvoiceReport>> futures = partnerMasterAirwayBill.stream()
                    .map(ikey -> CompletableFuture.supplyAsync(() -> {

                        CustomClearanceInvoiceReport customClearanceInvoice = new CustomClearanceInvoiceReport();
                        customClearanceInvoice.setPartnerMasterAirwayBill(ikey.getPartnerMasterAB());
                        customClearanceInvoice.setNoOfShipments(ikey.getShipment());

                        List<ReplicaClearanceCharges> clearanceCharges = clearanceChargesRepository.getClearance(
                                ikey.getCompanyId(), ikey.getLangId(), findInvoice.getPartnerId());

                        if(clearanceCharges.isEmpty()){
                            throw new BadRequestException("Clearance Charge not maintained in masters CompanyId - " +ikey.getCompanyId() +
                                    "LanguageId -" + ikey.getLangId() + "PartnerId - " + findInvoice.getPartnerId());
                        }

                        // Filter clearanceCharges where ikey.getShipment() is between noOfShipmentsFrom and noOfShipmentsTo
                        clearanceCharges.stream()
                                .filter(i -> {
                                    int shipment = Math.toIntExact(ikey.getShipment());
                                    int fromShipment = Integer.parseInt(i.getNoOfShipmentsFrom());
                                    int toShipment = Integer.parseInt(i.getNoOfShipmentsTo());
                                    return shipment >= fromShipment && shipment <= toShipment;
                                })
                                .forEach(filteredCharge -> {
                                    if (filteredCharge.getClearanceCharges() != null) {
                                        customClearanceInvoice.setClearanceCharge(Double.valueOf(filteredCharge.getClearanceCharges()));
                                    } else {
                                        String formula = filteredCharge.getFormulaField1();
                                        String minimumCharge = filteredCharge.getAddMinCharge();

                                        // Parse and calculate the formula
                                        double calculatedValue = parseAndCalculateFormula(formula, ikey.getShipment(), minimumCharge);
                                        customClearanceInvoice.setClearanceCharge(calculatedValue);
                                    }

                                });
                        List<CustomCostingInvoice> custom = replicaCustomsCostingRepository.getCustom(customClearanceInvoice.getPartnerMasterAirwayBill());
                        custom.stream().forEach(dec -> {
//                            String costDescription = Normalizer.normalize(dec.getCostDescription(), Normalizer.Form.NFD)
//                                    .replaceAll("[^\\p{ASCII}]", "")
//                                    .trim();

                            if(dec.getCostDescription() != null) {
                            String costDescription = dec.getCostDescription();
                            if (costDescription.equalsIgnoreCase("FoodApprovals")) {
                                customClearanceInvoice.setFoodApproval(dec.getCostAmount());
                            } else if (costDescription.equalsIgnoreCase("Approval")) {
                                customClearanceInvoice.setApprovals(dec.getCostAmount());
                            } else if (costDescription.equalsIgnoreCase("HandlingFees")) {
                                customClearanceInvoice.setHandlingFees(dec.getCostAmount());
                            } else if (costDescription.equalsIgnoreCase("OtherApprovals")) {
                                customClearanceInvoice.setOtherApproval(dec.getCostAmount());
//                            } else if (costDescription.equalsIgnoreCase("CustomDuty")) {
//                                customClearanceInvoice.setCustomDuty(dec.getCostAmount());
                            } else if (costDescription.equalsIgnoreCase("SpecialApprovals")) {
                                customClearanceInvoice.setSpecialApproval(dec.getCostAmount());
                            }
                        }});

                        Double clearCharge = customClearanceInvoice.getClearanceCharge();
//                        Double handlingFees = customClearanceInvoice.getHandlingFees();
//                        Double customDuty = customClearanceInvoice.getCustomDuty();
                        Double approvals = customClearanceInvoice.getApprovals();
                        Double specialApproval = customClearanceInvoice.getSpecialApproval();
                        Double foodApproval = customClearanceInvoice.getFoodApproval();
                        Double otherApproval = customClearanceInvoice.getOtherApproval();

                        double totalApproval = ((approvals != null ? approvals : 0.0) +
                                (foodApproval != null ? foodApproval : 0.0) +
                                (specialApproval != null ? specialApproval : 0.0) +
                                (otherApproval != null ? otherApproval : 0.0));

                        // Handling Fees
                        if(totalApproval != 0.0) {
                            String handlingFee = replicaCustomsCostingRepository.clearanceCharges(ikey.getLangId(), ikey.getCompanyId());

                            if (customClearanceInvoice.getHandlingFees() == null && handlingFee != null
                                    && !handlingFee.isEmpty() && !handlingFee.equalsIgnoreCase("String")) {
                                customClearanceInvoice.setHandlingFees(Double.valueOf(handlingFee));
                            } else {
                                customClearanceInvoice.setHandlingFees(5D);
                            }
                        } else {
                            customClearanceInvoice.setHandlingFees(0.0);
                        }

                        // Custom_duty
                        Double customDuty = replicaConsoleRepository.getTotalDuty(ikey.getPartnerMasterAB());
                        if(customDuty != null) {
                            String formatCustomDuty = decimalFormat.format(customDuty);
                            customClearanceInvoice.setCustomDuty(Double.valueOf(formatCustomDuty));
                        }

                        // TotalValue
                        double totalValue = (clearCharge != null ? clearCharge : 0.0) +
                                (totalApproval != 0.0 ? totalApproval : 0.0) +
                                (customClearanceInvoice.getHandlingFees() != null ? customClearanceInvoice.getHandlingFees() : 0.0) +
                                (customDuty != null ? customDuty : 0.0);

                        double cps = totalValue - ((customDuty != null ? customDuty : 0.0) +
                                (specialApproval != null ? specialApproval : 0.0));

                        double costPerShipment = cps / customClearanceInvoice.getNoOfShipments();
                        String formatConsignmentValue = decimalFormat.format(costPerShipment);


                        String tv = decimalFormat.format(totalValue);

                        customClearanceInvoice.setTotalApproval(totalApproval);
                        customClearanceInvoice.setCostPerShipment(formatConsignmentValue);
                        customClearanceInvoice.setTotalValue(Double.valueOf(tv));
                        return customClearanceInvoice;
                    }, executorService))
                    .toList();

            // Collect all the results
            customClearanceInvoicesList = futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }

        return customClearanceInvoicesList;
    }

    /**
     * Calculation Formula
     *
     * @param formula
     * @param noOfShipments
     * @param minimumCharge
     * @return
     */
    public double parseAndCalculateFormula(String formula, Long noOfShipments, String minimumCharge) {
        // Replace placeholders with actual values
        formula = formula.replaceAll("(?i)No Of Shipment", noOfShipments.toString());
        formula = formula.replaceAll("(?i)noofShipments", noOfShipments.toString());

        if (minimumCharge != null) {
            formula = formula.replaceAll("(?i)minimum Charges", minimumCharge);
            formula = formula.replaceAll("(?i)Minimum Charge", minimumCharge);
        }

        // Evaluate the formula using mxparser
        Expression expression = new Expression(formula);
        return expression.calculate();
    }

    /**
     * @param costDescription
     * @return
     */
    private String normalizeCostDescription(String costDescription) {

        String normalized = Normalizer.normalize(costDescription, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .trim()
                .toLowerCase();

//        normalized = normalized.replaceAll("\\s*-\\s*", "-").replaceAll("\\s+", " ");
        return normalized;
    }


    /**
     * Hub Ops Count
     *
     * @param mobileDashboardRequest
     * @return
     */
    public HubCountResponse getHubScanAndReceiveCount(MobileDashboardRequest mobileDashboardRequest) {
//        Long inScan = replicaConsignmentEntityRepository.getInscanCount(mobileDashboardRequest.getCompanyId(),mobileDashboardRequest.getLanguageId());
        Long storage = replicaConsignmentEntityRepository.getReceiveCount(mobileDashboardRequest.getCompanyId(),mobileDashboardRequest.getLanguageId());
        Long outScan = replicaConsignmentEntityRepository.getReceiveCountOutScan(mobileDashboardRequest.getCompanyId(), mobileDashboardRequest.getLanguageId());
        HubCountResponse hubCountResponse = new HubCountResponse();
//        hubCountResponse.setInScan(inScan);
        hubCountResponse.setStorage(storage);
        hubCountResponse.setOutScan(outScan);
        return hubCountResponse;
    }

}