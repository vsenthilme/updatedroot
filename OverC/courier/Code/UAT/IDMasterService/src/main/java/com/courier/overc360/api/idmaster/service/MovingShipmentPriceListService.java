package com.courier.overc360.api.idmaster.service;

import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.movingshipmentpricelist.AddMovingShipmentPriceList;
import com.courier.overc360.api.idmaster.primary.model.movingshipmentpricelist.MovingShipmentPriceList;
import com.courier.overc360.api.idmaster.primary.repository.MovingShipmentPriceListRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.movingshipmentpricelist.FindMovingShipmentPriceList;
import com.courier.overc360.api.idmaster.replica.model.movingshipmentpricelist.ReplicaMovingShipmentPriceList;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaMovingShipmentPriceListRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaStatusRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaMovingShipmentPriceListSpecification;
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
public class MovingShipmentPriceListService {


    @Autowired
    private MovingShipmentPriceListRepository movingShipmentPriceListRepository;

    @Autowired
    private ReplicaMovingShipmentPriceListRepository replicaMovingShipmentPriceListRepository;

    @Autowired
    private ReplicaCompanyRepository replicaCompanyRepository;

    @Autowired
    private ReplicaStatusRepository replicaStatusRepository;

    /**
     * @param addMovingShipmentPriceList
     * @param loginUserID
     * @return
     */

    public List<MovingShipmentPriceList> createMovingShipmentPriceList(List<AddMovingShipmentPriceList> addMovingShipmentPriceList, String loginUserID) {
        List<MovingShipmentPriceList> movingShipmentPriceList = new ArrayList<>();
        try {
            addMovingShipmentPriceList.stream().forEach(movingShipmentPrice -> {
                boolean duplicate = movingShipmentPriceListRepository.existsByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                        movingShipmentPrice.getLanguageId(), movingShipmentPrice.getCompanyId(), movingShipmentPrice.getPartnerId(), movingShipmentPrice.getLineNo(), 0L);
                if (duplicate) {
                    throw new BadRequestException("Record is getting Duplicated with the given values");
                }
                MovingShipmentPriceList newMovingShipmentPriceList = new MovingShipmentPriceList();
                BeanUtils.copyProperties(movingShipmentPrice, newMovingShipmentPriceList, CommonUtils.getNullPropertyNames(movingShipmentPrice));
                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(newMovingShipmentPriceList.getLanguageId(), newMovingShipmentPriceList.getCompanyId());
                if (iKeyValuePair != null) {
                    newMovingShipmentPriceList.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newMovingShipmentPriceList.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                String statusDesc = replicaMovingShipmentPriceListRepository.getStatusDescription(movingShipmentPrice.getStatusId());
                if (statusDesc != null) {
                    newMovingShipmentPriceList.setStatusDescription(statusDesc);
                }
                newMovingShipmentPriceList.setCreatedBy(loginUserID);
                newMovingShipmentPriceList.setCreatedOn(new Date());
                newMovingShipmentPriceList.setUpdatedBy(loginUserID);
                newMovingShipmentPriceList.setUpdatedOn(new Date());
                MovingShipmentPriceList movingShipmentPriceList1 = movingShipmentPriceListRepository.save(newMovingShipmentPriceList);
                movingShipmentPriceList.add(movingShipmentPriceList1);
            });
        } catch (Exception e) {
            throw new BadRequestException("MovingShipmentPriceList Create Error" + e);
        }
        return movingShipmentPriceList;
    }

