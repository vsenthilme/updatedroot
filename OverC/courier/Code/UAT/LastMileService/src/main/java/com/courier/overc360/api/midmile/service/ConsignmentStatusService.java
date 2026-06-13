package com.courier.overc360.api.midmile.service;


import com.courier.overc360.api.midmile.primary.model.consignmentstatus.ConsignmentStatus;
import com.courier.overc360.api.midmile.primary.repository.ConsignmentStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class ConsignmentStatusService {

    @Autowired
    ConsignmentStatusRepository consignmentStatusRepository;

    // Auto Gen
    private static final AtomicLong counter = new AtomicLong(1);


    @Transactional
    public ConsignmentStatus insertConsignmentStatusRecord(String languageId, String languageDesc, String companyId, String companyName, String pieceId,
                                              String houseAirwayBill, String hawbType, String hawbTypeId, String hawbTypeDescription,
                                              Date hawbTimeStamp, String pieceType, String pieceTypeId, String pieceTypeDescription,
                                              Date pieceTimeStamp, String loginUserID) {
        ConsignmentStatus newConsignmentStatus = new ConsignmentStatus();
        try {
            log.info("Consignment_Status Insert Record LanguageId - " + languageId + " CompanyId - " + companyId + " PieceId - " + pieceId + " HouseAirwayBill " + houseAirwayBill);
            if (languageId != null && companyId != null && pieceId != null && houseAirwayBill != null) {

                Long maxStatus =  consignmentStatusRepository.getMaxConStatusId();                                                       // CON_STATUS -- MAX + 1
                newConsignmentStatus.setConsignmentStatusId(maxStatus != null ? maxStatus : 1L);
                newConsignmentStatus.setLanguageId(languageId);
                newConsignmentStatus.setLanguageDescription(languageDesc);
                newConsignmentStatus.setCompanyId(companyId);
                newConsignmentStatus.setCompanyName(companyName);
                newConsignmentStatus.setPieceId(pieceId);
                newConsignmentStatus.setHouseAirwayBill(houseAirwayBill);
                newConsignmentStatus.setHawbType(hawbType);
                newConsignmentStatus.setHawbTypeId(hawbTypeId);
                newConsignmentStatus.setHawbTypeDescription(hawbTypeDescription);
                newConsignmentStatus.setHawbTimeStamp(new Date());
                newConsignmentStatus.setPieceType(pieceType);
                newConsignmentStatus.setPieceTypeId(pieceTypeId);
                newConsignmentStatus.setPieceTypeDescription(pieceTypeDescription);
                newConsignmentStatus.setPieceTimeStamp(new Date());
                newConsignmentStatus.setDeletionIndicator(0L);
                newConsignmentStatus.setCreatedBy(loginUserID);
                newConsignmentStatus.setCreatedOn(new Date());
                newConsignmentStatus.setUpdatedBy(loginUserID);
                newConsignmentStatus.setUpdatedOn(new Date());
                consignmentStatusRepository.save(newConsignmentStatus);
                log.info("Consignment Status Updated Successfully <-----> HouseAirwayBill " + houseAirwayBill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newConsignmentStatus;
    }

    @Transactional
    public void insertConsignmentStatusRecordV2(String languageId, String companyId, String pieceId,
                                              String houseAirwayBill, String hawbType, String hawbTypeId, String hawbTypeDescription,
                                              String pieceType, String pieceTypeId, String pieceTypeDescription,
                                              String loginUserID) {
        try {
            log.info("Consignment_Status Insert Record LanguageId - " + languageId + " CompanyId - " + companyId + " PieceId - " + pieceId + " HouseAirwayBill " + houseAirwayBill);
            if (languageId != null && companyId != null && pieceId != null && houseAirwayBill != null) {
                ConsignmentStatus newConsignmentStatus = new ConsignmentStatus();
                Long maxStatus =  consignmentStatusRepository.getMaxConStatusId();                                                       // CON_STATUS -- MAX + 1
                newConsignmentStatus.setConsignmentStatusId(maxStatus != null ? maxStatus : 1L);
                newConsignmentStatus.setLanguageId(languageId);
                newConsignmentStatus.setCompanyId(companyId);
                newConsignmentStatus.setPieceId(pieceId);
                newConsignmentStatus.setHouseAirwayBill(houseAirwayBill);
                newConsignmentStatus.setHawbType(hawbType);
                newConsignmentStatus.setHawbTypeId(hawbTypeId);
                newConsignmentStatus.setHawbTypeDescription(hawbTypeDescription);
                newConsignmentStatus.setHawbTimeStamp(new Date());
                newConsignmentStatus.setPieceType(pieceType);
                newConsignmentStatus.setPieceTypeId(pieceTypeId);
                newConsignmentStatus.setPieceTypeDescription(pieceTypeDescription);
                newConsignmentStatus.setPieceTimeStamp(new Date());
                newConsignmentStatus.setDeletionIndicator(0L);
                newConsignmentStatus.setCreatedBy(loginUserID);
                newConsignmentStatus.setCreatedOn(new Date());
                newConsignmentStatus.setUpdatedBy(loginUserID);
                newConsignmentStatus.setUpdatedOn(new Date());
                consignmentStatusRepository.save(newConsignmentStatus);
                log.info("Consignment Status Updated Successfully <-----> HouseAirwayBill " + houseAirwayBill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
