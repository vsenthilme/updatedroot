package com.courier.overc360.api.midmile.service;


import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.npr.Npr;
import com.courier.overc360.api.midmile.primary.model.pickup.PickupEntity;
import com.courier.overc360.api.midmile.primary.model.rescheduledelivery.RescheduleDelivery;
import com.courier.overc360.api.midmile.primary.model.reschedulepickup.ReSchedulePickUp;
import com.courier.overc360.api.midmile.primary.repository.ReschedulePickupRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.primary.util.DateUtils;
import com.courier.overc360.api.midmile.replica.model.npr.ReplicaNpr;
import com.courier.overc360.api.midmile.replica.model.reschedulepickup.FindReschedulePickup;
import com.courier.overc360.api.midmile.replica.model.reschedulepickup.ReplicaReSchedulePickUp;
import com.courier.overc360.api.midmile.replica.repository.ReplicaReschedulePickupRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaNprSpecification;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaReschedulePickupSpecification;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.node.LongNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReschedulePickupService {

    @Autowired
    private ReschedulePickupRepository reschedulePickupRepository;

    @Autowired
    private ReplicaReschedulePickupRepository replicaReschedulePickupRepository;


    /**
     *
     * @param reSchedulePickUps
     * @param loginUserID
     * @return
     */
    public List<ReSchedulePickUp> createReschedulePickup(List<ReSchedulePickUp> reSchedulePickUps, String loginUserID) {
        List<ReSchedulePickUp> reSchedulePickUpList = new ArrayList<>();
        try {
            reSchedulePickUps.stream().forEach(reSchedulePickUp -> {

                boolean duplicate = reschedulePickupRepository.existsByLanguageIdAndCompanyIdAndPickupIdAndDeletionIndicator(
                        reSchedulePickUp.getLanguageId(), reSchedulePickUp.getCompanyId(), reSchedulePickUp.getPickupId(), 0L);
                if (duplicate) {
                    throw new BadRequestException("Record is getting Duplicated with the given values");
                }

                // Create new reschedule pickup and related entities
                ReSchedulePickUp newReschedulePickup = new ReSchedulePickUp();
                BeanUtils.copyProperties(reSchedulePickUp, newReschedulePickup, CommonUtils.getNullPropertyNames(reSchedulePickUp));

                newReschedulePickup.setCreatedBy(loginUserID);
                newReschedulePickup.setCreatedOn(new Date());
                newReschedulePickup.setUpdatedBy(loginUserID);
                newReschedulePickup.setUpdatedOn(new Date());
                ReSchedulePickUp reSchedulePickUp1 = reschedulePickupRepository.save(newReschedulePickup);
                reSchedulePickUpList.add(reSchedulePickUp1);
                log.info("size --{}", reSchedulePickUpList.size());
            });
        }  catch (Exception e) {
            throw new BadRequestException("Reschedule Create Error " + e);
        }
        return reSchedulePickUpList;
    }


    /**
     *
     * @param updateRescheduleList
     * @param loginUserID
     * @return
     */
    public List<ReSchedulePickUp> updateRescheduleList(List<ReSchedulePickUp> updateRescheduleList, String loginUserID) {

        List<ReSchedulePickUp> reSchedulePickUpList = new ArrayList<>();

        for (ReSchedulePickUp updateReschedule : updateRescheduleList) {
            Optional<ReSchedulePickUp> dbReschedulePickup = reschedulePickupRepository.findByLanguageIdAndCompanyIdAndPickupIdAndRescheduleNoAndDeletionIndicator(
                    updateReschedule.getLanguageId(), updateReschedule.getCompanyId(), updateReschedule.getPickupId(),updateReschedule.getRescheduleNo(), 0L);

            if (dbReschedulePickup.isPresent()) {
                ReSchedulePickUp newReschedule = dbReschedulePickup.get();
                BeanUtils.copyProperties(updateReschedule, newReschedule, CommonUtils.getNullPropertyNames(updateReschedule));
                newReschedule.setUpdatedOn(new Date());
                newReschedule.setUpdatedBy(loginUserID);
                ReSchedulePickUp reSchedulePickUp = reschedulePickupRepository.save(newReschedule);
                reSchedulePickUpList.add(reSchedulePickUp);
            }
        }
        return reSchedulePickUpList;

    }


    /**
     * 
     * @param deleteRescheduleList
     * @param loginUserID
     */
    public void deleteReschedulePickupList(List<ReSchedulePickUp> deleteRescheduleList, String loginUserID) {
        if (deleteRescheduleList != null && !deleteRescheduleList.isEmpty()) {
            log.info("given values to delete Reschedule pickup --->  {}", deleteRescheduleList);

            deleteRescheduleList.parallelStream().forEach(deleteInput -> {
                ReSchedulePickUp dbReschedule = getReschedulePickup(deleteInput.getLanguageId(), deleteInput.getCompanyId(), deleteInput.getPickupId(),deleteInput.getRescheduleNo());
                dbReschedule.setDeletionIndicator(1L);
                dbReschedule.setUpdatedBy(loginUserID);
                dbReschedule.setUpdatedOn(new Date());
                reschedulePickupRepository.save(dbReschedule);
            });
        }
    }

    /**
     * 
     * @param languageId
     * @param companyId
     * @param pickupId
     * @return
     */
    private ReSchedulePickUp getReschedulePickup(String languageId, String companyId, String pickupId, Long rescheduleNo) {
        Optional<ReSchedulePickUp> dbReschedulePickup = reschedulePickupRepository.findByLanguageIdAndCompanyIdAndPickupIdAndRescheduleNoAndDeletionIndicator(
                languageId, companyId, pickupId, rescheduleNo,0L);
        if (dbReschedulePickup.isEmpty()) {
            throw new BadRequestException("Reschedule Pickup with the given values:  languageaId: " + languageId + ", companyId: " + companyId + ", pickupId: " + pickupId +  " doesn't exists");
        }
        return dbReschedulePickup.get();

    }

    /**
     *
     * @return
     */
    public List<ReplicaReSchedulePickUp> getAllReschedulePickup() {
        List<ReplicaReSchedulePickUp> reSchedulePickUpList = replicaReschedulePickupRepository.findAll();

        reSchedulePickUpList = reSchedulePickUpList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return reSchedulePickUpList;
    }

    /**
     *
     * @param languageId
     * @param companyId
     * @param pickupId
     * @return
     */
    public ReplicaReSchedulePickUp getReplicaReschedule(String languageId, String companyId, String pickupId) {
        Optional<ReplicaReSchedulePickUp> dbReschedulePickup = replicaReschedulePickupRepository.findByLanguageIdAndCompanyIdAndPickupIdAndDeletionIndicator(
                languageId, companyId, pickupId, 0L);

        if (dbReschedulePickup.isEmpty()) {
            throw new BadRequestException("Reschedule pickup with given values : languageId: " + languageId + ", companyId: " + companyId + ", pickupId: " + pickupId +  " doesn't exists");
        }
        return dbReschedulePickup.get();

    }

    /**
     *
     * @param findReschedulePickup
     * @return
     */
    public List<ReplicaReSchedulePickUp> findReschedulePickup(FindReschedulePickup findReschedulePickup) {
        log.info("findReschedulePickup -------->>" + findReschedulePickup);
        ReplicaReschedulePickupSpecification spec = new ReplicaReschedulePickupSpecification(findReschedulePickup);
        List<ReplicaReSchedulePickUp> results = replicaReschedulePickupRepository.findAll(spec);
        log.info("Found Reschedule pickup ----> " + results);
        return results;
    }

    /**
     * Adding Reschedule Pickup log
     * @param pickup1
     * @return
     */

    public ReSchedulePickUp addReschedulePickup(PickupEntity pickup1) {
        try {
            // Create new Reschedule pickup
            ReSchedulePickUp newReschedulePickup = new ReSchedulePickUp();
            BeanUtils.copyProperties(pickup1, newReschedulePickup, CommonUtils.getNullPropertyNames(pickup1));

            log.info("Pickup_Update_Rescheduled_Input " + pickup1);

            if (pickup1.getReschedule() != null) {
                // Set the reschedule date
                Date rescheduleDate = DateUtils.extractDateOnly(pickup1.getReschedule());
                newReschedulePickup.setRescheduleDate(rescheduleDate);

                LocalTime rescheduleStartTime = DateUtils.extractTimeOnly(pickup1.getReschedule());
                newReschedulePickup.setRescheduleStartTime(rescheduleStartTime);
                newReschedulePickup.setRescheduleEndTime(rescheduleStartTime);

            }

            newReschedulePickup.setReasonId(pickup1.getNprId());
            newReschedulePickup.setReasonDescription(pickup1.getRescheduleReason());
            newReschedulePickup.setCreatedOn(new Date());
            newReschedulePickup.setUpdatedOn(new Date());

            return reschedulePickupRepository.save(newReschedulePickup);
        } catch (Exception e) {
            throw new BadRequestException("Reschedule Create Error " + e);
        }
    }



}
