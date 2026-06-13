package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.powder.Powder;
import com.tekclover.wms.api.mfg.model.powder.SearchPowder;
import com.tekclover.wms.api.mfg.model.prodcutionorder.SearchOperationLineReportProcess;
import com.tekclover.wms.api.mfg.repository.PowderRepository;
import com.tekclover.wms.api.mfg.repository.specification.PowderSpecification;
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
public class PowderService extends BaseService {

    @Autowired
    PowderRepository powderRepository;

    @Autowired
    OperationConsumptionService operationConsumptionService;
    //--------------------------------------------------------------------------

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param operationNumber
     * @param receipeId
     * @param productionOrderNo
     * @param productionOrderLineNo
     * @return
     */
    public Powder getPowder(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode) {
        Optional<Powder> powderOperation = powderRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(languageId, companyCodeId, plantId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, 0L);
        if (powderOperation.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId: " + warehouseId + ",companyCodeId: " + companyCodeId + ",languageId: " + languageId + ",plantId: " + plantId + ", operationNumber: " + operationNumber + ",receipeId: " + receipeId + ",productionOrderNo: " + productionOrderNo + ",productionOrderLineNo: " + productionOrderLineNo + ",itemCode: " + itemCode + " doesn't exist.");
        }
        return powderOperation.get();
    }

