package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.masteroperation.MasterOperation;
import com.tekclover.wms.api.mfg.model.masterreceipe.MasterReceipe;
import com.tekclover.wms.api.mfg.model.masterreceipe.SearchMasterReceipe;
import com.tekclover.wms.api.mfg.repository.MasterReceipeRepository;
import com.tekclover.wms.api.mfg.repository.specification.MasterReceipeSpecification;
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
public class MasterReceipeService extends BaseService {

    @Autowired
    MasterReceipeRepository masterReceipeRepository;

    @Autowired
    MasterOperationService masterOperationService;
    //--------------------------------------------------------------------------

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param operationNumber
     * @param receipeId
     * @param itemCode
     * @param bomNumber
     * @param phaseNumber
     * @param childItemCode
     * @return
     */
    public MasterReceipe getMasterReceipe(String companyCodeId, String plantId, String languageId, String warehouseId, String operationNumber,
                                          String receipeId, String itemCode, String bomNumber, String phaseNumber, String childItemCode) {
        Optional<MasterReceipe> masterOperation =
                masterReceipeRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndItemCodeAndBomNumberAndPhaseNumberAndChildItemCodeAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        operationNumber,
                        receipeId,
                        itemCode,
                        bomNumber,
                        phaseNumber,
                        childItemCode,
                        0L);
        if (masterOperation.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",operationNumber: " + operationNumber +
                    ",receipeId: " + receipeId +
                    ",itemCode: " + itemCode +
                    ",phaseNumber: " + phaseNumber +
                    ",childItemCode: " + childItemCode +
                    ",bomNumber: " + bomNumber +
                    " doesn't exist.");
        }
        return masterOperation.get();
    }
    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param itemCode
     * @return
     */
    public MasterReceipe getMasterReceipe(String companyCodeId, String plantId, String languageId, String warehouseId, String itemCode) {
        List<MasterReceipe> masterOperation =
                masterReceipeRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndItemCodeAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        itemCode,
                        0L);
        if (masterOperation.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",itemCoder: " + itemCode +
                    " doesn't exist.");
        }
        return masterOperation.get(0);
    }
    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param receipeId
     * @param operationNumber
     * @return
     */
    public List<MasterReceipe> getMasterReceipe(String companyCodeId, String plantId, String languageId,
                                                String warehouseId, String receipeId, String operationNumber) {
        List<MasterReceipe> masterOperation =
                masterReceipeRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndReceipeIdAndOperationNumberAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        receipeId,
                        operationNumber,
                        0L);
        if (masterOperation == null || masterOperation.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",receipeId: " + receipeId +
                    ",operationNumber: " + operationNumber +
                    " doesn't exist.");
        }
        return masterOperation;
    }

    /**
     *
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param itemCode
     * @return
     */
    public List<MasterReceipe> getMasterReceipeByItemCode(String companyCodeId, String plantId, String languageId,
                                                          String warehouseId, String itemCode) {
        List<MasterReceipe> masterOperation =
                masterReceipeRepository.findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndItemCodeAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        itemCode,
                        0L);
        if (masterOperation == null || masterOperation.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",itemCode: " + itemCode +
                    " doesn't exist.");
        }
        return masterOperation;
    }

    /**
     * @param searchMasterReceipe
     * @return
     * @throws Exception
     */
    public Stream<MasterReceipe> findMasterReceipe(SearchMasterReceipe searchMasterReceipe) throws Exception {
        if (searchMasterReceipe.getStartCreatedOn() != null && searchMasterReceipe.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchMasterReceipe.getStartCreatedOn(), searchMasterReceipe.getEndCreatedOn());
            searchMasterReceipe.setStartCreatedOn(dates[0]);
            searchMasterReceipe.setEndCreatedOn(dates[1]);
        }
        log.info("searchMasterReceipe Input: {}", searchMasterReceipe);
        MasterReceipeSpecification spec = new MasterReceipeSpecification(searchMasterReceipe);
        Stream<MasterReceipe> results = masterReceipeRepository.stream(spec, MasterReceipe.class);
        return results;
    }

    /**
     * @param newMasterReceipe
     * @param loginUserID
     * @return
     */
    public MasterReceipe createMasterReceipe(MasterReceipe newMasterReceipe, String loginUserID) {
        try {
            Optional<MasterReceipe> masterReceipe =
                    masterReceipeRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndItemCodeAndBomNumberAndPhaseNumberAndChildItemCodeAndDeletionIndicator(
                            newMasterReceipe.getLanguageId(),
                            newMasterReceipe.getCompanyCodeId(),
                            newMasterReceipe.getPlantId(),
                            newMasterReceipe.getWarehouseId(),
                            newMasterReceipe.getOperationNumber(),
                            newMasterReceipe.getReceipeId(),
                            newMasterReceipe.getItemCode(),
                            newMasterReceipe.getBomNumber(),
                            newMasterReceipe.getPhaseNumber(),
                            newMasterReceipe.getChildItemCode(),
                            0L);
            if (masterReceipe.isPresent()) {
                throw new BadRequestException("Record is getting duplicated with the given values");
            }
            log.info("create Master Receipe Initiated: {}", newMasterReceipe);
            MasterReceipe dbMasterReceipe = new MasterReceipe();
            BeanUtils.copyProperties(newMasterReceipe, dbMasterReceipe, CommonUtils.getNullPropertyNames(newMasterReceipe));
            if (dbMasterReceipe.getCompanyCodeId() != null && dbMasterReceipe.getPlantId() != null &&
                    dbMasterReceipe.getLanguageId() != null && dbMasterReceipe.getWarehouseId() != null) {

                NUMBER_RANGE_CODE = 22L;
                numberRangeId = getNextRangeNumber(NUMBER_RANGE_CODE, dbMasterReceipe.getCompanyCodeId(), dbMasterReceipe.getPlantId(),
                        dbMasterReceipe.getLanguageId(), dbMasterReceipe.getWarehouseId());
                if (numberRangeId != null) {
                    dbMasterReceipe.setReceipeId(numberRangeId);
                }

                description = getDescription(dbMasterReceipe.getCompanyCodeId(), dbMasterReceipe.getPlantId(),
                        dbMasterReceipe.getLanguageId(), dbMasterReceipe.getWarehouseId());
                if (description != null) {
                    dbMasterReceipe.setCompanyDescription(description.getCompanyDescription());
                    dbMasterReceipe.setPlantDescription(description.getPlantDescription());
                    dbMasterReceipe.setWarehouseDescription(description.getWarehouseDescription());
                }

                if(newMasterReceipe.getOperationDescription() == null || newMasterReceipe.getOperationDescription().isBlank() ||
                        newMasterReceipe.getPhaseDescription() == null || newMasterReceipe.getPhaseDescription().isBlank()) {
                    MasterOperation masterOperation = masterOperationService.getMasterOperation(dbMasterReceipe.getCompanyCodeId(), dbMasterReceipe.getPlantId(),
                            dbMasterReceipe.getLanguageId(), dbMasterReceipe.getWarehouseId(), dbMasterReceipe.getOperationNumber(), dbMasterReceipe.getPhaseNumber());
                    if(masterOperation != null) {
                        newMasterReceipe.setPhaseDescription(masterOperation.getPhaseDescription());
                        newMasterReceipe.setOperationDescription(masterOperation.getOperationDescription());
                    }
                }

            } else {
                throw new BadRequestException("ReceipeId cannot be Null. provide company, plant, language & warehouse");
            }
            if(dbMasterReceipe.getStatusId() != null && dbMasterReceipe.getLanguageId() != null) {
                statusDescription = getStatusDescription(dbMasterReceipe.getStatusId(), dbMasterReceipe.getLanguageId());
                if (statusDescription != null) {
                    dbMasterReceipe.setStatusDescription(statusDescription);
                }
            }
            dbMasterReceipe.setDeletionIndicator(0L);
            dbMasterReceipe.setCreatedBy(loginUserID);
            dbMasterReceipe.setCreatedOn(new Date());
            return masterReceipeRepository.save(dbMasterReceipe);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param newMasterReceipeList
     * @param loginUserID
     * @return
     */
    public List<MasterReceipe> createMasterReceipeBatch(List<MasterReceipe> newMasterReceipeList, String loginUserID) {
        try {
            List<MasterReceipe> createdMasterReceipeList = new ArrayList<>();
            String receipeId = null;
            for (MasterReceipe dbMasterReceipe : newMasterReceipeList) {

                Optional<MasterReceipe> masterReceipe =
                        masterReceipeRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndItemCodeAndBomNumberAndPhaseNumberAndChildItemCodeAndDeletionIndicator(
                                dbMasterReceipe.getLanguageId(),
                                dbMasterReceipe.getCompanyCodeId(),
                                dbMasterReceipe.getPlantId(),
                                dbMasterReceipe.getWarehouseId(),
                                dbMasterReceipe.getOperationNumber(),
                                dbMasterReceipe.getItemCode(),
                                dbMasterReceipe.getBomNumber(),
                                dbMasterReceipe.getPhaseNumber(),
                                dbMasterReceipe.getChildItemCode(),
                                0L);
                if (masterReceipe.isPresent()) {
                    throw new BadRequestException("Record is getting duplicated with the given values");
                }

                log.info("create Master Receipe Initiated: {}", dbMasterReceipe);

            MasterReceipe newMasterReceipe = new MasterReceipe();
            BeanUtils.copyProperties(dbMasterReceipe, newMasterReceipe, CommonUtils.getNullPropertyNames(dbMasterReceipe));

                if (dbMasterReceipe.getCompanyCodeId() != null && dbMasterReceipe.getPlantId() != null &&
                        dbMasterReceipe.getLanguageId() != null && dbMasterReceipe.getWarehouseId() != null) {

                    if (receipeId != null) {
                        newMasterReceipe.setReceipeId(receipeId);
                    } else {
                        NUMBER_RANGE_CODE = 22L;
                        numberRangeId = getNextRangeNumber(NUMBER_RANGE_CODE, dbMasterReceipe.getCompanyCodeId(), dbMasterReceipe.getPlantId(),
                                dbMasterReceipe.getLanguageId(), dbMasterReceipe.getWarehouseId());
                        if (numberRangeId != null) {
                            newMasterReceipe.setReceipeId(numberRangeId);
                            receipeId = numberRangeId;
                        }
                    }

                    description = getDescription(dbMasterReceipe.getCompanyCodeId(), dbMasterReceipe.getPlantId(),
                            dbMasterReceipe.getLanguageId(), dbMasterReceipe.getWarehouseId());
                    if (description != null) {
                        newMasterReceipe.setCompanyDescription(description.getCompanyDescription());
                        newMasterReceipe.setPlantDescription(description.getPlantDescription());
                        newMasterReceipe.setWarehouseDescription(description.getWarehouseDescription());
                    }

                    if(newMasterReceipe.getOperationDescription() == null || newMasterReceipe.getOperationDescription().isBlank() ||
                            newMasterReceipe.getPhaseDescription() == null || newMasterReceipe.getPhaseDescription().isBlank()) {
                        MasterOperation masterOperation = masterOperationService.getMasterOperation(dbMasterReceipe.getCompanyCodeId(), dbMasterReceipe.getPlantId(),
                                dbMasterReceipe.getLanguageId(), dbMasterReceipe.getWarehouseId(), dbMasterReceipe.getOperationNumber(), dbMasterReceipe.getPhaseNumber());
                        if(masterOperation != null) {
                            newMasterReceipe.setPhaseDescription(masterOperation.getPhaseDescription());
                            newMasterReceipe.setOperationDescription(masterOperation.getOperationDescription());
                        }
                    }

                } else {
                    throw new BadRequestException("ReceipeId cannot be Null. provide company, plant, language & warehouse");
                }

                if(dbMasterReceipe.getStatusId() != null && dbMasterReceipe.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbMasterReceipe.getStatusId(), dbMasterReceipe.getLanguageId());
                    if (statusDescription != null) {
                        newMasterReceipe.setStatusDescription(statusDescription);
                    }
                }

                newMasterReceipe.setDeletionIndicator(0L);
                newMasterReceipe.setCreatedBy(loginUserID);
                newMasterReceipe.setCreatedOn(new Date());
                masterReceipeRepository.save(newMasterReceipe);
                createdMasterReceipeList.add(newMasterReceipe);
            }
            return createdMasterReceipeList;
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
     * @param loginUserID
     * @param modifyMasterReceipeList
     * @return
     */
    public List<MasterReceipe> updateMasterReceipeBatch(String companyCodeId, String plantId, String languageId, String warehouseId,
                                                        String loginUserID, List<MasterReceipe> modifyMasterReceipeList) {
        try {
            List<MasterReceipe> updatedMasterReceipeList = new ArrayList<>();
            String receipeId = null;
            String operationNumber = null;
            Date createdOn = null;
            String createdBy = null;
            String companyDesc = null;
            String plantDesc = null;
            String warehouseDesc = null;
            String statusDesc = null;
            if(modifyMasterReceipeList != null && !modifyMasterReceipeList.isEmpty()) {
                receipeId = modifyMasterReceipeList.get(0).getReceipeId();
                operationNumber = modifyMasterReceipeList.get(0).getOperationNumber();
                createdOn = modifyMasterReceipeList.get(0).getCreatedOn();
                createdBy = modifyMasterReceipeList.get(0).getCreatedBy();
                companyDesc = modifyMasterReceipeList.get(0).getCompanyDescription();
                plantDesc = modifyMasterReceipeList.get(0).getPlantDescription();
                warehouseDesc = modifyMasterReceipeList.get(0).getWarehouseDescription();
                statusDesc = modifyMasterReceipeList.get(0).getStatusDescription();

                List<MasterReceipe> masterReceipeList = masterReceipeRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndReceipeIdAndOperationNumberAndDeletionIndicator(
                  languageId, companyCodeId, plantId, warehouseId, modifyMasterReceipeList.get(0).getReceipeId(), modifyMasterReceipeList.get(0).getOperationNumber(),0L);
                if(masterReceipeList != null && !masterReceipeList.isEmpty()) {
                    masterReceipeRepository.deleteAll(masterReceipeList);
                    log.info("master receipe deleted successfully");
                }
                for (MasterReceipe modifyMasterReceipe : modifyMasterReceipeList) {
                    MasterReceipe dbMasterReceipe = new MasterReceipe();
                    log.info("Update Masters - Receipe Initiated : {}", modifyMasterReceipe);
                    BeanUtils.copyProperties(modifyMasterReceipe, dbMasterReceipe, CommonUtils.getNullPropertyNames(modifyMasterReceipe));

                    if(modifyMasterReceipe.getOperationDescription() == null || modifyMasterReceipe.getOperationDescription().isBlank() ||
                            modifyMasterReceipe.getPhaseDescription() == null || modifyMasterReceipe.getPhaseDescription().isBlank()) {
                        MasterOperation masterOperation = masterOperationService.getMasterOperation(dbMasterReceipe.getCompanyCodeId(), dbMasterReceipe.getPlantId(),
                                dbMasterReceipe.getLanguageId(), dbMasterReceipe.getWarehouseId(), dbMasterReceipe.getOperationNumber(), dbMasterReceipe.getPhaseNumber());
                        if(masterOperation != null) {
                            dbMasterReceipe.setPhaseDescription(masterOperation.getPhaseDescription());
                            dbMasterReceipe.setOperationDescription(masterOperation.getOperationDescription());
                        }
                    }
                    dbMasterReceipe.setReceipeId(receipeId);
                    dbMasterReceipe.setOperationNumber(operationNumber);
                    dbMasterReceipe.setCompanyDescription(companyDesc);
                    dbMasterReceipe.setPlantDescription(plantDesc);
                    dbMasterReceipe.setWarehouseDescription(warehouseDesc);
                    dbMasterReceipe.setStatusDescription(statusDesc);
                    dbMasterReceipe.setCreatedBy(createdBy);
                    dbMasterReceipe.setCreatedOn(createdOn);
                    dbMasterReceipe.setUpdatedBy(loginUserID);
                    dbMasterReceipe.setUpdatedOn(new Date());
                    MasterReceipe updatedMasterReceipe = masterReceipeRepository.save(dbMasterReceipe);
                    updatedMasterReceipeList.add(updatedMasterReceipe);
                }
            }
            return updatedMasterReceipeList;
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
     * @param loginUserID
     */
    public void deleteMasterReceipe(String companyCodeId, String plantId, String languageId, String warehouseId,
                                    String operationNumber, String receipeId, String loginUserID) {
        try {
            List<MasterReceipe> dbMasterReceipeList = getMasterReceipe(companyCodeId, plantId, languageId, warehouseId, receipeId, operationNumber);
            log.info("Delete Masters - Receipe Initiated : {}", dbMasterReceipeList);
            for(MasterReceipe dbMasterReceipe : dbMasterReceipeList) {
                dbMasterReceipe.setDeletionIndicator(1L);
                dbMasterReceipe.setUpdatedBy(loginUserID);
                dbMasterReceipe.setUpdatedOn(new Date());
                masterReceipeRepository.save(dbMasterReceipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
}