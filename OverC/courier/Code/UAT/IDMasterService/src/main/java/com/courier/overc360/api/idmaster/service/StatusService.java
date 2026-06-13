package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.language.Language;
import com.courier.overc360.api.idmaster.primary.model.status.AddStatus;
import com.courier.overc360.api.idmaster.primary.model.status.Status;
import com.courier.overc360.api.idmaster.primary.model.status.UpdateStatus;
import com.courier.overc360.api.idmaster.primary.repository.LanguageRepository;
import com.courier.overc360.api.idmaster.primary.repository.StatusRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.status.FindReplicaStatus;
import com.courier.overc360.api.idmaster.replica.model.status.ReplicaStatus;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaLanguageRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaStatusSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StatusService {

    @Autowired
    private ReplicaLanguageRepository replicaLanguageRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private NumberRangeService numberRangeService;


    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/

    /**
     * Get Status
     *
     * @param languageId
     * @param statusId
     * @return
     */
    public Status getStatus(String languageId, String statusId) {
        Optional<Status> dbStatus = statusRepository.findByLanguageIdAndStatusIdAndDeletionIndicator(
                languageId, statusId, 0L);
        if (dbStatus.isEmpty()) {
            throw new BadRequestException("The given values : languageId - " +
                    languageId + " and statusId - " + statusId + " doesn't exists");
        }
        return dbStatus.get();
    }

    /**
     * Create Status
     *
     * @param newStatus
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Transactional
    public Status createStatus(AddStatus newStatus, String loginUserID)
            throws IllegalAccessException, InvocationTargetException {
        try {
            Optional<Status> duplicateStatus = statusRepository.findByLanguageIdAndStatusIdAndDeletionIndicator(
                    newStatus.getLanguageId(), newStatus.getStatusId(), 0L);
            Optional<Language> dbLanguage = languageRepository.findByLanguageIdAndDeletionIndicator(
                    newStatus.getLanguageId(), 0L);

            if (dbLanguage.isEmpty()) {
                throw new BadRequestException("LanguageId - " + newStatus.getLanguageId() + " doesn't exists");
            } else if (duplicateStatus.isPresent()) {
                throw new BadRequestException("Record is Getting Duplicated with the given values");
            } else {
                log.info("newStatus : " + newStatus);
                Status dbStatus = new Status();
                IKeyValuePair iKeyValuePair = replicaLanguageRepository.getDescription(newStatus.getLanguageId());
                BeanUtils.copyProperties(newStatus, dbStatus, CommonUtils.getNullPropertyNames(newStatus));
                if ((newStatus.getStatusId() != null &&
                        (newStatus.getReferenceField10() != null && newStatus.getReferenceField10().equalsIgnoreCase("true"))) ||
                        newStatus.getStatusId() == null || newStatus.getStatusId().isBlank()) {
                    String NUM_RANGE_OBJ = "STATUS";
                    String STATUS_ID = numberRangeService.getNextNumberRange(NUM_RANGE_OBJ);
                    log.info("next Value from NumberRange for STATUS_ID : " + STATUS_ID);
                    dbStatus.setStatusId(STATUS_ID);
                }
                if (iKeyValuePair != null) {
                    dbStatus.setLanguageDescription(iKeyValuePair.getLangDesc());
                }
                dbStatus.setDeletionIndicator(0L);
                dbStatus.setCreatedBy(loginUserID);
                dbStatus.setUpdatedBy(loginUserID);
                dbStatus.setCreatedOn(new Date());
                dbStatus.setUpdatedOn(new Date());
                log.info("Status Id created Time " + new Date());
                return statusRepository.save(dbStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update Status
     *
     * @param languageId
     * @param statusId
     * @param loginUserID
     * @param updateStatus
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ParseException
     */
    @Transactional
    public Status updateStatus(String languageId, String statusId, String loginUserID, UpdateStatus updateStatus)
            throws IllegalAccessException, InvocationTargetException, ParseException {
        try {
            Status dbStatus = getStatus(languageId, statusId);
            BeanUtils.copyProperties(updateStatus, dbStatus, CommonUtils.getNullPropertyNames(updateStatus));
            dbStatus.setUpdatedBy(loginUserID);
            dbStatus.setUpdatedOn(new Date());
            return statusRepository.save(dbStatus);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete Status
     *
     * @param languageId
     * @param statusId
     * @param loginUserID
     */
    public void deleteStatus(String languageId, String statusId, String loginUserID) {
        Status dbStatus = getStatus(languageId, statusId);
        if (dbStatus != null) {
            dbStatus.setDeletionIndicator(1L);
            dbStatus.setUpdatedBy(loginUserID);
            dbStatus.setUpdatedOn(new Date());
            log.info("Delete Status Time " + new Date());
            statusRepository.save(dbStatus);
        } else {
            throw new EntityNotFoundException("Error in deleting StatusId - " + statusId);
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get All Statuses
     *
     * @return
     */
    public List<ReplicaStatus> getAllStatuses() {
        List<ReplicaStatus> statusList = replicaStatusRepository.findAll();
        statusList = statusList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return statusList;
    }

    /**
     * Get Status
     *
     * @param languageId
     * @param statusId
     * @return
     */
    public ReplicaStatus getReplicaStatus(String languageId, String statusId) {
        Optional<ReplicaStatus> dbStatus = replicaStatusRepository.findByLanguageIdAndStatusIdAndDeletionIndicator(
                languageId, statusId, 0L);
        if (dbStatus.isEmpty()) {
            throw new BadRequestException("The given values : languageId - " +
                    languageId + " and statusId - " + statusId + " doesn't exists");
        }
        return dbStatus.get();
    }

    /**
     * Find Status
     *
     * @param findReplicaStatus
     * @return
     */
    public List<ReplicaStatus> findReplicaStatus(FindReplicaStatus findReplicaStatus) {

        ReplicaStatusSpecification spec = new ReplicaStatusSpecification(findReplicaStatus);
        log.info("Status Return Find Method " + new Date());
        List<ReplicaStatus> results = replicaStatusRepository.findAll(spec);
        log.info("results: " + results);
        return results;
    }

}
