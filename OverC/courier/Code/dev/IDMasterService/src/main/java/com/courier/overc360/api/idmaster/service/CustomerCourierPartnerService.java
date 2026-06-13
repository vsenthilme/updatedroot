package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.customercourierpartner.AddCustomerCourierPartner;
import com.courier.overc360.api.idmaster.primary.model.customercourierpartner.CustomerCourierPartner;
import com.courier.overc360.api.idmaster.primary.model.customercourierpartner.UpdateCustomerCourierPartner;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.CustomerCourierPartnerRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.customercourierpartner.FindCustomerCourierPartner;
import com.courier.overc360.api.idmaster.replica.model.customercourierpartner.ReplicaCustomerCourierPartner;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCustomerCourierPartnerRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaCustomerCourierPartnerSpecification;
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
public class CustomerCourierPartnerService {

    @Autowired
    private CustomerCourierPartnerRepository customerCourierPartnerRepository;

    @Autowired
    private ReplicaCustomerCourierPartnerRepository replicaCustomerCourierPartnerRepository;

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
     * @param partnerId
     * @return
     */
    public CustomerCourierPartner getCustomerCourierPartner(String companyId, String languageId, String courierPartnerId, String partnerId) {
        Optional<CustomerCourierPartner> dbCustomerCourierPartner = customerCourierPartnerRepository.findByCompanyIdAndLanguageIdAndCourierPartnerIdAndPartnerIdAndDeletionIndicator
                (companyId, languageId, courierPartnerId, partnerId, 0L);
        if (dbCustomerCourierPartner.isEmpty()) {
            //Error Log
            createCustomerCourierPartnerLog1(languageId, companyId, courierPartnerId, partnerId ," CourierPartnerId - " + courierPartnerId + " and given values doesn't exists");
            throw new BadRequestException("The given values : languageId - " + languageId + " companyId - " + companyId + " partnerId - " + partnerId +
                    " CourierPartnerId - " + courierPartnerId + " doesn't exists");
        }
        return dbCustomerCourierPartner.get();
    }

    /**
     * Create
     *
     * @param addCustomerCourierPartner
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public CustomerCourierPartner createCustomerCourierPartner(AddCustomerCourierPartner addCustomerCourierPartner, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            customerCourierPartnerRepository.findByCompanyIdAndLanguageIdAndCourierPartnerIdAndPartnerIdAndDeletionIndicator
                            (addCustomerCourierPartner.getCompanyId(), addCustomerCourierPartner.getLanguageId(), addCustomerCourierPartner.getCourierPartnerId(), addCustomerCourierPartner.getPartnerId(), 0L)
                    .ifPresent(duplicate -> {
                        throw new BadRequestException("Record is getting duplicated with the given values : courierPartnerId - " + addCustomerCourierPartner.getCourierPartnerId());
                    });
            companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator
                            (addCustomerCourierPartner.getCompanyId(), addCustomerCourierPartner.getLanguageId(), 0L)
                    .orElseThrow(() -> new BadRequestException("The given values : CompanyId - " + addCustomerCourierPartner.getCompanyId()
                            + " and LanguageId - " + addCustomerCourierPartner.getLanguageId() + "  doesn't exists"));

            log.info("new CustomerCourierPartner --> " + addCustomerCourierPartner);
            CustomerCourierPartner newCustomerCourierPartner = new CustomerCourierPartner();
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addCustomerCourierPartner.getLanguageId(), addCustomerCourierPartner.getCompanyId());
            BeanUtils.copyProperties(addCustomerCourierPartner, newCustomerCourierPartner, CommonUtils.getNullPropertyNames(addCustomerCourierPartner));
            if (iKeyValuePair != null) {
                newCustomerCourierPartner.setCompanyName(iKeyValuePair.getCompanyDesc());
                newCustomerCourierPartner.setLanguageDescription(iKeyValuePair.getLangDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addCustomerCourierPartner.getStatusId());
            if (statusDesc != null) {
                newCustomerCourierPartner.setStatusDescription(statusDesc);
            }
            //Save without spacing
            newCustomerCourierPartner.setCourierPartnerId(newCustomerCourierPartner.getCourierPartnerId().replaceAll("\\s+", ""));

            newCustomerCourierPartner.setDeletionIndicator(0L);
            newCustomerCourierPartner.setCreatedBy(loginUserID);
            newCustomerCourierPartner.setCreatedOn(new Date());
            newCustomerCourierPartner.setUpdatedBy(null);
            newCustomerCourierPartner.setUpdatedOn(null);
            return customerCourierPartnerRepository.save(newCustomerCourierPartner);
        } catch (Exception e) {
            // Error Log
            createCustomerCourierPartnerLog2(addCustomerCourierPartner, e.toString());
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
     * @param partnerId
     * @param updateCustomerCourierPartner
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    public CustomerCourierPartner updateCustomerCourierPartner(String companyId, String languageId, String courierPartnerId, String partnerId,
                                                               UpdateCustomerCourierPartner updateCustomerCourierPartner, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            CustomerCourierPartner dbCustomerCourierPartner = getCustomerCourierPartner(companyId, languageId, courierPartnerId, partnerId);
            BeanUtils.copyProperties(updateCustomerCourierPartner, dbCustomerCourierPartner, CommonUtils.getNullPropertyNames(updateCustomerCourierPartner));

            if (updateCustomerCourierPartner.getStatusId() != null && !updateCustomerCourierPartner.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateCustomerCourierPartner.getStatusId());
                if (statusDesc != null) {
                    dbCustomerCourierPartner.setStatusDescription(statusDesc);
                }
            }
            dbCustomerCourierPartner.setUpdatedBy(loginUserID);
            dbCustomerCourierPartner.setUpdatedOn(new Date());
            return customerCourierPartnerRepository.save(dbCustomerCourierPartner);
        } catch (Exception e) {
            // Error Log
            createCustomerCourierPartnerLog(companyId, languageId, courierPartnerId, partnerId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete
     *
     * @param companyId
     * @param languageId
     * @param partnerId
     * @param courierPartnerId
     * @param loginUserID
     */
    public void deleteCustomerCourierPartner(String companyId, String languageId, String courierPartnerId, String partnerId, String loginUserID) {
        CustomerCourierPartner dbCustomerCourierPartner = getCustomerCourierPartner(companyId, languageId, courierPartnerId, partnerId);
        if (dbCustomerCourierPartner != null) {
            dbCustomerCourierPartner.setDeletionIndicator(1L);
            dbCustomerCourierPartner.setUpdatedBy(loginUserID);
            dbCustomerCourierPartner.setUpdatedOn(new Date());
            customerCourierPartnerRepository.save(dbCustomerCourierPartner);
        } else {
            // Error Log
            createCustomerCourierPartnerLog1(companyId, languageId, courierPartnerId, partnerId, "Error in deleting CourierPartnerId - " + courierPartnerId);
            throw new EntityNotFoundException("Error in deleting CourierPartnerId - " + courierPartnerId);
        }
    }
    /*======================================================REPLICA=====================================================*/


