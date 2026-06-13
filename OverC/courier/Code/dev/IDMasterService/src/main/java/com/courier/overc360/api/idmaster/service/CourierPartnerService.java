package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.courierpartner.AddCourierPartner;
import com.courier.overc360.api.idmaster.primary.model.courierpartner.CourierPartner;
import com.courier.overc360.api.idmaster.primary.model.courierpartner.UpdateCourierPartner;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.CourierPartnerRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.courierpartner.FindCourierPartner;
import com.courier.overc360.api.idmaster.replica.model.courierpartner.ReplicaCourierPartner;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCourierPartnerRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaCourierPartnerSpecification;
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

@Service
@Slf4j
public class CourierPartnerService {

    @Autowired
    private CourierPartnerRepository courierPartnerRepository;

    @Autowired
    private ReplicaCourierPartnerRepository replicaCourierPartnerRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    /*======================================================PRIMARY=============================================================*/

    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param courierPartnerId
     * @return
     */
    public CourierPartner getCourierPartner(String companyId, String languageId, String courierPartnerId, String partnerId) {
        Optional<CourierPartner> dbCourierPartner = courierPartnerRepository.findByCompanyIdAndLanguageIdAndCourierPartnerIdAndPartnerIdAndDeletionIndicator
                (companyId, languageId, courierPartnerId, partnerId, 0L);
        if (dbCourierPartner.isEmpty()) {
            //Error Log
            createCourierPartnerLog1(languageId, companyId, courierPartnerId, " CourierPartnerId - " + courierPartnerId + " and given values doesn't exists");
            throw new BadRequestException("The given values : languageId - " + languageId + " companyId - " + companyId +
                    " CourierPartnerId - " + courierPartnerId + " doesn't exists");
        }
        return dbCourierPartner.get();
    }

    /**
     * Create
     *
     * @param addCourierPartner
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public CourierPartner createCourierPartner(AddCourierPartner addCourierPartner, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            courierPartnerRepository.findByCompanyIdAndLanguageIdAndCourierPartnerIdAndPartnerIdAndDeletionIndicator
                            (addCourierPartner.getCompanyId(), addCourierPartner.getLanguageId(), addCourierPartner.getCourierPartnerId(), addCourierPartner.getPartnerId(), 0L)
                    .ifPresent(duplicate -> {
                        throw new BadRequestException("Record is getting duplicated with the given values : courierPartnerId - " + addCourierPartner.getCourierPartnerId());
                    });
            companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator
                            (addCourierPartner.getCompanyId(), addCourierPartner.getLanguageId(), 0L)
                    .orElseThrow(() -> new BadRequestException("The given values : CompanyId - " + addCourierPartner.getCompanyId()
                            + " and LanguageId - " + addCourierPartner.getLanguageId() + "  doesn't exists"));

            log.info("new CourierPartner --> " + addCourierPartner);
            CourierPartner newCourierPartner = new CourierPartner();
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addCourierPartner.getLanguageId(), addCourierPartner.getCompanyId());
            BeanUtils.copyProperties(addCourierPartner, newCourierPartner, CommonUtils.getNullPropertyNames(addCourierPartner));
            if (iKeyValuePair != null) {
                newCourierPartner.setCompanyName(iKeyValuePair.getCompanyDesc());
                newCourierPartner.setLanguageDescription(iKeyValuePair.getLangDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addCourierPartner.getStatusId());
            if (statusDesc != null) {
                newCourierPartner.setStatusDescription(statusDesc);
            }
            //Save without spacing
            newCourierPartner.setCourierPartnerId(newCourierPartner.getCourierPartnerId().replaceAll("\\s+",""));

            newCourierPartner.setDeletionIndicator(0L);
            newCourierPartner.setCreatedBy(loginUserID);
            newCourierPartner.setCreatedOn(new Date());
            newCourierPartner.setUpdatedBy(null);
            newCourierPartner.setUpdatedOn(null);
            return courierPartnerRepository.save(newCourierPartner);
        } catch (Exception e) {
            // Error Log
            createCourierPartnerLog2(addCourierPartner, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update
     *
     * @param companyId
     * @param languageId
     * @param courierPartnerId
     * @param updateCourierPartner
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    public CourierPartner updateCourierPartner(String companyId, String languageId, String courierPartnerId, String partnerId,
                                                UpdateCourierPartner updateCourierPartner, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            CourierPartner dbCourierPartner = getCourierPartner(companyId, languageId, courierPartnerId, partnerId);
            BeanUtils.copyProperties(updateCourierPartner, dbCourierPartner, CommonUtils.getNullPropertyNames(updateCourierPartner));

            if (updateCourierPartner.getStatusId() != null && !updateCourierPartner.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateCourierPartner.getStatusId());
                if (statusDesc != null) {
                    dbCourierPartner.setStatusDescription(statusDesc);
                }
            }
            dbCourierPartner.setUpdatedBy(loginUserID);
            dbCourierPartner.setUpdatedOn(new Date());
            return courierPartnerRepository.save(dbCourierPartner);
        } catch (Exception e) {
            // Error Log
            createCourierPartnerLog(companyId, languageId, courierPartnerId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete
     *
     * @param companyId
     * @param languageId
     * @param courierPartnerId
     * @param loginUserID
     */
    public void deleteCourierPartner(String companyId, String languageId, String courierPartnerId, String partnerId, String loginUserID){
        CourierPartner dbCourierPartner = getCourierPartner(companyId,languageId,courierPartnerId, partnerId);
        if(dbCourierPartner !=null){
            dbCourierPartner.setDeletionIndicator(1L);
            dbCourierPartner.setUpdatedBy(loginUserID);
            dbCourierPartner.setUpdatedOn(new Date());
            courierPartnerRepository.save(dbCourierPartner);
        } else {
            // Error Log
            createCourierPartnerLog1(companyId, languageId, courierPartnerId, "Error in deleting CourierPartnerId - " + courierPartnerId);
            throw new EntityNotFoundException("Error in deleting CourierPartnerId - " + courierPartnerId);
        }
    }
    /*======================================================REPLICA=====================================================*/


