package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.diceslicechop.DiceSliceChop;
import com.tekclover.wms.api.mfg.model.diceslicechop.SearchDiceSliceChop;
import com.tekclover.wms.api.mfg.model.prodcutionorder.SearchOperationLineReportProcess;
import com.tekclover.wms.api.mfg.repository.DiceSliceChopRepository;
import com.tekclover.wms.api.mfg.repository.specification.DiceSliceChopSpecification;
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
public class DiceSliceChopService extends BaseService {

    @Autowired
    DiceSliceChopRepository diceSliceChopRepository;

    @Autowired
    OperationConsumptionService operationConsumptionService;

    //---------------------------------------------------------------------------

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
    public DiceSliceChop getDiceSliceChop(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode) {
        Optional<DiceSliceChop> diceSliceChopOperation = diceSliceChopRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(languageId, companyCodeId, plantId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, 0L);

        if (diceSliceChopOperation.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId: " + warehouseId + ",companyCodeId: " + companyCodeId + ",languageId: " + languageId + ",plantId: " + plantId + ", operationNumber: " + operationNumber + ", receipeId: " + receipeId + ",productionOrderNo: " + productionOrderNo + ",productionOrderLineNo: " + productionOrderLineNo + ",itemCode: " + itemCode + " doesn't exist.");
        }
        return diceSliceChopOperation.get();
    }

