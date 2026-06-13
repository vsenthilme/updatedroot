package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.specialapproval.AddSpecialApproval;
import com.courier.overc360.api.idmaster.primary.model.specialapproval.SpecialApproval;
import com.courier.overc360.api.idmaster.primary.model.specialapproval.UpdateSpecialApproval;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.SpecialApprovalRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.specialapproval.FindSpecialApproval;
import com.courier.overc360.api.idmaster.replica.model.specialapproval.ReplicaSpecialApproval;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaSpecialApprovalRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaSpecialApprovalSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SpecialApprovalService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private SpecialApprovalRepository specialApprovalRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ReplicaSpecialApprovalRepository replicaSpecialApprovalRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get SpecialApproval
     *
     * @param companyId
     * @param languageId
     * @param specialApprovalId
     * @return
     */
    public SpecialApproval getSpecialApproval(String companyId, String languageId, String specialApprovalId) {
        Optional<SpecialApproval> dbSpecialApproval = specialApprovalRepository.findByCompanyIdAndLanguageIdAndSpecialApprovalIdAndDeletionIndicator(
                companyId, languageId, specialApprovalId, 0L);
        if (dbSpecialApproval.isEmpty()) {
            // Error Log
            createSpecialApprovalLog1(languageId, companyId, specialApprovalId, "SpecialApprovalId - " + specialApprovalId + " and given values doesn't exists");
            throw new BadRequestException("The given values : companyId - " + companyId + " ,languageId - " + languageId +
                    " and specialApprovalId - " + specialApprovalId + " doesn't exists");
        }
        return dbSpecialApproval.get();
    }

    /**
     * Create SpecialApproval
     *
     * @param addSpecialApproval
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public SpecialApproval createSpecialApproval(AddSpecialApproval addSpecialApproval, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbCompanyPresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addSpecialApproval.getCompanyId(), addSpecialApproval.getLanguageId(), 0L);
            if (!dbCompanyPresent) {
                throw new BadRequestException("CompanyId: " + addSpecialApproval.getCompanyId() + " and languageId" + addSpecialApproval.getLanguageId() + "  doesn't exists");
            }

            Optional<SpecialApproval> duplicateSpecialApproval = specialApprovalRepository.findByCompanyIdAndLanguageIdAndSpecialApprovalIdAndDeletionIndicator(
                    addSpecialApproval.getCompanyId(), addSpecialApproval.getLanguageId(), addSpecialApproval.getSpecialApprovalId(), 0L);
            if (duplicateSpecialApproval.isPresent()) {
                throw new BadRequestException("Record is getting Duplicated with the given values : specialApprovalId - " + addSpecialApproval.getSpecialApprovalId());
            }
            log.info("new SpecialApproval --> {}", addSpecialApproval);
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addSpecialApproval.getLanguageId(), addSpecialApproval.getCompanyId());
            SpecialApproval newSpecialApproval = new SpecialApproval();
            BeanUtils.copyProperties(addSpecialApproval, newSpecialApproval, CommonUtils.getNullPropertyNames(addSpecialApproval));
            if ((addSpecialApproval.getSpecialApprovalId() != null &&
                    (addSpecialApproval.getReferenceField10() != null && addSpecialApproval.getReferenceField10().equalsIgnoreCase("true"))) ||
                    addSpecialApproval.getSpecialApprovalId() == null || addSpecialApproval.getSpecialApprovalId().isBlank()) {
                String NUM_RAN_OBJ = "SPECIALAPPROVAL";
                String SPECIAL_APPROVAL_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                log.info("next Value from NumberRange for SPECIAL_APPROVAL_ID : " + SPECIAL_APPROVAL_ID);
                newSpecialApproval.setSpecialApprovalId(SPECIAL_APPROVAL_ID);
            }
            if (iKeyValuePair != null) {
                newSpecialApproval.setLanguageDescription(iKeyValuePair.getLangDesc());
                newSpecialApproval.setCompanyName(iKeyValuePair.getCompanyDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addSpecialApproval.getStatusId());
            if (statusDesc != null) {
                newSpecialApproval.setStatusDescription(statusDesc);
            }
            newSpecialApproval.setDeletionIndicator(0L);
            newSpecialApproval.setCreatedBy(loginUserID);
            newSpecialApproval.setUpdatedBy(loginUserID);
            newSpecialApproval.setCreatedOn(new Date());
            newSpecialApproval.setUpdatedOn(new Date());
            return specialApprovalRepository.save(newSpecialApproval);
        } catch (Exception e) {
            // Error Log
            createSpecialApprovalLog2(addSpecialApproval, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update SpecialApproval
     *
     * @param companyId
     * @param languageId
     * @param specialApprovalId
     * @param loginUserID
     * @param updateSpecialApproval
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public SpecialApproval updateSpecialApproval(String companyId, String languageId, String specialApprovalId, String loginUserID,
                                                 UpdateSpecialApproval updateSpecialApproval)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            SpecialApproval dbSpecialApproval = getSpecialApproval(companyId, languageId, specialApprovalId);
            BeanUtils.copyProperties(updateSpecialApproval, dbSpecialApproval, CommonUtils.getNullPropertyNames(updateSpecialApproval));
            if (updateSpecialApproval.getStatusId() != null && !updateSpecialApproval.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateSpecialApproval.getStatusId());
                if (statusDesc != null) {
                    dbSpecialApproval.setStatusDescription(statusDesc);
                }
            }
            dbSpecialApproval.setUpdatedBy(loginUserID);
            dbSpecialApproval.setUpdatedOn(new Date());
            return specialApprovalRepository.save(dbSpecialApproval);
        } catch (Exception e) {
            // Error Log
            createSpecialApprovalLog(languageId, companyId, specialApprovalId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete SpecialApproval
     *
     * @param companyId
     * @param languageId
     * @param specialApprovalId
     * @param loginUserID
     */
    public void deleteSpecialApproval(String companyId, String languageId, String specialApprovalId, String loginUserID) {
        SpecialApproval dbSpecialApproval = getSpecialApproval(companyId, languageId, specialApprovalId);
        if (dbSpecialApproval != null) {
            dbSpecialApproval.setDeletionIndicator(1L);
            dbSpecialApproval.setUpdatedBy(loginUserID);
            dbSpecialApproval.setUpdatedOn(new Date());
            specialApprovalRepository.save(dbSpecialApproval);
        } else {
            // Error Log
            createSpecialApprovalLog1(languageId, companyId, specialApprovalId, "Error in deleting SpecialApprovalId - " + specialApprovalId);
            throw new EntityNotFoundException("Error in deleting SpecialApprovalId - " + specialApprovalId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get All Replica SpecialApproval Details
     *
     * @return
     */
    public List<ReplicaSpecialApproval> getAllSpecialApproval() {
        List<ReplicaSpecialApproval> specialApprovalList = replicaSpecialApprovalRepository.findAll();
        specialApprovalList = specialApprovalList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return specialApprovalList;
    }

    /**
     * Get Replica SpecialApproval
     *
     * @param companyId
     * @param languageId
     * @param specialApprovalId
     * @return
     */
    public ReplicaSpecialApproval getReplicaSpecialApproval(String companyId, String languageId, String specialApprovalId) {
        Optional<ReplicaSpecialApproval> dbSpecialApproval = replicaSpecialApprovalRepository.findByCompanyIdAndLanguageIdAndSpecialApprovalIdAndDeletionIndicator(
                companyId, languageId, specialApprovalId, 0L);
        if (dbSpecialApproval.isEmpty()) {
            // Error Log
            createSpecialApprovalLog1(languageId, companyId, specialApprovalId, "SpecialApprovalId - " + specialApprovalId + " and given values doesn't exists");
            throw new BadRequestException("The given values : companyId - " + companyId + " languageId - " + languageId +
                    " SpecialApprovalId - " + specialApprovalId + " doesn't exists");
        }
        return dbSpecialApproval.get();
    }

    /**
     * Find SpecialApproval
     *
     * @param findSpecialApproval
     * @return
     */
    public List<ReplicaSpecialApproval> findSpecialApproval(FindSpecialApproval findSpecialApproval) {

        ReplicaSpecialApprovalSpecification spec = new ReplicaSpecialApprovalSpecification(findSpecialApproval);
        List<ReplicaSpecialApproval> results = replicaSpecialApprovalRepository.findAll(spec);
        log.info("found SpecialApproval --> " + results);
        return results;
    }

    //=========================================ServiceType_ErrorLog====================================================
    private void createSpecialApprovalLog(String languageId, String companyId, String specialApprovalId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(specialApprovalId);
        errorLog.setMethod("Exception thrown in updateSpecialApproval");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createSpecialApprovalLog1(String languageId, String companyId, String specialApprovalId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(specialApprovalId);
        errorLog.setMethod("Exception thrown in getSpecialApproval");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createSpecialApprovalLog2(AddSpecialApproval addSpecialApproval, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addSpecialApproval.getLanguageId());
        errorLog.setCompanyId(addSpecialApproval.getCompanyId());
        errorLog.setRefDocNumber(addSpecialApproval.getSpecialApprovalId());
        errorLog.setMethod("Exception thrown in createSpecialApproval");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}