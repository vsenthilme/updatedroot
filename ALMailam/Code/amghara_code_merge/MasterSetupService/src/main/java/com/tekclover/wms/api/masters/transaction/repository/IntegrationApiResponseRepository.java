package com.tekclover.wms.api.masters.transaction.repository;

import com.tekclover.wms.api.masters.transaction.model.integration.IntegrationApiResponse;
import com.tekclover.wms.api.masters.transaction.repository.fragments.StreamableJpaSpecificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface IntegrationApiResponseRepository extends JpaRepository<IntegrationApiResponse,Long>,
		StreamableJpaSpecificationRepository<IntegrationApiResponse> {

	public List<IntegrationApiResponse> findByOrderNumber (String orderNumber);
}