package com.tekclover.wms.api.mfg.repository;

import com.tekclover.wms.api.mfg.model.fgDelivery.FgDelivery;
import com.tekclover.wms.api.mfg.repository.fragments.StreamableJpaSpecificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface FgDeliveryRepository extends JpaRepository<FgDelivery, Long>, JpaSpecificationExecutor<FgDelivery> , StreamableJpaSpecificationRepository<FgDelivery> {

    Optional<FgDelivery> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode, Long deletionIndicator
    );
}
