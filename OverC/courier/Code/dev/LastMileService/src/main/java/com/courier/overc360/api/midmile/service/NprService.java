package com.courier.overc360.api.midmile.service;


import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.drs.AddDrs;
import com.courier.overc360.api.midmile.primary.model.drs.Drs;
import com.courier.overc360.api.midmile.primary.model.npr.Npr;
import com.courier.overc360.api.midmile.primary.model.pickup.PickupEntity;
import com.courier.overc360.api.midmile.primary.model.reschedulepickup.ReSchedulePickUp;
import com.courier.overc360.api.midmile.primary.repository.NprRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.drs.ReplicaDrs;
import com.courier.overc360.api.midmile.replica.model.npr.FindNpr;
import com.courier.overc360.api.midmile.replica.model.npr.ReplicaNpr;
import com.courier.overc360.api.midmile.replica.repository.ReplicaNprRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaDrsSpecification;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaNprSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
public class NprService {

    @Autowired
    private NprRepository nprRepository;

    @Autowired
    private ReplicaNprRepository replicaNprRepository;


    /**
     *
     * @param nprList
     * @param loginUserID
     * @return
     */
    public List<Npr> createNpr(List<Npr> nprList, String loginUserID) {

        List<Npr> nprs = new ArrayList<>();
        try {
            nprList.stream().forEach(npr -> {

                boolean duplicate = nprRepository.existsByLanguageIdAndCompanyIdAndPickupIdAndDeletionIndicator(
                        npr.getLanguageId(), npr.getCompanyId(), npr.getPickupId(), 0L);
                if (duplicate) {
                    throw new BadRequestException("Record is getting Duplicated with the given values");
                }

                // Create new Npr and related entities
                Npr newNpr = new Npr();
            BeanUtils.copyProperties(npr, newNpr, CommonUtils.getNullPropertyNames(npr));

                newNpr.setCreatedBy(loginUserID);
                newNpr.setCreatedOn(new Date());
                newNpr.setUpdatedBy(loginUserID);
                newNpr.setUpdatedOn(new Date());
                Npr npr1 = nprRepository.save(newNpr);
                nprs.add(npr1);
                log.info("size --{}", nprs.size());
            });
        }  catch (Exception e) {
            throw new BadRequestException("Npr Create Error " + e);
        }
        return nprs;
    }


    /**
     *
     * @param updateNprList
     * @param loginUserID
     * @return
     */
    public List<Npr> updateNprList(List<Npr> updateNprList, String loginUserID) {

        List<Npr> nprList = new ArrayList<>();

        for (Npr updateNpr : updateNprList) {
            Optional<Npr> dbNpr = nprRepository.findByLanguageIdAndCompanyIdAndPickupIdAndDeletionIndicator(
                    updateNpr.getLanguageId(), updateNpr.getCompanyId(), updateNpr.getPickupId(), 0L);

            if (dbNpr.isPresent()) {
                Npr newNpr = dbNpr.get();
                BeanUtils.copyProperties(updateNpr, newNpr, CommonUtils.getNullPropertyNames(updateNpr));
                newNpr.setUpdatedOn(new Date());
                newNpr.setUpdatedBy(loginUserID);
                Npr npr = nprRepository.save(newNpr);
                nprList.add(npr);
            }
        }
        return nprList;
    }


    /**
     *
     * @param deleteNprList
     * @param loginUserID
     */
    public void deleteNprList(List<Npr> deleteNprList, String loginUserID) {
        if (deleteNprList != null && !deleteNprList.isEmpty()) {
            log.info("given values to delete Npr --->  {}", deleteNprList);

            deleteNprList.parallelStream().forEach(deleteInput -> {
                Npr dbNpr = getNpr(deleteInput.getLanguageId(), deleteInput.getCompanyId(), deleteInput.getPickupId());
                dbNpr.setDeletionIndicator(1L);
                dbNpr.setUpdatedBy(loginUserID);
                dbNpr.setUpdatedOn(new Date());
                nprRepository.save(dbNpr);
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
    public Npr getNpr(String languageId, String companyId, String pickupId) {
        Optional<Npr> dbNpr = nprRepository.findByLanguageIdAndCompanyIdAndPickupIdAndDeletionIndicator(
            languageId, companyId, pickupId, 0L);
        if (dbNpr.isEmpty()) {
            throw new BadRequestException("NPR with the given values:  languageaId: " + languageId + ", companyId: " + companyId + ", pickupId: " + pickupId +  " doesn't exists");
        }
        return dbNpr.get();

    }

    public List<ReplicaNpr> getAllNpr() {

            List<ReplicaNpr> nprList = replicaNprRepository.findAll();

            nprList = nprList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
            return nprList;

    }

    /**
     *
     * @param languageId
     * @param companyId
     * @param pickupId
     * @return
     */
    public ReplicaNpr getReplicaNpr(String languageId, String companyId, String pickupId) {
        Optional<ReplicaNpr> dbNpr = replicaNprRepository.findByLanguageIdAndCompanyIdAndPickupIdAndDeletionIndicator(
                languageId, companyId, pickupId, 0L);

        if (dbNpr.isEmpty()) {
            throw new BadRequestException("Npr with given values : languageId: " + languageId + ", companyId: " + companyId + ", pickupId: " + pickupId +  " doesn't exists");
        }
        return dbNpr.get();

    }


    /**
     *
     * @param findNpr
     * @return
     */
    public List<ReplicaNpr> findNpr(FindNpr findNpr) {
        ReplicaNprSpecification spec = new ReplicaNprSpecification(findNpr);
        List<ReplicaNpr> results = replicaNprRepository.findAll(spec);
        log.info("Found Npr ----> " + results);
        return results;
    }

    /**
     * Adding Npr log details
     * @param pickup1
     * @return
     */

    public Npr addNpr(PickupEntity pickup1) {
        try {
            // Create new NPR
            Npr newNpr = new Npr();
            BeanUtils.copyProperties(pickup1, newNpr, CommonUtils.getNullPropertyNames(pickup1));
            newNpr.setNprId(pickup1.getNprId());
            newNpr.setNprDescription(pickup1.getNprText());
            newNpr.setCreatedOn(new Date());
            newNpr.setUpdatedOn(new Date());
            return nprRepository.save(newNpr);
        } catch (Exception e) {
            throw new BadRequestException("Npr Create Error " + e);
        }



    }
}
