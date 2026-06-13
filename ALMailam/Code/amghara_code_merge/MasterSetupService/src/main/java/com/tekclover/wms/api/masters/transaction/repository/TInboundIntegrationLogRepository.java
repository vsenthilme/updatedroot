package com.tekclover.wms.api.masters.transaction.repository;

import com.tekclover.wms.api.masters.transaction.model.inbound.preinbound.TInboundIntegrationLog;
import com.tekclover.wms.api.masters.transaction.repository.fragments.StreamableJpaSpecificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TInboundIntegrationLogRepository extends JpaRepository<TInboundIntegrationLog,Long>,
		JpaSpecificationExecutor<TInboundIntegrationLog>, StreamableJpaSpecificationRepository<TInboundIntegrationLog> {
	
	public List<TInboundIntegrationLog> findAll();
	public Optional<TInboundIntegrationLog>
		findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndIntegrationLogNumberAndRefDocNumberAndDeletionIndicator(
				String languageId, String companyCodeId, String plantId, String warehouseId, String integrationLogNumber, String refDocNumber, Long deletionIndicator);
	public List<TInboundIntegrationLog> findByIntegrationStatus(String string);
}