    /**
     * Get BulkDiceSliceChop
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public List<DiceSliceChop> getBulkDiceSliceChop(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        List<DiceSliceChop> diceSliceChop =
                diceSliceChopRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        0L);
        if (diceSliceChop == null || diceSliceChop.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",productionOrderNo: " + productionOrderNo +
                    " doesn't exist.");
        }
        return diceSliceChop;
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
    public DiceSliceChop getDiceSliceChop(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        DiceSliceChop diceSliceChop =
                diceSliceChopRepository.findTopByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        productionOrderNo,
                        batchNumber,
                        0L);
        return diceSliceChop;
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
    public List<DiceSliceChop> getDiceSliceChopV2(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        List<DiceSliceChop> diceSliceChop =
                diceSliceChopRepository.findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        productionOrderNo,
                        batchNumber,
                        0L);
        return diceSliceChop;
    }

    /**
     *
     * @param searchOperationLineReportProcess
     * @return
     */
    public List<DiceSliceChop> getDiceSliceChopV2(SearchOperationLineReportProcess searchOperationLineReportProcess) {
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
            List<DiceSliceChop> diceSliceChop =
                    diceSliceChopRepository.findDiceSliceChop(
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
            return diceSliceChop;
        } catch (Exception e) {
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param searchDiceSliceChop
     * @return
     * @throws Exception
     */
    public Stream<DiceSliceChop> findDiceSliceChop(SearchDiceSliceChop searchDiceSliceChop) throws Exception {
        if (searchDiceSliceChop.getStartCreatedOn() != null && searchDiceSliceChop.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchDiceSliceChop.getStartCreatedOn(), searchDiceSliceChop.getEndCreatedOn());
        }
        log.info("Search DiceSliceChop Initiated -> {}", searchDiceSliceChop);
        DiceSliceChopSpecification spec = new DiceSliceChopSpecification(searchDiceSliceChop);
        Stream<DiceSliceChop> results = diceSliceChopRepository.stream(spec, DiceSliceChop.class);
        return results;
    }

    /**
     * @param newDiceSliceChopList
     * @param loginUserID
     * @return
     */
    public List<DiceSliceChop> createDiceSliceChopBatch(List<DiceSliceChop> newDiceSliceChopList, String loginUserID) {
        try {
            List<DiceSliceChop> createdDiceSliceChopList = new ArrayList<>();
            for (DiceSliceChop newDiceSliceChop : newDiceSliceChopList) {
                Optional<DiceSliceChop> diceSliceChopOperation =
                        diceSliceChopRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(
                                newDiceSliceChop.getLanguageId(), newDiceSliceChop.getCompanyCodeId(), newDiceSliceChop.getPlantId(), newDiceSliceChop.getWarehouseId(), newDiceSliceChop.getOperationNumber(),
                                newDiceSliceChop.getReceipeId(), newDiceSliceChop.getProductionOrderNo(), newDiceSliceChop.getProductionOrderLineNo(), newDiceSliceChop.getItemCode(), 0L);

                if (diceSliceChopOperation.isPresent()) {
                    throw new BadRequestException("Record is getting duplicated with the given values");
                }

                log.info("Create DiceSliceChop Initiated -> {}", newDiceSliceChop);
                DiceSliceChop dbDiceSliceChop = new DiceSliceChop();
                BeanUtils.copyProperties(newDiceSliceChop, dbDiceSliceChop, CommonUtils.getNullPropertyNames(newDiceSliceChop));

                if (dbDiceSliceChop.getCompanyCodeId() != null && dbDiceSliceChop.getPlantId() != null && dbDiceSliceChop.getLanguageId() != null && dbDiceSliceChop.getWarehouseId() != null) {
                    description = getDescription(dbDiceSliceChop.getCompanyCodeId(), dbDiceSliceChop.getPlantId(), dbDiceSliceChop.getLanguageId(), dbDiceSliceChop.getWarehouseId());
                    if (description != null) {
                        dbDiceSliceChop.setCompanyDescription(description.getCompanyDescription());
                        dbDiceSliceChop.setPlantDescription(description.getPlantDescription());
                        dbDiceSliceChop.setWarehouseDescription(description.getWarehouseDescription());
                    }
                }

                if (dbDiceSliceChop.getStatusId() != null && dbDiceSliceChop.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbDiceSliceChop.getStatusId(), dbDiceSliceChop.getLanguageId());
                    if (statusDescription != null) {
                        dbDiceSliceChop.setStatusDescription(statusDescription);
                    }
                }

                dbDiceSliceChop.setDeletionIndicator(0L);
                dbDiceSliceChop.setCreatedBy(loginUserID);
                dbDiceSliceChop.setCreatedOn(new Date());
                DiceSliceChop savedDiceSliceChop = diceSliceChopRepository.save(dbDiceSliceChop);
                createdDiceSliceChopList.add(savedDiceSliceChop);

//                OperationConsumption operationConsumption = new OperationConsumption();
//                BeanUtils.copyProperties(dbDiceSliceChop, operationConsumption, CommonUtils.getNullPropertyNames(dbDiceSliceChop));
//                operationConsumptionService.createOperationConsumption(operationConsumption, loginUserID);
            }
            return createdDiceSliceChopList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param newDiceSliceChop
     * @param loginUserID
     * @return
     */
    public DiceSliceChop createDiceSliceChopBatch(DiceSliceChop newDiceSliceChop, String loginUserID) {
        try {
//            Optional<DiceSliceChop> diceSliceChopOperation =
//                    diceSliceChopRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
//                            newDiceSliceChop.getLanguageId(), newDiceSliceChop.getCompanyCodeId(), newDiceSliceChop.getPlantId(), newDiceSliceChop.getWarehouseId(), newDiceSliceChop.getOperationNumber(),
//                            newDiceSliceChop.getReceipeId(), newDiceSliceChop.getProductionOrderNo(), newDiceSliceChop.getProductionOrderLineNo(), newDiceSliceChop.getItemCode(),
//                            newDiceSliceChop.getPhaseNumber(), newDiceSliceChop.getBomItem(), newDiceSliceChop.getBatchNumber(), 0L);
//
//            if (diceSliceChopOperation.isPresent()) {
//                throw new BadRequestException("Record is getting duplicated with the given values");
//            }

            log.info("Create DiceSliceChop Initiated -> {}", newDiceSliceChop);
            DiceSliceChop dbDiceSliceChop = new DiceSliceChop();
            BeanUtils.copyProperties(newDiceSliceChop, dbDiceSliceChop, CommonUtils.getNullPropertyNames(newDiceSliceChop));

            if (dbDiceSliceChop.getCompanyCodeId() != null && dbDiceSliceChop.getPlantId() != null && dbDiceSliceChop.getLanguageId() != null && dbDiceSliceChop.getWarehouseId() != null) {
                description = getDescription(dbDiceSliceChop.getCompanyCodeId(), dbDiceSliceChop.getPlantId(), dbDiceSliceChop.getLanguageId(), dbDiceSliceChop.getWarehouseId());
                if (description != null) {
                    dbDiceSliceChop.setCompanyDescription(description.getCompanyDescription());
                    dbDiceSliceChop.setPlantDescription(description.getPlantDescription());
                    dbDiceSliceChop.setWarehouseDescription(description.getWarehouseDescription());
                }
            }

            if (dbDiceSliceChop.getStatusId() != null && dbDiceSliceChop.getLanguageId() != null) {
                statusDescription = getStatusDescription(dbDiceSliceChop.getStatusId(), dbDiceSliceChop.getLanguageId());
                if (statusDescription != null) {
                    dbDiceSliceChop.setStatusDescription(statusDescription);
                }
            }

            dbDiceSliceChop.setDeletionIndicator(0L);
            dbDiceSliceChop.setCreatedBy(loginUserID);
            dbDiceSliceChop.setCreatedOn(new Date());
            DiceSliceChop savedDiceSliceChop = diceSliceChopRepository.save(dbDiceSliceChop);
            return savedDiceSliceChop;
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
     * @param modifyDiceSliceChopList
     * @return
     */
    public List<DiceSliceChop> updateDiceSliceChopBatch(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode, String loginUserID, List<DiceSliceChop> modifyDiceSliceChopList) {
        try {
            List<DiceSliceChop> updatedDiceSliceChopList = new ArrayList<>();
            for (DiceSliceChop modifyDiceSliceChop : modifyDiceSliceChopList) {
                DiceSliceChop dbDiceSliceChop = getDiceSliceChop(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);
                log.info("Update DiceSliceChop Initiated -> {}", dbDiceSliceChop);
                BeanUtils.copyProperties(modifyDiceSliceChop, dbDiceSliceChop, CommonUtils.getNullPropertyNames(modifyDiceSliceChop));

                if (dbDiceSliceChop.getStatusId() != null && dbDiceSliceChop.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbDiceSliceChop.getStatusId(), dbDiceSliceChop.getLanguageId());
                    if (statusDescription != null) {
                        dbDiceSliceChop.setStatusDescription(statusDescription);
                    }
                }

                dbDiceSliceChop.setUpdatedBy(loginUserID);
                dbDiceSliceChop.setUpdatedOn(new Date());
                DiceSliceChop updateDiceSliceChop = diceSliceChopRepository.save(dbDiceSliceChop);
                updatedDiceSliceChopList.add(updateDiceSliceChop);
            }
            return updatedDiceSliceChopList;
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
     * @param itemCode
     * @param loginUserID
     */
    public void deleteDiceSliceChop(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode, String loginUserID) {
        try {
            DiceSliceChop dbDiceSliceChop = getDiceSliceChop(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);
            log.info("Delete DiceSliceChop Initiated : {}", dbDiceSliceChop);
            dbDiceSliceChop.setDeletionIndicator(1L);
            dbDiceSliceChop.setUpdatedBy(loginUserID);
            dbDiceSliceChop.setUpdatedOn(new Date());
            diceSliceChopRepository.save(dbDiceSliceChop);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
}