package com.courier.overc360.api.idmaster.service;


import com.courier.overc360.api.idmaster.controller.exception.BadRequestException;
import com.courier.overc360.api.idmaster.primary.model.paymentType.AddPaymentType;
import com.courier.overc360.api.idmaster.primary.model.paymentType.PaymentType;
import com.courier.overc360.api.idmaster.primary.model.timeslot.AddTimeSlot;
import com.courier.overc360.api.idmaster.primary.model.timeslot.TimeSlot;
import com.courier.overc360.api.idmaster.primary.repository.PaymentTypeRepository;
import com.courier.overc360.api.idmaster.primary.util.CommonUtils;
import com.courier.overc360.api.idmaster.replica.model.IKeyValuePair;
import com.courier.overc360.api.idmaster.replica.model.paymenttype.FindPaymentType;
import com.courier.overc360.api.idmaster.replica.model.paymenttype.ReplicaPaymentType;
import com.courier.overc360.api.idmaster.replica.model.timeslot.ReplicaTimeSlot;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaCompanyRepository;
import com.courier.overc360.api.idmaster.replica.repository.ReplicaPaymentTypeRepository;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaPaymentTypeSpecification;
import com.courier.overc360.api.idmaster.replica.repository.specification.ReplicaTimeslotSpecification;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
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
public class PaymentTypeService {

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    @Autowired
    private ReplicaPaymentTypeRepository replicaPaymentTypeRepository;

    @Autowired
    ReplicaCompanyRepository replicaCompanyRepository;

    /**
     *
     * @param addPaymentTypes
     * @param loginUserID
     * @return
     */
    public List<PaymentType> createPaymentType(List<AddPaymentType> addPaymentTypes, String loginUserID) {
        List<PaymentType> paymentTypes = new ArrayList<>();
        try {
            addPaymentTypes.stream().forEach(paymentType -> {

                boolean duplicate = paymentTypeRepository.existsByLanguageIdAndCompanyIdAndPaymentTypeIdAndDeletionIndicator(
                        paymentType.getLanguageId(), paymentType.getCompanyId(), paymentType.getPaymentTypeId(), 0L);
                if (duplicate) {
                    throw new BadRequestException("Record is getting Duplicated with the given values");
                }

                // Create new PaymentType and related entities
                PaymentType newPaymentType = new PaymentType();
                BeanUtils.copyProperties(paymentType, newPaymentType, CommonUtils.getNullPropertyNames(paymentType));

                IKeyValuePair iKeyValuePair = replicaCompanyRepository.getDescription(newPaymentType.getLanguageId(), newPaymentType.getCompanyId());
                if (iKeyValuePair != null) {
                    newPaymentType.setLanguageDescription(iKeyValuePair.getLangDesc());
                    newPaymentType.setCompanyName(iKeyValuePair.getCompanyDesc());
                }
                String statusDesc = replicaPaymentTypeRepository.getStatusDescription(paymentType.getStatusCode());
                if (statusDesc != null) {
                    newPaymentType.setStatusText(statusDesc);
                }

                newPaymentType.setCreatedBy(loginUserID);
                newPaymentType.setCreatedOn(new Date());
                newPaymentType.setUpdatedBy(loginUserID);
                newPaymentType.setUpdatedOn(new Date());
                PaymentType paymentTypeCreate = paymentTypeRepository.save(newPaymentType);
                paymentTypes.add(paymentTypeCreate);
            });
        }  catch (Exception e) {
            throw new BadRequestException("Npr Create Error " + e);
        }
        return paymentTypes;

    }


