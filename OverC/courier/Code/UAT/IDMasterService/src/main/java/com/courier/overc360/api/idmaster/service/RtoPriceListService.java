package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.rtopricelist.AddRtoPrice;
import com.courier.overc360.api.idmaster.primary.model.rtopricelist.RtoPriceList;
import com.courier.overc360.api.idmaster.primary.repository.RtoPriceRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.rtopricelist.FindRtoPrice;
import com.courier.overc360.api.idmaster.replica.model.rtopricelist.ReplicaRtoPrice;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaRtoPriceRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaRtoPriceSpecification;
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
public class RtoPriceListService {

    @Autowired
    private RtoPriceRepository rtoPriceRepository;

    @Autowired
    private ReplicaRtoPriceRepository replicaRtoPriceRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    /**
     * @param addRtoPrice
     * @param loginUserID
     * @return
     */

    public List<RtoPriceList> createRtoPrice(List<AddRtoPrice> addRtoPrice, String loginUserID) {
        List<RtoPriceList> rtoPriceList = new ArrayList<>();
        try {
            addRtoPrice.stream().forEach(rtoPrice -> {
                boolean duplicate = rtoPriceRepository.existsByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                        rtoPrice.getLanguageId(), rtoPrice.getCompanyId(), rtoPrice.getPartnerId(), rtoPrice.getLineNo(), 0L);
                if (duplicate) {
                    throw new BadRequestException("Record is getting Duplicated with the given values");
                }
                RtoPriceList newRtoPrice = new RtoPriceList();
                BeanUtils.copyProperties(rtoPrice, newRtoPrice, CommonUtils.getNullPropertyNames(rtoPrice));
                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(newRtoPrice.getLanguageId(), newRtoPrice.getCompanyId());
                if (iKeyValuePair != null) {
                    newRtoPrice.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newRtoPrice.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                String statusDesc = replicaRtoPriceRepository.getStatusDescription(rtoPrice.getStatusId());
                if (statusDesc != null) {
                    newRtoPrice.setStatusDescription(statusDesc);
                }
                newRtoPrice.setCreatedBy(loginUserID);
                newRtoPrice.setCreatedOn(new Date());
                newRtoPrice.setUpdatedBy(loginUserID);
                newRtoPrice.setUpdatedOn(new Date());
                RtoPriceList rtoPriceList1 = rtoPriceRepository.save(newRtoPrice);
                rtoPriceList.add(rtoPriceList1);
            });
        } catch (Exception e) {
            throw new BadRequestException("RtoPrice Create Error" + e);
        }
        return rtoPriceList;
    }

    /**
     * @param updateRtoPrice
     * @param loginUserID
     * @return
     */
    public List<RtoPriceList> updateRtoPrice(List<AddRtoPrice> updateRtoPrice, String loginUserID) {

        List<RtoPriceList> rtoPriceLists = new ArrayList<>();
        for (AddRtoPrice updateRto : updateRtoPrice) {
            Optional<RtoPriceList> dbRtoprice = rtoPriceRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                    updateRto.getLanguageId(), updateRto.getCompanyId(), updateRto.getPartnerId(), updateRto.getLineNo(), 0L);
            if (dbRtoprice.isPresent()) {
                RtoPriceList newRto = dbRtoprice.get();
                BeanUtils.copyProperties(updateRto, newRto, CommonUtils.getNullPropertyNames(updateRto));
                if (updateRto.getStatusId() != null && !updateRto.getStatusId().isEmpty()) {
                    String statusDesc = replicaStatusRepository.getStatusDescription(updateRto.getStatusId());
                    if (statusDesc != null) {
                        newRto.setStatusDescription(statusDesc);
                    }
                }
                newRto.setUpdatedBy(loginUserID);
                newRto.setUpdatedOn(new Date());
                RtoPriceList rto = rtoPriceRepository.save(newRto);
                rtoPriceLists.add(rto);
            }
            else {
                RtoPriceList newRto = new RtoPriceList();
                BeanUtils.copyProperties(updateRto, newRto, CommonUtils.getNullPropertyNames(updateRto));
                newRto.setUpdatedBy(loginUserID);
                newRto.setUpdatedOn(new Date());
                rtoPriceLists.add(rtoPriceRepository.save(newRto));
            }
        }
        return rtoPriceLists;
    }

    /**
     * @param deleteRto
     * @param loginUserID
     */

    public void deleteRtoPrice(List<RtoPriceList> deleteRto, String loginUserID) {
        if (deleteRto != null && !deleteRto.isEmpty()) {
            log.info("given values to delete Rto Price---> {}", deleteRto);
            deleteRto.parallelStream().forEach(deleteInput -> {
                RtoPriceList dbRtoPrice = getRtoPrice(deleteInput.getLanguageId(), deleteInput.getCompanyId(), deleteInput.getPartnerId(), deleteInput.getLineNo());
                dbRtoPrice.setDeletionIndicator(1L);
                dbRtoPrice.setUpdatedBy(loginUserID);
                dbRtoPrice.setUpdatedOn(new Date());
                rtoPriceRepository.save(dbRtoPrice);
            });
        }
    }

    /**
     * @param languageId
     * @param companyId
     * @param partnerId
     * @return
     */

    public RtoPriceList getRtoPrice(String languageId, String companyId, String partnerId, Long lineNo) {
        Optional<RtoPriceList> dbRtoPrice = rtoPriceRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                languageId, companyId, partnerId, lineNo, 0L);
        if (dbRtoPrice.isEmpty()) {
            throw new BadRequestException("RtoPrice with the given values: LanguageId: " + languageId + " CompanyId " + companyId + " PartnerId " + partnerId +
                    " LineNo " + lineNo + " doesn't exists ");
        }
        return dbRtoPrice.get();
    }

    /**
     * Get All
     *
     * @return
     */
    public List<ReplicaRtoPrice> getAllRtoPrice() {
        List<ReplicaRtoPrice> rtoPriceList = replicaRtoPriceRepository.findAll();
        rtoPriceList = rtoPriceList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return rtoPriceList;
    }

    /**
     * @param languageId
     * @param companyId
     * @param partnerId
     * @return
     */
    public ReplicaRtoPrice getReplicaRtoPrice(String languageId, String companyId, String partnerId, Long lineNo) {
        Optional<ReplicaRtoPrice> dbReplicaRto = replicaRtoPriceRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                languageId, companyId, partnerId, lineNo, 0L);
        if (dbReplicaRto.isEmpty()) {
            throw new BadRequestException("RtoPrice with the given values: LanguageId: " + languageId + " CompanyId " + companyId + " PartnerId " + partnerId +
                    " LineNo " + lineNo + " doesn't exists ");
        }
        return dbReplicaRto.get();
    }

    public List<ReplicaRtoPrice> findRtoPrice(FindRtoPrice findRtoPrice) {
        ReplicaRtoPriceSpecification spec = new ReplicaRtoPriceSpecification(findRtoPrice);
        List<ReplicaRtoPrice> results = replicaRtoPriceRepository.findAll(spec);
        return results;
    }

}
