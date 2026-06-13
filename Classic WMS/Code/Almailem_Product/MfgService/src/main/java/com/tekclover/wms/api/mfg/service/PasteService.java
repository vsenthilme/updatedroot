package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.paste.Paste;
import com.tekclover.wms.api.mfg.model.paste.SearchPaste;
import com.tekclover.wms.api.mfg.model.prodcutionorder.SearchOperationLineReportProcess;
import com.tekclover.wms.api.mfg.repository.PasteRepository;
import com.tekclover.wms.api.mfg.repository.specification.PasteSpecification;
import com.tekclover.wms.api.mfg.util.CommonUtils;
import com.tekclover.wms.api.mfg.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class PasteService extends BaseService {

    @Autowired
    PasteRepository pasteRepository;

    @Autowired
    OperationConsumptionService operationConsumptionService;
    //-----------------------------------------------------Paste Service--------------------------------------------------

    /**
     * Get Paste
     *
     * @param languageId
     * @param companyCodeId
     * @param plantId
     * @param warehouseId
     * @param productionOrderNo
     * @param productionOrderLineNo
     * @param receipeId
     * @param operationNumber
     * @param itemCode
     * @return
     */
    public Paste getPaste(String languageId, String companyCodeId, String plantId, String warehouseId,
                          String productionOrderNo, Long productionOrderLineNo, String receipeId, String operationNumber, String itemCode) {
        Optional<Paste> paste =
                pasteRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        productionOrderLineNo,
                        receipeId,
                        operationNumber,
                        itemCode,
                        0L
                );
        if (paste.isEmpty()) {
            throw new BadRequestException("The given values: languageId: " + languageId +
                    ",companyCodeId: " + companyCodeId +
                    ",plantId: " + plantId +
                    ",warehouseId: " + warehouseId +
                    ",productionOrderNo: " + productionOrderNo +
                    ",productionOrderLineNo: " + productionOrderLineNo +
                    ",receipeId: " + receipeId +
                    ",operationNumber: " + operationNumber +
                    " and itemCode: " + itemCode +
                    "doesn't exist.");
        }
        return paste.get();
    }

    /**
     * Get BulkPaste
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public List<Paste> getBulkPaste(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        List<Paste> paste =
                pasteRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        0L);
        if (paste == null || paste.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",productionOrderNo: " + productionOrderNo +
                    " doesn't exist.");
        }
        return paste;
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param batchNumber
     * @return
     */
    public Paste getPaste(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        Paste paste =
                pasteRepository.findTopByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        productionOrderNo,
                        batchNumber,
                        0L);
        return paste;
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param batchNumber
     * @return
     */
    public List<Paste> getPasteV2(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        List<Paste> paste =
                pasteRepository.findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        productionOrderNo,
                        batchNumber,
                        0L);
        return paste;
    }

    /**
     *
     * @param searchOperationLineReportProcess
     * @return
     */
    public List<Paste> getPasteV2(SearchOperationLineReportProcess searchOperationLineReportProcess) {
        try {
            if (searchOperationLineReportProcess.getStartCreatedOn() != null && searchOperationLineReportProcess.getEndCreatedOn() != null) {
                Date[] dates = DateUtils.addTimeToDatesForSearch(searchOperationLineReportProcess.getStartCreatedOn(), searchOperationLineReportProcess.getEndCreatedOn());
                searchOperationLineReportProcess.setStartCreatedOn(dates[0]);
                searchOperationLineReportProcess.setEndCreatedOn(dates[1]);
            }
            if(searchOperationLineReportProcess.getCompanyCodeId() != null && searchOperationLineReportProcess.getCompanyCodeId().isEmpty()) {
                searchOperationLineReportProcess.setCompanyCodeId(null);
            }
            if(searchOperationLineReportProcess.getPlantId() != null && searchOperationLineReportProcess.getPlantId().isEmpty()) {
                searchOperationLineReportProcess.setPlantId(null);
            }
            if(searchOperationLineReportProcess.getLanguageId() != null && searchOperationLineReportProcess.getLanguageId().isEmpty()) {
                searchOperationLineReportProcess.setLanguageId(null);
            }
            if(searchOperationLineReportProcess.getWarehouseId() != null && searchOperationLineReportProcess.getWarehouseId().isEmpty()) {
                searchOperationLineReportProcess.setWarehouseId(null);
            }
            if(searchOperationLineReportProcess.getItemCode() != null && searchOperationLineReportProcess.getItemCode().isEmpty()) {
                searchOperationLineReportProcess.setItemCode(null);
            }
            if(searchOperationLineReportProcess.getBatchNumber() != null && searchOperationLineReportProcess.getBatchNumber().isEmpty()) {
                searchOperationLineReportProcess.setBatchNumber(null);
            }
            if(searchOperationLineReportProcess.getBomItem() != null && searchOperationLineReportProcess.getBomItem().isEmpty()) {
                searchOperationLineReportProcess.setBomItem(null);
            }
            if(searchOperationLineReportProcess.getPhaseNumber() != null && searchOperationLineReportProcess.getPhaseNumber().isEmpty()) {
                searchOperationLineReportProcess.setPhaseNumber(null);
            }
            if(searchOperationLineReportProcess.getOperationNumber() != null && searchOperationLineReportProcess.getOperationNumber().isEmpty()) {
                searchOperationLineReportProcess.setOperationNumber(null);
            }
            if(searchOperationLineReportProcess.getReceipeId() != null && searchOperationLineReportProcess.getReceipeId().isEmpty()) {
                searchOperationLineReportProcess.setReceipeId(null);
            }
            if(searchOperationLineReportProcess.getProductionOrderNo() != null && searchOperationLineReportProcess.getProductionOrderNo().isEmpty()) {
                searchOperationLineReportProcess.setProductionOrderNo(null);
            }
            if(searchOperationLineReportProcess.getSupervisorName() != null && searchOperationLineReportProcess.getSupervisorName().isEmpty()) {
                searchOperationLineReportProcess.setSupervisorName(null);
            }
            if(searchOperationLineReportProcess.getStatusId() != null && searchOperationLineReportProcess.getStatusId().isEmpty()) {
                searchOperationLineReportProcess.setStatusId(null);
            }
            List<Paste> paste =
                    pasteRepository.findPaste(
                            searchOperationLineReportProcess.getCompanyCodeId(),
                            searchOperationLineReportProcess.getPlantId(),
                            searchOperationLineReportProcess.getLanguageId(),
                            searchOperationLineReportProcess.getWarehouseId(),
                            searchOperationLineReportProcess.getItemCode(),
                            searchOperationLineReportProcess.getBatchNumber(),
                            searchOperationLineReportProcess.getBomItem(),
                            searchOperationLineReportProcess.getPhaseNumber(),
                            searchOperationLineReportProcess.getOperationNumber(),
                            searchOperationLineReportProcess.getReceipeId(),
                            searchOperationLineReportProcess.getProductionOrderNo(),
                            searchOperationLineReportProcess.getStatusId(),
                            searchOperationLineReportProcess.getSupervisorName(),
                            searchOperationLineReportProcess.getStartCreatedOn(),
                            searchOperationLineReportProcess.getEndCreatedOn());
            return paste;
        } catch (Exception e) {
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * create Paste
     *
     * @param addPaste
     * @param loginUserID
     * @return
     */
    public List<Paste> createPaste(List<Paste> addPaste, String loginUserID) {
        try {
            List<Paste> createdPaste = new ArrayList<>();
            if (addPaste != null && !addPaste.isEmpty()) {
                for (Paste newPaste : addPaste) {
                    if (newPaste.getProductionOrderNo() == null || newPaste.getProductionOrderLineNo() == null) {
                        throw new BadRequestException("ProductionOrderNo & Line No is must to create Paste");
                    }
                    Optional<Paste> paste =
                            pasteRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndDeletionIndicator(
                                    newPaste.getLanguageId(),
                                    newPaste.getCompanyCodeId(),
                                    newPaste.getPlantId(),
                                    newPaste.getWarehouseId(),
                                    newPaste.getProductionOrderNo(),
                                    newPaste.getProductionOrderLineNo(),
                                    newPaste.getReceipeId(),
                                    newPaste.getOperationNumber(),
                                    newPaste.getItemCode(),
                                    0L
                            );
                    if (paste.isPresent()) {
                        throw new BadRequestException("Record is getting duplicated with the given values");
                    }
                    log.info("Create Paste initiated : {}", newPaste);
                    Paste dbPaste = new Paste();
                    BeanUtils.copyProperties(newPaste, dbPaste, CommonUtils.getNullPropertyNames(newPaste));
                    if (dbPaste.getLanguageId() != null && dbPaste.getCompanyCodeId() != null &&
                            dbPaste.getPlantId() != null && dbPaste.getWarehouseId() != null) {
                        description = getDescription(dbPaste.getCompanyCodeId(), dbPaste.getPlantId(), dbPaste.getLanguageId(), dbPaste.getWarehouseId());
                        if (description != null) {
                            dbPaste.setCompanyDescription(description.getCompanyDescription());
                            dbPaste.setPlantDescription(description.getPlantDescription());
                            dbPaste.setWarehouseDescription(description.getWarehouseDescription());
                        }
                    }
                    if (dbPaste.getStatusId() != null && dbPaste.getLanguageId() != null) {
                        statusDescription = getStatusDescription(dbPaste.getStatusId(), dbPaste.getLanguageId());
                        if (statusDescription != null) {
                            dbPaste.setStatusDescription(statusDescription);
                        }
                    }
                    dbPaste.setDeletionIndicator(0L);
                    dbPaste.setCreatedBy(loginUserID);
                    dbPaste.setCreatedOn(new Date());
                    pasteRepository.save(dbPaste);
                    createdPaste.add(dbPaste);
//                    OperationConsumption operationConsumption = new OperationConsumption();
//                    BeanUtils.copyProperties(dbPaste, operationConsumption, CommonUtils.getNullPropertyNames(dbPaste));
//                    operationConsumptionService.createOperationConsumption(operationConsumption, loginUserID);
                }
            }
            return createdPaste;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param newPaste
     * @param loginUserID
     * @return
     */
    public Paste createPaste(Paste newPaste, String loginUserID) {
        try {
            if (newPaste.getProductionOrderNo() == null || newPaste.getProductionOrderLineNo() == null) {
                throw new BadRequestException("ProductionOrderNo & Line No is must to create Paste");
            }
//            Optional<Paste> paste =
//                    pasteRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
//                            newPaste.getLanguageId(),
//                            newPaste.getCompanyCodeId(),
//                            newPaste.getPlantId(),
//                            newPaste.getWarehouseId(),
//                            newPaste.getProductionOrderNo(),
//                            newPaste.getProductionOrderLineNo(),
//                            newPaste.getReceipeId(),
//                            newPaste.getOperationNumber(),
//                            newPaste.getItemCode(),
//                            newPaste.getPhaseNumber(),
//                            newPaste.getBomItem(),
//                            newPaste.getBatchNumber(),
//                            0L);
//            if (paste.isPresent()) {
//                throw new BadRequestException("Record is getting duplicated with the given values");
//            }
            log.info("Create Paste initiated : {}", newPaste);
            Paste dbPaste = new Paste();
            BeanUtils.copyProperties(newPaste, dbPaste, CommonUtils.getNullPropertyNames(newPaste));
            if (dbPaste.getLanguageId() != null && dbPaste.getCompanyCodeId() != null &&
                    dbPaste.getPlantId() != null && dbPaste.getWarehouseId() != null) {
                description = getDescription(dbPaste.getCompanyCodeId(), dbPaste.getPlantId(), dbPaste.getLanguageId(), dbPaste.getWarehouseId());
                if (description != null) {
                    dbPaste.setCompanyDescription(description.getCompanyDescription());
                    dbPaste.setPlantDescription(description.getPlantDescription());
                    dbPaste.setWarehouseDescription(description.getWarehouseDescription());
                }
            }
            if (dbPaste.getStatusId() != null && dbPaste.getLanguageId() != null) {
                statusDescription = getStatusDescription(dbPaste.getStatusId(), dbPaste.getLanguageId());
                if (statusDescription != null) {
                    dbPaste.setStatusDescription(statusDescription);
                }
            }
            dbPaste.setDeletionIndicator(0L);
            dbPaste.setCreatedBy(loginUserID);
            dbPaste.setCreatedOn(new Date());
            Paste createdPaste = pasteRepository.save(dbPaste);
            return createdPaste;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * update Paste
     *
     * @param languageId
     * @param plantId
     * @param companyCodeId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     * @param modifyPaste
     * @return
     */
    public List<Paste> updatePaste(String languageId, String plantId, String companyCodeId, String warehouseId,
                                   String productionOrderNo, String loginUserID, List<Paste> modifyPaste) {
        try {
            List<Paste> updatedPaste = new ArrayList<>();
            for (Paste newPaste : modifyPaste) {
                Paste dbPaste = getPaste(languageId, companyCodeId, plantId, warehouseId, productionOrderNo,
                        newPaste.getProductionOrderLineNo(), newPaste.getReceipeId(), newPaste.getOperationNumber(), newPaste.getItemCode());
                log.info("Update Paste Initiated : {}", dbPaste);
                BeanUtils.copyProperties(newPaste, dbPaste, CommonUtils.getNullPropertyNames(newPaste));
                if (dbPaste.getStatusId() != null && dbPaste.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbPaste.getStatusId(), dbPaste.getLanguageId());
                    if (statusDescription != null) {
                        dbPaste.setStatusDescription(statusDescription);
                    }
                }
                dbPaste.setUpdatedBy(loginUserID);
                dbPaste.setUpdatedOn(new Date());
                pasteRepository.save(dbPaste);
                updatedPaste.add(dbPaste);
            }
            return updatedPaste;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Delete Paste
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     */
    public void deletePaste(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String loginUserID) {
        try {
            List<Paste> dbulkPaste = getBulkPaste(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
            log.info("Delete Paste Initiated : {}", dbulkPaste);
            for (Paste dbPaste : dbulkPaste) {
                dbPaste.setDeletionIndicator(1L);
                dbPaste.setUpdatedBy(loginUserID);
                dbPaste.setUpdatedOn(new Date());
                pasteRepository.save(dbPaste);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Search Paste
     *
     * @param searchPaste
     * @return
     * @throws Exception
     */
    public Stream<Paste> findPaste(SearchPaste searchPaste) throws Exception {
        if (searchPaste.getStartCreatedOn() != null && searchPaste.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchPaste.getStartCreatedOn(), searchPaste.getEndCreatedOn());
            searchPaste.setStartCreatedOn(dates[0]);
            searchPaste.setEndCreatedOn(dates[1]);
        }
        if (searchPaste.getStartConfirmedOn() != null && searchPaste.getEndConfirmedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchPaste.getStartConfirmedOn(), searchPaste.getEndConfirmedOn());
            searchPaste.setStartConfirmedOn(dates[0]);
            searchPaste.setEndConfirmedOn(dates[1]);
        }
        log.info("searchPaste Input: {}", searchPaste);
        PasteSpecification spec = new PasteSpecification(searchPaste);
        Stream<Paste> results = pasteRepository.stream(spec, Paste.class);
        return results;
    }
}