package com.tekclover.wms.api.masters.transaction.repository;

import com.tekclover.wms.api.masters.transaction.model.auditlog.TAuditLog;
import com.tekclover.wms.api.masters.transaction.repository.fragments.StreamableJpaSpecificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TAuditLogRepository extends JpaRepository<TAuditLog, Long>, JpaSpecificationExecutor<TAuditLog>,
		StreamableJpaSpecificationRepository<TAuditLog> {
	public TAuditLog findByAuditLogNumber(String auditLogNumber);

	@Query(value ="select max(AUD_LOG_NO)+1 \n"+
			" from tbltransactionauditlog ",nativeQuery = true)
	public String getAuditLogNumber();
}