package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.codpricelist.CodPriceList;
import com.courier.overc360.api.idmaster.primary.model.fulFillmentPrice.AddFulfillmentPrice;
import com.courier.overc360.api.idmaster.primary.model.fulFillmentPrice.FulfillmentPrice;
import com.courier.overc360.api.idmaster.primary.repository.FulfillmentPriceRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.fulFillmentPrice.FindFulfillmentPrice;
import com.courier.overc360.api.idmaster.replica.model.fulFillmentPrice.ReplicaFulfillmentPrice;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaFulfillmentPriceRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaFulfillmentPriceSpecification;
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
public class FulfillmentPriceService {

    @Autowired
    private FulfillmentPriceRepository fulfillmentPriceRepository;

    @Autowired
    private ReplicaFulfillmentPriceRepository replicaFulfillmentPriceRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;


    /**
     *
     * @param addFulfillmentPrices
     * @param loginUserID
     * @return
     */
    public List<FulfillmentPrice> createFulfillmentPrice(List<AddFulfillmentPrice> addFulfillmentPrices, String loginUserID) {
        List<FulfillmentPrice> fulfillmentPriceList = new ArrayList<>();
        try {
            addFulfillmentPrices.stream().forEach(fulfillmentPrice -> {

                boolean duplicate = fulfillmentPriceRepository.existsByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                        fulfillmentPrice.getLanguageId(), fulfillmentPrice.getCompanyId(), fulfillmentPrice.getPartnerId(), fulfillmentPrice.getLineNo(), 0L);
                if (duplicate) {
                    throw new BadRequestException("Record is getting Duplicated with the given values");
                }

                // Create new Fulfillment price and related entities
                FulfillmentPrice newFulfillment = new FulfillmentPrice();
                BeanUtils.copyProperties(fulfillmentPrice, newFulfillment, CommonUtils.getNullPropertyNames(fulfillmentPrice));

                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(newFulfillment.getLanguageId(), newFulfillment.getCompanyId());
                if (iKeyValuePair != null) {
                    newFulfillment.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newFulfillment.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                String statusDesc = replicaFulfillmentPriceRepository.getStatusDescription(fulfillmentPrice.getStatusId());
                if (statusDesc != null) {
                    newFulfillment.setStatusDescription(statusDesc);
                }

                newFulfillment.setCreatedBy(loginUserID);
                newFulfillment.setCreatedOn(new Date());
                newFulfillment.setUpdatedBy(loginUserID);
                newFulfillment.setUpdatedOn(new Date());
                FulfillmentPrice fufillmentCreate = fulfillmentPriceRepository.save(newFulfillment);
                fulfillmentPriceList.add(fufillmentCreate);
            });
        }  catch (Exception e) {
            throw new BadRequestException("FulfillmentPrice Create Error " + e);
        }
        return fulfillmentPriceList;
    }


    /**
     *
     * @param updateFulfillment
     * @param loginUserID
     * @return
     */
    public List<FulfillmentPrice> updateFulfillment(List<AddFulfillmentPrice> updateFulfillment, String loginUserID) {
        List<FulfillmentPrice> fulfillmentPriceList = new ArrayList<>();

        for (AddFulfillmentPrice updateFulfillmentPrice : updateFulfillment) {
            Optional<FulfillmentPrice> dbFulfillment = fulfillmentPriceRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                    updateFulfillmentPrice.getLanguageId(), updateFulfillmentPrice.getCompanyId(), updateFulfillmentPrice.getPartnerId(), updateFulfillmentPrice.getLineNo(), 0L);

            if (dbFulfillment.isPresent()) {
                FulfillmentPrice newFulfillment = dbFulfillment.get();
                BeanUtils.copyProperties(updateFulfillmentPrice, newFulfillment, CommonUtils.getNullPropertyNames(updateFulfillmentPrice));

                String statusDesc = replicaStatusRepository.getStatusDescription(updateFulfillmentPrice.getStatusId());
                if (statusDesc != null) {
                    newFulfillment.setStatusDescription(statusDesc);
                }
                newFulfillment.setUpdatedOn(new Date());
                newFulfillment.setUpdatedBy(loginUserID);
                FulfillmentPrice fulfillmentPrice = fulfillmentPriceRepository.save(newFulfillment);
                fulfillmentPriceList.add(fulfillmentPrice);
            }
            else {
                FulfillmentPrice newFulFill = new FulfillmentPrice();
                BeanUtils.copyProperties(updateFulfillmentPrice, newFulFill, CommonUtils.getNullPropertyNames(updateFulfillmentPrice));
                newFulFill.setUpdatedBy(loginUserID);
                newFulFill.setUpdatedOn(new Date());
                fulfillmentPriceList.add(fulfillmentPriceRepository.save(newFulFill));
            }
        }
        return fulfillmentPriceList;
    }


    /**
     *
     * @param deleteFulfillment
     * @param loginUserID
     */
    public void deleteFulfillmentList(List<FulfillmentPrice> deleteFulfillment, String loginUserID) {
        if (deleteFulfillment != null && !deleteFulfillment.isEmpty()) {
            log.info("given values to delete payment type --->  {}", deleteFulfillment);

            deleteFulfillment.parallelStream().forEach(deleteInput -> {
                FulfillmentPrice dbFulfillment = getFulfillment(deleteInput.getLanguageId(), deleteInput.getCompanyId(), deleteInput.getPartnerId(), deleteInput.getLineNo());
                dbFulfillment.setDeletionIndicator(1L);
                dbFulfillment.setUpdatedBy(loginUserID);
                dbFulfillment.setUpdatedOn(new Date());
                fulfillmentPriceRepository.save(dbFulfillment);
            });
        }
    }


    /**
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param lineNo
     * @return
     */
    private FulfillmentPrice getFulfillment(String languageId, String companyId, String partnerId, Long lineNo) {
        Optional<FulfillmentPrice> dbFulfillment = fulfillmentPriceRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                languageId, companyId, partnerId, lineNo, 0L);
        if (dbFulfillment.isEmpty()) {
            throw new BadRequestException("Fulfillment with the given values:  languageaId: " + languageId + ", companyId: " + companyId + ", partnerId: " + partnerId
                    + ", lineNo: " + lineNo +" doesn't exists");
        }
        return dbFulfillment.get();
    }

    public List<ReplicaFulfillmentPrice> getAllFulfillment() {
        List<ReplicaFulfillmentPrice> fulfillmentList = replicaFulfillmentPriceRepository.findAll();

        fulfillmentList = fulfillmentList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return fulfillmentList;
    }


    /**
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @return
     */
    public ReplicaFulfillmentPrice getFulfillmentid(String languageId, String companyId, String partnerId, Long lineNo) {
        Optional<ReplicaFulfillmentPrice> dbFulfillment = replicaFulfillmentPriceRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                languageId, companyId, partnerId, lineNo, 0L);

        if (dbFulfillment.isEmpty()) {
            throw new BadRequestException("Fulfillment with given values : languageId: " + languageId + ", companyId: " + companyId + ", partnerId: " + partnerId
                    + ", lineNo: " + lineNo +" doesn't exists");
        }
        return dbFulfillment.get();

    }

    /**
     *
     * @param findFulfillmentPrice
     * @return
     */
    public List<ReplicaFulfillmentPrice> findFulfillmentList(FindFulfillmentPrice findFulfillmentPrice) {
        ReplicaFulfillmentPriceSpecification spec = new ReplicaFulfillmentPriceSpecification(findFulfillmentPrice);
        List<ReplicaFulfillmentPrice> results = replicaFulfillmentPriceRepository.findAll(spec);
        return results;
    }
}
