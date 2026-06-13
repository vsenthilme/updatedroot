package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.billingfrequency.AddBillingFrequency;
import com.courier.overc360.api.idmaster.primary.model.billingfrequency.BillingFrequency;
import com.courier.overc360.api.idmaster.primary.repository.BillingFrequencyRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.billingfrequency.FindBillingFrequency;
import com.courier.overc360.api.idmaster.replica.model.billingfrequency.ReplicaBillingFrequency;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaBillingFrequencyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaBillingFrequencySpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BillingFrequencyService {

    @Autowired
    private BillingFrequencyRepository billingFrequencyRepository;

    @Autowired
    private ReplicaBillingFrequencyRepository replicaBillingFrequencyRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    /**
     * @param addBillingFrequency
     * @param loginUserID
     * @return
     */

    public List<BillingFrequency> createBillingFrequency(List<AddBillingFrequency> addBillingFrequency, String loginUserID) {
        List<BillingFrequency> billingFrequencyList = new ArrayList<>();
        try {
            addBillingFrequency.stream().forEach(billingFrequency -> {
                boolean duplicate = billingFrequencyRepository.existsByLanguageIdAndCompanyIdAndBillingFrequencyIdAndDeletionIndicator(
                        billingFrequency.getLanguageId(), billingFrequency.getCompanyId(), billingFrequency.getBillingFrequencyId(), 0L);
                if (duplicate) {
                    throw new BadRequestException("Record is getting Duplicated with the given values");
                }
                BillingFrequency newBillingFrequency = new BillingFrequency();
                BeanUtils.copyProperties(billingFrequency, newBillingFrequency, CommonUtils.getNullPropertyNames(billingFrequency));
                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(newBillingFrequency.getLanguageId(), newBillingFrequency.getCompanyId());
                if (iKeyValuePair != null) {
                    newBillingFrequency.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newBillingFrequency.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                String statusDesc = replicaBillingFrequencyRepository.getStatusDescription(billingFrequency.getStatusId());
                if (statusDesc != null) {
                    newBillingFrequency.setStatusDescription(statusDesc);
                }
                newBillingFrequency.setCreatedBy(loginUserID);
                newBillingFrequency.setCreatedOn(new Date());
                newBillingFrequency.setUpdatedBy(loginUserID);
                newBillingFrequency.setUpdatedOn(new Date());
                BillingFrequency billingFrequency1 = billingFrequencyRepository.save(newBillingFrequency);
                billingFrequencyList.add(billingFrequency1);
            });
        } catch (Exception e) {
            throw new BadRequestException("BillingFrequency Create Error" + e);
        }
        return billingFrequencyList;
    }

    /**
     * @param updateBillingFrequency
     * @param loginUserID
     * @return
     */
    public List<BillingFrequency> updateBillingFrequency(List<AddBillingFrequency> updateBillingFrequency, String loginUserID) {

        List<BillingFrequency> billingFrequencyLists = new ArrayList<>();
        for (AddBillingFrequency updateBilling : updateBillingFrequency) {
            Optional<BillingFrequency> dbBillingFrequency = billingFrequencyRepository.findByLanguageIdAndCompanyIdAndBillingFrequencyIdAndDeletionIndicator(
                    updateBilling.getLanguageId(), updateBilling.getCompanyId(), updateBilling.getBillingFrequencyId(), 0L);
            if (dbBillingFrequency.isPresent()) {
                BillingFrequency newBillingFrequency = dbBillingFrequency.get();
                BeanUtils.copyProperties(updateBilling, newBillingFrequency, CommonUtils.getNullPropertyNames(updateBilling));
                newBillingFrequency.setUpdatedBy(loginUserID);
                newBillingFrequency.setUpdatedOn(new Date());
                BillingFrequency billing = billingFrequencyRepository.save(newBillingFrequency);
                billingFrequencyLists.add(billing);
            }
        }
        return billingFrequencyLists;
    }

    /**
     * @param deleteBillingFrequency
     * @param loginUserID
     */

    public void deleteBillingFrequency(List<BillingFrequency> deleteBillingFrequency, String loginUserID) {
        if (deleteBillingFrequency != null && !deleteBillingFrequency.isEmpty()) {
            log.info("given values to delete BillingFrequency---> {}", deleteBillingFrequency);
            deleteBillingFrequency.parallelStream().forEach(deleteInput -> {
                BillingFrequency dbBillingFrequency = getBillingFrequency(deleteInput.getLanguageId(), deleteInput.getCompanyId(), deleteInput.getBillingFrequencyId());
                dbBillingFrequency.setDeletionIndicator(1L);
                dbBillingFrequency.setUpdatedBy(loginUserID);
                dbBillingFrequency.setUpdatedOn(new Date());
                billingFrequencyRepository.save(dbBillingFrequency);
            });
        }
    }

    /**
     * @param languageId
     * @param companyId
     * @param billingFrequencyId
     * @return
     */

    public BillingFrequency getBillingFrequency(String languageId, String companyId, String billingFrequencyId) {
        Optional<BillingFrequency> dbBillingFrequency = billingFrequencyRepository.findByLanguageIdAndCompanyIdAndBillingFrequencyIdAndDeletionIndicator(
                languageId, companyId, billingFrequencyId, 0L);
        if (dbBillingFrequency.isEmpty()) {
            throw new BadRequestException("BillingFrequency with the given values: LanguageId: " + languageId + " CompanyId " + companyId + " BillingFrequencyId " + billingFrequencyId + " doesn't exists ");
        }
        return dbBillingFrequency.get();
    }

    /**
     * Get All
     *
     * @return
     */
    public List<ReplicaBillingFrequency> getAllBillingFrequency() {
        List<ReplicaBillingFrequency> billingFrequencyList = replicaBillingFrequencyRepository.findAll();
        billingFrequencyList = billingFrequencyList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return billingFrequencyList;
    }

    /**
     * @param languageId
     * @param companyId
     * @param billingFrequencyId
     * @return
     */
    public ReplicaBillingFrequency getReplicaBillingFrequency(String languageId, String companyId, String billingFrequencyId) {
        Optional<ReplicaBillingFrequency> dbReplicaBillingFrequency = replicaBillingFrequencyRepository.findByLanguageIdAndCompanyIdAndBillingFrequencyIdAndDeletionIndicator(
                languageId, companyId, billingFrequencyId, 0L);
        if (dbReplicaBillingFrequency.isEmpty()) {
            throw new BadRequestException("BillingFrequency with the given values: LanguageId: " + languageId + " CompanyId " + companyId + " BillingFrequencyId " + billingFrequencyId + " doesn't exists ");
        }
        return dbReplicaBillingFrequency.get();
    }

    public List<ReplicaBillingFrequency> findBillingFrequency(FindBillingFrequency findBillingFrequency) {
        ReplicaBillingFrequencySpecification spec = new ReplicaBillingFrequencySpecification(findBillingFrequency);
        List<ReplicaBillingFrequency> results = replicaBillingFrequencyRepository.findAll(spec);
        return results;
    }

}
