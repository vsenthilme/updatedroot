package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.company.Company;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.reasonslistdelivery.AddReasonsListDelivery;
import com.courier.overc360.api.idmaster.primary.model.reasonslistdelivery.ReasonsListDelivery;
import com.courier.overc360.api.idmaster.primary.model.reasonslistdelivery.UpdateReasonsListDelivery;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.ReasonsListDeliveryRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.reasonslistdelivery.FindReasonsListDelivery;
import com.courier.overc360.api.idmaster.replica.model.reasonslistdelivery.ReplicaReasonsListDelivery;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaReasonsListDeliveryRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaReasonsListDeliverySpecification;
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
public class ReasonsListDeliveryService {


    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ReasonsListDeliveryRepository reasonsListDeliveryRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ReplicaReasonsListDeliveryRepository replicaReasonsListDeliveryRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get Reasons List
     *
     * @param companyId
     * @param languageId
     * @param reasonsId
     * @return
     */
    public ReasonsListDelivery getReasonsListDelivery(String companyId, String languageId, String reasonsId) {
        Optional<ReasonsListDelivery> dbReasonsListDelivery = reasonsListDeliveryRepository.findByCompanyIdAndLanguageIdAndReasonsIdAndDeletionIndicator(
                companyId, languageId, reasonsId, 0L);
        if (dbReasonsListDelivery.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId +
                    " and reasonsId - " + reasonsId + " doesn't exists";
            // Error Log
            createReasonsListDeliveryLog1(languageId, companyId, reasonsId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbReasonsListDelivery.get();
    }

    /**
     * Create ReasonsListDelivery
     *
     * @param addReasonsListDelivery
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public ReasonsListDelivery createReasonsListDelivery(AddReasonsListDelivery addReasonsListDelivery, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Optional<ReasonsListDelivery> duplicateReasonsListDelivery = reasonsListDeliveryRepository.findByCompanyIdAndLanguageIdAndReasonsIdAndDeletionIndicator(
                    addReasonsListDelivery.getCompanyId(), addReasonsListDelivery.getLanguageId(), addReasonsListDelivery.getReasonsId(), 0L);

            Optional<Company> dbCompany = companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addReasonsListDelivery.getCompanyId(), addReasonsListDelivery.getLanguageId(), 0L);

            if (dbCompany.isEmpty()) {
                throw new BadRequestException("The given values : CompanyId - " + addReasonsListDelivery.getCompanyId()
                        + " and LanguageId - " + addReasonsListDelivery.getLanguageId() + "  doesn't exists");
            } else if (duplicateReasonsListDelivery.isPresent()) {
                throw new BadRequestException("Record is getting Duplicated with the given values : reasonsId - " + addReasonsListDelivery.getReasonsId());
            } else {
                log.info("new ReasonsListDelivery --> {}", addReasonsListDelivery);
                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addReasonsListDelivery.getLanguageId(), addReasonsListDelivery.getCompanyId());
                ReasonsListDelivery newReasonsListDelivery = new ReasonsListDelivery();
                BeanUtils.copyProperties(addReasonsListDelivery, newReasonsListDelivery, CommonUtils.getNullPropertyNames(addReasonsListDelivery));
                if ((addReasonsListDelivery.getReasonsId() != null &&
                        (addReasonsListDelivery.getReferenceField10() != null && addReasonsListDelivery.getReferenceField10().equalsIgnoreCase("true"))) ||
                        addReasonsListDelivery.getReasonsId() == null || addReasonsListDelivery.getReasonsId().isBlank()) {
                    String NUM_RAN_OBJ = "NDR";
                    String REASONS_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                    log.info("next Value from NumberRange for REASONS_ID : " + REASONS_ID);
                    newReasonsListDelivery.setReasonsId(REASONS_ID);
                }
                if (iKeyValuePair != null) {
                    newReasonsListDelivery.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newReasonsListDelivery.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addReasonsListDelivery.getStatusId());
                if (statusDesc != null) {
                    newReasonsListDelivery.setStatusDescription(statusDesc);
                }
                newReasonsListDelivery.setDeletionIndicator(0L);
                newReasonsListDelivery.setCreatedBy(loginUserID);
                newReasonsListDelivery.setUpdatedBy(loginUserID);
                newReasonsListDelivery.setCreatedOn(new Date());
                newReasonsListDelivery.setUpdatedOn(new Date());
                return reasonsListDeliveryRepository.save(newReasonsListDelivery);
            }
        } catch (Exception e) {
            // Error Log
            createReasonsListDeliveryLog2(addReasonsListDelivery, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update ReasonsListDelivery
     *
     * @param companyId
     * @param languageId
     * @param reasonsId
     * @param loginUserID
     * @param updateReasonsListDelivery
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public ReasonsListDelivery updateReasonsListDelivery(String companyId, String languageId, String reasonsId, String loginUserID,
                                                         UpdateReasonsListDelivery updateReasonsListDelivery)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            ReasonsListDelivery dbReasonsListDelivery = getReasonsListDelivery(companyId, languageId, reasonsId);
            BeanUtils.copyProperties(updateReasonsListDelivery, dbReasonsListDelivery, CommonUtils.getNullPropertyNames(updateReasonsListDelivery));
            if (updateReasonsListDelivery.getStatusId() != null && !updateReasonsListDelivery.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateReasonsListDelivery.getStatusId());
                if (statusDesc != null) {
                    dbReasonsListDelivery.setStatusDescription(statusDesc);
                }
            }
            dbReasonsListDelivery.setUpdatedBy(loginUserID);
            dbReasonsListDelivery.setUpdatedOn(new Date());
            return reasonsListDeliveryRepository.save(dbReasonsListDelivery);
        } catch (Exception e) {
            // Error Log
            createReasonsListDeliveryLog(languageId, companyId, reasonsId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete ReasonsListDelivery
     *
     * @param loginUserID
     * @param languageId
     * @param reasonsId
     * @param companyId
     */
    public void deleteReasonsListDelivery(String companyId, String languageId, String reasonsId, String loginUserID) {
        ReasonsListDelivery dbReasonsListDelivery = getReasonsListDelivery(companyId, languageId, reasonsId);
        if (dbReasonsListDelivery != null) {
            dbReasonsListDelivery.setDeletionIndicator(1L);
            dbReasonsListDelivery.setUpdatedBy(loginUserID);
            dbReasonsListDelivery.setUpdatedOn(new Date());
            reasonsListDeliveryRepository.save(dbReasonsListDelivery);
        } else {
            // Error Log
            createReasonsListDeliveryLog1(languageId, companyId, reasonsId, "Error in deleting reasonsId - " + reasonsId);
            throw new EntityNotFoundException("Error in deleting ReasonsId - " + reasonsId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get All ReasonsListDelivery Details
     *
     * @return
     */
    public List<ReplicaReasonsListDelivery> getAllReasonsListDeliveries() {
        List<ReplicaReasonsListDelivery> reasonsListDeliveries = replicaReasonsListDeliveryRepository.findAll();
        reasonsListDeliveries = reasonsListDeliveries.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return reasonsListDeliveries;
    }

    /**
     * Get ReasonsListDelivery
     *
     * @param companyId
     * @param languageId
     * @param reasonsId
     * @return
     */
    public ReplicaReasonsListDelivery getReplicaReasonsListDelivery(String companyId, String languageId, String reasonsId) {
        Optional<ReplicaReasonsListDelivery> dbReasonsListDelivery = replicaReasonsListDeliveryRepository.findByCompanyIdAndLanguageIdAndReasonsIdAndDeletionIndicator(
                companyId, languageId, reasonsId, 0L);
        if (dbReasonsListDelivery.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId +
                    " and reasonsId - " + reasonsId + " doesn't exists";
            // Error Log
            createReasonsListDeliveryLog1(languageId, companyId, reasonsId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbReasonsListDelivery.get();
    }

    /**
     * Find ReasonsListDelivery
     *
     * @param findReasonsListDelivery
     * @return
     */
    public List<ReplicaReasonsListDelivery> findReasonsListDeliveries(FindReasonsListDelivery findReasonsListDelivery) {

        ReplicaReasonsListDeliverySpecification spec = new ReplicaReasonsListDeliverySpecification(findReasonsListDelivery);
        List<ReplicaReasonsListDelivery> results = replicaReasonsListDeliveryRepository.findAll(spec);
        log.info("found ReasonsListDeliveries --> {}", results);
        return results;
    }

    //=========================================ReasonsListDelivery_ErrorLog====================================================
    private void createReasonsListDeliveryLog(String languageId, String companyId, String reasonsId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(reasonsId);
        errorLog.setMethod("Exception thrown in updateReasonsListDelivery");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createReasonsListDeliveryLog1(String languageId, String companyId, String reasonsId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(reasonsId);
        errorLog.setMethod("Exception thrown in getReasonsListDelivery");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createReasonsListDeliveryLog2(AddReasonsListDelivery addReasonsListDelivery, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addReasonsListDelivery.getLanguageId());
        errorLog.setCompanyId(addReasonsListDelivery.getCompanyId());
        errorLog.setRefDocNumber(addReasonsListDelivery.getReasonsId());
        errorLog.setMethod("Exception thrown in createReasonsListDelivery");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

}
