package com.tekclover.wms.api.mfg.repository;

import com.tekclover.wms.api.mfg.model.sfgqi.SFGQI;
import com.tekclover.wms.api.mfg.repository.fragments.StreamableJpaSpecificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface SFGQIRepository extends JpaRepository<SFGQI, Long>, JpaSpecificationExecutor<SFGQI>, StreamableJpaSpecificationRepository<SFGQI> {

    Optional<SFGQI> findByLanguageIdAndCompanyCodeIdAndPlantIdAndWarehouseIdAndOperationNumberAndReceipeIdAndProductionOrderNoAndProductionOrderLineNoAndItemCodeAndDeletionIndicator(
            String languageId, String companyCodeId, String plantId, String warehouseId, String operationNumber, String receipeId, String productionOrderNo, Long productionOrderLineNo, String itemCode, Long deletionIndicator
    );
}
