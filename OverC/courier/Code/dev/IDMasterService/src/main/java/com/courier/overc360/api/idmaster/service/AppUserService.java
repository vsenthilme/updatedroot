package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.appuser.AddAppUser;
import com.courier.overc360.api.idmaster.primary.model.appuser.AppUser;
import com.courier.overc360.api.idmaster.primary.model.appuser.UpdateAppUser;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.user.UserManagement;
import com.courier.overc360.api.idmaster.primary.repository.AppUserRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.primary.util.PasswordEncoder;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.appuser.FindAppUser;
import com.courier.overc360.api.idmaster.replica.model.appuser.ReplicaAppUser;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaAppUserRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaAppUserSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ReplicaAppUserRepository replicaAppUserRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    private PasswordEncoder passwordEncoder = new PasswordEncoder();
    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get AppUser
     *
     * @param companyId
     * @param languageId
     * @param appUserId
     * @return
     */
    public AppUser getAppUser(String companyId, String languageId, String appUserId) {
        Optional<AppUser> dbAppUser = appUserRepository.findByCompanyIdAndLanguageIdAndAppUserIdAndDeletionIndicator(
                companyId, languageId, appUserId, 0l);
        if (dbAppUser.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId +
                    " and appUserId - " + appUserId + "doesn't exists";
            //Error Lof
            createAppUserLog1(languageId, companyId, appUserId, errMsg);
        }
        return dbAppUser.get();
    }

    /**
     * @param addAppUser
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public AppUser createAppUser(AddAppUser addAppUser, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            boolean dbCompanyPresent = replicaCompanyRepository.existsByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addAppUser.getCompanyId(), addAppUser.getLanguageId(), 0l);
            if (!dbCompanyPresent) {
                throw new BadRequestException("CompanyId - " + addAppUser.getAppUserId() + " LanguageId - " + addAppUser.getAppUserId() + " doesn't exists ");
            }
            boolean duplicateAppUser = replicaAppUserRepository.existsByCompanyIdAndLanguageIdAndAppUserIdAndDeletionIndicator(
                    addAppUser.getCompanyId(), addAppUser.getLanguageId(), addAppUser.getAppUserId(), 0L);
            if (duplicateAppUser) {
                throw new BadRequestException("Record is getting Duplicated with the given values : AppUser - " + addAppUser.getAppUserId());
            }

            log.info("new AppUser --> {}", addAppUser);
            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addAppUser.getLanguageId(), addAppUser.getCompanyId());
            AppUser newAppUser = new AppUser();
            BeanUtils.copyProperties(addAppUser, newAppUser, CommonUtils.getNullPropertyNames(addAppUser));
            if (iKeyValuePair != null) {
                newAppUser.setLanguageDescription(iKeyValuePair.getLangDesc());
                newAppUser.setCompanyName(iKeyValuePair.getCompanyDesc());
            }
            String statusDesc = replicaStatusRepository.getStatusDescription(addAppUser.getStatusId());
            if (statusDesc != null) {
                newAppUser.setStatusDescription(statusDesc);
            }

            //Save without spacing
            newAppUser.setAppUserId(newAppUser.getAppUserId().replaceAll("\\s+",""));

            // Password encryption
            String encodedPwd = passwordEncoder.encodePassword(newAppUser.getPassword());
            newAppUser.setPassword(encodedPwd);
            newAppUser.setAppUserId(newAppUser.getAppUserId().toUpperCase());
            newAppUser.setDeletionIndicator(0L);
            newAppUser.setCreatedBy(loginUserID);
            newAppUser.setCreatedOn(new Date());
            newAppUser.setUpdatedBy(loginUserID);
            newAppUser.setUpdatedOn(new Date());
            return appUserRepository.save(newAppUser);

        } catch (Exception e) {
            // Error Log
            createAppUserLog2(addAppUser, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update AppUser
     *
     * @param companyId
     * @param languageId
     * @param appUserId
     * @param loginUserID
     * @param updateAppUser
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public AppUser updateAppUser(String companyId, String languageId, String appUserId, String loginUserID,
                                 UpdateAppUser updateAppUser)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            AppUser dbAppUser = getAppUser(companyId, languageId, appUserId);
            BeanUtils.copyProperties(updateAppUser, dbAppUser, CommonUtils.getNullPropertyNames(updateAppUser));
            if (updateAppUser.getStatusId() != null && !updateAppUser.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateAppUser.getStatusId());
                if (statusDesc != null) {
                    dbAppUser.setStatusDescription(statusDesc);
                }
            }
            dbAppUser.setUpdatedBy(loginUserID);
            dbAppUser.setUpdatedOn(new Date());
            return appUserRepository.save(dbAppUser);
        } catch (Exception e) {
            // Error Log
            createAppUserLog(languageId, companyId, appUserId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete AppUser
     *
     * @param languageId
     * @param companyId
     * @param appUserId
     * @param loginUserID
     */
    public void deleteAppUser(String languageId, String companyId, String appUserId, String loginUserID) {
        AppUser dbAppUser = getAppUser(languageId, companyId, appUserId);
        if (dbAppUser != null) {
            dbAppUser.setDeletionIndicator(1L);
            dbAppUser.setUpdatedBy(loginUserID);
            dbAppUser.setUpdatedOn(new Date());
            appUserRepository.save(dbAppUser);
        } else {
            createAppUserLog1(languageId, companyId, appUserId, "Error in deleting AppUser - " + appUserId);
            throw new BadRequestException("Error in deleting AppUser - " + appUserId);
        }
    }

    /*=================================================REPLICA=======================================================*/

    /**
     * Get all AppUser Details
     *
     * @return
     */
    public List<ReplicaAppUser> getAllAppUsers() {
        List<ReplicaAppUser> appUserList = replicaAppUserRepository.findAll();
        appUserList = appUserList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return appUserList;
    }

    /**
     * Get AppUser
     *
     * @param languageId
     * @param companyId
     * @param appUserId
     * @return
     */
    public ReplicaAppUser getReplicaAppUser(String languageId, String companyId, String appUserId) {

        Optional<ReplicaAppUser> dbAppUser = replicaAppUserRepository.findByCompanyIdAndLanguageIdAndAppUserIdAndDeletionIndicator
                (languageId, companyId, appUserId, 0L);

        if (dbAppUser.isEmpty()) {
            String errMsg = "The given values : companyId - " + companyId + ", languageId - " + languageId +
                    " and appUserId - " + appUserId + " doesn't exists";
            // Error Log
            createAppUserLog1(languageId, companyId, appUserId, errMsg);
            throw new BadRequestException(errMsg);
        }
        return dbAppUser.get();
    }

    /**
     * Find AppUser
     *
     * @param findAppUser
     * @return
     */
    public List<ReplicaAppUser> findAppUsers(FindAppUser findAppUser) {

        ReplicaAppUserSpecification spec = new ReplicaAppUserSpecification(findAppUser);
        List<ReplicaAppUser> results = replicaAppUserRepository.findAll(spec);
        log.info("found AppUsers --> {}", results);
        return results;
    }

    //========================================AppUser_ErrorLog=================================================
    private void createAppUserLog(String languageId, String companyId, String appUserId, String error) throws
            IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(appUserId);
        errorLog.setMethod("Exception thrown in updateAppUser");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createAppUserLog1(String languageId, String companyId, String appUserId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(appUserId);
        errorLog.setMethod("Exception thrown in getAppUser");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createAppUserLog2(AddAppUser addAppUser, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addAppUser.getLanguageId());
        errorLog.setCompanyId(addAppUser.getCompanyId());
        errorLog.setRefDocNumber(addAppUser.getAppUserId());
        errorLog.setMethod("Exception thrown in createAppUser");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    /**
     * Validate User
     *
     * @param appUserId
     * @param loginPassword
     * @param version
     * @return
     */
    public AppUser validateAppUser(String appUserId, String loginPassword, String version, String appUserType) {
        List<AppUser> appUsers =
                appUserRepository.findByAppUserIdAndAppUserTypeAndDeletionIndicator(appUserId, appUserType,0L);
        if (appUsers.isEmpty()) {
            throw new BadRequestException("Invalid Username : " + appUserId + " app user type :" + appUserType);
        }

        boolean isSuccess = false;
        for (AppUser appUser : appUsers) {
            isSuccess = passwordEncoder.matches(loginPassword, appUser.getPassword());
            log.info("version : " + version);
            if(isSuccess) {
                return appUser;
            }
        }
        if (!isSuccess) {
            throw new BadRequestException("Password is wrong. Please enter correct password.");
        }
        return null;
    }

}
