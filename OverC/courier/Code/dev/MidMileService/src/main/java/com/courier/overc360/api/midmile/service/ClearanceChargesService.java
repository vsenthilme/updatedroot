package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.clearancecharges.AddClearanceCharges;
import com.courier.overc360.api.midmile.primary.model.clearancecharges.ClearanceCharges;
import com.courier.overc360.api.midmile.primary.repository.ClearanceChargesRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.clearancecharges.FindClearanceCharges;
import com.courier.overc360.api.midmile.replica.model.clearancecharges.ReplicaClearanceCharges;
import com.courier.overc360.api.midmile.replica.repository.ReplicaClearanceChargesRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaClearanceChargesSpecification;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClearanceChargesService {

    @Autowired
    private ReplicaClearanceChargesRepository replicaClearanceChargesRepository;

    @Autowired
    private ClearanceChargesRepository clearanceChargesRepository;

    /*--------------------------------------------------------PRIMARY------------------------------------------------------------------------*/


    /**
     * Get
     *
     * @param clearanceChargesId
     * @return
     */
    public ClearanceCharges getClearanceCharges(Long clearanceChargesId) {
        Optional<ClearanceCharges> dbclearanceCharges = clearanceChargesRepository.findByClearanceChargesIdAndDeletionIndicator
                (clearanceChargesId, 0L);
        if (dbclearanceCharges.isEmpty()) {
            throw new BadRequestException("The given values : clearanceChargeId - " + clearanceChargesId + " doesn't exists");
        }
        return dbclearanceCharges.get();
    }

    /**
     * Create
     *
     * @param addClearanceCharges
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public List<ClearanceCharges> createClearanceCharges(List<AddClearanceCharges> addClearanceCharges, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<ClearanceCharges> clearanceChargesList = new ArrayList<>();
        try {
            addClearanceCharges.stream().forEach(addClearance -> {
                clearanceChargesRepository.findByClearanceChargesIdAndDeletionIndicator
                                (addClearance.getClearanceChargesId(), 0L)
                        .ifPresent(duplicate -> {
                            throw new BadRequestException("Record is getting duplicated with the given values : clearanceChargesId - " + addClearance.getClearanceChargesId());
                        });
                log.info("new ClearanceCharges --> {}", addClearanceCharges);
                String statusDesc = replicaClearanceChargesRepository.getStatusDescription(addClearance.getStatusId());
                ClearanceCharges newClearanceCharges = new ClearanceCharges();
                BeanUtils.copyProperties(addClearance, newClearanceCharges, CommonUtils.getNullPropertyNames(addClearance));
                if (statusDesc != null) {
                    newClearanceCharges.setStatusDescription(statusDesc);
                }
                newClearanceCharges.setDeletionIndicator(0L);
                newClearanceCharges.setCreatedBy(loginUserID);
                newClearanceCharges.setUpdatedBy(loginUserID);
                newClearanceCharges.setCreatedOn(new Date());
                newClearanceCharges.setUpdatedOn(new Date());
                clearanceChargesList.add(clearanceChargesRepository.save(newClearanceCharges));
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return clearanceChargesList;
    }

//    /**
//     * Update
//     *
//     * @param loginUserID
//     * @return
//     * @throws IllegalAccessException
//     * @throws InvocationTargetException
//     * @throws IOException
//     * @throws CsvException
//     */
//    @Transactional
//    public List<ClearanceCharges> updateClearanceCharges(List<ClearanceCharges> clearanceCharges, String loginUserID)
//            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
//
//            List<ClearanceCharges> clearanceChargesList = new ArrayList<>();
//        try {
//            clearanceCharges.stream().forEach(updateClearance -> {
//                ClearanceCharges dbClearanceCharges = getClearanceCharges(updateClearance.getClearanceChargesId());
//                BeanUtils.copyProperties(updateClearance, dbClearanceCharges, CommonUtils.getNullPropertyNames(updateClearance));
//                dbClearanceCharges.setUpdatedBy(loginUserID);
//                dbClearanceCharges.setUpdatedOn(new Date());
//                clearanceChargesList.add(clearanceChargesRepository.save(dbClearanceCharges));
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//        return clearanceChargesList;
//    }

    /**
     * Update
     *
     * @param loginUserID
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */
    @Transactional
    public List<ClearanceCharges> updateClearanceCharges(List<ClearanceCharges> clearanceCharges, String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {

        List<ClearanceCharges> clearanceChargesList = new ArrayList<>();
        try {
            clearanceCharges.stream().forEach(updateClearance -> {
                Optional<ClearanceCharges> CC = clearanceChargesRepository.findByClearanceChargesIdAndDeletionIndicator(updateClearance.getClearanceChargesId(), 0L);
                if (CC.isPresent()) {
                    ClearanceCharges UP = CC.get();
                    BeanUtils.copyProperties(updateClearance, UP, CommonUtils.getNullPropertyNames(updateClearance));
                    UP.setUpdatedBy(loginUserID);
                    UP.setUpdatedOn(new Date());
                    ClearanceCharges saved = clearanceChargesRepository.save(UP);
                    clearanceChargesList.add(saved);
                    log.info("Clearance Charge Table Update Successfully {}", saved);
                } else {
                    ClearanceCharges newCC = new ClearanceCharges();
                    BeanUtils.copyProperties(updateClearance, newCC, CommonUtils.getNullPropertyNames(updateClearance));
                    newCC.setCreatedBy(loginUserID);
                    newCC.setCreatedOn(new Date());
                    ClearanceCharges created = clearanceChargesRepository.save(newCC);
                    clearanceChargesList.add(created);
                    log.info("Clearance Charge New Created Successfully in Update Method {}", created);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return clearanceChargesList;
    }

    /**
     * Delete
     *
     * @param deleteInputList
     * @param loginUserID
     */
    public void deleteClearanceCharges(List<ClearanceCharges> deleteInputList, String loginUserID) {
        if (deleteInputList != null || !deleteInputList.isEmpty()) {
            for (ClearanceCharges deleteInput : deleteInputList) {
                ClearanceCharges dbClearanceCharges = getClearanceCharges(deleteInput.getClearanceChargesId());
                if (dbClearanceCharges.getSubCustomerId() != null) {
                    Long subCustomerIdCount = replicaClearanceChargesRepository.countCustomerId(dbClearanceCharges.getSubCustomerId());
                    if (subCustomerIdCount != null && subCustomerIdCount != 0) {
                        throw new BadRequestException("Sub-Customer ID " + dbClearanceCharges.getSubCustomerId()
                                + " exists in the pre-alert records. Clearance charges cannot be deleted.");
                    }
                }
                if (dbClearanceCharges != null) {
                    dbClearanceCharges.setDeletionIndicator(1L);
                    dbClearanceCharges.setUpdatedBy(loginUserID);
                    dbClearanceCharges.setUpdatedOn(new Date());
                    clearanceChargesRepository.save(dbClearanceCharges);
                }
            }
        } else {
            throw new EntityNotFoundException("Error in deleting ClearanceChargesId ");
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * Get all
     *
     * @return
     */
    public List<ReplicaClearanceCharges> getAll() {
        List<ReplicaClearanceCharges> clearanceChargesList = replicaClearanceChargesRepository.findAll();
        clearanceChargesList = clearanceChargesList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return clearanceChargesList;
    }

    /**
     * Get
     *
     * @param clearanceChargesId
     * @return
     */
    public ReplicaClearanceCharges getReplicaClearanceCharges(Long clearanceChargesId) {
        Optional<ReplicaClearanceCharges> dbclearanceCharges = replicaClearanceChargesRepository.findByClearanceChargesIdAndDeletionIndicator
                (clearanceChargesId, 0L);
        if (dbclearanceCharges.isEmpty()) {
            throw new BadRequestException("The given values : clearanceChargesId - " + clearanceChargesId + " doesn't exists");
        }
        return dbclearanceCharges.get();
    }

    /**
     * Find
     *
     * @param findClearanceCharges
     * @return
     */
    public List<ReplicaClearanceCharges> findClearanceCharges(FindClearanceCharges findClearanceCharges) {
        ReplicaClearanceChargesSpecification spec = new ReplicaClearanceChargesSpecification(findClearanceCharges);
        List<ReplicaClearanceCharges> results = replicaClearanceChargesRepository.findAll(spec);
        log.info("found Clearance Charges --> {}", results);
        return results;
    }

}
