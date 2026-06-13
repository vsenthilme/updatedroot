package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.cooking.Cooking;
import com.tekclover.wms.api.mfg.model.cooking.SearchCooking;
import com.tekclover.wms.api.mfg.model.prodcutionorder.SearchOperationLineReportProcess;
import com.tekclover.wms.api.mfg.repository.CookingRepository;
import com.tekclover.wms.api.mfg.repository.specification.CookingSpecification;
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
public class CookingService extends BaseService {

    @Autowired
    CookingRepository cookingRepository;

    @Autowired
    OperationConsumptionService operationConsumptionService;

    //-----------------------------------------------------------------------

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
     * @return
     */
    public Cooking getCooking(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode) {
        Optional<Cooking> cookingOperation = cookingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(languageId, companyCodeId, plantId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, 0L);

        if (cookingOperation.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId: " + warehouseId + ",companyCodeId: " + companyCodeId + ", languageId: " + languageId + ", plantId: " + plantId + ", operationNumber: " + operationNumber + ", receipeId: " + receipeId + ", productionOrderNo: " + productionOrderNo + ", productionOrderLineNo: " + productionOrderLineNo + ", itemCode: " + itemCode + " doesn't exist.");
        }
        return cookingOperation.get();
    }

    /**
     * GetBulkCooking
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public List<Cooking> getBulkCooking(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        List<Cooking> cooking =
                cookingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        0L);
        if (cooking == null || cooking.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",productionOrderNo: " + productionOrderNo +
                    " doesn't exist.");
        }
        return cooking;
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
    public Cooking getCooking(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        Cooking cooking =
                cookingRepository.findTopByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        productionOrderNo,
                        batchNumber,
                        0L);
        return cooking;
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
    public List<Cooking> getCookingV2(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String batchNumber) {
        List<Cooking> cooking =
                cookingRepository.findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndProductionOrderNoAndBatchNumberAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        productionOrderNo,
                        batchNumber,
                        0L);
        return cooking;
    }

    /**
     *
     * @param searchOperationLineReportProcess
     * @return
     */
    public List<Cooking> getCookingV2(SearchOperationLineReportProcess searchOperationLineReportProcess) {
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
            List<Cooking> cooking =
                    cookingRepository.findCooking(
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
            return cooking;
        } catch (Exception e) {
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param searchCooking
     * @return
     * @throws Exception
     */
    public Stream<Cooking> findCooking(SearchCooking searchCooking) throws Exception {
        if (searchCooking.getStartCreatedOn() != null && searchCooking.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchCooking.getStartCreatedOn(), searchCooking.getEndCreatedOn());
        }
        log.info("Search Cooking Initiated -> {}", searchCooking);
        CookingSpecification spec = new CookingSpecification(searchCooking);
        Stream<Cooking> results = cookingRepository.stream(spec, Cooking.class);
        return results;
    }

    /**
     * @param newCookingList
     * @param loginUserID
     * @return
     */
    public List<Cooking> createCookingBatch(List<Cooking> newCookingList, String loginUserID) {
        try {
            List<Cooking> createdCookingList = new ArrayList<>();
            for (Cooking newCooking : newCookingList) {
                Optional<Cooking> cookingOperation = cookingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(
                        newCooking.getLanguageId(), newCooking.getCompanyCodeId(), newCooking.getPlantId(), newCooking.getWarehouseId(), newCooking.getOperationNumber(),
                        newCooking.getReceipeId(), newCooking.getProductionOrderNo(), newCooking.getProductionOrderLineNo(), newCooking.getItemCode(), 0L);

                if (cookingOperation.isPresent()) {
                    throw new BadRequestException("Record is getting duplicated with the given values");
                }

                log.info("Created Cooking Initiated -> {}", newCooking);
                Cooking dbCooking = new Cooking();
                BeanUtils.copyProperties(newCooking, dbCooking, CommonUtils.getNullPropertyNames(newCooking));

                if (dbCooking.getCompanyCodeId() != null && dbCooking.getPlantId() != null && dbCooking.getLanguageId() != null && dbCooking.getWarehouseId() != null) {

                    if (description != null) {
                        dbCooking.setCompanyDescription(description.getCompanyDescription());
                        dbCooking.setPlantDescription(description.getPlantDescription());
                        dbCooking.setWarehouseDescription(description.getWarehouseDescription());
                    }
                }

                if (dbCooking.getStatusId() != null && dbCooking.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbCooking.getStatusId(), dbCooking.getLanguageId());

                    if (statusDescription != null) {
                        dbCooking.setStatusDescription(statusDescription);
                    }
                }

                dbCooking.setDeletionIndicator(0L);
                dbCooking.setCreatedBy(loginUserID);
                dbCooking.setCreatedOn(new Date());
                Cooking savedCooking = cookingRepository.save(dbCooking);
                createdCookingList.add(savedCooking);

//                OperationConsumption operationConsumption = new OperationConsumption();
//                BeanUtils.copyProperties(dbCooking, operationConsumption, CommonUtils.getNullPropertyNames(dbCooking));
//                operationConsumptionService.createOperationConsumption(operationConsumption, loginUserID);
            }
            return createdCookingList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     *
     * @param newCooking
     * @param loginUserID
     * @return
     */
    public Cooking createCooking(Cooking newCooking, String loginUserID) {
        try {
//            Optional<Cooking> cookingOperation = cookingRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndPhaseNumberAndBomItemAndBatchNumberAndDeletionIndicator(
//                    newCooking.getLanguageId(), newCooking.getCompanyCodeId(), newCooking.getPlantId(), newCooking.getWarehouseId(), newCooking.getOperationNumber(),
//                    newCooking.getReceipeId(), newCooking.getProductionOrderNo(), newCooking.getProductionOrderLineNo(), newCooking.getItemCode(),
//                    newCooking.getPhaseNumber(), newCooking.getBomItem(), newCooking.getBatchNumber(), 0L);
//
//            if (cookingOperation.isPresent()) {
//                throw new BadRequestException("Record is getting duplicated with the given values");
//            }

            log.info("Created Cooking Initiated -> {}", newCooking);
            Cooking dbCooking = new Cooking();
            BeanUtils.copyProperties(newCooking, dbCooking, CommonUtils.getNullPropertyNames(newCooking));

            if (dbCooking.getCompanyCodeId() != null && dbCooking.getPlantId() != null && dbCooking.getLanguageId() != null && dbCooking.getWarehouseId() != null) {

                if (description != null) {
                    dbCooking.setCompanyDescription(description.getCompanyDescription());
                    dbCooking.setPlantDescription(description.getPlantDescription());
                    dbCooking.setWarehouseDescription(description.getWarehouseDescription());
                }
            }

            if (dbCooking.getStatusId() != null && dbCooking.getLanguageId() != null) {
                statusDescription = getStatusDescription(dbCooking.getStatusId(), dbCooking.getLanguageId());

                if (statusDescription != null) {
                    dbCooking.setStatusDescription(statusDescription);
                }
            }

            dbCooking.setDeletionIndicator(0L);
            dbCooking.setCreatedBy(loginUserID);
            dbCooking.setCreatedOn(new Date());
            Cooking savedCooking = cookingRepository.save(dbCooking);
            return savedCooking;
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
     * @param modifyCookingList
     * @return
     */
    public List<Cooking> updateCookingBatch(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode, String loginUserID, List<Cooking> modifyCookingList) {
        try {
            List<Cooking> updatedCookingList = new ArrayList<>();
            for (Cooking modifyCooking : modifyCookingList) {
                Cooking dbCooking = getCooking(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);

                log.info("Update Cooking Initiated -> {}", dbCooking);
                BeanUtils.copyProperties(modifyCooking, dbCooking, CommonUtils.getNullPropertyNames(modifyCooking));

                if (dbCooking.getStatusId() != null && dbCooking.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbCooking.getStatusId(), dbCooking.getLanguageId());

                    if (statusDescription != null) {
                        dbCooking.setStatusDescription(statusDescription);
                    }
                }

                dbCooking.setUpdatedBy(loginUserID);
                dbCooking.setUpdatedOn(new Date());
                Cooking updateCooking = cookingRepository.save(dbCooking);
                updatedCookingList.add(updateCooking);
            }
            return updatedCookingList;
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
    public void deleteCooking(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode, String loginUserID) {
        try {
            Cooking dbCooking = getCooking(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);
            log.info("Delete Cooking Initiated -> {}", dbCooking);
            dbCooking.setDeletionIndicator(1L);
            dbCooking.setUpdatedBy(loginUserID);
            dbCooking.setUpdatedOn(new Date());
            cookingRepository.save(dbCooking);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
}