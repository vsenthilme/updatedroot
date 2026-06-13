package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.sfgqi.SFGQI;
import com.tekclover.wms.api.mfg.model.sfgqi.SearchSFGQI;
import com.tekclover.wms.api.mfg.repository.SFGQIRepository;
import com.tekclover.wms.api.mfg.repository.specification.SFGQISpecification;
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
public class SFGQIService extends BaseService {

    @Autowired
    SFGQIRepository sfgqiRepository;

    @Autowired
    OperationConsumptionService operationConsumptionService;

    //--------------------------------------------------------------------------

    /**
     *
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
    public SFGQI getSFGQI(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode ) {
        Optional<SFGQI> sfgqiOperation = sfgqiRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(languageId, companyCodeId, plantId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, 0L);

        if (sfgqiOperation.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId: " + warehouseId + ",companyCodeId: " + companyCodeId + ",languageId: " + languageId + ",plantId: " + plantId + ", operationNumber: " + operationNumber + ", receipeId: " + receipeId + ", productionOrderNo: " + productionOrderNo + ", productionOrderLineNo: " + productionOrderLineNo + ", itemCode: " + itemCode + " doesn't exist.");
        }
        return sfgqiOperation.get();
    }

    /**
     *
     * @param searchSFGQI
     * @return
     * @throws Exception
     */
    public Stream<SFGQI> findSFGQI(SearchSFGQI searchSFGQI) throws Exception {
        if (searchSFGQI.getStartCreatedOn() != null && searchSFGQI.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchSFGQI.getStartCreatedOn(), searchSFGQI.getEndCreatedOn());
        }
        log.info("Search SFGQI Initiated -> {}", searchSFGQI);
        SFGQISpecification spec = new SFGQISpecification(searchSFGQI);
        Stream<SFGQI> results = sfgqiRepository.stream(spec, SFGQI.class);
        return results;
    }

    /**
     *
     * @param newSFGQIList
     * @param loginUserID
     * @return
     */
    public List<SFGQI> createSFGQIBatch(List<SFGQI> newSFGQIList, String loginUserID) {
        try {
            List<SFGQI> createdSFGQIList = new ArrayList<>();
            for (SFGQI newSFGQI : newSFGQIList) {
                Optional<SFGQI> sfgqiOperation =
                        sfgqiRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(
                                newSFGQI.getLanguageId(), newSFGQI.getCompanyCodeId(), newSFGQI.getPlantId(), newSFGQI.getWarehouseId(), newSFGQI.getOperationNumber(),
                                newSFGQI.getReceipeId(), newSFGQI.getProductionOrderNo(), newSFGQI.getProductionOrderLineNo(), newSFGQI.getItemCode(), 0L);

                if (sfgqiOperation.isPresent()) {
                    throw new BadRequestException("Record is getting duplicated with the given values");
                }

                log.info("Created SFGQI Initiated -> {}", newSFGQI);
                SFGQI dbSFGQI = new SFGQI();
                BeanUtils.copyProperties(newSFGQI, dbSFGQI, CommonUtils.getNullPropertyNames(newSFGQI));

                if (dbSFGQI.getCompanyCodeId() != null && dbSFGQI.getPlantId() != null && dbSFGQI.getLanguageId() != null && dbSFGQI.getWarehouseId() != null) {
                    description = getDescription(dbSFGQI.getCompanyCodeId(), dbSFGQI.getPlantId(), dbSFGQI.getLanguageId(), dbSFGQI.getWarehouseId());
                    if (description != null) {
                        dbSFGQI.setCompanyDescription(description.getCompanyDescription());
                        dbSFGQI.setPlantDescription(description.getPlantDescription());
                        dbSFGQI.setWarehouseDescription(description.getWarehouseDescription());
                    }
                }

                if (dbSFGQI.getStatusId() != null && dbSFGQI.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbSFGQI.getStatusId(), dbSFGQI.getLanguageId());
                    if (statusDescription != null) {
                        dbSFGQI.setStatusDescription(statusDescription);
                    }
                }

                dbSFGQI.setDeletionIndicator(0L);
                dbSFGQI.setCreatedBy(loginUserID);
                dbSFGQI.setCreatedOn(new Date());
                SFGQI savedSFGQI = sfgqiRepository.save(dbSFGQI);
                createdSFGQIList.add(savedSFGQI);

//                OperationConsumption operationConsumption = new OperationConsumption();
//                BeanUtils.copyProperties(dbSFGQI, operationConsumption, CommonUtils.getNullPropertyNames(dbSFGQI));
//                operationConsumptionService.createOperationConsumption(operationConsumption, loginUserID);
            }
            return createdSFGQIList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     *
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
     * @param modifySFGQIList
     * @return
     */
    public List<SFGQI> updateSFGQIBatch(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode, String loginUserID, List<SFGQI> modifySFGQIList) {
        try {
            List<SFGQI> updatedSFGQIList = new ArrayList<>();
            for (SFGQI modifySFGQI : modifySFGQIList) {
                SFGQI dbSFGQI = getSFGQI(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);
                log.info("Update SFGQI Initiated -> {}", dbSFGQI);
                BeanUtils.copyProperties(modifySFGQI, dbSFGQI, CommonUtils.getNullPropertyNames(modifySFGQI));

                if (dbSFGQI.getStatusId() != null && dbSFGQI.getLanguageId() != null ) {
                    statusDescription = getStatusDescription(dbSFGQI.getStatusId(), dbSFGQI.getLanguageId());

                    if (statusDescription != null ) {
                        dbSFGQI.setStatusDescription(statusDescription);
                    }
                }

                dbSFGQI.setUpdatedBy(loginUserID);
                dbSFGQI.setUpdatedOn(new Date());
                SFGQI updateSFGQI = sfgqiRepository.save(dbSFGQI);
                updatedSFGQIList.add(updateSFGQI);
            }
            return updatedSFGQIList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     *
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
    public void deleteSFGQI(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode, String loginUserID) {
        try {
            SFGQI dbSFGQI = getSFGQI(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);
            log.info("Delete SFGQI Initiated : {}", dbSFGQI);
            dbSFGQI.setDeletionIndicator(1L);
            dbSFGQI.setUpdatedBy(loginUserID);
            dbSFGQI.setUpdatedOn(new Date());
            sfgqiRepository.save(dbSFGQI);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
}