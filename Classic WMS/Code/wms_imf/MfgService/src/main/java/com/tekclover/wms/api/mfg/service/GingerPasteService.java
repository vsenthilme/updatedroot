package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.gingerpaste.GingerPaste;
import com.tekclover.wms.api.mfg.model.gingerpaste.SearchGingerPaste;
import com.tekclover.wms.api.mfg.repository.GingerPasteRepository;
import com.tekclover.wms.api.mfg.repository.specification.GingerPasteSpecification;
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
public class GingerPasteService extends BaseService {

    @Autowired
    GingerPasteRepository gingerPasteRepository;

    @Autowired
    OperationConsumptionService operationConsumptionService;
    //-----------------------------------------------------Gingerpaste Service--------------------------------------------------

    /**
     * Get Gingerpaste
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
    public GingerPaste getGingerPaste(String languageId, String companyCodeId, String plantId, String warehouseId,
                                      String productionOrderNo, Long productionOrderLineNo, String receipeId, String operationNumber, String itemCode) {
        Optional<GingerPaste> gingerPaste =
                gingerPasteRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndDeletionIndicator(
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
        if (gingerPaste.isEmpty()) {
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
        return gingerPaste.get();
    }

    /**
     * Get BulkGingerPaste
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @return
     */
    public List<GingerPaste> getBulkGingerPaste(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo) {
        List<GingerPaste> gingerPaste =
                gingerPasteRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        productionOrderNo,
                        0L);
        if (gingerPaste == null || gingerPaste.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",productionOrderNo: " + productionOrderNo +
                    " doesn't exist.");
        }
        return gingerPaste;
    }

    /**
     * create GingerPaste
     *
     * @param addGingerPaste
     * @param loginUserID
     * @return
     */
    public List<GingerPaste> createGingerPaste(List<GingerPaste> addGingerPaste, String loginUserID) {
        try {
            List<GingerPaste> createdGingerPaste = new ArrayList<>();
            if (addGingerPaste != null && !addGingerPaste.isEmpty()) {
                for (GingerPaste newGingerPaste : addGingerPaste) {
                    if (newGingerPaste.getProductionOrderNo() == null || newGingerPaste.getProductionOrderLineNo() == null) {
                        throw new BadRequestException("ProductionOrderNo & Line No is must to create Gingerpaste");
                    }
                    Optional<GingerPaste> gingerPaste =
                            gingerPasteRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndProductionOrderNoAndProductionOrderLineNoAndReceipeIdAndOperationNumberAndItemCodeAndDeletionIndicator(
                                    newGingerPaste.getLanguageId(),
                                    newGingerPaste.getCompanyCodeId(),
                                    newGingerPaste.getPlantId(),
                                    newGingerPaste.getWarehouseId(),
                                    newGingerPaste.getProductionOrderNo(),
                                    newGingerPaste.getProductionOrderLineNo(),
                                    newGingerPaste.getReceipeId(),
                                    newGingerPaste.getOperationNumber(),
                                    newGingerPaste.getItemCode(),
                                    0L
                            );
                    if (gingerPaste.isPresent()) {
                        throw new BadRequestException("Record is getting duplicated with the given values");
                    }
                    log.info("Create GingerPaste initiated : {}", newGingerPaste);
                    GingerPaste dbGingerPaste = new GingerPaste();
                    BeanUtils.copyProperties(newGingerPaste, dbGingerPaste, CommonUtils.getNullPropertyNames(newGingerPaste));
                    if (dbGingerPaste.getLanguageId() != null && dbGingerPaste.getCompanyCodeId() != null &&
                            dbGingerPaste.getPlantId() != null && dbGingerPaste.getWarehouseId() != null) {
                        description = getDescription(dbGingerPaste.getCompanyCodeId(), dbGingerPaste.getPlantId(), dbGingerPaste.getLanguageId(), dbGingerPaste.getWarehouseId());
                        if (description != null) {
                            dbGingerPaste.setCompanyDescription(description.getCompanyDescription());
                            dbGingerPaste.setPlantDescription(description.getPlantDescription());
                            dbGingerPaste.setWarehouseDescription(description.getWarehouseDescription());
                        }
                    }
                    if (dbGingerPaste.getStatusId() != null && dbGingerPaste.getLanguageId() != null) {
                        statusDescription = getStatusDescription(dbGingerPaste.getStatusId(), dbGingerPaste.getLanguageId());
                        if (statusDescription != null) {
                            dbGingerPaste.setStatusDescription(statusDescription);
                        }
                    }
                    dbGingerPaste.setDeletionIndicator(0L);
                    dbGingerPaste.setCreatedBy(loginUserID);
                    dbGingerPaste.setCreatedOn(new Date());
                    gingerPasteRepository.save(dbGingerPaste);
//                    OperationConsumption operationConsumption = new OperationConsumption();
//                    BeanUtils.copyProperties(dbGingerPaste, operationConsumption, CommonUtils.getNullPropertyNames(dbGingerPaste));
//                    operationConsumptionService.createOperationConsumption(operationConsumption, loginUserID);
                    createdGingerPaste.add(dbGingerPaste);
                }
            }
            return createdGingerPaste;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
    /**
     * update GingerPaste
     *
     * @param languageId
     * @param plantId
     * @param companyCodeId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     * @param modifyGingerPaste
     * @return
     */
    public List<GingerPaste> updateGingerPaste(String languageId, String plantId, String companyCodeId, String warehouseId,
                                               String productionOrderNo, String loginUserID, List<GingerPaste> modifyGingerPaste) {
        try {
            List<GingerPaste> updatedGingerPaste = new ArrayList<>();
            for (GingerPaste newGingerPaste : modifyGingerPaste) {
                GingerPaste dbGingerPaste = getGingerPaste(languageId, companyCodeId, plantId, warehouseId, productionOrderNo,
                        newGingerPaste.getProductionOrderLineNo(), newGingerPaste.getReceipeId(), newGingerPaste.getOperationNumber(), newGingerPaste.getItemCode());
                log.info("Update GingerPaste Initiated : {}", dbGingerPaste);
                BeanUtils.copyProperties(newGingerPaste, dbGingerPaste, CommonUtils.getNullPropertyNames(newGingerPaste));
                if (dbGingerPaste.getStatusId() != null && dbGingerPaste.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbGingerPaste.getStatusId(), dbGingerPaste.getLanguageId());
                    if (statusDescription != null) {
                        dbGingerPaste.setStatusDescription(statusDescription);
                    }
                }
                dbGingerPaste.setUpdatedBy(loginUserID);
                dbGingerPaste.setUpdatedOn(new Date());
                gingerPasteRepository.save(dbGingerPaste);
                updatedGingerPaste.add(dbGingerPaste);
            }
            return updatedGingerPaste;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
    /**
     * Delete GingerPaste
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param productionOrderNo
     * @param loginUserID
     */
    public void deleteGingerPaste(String companyCodeId, String plantId, String languageId, String warehouseId, String productionOrderNo, String loginUserID) {
        try {
            List<GingerPaste> dbulkGingerPaste = getBulkGingerPaste(companyCodeId, plantId, languageId, warehouseId, productionOrderNo);
            log.info("Delete GingerPaste Initiated : {}", dbulkGingerPaste);
            for (GingerPaste dbGingerPaste : dbulkGingerPaste) {
                dbGingerPaste.setDeletionIndicator(1L);
                dbGingerPaste.setUpdatedBy(loginUserID);
                dbGingerPaste.setUpdatedOn(new Date());
                gingerPasteRepository.save(dbGingerPaste);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * Search GingerPaste
     *
     * @param searchGingerPaste
     * @return
     * @throws Exception
     */
    public Stream<GingerPaste> findGingerPaste(SearchGingerPaste searchGingerPaste) throws Exception {
        if (searchGingerPaste.getStartCreatedOn() != null && searchGingerPaste.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchGingerPaste.getStartCreatedOn(), searchGingerPaste.getEndCreatedOn());
            searchGingerPaste.setStartCreatedOn(dates[0]);
            searchGingerPaste.setEndCreatedOn(dates[1]);
        }
        if (searchGingerPaste.getStartConfirmedOn() != null && searchGingerPaste.getEndConfirmedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchGingerPaste.getStartConfirmedOn(), searchGingerPaste.getEndConfirmedOn());
            searchGingerPaste.setStartConfirmedOn(dates[0]);
            searchGingerPaste.setEndConfirmedOn(dates[1]);
        }
        log.info("searchGingerPaste Input: {}", searchGingerPaste);
        GingerPasteSpecification spec = new GingerPasteSpecification(searchGingerPaste);
        Stream<GingerPaste> results = gingerPasteRepository.stream(spec, GingerPaste.class);
        return results;
    }
}