package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.consignment.AddConsignment;
import com.courier.overc360.api.midmile.primary.model.riderassignment.AddRiderAssignment;
import com.courier.overc360.api.midmile.primary.model.riderassignment.RiderAssignmentDeleteInput;
import com.courier.overc360.api.midmile.primary.model.riderassignment.RiderAssignmentEntity;
import com.courier.overc360.api.midmile.primary.repository.RiderAssignmentEntityRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.primary.util.DateUtils;
import com.courier.overc360.api.midmile.replica.model.riderassignment.FindRiderAssignment;
import com.courier.overc360.api.midmile.replica.model.riderassignment.ReplicaRiderAssignmentEntity;
import com.courier.overc360.api.midmile.replica.repository.ReplicaRiderAssignmentEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RiderAssignmentService {

    @Autowired
    RiderAssignmentEntityRepository riderAssignmentEntityRepository;

    @Autowired
    ReplicaRiderAssignmentEntityRepository replicaRiderAssignmentEntityRepository;


    /*---------------------------------------------------PRIMARY-----------------------------------------------------*/
    // Get RiderAssignment
    private RiderAssignmentEntity getRiderAssignment(String languageId, String companyId, String partnerId, String houseAirwayBill, String pickupId) {

        Optional<RiderAssignmentEntity> riderAssignmentOpt = riderAssignmentEntityRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndHouseAirwayBillAndPickupIdAndDeletionIndicator(
                languageId, companyId, partnerId, houseAirwayBill, pickupId, 0L);
        if (riderAssignmentOpt.isEmpty()) {
            throw new BadRequestException("RiderAssignment with given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", partnerId - " + partnerId + ", houseAirwayBill - " + houseAirwayBill
                    + " and pickupId - " + pickupId + " doesn't exists");
        }
        return riderAssignmentOpt.get();
    }

    /**
     * Create RiderAssignments on Consignment Response
     *
     * @param addConsignmentList
     * @param loginUserID
     * @return
     */
    public List<RiderAssignmentEntity> createRiderConsignmentList(List<AddConsignment> addConsignmentList, String loginUserID) {

        List<RiderAssignmentEntity> riderAssignmentEntityList = new ArrayList<>();
        try {
            addConsignmentList.stream().forEach(addConsignment -> {

                AddRiderAssignment newRider = new AddRiderAssignment();
                BeanUtils.copyProperties(addConsignment, newRider);
                riderAssignmentEntityList.add(createRiderAssignment(newRider, loginUserID));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return riderAssignmentEntityList;
    }


    /**
     * Create RiderAssignments
     *
     * @param addRiderAssignment
     * @param loginUserID
     * @return
     */
    public RiderAssignmentEntity createRiderAssignment(AddRiderAssignment addRiderAssignment, String loginUserID) {

        // Checking for duplicate record
        boolean duplicate = riderAssignmentEntityRepository.existsByLanguageIdAndCompanyIdAndPartnerIdAndHouseAirwayBillAndPickupIdAndDeletionIndicator(
                addRiderAssignment.getLanguageId(), addRiderAssignment.getCompanyId(), addRiderAssignment.getPartnerId(),
                addRiderAssignment.getHouseAirwayBill(), addRiderAssignment.getPickupId(), 0L);
        if (duplicate) {
            throw new BadRequestException("Record is getting Duplicated with given values");
        }

        // Create new RiderAssignmentEntity and related entities
        RiderAssignmentEntity newRiderAssignment = new RiderAssignmentEntity();
        BeanUtils.copyProperties(addRiderAssignment, newRiderAssignment, CommonUtils.getNullPropertyNames(addRiderAssignment));
        newRiderAssignment.setCreatedBy(loginUserID);
        newRiderAssignment.setCreatedOn(new Date());
        newRiderAssignment.setUpdatedBy(null);
        newRiderAssignment.setUpdatedOn(null);

        // Set DestinationDetails
        newRiderAssignment.getDestinationDetails().setCreatedBy(loginUserID);
        newRiderAssignment.getDestinationDetails().setCreatedOn(new Date());
        newRiderAssignment.getDestinationDetails().setUpdatedBy(null);
        newRiderAssignment.getDestinationDetails().setUpdatedOn(null);

        // Set OriginDetails
        newRiderAssignment.getOriginDetails().setCreatedBy(loginUserID);
        newRiderAssignment.getOriginDetails().setCreatedOn(new Date());
        newRiderAssignment.getOriginDetails().setUpdatedBy(null);
        newRiderAssignment.getOriginDetails().setUpdatedOn(null);

        // Set PickupDetails
        newRiderAssignment.getPickupDetails().setCreatedBy(loginUserID);
        newRiderAssignment.getPickupDetails().setCreatedOn(new Date());
        newRiderAssignment.getPickupDetails().setUpdatedBy(null);
        newRiderAssignment.getPickupDetails().setUpdatedOn(null);
        return riderAssignmentEntityRepository.save(newRiderAssignment);
    }

    /**
     * Update RiderAssignments
     *
     * @param updateRiderAssignmentList
     * @param loginUserID
     * @return
     */
    public List<RiderAssignmentEntity> updateRiderAssignment(List<RiderAssignmentEntity> updateRiderAssignmentList, String loginUserID) {

        List<RiderAssignmentEntity> updatedRiderAssignmentList = new ArrayList<>();

        for (RiderAssignmentEntity updateRiderAssignment : updateRiderAssignmentList) {

            RiderAssignmentEntity dbRiderAssignment = getRiderAssignment(updateRiderAssignment.getLanguageId(), updateRiderAssignment.getCompanyId(),
                    updateRiderAssignment.getPartnerId(), updateRiderAssignment.getHouseAirwayBill(), updateRiderAssignment.getPickupId());
            BeanUtils.copyProperties(updateRiderAssignment, dbRiderAssignment, CommonUtils.getNullPropertyNames(updateRiderAssignment));
            dbRiderAssignment.setUpdatedBy(loginUserID);
            dbRiderAssignment.setUpdatedOn(new Date());
            RiderAssignmentEntity riderAssignment = riderAssignmentEntityRepository.save(dbRiderAssignment);

            updatedRiderAssignmentList.add(riderAssignment);
        }
        return updatedRiderAssignmentList;
    }

    /**
     * Delete RiderAssignments
     *
     * @param riderAssignmentDeleteInputList
     * @param loginUserID
     */
    public void deleteRiderAssignments(List<RiderAssignmentDeleteInput> riderAssignmentDeleteInputList, String loginUserID) {

        if (riderAssignmentDeleteInputList != null && !riderAssignmentDeleteInputList.isEmpty()) {
            log.info("given values to delete RiderAssignments --> {}", riderAssignmentDeleteInputList);
//            for (RiderAssignmentDeleteInput deleteInput : riderAssignmentDeleteInputList) {
//
//                RiderAssignmentEntity dbRiderAssignment = getRiderAssignment(deleteInput.getLanguageId(), deleteInput.getCompanyId(),
//                        deleteInput.getPartnerId(), deleteInput.getHouseAirwayBill(), deleteInput.getPickUpId());
//                dbRiderAssignment.setDeletionIndicator(1L);
//                dbRiderAssignment.setUpdatedBy(loginUserID);
//                dbRiderAssignment.setUpdatedOn(new Date());
//                riderAssignmentEntityRepository.save(dbRiderAssignment);
//            }
            riderAssignmentDeleteInputList.parallelStream().forEach(deleteInput -> {
                RiderAssignmentEntity dbRiderAssignment = getRiderAssignment(deleteInput.getLanguageId(), deleteInput.getCompanyId(),
                        deleteInput.getPartnerId(), deleteInput.getHouseAirwayBill(), deleteInput.getPickupId());
                dbRiderAssignment.setDeletionIndicator(1L);
                dbRiderAssignment.setUpdatedBy(loginUserID);
                dbRiderAssignment.setUpdatedOn(new Date());
                riderAssignmentEntityRepository.save(dbRiderAssignment);
            });
        }
    }

    /*---------------------------------------------------REPLICA-----------------------------------------------------*/

    /**
     * Get ALl RiderAssignment Details
     *
     * @return
     */
    public List<ReplicaRiderAssignmentEntity> getAllRiderAssignments() {
        return replicaRiderAssignmentEntityRepository.getAllNonDeletedRiderAssignments();
    }
//    public List<ReplicaRiderAssignmentEntity> getAllRiderAssignments() {
//        List<ReplicaRiderAssignmentEntity> riderAssignmentEntities = replicaRiderAssignmentEntityRepository.findAll()
//                .stream()
//                .filter(i -> i.getDeletionIndicator() == 0)
//                .collect(Collectors.toList());
//        return riderAssignmentEntities;
//    }

    /**
     * Get RiderAssignment - Replica
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param houseAirwayBill
     * @param pickupId
     * @return
     */
    public ReplicaRiderAssignmentEntity getRiderAssignmentReplica(String languageId, String companyId, String partnerId, String houseAirwayBill, String pickupId) {

        Optional<ReplicaRiderAssignmentEntity> riderAssignmentOpt = replicaRiderAssignmentEntityRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndHouseAirwayBillAndPickupIdAndDeletionIndicator(
                languageId, companyId, partnerId, houseAirwayBill, pickupId, 0L);
        if (riderAssignmentOpt.isEmpty()) {
            throw new BadRequestException("RiderAssignment with given values : languageId - " + languageId + ", companyId - " + companyId
                    + ", partnerId - " + partnerId + ", houseAirwayBill - " + houseAirwayBill
                    + " and pickupId " + pickupId + " doesn't exists");
        }
        return riderAssignmentOpt.get();
    }

    /**
     * Find RiderAssignments
     *
     * @param findRiderAssignment
     * @return
     * @throws Exception
     */
//    public Stream<ReplicaRiderAssignmentEntity> findRiderAssignments(FindRiderAssignment findRiderAssignment) throws ParseException {
//
//        if (findRiderAssignment.getFromCreatedOn() != null && findRiderAssignment.getToCreatedOn() != null) {
//            Date[] dates = DateUtils.addTimeToDatesForSearch(findRiderAssignment.getFromCreatedOn(), findRiderAssignment.getToCreatedOn());
//            findRiderAssignment.setFromCreatedOn(dates[0]);
//            findRiderAssignment.setToCreatedOn(dates[1]);
//        }
//        log.info("given Values to fetch RiderAssignments --> {}", findRiderAssignment);
//        RiderAssignmentSpecification spec = new RiderAssignmentSpecification(findRiderAssignment);
//        return replicaRiderAssignmentEntityRepository.findAll(spec).stream();
//    }
    public List<ReplicaRiderAssignmentEntity> findRiderAssignments(FindRiderAssignment findRiderAssignment) throws Exception {

        if (findRiderAssignment.getFromCreatedOn() != null && findRiderAssignment.getToCreatedOn() != null) {
            Date[] dates = DateUtils.addTimeToDatesForSearchV2(findRiderAssignment.getFromCreatedOn(), findRiderAssignment.getToCreatedOn());
            findRiderAssignment.setFromCreatedOn(dates[0]);
            findRiderAssignment.setToCreatedOn(dates[1]);
        }
        log.info("given Values to fetch RiderAssignments with Qry --> {}", findRiderAssignment);
        return replicaRiderAssignmentEntityRepository.findRiderAssignmentsWithOptionalParams(
                findRiderAssignment.getLanguageId(), findRiderAssignment.getCompanyId(), findRiderAssignment.getPartnerId(),
                findRiderAssignment.getHouseAirwayBill(), findRiderAssignment.getPickupId(),
                findRiderAssignment.getFromCreatedOn(), findRiderAssignment.getToCreatedOn());
    }


}
