package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.consignment.ConsignmentEntity;
import com.courier.overc360.api.midmile.primary.model.inventorytable.InventoryTable;
import com.courier.overc360.api.midmile.primary.repository.InventoryTableRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class InventoryTableService {

    @Autowired
    InventoryTableRepository inventoryTableRepository;

    /*---------------------------------------------------Inventory Create-------------------------------------*/

    /**
     * InventoryTable Create
     *
     * @param consignmentEntity
     */
    public void InventoryTableInsert(ConsignmentEntity consignmentEntity, String loginUserID, Long agingCount) {

        try {
            // Create new Drs and related entities
            InventoryTable newInventoryTable = new InventoryTable();
            BeanUtils.copyProperties(consignmentEntity, newInventoryTable, CommonUtils.getNullPropertyNames(consignmentEntity));

            // Checking for duplicate record
            if (newInventoryTable.getHouseAirwayBill() != null) {
                boolean duplicate = inventoryTableRepository.existsByLanguageIdAndCompanyIdAndCustomerIdAndHouseAirwayBillAndDeletionIndicator(
                        newInventoryTable.getLanguageId(), newInventoryTable.getCompanyId(), newInventoryTable.getCustomerId(), newInventoryTable.getHouseAirwayBill(), 0L
                );
                if (duplicate) {
                    throw new BadRequestException("Record is getting Duplicated with the given values");
                }
            }
            Long statusCount = inventoryTableRepository.getStatusCount(consignmentEntity.getHawbTypeId(), consignmentEntity.getHouseAirwayBill());
            if (agingCount >= statusCount) {
                // Setting Required fields
                newInventoryTable.setAgingCount(String.valueOf(agingCount));
                newInventoryTable.setPartnerHouseAirwayBill(consignmentEntity.getPartnerHouseAirwayBill());
                newInventoryTable.setPartnerMasterAirwayBill(consignmentEntity.getPartnerMasterAirwayBill());
                newInventoryTable.setStorageId(consignmentEntity.getStorageLocation());
                newInventoryTable.setStatusId(consignmentEntity.getHawbTypeId());
                newInventoryTable.setStatusText(consignmentEntity.getHawbTypeDescription());
                newInventoryTable.setStatusTimestamp(consignmentEntity.getHawbTimeStamp());
                newInventoryTable.setCustomerId(consignmentEntity.getPartnerId());
                newInventoryTable.setCustomerName(consignmentEntity.getPartnerName());
                newInventoryTable.setUpdatedBy(loginUserID);
                newInventoryTable.setUpdatedOn(new Date());
                inventoryTableRepository.save(newInventoryTable);
            } else {
                throw new BadRequestException("The no of Inventory scans are exceeded");
            }
        } catch (Exception e) {
            throw new BadRequestException("InventoryTable Insert Error " + e);
        }

    }
}