    /**
     *
     * @param updatePaymentType
     * @param loginUserID
     * @return
     */
    public List<PaymentType> updatePaymentType(List<AddPaymentType> updatePaymentType, String loginUserID) {
        List<PaymentType> paymentTypes = new ArrayList<>();

        for (AddPaymentType updatePayment : updatePaymentType) {
            Optional<PaymentType> dbPaymentType = paymentTypeRepository.findByLanguageIdAndCompanyIdAndPaymentTypeIdAndDeletionIndicator(
                    updatePayment.getLanguageId(), updatePayment.getCompanyId(), updatePayment.getPaymentTypeId(), 0L);

            if (dbPaymentType.isPresent()) {
                PaymentType newPaymentType = dbPaymentType.get();
                BeanUtils.copyProperties(updatePayment, newPaymentType, CommonUtils.getNullPropertyNames(updatePayment));

                String statusDesc = replicaPaymentTypeRepository.getStatusDescription(updatePayment.getStatusCode());
                if (statusDesc != null) {
                    newPaymentType.setStatusText(statusDesc);
                }
                newPaymentType.setUpdatedOn(new Date());
                newPaymentType.setUpdatedBy(loginUserID);
                PaymentType paymentType = paymentTypeRepository.save(newPaymentType);
                paymentTypes.add(paymentType);
            }
        }
        return paymentTypes;
    }

    /**
     *
     * @param deletePaymentList
     * @param loginUserID
     */
    public void deletePaymentTypeList(List<PaymentType> deletePaymentList, String loginUserID) {
        if (deletePaymentList != null && !deletePaymentList.isEmpty()) {
            log.info("given values to delete payment type --->  {}", deletePaymentList);

            deletePaymentList.parallelStream().forEach(deleteInput -> {
                PaymentType dbPaymentType = getPaymentType(deleteInput.getLanguageId(), deleteInput.getCompanyId(), deleteInput.getPaymentTypeId());
                dbPaymentType.setDeletionIndicator(1L);
                dbPaymentType.setUpdatedBy(loginUserID);
                dbPaymentType.setUpdatedOn(new Date());
                paymentTypeRepository.save(dbPaymentType);
            });
        }
    }


    /**
     *
     * @param languageId
     * @param companyId
     * @param paymentTypeId
     * @return
     */
    private PaymentType getPaymentType(String languageId, String companyId, String paymentTypeId) {
        Optional<PaymentType> dbPaymentType = paymentTypeRepository.findByLanguageIdAndCompanyIdAndPaymentTypeIdAndDeletionIndicator(
                languageId, companyId, paymentTypeId, 0L);
        if (dbPaymentType.isEmpty()) {
            throw new BadRequestException("PaymentType with the given values:  languageaId: " + languageId + ", companyId: " + companyId + ", paymentTypeId: " + paymentTypeId +  " doesn't exists");
        }
        return dbPaymentType.get();

    }


    /**
     *
     * @return
     */
    public List<ReplicaPaymentType> getAllPaymentTypes() {
        List<ReplicaPaymentType> paymentTypeList = replicaPaymentTypeRepository.findAll();

        paymentTypeList = paymentTypeList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return paymentTypeList;
    }

    /**
     *
     * @param languageId
     * @param companyId
     * @param paymentTypeId
     * @return
     */
    public ReplicaPaymentType getPaymentTypeid(String languageId, String companyId, String paymentTypeId) {

        Optional<ReplicaPaymentType> dbPaymentType = replicaPaymentTypeRepository.findByLanguageIdAndCompanyIdAndPaymentTypeIdAndDeletionIndicator(
                languageId, companyId, paymentTypeId, 0L);

        if (dbPaymentType.isEmpty()) {
            throw new BadRequestException("PaymentType with given values : languageId: " + languageId + ", companyId: " + companyId + ", paymentTypeId: " + paymentTypeId +  " doesn't exists");
        }
        return dbPaymentType.get();
    }

    /**
     *
     * @param findPaymentType
     * @return
     */
    public List<ReplicaPaymentType> findPaymentType(FindPaymentType findPaymentType) {
        ReplicaPaymentTypeSpecification spec = new ReplicaPaymentTypeSpecification(findPaymentType);
        List<ReplicaPaymentType> results = replicaPaymentTypeRepository.findAll(spec);
        return results;
    }
}
