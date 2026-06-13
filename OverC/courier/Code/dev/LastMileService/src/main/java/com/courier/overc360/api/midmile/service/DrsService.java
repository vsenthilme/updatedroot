package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.delivery.Delivery;
import com.courier.overc360.api.midmile.primary.model.drs.AddDrs;
import com.courier.overc360.api.midmile.primary.model.drs.Drs;
import com.courier.overc360.api.midmile.primary.model.drs.UpdateDrs;
import com.courier.overc360.api.midmile.primary.model.pickup.ConsignmentEntity;
import com.courier.overc360.api.midmile.primary.repository.DrsRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.drs.FindDrs;
import com.courier.overc360.api.midmile.replica.model.drs.ReplicaDrs;
import com.courier.overc360.api.midmile.replica.repository.ReplicaDrsRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaDrsSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DrsService {

    @Autowired
    DrsRepository drsRepository;

    @Autowired
    ReplicaDrsRepository replicaDrsRepository;

    @Autowired
    NumberRangeService numberRangeService;

    /*---------------------------------------------------PRIMARY-----------------------------------------------------*/

    /**
     * Get DRS
     *
     * @param languageId
     * @param companyId
     * @param customerId
     * @param houseAirwayBill
     * @return
     */
    public Drs getDrs(String languageId, String companyId, String customerId, String houseAirwayBill, String pieceId) {

        Optional<Drs> dbDrs = drsRepository.findByLanguageIdAndCompanyIdAndCustomerIdAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
                languageId, companyId, customerId, houseAirwayBill, pieceId,0L
        );
        if (dbDrs.isEmpty()) {
            throw new BadRequestException("DRS with the given values:  languageaId: " + languageId + ", companyId: " + companyId + ", customerId: " + customerId + ", houseAirwayBill: " + houseAirwayBill + " doesn't exists");
        }
        return dbDrs.get();
    }

    /**
     * Create Drs
     *
     * @param addDrs
     * @param loginUserID
     * @return
     */
    public Drs createDrs(AddDrs addDrs, String loginUserID) {

        try {
            // Checking for duplicate record
            if (addDrs.getHouseAirwayBill() != null) {
                boolean duplicate = drsRepository.existsByLanguageIdAndCompanyIdAndCustomerIdAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
                        addDrs.getLanguageId(), addDrs.getCompanyId(), addDrs.getCustomerId(), addDrs.getHouseAirwayBill(),
                        addDrs.getPieceId(), 0L
                );
                if (duplicate) {
                    throw new BadRequestException("Record is getting Duplicated with the given values");
                }
            }

            // Create new Drs and related entities
            Drs newDrs = new Drs();
            BeanUtils.copyProperties(addDrs, newDrs, CommonUtils.getNullPropertyNames(addDrs));
            newDrs.setCreatedBy(loginUserID);
            newDrs.setCreatedOn(new Date());
            newDrs.setUpdatedBy(loginUserID);
            newDrs.setUpdatedOn(new Date());

            return drsRepository.save(newDrs);
        } catch (Exception e) {
            throw new BadRequestException("Drs Create Error " + e);
        }
    }

    /**
     * Create Drs List
     *
     * @param addDrsList
     * @param loginUserID
     * @return
     */
    public List<Drs> createDrsList(List<AddDrs> addDrsList, String loginUserID) {

        List<Drs> drsList = new ArrayList<>();
        try {
            addDrsList.stream().forEach(drs -> {
                AddDrs newDrs = new AddDrs();
                BeanUtils.copyProperties(drs, newDrs);
                drsList.add(createDrs(newDrs, loginUserID));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drsList;
    }

    /**
     * Drs Delivery create
     *
     * @param delivery
     * @param loginUserID
     * @return
     */
    public Drs createDeliveryDrs(Delivery delivery, String loginUserID) {

        try {
            Drs newDrs = new Drs();
            BeanUtils.copyProperties(delivery, newDrs, CommonUtils.getNullPropertyNames(delivery));

            // Set ConsignorName, Address and PhoneNumber
            String consigneeDetails = drsRepository.consigneeDetails(delivery.getLanguageId(), delivery.getCompanyId(), delivery.getHouseAirwayBill());
            newDrs.setConsignorDetails(consigneeDetails);

            // Set ConsigneeName, Address and PhoneNumber
            newDrs.setConsigneeDetails(delivery.getDestinationAddress1());

            // Set HubCode
            String hubDes = drsRepository.hubDes(delivery.getCourierId());
            newDrs.setHubCode(hubDes);

            // Set Description
            newDrs.setDescription(delivery.getDescription() != null ? delivery.getDescription() : " ");

            // Set No. of piece
            newDrs.setPieceCount(delivery.getPieceCount());

            // Set Amount
            newDrs.setAmount(String.valueOf(delivery.getCodAmount()));

            newDrs.setCustomerId(delivery.getCourierId());
            newDrs.setCreatedBy(loginUserID);
            newDrs.setCreatedOn(new Date());
            newDrs.setUpdatedBy(loginUserID);
            newDrs.setUpdatedOn(new Date());

            return drsRepository.save(newDrs);
        } catch (Exception e) {
            throw new BadRequestException("Drs Create error " + e);
        }
    }

    /**
     * Update Drs List
     *
     * @param updateDrsList
     * @param loginUserID
     * @return
     */
    public List<Drs> updateDrsList(List<Drs> updateDrsList, String loginUserID) {

        List<Drs> drsArrayList = new ArrayList<>();

        for (Drs updateDrs : updateDrsList) {
            Optional<Drs> dbDrs = drsRepository.findByLanguageIdAndCompanyIdAndCustomerIdAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
                    updateDrs.getLanguageId(), updateDrs.getCompanyId(), updateDrs.getCustomerId(), updateDrs.getHouseAirwayBill(),
                    updateDrs.getPieceId(), 0L
            );

            if (dbDrs.isPresent()) {
                Drs newDrs = dbDrs.get();
                BeanUtils.copyProperties(updateDrs, newDrs, CommonUtils.getNullPropertyNames(updateDrs));
                newDrs.setUpdatedOn(new Date());
                newDrs.setUpdatedBy(loginUserID);
                Drs drs1 = drsRepository.save(newDrs);
                drsArrayList.add(drs1);
            }
        }
        return updateDrsList;
    }

    /**
     * Update Drs Single
     *
     * @param updateDrs
     * @param loginUserID
     * @return
     */
    public Drs updateDrs(UpdateDrs updateDrs, String loginUserID) {

        Optional<Drs> dbDrs = drsRepository.findByLanguageIdAndCompanyIdAndCustomerIdAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
                updateDrs.getLanguageId(), updateDrs.getCompanyId(), updateDrs.getCustomerId(), updateDrs.getHouseAirwayBill(),
                updateDrs.getPieceId(), 0L
        );

        Drs newDrs = new Drs();
        BeanUtils.copyProperties(updateDrs, newDrs, CommonUtils.getNullPropertyNames(updateDrs));
        newDrs.setUpdatedBy(loginUserID);
        newDrs.setUpdatedOn(new Date());
        return drsRepository.save(newDrs);
    }

    /**
     * Delete Drs List
     *
     * @param drsDeleteList
     * @param loginUserID
     */
    public void deleteDrsList(List<Drs> drsDeleteList, String loginUserID) {

        if (drsDeleteList != null && !drsDeleteList.isEmpty()) {
            log.info("given values to delete Drs --->  {}", drsDeleteList);

            drsDeleteList.parallelStream().forEach(deleteInput -> {
                Drs dbDrs = getDrs(deleteInput.getLanguageId(), deleteInput.getCompanyId(), deleteInput.getCustomerId(),
                        deleteInput.getHouseAirwayBill(), deleteInput.getPieceId());
                dbDrs.setDeletionIndicator(1L);
                dbDrs.setUpdatedBy(loginUserID);
                dbDrs.setUpdatedOn(new Date());
                drsRepository.save(dbDrs);
            });
        }
    }

    /**
     * Delete Drs
     *
     * @param languageId
     * @param companyId
     * @param customerId
     * @param houseAirwayBill
     * @param loginUserID
     */
    public void deleteDrs(String languageId, String companyId, String customerId, String houseAirwayBill, String pieceId, String loginUserID) {

        Drs dbDrs = getDrs(languageId, companyId, customerId, houseAirwayBill, pieceId);
        dbDrs.setDeletionIndicator(1L);
        dbDrs.setUpdatedBy(loginUserID);
        dbDrs.setUpdatedOn(new Date());
        drsRepository.save(dbDrs);
    }

    /*---------------------------------------------------REPLICA-----------------------------------------------------*/

    /**
     * Get All Drs
     *
     * @return
     */
    public List<ReplicaDrs> getAllDrs() {
        List<ReplicaDrs> drsList = replicaDrsRepository.findAll();

        drsList = drsList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return drsList;
    }

    /**
     * Get Drs
     *
     * @param languageId
     * @param companyId
     * @param customerId
     * @param houseAirwayBill
     * @return
     */
    public ReplicaDrs getReplicaDrs(String languageId, String companyId, String customerId, String houseAirwayBill, String pieceId) {

        Optional<ReplicaDrs> dbDrs = replicaDrsRepository.findByLanguageIdAndCompanyIdAndCustomerIdAndHouseAirwayBillAndPieceIdAndDeletionIndicator(
                languageId, companyId, customerId, houseAirwayBill, pieceId,0L
        );

        if (dbDrs.isEmpty()) {
            throw new BadRequestException("Drs with given values : languageId: " + languageId + ", companyId: " + companyId + ", customerId: " + customerId + ", houseAirwayBilll: " + houseAirwayBill + " doesn't exists");
        }
        return dbDrs.get();
    }

    /**
     * Find Drs
     *
     * @param findDrs
     * @return
     */
    public List<ReplicaDrs> findDrs(FindDrs findDrs) {

        ReplicaDrsSpecification spec = new ReplicaDrsSpecification(findDrs);
        List<ReplicaDrs> results = replicaDrsRepository.findAll(spec);
        log.info("Found Drs ----> " + results);
        return results;
    }
}
