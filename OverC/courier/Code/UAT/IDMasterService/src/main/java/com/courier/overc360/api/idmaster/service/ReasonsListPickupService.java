package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.company.Company;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.reasonslistpickup.AddReasonsListPickup;
import com.courier.overc360.api.idmaster.primary.model.reasonslistpickup.ReasonsListPickup;
import com.courier.overc360.api.idmaster.primary.model.reasonslistpickup.UpdateReasonsListPickup;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.ReasonsListPickupRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.reasonslistpickup.FindReasonsListPickup;
import com.courier.overc360.api.idmaster.replica.model.reasonslistpickup.ReplicaReasonsListPickup;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaReasonsListPickupRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaReasonsListPickupSpecification;
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
public class ReasonsListPickupService {
    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ReasonsListPickupRepository reasonsListPickupRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ReplicaReasonsListPickupRepository replicaReasonsListPickupRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get Reasons List Pickup
     *
     * @param companyId
     * @param languageId
     * @param reasonsId
     * @return
     */
    public ReasonsListPickup getReasonsListPickup(String companyId, String languageId, String reasonsId) {
        Optional<ReasonsListPickup> dbReasonsListPickup = reasonsListPickupRepository.findByCompanyIdAndLanguageIdAndReasonsIdAndDeletionIndicator(
                companyId, languageId, reasonsId, 0L);
        if (dbReasonsListPickup.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId +
                    " and reasonsId - " + reasonsId + " doesn't exists";
            // Error Log
            createReasonsListPickupLog1(languageId, companyId, reasonsId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbReasonsListPickup.get();
    }

    /**
     * Create ReasonsListPickup
     *
     * @param addReasonsListPickup
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public ReasonsListPickup createReasonsListPickup(AddReasonsListPickup addReasonsListPickup, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Optional<ReasonsListPickup> duplicateReasonsListPickup = reasonsListPickupRepository.findByCompanyIdAndLanguageIdAndReasonsIdAndDeletionIndicator(
                    addReasonsListPickup.getCompanyId(), addReasonsListPickup.getLanguageId(), addReasonsListPickup.getReasonsId(), 0L);

            Optional<Company> dbCompany = companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addReasonsListPickup.getCompanyId(), addReasonsListPickup.getLanguageId(), 0L);

            if (dbCompany.isEmpty()) {
                throw new BadRequestException("The given values : CompanyId - " + addReasonsListPickup.getCompanyId()
                        + " and LanguageId - " + addReasonsListPickup.getLanguageId() + "  doesn't exists");
            } else if (duplicateReasonsListPickup.isPresent()) {
                throw new BadRequestException("Record is getting Duplicated with the given values : reasonsId - " + addReasonsListPickup.getReasonsId());
            } else {
                log.info("new ReasonsListPickup --> {}", addReasonsListPickup);
                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addReasonsListPickup.getLanguageId(), addReasonsListPickup.getCompanyId());
                ReasonsListPickup newReasonsListPickup = new ReasonsListPickup();
                BeanUtils.copyProperties(addReasonsListPickup, newReasonsListPickup, CommonUtils.getNullPropertyNames(addReasonsListPickup));
                if ((addReasonsListPickup.getReasonsId() != null &&
                        (addReasonsListPickup.getReferenceField10() != null && addReasonsListPickup.getReferenceField10().equalsIgnoreCase("true"))) ||
                        addReasonsListPickup.getReasonsId() == null || addReasonsListPickup.getReasonsId().isBlank()) {
                    String NUM_RAN_OBJ = "NPR";
                    String REASONS_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                    log.info("next Value from NumberRange for REASONS_ID : " + REASONS_ID);
                    newReasonsListPickup.setReasonsId(REASONS_ID);
                }
                if (iKeyValuePair != null) {
                    newReasonsListPickup.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newReasonsListPickup.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addReasonsListPickup.getStatusId());
                if (statusDesc != null) {
                    newReasonsListPickup.setStatusDescription(statusDesc);
                }
                newReasonsListPickup.setDeletionIndicator(0L);
                newReasonsListPickup.setCreatedBy(loginUserID);
                newReasonsListPickup.setUpdatedBy(loginUserID);
                newReasonsListPickup.setCreatedOn(new Date());
                newReasonsListPickup.setUpdatedOn(new Date());
                return reasonsListPickupRepository.save(newReasonsListPickup);
            }
        } catch (Exception e) {
            // Error Log
            createReasonsListPickupLog2(addReasonsListPickup, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update ReasonsListPickup
     *
     * @param companyId
     * @param languageId
     * @param reasonsId
     * @param loginUserID
     * @param updateReasonsListPickup
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public ReasonsListPickup updateReasonsListPickup(String companyId, String languageId, String reasonsId, String loginUserID,
                                               UpdateReasonsListPickup updateReasonsListPickup)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            ReasonsListPickup dbReasonsListPickup = getReasonsListPickup(companyId, languageId, reasonsId);
            BeanUtils.copyProperties(updateReasonsListPickup, dbReasonsListPickup, CommonUtils.getNullPropertyNames(updateReasonsListPickup));
            if (updateReasonsListPickup.getStatusId() != null && !updateReasonsListPickup.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateReasonsListPickup.getStatusId());
                if (statusDesc != null) {
                    dbReasonsListPickup.setStatusDescription(statusDesc);
                }
            }
            dbReasonsListPickup.setUpdatedBy(loginUserID);
            dbReasonsListPickup.setUpdatedOn(new Date());
            return reasonsListPickupRepository.save(dbReasonsListPickup);
        } catch (Exception e) {
            // Error Log
            createReasonsListPickupLog(languageId, companyId, reasonsId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete ReasonsListPickup
     *
     * @param loginUserID
     * @param languageId
     * @param reasonsId
     * @param companyId
     */
    public void deleteReasonsListPickup(String companyId, String languageId, String reasonsId, String loginUserID) {
        ReasonsListPickup dbReasonsListPickup = getReasonsListPickup(companyId, languageId, reasonsId);
        if (dbReasonsListPickup != null) {
            dbReasonsListPickup.setDeletionIndicator(1L);
            dbReasonsListPickup.setUpdatedBy(loginUserID);
            dbReasonsListPickup.setUpdatedOn(new Date());
            reasonsListPickupRepository.save(dbReasonsListPickup);
        } else {
            // Error Log
            createReasonsListPickupLog1(languageId, companyId, reasonsId, "Error in deleting reasonsId - " + reasonsId);
            throw new EntityNotFoundException("Error in deleting ReasonsId - " + reasonsId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get All ReasonsListPickup Details
     *
     * @return
     */
    public List<ReplicaReasonsListPickup> getAllReasonsListPickups() {
        List<ReplicaReasonsListPickup> reasonsListPickups = replicaReasonsListPickupRepository.findAll();
        reasonsListPickups = reasonsListPickups.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return reasonsListPickups;
    }

    /**
     * Get ReasonsListPickup
     *
     * @param companyId
     * @param languageId
     * @param reasonsId
     * @return
     */
    public ReplicaReasonsListPickup getReplicaReasonsListPickup(String companyId, String languageId, String reasonsId) {
        Optional<ReplicaReasonsListPickup> dbReasonsListPickup = replicaReasonsListPickupRepository.findByCompanyIdAndLanguageIdAndReasonsIdAndDeletionIndicator(
                companyId, languageId, reasonsId, 0L);
        if (dbReasonsListPickup.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId +
                     " and reasonsId - " + reasonsId + " doesn't exists";
            // Error Log
            createReasonsListPickupLog1(languageId, companyId, reasonsId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbReasonsListPickup.get();
    }

    /**
     * Find ReasonsListPickup
     *
     * @param findReasonsListPickup
     * @return
     */
    public List<ReplicaReasonsListPickup> findReasonsListPickups(FindReasonsListPickup findReasonsListPickup) {

        ReplicaReasonsListPickupSpecification spec = new ReplicaReasonsListPickupSpecification(findReasonsListPickup);
        List<ReplicaReasonsListPickup> results = replicaReasonsListPickupRepository.findAll(spec);
        log.info("found ReasonsListPickups --> {}", results);
        return results;
    }

    //=========================================ReasonsListPickup_ErrorLog====================================================
    private void createReasonsListPickupLog(String languageId, String companyId, String reasonsId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(reasonsId);
        errorLog.setMethod("Exception thrown in updateReasonsListPickup");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createReasonsListPickupLog1(String languageId, String companyId, String reasonsId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(reasonsId);
        errorLog.setMethod("Exception thrown in getReasonsListPickup");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createReasonsListPickupLog2(AddReasonsListPickup addReasonsListPickup, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addReasonsListPickup.getLanguageId());
        errorLog.setCompanyId(addReasonsListPickup.getCompanyId());
        errorLog.setRefDocNumber(addReasonsListPickup.getReasonsId());
        errorLog.setMethod("Exception thrown in createReasonsListPickup");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