    public List<ReplicaCustomerCourierPartner> getAll() {
        List<ReplicaCustomerCourierPartner> customerCourierPartnerList = replicaCustomerCourierPartnerRepository.findAll();
        customerCourierPartnerList = customerCourierPartnerList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return customerCourierPartnerList;
    }


    public ReplicaCustomerCourierPartner getReplicaCustomerCourierPartner(String companyId, String languageId, String courierPartnerId, String partnerId) {
        Optional<ReplicaCustomerCourierPartner> dbCustomerCourierPartner = replicaCustomerCourierPartnerRepository.findByCompanyIdAndLanguageIdAndCourierPartnerIdAndPartnerIdAndDeletionIndicator
                (companyId, languageId, courierPartnerId, partnerId, 0L);
        if (dbCustomerCourierPartner.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId + ", partnerId - " + partnerId +" and courierPartnerId - " + courierPartnerId + " doesn't exists";
            // Error Log
            createCustomerCourierPartnerLog1(companyId, languageId, courierPartnerId, partnerId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbCustomerCourierPartner.get();
    }


    public List<ReplicaCustomerCourierPartner> findCustomerCourierPartner(FindCustomerCourierPartner findCustomerCourierPartner) {
        ReplicaCustomerCourierPartnerSpecification spec = new ReplicaCustomerCourierPartnerSpecification(findCustomerCourierPartner);
        List<ReplicaCustomerCourierPartner> results = replicaCustomerCourierPartnerRepository.findAll(spec);
        log.info("found CustomerCourierPartner --> {}", results);
        return results;
    }

    //=========================================Courier_Partner_log====================================================

    private void createCustomerCourierPartnerLog(String companyId, String languageId, String courierPartnerId, String partnerId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(courierPartnerId);
        errorLog.setReferenceField1(partnerId);
        errorLog.setMethod("Exception thrown in updateCustomerCourierPartner");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createCustomerCourierPartnerLog1(String companyId, String languageId, String courierPartnerId, String partnerId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(courierPartnerId);
        errorLog.setReferenceField1(partnerId);
        errorLog.setMethod("Exception thrown in getCustomerCourierPartner");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createCustomerCourierPartnerLog2(AddCustomerCourierPartner addCustomerCourierPartner, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addCustomerCourierPartner.getLanguageId());
        errorLog.setCompanyId(addCustomerCourierPartner.getCompanyId());
        errorLog.setRefDocNumber(addCustomerCourierPartner.getCourierPartnerId());
        errorLog.setMethod("Exception thrown in createCustomerCourierPartner");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }
}

