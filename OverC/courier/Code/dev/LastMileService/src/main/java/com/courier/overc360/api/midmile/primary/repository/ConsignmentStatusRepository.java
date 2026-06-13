package com.courier.overc360.api.midmile.primary.repository;

import com.courier.overc360.api.midmile.primary.model.consignmentstatus.ConsignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ConsignmentStatusRepository extends JpaRepository<ConsignmentStatus, String>,
        JpaSpecificationExecutor<ConsignmentStatus> {


    @Query(value = "SELECT MAX(CON_STATUS_ID) + 1 FROM tblconsignmentstatus WHERE IS_DELETED = 0", nativeQuery = true)
    Long getMaxConStatusId();

}
