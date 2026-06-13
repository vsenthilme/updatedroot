package com.tekclover.wms.api.transaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tekclover.wms.api.transaction.model.inbound.preinbound.v2.PreInboundLineEntityV2;

@Repository
@Transactional
public interface PreInboundLineV2Repository extends JpaRepository<PreInboundLineEntityV2,Long>, JpaSpecificationExecutor<PreInboundLineEntityV2> {
	
	public List<PreInboundLineEntityV2> findByPreInboundNoAndDeletionIndicator(String preInboundNo, Long deletionIndicator);
	
	/**
	 * 
	 * @param rssFeedEntryId
	 * @param isRead
	 */
	@Modifying(clearAutomatically = true)
	@Query("UPDATE PreInboundLineEntityV2 ib SET ib.statusId = :statusId WHERE ib.warehouseId = :warehouseId AND ib.refDocNumber = :refDocNumber")
	void updatePreInboundLineStatus(@Param ("warehouseId") String warehouseId,
			@Param ("refDocNumber") String refDocNumber, @Param ("statusId") Long statusId);

	public List<PreInboundLineEntityV2> findByWarehouseIdAndPreInboundNoAndDeletionIndicator(String warehouseId,
			String preInboundNo, long l);
}