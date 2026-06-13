package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.inventorytable.InventoryTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface InventoryTableRepository extends JpaRepository<InventoryTable, String> {

    Optional<InventoryTable> findByLanguageIdAndCompanyIdAndCustomerIdAndHouseAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String customerId, String houseAirwayBill, Long deletionIndicator
    );

    boolean existsByLanguageIdAndCompanyIdAndCustomerIdAndHouseAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String customerId, String houseAirwayBill, Long deletionIndicator
    );
}
