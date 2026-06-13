package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.prodcutionorder.SearchOperationLineReportProcess;
import com.tekclover.wms.api.mfg.model.soaking.SearchSoaking;
import com.tekclover.wms.api.mfg.model.soaking.Soaking;
import com.tekclover.wms.api.mfg.repository.SoakingRepository;
import com.tekclover.wms.api.mfg.repository.specification.SoakingSpecification;
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
public class SoakingService extends BaseService {

    @Autowired
    SoakingRepository soakingRepository;

    @Autowired
    OperationConsumptionService operationConsumptionService;
    //-----------------------------------------------------Sorting Service--------------------------------------------------

    /**
     * Get Soaking
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
    public Soaking getSoaking(String languageId, String companyCodeId, String plantId, String warehouseId,
                              String productionOrderNo, Long productionOrderLineNo, String receipeId, String operationNumber, String itemCode) {
        Optional<Soaking> soaking =
                soakingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndDeletionIndicator(
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
        if (soaking.isEmpty()) {
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
        return soaking.get();
    }

    /**
     * Get BulkSoaking
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public List<Soaking> getBulkSoaking(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        List<Soaking> soaking =
                soakingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        0L);
        if (soaking == null || soaking.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",productionOrderNo: " + productionOrderNo +
                    " doesn't exist.");
        }
        return soaking;
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
    public Soaking getSoaking(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        Soaking soaking =
                soakingRepository.findTopByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        productionOrderNo,
                        batchNumber,
                        0L);
        return soaking;
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
    public List<Soaking> getSoakingV2(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        List<Soaking> soaking =
                soakingRepository.findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        productionOrderNo,
                        batchNumber,
                        0L);
        return soaking;
    }

    /**
     *
     * @param searchOperationLineReportProcess
     * @return
     */
    public List<Soaking> getSoakingV2(SearchOperationLineReportProcess searchOperationLineReportProcess) {
        try {
            if (searchOperationLineReportProcess.getStartCreatedOn() != null && searchOperationLineReportProcess.getEndCreatedOn() != null) {
                Date[] dates = DateUtils.addTimeToDatesForSearch(searchOperationLineReportProcess.getStartCreatedOn(), searchOperationLineReportProcess.getEndCreatedOn());
                searchOperationLineReportProcess.setStartCreatedOn(dates[0]);
                searchOperationLineReportProcess.setEndCreatedOn(dates[1]);
            }
            if (searchOperationLineReportProcess.getCompanyCodeId() != null && searchOperationLineReportProcess.getCompanyCodeId().isEmpty()) {
                searchOperationLineReportProcess.setCompanyCodeId(null);
            }
            if (searchOperationLineReportProcess.getPlantId() != null && searchOperationLineReportProcess.getPlantId().isEmpty()) {
                searchOperationLineReportProcess.setPlantId(null);
            }
            if (searchOperationLineReportProcess.getLanguageId() != null && searchOperationLineReportProcess.getLanguageId().isEmpty()) {
                searchOperationLineReportProcess.setLanguageId(null);
            }
            if (searchOperationLineReportProcess.getWarehouseId() != null && searchOperationLineReportProcess.getWarehouseId().isEmpty()) {
                searchOperationLineReportProcess.setWarehouseId(null);
            }
            if (searchOperationLineReportProcess.getItemCode() != null && searchOperationLineReportProcess.getItemCode().isEmpty()) {
                searchOperationLineReportProcess.setItemCode(null);
            }
            if (searchOperationLineReportProcess.getBatchNumber() != null && searchOperationLineReportProcess.getBatchNumber().isEmpty()) {
                searchOperationLineReportProcess.setBatchNumber(null);
            }
            if (searchOperationLineReportProcess.getBomItem() != null && searchOperationLineReportProcess.getBomItem().isEmpty()) {
                searchOperationLineReportProcess.setBomItem(null);
            }
            if (searchOperationLineReportProcess.getPhaseNumber() != null && searchOperationLineReportProcess.getPhaseNumber().isEmpty()) {
                searchOperationLineReportProcess.setPhaseNumber(null);
            }
            if (searchOperationLineReportProcess.getOperationNumber() != null && searchOperationLineReportProcess.getOperationNumber().isEmpty()) {
                searchOperationLineReportProcess.setOperationNumber(null);
            }
            if (searchOperationLineReportProcess.getReceipeId() != null && searchOperationLineReportProcess.getReceipeId().isEmpty()) {
                searchOperationLineReportProcess.setReceipeId(null);
            }
            if (searchOperationLineReportProcess.getProductionOrderNo() != null && searchOperationLineReportProcess.getProductionOrderNo().isEmpty()) {
                searchOperationLineReportProcess.setProductionOrderNo(null);
            }
            if (searchOperationLineReportProcess.getSupervisorName() != null && searchOperationLineReportProcess.getSupervisorName().isEmpty()) {
                searchOperationLineReportProcess.setSupervisorName(null);
            }
            if (searchOperationLineReportProcess.getStatusId() != null && searchOperationLineReportProcess.getStatusId().isEmpty()) {
                searchOperationLineReportProcess.setStatusId(null);
            }
            List<Soaking> soaking =
                    soakingRepository.findSoaking(
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
            return soaking;
        } catch (Exception e) {
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * create Soaking
     *
     * @param addSoaking
     * @param loginUserID
     * @return
     */
    public List<Soaking> createSoaking(List<Soaking> addSoaking, String loginUserID) {
        try {
            List<Soaking> createdSoaking = new ArrayList<>();
            if (addSoaking != null && !addSoaking.isEmpty()) {
                for (Soaking newSoaking : addSoaking) {
                    if (newSoaking.getProductionOrderNo() == null || newSoaking.getProductionOrderLineNo() == null) {
                        throw new BadRequestException("ProductionOrderNo & Line No is must to create Sorting");
                    }
                    Optional<Soaking> soaking =
                            soakingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndDeletionIndicator(
                                    newSoaking.getLanguageId(),
                                    newSoaking.getCompanyCodeId(),
                                    newSoaking.getPlantId(),
                                    newSoaking.getWarehouseId(),
                                    newSoaking.getProductionOrderNo(),
                                    newSoaking.getProductionOrderLineNo(),
                                    newSoaking.getReceipeId(),
                                    newSoaking.getOperationNumber(),
                                    newSoaking.getItemCode(),
                                    0L
                            );
                    if (soaking.isPresent()) {
                        throw new BadRequestException("Record is getting duplicated with the given values");
                    }
                    log.info("Create Soaking initiated : {}", newSoaking);
                    Soaking dbSoaking = new Soaking();
                    BeanUtils.copyProperties(newSoaking, dbSoaking, CommonUtils.getNullPropertyNames(newSoaking));
                    if (dbSoaking.getLanguageId() != null && dbSoaking.getCompanyCodeId() != null &&
                            dbSoaking.getPlantId() != null && dbSoaking.getWarehouseId() != null) {
                        description = getDescription(dbSoaking.getCompanyCodeId(), dbSoaking.getPlantId(), dbSoaking.getLanguageId(), dbSoaking.getWarehouseId());
                        if (description != null) {
                            dbSoaking.setCompanyDescription(description.getCompanyDescription());
                            dbSoaking.setPlantDescription(description.getPlantDescription());
                            dbSoaking.setWarehouseDescription(description.getWarehouseDescription());
                        }
                    }
                    if (dbSoaking.getStatusId() != null && dbSoaking.getLanguageId() != null) {
                        statusDescription = getStatusDescription(dbSoaking.getStatusId(), dbSoaking.getLanguageId());
                        if (statusDescription != null) {
                            dbSoaking.setStatusDescription(statusDescription);
                        }
                    }
                    dbSoaking.setDeletionIndicator(0L);
                    dbSoaking.setCreatedBy(loginUserID);
                    dbSoaking.setCreatedOn(new Date());
                    soakingRepository.save(dbSoaking);
                    createdSoaking.add(dbSoaking);
//                    OperationConsumption operationConsumption = new OperationConsumption();
//                    BeanUtils.copyProperties(dbSoaking, operationConsumption, CommonUtils.getNullPropertyNames(dbSoaking));
//                    operationConsumptionService.createOperationConsumption(operationConsumption, loginUserID);
                }
            }
            return createdSoaking;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param newSoaking
     * @param loginUserID
     * @return
     */
    public Soaking createSoaking(Soaking newSoaking, String loginUserID) {
        try {
            if (newSoaking.getProductionOrderNo() == null || newSoaking.getProductionOrderLineNo() == null) {
                throw new BadRequestException("ProductionOrderNo & Line No is must to create Sorting");
            }
//            Optional<Soaking> soaking =
//                    soakingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
//                            newSoaking.getLanguageId(),
//                            newSoaking.getCompanyCodeId(),
//                            newSoaking.getPlantId(),
//                            newSoaking.getWarehouseId(),
//                            newSoaking.getProductionOrderNo(),
//                            newSoaking.getProductionOrderLineNo(),
//                            newSoaking.getReceipeId(),
//                            newSoaking.getOperationNumber(),
//                            newSoaking.getItemCode(),
//                            newSoaking.getPhaseNumber(),
//                            newSoaking.getBomItem(),
//                            newSoaking.getBatchNumber(),
//                            0L );
//            if (soaking.isPresent()) {
//                throw new BadRequestException("Record is getting duplicated with the given values");
//            }
            log.info("Create Soaking initiated : {}", newSoaking);
            Soaking dbSoaking = new Soaking();
            BeanUtils.copyProperties(newSoaking, dbSoaking, CommonUtils.getNullPropertyNames(newSoaking));
            if (dbSoaking.getLanguageId() != null && dbSoaking.getCompanyCodeId() != null &&
                    dbSoaking.getPlantId() != null && dbSoaking.getWarehouseId() != null) {
                description = getDescription(dbSoaking.getCompanyCodeId(), dbSoaking.getPlantId(), dbSoaking.getLanguageId(), dbSoaking.getWarehouseId());
                if (description != null) {
                    dbSoaking.setCompanyDescription(description.getCompanyDescription());
                    dbSoaking.setPlantDescription(description.getPlantDescription());
                    dbSoaking.setWarehouseDescription(description.getWarehouseDescription());
                }
            }
            if (dbSoaking.getStatusId() != null && dbSoaking.getLanguageId() != null) {
                statusDescription = getStatusDescription(dbSoaking.getStatusId(), dbSoaking.getLanguageId());
                if (statusDescription != null) {
                    dbSoaking.setStatusDescription(statusDescription);
                }
            }
            dbSoaking.setDeletionIndicator(0L);
            dbSoaking.setCreatedBy(loginUserID);
            dbSoaking.setCreatedOn(new Date());
            Soaking createdSoaking = soakingRepository.save(dbSoaking);
            return createdSoaking;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * update Soaking
     *
     * @param languageId
     * @param plantId
     * @param companyCodeId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     * @param modifySoaking
     * @return
     */
    public List<Soaking> updateSoaking(String languageId, String plantId, String companyCodeId, String warehouseId,
                                       String productionOrderNo, String loginUserID, List<Soaking> modifySoaking) {
        try {
            List<Soaking> updatedSoaking = new ArrayList<>();
            for (Soaking newSoaking : modifySoaking) {
                Soaking dbSoaking = getSoaking(languageId, companyCodeId, plantId, warehouseId, productionOrderNo,
                        newSoaking.getProductionOrderLineNo(), newSoaking.getReceipeId(), newSoaking.getOperationNumber(), newSoaking.getItemCode());
                log.info("Update Soaking Initiated : {}", dbSoaking);
                BeanUtils.copyProperties(newSoaking, dbSoaking, CommonUtils.getNullPropertyNames(newSoaking));
                if (dbSoaking.getStatusId() != null && dbSoaking.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbSoaking.getStatusId(), dbSoaking.getLanguageId());
                    if (statusDescription != null) {
                        dbSoaking.setStatusDescription(statusDescription);
                    }
                }
                dbSoaking.setUpdatedBy(loginUserID);
                dbSoaking.setUpdatedOn(new Date());
                soakingRepository.save(dbSoaking);
                updatedSoaking.add(dbSoaking);
            }
            return updatedSoaking;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Delete Soaking
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     */
    public void deleteSoaking(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String loginUserID) {
        try {
            List<Soaking> dbulkSoaking = getBulkSoaking(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
            log.info("Delete Soaking Initiated : {}", dbulkSoaking);
            for (Soaking dbSoaking : dbulkSoaking) {
                dbSoaking.setDeletionIndicator(1L);
                dbSoaking.setUpdatedBy(loginUserID);
                dbSoaking.setUpdatedOn(new Date());
                soakingRepository.save(dbSoaking);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Search Soaking
     *
     * @param searchSoaking
     * @return
     * @throws Exception
     */
    public Stream<Soaking> findSoaking(SearchSoaking searchSoaking) throws Exception {
        if (searchSoaking.getStartCreatedOn() != null && searchSoaking.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchSoaking.getStartCreatedOn(), searchSoaking.getEndCreatedOn());
            searchSoaking.setStartCreatedOn(dates[0]);
            searchSoaking.setEndCreatedOn(dates[1]);
        }
        if (searchSoaking.getStartConfirmedOn() != null && searchSoaking.getEndConfirmedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchSoaking.getStartConfirmedOn(), searchSoaking.getEndConfirmedOn());
            searchSoaking.setStartConfirmedOn(dates[0]);
            searchSoaking.setEndConfirmedOn(dates[1]);
        }
        log.info("searchSoaking Input: {}", searchSoaking);
        SoakingSpecification spec = new SoakingSpecification(searchSoaking);
        Stream<Soaking> results = soakingRepository.stream(spec, Soaking.class);
        return results;
    }
}