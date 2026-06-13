package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.bagtracking.AddBagTracking;
import com.courier.overc360.api.midmile.primary.model.bagtracking.BagTracking;
import com.courier.overc360.api.midmile.primary.model.bagtracking.UpdateBagTracking;
import com.courier.overc360.api.midmile.primary.model.consignment.ConsignmentEntity;
import com.courier.overc360.api.midmile.primary.model.pickup.PickupEntity;
import com.courier.overc360.api.midmile.primary.repository.BagTrackingRepository;
import com.courier.overc360.api.midmile.primary.repository.ConsignmentEntityRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.bagtracking.FindBagTracking;
import com.courier.overc360.api.midmile.replica.model.bagtracking.ReplicaBagTracking;
import com.courier.overc360.api.midmile.replica.repository.ReplicaBagTrackingRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaBagTrackingSpecification;
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
public class BagTrackingService {

    @Autowired
    private ReplicaBagTrackingRepository replicaBagTrackingRepository;

    @Autowired
    ConsignmentEntityRepository consignmentEntityRepository;

    @Autowired
    private NumberRangeService numberRangeService;

    @Autowired
    private BagTrackingRepository bagTrackingRepository;

    @Autowired
    private ConsignmentStatusService consignmentStatusService;


    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/


    /**
     * Get
     *
     * @param companyId
     * @param languageId
     * @param partnerId
     * @param houseAirwayBill
     * @return
     */
    public BagTracking getBagTracking(String companyId, String languageId, String partnerId, Long consignmentBagId, String houseAirwayBill) {
        Optional<BagTracking> dbBagTracking = bagTrackingRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndConsignmentBagIdAndHouseAirwayBillAndDeletionIndicator
                (languageId, companyId, partnerId, consignmentBagId, houseAirwayBill, 0L);
        if (dbBagTracking.isEmpty()) {
            throw new BadRequestException("The given values : companyId - " + companyId + ", languageId - " + languageId + ", partnerId - "
                    + partnerId + ", houseAirwayBill - " + houseAirwayBill + " and consignmentBagId - " + consignmentBagId + " doesn't exists");
        }
        return dbBagTracking.get();
    }

    /**
     * Create
     *
     * @param addBagTracking
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public List<BagTracking> createBagTracking(List<AddBagTracking> addBagTracking, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            String BAG_ID = numberRangeService.getNextNumberRange("BAGID");
            log.info("Next Value from NumberRange for BAG_ID: " + BAG_ID);

            List<BagTracking> bagTrackingList = new ArrayList<>();
            for (AddBagTracking bagTracking : addBagTracking) {
                boolean duplicateBagTracking = replicaBagTrackingRepository.existsByLanguageIdAndCompanyIdAndPartnerIdAndHouseAirwayBillAndDeletionIndicator(
                        bagTracking.getCompanyId(), bagTracking.getLanguageId(), bagTracking.getPartnerId(), bagTracking.getHouseAirwayBill(), 0L);
                if (duplicateBagTracking) {
                    throw new BadRequestException("Record is getting Duplicated with the given values : BagTracking - " + bagTracking.getHouseAirwayBill());
                }

                log.info("new BagTracking --> {}", bagTracking);
                BagTracking newBagTracking = new BagTracking();
                BeanUtils.copyProperties(bagTracking, newBagTracking, CommonUtils.getNullPropertyNames(addBagTracking));
                newBagTracking.setConsignmentBagId(Long.valueOf(BAG_ID));
                newBagTracking.setStatusId("39");
                String status = consignmentEntityRepository.statusText("39");
                if(status != null) {
                    newBagTracking.setStatusDescription(status);
                }
                newBagTracking.setDeletionIndicator(0L);
                newBagTracking.setCreatedBy(loginUserID);
                newBagTracking.setCreatedOn(new Date());
                newBagTracking.setUpdatedBy(loginUserID);
                newBagTracking.setUpdatedOn(new Date());
                bagTrackingRepository.save(newBagTracking);
                consignmentEntityRepository.updateConsignmentBagTracking(newBagTracking.getConsignmentBagId(), newBagTracking.getCompanyId(), newBagTracking.getLanguageId(), newBagTracking.getHouseAirwayBill(), newBagTracking.getStatusId(), newBagTracking.getStatusDescription());
                consignmentStatusService.insertConsignmentStatusRecord(newBagTracking.getLanguageId(), newBagTracking.getLanguageDescription(), newBagTracking.getCompanyId(), newBagTracking.getCompanyName(), newBagTracking.getPieceId(), newBagTracking.getPartnerMasterAirwayBill(), newBagTracking.getHouseAirwayBill(),
                        "STATUS", newBagTracking.getStatusId(), newBagTracking.getStatusDescription(), null, "STATUS", newBagTracking.getStatusId(), newBagTracking.getStatusDescription(), null, loginUserID, newBagTracking.getPartnerHouseAirwayBill(),
                        newBagTracking.getPartnerMasterAirwayBill(), newBagTracking.getConsignmentBagId(), newBagTracking.getHubCode(), newBagTracking.getHubName());
               log.info("BagTracking Consignment Status Created Successfully");
                bagTrackingList.add(newBagTracking);
            }
            return bagTrackingList;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update BagTracking
     *
     * @param loginUserID
     * @param updateBagTracking
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public List<BagTracking> updateBagTracking(String loginUserID,
                                         List<UpdateBagTracking> updateBagTracking)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        try {
            List<BagTracking> updateBagTrackingList = new ArrayList<>();
            for (UpdateBagTracking updateBagTracking1 : updateBagTracking) {
                BagTracking dbBagTracking = getBagTracking(updateBagTracking1.getCompanyId(), updateBagTracking1.getCompanyId(),
                        updateBagTracking1.getPieceId(), updateBagTracking1.getConsignmentBagId(), updateBagTracking1.getHouseAirwayBill());
                BeanUtils.copyProperties(updateBagTracking1, dbBagTracking, CommonUtils.getNullPropertyNames(updateBagTracking1));
                dbBagTracking.setUpdatedBy(loginUserID);
                dbBagTracking.setUpdatedOn(new Date());
                bagTrackingRepository.save(dbBagTracking);
                updateBagTrackingList.add(dbBagTracking);
            }
            return updateBagTrackingList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete BagTracking
     *
     * @param deleteInput
     * @param loginUserID
     */
    public void deleteBagTracking(List<BagTracking> deleteInput, String loginUserID) {
        List<BagTracking> bagTrackingList = new ArrayList<>();

        for (BagTracking bagTracking : deleteInput) {
            BagTracking dbBagTracking = getBagTracking(bagTracking.getLanguageId(), bagTracking.getCompanyId(),
                    bagTracking.getPartnerId(), bagTracking.getConsignmentBagId(), bagTracking.getHouseAirwayBill());
            if (dbBagTracking != null) {
                dbBagTracking.setDeletionIndicator(1L);
                dbBagTracking.setUpdatedBy(loginUserID);
                dbBagTracking.setUpdatedOn(new Date());
                bagTrackingRepository.save(dbBagTracking);
                bagTrackingList.add(dbBagTracking);
            } else {
                throw new BadRequestException("Error in deleting BagTracking - " + bagTracking.getConsignmentBagId());
            }
        }
    }

