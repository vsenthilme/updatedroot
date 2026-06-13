package com.courier.overc360.api.midmile.replica.repository;

import com.courier.overc360.api.midmile.replica.model.inventorytable.ReplicaInventoryTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReplicaInventoryTableRepository extends JpaRepository<ReplicaInventoryTable, String>, JpaSpecificationExecutor<ReplicaInventoryTable> {

    Optional<ReplicaInventoryTable> findByLanguageIdAndCompanyIdAndCustomerIdAndHouseAirwayBillAndDeletionIndicator(
            String languageId, String companyId, String customerId, String houseAirwayBill, Long deletionIndicator
    );
}
