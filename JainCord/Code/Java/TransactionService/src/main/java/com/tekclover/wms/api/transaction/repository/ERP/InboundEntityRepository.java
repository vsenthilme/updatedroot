package com.tekclover.wms.api.transaction.repository.ERP;


import com.tekclover.wms.api.transaction.model.warehouse.ERP.InboundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface InboundEntityRepository extends JpaRepository<InboundEntity, Long>, JpaSpecificationExecutor<InboundEntity> {

    List<InboundEntity> findByProcessedStatusId(Long processedStatusId);

    @Modifying
    @Query(value = "Update t_inbound set processed_status_id = :statusId " +
            "where order_ref = :orderNo", nativeQuery = true)
    void updateInbound(@Param("statusId") Long statusId,
                       @Param("orderNo") String orderNo);
}
