package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.controller.exception.BadRequestException;
import com.courier.overc360.api.midmile.primary.model.drs.Drs;
import com.courier.overc360.api.midmile.primary.model.inventorytable.AddInventoryTable;
import com.courier.overc360.api.midmile.primary.model.inventorytable.InventoryTable;
import com.courier.overc360.api.midmile.primary.model.inventorytable.UpdateInventoryTable;
import com.courier.overc360.api.midmile.primary.repository.InventoryTableRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import com.courier.overc360.api.midmile.replica.model.inventorytable.FindInventoryTable;
import com.courier.overc360.api.midmile.replica.model.inventorytable.ReplicaInventoryTable;
import com.courier.overc360.api.midmile.replica.repository.ReplicaInventoryTableRepository;
import com.courier.overc360.api.midmile.replica.repository.specification.ReplicaInventoryTableSpecification;
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
public class InventoryTableService {

    @Autowired
    InventoryTableRepository inventoryTableRepository;

    @Autowired
    ReplicaInventoryTableRepository replicaInventoryTableRepository;

    @Autowired
    NumberRangeService numberRangeService;

    /*---------------------------------------------------PRIMARY-----------------------------------------------------*/

    /**
     * Get InventoryTable
     *
     * @param languageId
     * @param companyId
     * @param customerId
     * @param houseAirwayBill
     * @return
     */
    public InventoryTable getInventoryTable(String languageId, String companyId, String customerId, String houseAirwayBill) {
        Optional<InventoryTable> dbInventoryTable = inventoryTableRepository.findByLanguageIdAndCompanyIdAndCustomerIdAndHouseAirwayBillAndDeletionIndicator(
                languageId, companyId, customerId, houseAirwayBill, 0L
        );
        if (dbInventoryTable.isEmpty()) {
            throw new BadRequestException("DRS with the given values:  languageaId: " + languageId + ", companyId: " + companyId + ", customerId: " + customerId + ", houseAirwayBill: " + houseAirwayBill + " doesn't exists");
        }
        return dbInventoryTable.get();
    }

    /**
     * Create inventoryTable
     *
     * @param addInventoryTable
     * @param loginUserID
     * @return
     */
    public InventoryTable createInventoryTable(AddInventoryTable addInventoryTable, String loginUserID) {
        try {
            // Checking for duplicate record
            if (addInventoryTable.getHouseAirwayBill() != null) {
                boolean duplicate = inventoryTableRepository.existsByLanguageIdAndCompanyIdAndCustomerIdAndHouseAirwayBillAndDeletionIndicator(
                        addInventoryTable.getLanguageId(), addInventoryTable.getCompanyId(), addInventoryTable.getCustomerId(), addInventoryTable.getHouseAirwayBill(), 0L
                );
                if (duplicate) {
                    throw new BadRequestException("Record is getting Duplicated with the given values");
                }
            }

            // Create new Drs and related entities
            InventoryTable newInventoryTable = new InventoryTable();
            BeanUtils.copyProperties(addInventoryTable, newInventoryTable, CommonUtils.getNullPropertyNames(addInventoryTable));

            newInventoryTable.setCreatedBy(loginUserID);
            newInventoryTable.setCreatedOn(new Date());
            newInventoryTable.setUpdatedBy(loginUserID);
            newInventoryTable.setUpdatedOn(new Date());

            return inventoryTableRepository.save(newInventoryTable);
        } catch (Exception e) {
            throw new BadRequestException("InventoryTable Create Error " + e);
        }
    }

