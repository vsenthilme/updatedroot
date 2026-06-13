package com.courier.overc360.api.midmile.service;


import com.courier.overc360.api.midmile.primary.model.consignment.AddConsignment;
import com.courier.overc360.api.midmile.primary.model.consignment.ConsignmentEntity;
import com.courier.overc360.api.midmile.primary.model.consignment.ProcessConsignment;
import com.courier.overc360.api.midmile.primary.repository.ProcessedConsignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class ProcessedConsignmentService {

    @Autowired
    ProcessedConsignmentRepository repository;


    // Create Processed Consignment
    public void saveProcessedConsignmentWithLogs(ConsignmentEntity consignmentEntity, String status, Map<Integer, String> logs) {
        ProcessConsignment processedConsignment = new ProcessConsignment();
        processedConsignment.setConsignmentId(consignmentEntity.getHouseAirwayBill());
        processedConsignment.setPartnerId(consignmentEntity.getPartnerId());
        processedConsignment.setHouseAirwayBill(consignmentEntity.getPartnerHouseAirwayBill());
        processedConsignment.setMasterAirwayBill(consignmentEntity.getPartnerMasterAirwayBill());
        processedConsignment.setStatus(status); // SUCCESS or FAILED
        processedConsignment.setCreatedOn(new Date());
        // Dynamically save logs into reference fields
        processedConsignment.setReferenceField1(logs.getOrDefault(1, null));
        processedConsignment.setReferenceField2(logs.getOrDefault(2, null));
        processedConsignment.setReferenceField3(logs.getOrDefault(3, null));
        processedConsignment.setReferenceField4(logs.getOrDefault(4, null));
        processedConsignment.setReferenceField5(logs.getOrDefault(5, null));
        processedConsignment.setReferenceField6(logs.getOrDefault(6, null));
        processedConsignment.setReferenceField7(logs.getOrDefault(7, null));
        processedConsignment.setReferenceField8(logs.getOrDefault(8, null));
        processedConsignment.setReferenceField9(logs.getOrDefault(9, null));
        processedConsignment.setReferenceField10(logs.getOrDefault(10, null));
        repository.save(processedConsignment);
    }










}
