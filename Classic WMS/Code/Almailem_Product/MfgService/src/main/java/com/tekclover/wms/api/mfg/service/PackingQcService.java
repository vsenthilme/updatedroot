package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.packingqc.PackingQc;
import com.tekclover.wms.api.mfg.model.packingqc.SearchPackingQc;
import com.tekclover.wms.api.mfg.repository.PackingQcRepository;
import com.tekclover.wms.api.mfg.repository.specification.PackingQcSpecification;
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
public class PackingQcService extends BaseService {

    @Autowired
    PackingQcRepository packingQcRepository;

    @Autowired
    OperationConsumptionService operationConsumptionService;

    //---------------------------------------------------------------------

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
    public PackingQc getPackingQc(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode) {
        Optional<PackingQc> packingQcOperation = packingQcRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(languageId, companyCodeId, plantId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode, 0L );

        if (packingQcOperation.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId: " + warehouseId + ", companyCodeId: " + companyCodeId + ", languageId: " + languageId + ", plantId: " + plantId + ", operationNumber: " + operationNumber + ", receipeId: " + receipeId + ", productionOrderNo: " + productionOrderNo + ", productionOrderLineNo: " + productionOrderLineNo + ", itemCode: " + itemCode + " doesn't exist.");
        }
        return packingQcOperation.get();
    }

    /**
     *
     * @param searchPackingQc
     * @return
     * @throws Exception
     */
    public Stream<PackingQc> findPackingQc(SearchPackingQc searchPackingQc) throws Exception {
        if (searchPackingQc.getStartCreatedOn() != null && searchPackingQc.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchPackingQc.getStartCreatedOn(), searchPackingQc.getEndCreatedOn());
        }
        log.info("Search PackingQc Initiated -> {}", searchPackingQc);
        PackingQcSpecification spec = new PackingQcSpecification(searchPackingQc);
        Stream<PackingQc> results = packingQcRepository.stream(spec, PackingQc.class);
        return results;
    }

    /**
     *
     * @param newPackingQcList
     * @param loginUserID
     * @return
     */
    public List<PackingQc> createPackingQcBatch(List<PackingQc> newPackingQcList, String loginUserID) {
        try {
            List<PackingQc> createdPackingQcList = new ArrayList<>();
            for (PackingQc newPackingQc : newPackingQcList) {
                Optional<PackingQc> packingQcOperation = packingQcRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(newPackingQc.getLanguageId(), newPackingQc.getCompanyCodeId(), newPackingQc.getPlantId(), newPackingQc.getWarehouseId(), newPackingQc.getOperationNumber(), newPackingQc.getReceipeId(), newPackingQc.getProductionOrderNo(), newPackingQc.getProductionOrderLineNo(), newPackingQc.getItemCode(), 0L);

                if (packingQcOperation.isPresent()) {
                    throw new BadRequestException("Record is getting duplicated with the given values");
                }

                log.info("Create PackingQc Initiated -> {}", newPackingQc);
                PackingQc dbPackingQc = new PackingQc();
                BeanUtils.copyProperties(newPackingQc, dbPackingQc, CommonUtils.getNullPropertyNames(newPackingQc));

                if (dbPackingQc.getCompanyCodeId() != null && dbPackingQc.getPlantId() != null && dbPackingQc.getLanguageId() != null && dbPackingQc.getWarehouseId() != null) {
                    description = getDescription(dbPackingQc.getCompanyCodeId(), dbPackingQc.getPlantId(), dbPackingQc.getLanguageId(), dbPackingQc.getWarehouseId());
                    if (description != null) {
                        dbPackingQc.setCompanyDescription(description.getCompanyDescription());
                        dbPackingQc.setPlantDescription(description.getPlantDescription());
                        dbPackingQc.setWarehouseDescription(description.getWarehouseDescription());
                    }
                }

                if (dbPackingQc.getStatusId() != null && dbPackingQc.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbPackingQc.getStatusId(), dbPackingQc.getLanguageId());
                    if (statusDescription != null) {
                        dbPackingQc.setStatusDescription(statusDescription);
                    }
                }

                dbPackingQc.setDeletionIndicator(0L);
                dbPackingQc.setCreatedBy(loginUserID);
                dbPackingQc.setCreatedOn(new Date());
                PackingQc savedPackingQc = packingQcRepository.save(dbPackingQc);
                createdPackingQcList.add(savedPackingQc);

//                OperationConsumption operationConsumption = new OperationConsumption();
//                BeanUtils.copyProperties(dbPackingQc, operationConsumption, CommonUtils.getNullPropertyNames(dbPackingQc));
//                operationConsumptionService.createOperationConsumption(operationConsumption, loginUserID);
            }
            return createdPackingQcList;
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
     * @param modifyPackingQcList
     * @return
     */
    public List<PackingQc> updatePackingQcBatch(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode, String loginUserID, List<PackingQc> modifyPackingQcList) {
        try {
            List<PackingQc> updatedPackingQcList = new ArrayList<>();
            for (PackingQc modifyPackingQc : modifyPackingQcList) {
                PackingQc dbPackingQc = getPackingQc(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);

                log.info("Update PackingQc Initiated -> {}", dbPackingQc);
                BeanUtils.copyProperties(modifyPackingQc, dbPackingQc, CommonUtils.getNullPropertyNames(modifyPackingQc));

                if (dbPackingQc.getStatusId() != null && dbPackingQc.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbPackingQc.getStatusId(), dbPackingQc.getLanguageId());

                    if (statusDescription != null) {
                        dbPackingQc.setStatusDescription(statusDescription);
                    }
                }

                dbPackingQc.setUpdatedBy(loginUserID);
                dbPackingQc.setUpdatedOn(new Date());
                PackingQc updatePackingQc = packingQcRepository.save(dbPackingQc);
                updatedPackingQcList.add(updatePackingQc);
            }
            return updatedPackingQcList;
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
    public void deletePackingQc(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode, String loginUserID) {
        try {
            PackingQc dbPackingQc = getPackingQc(companyCodeId, plantId, languageId, warehouseId, operationNumber, receipeId, productionOrderNo, productionOrderLineNo, itemCode);
            log.info("Delete PackingQc Initiated -> {}", dbPackingQc);
            dbPackingQc.setDeletionIndicator(1L);
            dbPackingQc.setUpdatedBy(loginUserID);
            dbPackingQc.setUpdatedOn(new Date());
            packingQcRepository.save(dbPackingQc);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
}