    public List<ReplicaCourierPartner> getAll(){
        List<ReplicaCourierPartner> courierPartnerList = replicaCourierPartnerRepository.findAll();
        courierPartnerList = courierPartnerList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return courierPartnerList;
    }


    public ReplicaCourierPartner getReplicaCourierPartner(String companyId, String languageId, String courierPartnerId, String partnerId) {
        Optional<ReplicaCourierPartner> dbCourierPartnerId = replicaCourierPartnerRepository.findByCompanyIdAndLanguageIdAndCourierPartnerIdAndPartnerIdAndDeletionIndicator
                (companyId, languageId, courierPartnerId, partnerId, 0L);
        if (dbCourierPartnerId.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId + " and courierPartnerId - " + courierPartnerId + " doesn't exists";
            // Error Log
            createCourierPartnerLog1(companyId, languageId, courierPartnerId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbCourierPartnerId.get();
    }


    public List<ReplicaCourierPartner> findCourierPartner(FindCourierPartner findCourierPartner){
        ReplicaCourierPartnerSpecification spec = new ReplicaCourierPartnerSpecification(findCourierPartner);
        List<ReplicaCourierPartner> results = replicaCourierPartnerRepository.findAll(spec);
        log.info("found CourierPartner --> {}", results);
        return results;
    }

    //=========================================Courier_Partner_log====================================================

    private void createCourierPartnerLog(String companyId, String languageId, String courierPartnerId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(courierPartnerId);
        errorLog.setMethod("Exception thrown in updatecourierPartner");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createCourierPartnerLog1(String companyId, String languageId, String courierPartnerId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(courierPartnerId);
        errorLog.setMethod("Exception thrown in getcourierPartner");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createCourierPartnerLog2(AddCourierPartner addCourierPartner, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addCourierPartner.getLanguageId());
        errorLog.setCompanyId(addCourierPartner.getCompanyId());
        errorLog.setRefDocNumber(addCourierPartner.getCourierPartnerId());
        errorLog.setMethod("Exception thrown in createCourierPartner");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }
}






