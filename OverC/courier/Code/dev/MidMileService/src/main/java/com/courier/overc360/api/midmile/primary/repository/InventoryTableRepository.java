package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.inventorytable.InventoryTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT count(HOUSE_AIRWAY_BILL) from tblinventorytable where status_id = :hawb and is_deleted = 0 and HOUSE_AIRWAY_BILL = :houseAB", nativeQuery = true)
    Long getStatusCount(@Param("hawb") String hawb,
                        @Param("houseAB") String houseAB);
}
