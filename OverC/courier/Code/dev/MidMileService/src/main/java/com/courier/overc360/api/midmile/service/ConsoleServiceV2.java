package com.courier.overc360.api.midmile.service;


import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.console.Console;
import com.courier.overc360.api.midmile.primary.model.prealert.PreAlert;
import com.courier.overc360.api.midmile.primary.repository.*;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.repository.*;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ConsoleServiceV2 {

    @Autowired
    private ConsoleRepository consoleRepository;

    @Autowired
    private CcrRepository ccrRepository;

    @Autowired
    private ReplicaConsoleRepository replicaConsoleRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private ReplicaBondedManifestRepository replicaBondedManifestRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private CcrService ccrService;

    @Autowired
    private ConsignmentEntityRepository consignmentEntityRepository;

    @Autowired
    ConsignmentStatusService consignmentStatusService;

    @Autowired
    ReplicaPieceDetailsRepository replicaPieceDetailsRepository;

    @Autowired
    ReplicaCcrRepository replicaCcrRepository;

    @Autowired
    ReplicaConsignmentStatusRepository replicaConsignmentStatusRepository;

    @Autowired
    private UnconsolidationService unconsolidationService;

    @Autowired
    private CustomsClearanceInvoiceService customsClearanceInvoiceService;

    @Autowired
    PushNotificationService pushNotificationService;

    @Autowired
    CustomsCostingService customsCostingService;

    @Autowired
    CustomsCostingRepository customsCostingRepository;

    @Autowired
    ConsoleService consoleService;

    private Set<String> processedConsoleCreate = new HashSet<>();


    //public List<Console> createConsoleBasedOnPreAlertResponse(List<PreAlert> preAlerts, String loginUserID)
    //            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
    //
    //        List<CompletableFuture<Void>> futures = new ArrayList<>();
    //
    //        List<Console> consoleList = new CopyOnWriteArrayList<>();
    //        ExecutorService executor = Executors.newFixedThreadPool(10);
    //
    //        // Group by PartnerMasterAirwayBill
    //        Map<String, List<PreAlert>> groupedPreAlert = preAlerts.stream()
    //                        .collect(Collectors.groupingBy(PreAlert::getPartnerMasterAirwayBill));
    //
    //        groupedPreAlert.forEach((partnerMasterAirwayBill, groupedAlerts) -> {
    //            groupedAlerts.forEach(preAlert -> {
    //                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
    //
    //                    try {
    //                        Console newConsole = new Console();
    //                        BeanUtils.copyProperties(preAlert, newConsole, CommonUtils.getNullPropertyNames(preAlert));
    //                        // Set weights and quantities
    //                        newConsole.setTareWeight(preAlert.getTotalWeight());
    //                        newConsole.setGrossWeight(preAlert.getTotalWeight());
    //                        newConsole.setManifestedGrossWeight(preAlert.getTotalWeight());
    //                        newConsole.setNetWeight(preAlert.getTotalWeight());
    //                        newConsole.setManifestedQuantity(preAlert.getNoOfPieces());
    //                        newConsole.setLandedQuantity(preAlert.getNoOfPieces());
    //                        newConsole.setTotalQuantity(preAlert.getNoOfPieces());
    //                        newConsole.setCustomsCurrency(preAlert.getCurrency());
    //                        newConsole.setGoodsDescription(preAlert.getDescription());
    //                        newConsole.setShipperName(preAlert.getShipper());
    //                        newConsole.setInvoiceDate(preAlert.getEstimatedTimeOfArrival());
    //                        newConsole.setAirportOriginCode(preAlert.getOrigin());
    //                        newConsole.setCountryOfOrigin(preAlert.getOriginCode());
    //
    //                        // Add to the thread-safe list
    //                        consoleList.add(newConsole);
    //                    } catch (Exception e) {
    //                    e.printStackTrace();
    //                    }
    //                }, executor);
    //            });
    //        });
    //
    //        // Wait for all the futures to complete
    //        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    //        // Shut down the executor service
    //        executor.shutdown();
    //
    //        long count = consoleList.size();
    //        IKeyValuePair shipment = replicaConsoleRepository.getShipment(consoleList.get(0).getCompanyId(), consoleList.get(0).getLanguageId());
    //        int noOfShipment = (shipment.getShipment() != null && shipment.getShipment() != 0) ? Math.toIntExact(shipment.getShipment()) : 99;
    //        log.info("Console Create Based on <------------------ >Count " + count + " Shipment " + shipment.getShipment());
    //
    //        if (count <= noOfShipment) {
    //            return processNoOfCountLessThenNoOfShipment(consoleList, loginUserID);
    //        } else {
    //            return createConsoleList(consoleList, loginUserID);
    //        }
    //    }

    /**
     * Console Create
     *
     * @param preAlerts
     * @param loginUserID
     * @return
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws CsvException
     */
    public List<Console> createConsoleBasedOnPreAlertResponse(List<PreAlert> preAlerts, String loginUserID)
            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {

        List<Console> finalConsoleList = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Group by PartnerMasterAirwayBill
        Map<String, List<PreAlert>> groupedPreAlert = preAlerts.stream()
                .collect(Collectors.groupingBy(PreAlert::getPartnerMasterAirwayBill));

        for (Map.Entry<String, List<PreAlert>> entry : groupedPreAlert.entrySet()) {
            String partnerMasterAirwayBill = entry.getKey();
            List<PreAlert> groupedAlerts = entry.getValue();

            // Create a new console list and future list for each group
            List<Console> consoleList = new CopyOnWriteArrayList<>();
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            // Process each PreAlert
            groupedAlerts.forEach(preAlert -> {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        Console newConsole = new Console();
                        BeanUtils.copyProperties(preAlert, newConsole, CommonUtils.getNullPropertyNames(preAlert));

                        // Set weights and quantities
                        newConsole.setTareWeight(preAlert.getTotalWeight());
                        newConsole.setGrossWeight(preAlert.getTotalWeight());
                        newConsole.setManifestedGrossWeight(preAlert.getTotalWeight());
                        newConsole.setNetWeight(preAlert.getTotalWeight());
                        newConsole.setManifestedQuantity(preAlert.getNoOfPieces());
                        newConsole.setLandedQuantity(preAlert.getNoOfPieces());
                        newConsole.setTotalQuantity(preAlert.getNoOfPieces());
                        newConsole.setCustomsCurrency(preAlert.getCurrency());
                        newConsole.setGoodsDescription(preAlert.getDescription());
                        newConsole.setShipperName(preAlert.getShipper());
                        newConsole.setInvoiceDate(preAlert.getEstimatedTimeOfArrival());
                        newConsole.setAirportOriginCode(preAlert.getOrigin());
                        newConsole.setCountryOfOrigin(preAlert.getOriginCode());

                        // Add to the thread-safe list
                        consoleList.add(newConsole);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, executor);
                futures.add(future);
            });

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            // Get the count specific to this group only
            long count = consoleList.size();
            IKeyValuePair shipment = replicaConsoleRepository.getShipment(consoleList.get(0).getCompanyId(), consoleList.get(0).getLanguageId());
            int noOfShipment = (shipment.getShipment() != null && shipment.getShipment() != 0) ? Math.toIntExact(shipment.getShipment()) : 99;
            log.info("Console Create Based on <------------------ >Count for PartnerMasterAirwayBill " + partnerMasterAirwayBill + ": " + count);

            if (count <= noOfShipment) {
                finalConsoleList.addAll(processNoOfCountLessThenNoOfShipment(consoleList, loginUserID));
            } else {
                finalConsoleList.addAll(createConsoleList(consoleList, loginUserID));
            }
        }

        // Shut down the executor service
        executor.shutdown();
        return finalConsoleList;
    }


    /**
     * @param addConsoleList
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    public List<Console> createConsoleList(List<Console> addConsoleList, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Console> createdConsoleList = new CopyOnWriteArrayList<>();

        try {
            // SpecialApproval
            List<Console> specialApprovalList = new ArrayList<>();
            List<Console> withoutSpeList = new ArrayList<>();

            Long specialApproval = null;
            for (Console cl : addConsoleList) {
                specialApproval = replicaConsoleRepository.getSpecialApproval(cl.getCompanyId(), cl.getHsCode());

                if (specialApproval != null && specialApproval != 0) {
                    specialApprovalList.add(cl);
                } else {
                    withoutSpeList.add(cl);
                }
            }

            List<Console> specialApKd100More = specialApprovalList.stream()
                    .filter(console -> console.getConsignmentValueLocal() >= 100)
                    .peek(console -> console.setReferenceField1("SPHV"))
                    .collect(Collectors.toList());

            List<Console> specialApKd100Less = specialApprovalList.stream()
                    .filter(console -> console.getConsignmentValueLocal() < 100)
                    .peek(console -> console.setReferenceField1("SP"))
                    .collect(Collectors.toList());

            // Separate Console where consignmentValueLocal is 100 or more
            List<Console> valueKd10OrMore = withoutSpeList.stream()
                    .filter(console -> console.getConsignmentValueLocal() >= 100)
                    .peek(console -> console.setReferenceField1("HV"))
                    .collect(Collectors.toList());

            // Separate records where hsCode is null
            List<Console> nullHsCodeList = withoutSpeList.stream()
                    .filter(console -> console.getHsCode().isEmpty() && console.getConsignmentValueLocal() < 100)
                    .peek(console -> console.setReferenceField1("N"))
                    .collect(Collectors.toList());

            // Group consoles by HS code
            List<Console> hsCode = withoutSpeList.stream()
                    .filter(console -> !console.getHsCode().isEmpty() && console.getConsignmentValueLocal() < 100)
                    .peek(console -> console.setReferenceField1("N"))
                    .collect(Collectors.toList());

            // Separate Console where SpecialApproval
            //createdConsoleList.addAll(shipmentCount(specialApprovalList, loginUserID));

            // Special Approval 100More
            createdConsoleList.addAll(shipmentCount(specialApKd100More, loginUserID));

            // Special Approval 100Less
            createdConsoleList.addAll(shipmentCount(specialApKd100Less, loginUserID));

            // Process consoles with value less than 100
            createdConsoleList.addAll(shipmentCount(valueKd10OrMore, loginUserID));

            // Process record null HsCode
            createdConsoleList.addAll(shipmentCount(nullHsCodeList, loginUserID));

            // shipment
            createdConsoleList.addAll(shipmentCount(hsCode, loginUserID));

            List<Console> consoleList = setConsoleName(createdConsoleList, loginUserID);

            // This use for Multiple Piece get single piece value
            Map<String, Console> firstConsolePerGroup = consoleList.stream()
                    .filter(console -> console.getTotalDuty() != null && console.getTotalDuty() != 0)
                    .collect(Collectors.toMap(
                            Console::getPartnerHouseAirwayBill, // Group by partnerHouseAirwayBill
                            console -> console,                 // Use the console as the value
                            (first, second) -> first            // If there are duplicates, keep the first one
                    ));

            // Sum TotalDuty
            double totalDuty = firstConsolePerGroup.values().stream()
                    .mapToDouble(Console::getTotalDuty)
                    .sum();

            log.info("CustomCosting Create ---------------------------TotalDuty Value ----------------->" + totalDuty);
            // Set CustomDuty and StampCharge in CustomCosting
            Set<String> processedAirwayBills = new HashSet<>();
            for (Console console : firstConsolePerGroup.values()) {
                if (!processedAirwayBills.contains(console.getPartnerMasterAirwayBill())) {
                    customsCostingService.createCustomCostingForBayanValue(console, loginUserID, totalDuty);
                    customsCostingService.createCustomCostingForStampCharge(console, loginUserID);
                    // Send Notification
                    consoleService.sendNotificationForConsoleCreate(console.getCompanyId(), console.getLanguageId(), console.getConsoleId(),
                            console.getHouseAirwayBill(), console.getConsoleName());
                    processedAirwayBills.add(console.getPartnerMasterAirwayBill());
                }
            }
            return consoleList;
        } catch (Exception e) {
            throw new BadRequestException("Exception is Console Create " + e);
        }
    }

    // SetConsoleName

    /**
     * @param consoleList
     * @param loginUserID
     * @return
     */
    public List<Console> setConsoleName(List<Console> consoleList, String loginUserID) {
        log.info("Number of consoleList SetName records being processed: {}", consoleList.size());
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        List<Console> multiConsole = Collections.synchronizedList(new CopyOnWriteArrayList<>());
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Create a map to group consignments by consoleId
        Map<String, List<Console>> groupByConsoleId = consoleList.stream()
                .collect(Collectors.groupingBy(Console::getConsoleId));

        // Sort the grouped entries by consoleId
        List<Map.Entry<String, List<Console>>> sortedGroups = groupByConsoleId.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toList());

        AtomicInteger consoleName = new AtomicInteger(1);

        for (Map.Entry<String, List<Console>> entry : sortedGroups) {
            // Process each group asynchronously
            List<Console> listConsole = entry.getValue();
            String currentConsoleName = "ConsoleName - " + consoleName.getAndIncrement();

            listConsole.forEach(console -> {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    console.setConsoleName(currentConsoleName);

//                    log.info("CONSOLE_ID: - {}, ----------CONSOLE_NAME: {} ------------>", console.getConsoleId(), currentConsoleName);

                    Console duplicateConsole = consoleRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndDeletionIndicator(
                            console.getLanguageId(), console.getCompanyId(), console.getPartnerId(), console.getPartnerMasterAirwayBill(), console.getPartnerHouseAirwayBill(), 0L);

                    if (duplicateConsole != null) {
//                        multiConsole.add(duplicateConsole);
                    } else {
                        if (console.getCalculatedTotalDuty() != null) {
                            console.setTotalDuty(console.getCalculatedTotalDuty());
                        } else {
                            console.setTotalDuty(0.0);
                        }

                        log.info("Console Create ---------------------------------------------------------------------------> " + console);
                        Console createdConsole = consoleRepository.save(console);

                        // Custom Costing Create
                        if (createdConsole.getCalculatedTotalDuty() != null && createdConsole.getCalculatedTotalDuty() != 0.0) {
                            consoleRepository.updateCustomDuty(createdConsole.getCompanyId(), createdConsole.getLanguageId(),
                                    createdConsole.getPartnerMasterAirwayBill(), createdConsole.getPartnerHouseAirwayBill(), createdConsole.getPartnerId(),
                                    createdConsole.getCalculatedTotalDuty());
                        }
                        if (createdConsole != null) {
                            // Save ConsignmentStatus
                            consignmentStatusService.insertConsignmentStatusRecord(
                                    createdConsole.getLanguageId(), createdConsole.getLanguageDescription(),
                                    createdConsole.getCompanyId(), createdConsole.getCompanyName(), createdConsole.getPieceId(),
                                    createdConsole.getMasterAirwayBill(), createdConsole.getHouseAirwayBill(),
                                    createdConsole.getHawbType(), createdConsole.getHawbTypeId(),
                                    createdConsole.getHawbTypeDescription(), createdConsole.getHawbTimeStamp(),
                                    createdConsole.getPieceType(), createdConsole.getPieceTypeId(),
                                    createdConsole.getPieceTypeDescription(), createdConsole.getPieceTimeStamp(),
                                    loginUserID, createdConsole.getPartnerHouseAirwayBill(),
                                    createdConsole.getPartnerMasterAirwayBill(), null, createdConsole.getHubCode(),
                                    createdConsole.getHubName()
                            );

                            // Update ConsignmentEntity Table
                            consoleRepository.updateConsignmentOnConsoleCreate(
                                    createdConsole.getLanguageId(), createdConsole.getCompanyId(),
                                    createdConsole.getPartnerId(), createdConsole.getPartnerHouseAirwayBill(),
                                    createdConsole.getPartnerMasterAirwayBill()
                            );

                            // Update PreAlert Table
                            consoleRepository.updatePreAlertOnConsoleCreate(
                                    createdConsole.getLanguageId(), createdConsole.getCompanyId(),
                                    createdConsole.getPartnerId(), createdConsole.getPartnerHouseAirwayBill(),
                                    createdConsole.getPartnerMasterAirwayBill(), createdConsole.getHawbTypeDescription(),
                                    createdConsole.getHawbTypeId(), createdConsole.getHawbType()
                            );
                            log.info("Console Created<----------------------->Consignment Status Created");
                            multiConsole.add(createdConsole);
                        }
                    }
                }, executor);
                futures.add(future);
            });
        }

        // Wait for all futures to complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // Shutdown the executor service
        executor.shutdown();
        return multiConsole;
    }


    /**
     * @param consoles
     * @param loginUserID
     * @return
     */
    List<Console> shipmentCount(List<Console> consoles, String loginUserID) {

        List<Console> createdConsoleList = new ArrayList<>();

        if (!consoles.isEmpty()) {
            IKeyValuePair shipment = replicaConsoleRepository.getShipment(consoles.get(0).getCompanyId(), consoles.get(0).getLanguageId());
            int noOfShipment = (shipment.getShipment() != null && shipment.getShipment() != 0) ? Math.toIntExact(shipment.getShipment()) : 99;
            double consVal = (shipment.getConsignmentValue() != null && shipment.getConsignmentValue() != 0) ? shipment.getConsignmentValue() : 5000.0;

            List<List<Console>> finalGroups = new ArrayList<>();
            List<Console> currentGroup = new ArrayList<>();
            double currentGroupValue = 0.0;

            for (Console console : consoles) {
                double totalDuty = calculateTotalDuty(console);
                double recordValue = totalDuty + (console.getIata() != null ? console.getIata() : 0.0);

                // Check if adding this console exceeds both consignment value and shipment count limits
                if ((currentGroup.size() >= noOfShipment) || (currentGroupValue + recordValue > consVal)) {
                    finalGroups.add(new ArrayList<>(currentGroup));         // Add the current group to finalGroups
                    currentGroup.clear();                                   // Start a new group
                    currentGroupValue = 0.0;                                // Reset consignment value counter
                }

                currentGroup.add(console);                                  // Add the current console to the current group
                currentGroupValue += recordValue;                           // Add the record value to the current consignment value
            }

            // Add the last group if it’s not empty
            if (!currentGroup.isEmpty()) {
                finalGroups.add(new ArrayList<>(currentGroup));
            }

            // Step 2: Process finalGroups
            for (List<Console> group : finalGroups) {
                String SUB_CONSOLE_ID = numberRangeService.getNextNumberRange("CONSOLEID");

                for (Console console : group) {
                    Console newConsole = createConsole(console, SUB_CONSOLE_ID, loginUserID);

                    // Set pieces
                    List<String> pieces = replicaPieceDetailsRepository.getPieceId(newConsole.getLanguageId(), newConsole.getCompanyId(),
                            newConsole.getPartnerId(), newConsole.getPartnerHouseAirwayBill(), newConsole.getPartnerMasterAirwayBill());

                    if (pieces != null && !pieces.isEmpty()) {
                        for (String pieceId : pieces) {
                            Console pieceConsole = createPieceConsole(newConsole, pieceId);
                            createdConsoleList.add(pieceConsole);
                        }
                    } else {
                        log.info("Piece Not Available for Console ID: " + newConsole.getConsoleId());
                    }
                }
            }
        }
        return createdConsoleList;
    }


    // Calculated Total Duty
    private double calculateTotalDuty(Console console) {
        double totalDuty = 0.0;

        if (console.getConsignmentValueLocal() != null) {
            totalDuty = console.getConsignmentValueLocal();
        } else {
            IKeyValuePair iKeyValue = replicaBondedManifestRepository.getToCurrencyValue(console.getCompanyId(), console.getConsignmentCurrency());
            double toCurrencyValue = (iKeyValue != null && iKeyValue.getCurrencyValue() != null) ? Double.parseDouble(iKeyValue.getCurrencyValue()) : 0.0;

            if (console.getConsignmentValue() != null) {
                totalDuty = toCurrencyValue * console.getConsignmentValue();
            }
        }
        return totalDuty;
    }

    // Create Console
    private Console createConsole(Console console, String SUB_CONSOLE_ID, String loginUserID) {
        Console newConsole = new Console();
        BeanUtils.copyProperties(console, newConsole, CommonUtils.getNullPropertyNames(console));

        newConsole.setConsoleId(SUB_CONSOLE_ID);
        newConsole.setExpectedDuty(String.valueOf(calculateTotalDuty(console)));
        newConsole.setDeletionIndicator(0L);
        newConsole.setCreatedBy(loginUserID);
        newConsole.setCreatedOn(new Date());
        newConsole.setUpdatedBy(loginUserID);
        newConsole.setUpdatedOn(new Date());

        // Setting event description (example logic)
        Optional<String> eventDescOpt = consignmentEntityRepository.statusEventText(
                console.getCompanyId(), console.getLanguageId(), "45");
        eventDescOpt.ifPresent(eventDesc -> {
            newConsole.setHawbType("EVENT");
            newConsole.setHawbTypeId("45");
            newConsole.setHawbTypeDescription(eventDesc);
            newConsole.setHawbTimeStamp(new Date());
        });

        return newConsole;
    }

    private Console createPieceConsole(Console newConsole, String pieceId) {
        Console pieceConsole = new Console();
        BeanUtils.copyProperties(newConsole, pieceConsole, CommonUtils.getNullPropertyNames(newConsole));
        pieceConsole.setPieceId(pieceId);

        // Set piece type (example logic)
        Optional<String> eventDescOpt = consignmentEntityRepository.statusEventText(
                newConsole.getCompanyId(), newConsole.getLanguageId(), "45");
        eventDescOpt.ifPresent(eventDesc -> {
            pieceConsole.setPieceType("EVENT");
            pieceConsole.setPieceTypeId("45");
            pieceConsole.setPieceTypeDescription(eventDesc);
            pieceConsole.setPieceTimeStamp(new Date());
        });

        return pieceConsole;
    }

    /**
     * @param consoleList
     * @param loginUserID
     * @return
     */
    public List<Console> processNoOfCountLessThenNoOfShipment(List<Console> consoleList, String loginUserID) {

        List<Console> createdConsoleList = new ArrayList<>();
        List<Console> consoleReturn = new ArrayList<>();
        try {
            if (!consoleList.isEmpty()) {
                String CONSOLE_ID = numberRangeService.getNextNumberRange("CONSOLEID");
                log.info("next Value from NumberRange for CONSOLE_ID : " + CONSOLE_ID);

                for (Console console : consoleList) {
                    Console newConsole = createConsole(console, CONSOLE_ID, loginUserID);

                    // Set pieces
                    List<String> pieces = replicaPieceDetailsRepository.getPieceId(newConsole.getLanguageId(), newConsole.getCompanyId(),
                            newConsole.getPartnerId(), newConsole.getPartnerHouseAirwayBill(), newConsole.getPartnerMasterAirwayBill());

                    if (pieces != null && !pieces.isEmpty()) {
                        for (String pieceId : pieces) {
                            Console pieceConsole = createPieceConsole(newConsole, pieceId);
                            createdConsoleList.add(pieceConsole);
                        }
                    } else {
                        log.info("Piece Not Available for Console ID: " + newConsole.getConsoleId());
                    }
                }
            }

            List<Console> consoleSetName = setConsoleName(createdConsoleList, loginUserID);
            // This use for Multiple Piece get single piece value
            Map<String, Console> firstConsolePerGroup = consoleSetName.stream()
                    .filter(console -> console.getTotalDuty() != null && console.getTotalDuty() != 0)
                    .collect(Collectors.toMap(
                            Console::getPartnerHouseAirwayBill, // Group by partnerHouseAirwayBill
                            console -> console,                 // Use the console as the value
                            (first, second) -> first            // If there are duplicates, keep the first one
                    ));

            // Sum TotalDuty
            double totalDuty = firstConsolePerGroup.values().stream()
                    .mapToDouble(Console::getTotalDuty)
                    .sum();

            log.info("CustomCosting Create ---------------------------TotalDuty Value ----------------->" + totalDuty);
            // Set CustomDuty and StampCharge in CustomCosting
            Set<String> processedAirwayBills = new HashSet<>();
            for (Console console : firstConsolePerGroup.values()) {
                if (!processedAirwayBills.contains(console.getPartnerMasterAirwayBill())) {
                    customsCostingService.createCustomCostingForBayanValue(console, loginUserID, totalDuty);
                    customsCostingService.createCustomCostingForStampCharge(console, loginUserID);
                    processedAirwayBills.add(console.getPartnerMasterAirwayBill());
                }
            }
            // Add All Console in List
            consoleReturn.addAll(consoleSetName);
        } catch (Exception e) {
            e.getMessage();
        }
        return consoleReturn;
    }
}

