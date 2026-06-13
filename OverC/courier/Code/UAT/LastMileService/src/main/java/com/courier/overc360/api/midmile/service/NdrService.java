package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.delivery.Delivery;
import com.courier.overc360.api.midmile.primary.model.ndr.AddNdr;
import com.courier.overc360.api.midmile.primary.model.ndr.Ndr;
import com.courier.overc360.api.midmile.primary.model.ndr.UpdateNdr;
import com.courier.overc360.api.midmile.primary.model.npr.Npr;
import com.courier.overc360.api.midmile.primary.model.pickup.PickupEntity;
import com.courier.overc360.api.midmile.primary.repository.NdrRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.ndr.FindNdr;
import com.courier.overc360.api.midmile.replica.model.ndr.ReplicaNdr;
import com.courier.overc360.api.midmile.replica.repository.ReplicaNdrRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaNdrSpecification;
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
public class NdrService {

    @Autowired
    private NdrRepository ndrRepository;

    @Autowired
    private ReplicaNdrRepository replicaNdrRepository;

    /*---------------------------------------------------Primary--------------------------------------------------*/

    /**
     * Get Ndr
     *
     * @param languageId
     * @param companyId
     * @param deliveryId
     * @return
     */
    public Ndr getNdr(String languageId, String companyId, String deliveryId) {
        Optional<Ndr> dbndr = ndrRepository.findByLanguageIdAndCompanyIdAndDeliveryIdAndDeletionIndicator(
                languageId, companyId, deliveryId, 0L);
        if (dbndr.isEmpty()) {
            String errMsg = " The Given values LanguageId - " + languageId + " CompanyId - " + companyId + " Delivery Id - "
                    + deliveryId + " doesn't exists ";
            throw new BadRequestException(errMsg);
        }
        return dbndr.get();
    }


    /**
     * Create Ndr
     *
     * @param addNdr
     * @param loginUserID
     * @return
     * @throws InvocationTargetException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws CsvException
     */
    @Transactional
    public List<Ndr> createNdr(List<AddNdr> addNdr, String loginUserID) throws IllegalAccessException,
            InvocationTargetException, IOException, CsvException {
        List<Ndr> ndrList = new ArrayList<>();
        try {
            for (AddNdr ndr : addNdr) {
                ndrRepository.findByLanguageIdAndCompanyIdAndDeliveryIdAndDeletionIndicator(
                                ndr.getLanguageId(), ndr.getCompanyId(), ndr.getDeliveryId(), 0l)
                        .ifPresent(duplicate -> {
                            throw new BadRequestException("Record is getting duplicate with the given values : DeliveryId - " + ndr.getDeliveryId());
                        });
                log.info("new Ndr -->" + addNdr);
                Ndr newNdr = new Ndr();
                BeanUtils.copyProperties(ndr, newNdr, CommonUtils.getNullPropertyNames(ndr));
                newNdr.setDeliveryId(newNdr.getDeliveryId().replaceAll("\\s", "\\"));

                newNdr.setDeletionIndicator(0L);
                newNdr.setCreatedBy(loginUserID);
                newNdr.setCreatedOn(new Date());
                newNdr.setUpdatedBy(loginUserID);
                newNdr.setUpdatedOn(new Date());
                ndrList.add(ndrRepository.save(newNdr));
            }
            return ndrList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Update Ndr
     *
     * @param updateNdrList
     * @param loginUserID
     * @return
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IOException
     * @throws CsvException
     */

    @Transactional
    public List<Ndr> updateNdr(List<UpdateNdr> updateNdrList, String loginUserID) throws IllegalAccessException, InvocationTargetException,
            IOException, CsvException {
        try {
            List<Ndr> ndrList = new ArrayList<>();
            for (UpdateNdr updateNdr : updateNdrList) {
                Ndr dbNdr = getNdr(updateNdr.getLanguageId(), updateNdr.getCompanyId(), updateNdr.getDeliveryId());
                BeanUtils.copyProperties(updateNdr, dbNdr, CommonUtils.getNullPropertyNames(updateNdr));
                dbNdr.setUpdatedBy(loginUserID);
                dbNdr.setUpdatedOn(new Date());

                Ndr ndr1 = ndrRepository.save(dbNdr);
                ndrList.add(ndr1);
            }
            return ndrList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete Ndr
     *
     * @param deleteNdr
     * @param loginUserID
     */

    public void deleteNdr(List<Ndr> deleteNdr, String loginUserID) {
        if (deleteNdr != null || !deleteNdr.isEmpty()) {
            for (Ndr deleteInput : deleteNdr) {
                Ndr dbnNdr = getNdr(deleteInput.getLanguageId(), deleteInput.getCompanyId(), deleteInput.getDeliveryId());
                if (dbnNdr != null) {
                    dbnNdr.setDeletionIndicator(1L);
                    dbnNdr.setUpdatedBy(loginUserID);
                    dbnNdr.setUpdatedOn(new Date());
                    ndrRepository.save(dbnNdr);
                }
            }
        } else {
            throw new EntityNotFoundException("Error in deleting Ndr");
        }
    }

    /*======================================================REPLICA=====================================================*/

    /**
     * GetAll Ndr
     */
    public List<ReplicaNdr> getAllNdr() {
        List<ReplicaNdr> ndrList = replicaNdrRepository.findAll();
        ndrList = ndrList.stream().filter(n -> n.getDeletionIndicator() == 0).collect(Collectors.toList());
        return ndrList;
    }

    /**
     * @param languageId
     * @param companyId
     * @param deliveryId
     * @return
     */
    public ReplicaNdr getReplicaNdr(String languageId, String companyId, String deliveryId) {
        Optional<ReplicaNdr> dbNdr = replicaNdrRepository.findByLanguageIdAndCompanyIdAndDeliveryIdAndDeletionIndicator(
                languageId, companyId, deliveryId, 0L);
        if (dbNdr.isEmpty()) {
            throw new BadRequestException("The given values : companyId - " + companyId + ", languageId - " + languageId + " and DeliveryId - " + deliveryId + " doesn't exists");
        }
        return dbNdr.get();
    }

    public List<ReplicaNdr> findNdr(FindNdr findNdr) {
        ReplicaNdrSpecification spec = new ReplicaNdrSpecification(findNdr);
        List<ReplicaNdr> results = replicaNdrRepository.findAll();
        log.info("Found Ndr -->{}", results);
        return results;
    }

    /**
     * Adding Ndr is Id or Text given during update delivery
     *
     * @param delivery
     * @return
     */
    public Ndr addNdr(Delivery delivery) {
        try {
            // Create new NDR
            Ndr newNdr = new Ndr();
            BeanUtils.copyProperties(delivery, newNdr, CommonUtils.getNullPropertyNames(delivery));
            newNdr.setNdrId(delivery.getNdrId());
            newNdr.setNdrDescription(delivery.getNdrText());
            newNdr.setCreatedOn(new Date());
            newNdr.setUpdatedOn(new Date());
            return ndrRepository.save(newNdr);
        } catch (Exception e) {
            throw new BadRequestException("Npr Create Error " + e);
        }
    }
}


