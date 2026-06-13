package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.company.Company;
import com.courier.overc360.api.idmaster.primary.model.errorlog.ErrorLog;
import com.courier.overc360.api.idmaster.primary.model.notification.AddNotification;
import com.courier.overc360.api.idmaster.primary.model.notification.Notification;
import com.courier.overc360.api.idmaster.primary.model.notification.UpdateNotification;
import com.courier.overc360.api.idmaster.primary.repository.CompanyRepository;
import com.courier.overc360.api.idmaster.primary.repository.ErrorLogRepository;
import com.courier.overc360.api.idmaster.primary.repository.NotificationRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.notification.FindNotification;
import com.courier.overc360.api.idmaster.replica.model.notification.ReplicaNotification;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaNotificationRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaNotificationSpecification;
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
public class NotificationService {

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ReplicaNotificationRepository replicaNotificationRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ErrorLogService errorLogService;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get Notification
     *
     * @param languageId
     * @param companyId
     * @param notificationId
     * @return
     */
    public Notification getNotification(String languageId, String companyId, String notificationId) {

        Optional<Notification> dbNotification = notificationRepository.findByLanguageIdAndCompanyIdAndNotificationIdAndDeletionIndicator
                (languageId, companyId, notificationId, 0L);
        if (dbNotification.isEmpty()) {
            createNotificationLog1(languageId, companyId, notificationId, "The given values : languageId - " + languageId +
                    ", companyId - " + companyId + " and notificationId - " + notificationId + "  doesn't exists");
            throw new BadRequestException("The given values - LanguageId: " + languageId + ", CompanyId: " + companyId
                    + " and NotificationId: " + notificationId + "  doesn't exists");
        }
        return dbNotification.get();
    }

    /**
     * Create Notification
     *
     * @param addNotification
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    public Notification createNotification(AddNotification addNotification, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Optional<Company> dbCompany = companyRepository.findByCompanyIdAndLanguageIdAndDeletionIndicator(
                    addNotification.getCompanyId(), addNotification.getLanguageId(), 0L);

            Optional<Notification> duplicateNotification = notificationRepository.findByLanguageIdAndCompanyIdAndNotificationIdAndDeletionIndicator
                    (addNotification.getLanguageId(), addNotification.getCompanyId(), addNotification.getNotificationId(), 0L);

            if (dbCompany.isEmpty()) {
                throw new BadRequestException("CompanyId - " + addNotification.getCompanyId() + " and LanguageId - "
                        + addNotification.getLanguageId() + " doesn't exists");
            } else if (duplicateNotification.isPresent()) {
                throw new BadRequestException("Record is getting duplicated with given values : notificationId - " + addNotification.getNotificationId());
            } else {
                log.info("new Notification --> " + addNotification);

                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(addNotification.getLanguageId(), addNotification.getCompanyId());

                Notification newNotification = new Notification();
                BeanUtils.copyProperties(addNotification, newNotification, CommonUtils.getNullPropertyNames(addNotification));
                if ((addNotification.getNotificationId() != null &&
                        (addNotification.getReferenceField10() != null && addNotification.getReferenceField10().equalsIgnoreCase("true"))) ||
                        addNotification.getNotificationId() == null || addNotification.getNotificationId().isBlank()) {
                    String NUM_RAN_OBJ = "NOTIFICATION";
                    String NOTIFICATION_ID = numberRangeService.getNextNumberRange(NUM_RAN_OBJ);
                    log.info("next Value from NumberRange for NOTIFICATION_ID : " + NOTIFICATION_ID);
                    newNotification.setNotificationId(NOTIFICATION_ID);
                }
                if (iKeyValuePair != null) {
                    newNotification.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newNotification.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                if (addNotification.getServiceTypeId() != null) {
                    String serviceTypDesc = replicaNotificationRepository.getServiceTypeDesc(addNotification.getServiceTypeId(),
                            addNotification.getLanguageId(), addNotification.getCompanyId());
                    if (serviceTypDesc != null) {
                        newNotification.setServiceTypeText(serviceTypDesc);
                    }
                }
                if (addNotification.getSubProductId() != null && addNotification.getProductId() != null) {
                    String subProductDesc = replicaNotificationRepository.getSubProductDesc(addNotification.getSubProductId(),
                            addNotification.getLanguageId(), addNotification.getCompanyId());
                    if (subProductDesc != null) {
                        newNotification.setSubProductName(subProductDesc);
                    }
                    String productDesc = replicaNotificationRepository.getProductDesc(addNotification.getProductId(),
                            addNotification.getLanguageId(), addNotification.getCompanyId(), addNotification.getSubProductId());
                    if (productDesc != null) {
                        newNotification.setProductName(productDesc);
                    }
                }
                String statusDesc = replicaStatusRepository.getStatusDescription(addNotification.getStatusId());
                if (statusDesc != null) {
                    newNotification.setStatusDescription(statusDesc);
                }
                newNotification.setDeletionIndicator(0L);
                newNotification.setCreatedBy(loginUserID);
                newNotification.setCreatedOn(new Date());
                newNotification.setUpdatedBy(loginUserID);
                newNotification.setUpdatedOn(new Date());
                return notificationRepository.save(newNotification);
            }
        } catch (Exception e) {
            // Error Log
            createNotificationLog2(addNotification, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update Notification
     *
     * @param languageId
     * @param companyId
     * @param notificationId
     * @param updateNotification
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public Notification updateNotification(String languageId, String companyId, String notificationId,
                                           UpdateNotification updateNotification, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            Notification dbNotification = getNotification(languageId, companyId, notificationId);
            BeanUtils.copyProperties(updateNotification, dbNotification, CommonUtils.getNullPropertyNames(updateNotification));

            if (updateNotification.getServiceTypeId() != null) {
                String serviceTypDesc = replicaNotificationRepository.getServiceTypeDesc(updateNotification.getServiceTypeId(), languageId, companyId);
                if (serviceTypDesc != null) {
                    dbNotification.setServiceTypeText(serviceTypDesc);
                }
            }
            if (updateNotification.getSubProductId() != null && updateNotification.getProductId() != null) {
                String subProductDesc = replicaNotificationRepository.getSubProductDesc(updateNotification.getSubProductId(), languageId, companyId);
                if (subProductDesc != null) {
                    dbNotification.setSubProductName(subProductDesc);
                }
                String productDesc = replicaNotificationRepository.getProductDesc(updateNotification.getProductId(),
                        languageId, companyId, updateNotification.getSubProductId());
                if (productDesc != null) {
                    dbNotification.setProductName(productDesc);
                }
            }
            if (updateNotification.getStatusId() != null && !updateNotification.getStatusId().isEmpty()) {
                String statusDesc = replicaStatusRepository.getStatusDescription(updateNotification.getStatusId());
                if (statusDesc != null) {
                    dbNotification.setStatusDescription(statusDesc);
                }
            }
            dbNotification.setUpdatedBy(loginUserID);
            dbNotification.setUpdatedOn(new Date());
            return notificationRepository.save(dbNotification);
        } catch (Exception e) {
            createNotificationLog(languageId, companyId, notificationId, e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete Notification
     *
     * @param languageId
     * @param companyId
     * @param notificationId
     * @param loginUserID
     */
    public void deleteNotification(String languageId, String companyId, String notificationId, String loginUserID) {
        Notification dbNotification = getNotification(languageId, companyId, notificationId);
        if (dbNotification != null) {
            dbNotification.setDeletionIndicator(1L);
            dbNotification.setUpdatedBy(loginUserID);
            dbNotification.setUpdatedOn(new Date());
            notificationRepository.save(dbNotification);
        } else {
            createNotificationLog1(languageId, companyId, notificationId, "Error in deleting NotificationId - " + notificationId);
            throw new BadRequestException("Error in deleting NotificationId - " + notificationId);
        }
    }

