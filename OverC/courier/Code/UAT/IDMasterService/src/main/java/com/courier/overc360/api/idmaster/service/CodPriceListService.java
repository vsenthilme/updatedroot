package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.asrpricelist.AsrPriceList;
import com.courier.overc360.api.idmaster.primary.model.codpricelist.AddCodPriceList;
import com.courier.overc360.api.idmaster.primary.model.codpricelist.CodPriceList;
import com.courier.overc360.api.idmaster.primary.repository.CodPriceListRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.codpricelist.FindCodPriceList;
import com.courier.overc360.api.idmaster.replica.model.codpricelist.ReplicaCodPriceList;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCodPriceListRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaCodPriceListSpecification;
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
public class CodPriceListService {

    @Autowired
    private CodPriceListRepository codPriceListRepository;

    @Autowired
    private ReplicaCodPriceListRepository replicaCodPriceListRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    /*---------------------------------PRIMARY---------------------------------------*/

    /**
     * Get CodPriceList
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @return
     */
    public CodPriceList getCodPriceList(String languageId, String companyId, String partnerId, Long lineNo) {

        Optional<CodPriceList> dbCodPriceList = codPriceListRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                languageId, companyId, partnerId, lineNo, 0L
        );

        if (dbCodPriceList.isEmpty()) {
            String errMsg = "The given values - languageId: " + languageId + ", companyId: " + companyId + ", partnerId: " + partnerId
                    + ", lineNo: " + lineNo + " and doesn't exists";
            throw new BadRequestException(errMsg);
        }
        return dbCodPriceList.get();
    }

    /**
     * Create Cod
     *
     * @param addCodPriceList
     * @param loginUserID
     * @return
     */
    public CodPriceList createCodPrice(AddCodPriceList addCodPriceList, String loginUserID) {

        try {

            // Checking if duplicate Record
            if (addCodPriceList.getPartnerId() != null) {
                boolean duplicate = codPriceListRepository.existsByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                        addCodPriceList.getLanguageId(), addCodPriceList.getCompanyId(), addCodPriceList.getPartnerId(), addCodPriceList.getLineNo(), 0L
                );

                if (duplicate) {
                    throw new BadRequestException("Record is getting duplicated with the given values");
                }
            }

            // Create new CodPriceList
            CodPriceList newCodPriceList = new CodPriceList();
            BeanUtils.copyProperties(addCodPriceList, newCodPriceList, CommonUtils.getNullPropertyNames(addCodPriceList));

            IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(newCodPriceList.getLanguageId(), newCodPriceList.getCompanyId());
            if (iKeyValuePair != null) {
                newCodPriceList.setLanguageDescription(iKeyValuePair.getLangDesc());
                newCodPriceList.setCompanyName(iKeyValuePair.getCompanyDesc());
            }
            String statusDesc = replicaCodPriceListRepository.getStatusDescription(addCodPriceList.getStatusId());
            if (statusDesc != null) {
                newCodPriceList.setStatusDescription(statusDesc);
            }

            newCodPriceList.setCreatedBy(loginUserID);
            newCodPriceList.setCreatedOn(new Date());
            newCodPriceList.setUpdatedBy(loginUserID);
            newCodPriceList.setUpdatedOn(new Date());

            return codPriceListRepository.save(newCodPriceList);
        } catch (Exception e) {
            throw new BadRequestException("CodPriceList Create Error " + e);
        }
    }

    /**
     * Create CodPriceList
     *
     * @param addCodPriceLists
     * @param loginUserID
     * @return
     */
    public List<CodPriceList> createCodPriceList(List<AddCodPriceList> addCodPriceLists, String loginUserID) {

        List<CodPriceList> codPriceLists = new ArrayList<>();

        try {
            addCodPriceLists.stream().forEach(cod -> {
                AddCodPriceList newCodPriceList = new AddCodPriceList();
                BeanUtils.copyProperties(cod, newCodPriceList);
                codPriceLists.add(createCodPrice(newCodPriceList, loginUserID));
            });
        } catch (Exception e) {
            throw new BadRequestException("CodPrice List Create Error " + e);
        }
        return codPriceLists;
    }

    /**
     * Update CodPriceList
     *
     * @param updateCodPriceList
     * @param loginUserID
     * @return
     */
    public List<CodPriceList> updateCodPriceList(List<CodPriceList> updateCodPriceList, String loginUserID) {

        List<CodPriceList> codPriceLists = new ArrayList<>();

        for (CodPriceList updateCodPrice : updateCodPriceList) {
            Optional<CodPriceList> dbCodPrice = codPriceListRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                    updateCodPrice.getLanguageId(), updateCodPrice.getCompanyId(), updateCodPrice.getPartnerId(), updateCodPrice.getLineNo(),0L
            );

            if (dbCodPrice.isPresent()) {
                CodPriceList newCodPriceList = new CodPriceList();
                BeanUtils.copyProperties(updateCodPrice, newCodPriceList, CommonUtils.getNullPropertyNames(updateCodPrice));
                if (updateCodPrice.getStatusId() != null && !updateCodPrice.getStatusId().isEmpty()) {
                    String statusDesc = replicaStatusRepository.getStatusDescription(updateCodPrice.getStatusId());
                    if (statusDesc != null) {
                        newCodPriceList.setStatusDescription(statusDesc);
                    }
                }
                newCodPriceList.setUpdatedBy(loginUserID);
                newCodPriceList.setUpdatedOn(new Date());

                CodPriceList codList = codPriceListRepository.save(newCodPriceList);
                codPriceLists.add(codList);
            }else {
                CodPriceList newCod = new CodPriceList();
                BeanUtils.copyProperties(updateCodPrice, newCod, CommonUtils.getNullPropertyNames(updateCodPrice));
                newCod.setUpdatedBy(loginUserID);
                newCod.setUpdatedOn(new Date());
                codPriceLists.add(codPriceListRepository.save(newCod));
            }
        }
        return codPriceLists;
    }

    /**
     * Delete CodPriceList
     *
     * @param deleteCodPriceList
     * @param loginUserID
     */
    public void deleteCodPriceList(List<CodPriceList> deleteCodPriceList, String loginUserID) {

        if (deleteCodPriceList != null && !deleteCodPriceList.isEmpty()) {
            deleteCodPriceList.stream().forEach(deleteInput -> {
                CodPriceList dbCodPrice = getCodPriceList(deleteInput.getLanguageId(), deleteInput.getCompanyId(), deleteInput.getPartnerId(), deleteInput.getLineNo());
                dbCodPrice.setDeletionIndicator(1L);
                dbCodPrice.setUpdatedBy(loginUserID);
                dbCodPrice.setUpdatedOn(new Date());

                codPriceListRepository.save(dbCodPrice);
            });
        }
    }

    /*-------------------------------------------REPLICA--------------------------------*/

    public List<ReplicaCodPriceList> getAllCodPriceList() {
        List<ReplicaCodPriceList> codPriceList = replicaCodPriceListRepository.findAll();

        codPriceList = codPriceList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return codPriceList;
    }

    /**
     * Get ReplicaCodPriceList
     *
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param lineNo
     * @return
     */
    public ReplicaCodPriceList getReplicaCodPriceList(String languageId, String companyId, String partnerId, Long lineNo) {

        Optional<ReplicaCodPriceList> dbCodPrice = replicaCodPriceListRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                languageId, companyId, partnerId, lineNo,0L
        );

        if (dbCodPrice.isEmpty()) {
            throw new BadRequestException("CodPriceList with given values : languageId: " + languageId + ", companyId: " + companyId + ", partnerId: " + partnerId
                    + ", lineNo: " + lineNo +" doesn't exists");
        }
        return dbCodPrice.get();
    }

    /**
     * Find CodPriceList
     *
     * @param findCodPriceList
     * @return
     */
    public List<ReplicaCodPriceList> findCodPriceList(FindCodPriceList findCodPriceList) {
        ReplicaCodPriceListSpecification spec = new ReplicaCodPriceListSpecification(findCodPriceList);
        List<ReplicaCodPriceList> results = replicaCodPriceListRepository.findAll(spec);
        return results;
    }
}
