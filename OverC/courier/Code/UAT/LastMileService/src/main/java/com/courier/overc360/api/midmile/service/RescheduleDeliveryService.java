package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.delivery.Delivery;
import com.courier.overc360.api.midmile.primary.model.rescheduledelivery.AddRescheduleDelivery;
import com.courier.overc360.api.midmile.primary.model.rescheduledelivery.RescheduleDelivery;
import com.courier.overc360.api.midmile.primary.model.rescheduledelivery.UpdateRescheduleDelivery;
import com.courier.overc360.api.midmile.primary.repository.RescheduleDeliveryRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.primary.util.DateUtils;
import com.courier.overc360.api.midmile.replica.model.rescheduledelivery.FindRescheduleDelivery;
import com.courier.overc360.api.midmile.replica.model.rescheduledelivery.ReplicaRescheduleDelivery;
import com.courier.overc360.api.midmile.replica.repository.ReplicaRescheduleDeliveryRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaRescheduleDeliverySpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RescheduleDeliveryService {

    @Autowired
    RescheduleDeliveryRepository rescheduleDeliveryRepository;

    @Autowired
    ReplicaRescheduleDeliveryRepository replicaRescheduleDeliveryRepository;

    /*---------------------------------------------------PRIMARY-----------------------------------------------------*/

    /**
     * Get RescheduleDelivery
     *
     * @param languageId
     * @param companyId
     * @param deliveryId
     * @return
     */
    private RescheduleDelivery getReschedule(String languageId, String companyId, String deliveryId) {

        Optional<RescheduleDelivery> dbRescheduleDelivery = rescheduleDeliveryRepository.findByLanguageIdAndCompanyIdAndDeliveryIdAndDeletionIndicator(
                languageId, companyId, deliveryId, 0L
        );

        if (dbRescheduleDelivery.isEmpty()) {
            throw new BadRequestException("Reschedule with given values : languageId - " + languageId +
                    ", companyId - " + companyId + ", deliveryId - " + deliveryId + " doesn't exists");
        }
        return dbRescheduleDelivery.get();
    }

    /**
     * Create RescheduleDelivery
     *
     * @param addRescheduleDelivery
     * @param loginUserID
     * @return
     */
    public RescheduleDelivery createRescheduleDelivery(AddRescheduleDelivery addRescheduleDelivery, String loginUserID) {

        try {
            //Checking for duplicate record
            boolean duplicate = rescheduleDeliveryRepository.existsByLanguageIdAndCompanyIdAndDeliveryIdAndDeletionIndicator(
                    addRescheduleDelivery.getLanguageId(), addRescheduleDelivery.getCompanyId(), addRescheduleDelivery.getDeliveryId(), 0L
            );
            if (duplicate) {
                throw new BadRequestException("Record is getting Duplicated with the given values");
            }

            // Create new Reschedule
            RescheduleDelivery newRescheduleDelivery = new RescheduleDelivery();
            BeanUtils.copyProperties(addRescheduleDelivery, newRescheduleDelivery, CommonUtils.getNullPropertyNames(addRescheduleDelivery));

            newRescheduleDelivery.setCreatedBy(loginUserID);
            newRescheduleDelivery.setCreatedOn(new Date());
            newRescheduleDelivery.setUpdatedBy(loginUserID);
            newRescheduleDelivery.setUpdatedOn(new Date());

            return rescheduleDeliveryRepository.save(newRescheduleDelivery);
        } catch (Exception e) {
            throw new BadRequestException("Reschedule Create Error " + e);
        }
    }

    /**
     * Creating Record if RescheduleStart & End Time is present in Delivery Records
     *
     * @param delivery
     * @return
     */
    public RescheduleDelivery createRescheduleDelivery(Delivery delivery) {

        try {
            // Create new Reschedule
            RescheduleDelivery newRescheduleDelivery = new RescheduleDelivery();
            BeanUtils.copyProperties(delivery, newRescheduleDelivery, CommonUtils.getNullPropertyNames(delivery));

            if (delivery.getReScheduledSlotStart() != null) {
                // Set the reschedule date
                Date rescheduleDate = DateUtils.extractDateOnly(delivery.getReScheduledSlotStart());
                newRescheduleDelivery.setRescheduleDate(rescheduleDate);

                // Set the reschedule start time
                LocalTime rescheduleStartTime = DateUtils.extractTimeOnly(delivery.getReScheduledSlotStart());
                newRescheduleDelivery.setRescheduleStartTime(rescheduleStartTime);
            }
            if (delivery.getReScheduledSlotEnd() != null) {
                // Set the reschedule end time
                LocalTime rescheduleStartTime = DateUtils.extractTimeOnly(delivery.getReScheduledSlotEnd());
                newRescheduleDelivery.setRescheduleEndTime(rescheduleStartTime);
            }


            newRescheduleDelivery.setReasonId(delivery.getNdrId());
            newRescheduleDelivery.setReasonDescription(delivery.getNdrText());
            newRescheduleDelivery.setCreatedOn(new Date());
            newRescheduleDelivery.setUpdatedOn(new Date());
            return rescheduleDeliveryRepository.save(newRescheduleDelivery);
        } catch (Exception e) {
            throw new BadRequestException("Reschedule Create Error " + e);
        }
    }

    /**
     * Update RescheduleDelivery
     *
     * @param updateRescheduleDelivery
     * @param loginUserID
     * @return
     */
    public RescheduleDelivery updateRescheduleDelivery(UpdateRescheduleDelivery updateRescheduleDelivery, String loginUserID) {

        RescheduleDelivery newRescheduleDelivery = getReschedule(updateRescheduleDelivery.getLanguageId(), updateRescheduleDelivery.getCompanyId(), updateRescheduleDelivery.getDeliveryId());

        BeanUtils.copyProperties(updateRescheduleDelivery, newRescheduleDelivery, CommonUtils.getNullPropertyNames(updateRescheduleDelivery));
        newRescheduleDelivery.setUpdatedBy(loginUserID);
        newRescheduleDelivery.setUpdatedOn(new Date());
        return rescheduleDeliveryRepository.save(newRescheduleDelivery);
    }

    /**
     * Delete RescheduleDelivery
     *
     * @param languageId
     * @param companyId
     * @param deliveryId
     */
    public void deleteRescheduleDelivery(String languageId, String companyId, String deliveryId, String loginUserID) {

        RescheduleDelivery dbRescheduleDelivery = getReschedule(languageId, companyId, deliveryId);
        dbRescheduleDelivery.setDeletionIndicator(1L);
        dbRescheduleDelivery.setUpdatedBy(loginUserID);
        dbRescheduleDelivery.setUpdatedOn(new Date());
        rescheduleDeliveryRepository.save(dbRescheduleDelivery);
    }

    /*---------------------------------------------------REPLICA-----------------------------------------------------*/

    /**
     * Get All RescheduleDelivery
     *
     * @return
     */
    public List<ReplicaRescheduleDelivery> getAllRescheduleDelivery() {
        List<ReplicaRescheduleDelivery> rescheduleDeliveryList = replicaRescheduleDeliveryRepository.findAll();

        rescheduleDeliveryList = rescheduleDeliveryList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return rescheduleDeliveryList;
    }

    /**
     * Get RescheduleDelivery
     *
     * @param languageId
     * @param companyId
     * @param deliveryId
     * @return
     */
    public ReplicaRescheduleDelivery getReplicaRescheduleDelivery(String languageId, String companyId, String deliveryId) {

        Optional<ReplicaRescheduleDelivery> dbRescheduleDelivery = replicaRescheduleDeliveryRepository.findByLanguageIdAndCompanyIdAndDeliveryIdAndDeletionIndicator(
                languageId, companyId, deliveryId, 0L
        );

        if (dbRescheduleDelivery.isEmpty()) {
            throw new BadRequestException("RescheduleDelivery with given values : languageId: " + languageId +
                    ", companyId: " + companyId + ", deliveryId: " + deliveryId + " doesn't exists");
        }
        return dbRescheduleDelivery.get();
    }

    /**
     * Find RescheduleDelivery
     *
     * @param findRescheduleDelivery
     * @return
     */
    public List<ReplicaRescheduleDelivery> findRescheduleDelivery(FindRescheduleDelivery findRescheduleDelivery) {

        ReplicaRescheduleDeliverySpecification spec = new ReplicaRescheduleDeliverySpecification(findRescheduleDelivery);
        List<ReplicaRescheduleDelivery> results = replicaRescheduleDeliveryRepository.findAll(spec);
        return results;
    }
}
