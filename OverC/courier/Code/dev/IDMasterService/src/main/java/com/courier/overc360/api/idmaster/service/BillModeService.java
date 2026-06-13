package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.billmode.AddBillMode;
import com.courier.overc360.api.idmaster.primary.model.billmode.BillMode;
import com.courier.overc360.api.idmaster.primary.model.billmode.UpdateBillMode;
import com.courier.overc360.api.idmaster.primary.model.company.Company;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.repository.BillModeRepository;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.billmode.FindBillMode;
import com.courier.overc360.api.idmaster.replica.model.billmode.ReplicaBillMode;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaBillModeRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaBillModeSpecification;
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
public class BillModeService {

    @Autowired
    private BillModeRepository billModeRepository;

    @Autowired
    private ReplicaBillModeRepository replicaBillModeRepository;

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
     * @param billModeId
     * @return
     */
    public BillMode getBillMode(String companyId, String languageId, String billModeId) {
        Optional<BillMode> dbBillMode = billModeRepository.findByCompanyIdAndLanguageIdAndBillModeIdAndDeletionIndicator
                (companyId, languageId, billModeId, 0L);
        if (dbBillMode.isEmpty()) {
            //Error Log
            createBillModeLog1(languageId, companyId, billModeId, " billModeId - " + billModeId + " and given values doesn't exists");
            throw new BadRequestException("The given values : languageId - " + languageId + " companyId - " + companyId +
                    " and billModeId - " + billModeId + " doesn't exists");
        }
        return dbBillMode.get();
    }

