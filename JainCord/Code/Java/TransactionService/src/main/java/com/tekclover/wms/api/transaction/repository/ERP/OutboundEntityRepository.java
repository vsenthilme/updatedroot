package com.tekclover.wms.api.transaction.repository.ERP;


import com.tekclover.wms.api.transaction.model.warehouse.ERP.OutboundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OutboundEntityRepository extends JpaRepository<OutboundEntity, Long>, JpaSpecificationExecutor<OutboundEntity> {
    List<OutboundEntity> findByProcessedStatusId(Long processedStatusId);

    @Query(value = "Update t_outbound set processed_status_id = :statusId " +
            "Where rowid = :rowId", nativeQuery = true)
    void updateOutBoundStatus(@Param("statusId") Long statusId,
                              @Param("rowId") Long rowId);
}