    /**
     * Create InventoryTable List
     *
     * @param addInventoryTableList
     * @param loginUserID
     * @return
     */
    public List<InventoryTable> createInventoryTableList(List<AddInventoryTable> addInventoryTableList, String loginUserID) {

        List<InventoryTable> inventoryTableList = new ArrayList<>();
        try {
            addInventoryTableList.stream().forEach(inventoryTable -> {
                AddInventoryTable newInventoryTable = new AddInventoryTable();
                BeanUtils.copyProperties(inventoryTable, newInventoryTable);
                inventoryTableList.add(createInventoryTable(newInventoryTable, loginUserID));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inventoryTableList;
    }

    /**
     * Update InventoryTable List
     *
     * @param updateInventoryList
     * @param loginUserID
     * @return
     */
    public List<InventoryTable> updateInventoryTableList(List<InventoryTable> updateInventoryList, String loginUserID) {

        List<InventoryTable> updateInventoryTableList = new ArrayList<>();

        for (InventoryTable updateInventoryTable : updateInventoryList) {
            Optional<InventoryTable> dbInventoryTable = inventoryTableRepository.findByLanguageIdAndCompanyIdAndCustomerIdAndHouseAirwayBillAndDeletionIndicator(updateInventoryTable.getLanguageId(), updateInventoryTable.getCompanyId(), updateInventoryTable.getCustomerId(), updateInventoryTable.getHouseAirwayBill(), 0L);

            if (dbInventoryTable.isPresent()) {
                InventoryTable newInventory = dbInventoryTable.get();
                BeanUtils.copyProperties(updateInventoryTable, newInventory, CommonUtils.getNullPropertyNames(updateInventoryTable));
                newInventory.setUpdatedOn(new Date());
                newInventory.setUpdatedBy(loginUserID);
                InventoryTable drs1 = inventoryTableRepository.save(newInventory);
                updateInventoryTableList.add(drs1);
            }
        }
        return updateInventoryList;
    }

    /**
     * Update inventoryTable
     *
     * @param updateInventoryTable
     * @param loginUserID
     * @return
     */
    public InventoryTable updateInventoryTable(UpdateInventoryTable updateInventoryTable, String loginUserID) {

        Optional<InventoryTable> dbInventory = inventoryTableRepository.findByLanguageIdAndCompanyIdAndCustomerIdAndHouseAirwayBillAndDeletionIndicator(
                updateInventoryTable.getLanguageId(), updateInventoryTable.getCompanyId(), updateInventoryTable.getCustomerId(), updateInventoryTable.getHouseAirwayBill(), 0L
        );

        InventoryTable newInventoryTable = new InventoryTable();
        BeanUtils.copyProperties(updateInventoryTable, newInventoryTable, CommonUtils.getNullPropertyNames(updateInventoryTable));
        newInventoryTable.setUpdatedBy(loginUserID);
        newInventoryTable.setUpdatedOn(new Date());
        return inventoryTableRepository.save(newInventoryTable);
    }

    /**
     * Delete inventoryTableList
     *
     * @param inventoryTableList
     * @param loginUserID
     */
    public void deleteInventoryList(List<InventoryTable> inventoryTableList, String loginUserID) {

        if (inventoryTableList != null && !inventoryTableList.isEmpty()) {
            log.info("given values to delete inventoryTable -------> {}", inventoryTableList);

            inventoryTableList.parallelStream().forEach(deleteInventory -> {
                InventoryTable dbInventory = getInventoryTable(
                        deleteInventory.getLanguageId(), deleteInventory.getCompanyId(), deleteInventory.getCustomerId(), deleteInventory.getHouseAirwayBill()
                );
                dbInventory.setDeletionIndicator(1L);
                dbInventory.setUpdatedBy(loginUserID);
                dbInventory.setUpdatedOn(new Date());
                inventoryTableRepository.save(dbInventory);
            });
        }
    }

    /**
     * Delete InventoryTable
     *
     * @param languageId
     * @param companyId
     * @param customerId
     * @param houseAirwayBill
     * @param loginUserID
     */
    public void deleteInventoryTable(String languageId, String companyId, String customerId, String houseAirwayBill, String loginUserID) {

        InventoryTable dbInventory = getInventoryTable(languageId, companyId, customerId, houseAirwayBill);
        dbInventory.setDeletionIndicator(1L);
        dbInventory.setUpdatedBy(loginUserID);
        dbInventory.setUpdatedOn(new Date());
        inventoryTableRepository.save(dbInventory);
    }

    /*---------------------------------------------------REPLICA-----------------------------------------------------*/

    /**
     * Get inventory table list
     *
     * @return
     */
    public List<ReplicaInventoryTable> getAllInventoryTable() {
        List<ReplicaInventoryTable> inventoryTableList = replicaInventoryTableRepository.findAll();

        inventoryTableList = inventoryTableList.stream().filter(i -> i.getDeletionIndicator() == 0).collect(Collectors.toList());
        return inventoryTableList;
    }

    /**
     * Get InventoryTable
     *
     * @param languageId
     * @param companyId
     * @param customerId
     * @param houseAirwayBill
     * @return
     */
    public ReplicaInventoryTable getReplicaInventoryTable(String languageId, String companyId, String customerId, String houseAirwayBill) {

        Optional<ReplicaInventoryTable> dbInventory = replicaInventoryTableRepository.findByLanguageIdAndCompanyIdAndCustomerIdAndHouseAirwayBillAndDeletionIndicator(
                languageId, companyId, customerId, houseAirwayBill, 0L
        );

        if (dbInventory.isEmpty()) {
            throw new BadRequestException("InventoryTable with given values : languageId: " + languageId + ", companyId: " + companyId + ", customerId: " + customerId + ", houseAirwayBill: " + houseAirwayBill + " doesn't exists");
        }
        return dbInventory.get();
    }

    /**
     * Find inventoryTable
     *
     * @return
     */
    public List<ReplicaInventoryTable> findInventoryTable(FindInventoryTable findInventoryTable) {

        ReplicaInventoryTableSpecification spec = new ReplicaInventoryTableSpecification(findInventoryTable);
        List<ReplicaInventoryTable> results = replicaInventoryTableRepository.findAll(spec);
        log.info("Found InventoryTable ----> " + results);
        return results;
    }
}
