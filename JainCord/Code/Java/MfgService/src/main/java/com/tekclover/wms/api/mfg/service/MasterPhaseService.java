package com.tekclover.wms.api.mfg.service;

import com.tekclover.wms.api.mfg.controller.exception.BadRequestException;
import com.tekclover.wms.api.mfg.model.masterphase.MasterPhase;
import com.tekclover.wms.api.mfg.model.masterphase.SearchMasterPhase;
import com.tekclover.wms.api.mfg.repository.MasterPhaseRepository;
import com.tekclover.wms.api.mfg.repository.specification.MasterPhaseSpecification;
import com.tekclover.wms.api.mfg.util.CommonUtils;
import com.tekclover.wms.api.mfg.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class MasterPhaseService extends BaseService {

    @Autowired
    MasterPhaseRepository masterPhaseRepository;
    //--------------------------------------------------------------------------

    /**
     * @param companyCodeId
     * @param languageId
     * @param plantId
     * @param warehouseId
     * @param phaseNumber
     * @return
     */
    public MasterPhase getMasterPhase(String companyCodeId, String plantId, String languageId,
                                      String warehouseId, String phaseNumber) {
        try {
            Optional<MasterPhase> masterPhase =
                    masterPhaseRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndPhaseNumberAndDeletionIndicator(
                            languageId,
                            companyCodeId,
                            plantId,
                            warehouseId,
                            phaseNumber,
                            0L);
            if (masterPhase.isEmpty()) {
                throw new BadRequestException("The given values: warehouseId:" + warehouseId +
                        ",companyCodeId: " + companyCodeId +
                        ",languageId: " + languageId +
                        ",plantId: " + plantId +
                        ",phaseNumber: " + phaseNumber +
                        " doesn't exist.");
            }
            return masterPhase.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param searchMasterPhase
     * @return
     */
    public Stream<MasterPhase> findMasterPhase(SearchMasterPhase searchMasterPhase) {
        try {
            if (searchMasterPhase.getStartCreatedOn() != null && searchMasterPhase.getEndCreatedOn() != null) {
                Date[] dates = DateUtils.addTimeToDatesForSearch(searchMasterPhase.getStartCreatedOn(), searchMasterPhase.getEndCreatedOn());
                searchMasterPhase.setStartCreatedOn(dates[0]);
                searchMasterPhase.setEndCreatedOn(dates[1]);
            }
            log.info("searchMasterPhase Input: {}", searchMasterPhase);
            MasterPhaseSpecification spec = new MasterPhaseSpecification(searchMasterPhase);
            Stream<MasterPhase> results = masterPhaseRepository.stream(spec, MasterPhase.class);
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param newMasterPhase
     * @param loginUserID
     * @return
     */
    public MasterPhase createMasterPhase(MasterPhase newMasterPhase, String loginUserID) {
        try {
            Optional<MasterPhase> masterPhase =
                    masterPhaseRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndPhaseNumberAndDeletionIndicator(
                            newMasterPhase.getLanguageId(),
                            newMasterPhase.getCompanyCodeId(),
                            newMasterPhase.getPlantId(),
                            newMasterPhase.getWarehouseId(),
                            newMasterPhase.getPhaseNumber(),
                            0L);
            if (masterPhase.isPresent()) {
                throw new BadRequestException("Record is getting duplicated with the given values");
            }
            log.info("create Master Operation Initiated: {}", newMasterPhase);
            MasterPhase dbMasterPhase = new MasterPhase();
            BeanUtils.copyProperties(newMasterPhase, dbMasterPhase, CommonUtils.getNullPropertyNames(newMasterPhase));
            if (dbMasterPhase.getCompanyCodeId() != null && dbMasterPhase.getPlantId() != null &&
                    dbMasterPhase.getLanguageId() != null && dbMasterPhase.getWarehouseId() != null) {
                NUMBER_RANGE_CODE = 28L;
                numberRangeId = getNextRangeNumber(NUMBER_RANGE_CODE, dbMasterPhase.getCompanyCodeId(), dbMasterPhase.getPlantId(),
                        dbMasterPhase.getLanguageId(), dbMasterPhase.getWarehouseId());
                if (numberRangeId != null) {
                    dbMasterPhase.setPhaseNumber(numberRangeId);
                }
                description = getDescription(dbMasterPhase.getCompanyCodeId(), dbMasterPhase.getPlantId(),
                        dbMasterPhase.getLanguageId(), dbMasterPhase.getWarehouseId());
                if (description != null) {
                    dbMasterPhase.setCompanyDescription(description.getCompanyDescription());
                    dbMasterPhase.setPlantDescription(description.getPlantDescription());
                    dbMasterPhase.setWarehouseDescription(description.getWarehouseDescription());
                }
            } else {
                throw new BadRequestException("PhaseNumber cannot be Null. provide company, plant, language & warehouse");
            }
            if (dbMasterPhase.getStatusId() != null && dbMasterPhase.getLanguageId() != null) {
                statusDescription = getStatusDescription(dbMasterPhase.getStatusId(), dbMasterPhase.getLanguageId());
                if (statusDescription != null) {
                    dbMasterPhase.setStatusDescription(statusDescription);
                }
            }
            dbMasterPhase.setDeletionIndicator(0L);
            dbMasterPhase.setCreatedBy(loginUserID);
            dbMasterPhase.setCreatedOn(new Date());
            return masterPhaseRepository.save(dbMasterPhase);
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
     * @param phaseNumber
     * @param loginUserID
     * @param modifyMasterPhase
     * @return
     */
    public MasterPhase updateMasterPhase(String companyCodeId, String plantId, String languageId,
                                         String warehouseId, String phaseNumber,
                                         String loginUserID, MasterPhase modifyMasterPhase) {
        try {
            MasterPhase dbMasterPhase = getMasterPhase(companyCodeId, plantId, languageId,
                    warehouseId, phaseNumber);
            log.info("Update Masters - Operation Initiated : {}", dbMasterPhase);
            BeanUtils.copyProperties(modifyMasterPhase, dbMasterPhase, CommonUtils.getNullPropertyNames(modifyMasterPhase));
            if (dbMasterPhase.getStatusId() != null && dbMasterPhase.getLanguageId() != null) {
                statusDescription = getStatusDescription(dbMasterPhase.getStatusId(), dbMasterPhase.getLanguageId());
                if (statusDescription != null) {
                    dbMasterPhase.setStatusDescription(statusDescription);
                }
            }
            dbMasterPhase.setUpdatedBy(loginUserID);
            dbMasterPhase.setUpdatedOn(new Date());
            return masterPhaseRepository.save(dbMasterPhase);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }

    /**
     * @param newMasterPhaseList
     * @param loginUserID
     * @return
     */
    public List<MasterPhase> createMasterPhaseBatch(List<MasterPhase> newMasterPhaseList, String loginUserID) {
        try {
            List<MasterPhase> createdMasterPhaseList = new ArrayList<>();
            for (MasterPhase dbMasterPhase : newMasterPhaseList) {
                Optional<MasterPhase> masterPhase =
                        masterPhaseRepository.findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndPhaseNumberAndDeletionIndicator(
                                dbMasterPhase.getLanguageId(),
                                dbMasterPhase.getCompanyCodeId(),
                                dbMasterPhase.getPlantId(),
                                dbMasterPhase.getWarehouseId(),
                                dbMasterPhase.getPhaseNumber(),
                                0L);
                if (masterPhase.isPresent()) {
                    throw new BadRequestException("Record is getting duplicated with the given values");
                }
                log.info("create Master Operation Initiated: {}", dbMasterPhase);
                MasterPhase newMasterPhase = new MasterPhase();
                BeanUtils.copyProperties(dbMasterPhase, newMasterPhase, CommonUtils.getNullPropertyNames(dbMasterPhase));
                if (dbMasterPhase.getCompanyCodeId() != null && dbMasterPhase.getPlantId() != null &&
                        dbMasterPhase.getLanguageId() != null && dbMasterPhase.getWarehouseId() != null) {
                    NUMBER_RANGE_CODE = 28L;
                    numberRangeId = getNextRangeNumber(NUMBER_RANGE_CODE, dbMasterPhase.getCompanyCodeId(), dbMasterPhase.getPlantId(),
                            dbMasterPhase.getLanguageId(), dbMasterPhase.getWarehouseId());
                    if (numberRangeId != null) {
                        newMasterPhase.setPhaseNumber(numberRangeId);
                    }
                    description = getDescription(dbMasterPhase.getCompanyCodeId(), dbMasterPhase.getPlantId(),
                            dbMasterPhase.getLanguageId(), dbMasterPhase.getWarehouseId());
                    if (description != null) {
                        newMasterPhase.setCompanyDescription(description.getCompanyDescription());
                        newMasterPhase.setPlantDescription(description.getPlantDescription());
                        newMasterPhase.setWarehouseDescription(description.getWarehouseDescription());
                    }
                } else {
                    throw new BadRequestException("PhaseNumber cannot be Null. provide company, plant, language & warehouse");
                }
                if (dbMasterPhase.getStatusId() != null && dbMasterPhase.getLanguageId() != null) {
                    statusDescription = getStatusDescription(dbMasterPhase.getStatusId(), dbMasterPhase.getLanguageId());
                    if (statusDescription != null) {
                        newMasterPhase.setStatusDescription(statusDescription);
                    }
                }
                newMasterPhase.setDeletionIndicator(0L);
                newMasterPhase.setCreatedBy(loginUserID);
                newMasterPhase.setCreatedOn(new Date());
                masterPhaseRepository.save(newMasterPhase);
                createdMasterPhaseList.add(newMasterPhase);
            }
            return createdMasterPhaseList;
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
     * @param modifyMasterPhaseList
     * @return
     */
    public List<MasterPhase> updateMasterPhaseBatch(String companyCodeId, String plantId, String languageId,
                                                    String warehouseId, String loginUserID, List<MasterPhase> modifyMasterPhaseList) {
        try {
            List<MasterPhase> updatedMasterPhaseList = new ArrayList<>();

            for (MasterPhase dbMasterPhase : modifyMasterPhaseList) {
                MasterPhase updatedMasterPhase = updateMasterPhase(companyCodeId, plantId, languageId, warehouseId, dbMasterPhase.getPhaseNumber(), loginUserID, dbMasterPhase);
                updatedMasterPhaseList.add(updatedMasterPhase);
            }
            return updatedMasterPhaseList;
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
     * @param phaseNumber
     * @param loginUserID
     */
    public void deleteMasterPhase(String companyCodeId, String plantId, String languageId,
                                  String warehouseId, String phaseNumber, String loginUserID) {
        try {
            if (phaseNumber != null && !phaseNumber.isBlank()) {
                MasterPhase dbMasterPhase = getMasterPhase(companyCodeId, plantId, languageId, warehouseId, phaseNumber);
                log.info("Delete Masters - Operation Initiated : {}", dbMasterPhase);
                dbMasterPhase.setDeletionIndicator(1L);
                dbMasterPhase.setUpdatedBy(loginUserID);
                dbMasterPhase.setUpdatedOn(new Date());
                masterPhaseRepository.save(dbMasterPhase);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Exception : " + e);
        }
    }
}