    /*=================================================REPLICA=======================================================*/

    /**
     * Get all BagTracking Details
     *
     * @return
     */
    public List<ReplicaBagTracking> getAllBagTrackings() {
        List<ReplicaBagTracking> bagTrackingList = replicaBagTrackingRepository.findAll();
        bagTrackingList = bagTrackingList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return bagTrackingList;
    }

    /**
     * Get BagTracking
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param houseAirwayBill
     * @return
     */
    public ReplicaBagTracking getReplicaBagTracking(String languageId, String companyId, String partnerId, Long consignmentBagId, String houseAirwayBill) {

        Optional<ReplicaBagTracking> dbBagTracking = replicaBagTrackingRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndConsignmentBagIdAndHouseAirwayBillAndDeletionIndicator
                (languageId, companyId, partnerId, consignmentBagId, houseAirwayBill, 0L);

        if (dbBagTracking.isEmpty()) {
            throw new BadRequestException("The given values : companyId - " + companyId + ", languageId - " + languageId + ", partnerId - " + partnerId +
                    ", houseAirwayBill - " + houseAirwayBill + " and bagId - " + consignmentBagId + " doesn't exists");
        }
        return dbBagTracking.get();
    }

    /**
     * Find BagTracking
     *
     * @param findBagTracking
     * @return
     */
    public List<ReplicaBagTracking> findBagTrackings(FindBagTracking findBagTracking) {

        ReplicaBagTrackingSpecification spec = new ReplicaBagTrackingSpecification(findBagTracking);
        List<ReplicaBagTracking> results = replicaBagTrackingRepository.findAll(spec);
        log.info("found BagTracking --> {}", results);
        return results;
    }


    /**
     *
     * @param consignmentEntities
     * @param loginUserID
     * @return
     */
    public List<BagTracking> createConsignmentToBagTrack(List<ConsignmentEntity> consignmentEntities, String loginUserID) {
        List<AddBagTracking> bagTrackingList = new ArrayList<>();
        try {
            consignmentEntities.parallelStream().forEach(consignment -> {

                AddBagTracking newBagTrack = new AddBagTracking();
                BeanUtils.copyProperties(consignment, newBagTrack, CommonUtils.getNullPropertyNames(consignment));
                bagTrackingList.add(newBagTrack);
            });
            return createBagTracking(bagTrackingList, loginUserID);
        } catch (Exception e) {
            throw new RuntimeException("PickUp to Consignment Create Failed" + e.getMessage());
        }
    }

    /**
     * Create BagTracking from PickupRequest
     *
     * @param pickupEntityList
     * @param loginUserID
     * @return
     */
    public List<BagTracking> createBagFromPickupRequest(List<PickupEntity> pickupEntityList, String loginUserID) throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
        List<AddBagTracking> bagTrackingList = new ArrayList<>();

        try {
            pickupEntityList.stream().forEach(pickup -> {
                AddBagTracking bagTracking = new AddBagTracking();
                BeanUtils.copyProperties(pickup, bagTracking, CommonUtils.getNullPropertyNames(pickup));

                bagTracking.setReferenceField1("Bag Tracking Created From Pickup Request");
                bagTrackingList.add(bagTracking);
            });
        } catch (Exception e) {
            throw new RuntimeException("Pickup to BagTracking Create Failed " + e.getMessage());
        }

        return createBagTracking(bagTrackingList, loginUserID);
    }

}