    /**
     * Get Bulk Powder
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public List<Powder> getBulkPowder(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        List<Powder> powder =
                powderRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        0L);
        if (powder == null || powder.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",productionOrderNo: " + productionOrderNo +
                    " doesn't exist.");
        }
        return powder;
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
    public Powder getPowder(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        Powder powder =
                powderRepository.findTopByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        productionOrderNo,
                        batchNumber,
                        0L);
        return powder;
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
    public List<Powder> getPowderV2(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        List<Powder> powder =
                powderRepository.findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        productionOrderNo,
                        batchNumber,
                        0L);
        return powder;
    }

    /**
     *
     * @param searchOperationLineReportProcess
     * @return
     */
    public List<Powder> getPowderV2(SearchOperationLineReportProcess searchOperationLineReportProcess) {
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
            List<Powder> powder =
                    powderRepository.findPowder(
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
            return powder;
        } catch (Exception e) {
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param searchPowder
     * @return
     * @throws Exception
     */
    public Stream<Powder> findPowder(SearchPowder searchPowder) throws Exception {
        if (searchPowder.getStartCreatedOn() != null && searchPowder.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchPowder.getStartCreatedOn(), searchPowder.getEndCreatedOn());
            searchPowder.setStartCreatedOn(dates[0]);
            searchPowder.setEndCreatedOn(dates[1]);
        }
        log.info("Search Powder Initiated -> {}", searchPowder);
        PowderSpecification spec = new PowderSpecification(searchPowder);
        Stream<Powder> results = powderRepository.stream(spec, Powder.class);
        return results;
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param operationNumber
     * @param receipeId
     * @param productionOrderNo
     * @param productionOrderLineNo
     * @param loginUserID
     * @param modifyPowder
     * @return
     */
    public Powder updatePowder(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode, String loginUserID, Powder modifyPowder) {
        try {
            Powder dbPowder = getPowder(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);
            log.info("Update Powder Initiated -> {}", dbPowder);
            BeanUtils.copyProperties(modifyPowder, dbPowder, CommonUtils.getNullPropertyNames(modifyPowder));
            if (dbPowder.getStatusId() != null && dbPowder.getLanguageId() != null) {
                statusDescription = getStatusDescription(dbPowder.getStatusId(), dbPowder.getLanguageId());
                if (statusDescription != null) {
                    dbPowder.setStatusDescription(statusDescription);
                }
            }
            dbPowder.setUpdatedBy(loginUserID);
            dbPowder.setUpdatedOn(new Date());
            return powderRepository.save(dbPowder);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param newPowderList
     * @param loginUserID
     * @return
     */
    public List<Powder> createPowderBatch(List<Powder> newPowderList, String loginUserID) {
        try {
            List<Powder> createdPowderList = new ArrayList<>();
            for (Powder newPowder : newPowderList) {
                Optional<Powder> powderOperation = powderRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(
                        newPowder.getLanguageId(),
                        newPowder.getCompanyCodeId(),
                        newPowder.getPlantId(),
                        newPowder.getWarehouseId(),
                        newPowder.getOperationNumber(),
                        newPowder.getReceipeId(),
                        newPowder.getProductionOrderNo(),
                        newPowder.getProductionOrderLineNo(),
                        newPowder.getItemCode(),
                        0L);
                if (powderOperation.isPresent()) {
                    throw new BadRequestException("Record is getting duplicated with the given values");
                }
                log.info("Create Powder Initiated -> {}", newPowder);
                Powder dbPowder = new Powder();
                BeanUtils.copyProperties(newPowder, dbPowder, CommonUtils.getNullPropertyNames(newPowder));
                if (dbPowder.getCompanyCodeId() != null && dbPowder.getPlantId() != null && dbPowder.getLanguageId() != null && dbPowder.getWarehouseId() != null) {
                    description = getDescription(dbPowder.getCompanyCodeId(), dbPowder.getPlantId(), dbPowder.getLanguageId(), dbPowder.getWarehouseId());
                    if (description != null) {
                        dbPowder.setCompanyDescription(description.getCompanyDescription());
                        dbPowder.setPlantDescription(description.getPlantDescription());
                        dbPowder.setWarehouseDescription(description.getWarehouseDescription());
                    }
                }
                if (dbPowder.getStatusId() != null && dbPowder.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbPowder.getStatusId(), dbPowder.getLanguageId());
                    if (statusDescription != null) {
                        dbPowder.setStatusDescription(statusDescription);
                    }
                }
                dbPowder.setDeletionIndicator(0L);
                dbPowder.setCreatedBy(loginUserID);
                dbPowder.setCreatedOn(new Date());
                Powder savedPowder = powderRepository.save(dbPowder);
                createdPowderList.add(savedPowder);
//                OperationConsumption operationConsumption = new OperationConsumption();
//                BeanUtils.copyProperties(dbPowder, operationConsumption, CommonUtils.getNullPropertyNames(dbPowder));
//                operationConsumptionService.createOperationConsumption(operationConsumption, loginUserID);
            }
            return createdPowderList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param newPowder
     * @param loginUserID
     * @return
     */
    public Powder createPowder(Powder newPowder, String loginUserID) {
        try {
//            Optional<Powder> powderOperation = powderRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
//                    newPowder.getLanguageId(),
//                    newPowder.getCompanyCodeId(),
//                    newPowder.getPlantId(),
//                    newPowder.getWarehouseId(),
//                    newPowder.getOperationNumber(),
//                    newPowder.getReceipeId(),
//                    newPowder.getProductionOrderNo(),
//                    newPowder.getProductionOrderLineNo(),
//                    newPowder.getItemCode(),
//                    newPowder.getPhaseNumber(),
//                    newPowder.getBomItem(),
//                    newPowder.getBatchNumber(),
//                    0L);
//            if (powderOperation.isPresent()) {
//                throw new BadRequestException("Record is getting duplicated with the given values");
//            }
            log.info("Create Powder Initiated -> {}", newPowder);
            Powder dbPowder = new Powder();
            BeanUtils.copyProperties(newPowder, dbPowder, CommonUtils.getNullPropertyNames(newPowder));
            if (dbPowder.getCompanyCodeId() != null && dbPowder.getPlantId() != null && dbPowder.getLanguageId() != null && dbPowder.getWarehouseId() != null) {
                description = getDescription(dbPowder.getCompanyCodeId(), dbPowder.getPlantId(), dbPowder.getLanguageId(), dbPowder.getWarehouseId());
                if (description != null) {
                    dbPowder.setCompanyDescription(description.getCompanyDescription());
                    dbPowder.setPlantDescription(description.getPlantDescription());
                    dbPowder.setWarehouseDescription(description.getWarehouseDescription());
                }
            }
            if (dbPowder.getStatusId() != null && dbPowder.getLanguageId() != null) {
                statusDescription = getStatusDescription(dbPowder.getStatusId(), dbPowder.getLanguageId());
                if (statusDescription != null) {
                    dbPowder.setStatusDescription(statusDescription);
                }
            }
            dbPowder.setDeletionIndicator(0L);
            dbPowder.setCreatedBy(loginUserID);
            dbPowder.setCreatedOn(new Date());
            Powder savedPowder = powderRepository.save(dbPowder);
            return savedPowder;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param operationNumber
     * @param receipeId
     * @param productionOrderNo
     * @param productionOrderLineNo
     * @param loginUserID
     * @param modifyPowderList
     * @return
     */
    public List<Powder> updatePowderBatch(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode, String loginUserID, List<Powder> modifyPowderList) {
        try {
            List<Powder> updatedPowderList = new ArrayList<>();
            for (Powder modifyPowder : modifyPowderList) {
                Powder dbPowder = getPowder(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);
                log.info("Update Powder Initiated -> {}", dbPowder);
                BeanUtils.copyProperties(modifyPowder, dbPowder, CommonUtils.getNullPropertyNames(modifyPowder));
                if (dbPowder.getStatusId() != null && dbPowder.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbPowder.getStatusId(), dbPowder.getLanguageId());
                    if (statusDescription != null) {
                        dbPowder.setStatusDescription(statusDescription);
                    }
                }
                dbPowder.setUpdatedBy(loginUserID);
                dbPowder.setUpdatedOn(new Date());
                Powder updatedPowder = powderRepository.save(dbPowder);
                updatedPowderList.add(updatedPowder);
            }
            return updatedPowderList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param operationNumber
     * @param receipeId
     * @param productionOrderNo
     * @param productionOrderLineNo
     * @param loginUserID
     */
    public void deletePowder(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode, String loginUserID) {
        try {
            Powder dbPowder = getPowder(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);
            log.info("Delete Powder Initiated : {}", dbPowder);
            dbPowder.setDeletionIndicator(1L);
            dbPowder.setUpdatedBy(loginUserID);
            dbPowder.setUpdatedOn(new Date());
            powderRepository.save(dbPowder);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
}