    /**
     * Create
     *
     * @param addBillMode
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public BillMode createBillMode(AddBillMode addBillMode, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
//            companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator
//                            (addBillMode.getCompanyId(), addBillMode.getLanguageId(),0L)
//                    .isEmpty(dbCompany -> {
//                        throw new BadRequestException("CompanyId - " + addBillMode.getCompanyId() + " and LanguageId - " + addBillMode.getLanguageId() + " doesn't exists");
//                    });
            Optional<Company> dbCompany = companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator
                    (addBillMode.getCompanyId(), addBillMode.getLanguageId(), 0L);
            if (dbCompany.isEmpty()) {
                throw new BadRequestException("CompanyId - " + addBillMode.getCompanyId() + " and LanguageId - " + addBillMode.getLanguageId() + " doesn't exists");
            }

            billModeRepository.findByCompanyIdAndLanguageIdAndBillModeIdAndDeletionIndicator
                            (addBillMode.getCompanyId(), addBillMode.getLanguageId(),  addBillMode.getBillModeId(),0L)
                    .ifPresent(duplicate -> {
                        throw new BadRequestException("Record is getting duplicated with the given values : billModeId - " + addBillMode.getBillModeId());
                    });
                log.info("new BillMode --> " + addBillMode);
            BillMode newBillMode = new BillMode();
                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addBillMode.getLanguageId(), addBillMode.getCompanyId());
                BeanUtils.copyProperties(addBillMode, newBillMode, CommonUtils.getNullPropertyNames(addBillMode));
            if ((addBillMode.getBillMode() != null &&
                    (addBillMode.getReferenceField10() != null && addBillMode.getReferenceField10().equalsIgnoreCase("true"))) ||
                    addBillMode.getBillMode() == null || addBillMode.getBillMode().isBlank()) {
                String BILL_MODE_ID = numberRangeService.getNextNumberRange("BILLMODEID");
                log.info("next Value from NumberRange for BILL_MODE_ID : " + BILL_MODE_ID);
                newBillMode.setBillModeId(BILL_MODE_ID);
            }
                if (iKeyValuePair != null) {
                    newBillMode.setCompanyName(iKeyValuePair.getCompanyDesc());
                    newBillMode.setLanguageDescription(iKeyValuePair.getLangDesc());
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addBillMode.getStatusId());
                if (statusDesc != null) {
                    newBillMode.setStatusDescription(statusDesc);
                }
            newBillMode.setDeletionIndicator(0l);
            newBillMode.setCreatedBy(loginUserID);
            newBillMode.setCreatedOn(new Date());
            newBillMode.setUpdatedBy(loginUserID);
            newBillMode.setUpdatedOn(new Date());
                return billModeRepository.save(newBillMode);
        } catch (Exception e) {
            // Error Log
            createBillModeLog2(addBillMode, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update
     *
     * @param companyId
     * @param languageId
     * @param billModeId
     * @param updateBillMode
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    public BillMode updateBillMode(String companyId, String languageId, String billModeId,
                                   UpdateBillMode updateBillMode, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            BillMode dbBillMode= getBillMode(companyId, languageId, billModeId);
            BeanUtils.copyProperties(updateBillMode, dbBillMode, CommonUtils.getNullPropertyNames(updateBillMode));
            if (updateBillMode.getStatusId() != null && !updateBillMode.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateBillMode.getStatusId());
                if (statusDesc != null) {
                    dbBillMode.setStatusDescription(statusDesc);
                }
            }
            dbBillMode.setUpdatedBy(loginUserID);
            dbBillMode.setUpdatedOn(new Date());
            return billModeRepository.save(dbBillMode);
        } catch (Exception e) {
            // Error Log
            createBillModeLog(companyId, languageId, billModeId, e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete
     *
     * @param companyId
     * @param languageId
     * @param billModeId
     * @param loginUserID
     */
    public void deleteBillMode(String companyId, String languageId, String billModeId, String loginUserID) {

        BillMode dbBillMode = getBillMode(companyId, languageId, billModeId);
        if (dbBillMode != null) {
            dbBillMode.setDeletionIndicator(1l);
            dbBillMode.setUpdatedBy(loginUserID);
            dbBillMode.setUpdatedOn(new Date());
            billModeRepository.save(dbBillMode);
        } else {
            // Error Log
            createBillModeLog1(companyId, languageId, billModeId, "Error in deleting billModeId - " + billModeId);
            throw new BadRequestException("Error in deleting BillModeId - " + billModeId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get All
     *
     * @return
     */
    public List<ReplicaBillMode> getAllBillMode(){
        List<ReplicaBillMode> billModeList= replicaBillModeRepository.findAll();
        billModeList = billModeList.stream().filter(i -> i.getDeletionIndicator() ==0).collect(Collectors.toList());
        return  billModeList;
    }

    /**
     * get
     *
      * @param companyId
     * @param languageId
     * @param billModeId
     * @return
     */
    public ReplicaBillMode getReplicaBillMode(String companyId, String languageId, String billModeId) {
        Optional<ReplicaBillMode> dbBillMode = replicaBillModeRepository.findByCompanyIdAndLanguageIdAndBillModeIdAndDeletionIndicator
                (companyId, languageId, billModeId, 0l);
        if (dbBillMode.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId + " and billModeId - " + billModeId
                    + " doesn't exists";
            // Error Log
            createBillModeLog1(companyId, languageId, billModeId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbBillMode.get();
    }

    /**
     * Find
     *
     * @param findBillMode
     * @return
     * @throws ParseException
     */
    public List<ReplicaBillMode> findBillMode(FindBillMode findBillMode) throws ParseException {
        ReplicaBillModeSpecification spec = new ReplicaBillModeSpecification(findBillMode);
        List<ReplicaBillMode> results = replicaBillModeRepository.findAll(spec);
        log.info("found  BillMode --> " + results);
        return results;
    }

    //==============================================BillMode_ErrorLog==================================================

    private void createBillModeLog( String companyId, String languageId,String billModeId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(billModeId);
        errorLog.setMethod("Exception thrown in updateBillMode");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createBillModeLog1( String companyId,String languageId, String billModeId,String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(billModeId);
        errorLog.setMethod("Exception thrown in getBillMode");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createBillModeLog2(AddBillMode addBillMode, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addBillMode.getLanguageId());
        errorLog.setCompanyId(addBillMode.getCompanyId());
        errorLog.setRefDocNumber(addBillMode.getBillModeId());
        errorLog.setMethod("Exception thrown in createBillMode");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }
}