    /**
     * @param updateMovingShipmentPriceList
     * @param loginUserID
     * @return
     */
    public List<MovingShipmentPriceList> updateMovingShipmentPriceList(List<AddMovingShipmentPriceList> updateMovingShipmentPriceList, String loginUserID) {

        List<MovingShipmentPriceList> movingShipmentPriceLists = new ArrayList<>();
        for (AddMovingShipmentPriceList updateMovingShipment : updateMovingShipmentPriceList) {
            Optional<MovingShipmentPriceList> dbMovingShipmentPriceList = movingShipmentPriceListRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                    updateMovingShipment.getLanguageId(), updateMovingShipment.getCompanyId(), updateMovingShipment.getPartnerId(), updateMovingShipment.getLineNo(), 0L);
            if (dbMovingShipmentPriceList.isPresent()) {
                MovingShipmentPriceList newMovingShipmentPriceList = dbMovingShipmentPriceList.get();
                BeanUtils.copyProperties(updateMovingShipment, newMovingShipmentPriceList, CommonUtils.getNullPropertyNames(updateMovingShipment));
                if (updateMovingShipment.getStatusId() != null && !updateMovingShipment.getStatusId().isEmpty()) {
                    String statusDesc = replicaStatusRepository.getStatusDescription(updateMovingShipment.getStatusId());
                    if (statusDesc != null) {
                        newMovingShipmentPriceList.setStatusDescription(statusDesc);
                    }
                }
                newMovingShipmentPriceList.setUpdatedBy(loginUserID);
                newMovingShipmentPriceList.setUpdatedOn(new Date());
                MovingShipmentPriceList movingShipmentPriceList = movingShipmentPriceListRepository.save(newMovingShipmentPriceList);
                movingShipmentPriceLists.add(movingShipmentPriceList);
            } else {
                MovingShipmentPriceList newMoving = new MovingShipmentPriceList();
                BeanUtils.copyProperties(updateMovingShipment, newMoving, CommonUtils.getNullPropertyNames(updateMovingShipment));
                newMoving.setUpdatedBy(loginUserID);
                newMoving.setUpdatedOn(new Date());
                movingShipmentPriceLists.add(movingShipmentPriceListRepository.save(newMoving));
            }
        }
        return movingShipmentPriceLists;
    }

    /**
     * @param deleteMovingShipmentPriceList
     * @param loginUserID
     */

    public void deleteMovingShipmentPriceList(List<MovingShipmentPriceList> deleteMovingShipmentPriceList, String loginUserID) {
        if (deleteMovingShipmentPriceList != null && !deleteMovingShipmentPriceList.isEmpty()) {
            log.info("given values to delete MovingShipmentPriceList ---> {}", deleteMovingShipmentPriceList);
            deleteMovingShipmentPriceList.parallelStream().forEach(deleteInput -> {
                MovingShipmentPriceList dbMovingShipmentPriceList = getMovingShipmentPriceList(deleteInput.getLanguageId(), deleteInput.getCompanyId(), deleteInput.getPartnerId(), deleteInput.getLineNo());
                dbMovingShipmentPriceList.setDeletionIndicator(1L);
                dbMovingShipmentPriceList.setUpdatedBy(loginUserID);
                dbMovingShipmentPriceList.setUpdatedOn(new Date());
                movingShipmentPriceListRepository.save(dbMovingShipmentPriceList);
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

    public MovingShipmentPriceList getMovingShipmentPriceList(String languageId, String companyId, String partnerId, Long lineNo) {
        Optional<MovingShipmentPriceList> dbMovingShipmentPriceList = movingShipmentPriceListRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                languageId, companyId, partnerId, lineNo, 0L);
        if (dbMovingShipmentPriceList.isEmpty()) {
            throw new BadRequestException("MovingShipmentPriceList with the given values: LanguageId: " + languageId + " CompanyId " + companyId + " PartnerId " + partnerId +
                    " LineNo " + lineNo +" doesn't exists ");
        }
        return dbMovingShipmentPriceList.get();
    }

    /**
     * Get All
     *
     * @return
     */
    public List<ReplicaMovingShipmentPriceList> getAllMovingShipmentPriceList() {
        List<ReplicaMovingShipmentPriceList> movingShipmentPriceList = replicaMovingShipmentPriceListRepository.findAll();
        movingShipmentPriceList = movingShipmentPriceList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return movingShipmentPriceList;
    }

    /**
     * @param languageId
     * @param companyId
     * @param partnerId
     * @return
     */
    public ReplicaMovingShipmentPriceList getReplicaMovingShipmentPrice(String languageId, String companyId, String partnerId, Long lineNo) {
        Optional<ReplicaMovingShipmentPriceList> dbReplicaMovingShipment = replicaMovingShipmentPriceListRepository.findByLanguageIdAndCompanyIdAndPartnerIdAndLineNoAndDeletionIndicator(
                languageId, companyId, partnerId, lineNo, 0L);
        if (dbReplicaMovingShipment.isEmpty()) {
            throw new BadRequestException("MovingShipmentPriceList with the given values: LanguageId: " + languageId + " CompanyId " + companyId + " PartnerId " + partnerId +
                    " LineNo " + lineNo + " doesn't exists ");
        }
        return dbReplicaMovingShipment.get();
    }

    public List<ReplicaMovingShipmentPriceList> findMovingShipmentPriceList(FindMovingShipmentPriceList findMovingShipmentPriceList) {
        ReplicaMovingShipmentPriceListSpecification spec = new ReplicaMovingShipmentPriceListSpecification(findMovingShipmentPriceList);
        List<ReplicaMovingShipmentPriceList> results = replicaMovingShipmentPriceListRepository.findAll(spec);
        return results;
    }

}
