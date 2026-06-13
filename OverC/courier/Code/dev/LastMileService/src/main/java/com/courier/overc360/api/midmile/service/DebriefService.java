package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.debrief.AddDebrief;
import com.courier.overc360.api.midmile.primary.model.debrief.Debrief;
import com.courier.overc360.api.midmile.primary.model.delivery.Delivery;
import com.courier.overc360.api.midmile.primary.repository.DebriefRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.debrief.FindDebrief;
import com.courier.overc360.api.midmile.replica.model.debrief.ReplicaDebrief;
import com.courier.overc360.api.midmile.replica.repository.ReplicaDebriefRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaDebriefSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DebriefService {

    @Autowired
    DebriefRepository debriefRepository;

    @Autowired
    ReplicaDebriefRepository replicaDebriefRepository;

    /*---------------------------------------------------PRIMARY-----------------------------------------------------*/

    /**
     * Get Debrief
     *
     * @param languageId
     * @param companyId
     * @param courierId
     * @return
     */
    public Debrief getDebrief(String languageId, String companyId, String courierId) {

        Optional<Debrief> dbDebrief = debriefRepository.findByLanguageIdAndCompanyIdAndCourierIdAndDeletionIndicator(
                languageId, companyId, courierId, 0L
        );

        if (dbDebrief.isEmpty()) {
            throw new BadRequestException("Debrief with the given values:  languageaId: " + languageId + ", companyId: " + companyId + ", courierId: " + courierId + " doesn't exists");
        }

        return dbDebrief.get();
    }

    /**
     * Create Debrief
     *
     * @param addDebrief
     * @param loginUserID
     * @return
     */
    public Debrief createDebrief(AddDebrief addDebrief, String loginUserID) {

        try {
            // Create new Debrief
            Debrief newDebrief = new Debrief();
            BeanUtils.copyProperties(addDebrief, newDebrief, CommonUtils.getNullPropertyNames(addDebrief));

            // No of Assigned
            Long assigned = replicaDebriefRepository.pickupDeliveryCount(newDebrief.getCourierId(), "48");
            newDebrief.setNoOfAssigned(assigned != null ? assigned : 0L);

            // No of Delivered
            Long delivered = replicaDebriefRepository.pickupDeliveryCount(newDebrief.getCourierId(), "18");
            newDebrief.setNoOfDelivered(delivered != null ? delivered : 0L);

            // No of Returned
            Long returned = replicaDebriefRepository.pickupDeliveryCount(newDebrief.getCourierId(), "18");
            newDebrief.setNoOfReturned(returned != null ? returned : 0L);

            // No of Attempted
            Long attempted = replicaDebriefRepository.pickupDeliveryCount(newDebrief.getCourierId(), "18");
            newDebrief.setNoOfAttempted(attempted != null ? attempted : 0L);

            // No of PaidCash
            Long paidCash = replicaDebriefRepository.pickupDeliveryAmountSum(newDebrief.getCourierId());
            newDebrief.setNoOfPaidCash(paidCash != null ? paidCash : 0L);

            // No of PaidLink
            Long paidLink = replicaDebriefRepository.pickupDeliveryCount(newDebrief.getCourierId(), "18");
            newDebrief.setNoOfPaidLink(paidLink != null ? paidLink : 0L);

            newDebrief.setCreatedOn(new Date());
            newDebrief.setCreatedBy(loginUserID);
            newDebrief.setUpdatedOn(new Date());
            newDebrief.setUpdatedBy(loginUserID);

            return debriefRepository.save(newDebrief);
        } catch (Exception e) {
            throw new BadRequestException("Debrief Create Error " + e);
        }
    }

    /**
     * Debrief delivery create
     *
     * @param delivery
     * @return
     */
    public Debrief createDeliveryDebrief(Delivery delivery, String loginUserID) {

        try {
            // Create new Debrief
            Debrief newDebrief = new Debrief();
            BeanUtils.copyProperties(delivery, newDebrief, CommonUtils.getNullPropertyNames(delivery));

            // No of Assigned
            Long assigned = replicaDebriefRepository.pickupDeliveryCount(newDebrief.getCourierId(), "48");
            newDebrief.setNoOfAssigned(assigned != null ? assigned : 0L);

            // No of Delivered
            Long delivered = replicaDebriefRepository.pickupDeliveryCount(newDebrief.getCourierId(), "18");
            newDebrief.setNoOfDelivered(delivered != null ? delivered : 0L);

            // No of Returned
            Long returned = replicaDebriefRepository.pickupDeliveryCount(newDebrief.getCourierId(), "54");  // or 55
            newDebrief.setNoOfReturned(returned != null ? returned : 0L);

            // No of Attempted
            Long attempted = replicaDebriefRepository.pickupDeliveryCount(newDebrief.getCourierId(), "17");  // or 55
            newDebrief.setNoOfAttempted(attempted != null ? attempted : 0L);

            // No of PaidCash
            Long paidCash = replicaDebriefRepository.pickupDeliveryAmountSum(newDebrief.getCourierId());
            newDebrief.setNoOfPaidCash(paidCash != null ? paidCash : 0L);

            // No of PaidLink
            Long paidLink = replicaDebriefRepository.noOfPainLink(newDebrief.getCourierId());
            newDebrief.setNoOfPaidLink(paidLink != null ? paidLink : 0L);

            //Time of Departure
            Date departureTime = replicaDebriefRepository.departureTime(delivery.getManifestNumber(), "Out for delivery");
            newDebrief.setTimeOfDeparture(departureTime != null ? departureTime : null);

            //Time of FirstStop
            Date firstStop = replicaDebriefRepository.firstStop(delivery.getManifestNumber(), "Delivery Rescheduled");
            newDebrief.setTimeOfFirstStop(firstStop != null ? firstStop : null);

            //Time of LastStop
            Date lastStop = replicaDebriefRepository.lastStop(delivery.getManifestNumber(), "Delivery Rescheduled");
            newDebrief.setTimeOfLastStop(lastStop != null ? lastStop : null);

            newDebrief.setCreatedOn(new Date());
            newDebrief.setDate(new Date());
            newDebrief.setCreatedBy(loginUserID);
            newDebrief.setUpdatedOn(new Date());
            newDebrief.setUpdatedBy(loginUserID);

            return debriefRepository.save(newDebrief);
        } catch (Exception e) {
            throw new BadRequestException("Debrief create error " + e);
        }
    }

    /**
     * Update Debrief
     *
     * @param updateDebrief
     * @param loginUserID
     * @return
     */
    public Debrief updateDebrief(Debrief updateDebrief, String loginUserID) {

        Optional<Debrief> dbDebrief = debriefRepository.findByLanguageIdAndCompanyIdAndCourierIdAndDeletionIndicator(
                updateDebrief.getLanguageId(), updateDebrief.getCompanyId(), updateDebrief.getCourierId(), 0L
        );

        Debrief newDebrief = new Debrief();
        BeanUtils.copyProperties(updateDebrief, newDebrief, CommonUtils.getNullPropertyNames(updateDebrief));
        newDebrief.setUpdatedOn(new Date());
        newDebrief.setUpdatedBy(loginUserID);
        return debriefRepository.save(newDebrief);
    }

    /**
     * Delete Debrief
     *
     * @param languageId
     * @param companyId
     * @param customerId
     * @param loginUserID
     */
    public void deleteDebrief(String languageId, String companyId, String customerId, String loginUserID) {

        Debrief dbDebrief = getDebrief(languageId, companyId, customerId);
        dbDebrief.setDeletionIndicator(1L);
        dbDebrief.setUpdatedBy(loginUserID);
        dbDebrief.setUpdatedOn(new Date());
        debriefRepository.save(dbDebrief);
    }

    /**
     * Find Debrief
     *
     * @param findDebrief
     * @return
     */
    public List<ReplicaDebrief> findDebrief(FindDebrief findDebrief) {

        ReplicaDebriefSpecification spec = new ReplicaDebriefSpecification(findDebrief);
        List<ReplicaDebrief> results = replicaDebriefRepository.findAll(spec);
        return results;
    }
}
