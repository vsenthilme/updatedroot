package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.IKeyValuePair;
import com.courier.overc360.api.midmile.primary.model.consignment.ConsignmentEntity;
import com.courier.overc360.api.midmile.primary.model.console.*;
import com.courier.overc360.api.midmile.primary.model.console.unconsolidation.AddUnconsolidation;
import com.courier.overc360.api.midmile.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.midmile.primary.model.prealert.PreAlert;
import com.courier.overc360.api.midmile.primary.repository.*;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.consignmentstatus.ReplicaConsignmentStatus;
import com.courier.overc360.api.midmile.replica.model.console.*;
import com.courier.overc360.api.midmile.replica.repository.ReplicaBondedManifestRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaCcrRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaConsignmentStatusRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaConsoleRepository;
import com.courier.overc360.api.midmile.replica.repository.ReplicaPieceDetailsRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ConsoleSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.writer.BeansMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConsoleService {

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
    private NotificationService notificationService;

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
    ConsoleServiceV2 consoleServiceV2;

    @Autowired
    private CustomsClearanceInvoiceService customsClearanceInvoiceService;

    @Autowired
    PushNotificationService pushNotificationService;

    @Autowired
    CustomsCostingService customsCostingService;

    @Autowired
    CustomsCostingRepository customsCostingRepository;


    private Set<String> processedConsoleCreate = new HashSet<>();

    public synchronized String generateUniqueNumberRange(String numRanObj) {
        return numberRangeService.getNextNumberRange(numRanObj);
    }

    private final ExecutorService executorService;

    public ConsoleService(ReplicaConsoleRepository replicaConsoleRepository) {
        this.replicaConsoleRepository = replicaConsoleRepository;
        this.executorService = Executors.newFixedThreadPool(10); // Adjust the pool size as needed
    }

    /*---------------------------------------------------PRIMARY-----------------------------------------------------*/


    /**
     * Get Console
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param partnerMasterAirwayBill
     * @param partnerHouseAirwayBill
     * @param consoleId
     * @return
     */
    Console getConsole(String languageId, String companyId, String partnerId, String partnerMasterAirwayBill, String partnerHouseAirwayBill,
                       String consoleId, String pieceId) {
        Console dbConsole = consoleRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndConsoleIdAndPieceIdAndDeletionIndicator(
                languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, consoleId, pieceId, 0L);
        if (dbConsole == null) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", partnerId - " + partnerId + ", masterAirwayBill - " + partnerMasterAirwayBill
                    + ", houseAirwayBill - " + partnerHouseAirwayBill + " and consoleId - " + consoleId + " and pieceId " + pieceId + " doesn't exists";
            // Error Log
            createConsoleLog(languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, consoleId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbConsole;
    }


//    public List<Console> createConsoleBasedOnPreAlertResponse(List<PreAlert> preAlerts, String loginUserID)
//            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
//
//        List<CompletableFuture<Void>> futures = new ArrayList<>();
//
//        // Create a thread pool with a fixed number of threads
//        ExecutorService executor = Executors.newFixedThreadPool(10);
//
//        List<AddConsole> consoleList = new ArrayList<>();
//
//        preAlerts.stream().forEach(preAlert -> {
//            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//                AddConsole newConsole = new AddConsole();
//                BeanUtils.copyProperties(preAlert, newConsole, CommonUtils.getNullPropertyNames(preAlert));
//                // weight
//                newConsole.setTareWeight(preAlert.getTotalWeight());
//                newConsole.setGrossWeight(preAlert.getTotalWeight());
//                newConsole.setManifestedGrossWeight(preAlert.getTotalWeight());
//                newConsole.setNetWeight(preAlert.getTotalWeight());
//
//                // no_piece
//                newConsole.setManifestedQuantity(preAlert.getNoOfPieces());
//                newConsole.setLandedQuantity(preAlert.getNoOfPieces());
//                newConsole.setTotalQuantity(preAlert.getNoOfPieces());
//                newConsole.setCustomsCurrency(preAlert.getCurrency());
//                newConsole.setGoodsDescription(preAlert.getDescription());
//                newConsole.setShipperName(preAlert.getShipper());
//
//                newConsole.setAirportOriginCode(preAlert.getOrigin());
//                newConsole.setCountryOfOrigin(preAlert.getOriginCode());
//
//                consoleList.add(newConsole);
//
//            }, executor);
//            futures.add(future);
//        });
//        // Wait for all the futures to complete
//        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
//
//        // Shut down the executor service
//        executor.shutdown();
//
//        return createConsoleList(consoleList, loginUserID);
//    }

    /**
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

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // Use a thread-safe list
        List<AddConsole> consoleList = new CopyOnWriteArrayList<>();

        // Create a thread pool with a fixed number of threads
        ExecutorService executor = Executors.newFixedThreadPool(10);

        preAlerts.forEach(preAlert -> {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    AddConsole newConsole = new AddConsole();
                    BeanUtils.copyProperties(preAlert, newConsole, CommonUtils.getNullPropertyNames(preAlert));
                    // weight
                    newConsole.setTareWeight(preAlert.getTotalWeight());
                    newConsole.setGrossWeight(preAlert.getTotalWeight());
                    newConsole.setManifestedGrossWeight(preAlert.getTotalWeight());
                    newConsole.setNetWeight(preAlert.getTotalWeight());

                    // no_piece
                    newConsole.setManifestedQuantity(preAlert.getNoOfPieces());
                    newConsole.setLandedQuantity(preAlert.getNoOfPieces());
                    newConsole.setTotalQuantity(preAlert.getNoOfPieces());
                    newConsole.setCustomsCurrency(preAlert.getCurrency());
                    newConsole.setGoodsDescription(preAlert.getDescription());
                    newConsole.setShipperName(preAlert.getShipper());
                    newConsole.setInvoiceDate(preAlert.getEstimatedTimeOfArrival());

                    newConsole.setAirportOriginCode(preAlert.getOrigin());
                    newConsole.setCountryOfOrigin(preAlert.getOriginCode());

                    // Add to thread-safe list
                    consoleList.add(newConsole);

                } catch (Exception e) {
                    e.printStackTrace();  // Handle and log exception properly
                }
            }, executor);
            futures.add(future);
        });

        // Wait for all the futures to complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // Shut down the executor service
        executor.shutdown();

        return createConsoleList(consoleList, loginUserID);
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
    public List<Console> createConsoleList(List<AddConsole> addConsoleList, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<Console> createdConsoleList = new ArrayList<>();

        try {
            // SpecialApproval
            List<AddConsole> specialApprovalList = new ArrayList<>();
            List<AddConsole> withoutSpeList = new ArrayList<>();

            Long specialApproval = null;
            for (AddConsole cl : addConsoleList) {
                specialApproval = replicaConsoleRepository.getSpecialApproval(cl.getCompanyId(), cl.getHsCode());

                if (specialApproval != null && specialApproval != 0) {
                    specialApprovalList.add(cl);
                } else {
                    withoutSpeList.add(cl);
                }
            }

            // Separate Console where SpecialApproval
            createdConsoleList.addAll(processConsolesWithNullHsCode(specialApprovalList, loginUserID));

            // Separate Console where consignmentValueLocal is 100 or more
            List<AddConsole> valueKd100OrMore = withoutSpeList.stream()
                    .filter(console -> console.getConsignmentValueLocal() >= 100)
                    .collect(Collectors.toList());

            // Process consoles with value less than 100
            createdConsoleList.addAll(processConsolesWithValue100OrMore(valueKd100OrMore, loginUserID));

            // Separate records where hsCode is null
            List<AddConsole> nullHsCodeList = withoutSpeList.stream()
                    .filter(console -> console.getHsCode().isEmpty() && console.getConsignmentValueLocal() < 100)
                    .collect(Collectors.toList());

            // Process record null HsCode
            createdConsoleList.addAll(processConsolesWithNullHsCode(nullHsCodeList, loginUserID));

            // Group consoles by HS code
            /*---------------------------------------------------------------------------------------------------------------*/
            List<AddConsole> hsCode = withoutSpeList.stream()
                    .filter(console -> !console.getHsCode().isEmpty() && console.getConsignmentValueLocal() < 100)
                    .collect(Collectors.toList());

            // Further group the consignments into smaller groups of up to 6 records each
            List<List<AddConsole>> smallerGroups = new ArrayList<>();

            if (!hsCode.isEmpty()) {
                IKeyValuePair shipment = replicaConsoleRepository.getShipment(hsCode.get(0).getCompanyId(), hsCode.get(0).getLanguageId());
                int noOfShipment = 0;
                Double consVal = 0.0;
                if (shipment.getShipment() != null && shipment.getShipment() != 0) {
                    noOfShipment = Math.toIntExact(shipment.getShipment());
                } else {
                    noOfShipment = 99;
                }
                if (shipment.getConsignmentValue() != null && shipment.getConsignmentValue() != 0) {
                    consVal = shipment.getConsignmentValue();
                } else {
                    consVal = 5000.0;
                }

                for (int i = 0; i < hsCode.size(); i += noOfShipment) {
                    List<AddConsole> subList = hsCode.subList(i, Math.min(i + noOfShipment, hsCode.size()));
                    smallerGroups.add(subList);
                }

                // Process each smaller group
                for (List<AddConsole> smallerGroup : smallerGroups) {
                    List<List<AddConsole>> subGroups = new ArrayList<>();
                    List<AddConsole> currentSubGroup = new ArrayList<>();
                    Double currentSubGroupValue = 0.0;

                    for (AddConsole console : smallerGroup) {
                        Double consignmentValue = null;

                        IKeyValuePair iKeyValue = replicaBondedManifestRepository.getToCurrencyValue(console.getCompanyId(), console.getConsignmentCurrency());

                        Double toCurrencyValue = 0.0;
                        if (iKeyValue != null && iKeyValue.getCurrencyValue() != null) {
                            toCurrencyValue = Double.parseDouble(iKeyValue.getCurrencyValue());
                        }
                        Double totalDuty = 0.0;
                        if (console.getConsignmentValue() != null) {
                            consignmentValue = console.getConsignmentValue();
                            totalDuty = toCurrencyValue * consignmentValue;
                        }
//                    if (totalDuty > 100) {
//                        totalDuty += totalDuty * 0.05;
//                    }
//                    if (console.getIncoTerm() != null && console.getIncoTerm().equalsIgnoreCase("DDU")) {
//                        totalDuty += 4;
//                    }

                        Double iataKd = 0.0;
                        if (console.getIata() != null) {
                            iataKd = console.getIata();
                        }
                        Double recordValue = iataKd + totalDuty;

                        if (currentSubGroupValue + recordValue > consVal) {
                            subGroups.add(currentSubGroup);
                            currentSubGroup = new ArrayList<>();
                            currentSubGroupValue = 0.0;
                        }

                        currentSubGroup.add(console);
                        currentSubGroupValue += recordValue;
                    }

                    if (!currentSubGroup.isEmpty()) {
                        subGroups.add(currentSubGroup);
                    }

                    // Process each subgroup
                    for (List<AddConsole> subGroup : subGroups) {
                        // Generate a new CONSOLE_ID for each subgroup
                        String SUB_CONSOLE_ID = numberRangeService.getNextNumberRange("CONSOLEID");

                        for (AddConsole console : subGroup) {

                            // Pass ConsignmentCurrency
                            IKeyValuePair iKeyValuePair = replicaBondedManifestRepository.getToCurrencyValue(console.getCompanyId(), console.getCustomsCurrency());

                            Console newConsole = new Console();
                            BeanUtils.copyProperties(console, newConsole, CommonUtils.getNullPropertyNames(console));
                            Double consignmentValue = null;
                            if (console.getConsignmentValue() != null) {
                                consignmentValue = Double.valueOf(console.getConsignmentValue());
                            }
                            // Set TotalDuty Value
                            double totalDuty = 0;
                            if (iKeyValuePair != null && iKeyValuePair.getCurrencyValue() != null) {
                                double toCurrencyValue = Double.parseDouble(iKeyValuePair.getCurrencyValue());
                                if (toCurrencyValue != 0 && consignmentValue != 0 && consignmentValue != null) {
                                    totalDuty = toCurrencyValue * consignmentValue;
                                    if (totalDuty > 100) {
                                        totalDuty += totalDuty * 0.05;
                                    }
                                    if (console.getIncoTerm() != null && console.getIncoTerm().equalsIgnoreCase("DDU")) {
                                        totalDuty += 4;
                                    }
                                }
                            }

                            Optional<String> eventDescOpt = consignmentEntityRepository.statusEventText(
                                    console.getCompanyId(), console.getLanguageId(), "45");
                            if (eventDescOpt.isPresent()) {
                                String ikey = eventDescOpt.get();

                                newConsole.setHawbType("EVENT");
                                newConsole.setHawbTypeId("45");
                                newConsole.setHawbTypeDescription(ikey);
                                newConsole.setHawbTimeStamp(new Date());
                            }

                            newConsole.setExpectedDuty(String.valueOf(totalDuty));
//                            newConsole.setCustomsValue(CUS_VAL);
                            newConsole.setConsoleId(SUB_CONSOLE_ID);
                            newConsole.setDeletionIndicator(0L);
                            newConsole.setCreatedBy(loginUserID);
                            newConsole.setCreatedOn(new Date());
                            newConsole.setUpdatedBy(loginUserID);
                            newConsole.setUpdatedOn(new Date());

                            // Get Piece
                            List<String> piece = replicaPieceDetailsRepository.getPieceId(newConsole.getLanguageId(), newConsole.getCompanyId(),
                                    newConsole.getPartnerId(), newConsole.getPartnerHouseAirwayBill(), newConsole.getPartnerMasterAirwayBill());

                            if (piece != null && !piece.isEmpty()) {
                                for (String pieceId : piece) {
                                    Console addConsole = new Console();
                                    BeanUtils.copyProperties(newConsole, addConsole, CommonUtils.getNullPropertyNames(newConsole));
                                    addConsole.setPieceId(pieceId);
//
                                    if (eventDescOpt.isPresent()) {
                                        String ikey = eventDescOpt.get();

                                        addConsole.setPieceType("EVENT");
                                        addConsole.setPieceTypeId("45");
                                        addConsole.setPieceTypeDescription(ikey);
                                        addConsole.setPieceTimeStamp(new Date());
                                    }
                                    createdConsoleList.add(addConsole);
                                }
                            } else {
                                log.info("Piece Not Available in the CompanyId " + newConsole.getCompanyId() + " LanguageId " + newConsole.getConsoleId() +
                                        " PartnerId " + newConsole.getPartnerId() + " PartnerHouseAirwayBill " + newConsole.getPartnerMasterAirwayBill() +
                                        " PartnerMasterAirwayBill " + newConsole.getPartnerMasterAirwayBill() + " Doesn't exist");
                            }
                        }
                    }
                }
            }
            List<Console> consoleList = setConsoleName(createdConsoleList, loginUserID);

//            List<Console> cal = consoleList.stream()
//                    .filter(console -> console.getTotalDuty() != null && console.getTotalDuty() != 0)
//                    .collect(Collectors.toList());

            // Group By PartnerHouseAirwayBill
//            Map<String, List<Console>> groupedConsoles = consoleList.stream()
//                    .filter(console -> console.getTotalDuty() != null && console.getTotalDuty() != 0)
//                    .collect(Collectors.groupingBy(Console::getPartnerHouseAirwayBill));

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


//            double totalDuty = groupedConsoles.stream()
//                    .mapToDouble(Console::getTotalDuty)
//                    .sum();

//            if (!cal.isEmpty()) {
//                Console firstConsole = cal.get(0);
//            }

            // Set CustomDuty and StampCharge in CustomCosting
            Set<String> processedAirwayBills = new HashSet<>();
            for (Console console : firstConsolePerGroup.values()) {
//                for (Console console : list) {
                if (!processedAirwayBills.contains(console.getPartnerMasterAirwayBill())) {
                    customsCostingService.createCustomCostingForBayanValue(console, loginUserID, totalDuty);
                    customsCostingService.createCustomCostingForStampCharge(console, loginUserID);
                    processedAirwayBills.add(console.getPartnerMasterAirwayBill());
                }
//                }
            }
            return consoleList;
        } catch (Exception e) {
            throw new BadRequestException("Exception is Console Create " + e);
        }
    }

    // Process Console With Null HsCode

    /**
     * @param nullHsCodeList
     * @param loginUserID
     * @return
     */
    private List<Console> processConsolesWithNullHsCode(List<AddConsole> nullHsCodeList, String loginUserID) {

        List<Console> createdConsoleList = new ArrayList<>();
        try {
            //If HsCode is null
            if (!nullHsCodeList.isEmpty()) {
                String CONSOLE_ID = numberRangeService.getNextNumberRange("CONSOLEID");
                log.info("next Value from NumberRange for CONSOLE_ID : " + CONSOLE_ID);
                for (AddConsole addConsole : nullHsCodeList) {
                    Console newConsole = new Console();
                    BeanUtils.copyProperties(addConsole, newConsole, CommonUtils.getNullPropertyNames(addConsole));

                    Optional<String> eventDescOpt = consignmentEntityRepository.statusEventText(
                            addConsole.getCompanyId(), addConsole.getLanguageId(), "45");
                    if (eventDescOpt.isPresent()) {
                        String ikey = eventDescOpt.get();

                        newConsole.setHawbType("EVENT");
                        newConsole.setHawbTypeId("45");
                        newConsole.setHawbTypeDescription(ikey);
                        newConsole.setHawbTimeStamp(new Date());

                        newConsole.setPieceType("EVENT");
                        newConsole.setPieceTypeId("45");
                        newConsole.setPieceTypeDescription(ikey);
                        newConsole.setPieceTimeStamp(new Date());
                    }
                    newConsole.setConsoleId(CONSOLE_ID);
                    newConsole.setDeletionIndicator(0L);
                    newConsole.setCreatedBy(loginUserID);
                    newConsole.setCreatedOn(new Date());
                    newConsole.setUpdatedBy(loginUserID);
                    newConsole.setUpdatedOn(new Date());

                    // Get Piece
                    List<String> piece = replicaPieceDetailsRepository.getPieceId(newConsole.getLanguageId(), newConsole.getCompanyId(),
                            newConsole.getPartnerId(), newConsole.getPartnerHouseAirwayBill(), newConsole.getPartnerMasterAirwayBill());

                    if (piece != null && !piece.isEmpty()) {
                        for (String pieceId : piece) {
                            Console dbConsole = new Console();
                            BeanUtils.copyProperties(newConsole, dbConsole, CommonUtils.getNullPropertyNames(newConsole));
                            dbConsole.setPieceId(pieceId);
                            if (eventDescOpt.isPresent()) {
                                String ikey = eventDescOpt.get();

                                dbConsole.setPieceType("EVENT");
                                dbConsole.setPieceTypeId("45");
                                dbConsole.setPieceTypeDescription(ikey);
                                dbConsole.setPieceTimeStamp(new Date());
                            }
                            createdConsoleList.add(dbConsole);
                        }
                    } else {
                        log.info("Piece Not Available in the CompanyId " + newConsole.getCompanyId() + " LanguageId " + newConsole.getConsoleId() +
                                " PartnerId " + newConsole.getPartnerId() + " PartnerHouseAirwayBill " + newConsole.getPartnerMasterAirwayBill() +
                                " PartnerMasterAirwayBill " + newConsole.getPartnerMasterAirwayBill() + " Doesn't exist");
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return createdConsoleList;
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
                        multiConsole.add(duplicateConsole);
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
                            // Send Notification
                            sendNotificationForConsoleCreate(createdConsole.getCompanyId(), createdConsole.getLanguageId(), createdConsole.getConsoleId(),
                                    createdConsole.getHouseAirwayBill(), createdConsole.getConsoleName());
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
     * Create Console
     *
     * @param addConsoleList
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    public List<Console> processConsolesWithValue100OrMore(List<AddConsole> addConsoleList, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            List<Console> createdConsoleList = new ArrayList<>();

            String CONSOLE_ID = numberRangeService.getNextNumberRange("CONSOLEID");
            log.info("next Value from NumberRange for CONSOLE_ID : " + CONSOLE_ID);
            for (AddConsole addConsole : addConsoleList) {

                Console newConsole = new Console();
                BeanUtils.copyProperties(addConsole, newConsole, CommonUtils.getNullPropertyNames(addConsole));

                newConsole.setConsoleId(CONSOLE_ID);
                newConsole.setDeletionIndicator(0L);
                newConsole.setCreatedBy(loginUserID);
                newConsole.setCreatedOn(new Date());
                newConsole.setUpdatedBy(loginUserID);
                newConsole.setUpdatedOn(new Date());
                Optional<String> eventDescOpt = consignmentEntityRepository.statusEventText(
                        addConsole.getCompanyId(), addConsole.getLanguageId(), "45");

                // Get Piece
                List<String> piece = replicaPieceDetailsRepository.getPieceId(newConsole.getLanguageId(), newConsole.getCompanyId(),
                        newConsole.getPartnerId(), newConsole.getPartnerHouseAirwayBill(), newConsole.getPartnerMasterAirwayBill());

                if (piece != null && !piece.isEmpty()) {
                    for (String pieceId : piece) {
                        Console dbConsole = new Console();
                        BeanUtils.copyProperties(newConsole, dbConsole, CommonUtils.getNullPropertyNames(newConsole));
                        dbConsole.setPieceId(pieceId);

                        if (eventDescOpt.isPresent()) {
                            String ikey = eventDescOpt.get();
                            dbConsole.setHawbType("EVENT");
                            dbConsole.setHawbTypeId("45");
                            dbConsole.setHawbTypeDescription(ikey);
                            dbConsole.setHawbTimeStamp(new Date());

                            dbConsole.setPieceType("EVENT");
                            dbConsole.setPieceTypeId("45");
                            dbConsole.setPieceTypeDescription(ikey);
                            dbConsole.setPieceTimeStamp(new Date());
                        }
                        createdConsoleList.add(dbConsole);
                    }
                } else {
                    log.info("Piece Not Available in the CompanyId " + newConsole.getCompanyId() + " LanguageId " + newConsole.getConsoleId() +
                            " PartnerId " + newConsole.getPartnerId() + " PartnerHouseAirwayBill " + newConsole.getPartnerMasterAirwayBill() +
                            " PartnerMasterAirwayBill " + newConsole.getPartnerMasterAirwayBill() + " Doesn't exist");
                }
            }
            return createdConsoleList;
        } catch (Exception e) {
            // Error Log
            createConsoleLog2(addConsoleList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update Console
     *
     * @param updateConsoleList
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
//    public List<Console> updateConsoleCcrCreate(List<UpdateConsole> updateConsoleList, String loginUserID)
//            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
//
//        List<Console> updatedConsoleList = new CopyOnWriteArrayList<>();
//        List<CompletableFuture<Void>> futures = new ArrayList<>();
//        ExecutorService executor = Executors.newFixedThreadPool(10);
//        ConcurrentHashMap<String, String> consoleCcrIdMap = new ConcurrentHashMap<>();
//        try {
//            updateConsoleList.forEach(updateConsole -> {
//                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//                    Console dbConsole = getConsole(updateConsole.getLanguageId(), updateConsole.getCompanyId(), updateConsole.getPartnerId(),
//                            updateConsole.getPartnerMasterAirwayBill(), updateConsole.getPartnerHouseAirwayBill(), updateConsole.getConsoleId(), updateConsole.getPieceId());
//
//                    BeanUtils.copyProperties(updateConsole, dbConsole, CommonUtils.getNullPropertyNames(updateConsole));
//
//                    // CCR_ID generation based on unique consoleId
//                    String consoleId = updateConsole.getConsoleId();
//                    // Generate or retrieve the CCR_ID for this consoleId
//                    String CCR_ID = consoleCcrIdMap.computeIfAbsent(consoleId, id -> {
//                        String newCcrId = generateUniqueNumberRange("CCRID");
//                        log.info("Generated new CCR_ID {} for consoleId {}", newCcrId, consoleId);
//                        return newCcrId;
//                    });
//                    log.info("Using CCR_ID {} for consoleId {}", CCR_ID, consoleId);
//                    dbConsole.setCcrId(CCR_ID);
//                    dbConsole.setInvoiceType("FOB");
//                    dbConsole.setFreightCurrency("KWD");
//                    dbConsole.setUpdatedBy(loginUserID);
//                    dbConsole.setUpdatedOn(new Date());
//                    if (updateConsole.getCustomsValue() != null) {
//                        Double customsValue = updateConsole.getCustomsValue();
//
//                        if (customsValue < 100) {
//                            dbConsole.setIsExempted("yes");
//                            dbConsole.setExemptionFor("Regulation 94-2020");
//                            dbConsole.setExemptionBeneficiary("others");
//                            dbConsole.setExemptionReference("99");
//                        } else {
//                            dbConsole.setIsExempted("No");
//                        }
//                    }
//                    Optional<IKeyValuePair> ikey = replicaCcrRepository.getInvoice(dbConsole.getCompanyId(),
//                            dbConsole.getLanguageId(), dbConsole.getPartnerId(), dbConsole.getPartnerHouseAirwayBill(),
//                            dbConsole.getPartnerMasterAirwayBill());
//
//                    if (ikey.isPresent()) {
//                        IKeyValuePair invoice = ikey.get();
//                        dbConsole.setInvoiceDate(invoice.getInvoiceDate());
//                    }
//                    Console updatedConsole = consoleRepository.save(dbConsole);
//                    updatedConsoleList.add(updatedConsole);
//                }, executor);
//                futures.add(future);
//            });
//            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
//            executor.shutdown();
//            ccrService.createConsoleCcr(updatedConsoleList, loginUserID);
//            return updatedConsoleList;
//        } catch (Exception e) {
//            // Error Log
//            createConsoleLog3(updateConsoleList, e.toString());
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

    public List<Console> updateConsoleCcrCreate(List<UpdateConsole> updateConsoleList, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {

        // Generate CCR_IDs and create a mapping for each unique consoleId
        Map<String, String> consoleCcrIdMap = generateCcrIdsForConsoles(updateConsoleList);

        List<Console> updatedConsoleList = new CopyOnWriteArrayList<>();
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(6);
        try {
            updateConsoleList.forEach(updateConsole -> {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    Console dbConsole = getConsole(updateConsole.getLanguageId(), updateConsole.getCompanyId(), updateConsole.getPartnerId(),
                            updateConsole.getPartnerMasterAirwayBill(), updateConsole.getPartnerHouseAirwayBill(), updateConsole.getConsoleId(), updateConsole.getPieceId());

                    // Retrieve pre-generated CCR_ID for the consoleId
                    String ccrId = getCcrIdForConsole(updateConsole.getConsoleId(), consoleCcrIdMap);
                    dbConsole.setCcrId(ccrId);
                    BeanUtils.copyProperties(updateConsole, dbConsole, CommonUtils.getNullPropertyNames(updateConsole));

                    dbConsole.setInvoiceType("FOB");
                    dbConsole.setFreightCurrency("KWD");
                    dbConsole.setUpdatedBy(loginUserID);
                    dbConsole.setUpdatedOn(new Date());

                    if (updateConsole.getCustomsValue() != null) {
                        Double customsValue = updateConsole.getCustomsValue();
                        if (customsValue < 100) {
                            dbConsole.setIsExempted("yes");
                            dbConsole.setExemptionFor("Regulation 94-2020");
                            dbConsole.setExemptionBeneficiary("others");
                            dbConsole.setExemptionReference("99");
                        } else {
                            dbConsole.setIsExempted("No");
                        }
                    }

                    Optional<IKeyValuePair> ikey = replicaCcrRepository.getInvoice(dbConsole.getCompanyId(),
                            dbConsole.getLanguageId(), dbConsole.getPartnerId(), dbConsole.getPartnerHouseAirwayBill(),
                            dbConsole.getPartnerMasterAirwayBill());

                    if (ikey.isPresent()) {
                        IKeyValuePair invoice = ikey.get();
                        dbConsole.setInvoiceDate(invoice.getInvoiceDate());
                    }

                    Console updatedConsole = consoleRepository.save(dbConsole);
                    updatedConsoleList.add(updatedConsole);
                }, executor);
                futures.add(future);
            });

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            executor.shutdown();

            // console CCR creation
            ccrService.createConsoleCcr(updatedConsoleList, loginUserID);
            return updatedConsoleList;

        } catch (Exception e) {
            // Log the error
            createConsoleLog3(updateConsoleList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // generate CCR_IDs for each unique consoleId
    private Map<String, String> generateCcrIdsForConsoles(List<UpdateConsole> updateConsoleList) {
        Set<String> uniqueConsoleIds = new LinkedHashSet<>();
        updateConsoleList.forEach(updateConsole -> uniqueConsoleIds.add(updateConsole.getConsoleId()));

        Map<String, String> consoleCcrIdMap = new LinkedHashMap<>();
        uniqueConsoleIds.forEach(consoleId -> {
            String ccrId = numberRangeService.getNextNumberRange("CCRID");
            consoleCcrIdMap.put(consoleId, ccrId);
            log.info("Generated CCR_ID {} for consoleId {}", ccrId, consoleId);
        });
        return consoleCcrIdMap;
    }

    private String getCcrIdForConsole(String consoleId, Map<String, String> consoleCcrIdMap) {
        return consoleCcrIdMap.get(consoleId);
    }



    /**
     * Update Console
     *
     * @param updateConsoleList
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public List<Console> updateConsoleForMobileApp(List<UpdateConsole> updateConsoleList, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            List<Console> updatedConsoleList = new ArrayList<>();

            for (UpdateConsole updateConsole : updateConsoleList) {
                log.info("Console Id <-------------------------------> {}", updateConsole);

                // UnConsolidation Create
                if (updateConsole.getUnconsolidatedFlag() == 1L) {
                    AddUnconsolidation addUnconsolidation = new AddUnconsolidation();
                    BeanUtils.copyProperties(updateConsole, addUnconsolidation, CommonUtils.getNullPropertyNames(updateConsole));
                    unconsolidationService.generateUnconsolidation(addUnconsolidation, loginUserID);
                    return Collections.emptyList();
                } else {

                    log.info("UnConsolidatedFlag <---------------------------------------------> {} ", updateConsole.getUnconsolidatedFlag());
                    Console dbConsole = getConsole(updateConsole.getLanguageId(), updateConsole.getCompanyId(), updateConsole.getPartnerId(),
                            updateConsole.getPartnerMasterAirwayBill(), updateConsole.getPartnerHouseAirwayBill(), updateConsole.getConsoleId(), updateConsole.getPieceId());

                    BeanUtils.copyProperties(updateConsole, dbConsole, CommonUtils.getNullPropertyNames(updateConsole));
                    log.info("Console HawbTypeId: {} ", updateConsole.getHawbTypeId());

                    Optional<String> statusID5DescOpt = consignmentEntityRepository.statusEventText(updateConsole.getCompanyId(), updateConsole.getLanguageId(), "5");
                    if (statusID5DescOpt.isPresent()) {
                        String ikey = statusID5DescOpt.get();

                        dbConsole.setHawbType("STATUS");
                        dbConsole.setHawbTypeId("5");
                        dbConsole.setHawbTypeDescription(ikey);
                        dbConsole.setHawbTimeStamp(new Date());

                        dbConsole.setPieceType("STATUS");
                        dbConsole.setPieceTypeId("5");
                        dbConsole.setPieceTypeDescription(ikey);
                        dbConsole.setPieceTimeStamp(new Date());
                    }

                    if (updateConsole.getUnconsolidatedFlag() != null) {
                        if (updateConsole.getUnconsolidatedFlag() == 1L) {
                            AddUnconsolidation addUnconsolidation = new AddUnconsolidation();
                            BeanUtils.copyProperties(updateConsole, addUnconsolidation, CommonUtils.getNullPropertyNames(updateConsole));
                            // Create UnConsolidation record
                            unconsolidationService.generateUnconsolidation(addUnconsolidation, loginUserID);
                        }
                    }

                    dbConsole.setScannedBy(loginUserID);
                    dbConsole.setScannedOn(new Date());

                    dbConsole.setUpdatedBy(loginUserID);
                    dbConsole.setUpdatedOn(new Date());
                    Console updatedConsole = consoleRepository.save(dbConsole);

                    // BagId
                    Long BAG_ID = 0L;
                    ConsignmentEntity dbConsignment = consignmentEntityRepository.findByCompanyIdAndLanguageIdAndPartnerIdAndMasterAirwayBillAndHouseAirwayBillAndDeletionIndicator(
                            updatedConsole.getCompanyId(), updatedConsole.getLanguageId(), updatedConsole.getPartnerId(), updatedConsole.getMasterAirwayBill(), updatedConsole.getHouseAirwayBill(), 0L);

                    if (dbConsignment == null || dbConsignment.getConsignmentBagId() == null) {
                        BAG_ID = Long.valueOf(numberRangeService.getNextNumberRange("BAGID"));
                    } else {
                        BAG_ID = dbConsignment.getConsignmentBagId();
                    }

                    if (updatedConsole != null) {

                        Optional<String> statusID4DescOpt = consignmentEntityRepository.statusEventText(updateConsole.getCompanyId(), updateConsole.getLanguageId(), "4");
                        if (statusID4DescOpt.isPresent()) {
                            String ikey = statusID4DescOpt.get();

                            // Create Consignment Status Table record with StatusID - 4
                            consignmentStatusService.insertConsignmentStatusRecord(updatedConsole.getLanguageId(), updatedConsole.getLanguageDescription(),
                                    updatedConsole.getCompanyId(), updatedConsole.getCompanyName(), updatedConsole.getPieceId(),
                                    updatedConsole.getMasterAirwayBill(), updatedConsole.getHouseAirwayBill(), updatedConsole.getHawbType(),
                                    "4", ikey, updatedConsole.getHawbTimeStamp(), updatedConsole.getPieceType(),
                                    "4", ikey, updatedConsole.getPieceTimeStamp(), loginUserID,
                                    updatedConsole.getPartnerHouseAirwayBill(), updatedConsole.getPartnerMasterAirwayBill(), BAG_ID,
                                    updateConsole.getHubCode(), updateConsole.getHubName());
                        }

                        // Create Consignment Status Table record with StatusID - 5
                        consignmentStatusService.insertConsignmentStatusRecord(updatedConsole.getLanguageId(), updatedConsole.getLanguageDescription(),
                                updatedConsole.getCompanyId(), updatedConsole.getCompanyName(), updatedConsole.getPieceId(),
                                updatedConsole.getMasterAirwayBill(), updatedConsole.getHouseAirwayBill(), updatedConsole.getHawbType(),
                                updatedConsole.getHawbTypeId(), updatedConsole.getHawbTypeDescription(),
                                updatedConsole.getHawbTimeStamp(), updatedConsole.getPieceType(), updatedConsole.getPieceTypeId(),
                                updatedConsole.getPieceTypeDescription(), updatedConsole.getPieceTimeStamp(), loginUserID,
                                updatedConsole.getPartnerHouseAirwayBill(), updatedConsole.getPartnerMasterAirwayBill(), BAG_ID,
                                updateConsole.getHubCode(), updateConsole.getHubName());

                        // Update ConsignmentEntity Table
                        consoleRepository.updateConsignmentOnConsoleCreate(
                                updatedConsole.getLanguageId(), updatedConsole.getCompanyId(), updatedConsole.getPartnerId(),
                                updatedConsole.getPartnerHouseAirwayBill(), updatedConsole.getPartnerMasterAirwayBill(),
                                updatedConsole.getHawbTypeDescription(), updatedConsole.getHawbTypeId(), updatedConsole.getHawbType(),
                                updateConsole.getHubCode(), BAG_ID);

                        // Update PreAlert Table
                        consoleRepository.updatePreAlertOnConsoleCreate(
                                updatedConsole.getLanguageId(), updatedConsole.getCompanyId(), updatedConsole.getPartnerId(),
                                updatedConsole.getPartnerHouseAirwayBill(), updatedConsole.getPartnerMasterAirwayBill(),
                                updatedConsole.getHawbTypeDescription(), updatedConsole.getHawbTypeId(), updatedConsole.getHawbType());

                        // Update PieceDetails Table
                        consoleRepository.updatePieceDetailsOnConsoleCreate(
                                updatedConsole.getLanguageId(), updatedConsole.getCompanyId(), updatedConsole.getPartnerId(),
                                updatedConsole.getPartnerHouseAirwayBill(), updatedConsole.getPartnerMasterAirwayBill(),
                                updatedConsole.getHawbTypeDescription(), updatedConsole.getHawbTypeId(), updatedConsole.getHawbType(),
                                updatedConsole.getPieceId());

                        updatedConsoleList.add(updatedConsole);
                    }
                }
            }
            return updatedConsoleList;
        } catch (Exception e) {
            // Error Log
            createConsoleLog3(updateConsoleList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * UpdateConsole
     *
     * @param updateConsoleList
     * @param loginUserID
     * @return
     */
    public List<Console> updateConsoleList(List<UpdateConsole> updateConsoleList, String loginUserID) {

        List<Console> consoleList = new ArrayList<>();

        for (UpdateConsole updateConsole : updateConsoleList) {
            Console dbConsole = getConsole(
                    updateConsole.getLanguageId(), updateConsole.getCompanyId(),
                    updateConsole.getPartnerId(), updateConsole.getPartnerMasterAirwayBill(),
                    updateConsole.getPartnerHouseAirwayBill(), updateConsole.getConsoleId(),
                    updateConsole.getPieceId());

            BeanUtils.copyProperties(updateConsole, dbConsole, CommonUtils.getNullPropertyNames(updateConsole));
            dbConsole.setUpdatedBy(loginUserID);
            dbConsole.setUpdatedOn(new Date());

            Optional<IKeyValuePair> getStatusOpt =
                    consignmentEntityRepository.statusText(updateConsole.getCompanyId(), updateConsole.getLanguageId(), updateConsole.getHawbTypeId());

            if (getStatusOpt.isPresent()) {
                IKeyValuePair ikey = getStatusOpt.get();

                dbConsole.setHawbType(ikey.getType());
                dbConsole.setHawbTypeId(updateConsole.getHawbTypeId());
                dbConsole.setHawbTypeDescription(ikey.getTypeText());
                dbConsole.setHawbTimeStamp(new Date());

                dbConsole.setPieceType(ikey.getType());
                dbConsole.setPieceTypeId(updateConsole.getHawbTypeId());
                dbConsole.setPieceTypeDescription(ikey.getTypeText());
                dbConsole.setPieceTimeStamp(new Date());
            }

            Console updatedConsole = consoleRepository.save(dbConsole);

            if (updatedConsole != null) {
                // Update ConsignmentEntity Table
                if (updatedConsole.getHawbType().equalsIgnoreCase("STATUS")) {
                    consoleRepository.updateConsignmentOnConsoleUpdate(
                            updatedConsole.getLanguageId(), updatedConsole.getCompanyId(), updatedConsole.getPartnerId(),
                            updatedConsole.getPartnerHouseAirwayBill(), updatedConsole.getPartnerMasterAirwayBill(),
                            updatedConsole.getHawbTypeDescription(), updatedConsole.getHawbTypeId(), updatedConsole.getHawbType(),
                            updatedConsole.getHubCode());
                }
                // Update PreAlert Table
                consoleRepository.updatePreAlertOnConsoleCreate(
                        updatedConsole.getLanguageId(), updatedConsole.getCompanyId(), updatedConsole.getPartnerId(),
                        updatedConsole.getPartnerHouseAirwayBill(), updatedConsole.getPartnerMasterAirwayBill(),
                        updatedConsole.getHawbTypeDescription(), updatedConsole.getHawbTypeId(), updatedConsole.getHawbType());

                // Update PieceDetails Table
                consoleRepository.updatePieceDetailsOnConsoleCreate(
                        updatedConsole.getLanguageId(), updatedConsole.getCompanyId(), updatedConsole.getPartnerId(),
                        updatedConsole.getPartnerHouseAirwayBill(), updatedConsole.getPartnerMasterAirwayBill(),
                        updatedConsole.getHawbTypeDescription(), updatedConsole.getHawbTypeId(), updatedConsole.getHawbType(),
                        updatedConsole.getPieceId());

                //Insert ConsignmentStatus
                consignmentStatusService.insertConsignmentStatusRecord(updatedConsole.getLanguageId(), updatedConsole.getLanguageDescription(),
                        updatedConsole.getCompanyId(), updatedConsole.getCompanyName(), updatedConsole.getPieceId(),
                        updatedConsole.getMasterAirwayBill(), updatedConsole.getHouseAirwayBill(), updatedConsole.getHawbType(),
                        updatedConsole.getHawbTypeId(), updatedConsole.getHawbTypeDescription(),
                        updatedConsole.getHawbTimeStamp(), updatedConsole.getPieceType(), updatedConsole.getPieceTypeId(),
                        updatedConsole.getPieceTypeDescription(), updatedConsole.getPieceTimeStamp(), loginUserID,
                        updatedConsole.getPartnerHouseAirwayBill(), updatedConsole.getPartnerMasterAirwayBill(), null,
                        updatedConsole.getHubCode(), updatedConsole.getHubName());

                consoleList.add(updatedConsole);
            }
        }
        return consoleList;
    }


    /**
     * Update Mobile App & Status Event Update Mobile App Join API
     *
     * @param updateConsoleList
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    public List<Console> updateConsoleStatusOrForMobileApp(List<UpdateConsole> updateConsoleList, String
            loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            List<Console> result = new ArrayList<>();
            List<UpdateConsole> statusUpdateList = new ArrayList<>();
            List<UpdateConsole> mobileAppUpdateList = new ArrayList<>();

            // Separate consoles based on HAWBTypeId
            for (UpdateConsole updateConsole : updateConsoleList) {
                log.info(" HAWB_TYPE " + updateConsole.getHawbTypeId());
                log.info(" Update Console: {}", updateConsole);
                if ("45".equals(updateConsole.getHawbTypeId()) || updateConsole.getHawbTypeId() == null) {
                    mobileAppUpdateList.add(updateConsole);
                } else {
                    statusUpdateList.add(updateConsole);
                }
            }

            // Call updateConsoleForMobileApp for consoles with hawbTypeId 45 or null
            if (!mobileAppUpdateList.isEmpty()) {
                result.addAll(updateConsoleForMobileApp(mobileAppUpdateList, loginUserID));
            }

            // Manually Map UpdateConsole to ConsoleStatus
            List<ConsoleStatus> consoleStatuses = new ArrayList<>();
            for (UpdateConsole updateConsole : statusUpdateList) {
                ConsoleStatus consoleStatus = new ConsoleStatus();
                consoleStatus.setLanguageId(updateConsole.getLanguageId());
                consoleStatus.setCompanyId(updateConsole.getCompanyId());
                consoleStatus.setPartnerId(updateConsole.getPartnerId());
                consoleStatus.setPartnerMasterAirwayBill(updateConsole.getPartnerMasterAirwayBill());
                consoleStatus.setPartnerHouseAirwayBill(updateConsole.getPartnerHouseAirwayBill());
                consoleStatus.setConsoleId(updateConsole.getConsoleId());
                consoleStatus.setPieceId(updateConsole.getPieceId());
                consoleStatus.setHawbTypeId(updateConsole.getHawbTypeId());
                consoleStatus.setHubCode(updateConsole.getHubCode());
                consoleStatus.setUnconsolidatedFlag(updateConsole.getUnconsolidatedFlag());

                consoleStatuses.add(consoleStatus);
            }

            // Call updateConsoleStatus for other consoles
            if (!consoleStatuses.isEmpty()) {
                result.addAll(updateConsoleStatus(consoleStatuses, loginUserID));
            }
            log.info(" Updated Console: {}", result);
            // Send Notification
//            notificationService.sendNotificationForMobileAppCreate(result.get(0).getCompanyId(),result.get(0).getLanguageId());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * UpdateConsole
     *
     * @param updateConsoleList
     * @param loginUserID
     * @return
     */
    public List<Console> updateConsoleStatus(List<ConsoleStatus> updateConsoleList, String loginUserID) throws
            IOException, InvocationTargetException,
            IllegalAccessException, CsvException {
        try {
            List<Console> consoleList = new ArrayList<>();

            for (ConsoleStatus updateConsole : updateConsoleList) {
                log.info("UpdateConsole : {}", updateConsole);
                // UnConsolidation Create
                if (updateConsole.getUnconsolidatedFlag() != null && updateConsole.getUnconsolidatedFlag() == 1L) {
                    AddUnconsolidation addUnconsolidation = new AddUnconsolidation();
                    BeanUtils.copyProperties(updateConsole, addUnconsolidation, CommonUtils.getNullPropertyNames(updateConsole));
                    unconsolidationService.generateUnconsolidation(addUnconsolidation, loginUserID);
                    return Collections.emptyList();
                } else {
                    log.info("UnConsolidatedFlag <---------------------------------------------> {} ", updateConsole.getUnconsolidatedFlag());

                    Console dbConsole =
                            consoleRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndConsoleIdAndPieceIdAndDeletionIndicator(
                                    updateConsole.getLanguageId(), updateConsole.getCompanyId(), updateConsole.getPartnerId(), updateConsole.getPartnerMasterAirwayBill(),
                                    updateConsole.getPartnerHouseAirwayBill(), updateConsole.getConsoleId(), updateConsole.getPieceId(), 0L);

                    if (dbConsole == null) {
                        throw new BadRequestException("Given Values Doesn't exist");
                    }
                    log.info("Console Update HwbTypeId: {} ", updateConsole.getHawbTypeId());
                    BeanUtils.copyProperties(updateConsole, dbConsole, CommonUtils.getNullPropertyNames(updateConsole));

                    Optional<IKeyValuePair> getStatusOpt =
                            consignmentEntityRepository.statusText(dbConsole.getCompanyId(), dbConsole.getLanguageId(), dbConsole.getHawbTypeId());

                    if (getStatusOpt.isPresent()) {
                        IKeyValuePair ikey = getStatusOpt.get();

                        dbConsole.setHawbType(ikey.getType());
                        dbConsole.setHawbTypeId(updateConsole.getHawbTypeId());
                        dbConsole.setHawbTypeDescription(ikey.getTypeText());
                        dbConsole.setHawbTimeStamp(new Date());

                        dbConsole.setPieceType(ikey.getType());
                        dbConsole.setPieceTypeId(updateConsole.getHawbTypeId());
                        dbConsole.setPieceTypeDescription(ikey.getTypeText());
                        dbConsole.setPieceTimeStamp(new Date());
                    }

                    // HubName
                    if (dbConsole.getHubCode() != null) {
                        String hubCodeDesc = consoleRepository.getHubName(
                                dbConsole.getCompanyId(), dbConsole.getLanguageId(), dbConsole.getHubCode());
                        if (hubCodeDesc != null) {
                            dbConsole.setHubName(hubCodeDesc);
                        }
                    }

                    //Special Approval is Value return NewRecord Create CustomCosting
                    if (dbConsole.getSpecialApprovalCharge() != null) {
                        customsCostingService.createCustomCosting(dbConsole, loginUserID);
                    }
                    dbConsole.setUpdatedBy(loginUserID);
                    dbConsole.setUpdatedOn(new Date());

                    if (dbConsole.getHawbTypeId().equalsIgnoreCase("6") || dbConsole.getHawbTypeId().equalsIgnoreCase("7")
                            || dbConsole.getHawbTypeId().equalsIgnoreCase("8")) {

                        List<ReplicaConsignmentStatus> dbConsignment = replicaConsignmentStatusRepository.findByCompanyIdAndLanguageIdAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
                                dbConsole.getCompanyId(), dbConsole.getLanguageId(), dbConsole.getHouseAirwayBill(), dbConsole.getPieceId(), 0L);

                        boolean hawbTypeIdFound = dbConsignment.stream()
                                .anyMatch(status -> status.getHawbTypeId() != null && status.getHawbTypeId()
                                        .equalsIgnoreCase("5"));
                        if (!hawbTypeIdFound) {
                            log.error("No Record Found with TypeId 5 And TypeText {}", dbConsole.getHawbType());
                            throw new BadRequestException("No Record Found with TypeId 5 And TypeText " + dbConsole.getHawbType());
                        }
                    }


                    if (dbConsole.getHawbTypeId().equalsIgnoreCase("7") && dbConsole.getIncoTerm().equalsIgnoreCase("DDU")) {
                        customsClearanceInvoiceService.createCustomsClearance(dbConsole, loginUserID);
                    }
                    Console updatedConsole = consoleRepository.save(dbConsole);

                    if (updatedConsole != null) {
                        // Update ConsignmentEntity Table
                        if (updatedConsole.getHawbType().equalsIgnoreCase("STATUS")) {
                            consoleRepository.updateConsignmentOnConsoleUpdate(
                                    updatedConsole.getLanguageId(), updatedConsole.getCompanyId(), updatedConsole.getPartnerId(),
                                    updatedConsole.getPartnerHouseAirwayBill(), updatedConsole.getPartnerMasterAirwayBill(),
                                    updatedConsole.getHawbTypeDescription(), updatedConsole.getHawbTypeId(), updatedConsole.getHawbType(),
                                    updatedConsole.getHubCode(), updatedConsole.getHubName());
                        }

                        // Update PreAlert Table
                        consoleRepository.updatePreAlertOnConsoleCreate(
                                updatedConsole.getLanguageId(), updatedConsole.getCompanyId(), updatedConsole.getPartnerId(),
                                updatedConsole.getPartnerHouseAirwayBill(), updatedConsole.getPartnerMasterAirwayBill(),
                                updatedConsole.getHawbTypeDescription(), updatedConsole.getHawbTypeId(), updatedConsole.getHawbType());

                        // Update PieceDetails Table
                        consoleRepository.updatePieceDetailsOnConsoleCreate(
                                updatedConsole.getLanguageId(), updatedConsole.getCompanyId(), updatedConsole.getPartnerId(),
                                updatedConsole.getPartnerHouseAirwayBill(), updatedConsole.getPartnerMasterAirwayBill(),
                                updatedConsole.getHawbTypeDescription(), updatedConsole.getHawbTypeId(), updatedConsole.getHawbType(),
                                updatedConsole.getPieceId());

                        // Create Consignment Status Table record with StatusID - 5
                        consignmentStatusService.insertConsignmentStatusRecord(updatedConsole.getLanguageId(), updatedConsole.getLanguageDescription(),
                                updatedConsole.getCompanyId(), updatedConsole.getCompanyName(), updatedConsole.getPieceId(),
                                updatedConsole.getMasterAirwayBill(), updatedConsole.getHouseAirwayBill(), updatedConsole.getHawbType(),
                                updatedConsole.getHawbTypeId(), updatedConsole.getHawbTypeDescription(),
                                updatedConsole.getHawbTimeStamp(), updatedConsole.getPieceType(), updatedConsole.getPieceTypeId(),
                                updatedConsole.getPieceTypeDescription(), updatedConsole.getPieceTimeStamp(), loginUserID,
                                updatedConsole.getPartnerHouseAirwayBill(), updatedConsole.getPartnerMasterAirwayBill(), updateConsole.getBagId(),
                                updateConsole.getHubCode(), updatedConsole.getHubName());

                        consoleList.add(updatedConsole);
                    }
                }
            }
            return consoleList;
        } catch (BadRequestException e) {
            log.error("Error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }


    /**
     * Delete Console
     *
     * @param deleteInputList
     * @param loginUserID
     */
    @Transactional
    public void deleteConsole(List<ConsoleDeleteInput> deleteInputList, String loginUserID) throws
            IOException, CsvException {
        try {
            if (deleteInputList != null || !deleteInputList.isEmpty()) {
                log.info("Console List ------------------------->" + deleteInputList);
                List<CompletableFuture<Void>> futures = new ArrayList<>();
                ExecutorService executor = Executors.newFixedThreadPool(10);

                deleteInputList.stream().forEach(deleteInput -> {
                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        Console dbConsole = getConsole(deleteInput.getLanguageId(), deleteInput.getCompanyId(),
                                deleteInput.getPartnerId(), deleteInput.getPartnerMasterAirwayBill(), deleteInput.getPartnerHouseAirwayBill(),
                                deleteInput.getConsoleId(), deleteInput.getPieceId());

                        if (dbConsole != null) {
                            dbConsole.setDeletionIndicator(1L);
                            dbConsole.setUpdatedBy(loginUserID);
                            dbConsole.setUpdatedOn(new Date());

                            // CCR Delete
                            ccrRepository.deleteCcr(dbConsole.getCompanyId(), dbConsole.getLanguageId(), dbConsole.getPartnerId(),
                                    dbConsole.getPartnerMasterAirwayBill(), dbConsole.getPartnerHouseAirwayBill(), loginUserID);
                            log.info("CCR Delete ---- PartnerMAB - {} PartnerHAB - {} ", dbConsole.getPartnerMasterAirwayBill(), dbConsole.getPartnerHouseAirwayBill());

                            // CustomCosting Delete
                            customsCostingRepository.deleteCCWHConsole(loginUserID, dbConsole.getConsoleId());
                            log.info("CustomCosting Delete {}", dbConsole.getConsoleId());
                            consoleRepository.save(dbConsole);
                            log.info("Delete Console List---------------------------> " + dbConsole);
                        }
                    }, executor);
                    futures.add(future);
                });
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
                executor.shutdown();
            }
        } catch (Exception e) {
            // Error Log
            createConsoleLog4(deleteInputList, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    //TransferConsole

    /**
     * @param transferConsole
     * @param loginUserID
     * @return
     */
    public List<Console> transferConsole(List<TransferConsole> transferConsole, String loginUserID) {

        List<Console> consoleList = new ArrayList<>();
        for (TransferConsole transfer : transferConsole) {
            Console newConsole = new Console();
            Console dbConsole = consoleRepository.findByPartnerHouseAirwayBillAndConsoleIdAndPieceIdAndDeletionIndicator(
                    transfer.getPartnerHouseAirwayBill(), transfer.getFromConsoleId(), transfer.getPieceId(), 0L);

            if (dbConsole == null) {
                throw new BadRequestException("FromConsole ID Not found " + transfer.getFromConsoleId() + "HouseAirwayBill" + transfer.getPartnerHouseAirwayBill());
            }
            boolean toConsole = consoleRepository.existsByConsoleIdAndDeletionIndicator(transfer.getToConsoleId(), 0L);
            if (!toConsole) {
                throw new BadRequestException("ToConsole ID Not found " + transfer.getToConsoleId());
            } else {
                BeanUtils.copyProperties(dbConsole, newConsole, CommonUtils.getNullPropertyNames(dbConsole));
                newConsole.setConsoleId(transfer.getToConsoleId());
                newConsole.setCreatedBy(loginUserID);
                newConsole.setCreatedOn(new Date());
                consoleList.add(consoleRepository.save(newConsole));
            }
            dbConsole.setDeletionIndicator(1L);
            dbConsole.setUpdatedBy(loginUserID);
            dbConsole.setUpdatedOn(new Date());
            consoleRepository.save(dbConsole);
        }
        return consoleList;
    }
    /*---------------------------------------------------REPLICA-----------------------------------------------------*/

    /**
     * Get All Console Details
     *
     * @return
     */
    public List<ReplicaConsole> getAllConsole() {
        List<ReplicaConsole> consoleList = replicaConsoleRepository.findAll();
        consoleList = consoleList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return consoleList;
    }

    /**
     * Get Console - Replica
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param partnerMasterAirwayBill
     * @param consoleId
     * @return
     */
    public ReplicaConsole getConsoleReplica(String languageId, String companyId, String partnerId,
                                            String partnerMasterAirwayBill, String partnerHouseAirwayBill, String consoleId) {
        Optional<ReplicaConsole> dbConsole =
                replicaConsoleRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndPartnerMasterAirwayBillAndPartnerHouseAirwayBillAndConsoleIdAndDeletionIndicator(
                        languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, consoleId, 0L);
        if (dbConsole.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", partnerId - " + partnerId + ", masterAirwayBill - " + partnerMasterAirwayBill
                    + ", houseAirwayBill - " + partnerHouseAirwayBill + " and consoleId - " + consoleId + " doesn't exists";
            // Error Log
            createConsoleLog(languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, consoleId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbConsole.get();
    }

    /**
     * Find Consoles by Pagination
     *
     * @param findConsole
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @return
     * @throws Exception
     */
    public Page<ReplicaConsole> findConsolesByPagination(FindConsole findConsole, Integer pageNo, Integer
            pageSize, String sortBy) throws Exception {

        log.info("given Params to fetch Consoles by Pagination --> {}", findConsole);
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        ConsoleSpecification spec = new ConsoleSpecification(findConsole);
        Page<ReplicaConsole> results = replicaConsoleRepository.findAll(spec, paging);
//        log.info("no of Consoles fetched --> {}", results.getSize());
        return results;
    }

//    // FindConsole
//    public List<ConsoleProjection> findConsoles(FindConsole findConsole) throws Exception {
//        return findConsolesAsync(findConsole).get();
//    }
//
//    /**
//     * @param findConsole
//     * @return
//     */
//    public CompletableFuture<List<ConsoleProjection>> findConsolesAsync(FindConsole findConsole) {
//        return CompletableFuture.supplyAsync(() -> {
//            try {
//                return replicaConsoleRepository.findConsolidatedData(
//                        findConsole.getLanguageId(),
//                        findConsole.getCompanyId(),
//                        findConsole.getPartnerId(),
//                        findConsole.getPartnerMasterAirwayBill(),
//                        findConsole.getPartnerHouseAirwayBill(),
//                        findConsole.getHawbTypeId(),
//                        findConsole.getUnconsolidatedFlag(),
//                        findConsole.getConsoleId());
//            } catch (Exception e) {
//                // Handle exception, possibly log it or rethrow
//                throw new RuntimeException("Failed to fetch consoles", e);
//            }
//        }, executorService);
//    }

    /**
     * Find Console
     *
     * @param findConsole
     * @return
     */
    public List<ConsoleProjection> findConsoles(FindConsole findConsole) {

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        List<ConsoleProjection> consoleProjectionList = new CopyOnWriteArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            List<ConsoleProjection> consoleProjections = replicaConsoleRepository.findConsolidatedData(
                    findConsole.getLanguageId(),
                    findConsole.getCompanyId(),
                    findConsole.getPartnerId(),
                    findConsole.getPartnerMasterAirwayBill(),
                    findConsole.getPartnerHouseAirwayBill(),
                    findConsole.getHawbTypeId(),
                    findConsole.getUnconsolidatedFlag(),
                    findConsole.getConsoleId(),
                    findConsole.getSubCustomerId());
            consoleProjectionList.addAll(consoleProjections);
        }, executor);
        futures.add(future);
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
        return consoleProjectionList;
    }

    /**
     * Find Consoles - MobileApp
     *
     * @param findConsole
     * @return
     * @throws Exception
     */
    public List<ReplicaConsole> findConsolesMobileApp(FindConsole findConsole) throws Exception {

        if (findConsole.getShippingLabelNo() == null) {
            throw new BadRequestException("ShippingLabelNo cannot be null");
        }
        log.info("given Params to fetch Consoles for Mobile App with Qry --> {}", findConsole);

        List<String> languageId = findConsole.getLanguageId();
        List<String> companyId = findConsole.getCompanyId();
        List<String> partnerId = findConsole.getPartnerId();
        List<String> partnerMasterAirwayBill = findConsole.getPartnerMasterAirwayBill();
        List<String> partnerHouseAirwayBill = findConsole.getPartnerHouseAirwayBill();
        List<String> shippingLabelNo = findConsole.getShippingLabelNo();
        List<String> consoleId = findConsole.getConsoleId();
        List<Long> unconsolidatedFlag = findConsole.getUnconsolidatedFlag();
        List<String> hawbTypeId = findConsole.getHawbTypeId();

        // Initially pass shippingLabelNo to partnerHouseAirwayBill
        List<ReplicaConsole> consoleList = replicaConsoleRepository.findConsolesWithQry(
                languageId, companyId, partnerId, partnerMasterAirwayBill, shippingLabelNo, consoleId, unconsolidatedFlag, hawbTypeId);

        if (consoleList == null || consoleList.isEmpty()) {
            // Else pass shippingLabelNo to consoleId
            consoleList = replicaConsoleRepository.findConsolesWithQry(
                    languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, shippingLabelNo, unconsolidatedFlag, hawbTypeId);
        }
        if (consoleList == null || consoleList.isEmpty()) {
//            throw new BadRequestException("No console Data found for given params : shippingLabelNo - " + findConsole.getShippingLabelNo());
            log.warn("No console data found for given params: shippingLabelNo - {}", findConsole.getShippingLabelNo());
            return Collections.emptyList();
        }
        return consoleList;
    }

    //==========================================Console_ErrorLog================================================
    private void createConsoleLog(String languageId, String companyId, String partnerId, String masterAirwayBill,
                                  String houseAirwayBill, String consoleId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(masterAirwayBill);
        errorLog.setMethod("Exception thrown in getConsole");
        errorLog.setReferenceField1(houseAirwayBill);
        errorLog.setReferenceField2(partnerId);
        errorLog.setReferenceField3(consoleId);
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createConsoleLog2(List<AddConsole> addConsoleList, String error) throws
            IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (AddConsole addConsole : addConsoleList) {
            ErrorLog errorLog = new ErrorLog();

            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(addConsole.getLanguageId());
            errorLog.setCompanyId(addConsole.getCompanyId());
            errorLog.setRefDocNumber(addConsole.getPartnerMasterAirwayBill());
            errorLog.setMethod("Exception thrown in createConsole");
            errorLog.setReferenceField1(addConsole.getPartnerId());
            errorLog.setReferenceField2(addConsole.getPartnerHouseAirwayBill());
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }

    private void createConsoleLog3(List<UpdateConsole> updateConsoleList, String error) throws
            IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (UpdateConsole updateConsole : updateConsoleList) {
            ErrorLog errorLog = new ErrorLog();

            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(updateConsole.getLanguageId());
            errorLog.setCompanyId(updateConsole.getCompanyId());
            errorLog.setRefDocNumber(updateConsole.getPartnerMasterAirwayBill());
            errorLog.setMethod("Exception thrown in updateConsole");
            errorLog.setReferenceField1(updateConsole.getPartnerId());
            errorLog.setReferenceField2(updateConsole.getPartnerHouseAirwayBill());
            errorLog.setReferenceField3(updateConsole.getConsoleId());
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }

    private void createConsoleLog4(List<ConsoleDeleteInput> deleteInputList, String error) throws
            IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        for (ConsoleDeleteInput deleteInput : deleteInputList) {
            ErrorLog errorLog = new ErrorLog();

            errorLog.setLogDate(new Date());
            errorLog.setLanguageId(deleteInput.getLanguageId());
            errorLog.setCompanyId(deleteInput.getCompanyId());
            errorLog.setRefDocNumber(deleteInput.getPartnerMasterAirwayBill());
            errorLog.setMethod("Exception thrown in deleteConsole");
            errorLog.setReferenceField1(deleteInput.getPartnerId());
            errorLog.setReferenceField2(deleteInput.getPartnerHouseAirwayBill());
            errorLog.setReferenceField3(deleteInput.getConsoleId());
            errorLog.setErrorMessage(error);
            errorLog.setCreatedBy("Admin");
            errorLogRepository.save(errorLog);
            errorLogList.add(errorLog);
        }
        errorLogService.writeLog(errorLogList);
    }


    //Mobile App
    public List<MobileApp> getAllMobileApp() {
        return replicaConsoleRepository.getMobileApp();
    }

    // Get ConsoleId
    public List<Console> getConsole(String consoleId) {
        return consoleRepository.findByConsoleIdAndDeletionIndicator(consoleId, 0L);
    }

    /**
     * ManualCreateConsole
     *
     * @param consoles
     * @param loginUserID
     * @return
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws CsvException
     */
    public List<Console> manualCreateConsole(List<Console> consoles, String loginUserID)
            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
        List<Console> consoleList = new ArrayList<>();

        consoles.stream().forEach(exConsole -> {
            Console addConsole = new Console();
            Console dbConsole = getConsole(exConsole.getLanguageId(), exConsole.getCompanyId(), exConsole.getPartnerId(),
                    exConsole.getPartnerMasterAirwayBill(), exConsole.getPartnerHouseAirwayBill(), exConsole.getConsoleId(),
                    exConsole.getPieceId());
            dbConsole.setDeletionIndicator(1L);
            dbConsole.setUpdatedBy(loginUserID);
            dbConsole.setUpdatedOn(new Date());
            Console saveConsole = consoleRepository.save(dbConsole);
            log.info("Console Delete successfully");
            BeanUtils.copyProperties(saveConsole, addConsole);
            consoleList.add(addConsole);
        });
        return consoleServiceV2.processNoOfCountLessThenNoOfShipment(consoleList, loginUserID);
    }


    // Send Notification

    /**
     * @param companyId
     * @param languageId
     * @param consoleId
     * @param houseAirwayBill
     */
    public void sendNotificationForConsoleCreate(String companyId, String languageId, String consoleId, String houseAirwayBill, String consoleName) {

        try {
            // Get NotificationId
            IKeyValuePair notifyId = replicaCcrRepository.getNotificationId(companyId, languageId, "2");

            if (notifyId == null || notifyId.getUserRole() == null) {
                log.warn("Notification ID or User Role not found for companyId: {}, languageId: {}", companyId, languageId);
                return;
            }
            List<String> userIds = replicaCcrRepository.getUserId(companyId, languageId, notifyId.getUserRole());
            if (userIds.isEmpty()) {
                log.warn("No Users found for the specified role: {}, companyId: {}", notifyId.getUserRole(), companyId);
                return;
            }
            List<String> deviceToken = replicaCcrRepository.getToken(companyId, userIds);
            if (deviceToken == null || deviceToken.isEmpty()) {
                log.warn("No device token found for users : {}", userIds);
                return;
            }

            String title = "Console";
            String message = notifyId.getNotificationText() + "(Console Id / " + consoleId + " Console Name / " + consoleName + " )";
            String response = pushNotificationService.sendPushNotification(
                    deviceToken, title, message, companyId, languageId, houseAirwayBill, consoleId);

            if (response.equalsIgnoreCase("OK")) {
                log.info("Notification sent successfully. Updating console table. ");
                ccrRepository.updateNotificationInConsoleTable(companyId, languageId, consoleId);
            } else {
                log.warn("Failed to send notification. Response: {}", response);
            }
        } catch (Exception e) {
            log.error("Exception occurred while sending notification for Console Create", e);
        }
    }

    /**
     * @param consoleId
     * @return
     */
    public List<HsCode> findHsCode(ConsoleRequest consoleId) {
        List<HsCode> hsCodeList = new ArrayList<>();
        consoleId.getConsoleId().parallelStream().forEach(cl -> {
            List<Object[]> multipleHsCode = replicaConsoleRepository.getHsCode(cl);
            multipleHsCode.parallelStream().forEach(row -> {
                String hsCode = (String) row[0];
                String companyCode = (String) row[1];
                List<Object[]> specialApproval = replicaConsoleRepository.getSpecialApprovalList(companyCode, hsCode);
                specialApproval.parallelStream().forEach(sp -> {
                    String spId = (String) sp[0];
                    String spText = (String) sp[1];
                    HsCode newHsCode = new HsCode();
                    newHsCode.setConsoleId(cl);
                    newHsCode.setHsCode(hsCode);
                    newHsCode.setSpecialApprovalId(spId);
                    newHsCode.setSpecialApprovalText(spText);
                    hsCodeList.add(newHsCode);
                });
            });
        });
        return hsCodeList;
    }


    // Console Upload
    public List<Console> consoleUploadUpdate(List<Console> console, String loginUserID) {
        List<Console> consoleList = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<CompletableFuture<Void>> futures = new CopyOnWriteArrayList<>();
        Set<String> processedConsoleIds = Collections.synchronizedSet(new HashSet<>());
        Map<String, String> consoleIdToNumberRangeMap = new ConcurrentHashMap<>();
        Map<String, String> ccrTdToNumberRangeMap = new ConcurrentHashMap<>();
        console.forEach(con -> {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    String consoleId;
                    String ccrId;
                    synchronized (processedConsoleIds) {
                        if (!processedConsoleIds.contains(con.getConsoleId())) {
                            consoleId = generateUniqueNumberRange("CONSOLEID");
                            ccrId = generateUniqueNumberRange("CCRID");

                            consoleIdToNumberRangeMap.put(con.getConsoleId(), consoleId);
                            ccrTdToNumberRangeMap.put(con.getConsoleId(), ccrId);
                            processedConsoleIds.add(con.getConsoleId());
                        } else {
                            consoleId = consoleIdToNumberRangeMap.get(con.getConsoleId());
                            ccrId = ccrTdToNumberRangeMap.get(con.getConsoleId());
                        }
                    }
                    Console dbConsole = consoleRepository.findByPartnerHouseAirwayBillAndDeletionIndicator(con.getPartnerHouseAirwayBill(), 0L);

                    if (dbConsole != null) {
                        BeanUtils.copyProperties(con, dbConsole, CommonUtils.getNullPropertyNames(con));
                        ccrRepository.updateCcr(con.getPartnerHouseAirwayBill(), consoleId, ccrId);
                        dbConsole.setUpdatedBy(loginUserID);
                        dbConsole.setUpdatedOn(new Date());
                        dbConsole.setConsoleId(consoleId);
                        dbConsole.setCcrId(ccrId);
                        consoleRepository.save(dbConsole);
                        consoleList.add(dbConsole);
                    } else {
                        throw new BadRequestException("Console record not found for PartnerHouseAirwayBill: " + con.getPartnerHouseAirwayBill());
                    }
                } catch (Exception e) {
                    throw new BadRequestException("Console Update Failed ----> " + e.getMessage());
                }
            }, executor);
            futures.add(future);
        });
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
        return consoleList;
    }

}