    /*=================================================REPLICA=======================================================*/

    /**
     * Get all Notification Details
     *
     * @return
     */
    public List<ReplicaNotification> getAllNotification() {
        List<ReplicaNotification> notificationList = replicaNotificationRepository.findAll();
        notificationList = notificationList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return notificationList;
    }

    /**
     * Get Notification
     *
     * @param languageId
     * @param companyId
     * @param notificationId
     * @return
     */
    public ReplicaNotification getReplicaNotification(String languageId, String companyId, String notificationId) {

        Optional<ReplicaNotification> dbNotification = replicaNotificationRepository.findByLanguageIdAndCompanyIdAndNotificationIdAndDeletionIndicator(
                languageId, companyId, notificationId, 0L);

        if (dbNotification.isEmpty()) {
            createNotificationLog1(languageId, companyId, notificationId, "The given values : languageId - " + languageId +
                    ", companyId - " + companyId + " and notificationId - " + notificationId + "  doesn't exists");
            throw new BadRequestException("The given values - LanguageId: " + languageId + ", CompanyId: " + companyId + " and NotificationId: "
                    + notificationId + "  doesn't exists");
        }
        return dbNotification.get();
    }

    /**
     * Find Notifications
     *
     * @param findNotification
     * @return
     */
    public List<ReplicaNotification> findNotification(FindNotification findNotification) {

        ReplicaNotificationSpecification spec = new ReplicaNotificationSpecification(findNotification);
        List<ReplicaNotification> results = replicaNotificationRepository.findAll(spec);
//        log.info("found Notifications --> {}", results);
        return results;
    }

    //========================================Notification_ErrorLog=================================================
    private void createNotificationLog(String languageId, String companyId, String notificationId, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(notificationId);
        errorLog.setMethod("Exception thrown in updateNotification");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }

    private void createNotificationLog1(String languageId, String companyId, String notificationId, String error) {

        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(languageId);
        errorLog.setCompanyId(companyId);
        errorLog.setRefDocNumber(notificationId);
        errorLog.setMethod("Exception thrown in getNotification");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
    }

    private void createNotificationLog2(AddNotification addNotification, String error) throws IOException, CsvException {

        List<ErrorLog> errorLogList = new ArrayList<>();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLogDate(new Date());
        errorLog.setLanguageId(addNotification.getLanguageId());
        errorLog.setCompanyId(addNotification.getCompanyId());
        errorLog.setRefDocNumber(addNotification.getNotificationId());
        errorLog.setMethod("Exception thrown in createNotification");
        errorLog.setErrorMessage(error);
        errorLog.setCreatedBy("Admin");
        errorLogRepository.save(errorLog);
        errorLogList.add(errorLog);
        errorLogService.writeLog(errorLogList);
    }
}









