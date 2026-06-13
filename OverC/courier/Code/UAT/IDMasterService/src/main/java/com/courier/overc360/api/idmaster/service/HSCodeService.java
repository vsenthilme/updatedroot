package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.customer.AddCustomer;
import com.courier.overc360.api.idmaster.primary.model.customer.Customer;
import com.courier.overc360.api.idmaster.primary.model.customer.CustomerDeleteInput;
import com.courier.overc360.api.idmaster.primary.model.customer.UpdateCustomer;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.hsCode.AddHSCode;
import com.courier.overc360.api.idmaster.primary.model.hsCode.HSCode;
import com.courier.overc360.api.idmaster.primary.model.hsCode.HsCodeDeleteInput;
import com.courier.overc360.api.idmaster.primary.model.hsCode.UpdateHSCode;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.HSCodeRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.hsCode.FindHSCode;
import com.courier.overc360.api.idmaster.replica.model.hsCode.ReplicaHSCode;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaHSCodeRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaHSCodeSpecification;
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

@Slf4j
@Service
public class HSCodeService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private HSCodeRepository hsCodeRepository;

    @Autowired
    private ReplicaHSCodeRepository replicaHSCodeRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    @Autowired
    private ErrorLogService errorLogService;


    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get HSCode
     *
     * @param languageId
     * @param companyId
     * @param hsCode
     * @param specialApprovalId
     * @return
     */
    public HSCode getHsCode(String languageId, String companyId, String hsCode, String specialApprovalId) {

        Optional<HSCode> dbHsCode = hsCodeRepository.findByLanguageIdAndCompanyIdAndHsCodeAndSpecialApprovalIdAndDeletionIndicator(
                languageId, companyId, hsCode, specialApprovalId, 0L);
        if (dbHsCode.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId +
                    ", companyId - " + companyId + " specialApprovalId - " + specialApprovalId + " and HSCode - " + hsCode + " and doesn't exists";
            // Error Log
            createHSCodeLog1(languageId, companyId, hsCode, specialApprovalId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbHsCode.get();
    }

    /**
     * Create HSCode
     *
     * @param addHSCode
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public HSCode createHsCode(AddHSCode addHSCode, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbCompanyPresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addHSCode.getCompanyId(), addHSCode.getLanguageId(), 0L);
            if (!dbCompanyPresent) {
                throw new BadRequestException("CompanyId - " + addHSCode.getCompanyId() + " and languageId - " + addHSCode.getLanguageId() + " doesn't exists");
            }

            boolean duplicateHsCodePresent = replicaHSCodeRepository.existsByLanguageIdAndCompanyIdAndHsCodeAndSpecialApprovalIdAndDeletionIndicator(
                    addHSCode.getLanguageId(), addHSCode.getCompanyId(), addHSCode.getHsCode(), addHSCode.getSpecialApprovalId(), 0L);
            if (duplicateHsCodePresent) {
                throw new BadRequestException("Record is getting Duplicated with the given values : hsCode - " + addHSCode.getHsCode());
            }
            log.info("new HSCode --> {}", addHSCode);
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addHSCode.getLanguageId(), addHSCode.getCompanyId());
            HSCode newHsCode = new HSCode();
            BeanUtils.copyProperties(addHSCode, newHsCode, CommonUtils.getNullPropertyNames(addHSCode));
            if ((addHSCode.getHsCode() != null &&
                    (addHSCode.getReferenceField10() != null && addHSCode.getReferenceField10().equalsIgnoreCase("true"))) ||
                    addHSCode.getHsCode() == null || addHSCode.getHsCode().isBlank()) {
                String NUM_RAN_OBJ = "HSCODE";
                String HS_CODE = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                log.info("next Value from NumberRange for HS_CODE : " + HS_CODE);
                newHsCode.setHsCode(HS_CODE);
            }
            if (iKeyValuePair != null) {
                newHsCode.setLanguageDescription(iKeyValuePair.getLangDesc());
                newHsCode.setCompanyName(iKeyValuePair.getCompanyDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addHSCode.getStatusId());
            if (statusDesc != null) {
                newHsCode.setStatusDescription(statusDesc);
            }
            if (addHSCode.getSpecialApprovalId() != null) {
                String specialApprovalDesc = replicaHSCodeRepository.getSpecialApprovalDesc(addHSCode.getSpecialApprovalId(),
                        addHSCode.getLanguageId(), addHSCode.getCompanyId());
                if (specialApprovalDesc != null) {
                    newHsCode.setSpecialApprovalText(specialApprovalDesc);
                }
            }
            newHsCode.setDeletionIndicator(0L);
            newHsCode.setCreatedBy(loginUserID);
            newHsCode.setCreatedOn(new Date());
            newHsCode.setUpdatedBy(loginUserID);
            newHsCode.setUpdatedOn(new Date());
            return hsCodeRepository.save(newHsCode);
        } catch (Exception e) {
            // Error Log
            createHSCodeLog2(addHSCode, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update HSCode
     *
     * @param languageId
     * @param companyId
     * @param hsCode
     * @param specialApprovalId
     * @param updateHSCode
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public HSCode updateHsCode(String languageId, String companyId, String hsCode, String specialApprovalId, UpdateHSCode updateHSCode, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            HSCode dbHSCode = getHsCode(languageId, companyId, hsCode, specialApprovalId);
            BeanUtils.copyProperties(updateHSCode, dbHSCode, CommonUtils.getNullPropertyNames(updateHSCode));
            if (updateHSCode.getStatusId() != null && !updateHSCode.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateHSCode.getStatusId());
                if (statusDesc != null) {
                    dbHSCode.setStatusDescription(statusDesc);
                }
            }
            dbHSCode.setUpdatedBy(loginUserID);
            dbHSCode.setUpdatedOn(new Date());
            return hsCodeRepository.save(dbHSCode);
        } catch (Exception e) {
            // Error Log
            createHSCodeLog(languageId, companyId, hsCode, specialApprovalId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete HSCode
     *
     * @param languageId
     * @param companyId
     * @param hsCode
     * @param specialApprovalId
     * @param loginUserID
     */
    public void deleteHsCode(String languageId, String companyId, String hsCode, String specialApprovalId, String loginUserID) {

        HSCode dbHSCode = getHsCode(languageId, companyId, hsCode, specialApprovalId);
        if (dbHSCode != null) {
            dbHSCode.setDeletionIndicator(1L);
            dbHSCode.setUpdatedBy(loginUserID);
            dbHSCode.setUpdatedOn(new Date());
            hsCodeRepository.save(dbHSCode);
        } else {
            // Error Log
            createHSCodeLog1(languageId, companyId, specialApprovalId, hsCode, "Error in deleting HSCode - " + hsCode);
            throw new BadRequestException("Error in deleting HSCode - " + hsCode);
        }
    }

    //===============================================Replica==================================================

    /**
     * /**
     * Get All HSCode Details
     *
     * @return
     */
    public List<ReplicaHSCode> getAllHsCodes() {
        List<ReplicaHSCode> hsCodeList = replicaHSCodeRepository.findAll();
        hsCodeList = hsCodeList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return hsCodeList;
    }

    /**
     * Get HSCode
     *
     * @param languageId
     * @param companyId
     * @param hsCode
     * @return
     */
    public ReplicaHSCode replicaGetHsCode(String languageId, String companyId, String hsCode, String specialApprovalId) {

        Optional<ReplicaHSCode> dbHsCode = replicaHSCodeRepository.findByLanguageIdAndCompanyIdAndHsCodeAndSpecialApprovalIdAndDeletionIndicator(
                languageId, companyId, hsCode, specialApprovalId, 0L);
        if (dbHsCode.isEmpty()) {
            String errMsg = "The given values : languageId - " + languageId
                    + ", companyId - " + companyId + " specialApprovalId - " + specialApprovalId + " and HSCode - " + hsCode + " and doesn't exists";
            // Error Log
            createHSCodeLog1(languageId, companyId, hsCode, specialApprovalId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbHsCode.get();
    }

    /**
     * Find HSCodes
     *
     * @param findHSCode
     * @return
     * @throws ParseException
     */
    public List<ReplicaHSCode> findHsCodes(FindHSCode findHSCode) throws ParseException {

        ReplicaHSCodeSpecification spec = new ReplicaHSCodeSpecification(findHSCode);
        List<ReplicaHSCode> results = replicaHSCodeRepository.findAll(spec);
        log.info("found HSCodes --> " + results);
        return results;
    }

    //=============================================HSCode_ErrorLog=====================================================
    private void createHSCodeLog(String languageId, String companyId, String hsCode, String specialApprovalId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(hsCode);
        errorLog.setReferenceField1(specialApprovalId);
        errorLog.setMethod("Exception thrown in updateHSCode");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createHSCodeLog1(String languageId, String companyId, String hsCode, String specialApprovalId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(hsCode);
        errorLog.setReferenceField1(specialApprovalId);
        errorLog.setMethod("Exception thrown in getHSCode");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createHSCodeLog2(AddHSCode addHSCode, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addHSCode.getLanguageId());
        errorLog.setCompanyId(addHSCode.getCompanyId());
        errorLog.setRefDocNumber(addHSCode.getHsCode());
        errorLog.setReferenceField1(addHSCode.getSpecialApprovalId());
        errorLog.setMethod("Exception thrown in createHSCode");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }


    /**
     * @param addHSCodes
     * @param loginUserID
     * @return
     */
    public List<HSCode> createHSCodeBulk(List<AddHSCode> addHSCodes, String loginUserID) {
        try {
            List<HSCode> createdHsCodeList = new ArrayList<>();

            for (AddHSCode addHSCode : addHSCodes) {
                boolean dbCompanyPresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                        addHSCode.getCompanyId(), addHSCode.getLanguageId(), 0L);
                if (!dbCompanyPresent) {
                    throw new BadRequestException("CompanyId - " + addHSCode.getCompanyId() + " and languageId - " + addHSCode.getLanguageId() + " doesn't exists");
                }

                boolean duplicateHsCodePresent = replicaHSCodeRepository.existsByLanguageIdAndCompanyIdAndHsCodeAndSpecialApprovalIdAndDeletionIndicator(
                        addHSCode.getLanguageId(), addHSCode.getCompanyId(), addHSCode.getHsCode(), addHSCode.getSpecialApprovalId(), 0L);
                if (duplicateHsCodePresent) {
                    throw new BadRequestException("Record is getting Duplicated with the given values : hsCode - " + addHSCode.getHsCode());
                }
                log.info("new HSCode --> {}", addHSCode);

                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addHSCode.getLanguageId(), addHSCode.getCompanyId());
                HSCode newHsCode = new HSCode();
                BeanUtils.copyProperties(addHSCode, newHsCode, CommonUtils.getNullPropertyNames(addHSCode));
                if ((addHSCode.getHsCode() != null &&
                        (addHSCode.getReferenceField10() != null && addHSCode.getReferenceField10().equalsIgnoreCase("true"))) ||
                        addHSCode.getHsCode() == null || addHSCode.getHsCode().isBlank()) {
                    String NUM_RAN_OBJ = "HSCODE";
                    String HS_CODE = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                    log.info("next Value from NumberRange for HS_CODE : " + HS_CODE);
                    newHsCode.setHsCode(HS_CODE);
                }
                if (iKeyValuePair != null) {
                    newHsCode.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newHsCode.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addHSCode.getStatusId());
                if (statusDesc != null) {
                    newHsCode.setStatusDescription(statusDesc);
                }
                if (addHSCode.getSpecialApprovalId() != null) {
                    String specialApprovalDesc = replicaHSCodeRepository.getSpecialApprovalDesc(addHSCode.getSpecialApprovalId(),
                            addHSCode.getLanguageId(), addHSCode.getCompanyId());
                    if (specialApprovalDesc != null) {
                        newHsCode.setSpecialApprovalText(specialApprovalDesc);
                    }
                }
                newHsCode.setDeletionIndicator(0L);
                newHsCode.setCreatedBy(loginUserID);
                newHsCode.setCreatedOn(new Date());
                newHsCode.setUpdatedBy(loginUserID);
                newHsCode.setUpdatedOn(new Date());
                HSCode hsCode = hsCodeRepository.save(newHsCode);
                createdHsCodeList.add(hsCode);
            }
            return createdHsCodeList;
        } catch (Exception e) {
            // Error Log
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * @param updateHsCode
     * @param loginUserID
     * @return
     */
    public List<HSCode> updateHsCodeBulk(List<UpdateHSCode> updateHsCode, String loginUserID,String hsCode) {
        List<HSCode> updatedHsCodeList = new ArrayList<>();
        try {
            for (UpdateHSCode updatedHSCode : updateHsCode) {
            Optional<HSCode> hsCodeData = hsCodeRepository.findByLanguageIdAndCompanyIdAndHsCodeAndSpecialApprovalIdAndDeletionIndicator(
                    updatedHSCode.getLanguageId(), updatedHSCode.getCompanyId(), hsCode, updatedHSCode.getSpecialApprovalId(), 0L);
//                HSCode dbHSCode = getHsCode1(updateHSCode.getLanguageId(), updateHSCode.getCompanyId(), hsCode, updateHSCode.getSpecialApprovalId());
                if (hsCodeData.isPresent()){
                    HSCode hsCode1 = hsCodeData.get();
                    BeanUtils.copyProperties(updatedHSCode, hsCode1, CommonUtils.getNullPropertyNames(updatedHSCode));
                if (updatedHSCode.getStatusId() != null && !updatedHSCode.getStatusId().isEmpty()) {
                    String statusDesc = replicaStatusRepository.getStatusDescription(updatedHSCode.getStatusId());
                    if (statusDesc != null) {
                        hsCode1.setStatusDescription(statusDesc);
                    }
                }
                hsCode1.setUpdatedBy(loginUserID);
                hsCode1.setUpdatedOn(new Date());
                HSCode hsCode2 = hsCodeRepository.save(hsCode1);
                log.info("updated hscode --> {}", hsCode2);
                updatedHsCodeList.add(hsCode2);
            } else {
                HSCode newHsCode = new HSCode();
                    BeanUtils.copyProperties(updatedHSCode, newHsCode, CommonUtils.getNullPropertyNames(updatedHSCode));
                    if (updatedHSCode.getStatusId() != null && !updatedHSCode.getStatusId().isEmpty()) {
                        String statusDesc = replicaStatusRepository.getStatusDescription("1");
                        if (statusDesc != null) {
                            newHsCode.setStatusDescription(statusDesc);
                        }
                    }
                    newHsCode.setCreatedBy(loginUserID);
                    newHsCode.setCreatedOn(new Date());
                    HSCode created = hsCodeRepository.save(newHsCode);
                    updatedHsCodeList.add(created);
                }
            }

        } catch (Exception e) {
            // Error Log
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return updatedHsCodeList;
    }




    /**
     *
     * @param hsCodeDeleteInputList
     * @param loginUserID
     */
    public void deleteHsCodeBulk(List<HsCodeDeleteInput> hsCodeDeleteInputList, String loginUserID,String hsCode) {

        if (hsCodeDeleteInputList != null && !hsCodeDeleteInputList.isEmpty()) {
            for (HsCodeDeleteInput deleteInput : hsCodeDeleteInputList) {
                HSCode dbHSCode = getHsCode(deleteInput.getLanguageId(), deleteInput.getCompanyId(), hsCode, deleteInput.getSpecialApprovalId());
                if (dbHSCode != null) {
                    dbHSCode.setDeletionIndicator(1L);
                    dbHSCode.setUpdatedBy(loginUserID);
                    dbHSCode.setUpdatedOn(new Date());
                    hsCodeRepository.save(dbHSCode);
                } else {
                    // Error Log
                    createHSCodeLog1(deleteInput.getLanguageId(), deleteInput.getCompanyId(), deleteInput.getSpecialApprovalId(), deleteInput.getHsCode(), "Error in deleting HSCode - " + deleteInput.getHsCode());
                    throw new BadRequestException("Error in deleting HSCode - " + deleteInput.getHsCode());
                }
            }

        }
    }
}
