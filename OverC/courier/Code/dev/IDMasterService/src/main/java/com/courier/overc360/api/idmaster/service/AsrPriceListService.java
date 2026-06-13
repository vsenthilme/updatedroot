package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.asrpricelist.AddAsrPriceList;
import com.courier.overc360.api.idmaster.primary.model.asrpricelist.AsrPriceList;
import com.courier.overc360.api.idmaster.primary.repository.AsrPriceListRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.asrpriceList.FindAsrPriceList;
import com.courier.overc360.api.idmaster.replica.model.asrpriceList.ReplicaAsrPriceList;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaAsrPriceListRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaAsrPriceListSpecification;
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
public class AsrPriceListService {

    @Autowired
    private AsrPriceListRepository asrPriceListRepository;

    @Autowired
    private ReplicaAsrPriceListRepository replicaAsrPriceListRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    /**
     * @param addAsrPrice
     * @param loginUserID
     * @return
     */

    public List<AsrPriceList> createAsrPrice(List<AddAsrPriceList> addAsrPrice, String loginUserID) {
        List<AsrPriceList> asrPriceList = new ArrayList<>();
        try {
            addAsrPrice.stream().forEach(asrPrice -> {
                boolean duplicate = asrPriceListRepository.existsByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                        asrPrice.getLanguageId(), asrPrice.getCompanyId(), asrPrice.getPartnerId(), asrPrice.getLineNo(), 0L);
                if (duplicate) {
                    throw new BadRequestException("Record is getting Duplicated with the given values");
                }
                AsrPriceList newAsrPrice = new AsrPriceList();
                BeanUtils.copyProperties(asrPrice, newAsrPrice, CommonUtils.getNullPropertyNames(asrPrice));
                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(newAsrPrice.getLanguageId(), newAsrPrice.getCompanyId());
                if (iKeyValuePair != null) {
                    newAsrPrice.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newAsrPrice.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                String statusDesc = replicaAsrPriceListRepository.getStatusDescription(asrPrice.getStatusId());
                if (statusDesc != null) {
                    newAsrPrice.setStatusDescription(statusDesc);
                }
                newAsrPrice.setCreatedBy(loginUserID);
                newAsrPrice.setCreatedOn(new Date());
                newAsrPrice.setUpdatedBy(loginUserID);
                newAsrPrice.setUpdatedOn(new Date());
                AsrPriceList asrPriceList1 = asrPriceListRepository.save(newAsrPrice);
                asrPriceList.add(asrPriceList1);
            });
        } catch (Exception e) {
            throw new BadRequestException("AsrPrice Create Error" + e);
        }
        return asrPriceList;
    }

    /**
     * @param updateAsrPrice
     * @param loginUserID
     * @return
     */
    public List<AsrPriceList> updateAsrPrice(List<AddAsrPriceList> updateAsrPrice, String loginUserID) {

        List<AsrPriceList> asrPriceLists = new ArrayList<>();
        for (AddAsrPriceList asr : updateAsrPrice) {
            asrPriceListRepository.deleteAsr(asr.getCompanyId(), asr.getLanguageId(), asr.getPartnerId());
            log.info("ASR PriceList Delete SuccessFully " + asr.getPartnerId());
        }
        for (AddAsrPriceList asrPriceList : updateAsrPrice) {
            AsrPriceList newAsr = new AsrPriceList();
            BeanUtils.copyProperties(asrPriceList, newAsr, CommonUtils.getNullPropertyNames(asrPriceList));
            newAsr.setUpdatedBy(loginUserID);
            newAsr.setUpdatedOn(new Date());
            asrPriceLists.add(asrPriceListRepository.save(newAsr));
        }
        return asrPriceLists;
    }

    /**
     * @param deleteAsr
     * @param loginUserID
     */

    public void deleteAsrPrice(List<AsrPriceList> deleteAsr, String loginUserID) {
        if (deleteAsr != null && !deleteAsr.isEmpty()) {
            log.info("given values to delete Asr Price---> {}", deleteAsr);
            deleteAsr.parallelStream().forEach(deleteInput -> {
                AsrPriceList dbAsrPrice = getAsrPrice(deleteInput.getLanguageId(), deleteInput.getCompanyId(), deleteInput.getPartnerId(), deleteInput.getLineNo());
                dbAsrPrice.setDeletionIndicator(1L);
                dbAsrPrice.setUpdatedBy(loginUserID);
                dbAsrPrice.setUpdatedOn(new Date());
                asrPriceListRepository.save(dbAsrPrice);
            });
        }
    }

    /**
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param lineNo
     * @return
     */

    public AsrPriceList getAsrPrice(String languageId, String companyId, String partnerId, Long lineNo) {
        Optional<AsrPriceList> dbAsrPrice = asrPriceListRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                languageId, companyId, partnerId, lineNo, 0L);
        if (dbAsrPrice.isEmpty()) {
            throw new BadRequestException("AsrPrice with the given values: LanguageId: " + languageId + " CompanyId " + companyId + " PartnerId " + partnerId + " LineNo " + lineNo + " doesn't exists ");
        }
        return dbAsrPrice.get();
    }

    /**
     * Get All
     *
     * @return
     */
    public List<ReplicaAsrPriceList> getAllAsrPrice() {
        List<ReplicaAsrPriceList> asrPriceList = replicaAsrPriceListRepository.findAll();
        asrPriceList = asrPriceList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return asrPriceList;
    }

    /**
     * @param languageId
     * @param companyId
     * @param partnerId
     * @param lineNo
     * @return
     */
    public ReplicaAsrPriceList getReplicaAsrPrice(String languageId, String companyId, String partnerId, Long lineNo) {
        Optional<ReplicaAsrPriceList> dbReplicaAsr = replicaAsrPriceListRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                languageId, companyId, partnerId, lineNo, 0L);
        if (dbReplicaAsr.isEmpty()) {
            throw new BadRequestException("AsrPrice with the given values: LanguageId: " + languageId + " CompanyId " + companyId + " PartnerId " + partnerId + " doesn't exists ");
        }
        return dbReplicaAsr.get();
    }

    public List<ReplicaAsrPriceList> findAsrPrice(FindAsrPriceList findAsrPriceList) {
        ReplicaAsrPriceListSpecification spec = new ReplicaAsrPriceListSpecification(findAsrPriceList);
        List<ReplicaAsrPriceList> results = replicaAsrPriceListRepository.findAll(spec);
        return results;
    }

}
