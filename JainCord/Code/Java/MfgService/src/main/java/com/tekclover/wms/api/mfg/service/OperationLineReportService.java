package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.cooking.Cooking;
import com.tekclover.wms.api.mfg.model.diceslicechop.DiceSliceChop;
import com.tekclover.wms.api.mfg.model.paste.Paste;
import com.tekclover.wms.api.mfg.model.peeling.Peeling;
import com.tekclover.wms.api.mfg.model.powder.Powder;
import com.tekclover.wms.api.mfg.model.prodcutionorder.ProcessImpl;
import com.tekclover.wms.api.mfg.model.prodcutionorder.*;
import com.tekclover.wms.api.mfg.model.soaking.Soaking;
import com.tekclover.wms.api.mfg.model.sorting.Sorting;
import com.tekclover.wms.api.mfg.repository.*;
import com.tekclover.wms.api.mfg.util.CommonUtils;
import com.tekclover.wms.api.mfg.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OperationLineReportService extends BaseService {

    @Autowired
    SortingRepository sortingRepository;

    @Autowired
    SoakingRepository soakingRepository;

    @Autowired
    PeelingRepository peelingRepository;

    @Autowired
    PasteRepository pasteRepository;

    @Autowired
    PowderRepository powderRepository;

    @Autowired
    DiceSliceChopRepository diceSliceChopRepository;

    @Autowired
    CookingRepository cookingRepository;

    @Autowired
    SortingService sortingService;

    @Autowired
    SoakingService soakingService;

    @Autowired
    PeelingService peelingService;

    @Autowired
    PasteService pasteService;

    @Autowired
    PowderService powderService;

    @Autowired
    DiceSliceChopService diceSliceChopService;

    @Autowired
    CookingService cookingService;

    @Autowired
    OperationLineRepository operationLineRepository;

    @Autowired
    OperationConsumptionRepository operationConsumptionRepository;

    //---------------------------------------OperationLineReport List---------------

    public List<OperationLineReport> findOperationLineSQL(SearchOperationLineReport searchOperationLine) throws java.text.ParseException {
        if (searchOperationLine.getStartCreatedOn() != null && searchOperationLine.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchOperationLine.getStartCreatedOn(), searchOperationLine.getEndCreatedOn());
            searchOperationLine.setStartCreatedOn(dates[0]);
            searchOperationLine.setEndCreatedOn(dates[1]);
        }

        // Set null if empty to avoid unnecessary conditions in the query
        if (searchOperationLine.getItemCode() == null || searchOperationLine.getItemCode().isEmpty()) {
            searchOperationLine.setItemCode(null);
        }
        if (searchOperationLine.getProductionOrderNo() == null || searchOperationLine.getProductionOrderNo().isEmpty()) {
            searchOperationLine.setProductionOrderNo(null);
        }
        if (searchOperationLine.getBatchNumber() == null || searchOperationLine.getBatchNumber().isEmpty()) {
            searchOperationLine.setBatchNumber(null);
        }
        if (searchOperationLine.getStartCreatedOn() == null || searchOperationLine.getEndCreatedOn() == null) {
            searchOperationLine.setStartCreatedOn(null);
            searchOperationLine.setEndCreatedOn(null);
        }
        if (searchOperationLine.getStatusId() == null || searchOperationLine.getStatusId().isEmpty()) {
            searchOperationLine.setStatusId(null);
        }

        log.info("OperationLine Search Input - SQL: {}", searchOperationLine);
        List<OperationLineImpl> results = operationLineRepository.findOperationLine(
                searchOperationLine.getLanguageId(),
                searchOperationLine.getCompanyCodeId(),
                searchOperationLine.getPlantId(),
                searchOperationLine.getWarehouseId(),
                searchOperationLine.getItemCode(),
                searchOperationLine.getProductionOrderNo(),
                searchOperationLine.getBatchNumber(),
                searchOperationLine.getStartCreatedOn(),
                searchOperationLine.getEndCreatedOn(),
                searchOperationLine.getStatusId());
        log.info("OperationLine Search Output: {}", results.size());

        List<OperationLineReport> operationLineReportList = new ArrayList<>();
        if (results != null && !results.isEmpty()) {
            for (OperationLineImpl newOperationLineReport : results) {
                OperationLineReport dbOperationLineReport = new OperationLineReport();

                BeanUtils.copyProperties(newOperationLineReport, dbOperationLineReport, CommonUtils.getNullPropertyNames(newOperationLineReport));

                List<OperationConsumptionImpl> operationConsumptionList = operationConsumptionRepository.findOperationConsumption(dbOperationLineReport.getItemCode(),
                        dbOperationLineReport.getBatchNumber(), dbOperationLineReport.getProductionOrderNo());

                List<ProcessImpl> processList = new ArrayList<>();

                List<ProcessImpl> sorting = sortingRepository.findSorting(
                        dbOperationLineReport.getItemCode(), dbOperationLineReport.getBatchNumber(), dbOperationLineReport.getProductionOrderNo());

                List<ProcessImpl> soaking = soakingRepository.findSoaking(dbOperationLineReport.getItemCode(), dbOperationLineReport.getBatchNumber(), dbOperationLineReport.getProductionOrderNo());

                List<ProcessImpl> peeling = peelingRepository.findPeeling(dbOperationLineReport.getItemCode(), dbOperationLineReport.getBatchNumber(), dbOperationLineReport.getProductionOrderNo());

                List<ProcessImpl> paste = pasteRepository.findPaste(dbOperationLineReport.getItemCode(), dbOperationLineReport.getBatchNumber(), dbOperationLineReport.getProductionOrderNo());

                List<ProcessImpl> powder = powderRepository.findPowder(dbOperationLineReport.getItemCode(), dbOperationLineReport.getBatchNumber(), dbOperationLineReport.getProductionOrderNo());

                List<ProcessImpl> diceSliceChop = diceSliceChopRepository.findDiceSliceChop(dbOperationLineReport.getItemCode(), dbOperationLineReport.getBatchNumber(), dbOperationLineReport.getProductionOrderNo());

                List<ProcessImpl> cooking = cookingRepository.findCooking(dbOperationLineReport.getItemCode(), dbOperationLineReport.getBatchNumber(), dbOperationLineReport.getProductionOrderNo());

                if (sorting != null && !sorting.isEmpty()) {
                    processList.addAll(sorting);
                }
                if (soaking != null && !soaking.isEmpty()) {
                    processList.addAll(soaking);
                }
                if (peeling != null && !peeling.isEmpty()) {
                    processList.addAll(peeling);
                }
                if (paste != null && !paste.isEmpty()) {
                    processList.addAll(paste);
                }
                if (powder != null && !powder.isEmpty()) {
                    processList.addAll(powder);
                }
                if (diceSliceChop != null && !diceSliceChop.isEmpty()) {
                    processList.addAll(diceSliceChop);
                }
                if (cooking != null && !cooking.isEmpty()) {
                    processList.addAll(cooking);
                }

                dbOperationLineReport.setOperationConsumptionReports(operationConsumptionList);
                dbOperationLineReport.setProcess(processList);
                operationLineReportList.add(dbOperationLineReport);
            }
        }
        return operationLineReportList;
    }

    /**
     * @param searchOperationLine
     * @return
     */
    public ProcessReport findProcess(SearchOperationLineReportProcess searchOperationLine) {

        try {
            ProcessReport processReport = new ProcessReport();

            //This code is for Process Report Alone
            if (searchOperationLine.getProcess() != null && !searchOperationLine.getProcess().isEmpty()) {
                if (searchOperationLine.getProcess().stream().anyMatch(n -> n.equalsIgnoreCase(SORTING))) {
                    List<Sorting> sorting = sortingService.getSortingV2(searchOperationLine);
                    if (sorting != null && !sorting.isEmpty()) {
                        processReport.setSorting(sorting);
                    }
                }
                if (searchOperationLine.getProcess().stream().anyMatch(n -> n.equalsIgnoreCase(SOAKING))) {
                    List<Soaking> soaking = soakingService.getSoakingV2(searchOperationLine);
                    if (soaking != null && !soaking.isEmpty()) {
                        processReport.setSoaking(soaking);
                    }
                }
                if (searchOperationLine.getProcess().stream().anyMatch(n -> n.equalsIgnoreCase(PEELING))) {
                    List<Peeling> peeling = peelingService.getPeelingV2(searchOperationLine);
                    if (peeling != null && !peeling.isEmpty()) {
                        processReport.setPeeling(peeling);
                    }
                }
                if (searchOperationLine.getProcess().stream().anyMatch(n -> n.equalsIgnoreCase(PASTE))) {
                    List<Paste> paste = pasteService.getPasteV2(searchOperationLine);
                    if (paste != null && !paste.isEmpty()) {
                        processReport.setPaste(paste);
                    }
                }
                if (searchOperationLine.getProcess().stream().anyMatch(n -> n.equalsIgnoreCase(POWDER))) {
                    List<Powder> powder = powderService.getPowderV2(searchOperationLine);
                    if (powder != null && !powder.isEmpty()) {
                        processReport.setPowder(powder);
                    }
                }
                if (searchOperationLine.getProcess().stream().anyMatch(n -> n.equalsIgnoreCase(DICESLICECHOP))) {
                    List<DiceSliceChop> diceSliceChop = diceSliceChopService.getDiceSliceChopV2(searchOperationLine);
                    if (diceSliceChop != null && !diceSliceChop.isEmpty()) {
                        processReport.setDiceSliceChop(diceSliceChop);
                    }
                }
                if (searchOperationLine.getProcess().stream().anyMatch(n -> n.equalsIgnoreCase(COOKING))) {
                    List<Cooking> cooking = cookingService.getCookingV2(searchOperationLine);
                    if (cooking != null && !cooking.isEmpty()) {
                        processReport.setCooking(cooking);
                    }
                }
                return processReport;
            }
            List<Sorting> sorting = sortingService.getSortingV2(searchOperationLine);
            List<Soaking> soaking = soakingService.getSoakingV2(searchOperationLine);
            List<Peeling> peeling = peelingService.getPeelingV2(searchOperationLine);
            List<Paste> paste = pasteService.getPasteV2(searchOperationLine);
            List<Powder> powder = powderService.getPowderV2(searchOperationLine);
            List<DiceSliceChop> diceSliceChop = diceSliceChopService.getDiceSliceChopV2(searchOperationLine);
            List<Cooking> cooking = cookingService.getCookingV2(searchOperationLine);

            if (sorting != null && !sorting.isEmpty()) {
                processReport.setSorting(sorting);
            }
            if (soaking != null && !soaking.isEmpty()) {
                processReport.setSoaking(soaking);
            }
            if (peeling != null && !peeling.isEmpty()) {
                processReport.setPeeling(peeling);
            }
            if (paste != null && !paste.isEmpty()) {
                processReport.setPaste(paste);
            }
            if (powder != null && !powder.isEmpty()) {
                processReport.setPowder(powder);
            }
            if (diceSliceChop != null && !diceSliceChop.isEmpty()) {
                processReport.setDiceSliceChop(diceSliceChop);
            }
            if (cooking != null && !cooking.isEmpty()) {
                processReport.setCooking(cooking);
            }

            return processReport;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    //--------------------------------------------OperationLineV2Report---------------------------------

//    public List<OperationLineReport> findOperationLineV2SQL(SearchOperationLineReport searchOperationLine) throws java.text.ParseException {
//        if (searchOperationLine.getStartCreatedOn() != null && searchOperationLine.getEndCreatedOn() != null) {
//            Date[] dates = DateUtils.addTimeToDatesForSearch(searchOperationLine.getStartCreatedOn(), searchOperationLine.getEndCreatedOn());
//            searchOperationLine.setStartCreatedOn(dates[0]);
//            searchOperationLine.setEndCreatedOn(dates[1]);
//        }
//
//        // Set null if empty to avoid unnecessary conditions in the query
//        if (searchOperationLine.getItemCode() == null || searchOperationLine.getItemCode().isEmpty()) {
//            searchOperationLine.setItemCode(null);
//        }
//        if (searchOperationLine.getProductionOrderNo() == null || searchOperationLine.getProductionOrderNo().isEmpty()) {
//            searchOperationLine.setProductionOrderNo(null);
//        }
//        if (searchOperationLine.getBatchNumber() == null || searchOperationLine.getBatchNumber().isEmpty()) {
//            searchOperationLine.setBatchNumber(null);
//        }
//        if (searchOperationLine.getStartCreatedOn() == null || searchOperationLine.getEndCreatedOn() == null) {
//            searchOperationLine.setStartCreatedOn(null);
//            searchOperationLine.setEndCreatedOn(null);
//        }
//        if (searchOperationLine.getStatusId() == null || searchOperationLine.getStatusId().isEmpty()) {
//            searchOperationLine.setStatusId(null);
//        }
//
//        log.info("OperationLine Search Input - SQL: {}", searchOperationLine);
//        List<OperationLineImpl> results = operationLineRepository.findOperationLine(
//                searchOperationLine.getLanguageId(),
//                searchOperationLine.getCompanyCodeId(),
//                searchOperationLine.getPlantId(),
//                searchOperationLine.getWarehouseId(),
//                searchOperationLine.getItemCode(),
//                searchOperationLine.getProductionOrderNo(),
//                searchOperationLine.getBatchNumber(),
//                searchOperationLine.getStartCreatedOn(),
//                searchOperationLine.getEndCreatedOn(),
//                searchOperationLine.getStatusId());
//        log.info("OperationLine Search Output: {}", results.size());
//
//        if (results == null || results.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        List<OperationLineReport> operationLineReportList = new ArrayList<>();
//        Set<String> itemCodes = new HashSet<>();
//        Set<String> productionOrderNos = new HashSet<>();
//
//        for (OperationLineImpl result : results) {
//            itemCodes.add(result.getItemCode());
//            productionOrderNos.add(result.getProductionOrderNo());
//        }
//
//        // Batch fetching related data
//        List<OperationConsumptionImpl> operationConsumptionList = operationConsumptionRepository.findOperationConsumption(new ArrayList<>(itemCodes), new ArrayList<>(productionOrderNos));
//
//        // Fetch Sorting
//        Map<String, SortingImpl> sortingMap = new HashMap<>();
//        for (String itemCode : itemCodes) {
//            SortingImpl sorting = sortingRepository.findSorting(Collections.singletonList(itemCode), Collections.singletonList(productionOrderNos.iterator().next()));
//            sortingMap.put(itemCode, sorting);
//        }
//
//        // Fetch Soaking
//        Map<String, SoakingImpl> soakingMap = new HashMap<>();
//        for (String itemCode : itemCodes) {
//            SoakingImpl soaking = soakingRepository.findSoaking(Collections.singletonList(itemCode), Collections.singletonList(productionOrderNos.iterator().next()));
//            soakingMap.put(itemCode, soaking);
//        }
//
//        // Fetch Peeling
//        Map<String, PeelingImpl> peelingMap = new HashMap<>();
//        for (String itemCode : itemCodes) {
//            PeelingImpl peeling = peelingRepository.findPeeling(Collections.singletonList(itemCode), Collections.singletonList(productionOrderNos.iterator().next()));
//            peelingMap.put(itemCode, peeling);
//        }
//
//        // Fetch Paste
//        Map<String, PasteImpl> pasteMap = new HashMap<>();
//        for (String itemCode : itemCodes) {
//            PasteImpl paste = pasteRepository.findPaste(Collections.singletonList(itemCode), Collections.singletonList(productionOrderNos.iterator().next()));
//            pasteMap.put(itemCode, paste);
//        }
//
//        // Fetch Powder
//        Map<String, PowderImpl> powderMap = new HashMap<>();
//        for (String itemCode : itemCodes) {
//            PowderImpl powder = powderRepository.findPowder(Collections.singletonList(itemCode), Collections.singletonList(productionOrderNos.iterator().next()));
//            powderMap.put(itemCode, powder);
//        }
//
//        // Fetch DiceSliceChop
//        Map<String, DiceSliceChopImpl> diceSliceChopMap = new HashMap<>();
//        for (String itemCode : itemCodes) {
//            DiceSliceChopImpl diceSliceChop = diceSliceChopRepository.findDiceSliceChop(Collections.singletonList(itemCode), Collections.singletonList(productionOrderNos.iterator().next()));
//            diceSliceChopMap.put(itemCode, diceSliceChop);
//        }
//
//        // Fetch Cooking
//        Map<String, CookingImpl> cookingMap = new HashMap<>();
//        for (String itemCode : itemCodes) {
//            CookingImpl cooking = cookingRepository.findCooking(Collections.singletonList(itemCode), Collections.singletonList(productionOrderNos.iterator().next()));
//            cookingMap.put(itemCode, cooking);
//    }
//
//        // Map results for quick lookup
//        Map<String, List<OperationConsumptionImpl>> operationConsumptionMap = operationConsumptionList.stream()
//                .collect(Collectors.groupingBy(OperationConsumptionImpl::getItemCode));
//
//
//
//        for (OperationLineImpl newOperationLineReport : results) {
//            OperationLineReport dbOperationLineReport = new OperationLineReport();
//            Process process = new Process();
//            BeanUtils.copyProperties(newOperationLineReport, dbOperationLineReport, CommonUtils.getNullPropertyNames(newOperationLineReport));
//            dbOperationLineReport.setOperationConsumptionReports(operationConsumptionMap.getOrDefault(dbOperationLineReport.getItemCode(), Collections.emptyList()));
//            process.setSorting(sortingMap.get(dbOperationLineReport.getItemCode()));
//            process.setSoaking(soakingMap.get(dbOperationLineReport.getItemCode()));
//            process.setPeeling(peelingMap.get(dbOperationLineReport.getItemCode()));
//            process.setPaste(pasteMap.get(dbOperationLineReport.getItemCode()));
//            process.setPowder(powderMap.get(dbOperationLineReport.getItemCode()));
//            process.setDiceSliceChop(diceSliceChopMap.get(dbOperationLineReport.getItemCode()));
//            process.setCooking(cookingMap.get(dbOperationLineReport.getItemCode()));
//            dbOperationLineReport.setProcess(process);
//            operationLineReportList.add(dbOperationLineReport);
//        }
//
//        return operationLineReportList;
//    }

    //--------------------------------------OperationConsumption------------------------------------
//
//    public List<OperationConsumptionImpl> findOperationConsumptionSQL(SearchOperationConsumption searchOperationConsumption) throws java.text.ParseException {
//        if (searchOperationConsumption.getStartConfirmedOn() != null && searchOperationConsumption.getEndConfirmedOn() != null) {
//            Date[] dates = DateUtils.addTimeToDatesForSearch(searchOperationConsumption.getStartConfirmedOn(), searchOperationConsumption.getEndConfirmedOn());
//            searchOperationConsumption.setStartConfirmedOn(dates[0]);
//            searchOperationConsumption.setEndConfirmedOn(dates[1]);
//            }
//
//        // Set null if empty to avoid unnecessary conditions in the query
//        if (searchOperationConsumption.getItemCode() == null || searchOperationConsumption.getItemCode().isEmpty()) {
//            searchOperationConsumption.setItemCode(null);
//        }
//
//
//        log.info("OperationConsumption Search Input - SQL: {}", searchOperationConsumption);
//        List<OperationConsumptionImpl> results = operationConsumptionRepository.findOperationConsumption(
//                searchOperationConsumption.getItemCode(),
//                searchOperationConsumption.getProductionOrderNo());
//        log.info("OperationConsumption Search Output: {}", results.size());
//        return results;
//    }


    //---------------------------------------Sorting List---------------------------

//    public SortingImpl findSortingSQL(SearchSorting searchSorting) throws java.text.ParseException {
//        if (searchSorting.getStartUpdatedOn() != null && searchSorting.getEndUpdatedOn() != null) {
//            Date[] dates = DateUtils.addTimeToDatesForSearch(searchSorting.getStartUpdatedOn(), searchSorting.getEndUpdatedOn());
//            searchSorting.setStartUpdatedOn(dates[0]);
//            searchSorting.setEndUpdatedOn(dates[1]);
//        }
//
//        // Set null if empty to avoid unnecessary conditions in the query
//        if (searchSorting.getProductionOrderNo() == null || searchSorting.getProductionOrderNo().isEmpty()) {
//            searchSorting.setProductionOrderNo(null);
//        }
//        if (searchSorting.getSorting() == null || searchSorting.getSorting().isEmpty()) {
//            searchSorting.setSorting(null);
//        }
//        if (searchSorting.getStatusDescription() == null || searchSorting.getStatusDescription().isEmpty()) {
//            searchSorting.setStatusDescription(null);
//        }
//        if (searchSorting.getUpdatedBy() == null || searchSorting.getUpdatedBy().isEmpty()) {
//            searchSorting.setUpdatedBy(null);
//        }
//
//        log.info("Sorting Search Input - SQL: {}", searchSorting);
//        SortingImpl results = sortingRepository.findSorting(
//                searchSorting.getItemCode(),
//                searchSorting.getProductionOrderNo());
//        log.info("Sorting Search Output: {}", results);
//        return results;
//    }
//
//    //--------------------------------------SoakingList----------------------------------
//
//    public SoakingImpl findSoakingSQL(SearchSoaking searchSoaking) throws java.text.ParseException {
//        if (searchSoaking.getStartUpdatedOn() != null && searchSoaking.getEndUpdatedOn() != null) {
//            Date[] dates = DateUtils.addTimeToDatesForSearch(searchSoaking.getStartUpdatedOn(), searchSoaking.getEndUpdatedOn());
//            searchSoaking.setStartUpdatedOn(dates[0]);
//            searchSoaking.setEndUpdatedOn(dates[1]);
//        }
//
//        // Set null if empty to avoid unnecessary conditions in the query
//        if (searchSoaking.getProductionOrderNo() == null || searchSoaking.getProductionOrderNo().isEmpty()) {
//            searchSoaking.setProductionOrderNo(null);
//        }
//        if (searchSoaking.getSoaking() == null || searchSoaking.getSoaking().isEmpty()) {
//            searchSoaking.setSoaking(null);
//        }
//        if (searchSoaking.getStatusDescription() == null || searchSoaking.getStatusDescription().isEmpty()) {
//            searchSoaking.setStatusDescription(null);
//        }
//        if (searchSoaking.getUpdatedBy() == null || searchSoaking.getUpdatedBy().isEmpty()) {
//            searchSoaking.setUpdatedBy(null);
//        }
//        if (searchSoaking.getDetails() == null || searchSoaking.getDetails().isEmpty()) {
//            searchSoaking.setDetails(null);
//        }
//
//        log.info("Soaking Search Input - SQL: {}", searchSoaking);
//        SoakingImpl results = soakingRepository.findSoaking(
//                searchSoaking.getItemCode(),
//                searchSoaking.getProductionOrderNo()
//                );
//        log.info("Soaking Search Output: {}", results);
//        return results;
//    }
//
//    //----------------------------------------Peeling List--------------------------------
//
//    public PeelingImpl findPeelingSQL(SearchPeeling searchPeeling) throws java.text.ParseException {
//        if (searchPeeling.getStartUpdatedOn() != null && searchPeeling.getEndUpdatedOn() != null) {
//            Date[] dates = DateUtils.addTimeToDatesForSearch(searchPeeling.getStartUpdatedOn(), searchPeeling.getEndUpdatedOn());
//            searchPeeling.setStartUpdatedOn(dates[0]);
//            searchPeeling.setEndUpdatedOn(dates[1]);
//        }
//
//        // Set null if empty to avoid unnecessary conditions in the query
//        if (searchPeeling.getProductionOrderNo() == null || searchPeeling.getProductionOrderNo().isEmpty()) {
//            searchPeeling.setProductionOrderNo(null);
//        }
//        if (searchPeeling.getPeeling() == null || searchPeeling.getPeeling().isEmpty()) {
//            searchPeeling.setPeeling(null);
//        }
//        if (searchPeeling.getStatusDescription() == null || searchPeeling.getStatusDescription().isEmpty()) {
//            searchPeeling.setStatusDescription(null);
//        }
//        if (searchPeeling.getUpdatedBy() == null || searchPeeling.getUpdatedBy().isEmpty()) {
//            searchPeeling.setUpdatedBy(null);
//        }
//        if (searchPeeling.getDetails() == null || searchPeeling.getDetails().isEmpty()) {
//            searchPeeling.setDetails(null);
//        }
//
//        log.info("Peeling Search Input - SQL: {}", searchPeeling);
//        PeelingImpl results = peelingRepository.findPeeling(
//                searchPeeling.getItemCode(),
//                searchPeeling.getProductionOrderNo());
//        log.info("Peeling Search Output: {}", results);
//        return results;
//    }
//
//    //--------------------------------------Paste List-------------------------------------
//
//    public PasteImpl findPasteSQL(SearchPaste searchPaste) throws java.text.ParseException {
//        if (searchPaste.getStartUpdatedOn() != null && searchPaste.getEndUpdatedOn() != null) {
//            Date[] dates = DateUtils.addTimeToDatesForSearch(searchPaste.getStartUpdatedOn(), searchPaste.getEndUpdatedOn());
//            searchPaste.setStartUpdatedOn(dates[0]);
//            searchPaste.setEndUpdatedOn(dates[1]);
//        }
//
//        // Set null if empty to avoid unnecessary conditions in the query
//        if (searchPaste.getProductionOrderNo() == null || searchPaste.getProductionOrderNo().isEmpty()) {
//            searchPaste.setProductionOrderNo(null);
//        }
//        if (searchPaste.getPaste() == null || searchPaste.getPaste().isEmpty()) {
//            searchPaste.setPaste(null);
//        }
//        if (searchPaste.getStatusDescription() == null || searchPaste.getStatusDescription().isEmpty()) {
//            searchPaste.setStatusDescription(null);
//        }
//        if (searchPaste.getUpdatedBy() == null || searchPaste.getUpdatedBy().isEmpty()) {
//            searchPaste.setUpdatedBy(null);
//        }
//        if (searchPaste.getDetails() == null || searchPaste.getDetails().isEmpty()) {
//            searchPaste.setDetails(null);
//        }
//
//        log.info("Paste Search Input - SQL: {}", searchPaste);
//        PasteImpl results = pasteRepository.findPaste(
//                searchPaste.getItemCode(),
//                searchPaste.getProductionOrderNo());
//        log.info("Paste Search Output: {}", results);
//        return results;
//    }
//
//    //-------------------------------------------PowderList---------------------------------
//
//    public PowderImpl findPowderSQL(SearchPowder searchPowder) throws java.text.ParseException {
//        if (searchPowder.getStartUpdatedOn() != null && searchPowder.getEndUpdatedOn() != null) {
//            Date[] dates = DateUtils.addTimeToDatesForSearch(searchPowder.getStartUpdatedOn(), searchPowder.getEndUpdatedOn());
//            searchPowder.setStartUpdatedOn(dates[0]);
//            searchPowder.setEndUpdatedOn(dates[1]);
//        }
//
//        // Set null if empty to avoid unnecessary conditions in the query
//        if (searchPowder.getProductionOrderNo() == null || searchPowder.getProductionOrderNo().isEmpty()) {
//            searchPowder.setProductionOrderNo(null);
//        }
//        if (searchPowder.getPowder() == null || searchPowder.getPowder().isEmpty()) {
//            searchPowder.setPowder(null);
//        }
//        if (searchPowder.getStatusDescription() == null || searchPowder.getStatusDescription().isEmpty()) {
//            searchPowder.setStatusDescription(null);
//        }
//        if (searchPowder.getUpdatedBy() == null || searchPowder.getUpdatedBy().isEmpty()) {
//            searchPowder.setUpdatedBy(null);
//        }
//        if (searchPowder.getDetails() == null || searchPowder.getDetails().isEmpty()) {
//            searchPowder.setDetails(null);
//        }
//
//        log.info("Powder Search Input - SQL: {}", searchPowder);
//        PowderImpl results = powderRepository.findPowder(
//                searchPowder.getItemCode(),
//                searchPowder.getProductionOrderNo());
//        log.info("Powder Search Output: {}", results);
//        return results;
//    }
//
//    //-------------------------------------- DiceSliceChop List---------------------------
//
//    public DiceSliceChopImpl findDiceSliceChopSQL(SearchDiceSliceChop searchDiceSliceChop) throws java.text.ParseException {
//        if (searchDiceSliceChop.getStartUpdatedOn() != null && searchDiceSliceChop.getEndUpdatedOn() != null) {
//            Date[] dates = DateUtils.addTimeToDatesForSearch(searchDiceSliceChop.getStartUpdatedOn(), searchDiceSliceChop.getEndUpdatedOn());
//            searchDiceSliceChop.setStartUpdatedOn(dates[0]);
//            searchDiceSliceChop.setEndUpdatedOn(dates[1]);
//        }
//
//        // Set null if empty to avoid unnecessary conditions in the query
//        if (searchDiceSliceChop.getProductionOrderNo() == null || searchDiceSliceChop.getProductionOrderNo().isEmpty()) {
//            searchDiceSliceChop.setProductionOrderNo(null);
//        }
//        if (searchDiceSliceChop.getDiceSliceChop() == null || searchDiceSliceChop.getDiceSliceChop().isEmpty()) {
//            searchDiceSliceChop.setDiceSliceChop(null);
//        }
//        if (searchDiceSliceChop.getStatusDescription() == null || searchDiceSliceChop.getStatusDescription().isEmpty()) {
//            searchDiceSliceChop.setStatusDescription(null);
//        }
//        if (searchDiceSliceChop.getUpdatedBy() == null || searchDiceSliceChop.getUpdatedBy().isEmpty()) {
//            searchDiceSliceChop.setUpdatedBy(null);
//        }
//        if (searchDiceSliceChop.getDetails() == null || searchDiceSliceChop.getDetails().isEmpty()) {
//            searchDiceSliceChop.setDetails(null);
//        }
//
//        log.info("DiceSliceChop Search Input - SQL: {}", searchDiceSliceChop);
//        DiceSliceChopImpl results = diceSliceChopRepository.findDiceSliceChop(
//                searchDiceSliceChop.getItemCode(),
//                searchDiceSliceChop.getProductionOrderNo());
//        log.info("DiceSliceChop Search Output: {}", results);
//        return results;
//    }
//
//    //-----------------------------------Cooking List---------------------------------------
//
//    public CookingImpl findCookingSQL(SearchCooking searchCooking) throws java.text.ParseException {
//        if (searchCooking.getStartUpdatedOn() != null && searchCooking.getEndUpdatedOn() != null) {
//            Date[] dates = DateUtils.addTimeToDatesForSearch(searchCooking.getStartUpdatedOn(), searchCooking.getEndUpdatedOn());
//            searchCooking.setStartUpdatedOn(dates[0]);
//            searchCooking.setEndUpdatedOn(dates[1]);
//        }
//
//        // Set null if empty to avoid unnecessary conditions in the query
//        if (searchCooking.getProductionOrderNo() == null || searchCooking.getProductionOrderNo().isEmpty()) {
//            searchCooking.setProductionOrderNo(null);
//        }
//        if (searchCooking.getCooking() == null || searchCooking.getCooking().isEmpty()) {
//            searchCooking.setCooking(null);
//        }
//        if (searchCooking.getStatusDescription() == null || searchCooking.getStatusDescription().isEmpty()) {
//            searchCooking.setStatusDescription(null);
//        }
//        if (searchCooking.getUpdatedBy() == null || searchCooking.getUpdatedBy().isEmpty()) {
//            searchCooking.setUpdatedBy(null);
//        }
//        if (searchCooking.getDetails() == null || searchCooking.getDetails().isEmpty()) {
//            searchCooking.setDetails(null);
//        }
//
//        log.info("Cooking Search Input - SQL: {}", searchCooking);
//        CookingImpl results = cookingRepository.findCooking(
//                searchCooking.getItemCode(),
//                searchCooking.getProductionOrderNo());
//        log.info("Cooking Search Output: {}", results);
//        return results;
//    }

}