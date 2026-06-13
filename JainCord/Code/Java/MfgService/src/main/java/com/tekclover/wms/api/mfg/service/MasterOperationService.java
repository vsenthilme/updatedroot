package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.masteroperation.MasterOperation;
import com.tekclover.wms.api.mfg.model.masteroperation.SearchMasterOperation;
import com.tekclover.wms.api.mfg.repository.MasterOperationRepository;
import com.tekclover.wms.api.mfg.repository.specification.MasterOperationSpecification;
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
public class MasterOperationService extends BaseService {

    @Autowired
    MasterOperationRepository masterOperationRepository;
    //--------------------------------------------------------------------------

    /**
     * @param companyCodeId
     * @param languageId
     * @param plantId
     * @param warehouseId
     * @param operationNumber
     * @param phaseNumber
     * @return
     */
    public MasterOperation getMasterOperation(String companyCodeId, String plantId, String languageId,
                                              String warehouseId, String operationNumber, String phaseNumber) {
        Optional<MasterOperation> masterOperation =
                masterOperationRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndPhaseNumberAndDeletionIndicator(
                        languageId,
                        companyCodeId,
                        plantId,
                        warehouseId,
                        operationNumber,
                        phaseNumber,
                        0L);
        if (masterOperation.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",operationNumber: " + operationNumber +
                    ",phaseNumber: " + phaseNumber +
                    " doesn't exist.");
        }
        return masterOperation.get();
    }
    /**
     * @param companyCodeId
     * @param plantId
     * @param languageId
     * @param warehouseId
     * @param operationNumber
     * @return
     */
    public MasterOperation getMasterOperation(String companyCodeId, String plantId, String languageId,
                                              String warehouseId, String operationNumber) {
        List<MasterOperation> masterOperation =
                masterOperationRepository.findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndOperationNumberAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        operationNumber,
                        0L);
        if (masterOperation == null || masterOperation.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",operationNumber: " + operationNumber +
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
     * @param operationNumber
     * @return
     */
    public List<MasterOperation> getMasterOperations(String companyCodeId, String plantId, String languageId,
                                                     String warehouseId, String operationNumber) {
        List<MasterOperation> masterOperation =
                masterOperationRepository.findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndOperationNumberAndDeletionIndicator(
                        companyCodeId,
                        plantId,
                        languageId,
                        warehouseId,
                        operationNumber,
                        0L);
        if (masterOperation == null || masterOperation.isEmpty()) {
            throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                    ",companyCodeId: " + companyCodeId +
                    ",languageId: " + languageId +
                    ",plantId: " + plantId +
                    ",operationNumber: " + operationNumber +
                    " doesn't exist.");
        }
        return masterOperation;
    }

    /**
     * @param searchMasterOperation
     * @return
     * @throws Exception
     */
    public Stream<MasterOperation> findMasterOperation(SearchMasterOperation searchMasterOperation) throws Exception {
        if (searchMasterOperation.getStartCreatedOn() != null && searchMasterOperation.getEndCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearch(searchMasterOperation.getStartCreatedOn(), searchMasterOperation.getEndCreatedOn());
            searchMasterOperation.setStartCreatedOn(dates[0]);
            searchMasterOperation.setEndCreatedOn(dates[1]);
        }
        log.info("searchMasterOperation Input: {}", searchMasterOperation);
        MasterOperationSpecification spec = new MasterOperationSpecification(searchMasterOperation);
        Stream<MasterOperation> results = masterOperationRepository.stream(spec, MasterOperation.class);
        return results;
    }

    /**
     * @param newMasterOperation
     * @param loginUserID
     * @return
     */
    public MasterOperation createMasterOperation(MasterOperation newMasterOperation, String loginUserID) {
        try {
            Optional<MasterOperation> masterOperation =
                    masterOperationRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndPhaseNumberAndDeletionIndicator(
                            newMasterOperation.getLanguageId(),
                            newMasterOperation.getCompanyCodeId(),
                            newMasterOperation.getPlantId(),
                            newMasterOperation.getWarehouseId(),
                            newMasterOperation.getOperationNumber(),
                            newMasterOperation.getPhaseNumber(),
                            0L);
            if (masterOperation.isPresent()) {
                throw new BadRequestException("Record is getting duplicated with the given values");
            }
            log.info("create Master Operation Initiated: {}", newMasterOperation);
            MasterOperation dbMasterOperation = new MasterOperation();
            BeanUtils.copyProperties(newMasterOperation, dbMasterOperation, CommonUtils.getNullPropertyNames(newMasterOperation));
            if (dbMasterOperation.getCompanyCodeId() != null && dbMasterOperation.getPlantId() != null &&
                    dbMasterOperation.getLanguageId() != null && dbMasterOperation.getWarehouseId() != null) {
                NUMBER_RANGE_CODE = 21L;
                numberRangeId = getNextRangeNumber(NUMBER_RANGE_CODE, dbMasterOperation.getCompanyCodeId(), dbMasterOperation.getPlantId(),
                        dbMasterOperation.getLanguageId(), dbMasterOperation.getWarehouseId());
                if (numberRangeId != null) {
                    dbMasterOperation.setOperationNumber(numberRangeId);
                }
                description = getDescription(dbMasterOperation.getCompanyCodeId(), dbMasterOperation.getPlantId(),
                        dbMasterOperation.getLanguageId(), dbMasterOperation.getWarehouseId());
                if (description != null) {
                    dbMasterOperation.setCompanyDescription(description.getCompanyDescription());
                    dbMasterOperation.setPlantDescription(description.getPlantDescription());
                    dbMasterOperation.setWarehouseDescription(description.getWarehouseDescription());
                }
            } else {
                throw new BadRequestException("OperationNumber cannot be Null. provide company, plant, language & warehouse");
            }
            if (dbMasterOperation.getStatusId() != null && dbMasterOperation.getLanguageId() != null) {
                statusDescription = getStatusDescription(dbMasterOperation.getStatusId(), dbMasterOperation.getLanguageId());
                if (statusDescription != null) {
                    dbMasterOperation.setStatusDescription(statusDescription);
                }
            }
            dbMasterOperation.setDeletionIndicator(0L);
            dbMasterOperation.setCreatedBy(loginUserID);
            dbMasterOperation.setCreatedOn(new Date());
            return masterOperationRepository.save(dbMasterOperation);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param companyCodeId
     * @param languageId
     * @param plantId
     * @param warehouseId
     * @param operationNumber
     * @param phaseNumber
     * @param loginUserID
     * @param modifyMasterOperation
     * @return
     */
    public MasterOperation updateMasterOperation(String companyCodeId, String plantId, String languageId,
                                                 String warehouseId, String operationNumber, String phaseNumber,
                                                 String loginUserID, MasterOperation modifyMasterOperation) {
        try {
            MasterOperation dbMasterOperation = getMasterOperation(companyCodeId, plantId, languageId,
                    warehouseId, operationNumber, phaseNumber);
            log.info("Update Masters - Operation Initiated : {}", dbMasterOperation);
            BeanUtils.copyProperties(modifyMasterOperation, dbMasterOperation, CommonUtils.getNullPropertyNames(modifyMasterOperation));
            if (dbMasterOperation.getStatusId() != null && dbMasterOperation.getLanguageId() != null) {
                statusDescription = getStatusDescription(dbMasterOperation.getStatusId(), dbMasterOperation.getLanguageId());
                if (statusDescription != null) {
                    dbMasterOperation.setStatusDescription(statusDescription);
                }
            }
            dbMasterOperation.setUpdatedBy(loginUserID);
            dbMasterOperation.setUpdatedOn(new Date());
            return masterOperationRepository.save(dbMasterOperation);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param newMasterOperationList
     * @param loginUserID
     * @return
     */
    public List<MasterOperation> createMasterOperationBatch(List<MasterOperation> newMasterOperationList, String loginUserID) {
        try {
            List<MasterOperation> createdMasterOperationList = new ArrayList<>();
            String operationNumber = null;
            for (MasterOperation dbMasterOperation : newMasterOperationList) {
                Optional<MasterOperation> masterOperation =
                        masterOperationRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndPhaseNumberAndDeletionIndicator(
                                dbMasterOperation.getLanguageId(),
                                dbMasterOperation.getCompanyCodeId(),
                                dbMasterOperation.getPlantId(),
                                dbMasterOperation.getWarehouseId(),
                                dbMasterOperation.getOperationNumber(),
                                dbMasterOperation.getPhaseNumber(),
                                0L);
                if (masterOperation.isPresent()) {
                    throw new BadRequestException("Record is getting duplicated with the given values");
                }
                log.info("create Master Operation Initiated: {}", dbMasterOperation);
                MasterOperation newMasterOperation = new MasterOperation();
                BeanUtils.copyProperties(dbMasterOperation, newMasterOperation, CommonUtils.getNullPropertyNames(dbMasterOperation));
                if (dbMasterOperation.getCompanyCodeId() != null && dbMasterOperation.getPlantId() != null &&
                        dbMasterOperation.getLanguageId() != null && dbMasterOperation.getWarehouseId() != null) {
                    if (operationNumber != null) {
                        newMasterOperation.setOperationNumber(operationNumber);
                    } else {
                        NUMBER_RANGE_CODE = 21L;
                        numberRangeId = getNextRangeNumber(NUMBER_RANGE_CODE, dbMasterOperation.getCompanyCodeId(), dbMasterOperation.getPlantId(),
                                dbMasterOperation.getLanguageId(), dbMasterOperation.getWarehouseId());
                        if (numberRangeId != null) {
                            newMasterOperation.setOperationNumber(numberRangeId);
                            operationNumber = numberRangeId;
                        }
                    }
                    description = getDescription(dbMasterOperation.getCompanyCodeId(), dbMasterOperation.getPlantId(),
                            dbMasterOperation.getLanguageId(), dbMasterOperation.getWarehouseId());
                    if (description != null) {
                        newMasterOperation.setCompanyDescription(description.getCompanyDescription());
                        newMasterOperation.setPlantDescription(description.getPlantDescription());
                        newMasterOperation.setWarehouseDescription(description.getWarehouseDescription());
                    }
                } else {
                    throw new BadRequestException("OperationNumber cannot be Null. provide company, plant, language & warehouse");
                }
                if (dbMasterOperation.getStatusId() != null && dbMasterOperation.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbMasterOperation.getStatusId(), dbMasterOperation.getLanguageId());
                    if (statusDescription != null) {
                        newMasterOperation.setStatusDescription(statusDescription);
                    }
                }
                newMasterOperation.setDeletionIndicator(0L);
                newMasterOperation.setCreatedBy(loginUserID);
                newMasterOperation.setCreatedOn(new Date());
                masterOperationRepository.save(newMasterOperation);
                createdMasterOperationList.add(newMasterOperation);
            }
            return createdMasterOperationList;
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
     * @param modifyMasterOperationList
     * @return
     */
    public List<MasterOperation> updateMasterOperationBatch(String companyCodeId, String plantId, String languageId,
                                                            String warehouseId, String loginUserID, List<MasterOperation> modifyMasterOperationList) {
        try {
            List<MasterOperation> updatedMasterOperationList = new ArrayList<>();
            String operationNumber = null;
            Date createdOn = null;
            String createdBy = null;
            String companyDesc = null;
            String plantDesc = null;
            String warehouseDesc = null;
            String statusDesc = null;
            if (modifyMasterOperationList != null && !modifyMasterOperationList.isEmpty()) {
                operationNumber = modifyMasterOperationList.get(0).getOperationNumber();
                createdOn = modifyMasterOperationList.get(0).getCreatedOn();
                createdBy = modifyMasterOperationList.get(0).getCreatedBy();
                companyDesc = modifyMasterOperationList.get(0).getCompanyDescription();
                plantDesc = modifyMasterOperationList.get(0).getPlantDescription();
                warehouseDesc = modifyMasterOperationList.get(0).getWarehouseDescription();
                statusDesc = modifyMasterOperationList.get(0).getStatusDescription();
                List<MasterOperation> masterOperationList = masterOperationRepository.findByCompanyCodeIdAndPlantIdAndLanguageIdAndWarehouseIdAndOperationNumberAndDeletionIndicator(
                        companyCodeId, plantId, languageId, warehouseId, modifyMasterOperationList.get(0).getOperationNumber(), 0L);
                if (masterOperationList != null && !masterOperationList.isEmpty()) {
                    masterOperationRepository.deleteAll(masterOperationList);
                    log.info("master operation deleted successfully");
                }

                for (MasterOperation dbMasterOperation : modifyMasterOperationList) {
                    log.info("Update Masters - Operation Initiated : {}", dbMasterOperation);
                    MasterOperation newMasterOperation = new MasterOperation();
                    BeanUtils.copyProperties(dbMasterOperation, newMasterOperation, CommonUtils.getNullPropertyNames(dbMasterOperation));
                    if (operationNumber != null) {
                        newMasterOperation.setOperationNumber(operationNumber);
                    }
                    newMasterOperation.setCompanyDescription(companyDesc);
                    newMasterOperation.setPlantDescription(plantDesc);
                    newMasterOperation.setWarehouseDescription(warehouseDesc);
                    newMasterOperation.setStatusDescription(statusDesc);
                    newMasterOperation.setDeletionIndicator(0L);
                    newMasterOperation.setCreatedBy(createdBy);
                    newMasterOperation.setCreatedOn(createdOn);
                    newMasterOperation.setUpdatedBy(loginUserID);
                    newMasterOperation.setUpdatedOn(new Date());
                    masterOperationRepository.save(newMasterOperation);
                    updatedMasterOperationList.add(newMasterOperation);
                }
            }
            return updatedMasterOperationList;
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
     * @param phaseNumber
     * @param loginUserID
     */
    public void deleteMasterOperation(String companyCodeId, String plantId, String languageId,
                                      String warehouseId, String operationNumber, String phaseNumber, String loginUserID) {
        try {
            if(phaseNumber != null && !phaseNumber.isBlank()) {
                MasterOperation dbMasterOperation = getMasterOperation(companyCodeId, plantId, languageId, warehouseId, operationNumber, phaseNumber);
                log.info("Delete Masters - Operation Initiated : {}", dbMasterOperation);
                dbMasterOperation.setDeletionIndicator(1L);
                dbMasterOperation.setUpdatedBy(loginUserID);
                dbMasterOperation.setUpdatedOn(new Date());
                masterOperationRepository.save(dbMasterOperation);
            } else {
                List<MasterOperation> masterOperationList = getMasterOperations(companyCodeId, plantId, languageId, warehouseId, operationNumber);
                log.info("Delete Masters - Operation Initiated : {}", masterOperationList.size());
                for(MasterOperation dbMasterOperation : masterOperationList) {
                    dbMasterOperation.setDeletionIndicator(1L);
                    dbMasterOperation.setUpdatedBy(loginUserID);
                    dbMasterOperation.setUpdatedOn(new Date());
                    masterOperationRepository.save(dbMasterOperation);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
}