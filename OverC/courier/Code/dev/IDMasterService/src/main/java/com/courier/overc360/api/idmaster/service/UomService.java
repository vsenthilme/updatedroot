package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.company.Company;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.uom.AddUom;
import com.courier.overc360.api.idmaster.primary.model.uom.Uom;
import com.courier.overc360.api.idmaster.primary.model.uom.UpdateUom;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.UomRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.uom.FindUom;
import com.courier.overc360.api.idmaster.replica.model.uom.ReplicaUom;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaUomRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaUomSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UomService {

    @Autowired
    private UomRepository uomRepository;

    @Autowired
    private ReplicaUomRepository replicaUomRepository;

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
     * @param uomId
     * @return
     */
    public Uom getUom(String companyId, String languageId, String uomId) {
        Optional<Uom> dbUom = uomRepository.findByCompanyIdAndLanguageIdAndUomIdAndDeletionIndicator
                (companyId, languageId, uomId, 0l);
        if (dbUom.isEmpty()) {
            //Error Log
            createUomLog1(languageId, companyId, uomId, " UomId - " + uomId + " and given values doesn't exists");
            throw new BadRequestException("The given values : languageId - " + languageId + " companyId - " + companyId +
                    " uomId - " + uomId + " doesn't exists");
        }
        return dbUom.get();
    }

    /**
     * Create
     *
     * @param addUom
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Uom createUom(AddUom addUom, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Optional<Company> dbCompany = companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator
                    (addUom.getCompanyId(), addUom.getLanguageId(), 0L);
            if (dbCompany.isEmpty()) {
                throw new BadRequestException("CompanyId - " + addUom.getCompanyId() + " and LanguageId - " + addUom.getLanguageId() + " doesn't exists");
            }
            Optional<Uom> duplicateUom = uomRepository.findByCompanyIdAndLanguageIdAndUomIdAndDeletionIndicator
                    (addUom.getCompanyId(), addUom.getLanguageId(), addUom.getUomId(), 0L);
            if (duplicateUom.isPresent()) {
                throw new BadRequestException("Record is getting duplicated with uomId - " + addUom.getUomId());
            } else {
                log.info("new Uom --> " + addUom);
                Uom newUom = new Uom();
                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addUom.getLanguageId(), addUom.getCompanyId());
                BeanUtils.copyProperties(addUom, newUom, CommonUtils.getNullPropertyNames(addUom));
                if (iKeyValuePair != null) {
                    newUom.setCompanyName(iKeyValuePair.getCompanyDesc());
                    newUom.setLanguageDescription(iKeyValuePair.getLangDesc());
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addUom.getStatusId());
                if (statusDesc != null) {
                    newUom.setStatusDescription(statusDesc);
                }
                newUom.setDeletionIndicator(0l);
                newUom.setCreatedBy(loginUserID);
                newUom.setCreatedOn(new Date());
                newUom.setUpdatedBy(loginUserID);
                newUom.setUpdatedOn(new Date());
                return uomRepository.save(newUom);
            }
        } catch (Exception e) {
            // Error Log
            createUomLog2(addUom, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update
     *
     * @param companyId
     * @param languageId
     * @param uomId
     * @param updateUom
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    public Uom updateUom(String companyId, String languageId, String uomId,
                         UpdateUom updateUom, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Uom dbuom = getUom(companyId, languageId, uomId);
            BeanUtils.copyProperties(updateUom, dbuom, CommonUtils.getNullPropertyNames(updateUom));
            if (updateUom.getStatusId() != null && !updateUom.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateUom.getStatusId());
                if (statusDesc != null) {
                    dbuom.setStatusDescription(statusDesc);
                }
            }
            dbuom.setUpdatedBy(loginUserID);
            dbuom.setUpdatedOn(new Date());
            return uomRepository.save(dbuom);
        } catch (Exception e) {
            // Error Log
            createUomLog(companyId, languageId, uomId, e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete
     *
     * @param companyId
     * @param languageId
     * @param uomId
     * @param loginUserID
     */
    public void deleteUom(String companyId, String languageId, String uomId, String loginUserID) {

        Uom dbuom = getUom(companyId, languageId, uomId);
        if (dbuom != null) {
            dbuom.setDeletionIndicator(1l);
            dbuom.setUpdatedBy(loginUserID);
            dbuom.setUpdatedOn(new Date());
            uomRepository.save(dbuom);
        } else {
            // Error Log
            createUomLog1(companyId, languageId, uomId, "Error in deleting uomId - " + uomId);
            throw new BadRequestException("Error in deleting UomId - " + uomId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /***
     * Get All
     *
     * @return
     */
    public List<ReplicaUom> getAllUom(){
        List<ReplicaUom> uomList= replicaUomRepository.findAll();
        uomList = uomList.stream().filter(i -> i.getDeletionIndicator() ==0).collect(Collectors.toList());
        return  uomList;
    }

    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param uomId
     * @return
     */
    public ReplicaUom getReplicaUom(String companyId, String languageId, String uomId) {
        Optional<ReplicaUom> dbUom = replicaUomRepository.findByCompanyIdAndLanguageIdAndUomIdAndDeletionIndicator
                (companyId, languageId, uomId, 0l);
        if (dbUom.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId + " and uomId - " + uomId
                    + " doesn't exists";
            // Error Log
            createUomLog1(companyId, languageId, uomId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbUom.get();
    }

    public List<ReplicaUom> findUom(FindUom findUom) throws ParseException {
        ReplicaUomSpecification spec = new ReplicaUomSpecification(findUom);
        List<ReplicaUom> results = replicaUomRepository.findAll(spec);
        log.info("found Replica Uom --> " + results);
        return results;
    }

    //==============================================UOM_ErrorLog==================================================

    private void createUomLog( String companyId, String languageId,String uomId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(uomId);
        errorLog.setMethod("Exception thrown in updateUOM");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createUomLog1( String companyId,String languageId, String uomId,String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(uomId);
        errorLog.setMethod("Exception thrown in getUOM");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createUomLog2(AddUom addUom, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addUom.getLanguageId());
        errorLog.setCompanyId(addUom.getCompanyId());
        errorLog.setRefDocNumber(addUom.getUomId());
        errorLog.setMethod("Exception thrown in createUOM");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }
}







