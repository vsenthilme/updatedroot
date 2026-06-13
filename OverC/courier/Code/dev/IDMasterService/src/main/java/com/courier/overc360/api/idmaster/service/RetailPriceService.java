package com.courier.overc360.api.idmaster.service;


import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.retailPriceList.AddRetailPrice;
import com.courier.overc360.api.idmaster.primary.model.retailPriceList.RetailPrice;
import com.courier.overc360.api.idmaster.primary.repository.RetailPriceRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.retailPriceList.FindRetailPrice;
import com.courier.overc360.api.idmaster.replica.model.retailPriceList.ReplicaRetailPrice;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaRetailPriceRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaRetailPriceSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RetailPriceService {


    @Autowired
    private RetailPriceRepository retailPriceRepository;

    @Autowired
    private ReplicaRetailPriceRepository replicaRetailPriceRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    /**
     *
     * @param addRetailPrices
     * @param loginUserID
     * @return
     */
    public List<RetailPrice> createRetailPrice(List<AddRetailPrice> addRetailPrices, String loginUserID) {
        List<RetailPrice> retailPriceList = new ArrayList<>();
        try {
            addRetailPrices.stream().forEach(retailPrice -> {

                boolean duplicate = retailPriceRepository.existsByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                        retailPrice.getLanguageId(), retailPrice.getCompanyId(), retailPrice.getPartnerId(), retailPrice.getLineNo(), 0L);
                if (duplicate) {
                    throw new BadRequestException("Record is getting Duplicated with the given values");
                }

                // Create new RetailPrice and related entities
                RetailPrice newRetailPrice = new RetailPrice();
                BeanUtils.copyProperties(retailPrice, newRetailPrice, CommonUtils.getNullPropertyNames(retailPrice));
                String statusDesc = replicaStatusRepository.getStatusDescription(retailPrice.getStatusId());
                if (statusDesc != null) {
                    newRetailPrice.setStatusDescription(statusDesc);
                }
                newRetailPrice.setCreatedBy(loginUserID);
                newRetailPrice.setCreatedOn(new Date());
                newRetailPrice.setUpdatedBy(loginUserID);
                newRetailPrice.setUpdatedOn(new Date());
                RetailPrice retailPrice1 = retailPriceRepository.save(newRetailPrice);
                retailPriceList.add(retailPrice1);
            });
        }  catch (Exception e) {
            throw new BadRequestException("RetailPrice Create Error " + e);
        }
        return retailPriceList;
    }


    /**
     *
     * @param updateRetailPrice
     * @param loginUserID
     * @return
     */
    public List<RetailPrice> updateRetailPrice(List<AddRetailPrice> updateRetailPrice, String loginUserID) {
        List<RetailPrice> retailPrices = new ArrayList<>();

        for (AddRetailPrice updateRetail : updateRetailPrice) {
            Optional<RetailPrice> dbRetailPrice = retailPriceRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                    updateRetail.getLanguageId(), updateRetail.getCompanyId(), updateRetail.getPartnerId(), updateRetail.getLineNo(), 0L);

            if (dbRetailPrice.isPresent()) {
                RetailPrice newRetailPrice = dbRetailPrice.get();
                BeanUtils.copyProperties(updateRetail, newRetailPrice, CommonUtils.getNullPropertyNames(updateRetail));
                if (updateRetail.getStatusId() != null && !updateRetail.getStatusId().isEmpty()) {
                    String statusDesc = replicaStatusRepository.getStatusDescription(updateRetail.getStatusId());
                    if (statusDesc != null) {
                        newRetailPrice.setStatusDescription(statusDesc);
                    }
                }
                newRetailPrice.setUpdatedOn(new Date());
                newRetailPrice.setUpdatedBy(loginUserID);
                RetailPrice retailPrice = retailPriceRepository.save(newRetailPrice);
                retailPrices.add(retailPrice);
            }
            else {
                RetailPrice newRetail = new RetailPrice();
                BeanUtils.copyProperties(updateRetail, newRetail, CommonUtils.getNullPropertyNames(updateRetail));
                newRetail.setUpdatedBy(loginUserID);
                newRetail.setUpdatedOn(new Date());
                retailPrices.add(retailPriceRepository.save(newRetail));
            }
        }
        return retailPrices;
    }


    /**
     *
     * @param deleteRetailPrice
     * @param loginUserID
     */
    public void deleteRetailPrice(List<RetailPrice> deleteRetailPrice, String loginUserID) {
        if (deleteRetailPrice != null && !deleteRetailPrice.isEmpty()) {
            log.info("given values to delete retail price --->  {}", deleteRetailPrice);

            deleteRetailPrice.parallelStream().forEach(deleteInput -> {
                RetailPrice dbRetailPrice = getRetailPrice(deleteInput.getLanguageId(), deleteInput.getCompanyId(), deleteInput.getPartnerId(), deleteInput.getLineNo());
                dbRetailPrice.setDeletionIndicator(1L);
                dbRetailPrice.setUpdatedBy(loginUserID);
                dbRetailPrice.setUpdatedOn(new Date());
                retailPriceRepository.save(dbRetailPrice);
            });
        }
    }


    /**
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @return
     */
    private RetailPrice getRetailPrice(String languageId, String companyId, String partnerId, Long lineNo) {
        Optional<RetailPrice> dbRetailPrice = retailPriceRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                languageId, companyId, partnerId, lineNo, 0L);
        if (dbRetailPrice.isEmpty()) {
            throw new BadRequestException("RetailPrice with the given values:  languageaId: " + languageId + ", companyId: " + companyId + ", partnerId: " + partnerId +
                    ", LineNo: " + lineNo +" doesn't exists");
        }
        return dbRetailPrice.get();
    }

    public List<ReplicaRetailPrice> getAllRetailList() {
        List<ReplicaRetailPrice> retailPriceList = replicaRetailPriceRepository.findAll();

        retailPriceList = retailPriceList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return retailPriceList;
    }

    /**
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param lineNo
     * @return
     */
    public ReplicaRetailPrice getRetailPriceId(String languageId, String companyId, String partnerId, Long lineNo) {
        Optional<ReplicaRetailPrice> dbReplicaRetail = replicaRetailPriceRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                languageId, companyId, partnerId, lineNo,  0L);

        if (dbReplicaRetail.isEmpty()) {
            throw new BadRequestException("RetailPrice with given values : languageId: " + languageId + ", companyId: " + companyId + ", partnerId: " + partnerId +
                    ", lineNo: " + lineNo +" doesn't exists");
        }
        return dbReplicaRetail.get();
    }


    /**
     *
     * @param findRetailPrice
     * @return
     */
    public List<ReplicaRetailPrice> findRetail(FindRetailPrice findRetailPrice) {
        ReplicaRetailPriceSpecification spec = new ReplicaRetailPriceSpecification(findRetailPrice);
        List<ReplicaRetailPrice> results = replicaRetailPriceRepository.findAll(spec);
        return results;
    }
}
